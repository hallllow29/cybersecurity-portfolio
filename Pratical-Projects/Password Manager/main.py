from tkinter import *

from crypto import encrypt_file, decrypt_file
from gui import save, decrypt_specific_website

#Graphic Interface
window = Tk()
window.title("Password Manager")
window.config(padx=50, pady=50)

canvas = Canvas(height=200, width=200)
logo_img = PhotoImage(file="logo.png")
canvas.create_image(100, 100, image=logo_img)
canvas.pack()
canvas.grid(row=0, column=1)

#Labels
website_label = Label(text="Website:")
website_label.grid(row=1, column=0)
email_label = Label(text="E-mail/Username:")
email_label.grid(row=2, column=0)
password_label = Label(text="Password:")
password_label.grid(row=3, column=0)

#Entries
website_entry = Entry(width=35)
website_entry.grid(row=1, column=1, columnspan=2)
website_entry.focus()

email_entry = Entry(width=35)
email_entry.grid(row=2, column=1, columnspan=2)
email_entry.insert(0, "pedro.14895@gmail.com")

password_entry = Entry(width=35, show="*")
password_entry.grid(row=3, column=1, columnspan=2)

#Buttons
add_button = Button(text="Add", width=36, command=lambda: save(website_entry, email_entry, password_entry))
add_button.grid(row=4, column=1, columnspan=2)

decrypt_button = Button(text="Decrypt Specific Website", width=36, command=lambda: decrypt_specific_website(website_entry))
decrypt_button.grid(row=5, column=1, columnspan=2)

encrypt_file_button = Button(text= "Encrypt a file", width=36, command=encrypt_file)
encrypt_file_button.grid(row=6, column=1, columnspan=2)

decrypt_file_button = Button(text="Decrypt a file", width=36, command=decrypt_file)
decrypt_file_button.grid(row=7, column=1, columnspan=2)

window.mainloop()
