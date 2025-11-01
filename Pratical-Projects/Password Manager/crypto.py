from tkinter import filedialog, messagebox

from cryptography.fernet import Fernet

def encrypt_password(password):
    result = ""
    detachment = 5

    for char in password:
        if char.isupper():
            result += chr((ord(char) + detachment - 65) % 26 + 65)
        elif char.islower():
            result += chr((ord(char) + detachment - 97) % 26 + 97)
        elif char.isdigit():
            result += chr((ord(char) + detachment - 48) % 10 + 48)
        else:
            result += chr((ord(char) + detachment) % 256)

    return result


def decrypt_password(password):
    result = ""
    detachment = 5

    for char in password:
        if char.isupper():
            result += chr((ord(char) - detachment - 65) % 26 + 65)
        elif char.islower():
            result += chr((ord(char) - detachment - 97) % 26 + 97)
        elif char.isdigit():
            result += chr((ord(char) - detachment - 48) % 10 + 48)
        else:
            result += chr((ord(char) - detachment) % 256)

    return result



def encrypt_file():
    file_path = filedialog.askopenfilename()
    if file_path:
        try:
            with open("key.key", "rb") as key_file:
                key = key_file.read()
        except FileNotFoundError:
            key = Fernet.generate_key()
            with open("key.key", "wb") as key_file:
                key_file.write(key)

        cipher_suite = Fernet(key)

        with open(file_path, 'rb') as file:
            file_data = file.read()
            encrypted_data = cipher_suite.encrypt(file_data)
            encrypted_file_path = file_path + ".enc"
            with open(encrypted_file_path, 'wb') as encrypted_file:
                encrypted_file.write(encrypted_data)
        messagebox.showinfo("Success", f"File encrypted and saved as: {encrypted_file_path}")

def decrypt_file():
    file_path = filedialog.askopenfilename()
    if file_path:
        try:
            with open("key.key", "rb") as key_file:
                key = key_file.read()
        except FileNotFoundError:
            messagebox.showerror("Error", "Key file not found. Encrypt a file first.")
            return

        cipher_suite = Fernet(key)

        if not file_path.endswith(".enc"):
            messagebox.showerror("Error", "Selected file is not encrypted.")
            return

        try:
            with open(file_path, 'rb') as encrypted_file:
                encrypted_data = encrypted_file.read()
                decrypted_data = cipher_suite.decrypt(encrypted_data)
                decrypted_file_path = file_path[:-4]
                with open(decrypted_file_path, 'wb') as decrypted_file:
                    decrypted_file.write(decrypted_data)
            messagebox.showinfo("Success", f"File decrypted and saved as: {decrypted_file_path}")
        except Exception as e:
            messagebox.showerror("Error", f"Decryption failed: {str(e)}")