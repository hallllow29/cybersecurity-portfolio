from tkinter import *
from tkinter import messagebox
from tkinter.simpledialog import askstring
from crypto import encrypt_password, decrypt_password


def decrypt_specific_website(website_entry):
    website_to_find = askstring("Input", "Enter website to find:")
    if website_to_find:
        read_and_decrypt(website_entry, website_to_find)


def read_and_decrypt(website_entry, website_to_find):
    found = False
    try:
        with open("data.txt", "r") as data_file:
            lines = data_file.readlines()
            for line in lines:
                website, email, encrypted_password = line.strip().split(" | ")
                if website == website_to_find:
                    found = True
                    decrypted_password = decrypt_password(encrypted_password)
                    messagebox.showinfo(title="Decrypted Info",
                                        message=f"Website: {website}\nEmail: {email}\nPassword: {decrypted_password}")
                    break
            if not found:
                messagebox.showinfo(title="Error", message=f"No password found for {website_to_find}.")
    except FileNotFoundError:
        messagebox.showinfo(title="Error", message="No data file found.")


def save(website_entry, email_entry, password_entry):
    website = website_entry.get()
    email = email_entry.get()
    password = password_entry.get()

    if len(website) == 0 or len(password) == 0:
        messagebox.showinfo(title="Oops", message="Please make sure you haven't left any fields empty.")
    else:
        is_ok = messagebox.askokcancel(title=website, message=f"These are the details entered: \nEmail: {email}"
                                                              f"\nPassword: {password} \nIs it ok to save?")

    if is_ok:
        encrypted_password = encrypt_password(password)
        with open("data.txt", "a") as data_file:
            data_file.write(f"{website} | {email} | {encrypted_password}\n")
            website_entry.delete(0, END)
            password_entry.delete(0, END)
