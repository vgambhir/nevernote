# nevernote
Nevenote APIs

### Objective:
Design and implement a "Nevernote" REST APIs that can be used to save, retrieve and update organized notes.

### Assumptions:
1. Notebook name and note title are not unique. Used auto-generated ID to distinguish between resources
2. Deletion/Get/Update operation will use resource ID as input
3. Note tags are case agnostic. Tag "A" is equal to "a"
4. Created and LastModified captured the Unix timestamp (long milliseconds)

### Components:
- Java : 1.8
- Spring Boot 2.0.2 (embedded tomcat), lombok, spring test framework

### Build Tool:
- maven (apache-maven-3.5.3)

### Notes:
- Application is configured to run on port 8080. To change the port at runtime use the following command or modify application.properties <server.port>
```
java -jar nevernote-0.0.1-SNAPSHOT.jar --server.port=<portNumber>
```

### Build REST service Application:
Using maven wrapper to build, run tests and start application
A. Build application
```
$ ./mvnw clean install
```
B. Run application using maven
```
$ ./mvnw spring-boot:run
```
C. Run application using jar
```
java -jar nevernote-0.0.1-SNAPSHOT.jar
```

### REST API overview
Context path = /apis

| Uri | Http Method | Request| Response| Description|
| --- | ----------- | ------ | ------- | ---------- |
| /notebooks/{bookId} | GET | | 200, [{'id':1','name':'Book-1'},notes[]]  | Get notebook by ID |                         
| /notebooks | POST | {'title':'test title','content':'test content'} | 201| Create a new notebook |
| /notebooks/{bookId} | DELETE |  | 204| Delete a notebook by ID |
| /notebooks/{bookId}/notes | POST |  | 201| Add a note to a certain notebook |
| /notebooks/{bookId}/notes/{noteId} | POST |  | 200| Update note by ID in a certain notebook|
| /notebooks/{bookId}/notes/{noteId} | GET |  | 200| Get a note by ID in a certain notebook|
| /notebooks/{bookId}/notes/{noteId} | DELETE |  | 204| Delete a note by ID in a certain notebook |
| /notebooks/{bookId}/notes?tag={tag} | GET |  | 200| Filter notes in a certain notebook by given tag |






Good to have
- versioning

API documentation
with error codes

junit if time

date time stamp format