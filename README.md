## Library CLI Application

#### To use the application you need
- Java SDK 11
- Maven

#### To run the app via the project
###### You need to build the application by this command :
`mvn clean package`

###### And run the app :
`java -jar cli/target/tpcleancode.cli-1.0-SNAPSHOT-shaded.jar [option]`

#### Options to add in argument of the command application execution
##### To see all content
`seeContent`
##### To add book (only a librarian can add book. You can see the list of user in userStorage.txt)
`addBook [user login] [book title] [book author name] [book reference]`

##### To borrow book (a guest can't borrow book. You can see the list of user in userStorage.txt)
`borrowBook [user login] [book title]`