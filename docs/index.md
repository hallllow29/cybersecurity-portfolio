---
title: My Portfolio
description: CTF writeups, projects & security experiments
---

<!-- HERO -->
<div style="display:flex;gap:20px;align-items:center;margin-bottom:1.5rem;">
  <div style="flex:0 0 120px;">
    <!-- se tiveres avatar, muda o src -->
    <img src="/assets/avatar.png" alt="avatar" style="width:120px;border-radius:12px;object-fit:cover;">
  </div>
  <div>
    <h1 style="margin:0 0 .25rem 0">Olá — sou <strong>Hallllow29</strong></h1>
    <p style="margin:0;color:#555;">CTF player • security enthusiast • writeups & projects. Aqui mostro os meus repositórios, writeups e demos.</p>
    <p style="margin-top:.5rem;">
      <a href="https://github.com/hallllow29" target="_blank">GitHub</a> •
      <a href="https://www.linkedin.com/in/yourprofile" target="_blank">LinkedIn</a> •
      <a href="/contact">Contact</a>
    </p>
  </div>
</div>

---

<!-- BADGES -->
<p>
  <img src="https://img.shields.io/badge/CTF-active-brightgreen" alt="ctf">
  <img src="https://img.shields.io/badge/languages-Python%20%7C%20Bash-blue" alt="langs">
  <img src="https://img.shields.io/badge/tools-Burp%20%7C%20John%20%7C%20Nmap-orange" alt="tools">
</p>

---

<!-- PROJECTS (auto list) -->
## Projects

{% assign projects = site.pages | where_exp:"p","p.url contains '/projects/'" %}
{% if projects.size > 0 %}
<div>
  {% for p in projects %}
  <article style="margin-bottom:1.25rem;padding:12px;border-radius:8px;border:1px solid #eee;">
    <h3 style="margin:.1rem 0"><a href="{{ p.url }}">{{ p.title | default: p.url | split: '/' | last }}</a></h3>
    <div style="color:#444;">
      {% if p.excerpt %}
        {{ p.excerpt | markdownify }}
      {% else %}
        {{ p.content | strip_html | truncatewords:40 }}
      {% endif %}
    </div>
    <p style="margin-top:.5rem"><a href="{{ p.repo | default: '#' }}" target="_blank">View on GitHub</a></p>
  </article>
  {% endfor %}
</div>
{% else %}
<p>No projects auto-detected yet. Add project folders to <code>docs/projects/</code> or run the sync workflow to populate them.</p>
{% endif %}

---

<!-- WRITEUPS -->
## Latest writeups

<ul>
  {% for page in site.pages %}
    {% if page.url contains '/writeups/' %}
      <li><a href="{{ page.url }}">{{ page.title | default: page.url }}</a></li>
    {% endif %}
  {% endfor %}
</ul>

---

<!-- CONTACT / FOOTER -->
## Contact

Prefer email? <code>your.email@example.com</code> • Or open an issue on any repo.

---

*This site is generated with GitHub Pages. If something is missing, check `docs/projects/` or trigger the sync action.*

