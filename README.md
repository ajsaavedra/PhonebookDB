# PhonebookDB
This is a simple address book that implements a doubly-linked list from scratch. In addition to running CRUD operations on user input,
users are able to save to and upload data from a file.

##Building  

This project repo is ready to be used from Eclipse.

In Eclipse go to: File/New/Java Project

Un-check "Use default location" and then click Browse and navigate to the top level directory with contains your source, libs, configs, etc.

Eclipse will display a warning that says that your project "overlaps the location of another project".

Give your project the directory name, and now Eclipse will let you click on the "Next" button to continue configuration of your project.

##How to use

To use the application, simply fill out all the required fields in the form, and hit the Add Friend button.

To save your collection, fill out the File Name field and hit Save.

New entries are saved into the linked list in alphabetical order. Users can sort their friends by age, or revert the list to its original
alphabetical form.

To find an entry, simply type in a first or last name or both, and click Find Friend.

If saving a new entry with a duplicate first and last name, users can either update or cancel the Save operation.
