## Library CLI Application

##### To use the application you need
- Java SDK 11
- Maven

#### Options to add in argument of the command application execition
##### To see all content
`seeContent`
##### To add book (only a librarian can add book. You can see the list of user in userStorage.txt)
`addBook [user login] [book title] [book author name] [book reference]`

##### To borrow book (a guest can't borrow book. You can see the list of user in userStorage.txt)
`borrowBook [user login] [book title]`