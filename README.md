#Task-Management-API

---
### Owner

- Artur Volcek

---
### Tools and Technologies Used ###
* Spring Boot
* Maven
* H2
* Java 11
* Swagger




---

## Steps to run the application locally: 

1. Clone the repo
  ```sh
  git clone https://github.com/avolcek92/Task-Management-API.git
  ```
2. Pack the project
  ```sh
mvn clean package
 ```
3. Run project
  ```sh
java -jar target/task-management-api-0.0.1-SNAPSHOT.jar
 ```

---

 ## Commands:
#### Get all tasks

Send get requests to:
  ```sh
http://localhost:8081/api/v1/tasks
 ```

You can also get tasks by parameters, just add parameters after ? symbol:

  ```sh
http://localhost:8081/api/v1/tasks?pageSize=2
 ```
Available parameters:
  ```sh
pageSize
page
sortField
name
description
status
group
assignee
duration
 ```

---
####Create task:

Send post requests to:
  ```sh
http://localhost:8081/api/v1/tasks
 ```
Body:
  ```sh
{
    "name": "Create Front",
    "description": "Create Front with smile",
    "group": "FRONTEND",
    "status": "CREATED",
    "assignee": "Homer Simpson"
    "subTask": []
}
```
Assignee and subtask is not required fields

---
####Remove task by Id:

Send delete requests to:

  ```sh
http://localhost:8081/api/v1/tasks/1
 ```

---
####Get task by Id:

Send get requests to:

  ```sh
http://localhost:8081/api/v1/tasks/1
 ```

---
####Update task:

Send put requests to:

  ```sh
http://localhost:8081/api/v1/tasks/1
 ```
Body:
  ```sh
{   
    "id": 1,
    "name": "Create Front",
    "description": "Create Front with smile",
    "group": "FRONTEND",
    "status": "CREATED",
    "assignee": "Homer Simpson"
    "subTask": []
}
```
Assignee and subtask is not required fields


You also can send GET,POST,PUT,DELETE requests to /subTasks endpoint

---
## Deployment:



---
## A covering note explaining the choices for application design:
As my project required to be finished in a limited time:

For the project structure, I'm using layered architecture, because :
- It's a quick way to get the application running without much complexity.
- Layers can be independently tested.
- It's well known by developers so everybody can easily find their way through the codebase

---
### Layers:
- Controller - The most important package, it's binds everything together right from the moment a request is intercepted till the response is prepared and sent back.
  The best practices suggest that we keep this layer versioned to support multiple versions of the application and the same practice is applied here.
  I use ResponseEntity to send statuses for UI.

- DAO package - The data access objects (DAOs) are present in the DAO package. They are all extensions of the JpaRepository interface helping the service layer to persist and retrieve the data from H2.
  I used JpaSpecificationExecutor to implement filters for every column.

- Domain - The various models of the application are organized under this package.
  I used an int type for the model ID because the project is not expected to grow.
  I have added different status change dates to be able to track the scope of work.

- DTO - Represents data transfer objects, DTOs let us transfer only the data that we need to share with the user interface.
  There are different opinions about whether we should use DTOs or not, I belong to the set of minds who think we definitely should.
  And not using DTOs makes your model layer very tightly coupled with the UI layer.

- Exception - I didn't create any custom exceptions, but I created ValidationExceptionHandler to handle validation exceptions and give a response to the user interface about the invalid request.

- Mapper - I don't use ModelMapper, MapStruct, or other libraries, I just created simple custom mappers to map DTOs with Entities.

- Service - There we can find all the main business logic of this project. To make my code more clear for you, I decide to add comments in service classes.
  In this package I also add implementation for filtering, sorting, pagination, status dates changes and task duration.

---
### Tests

I created an integration test to cover the whole project, where I mock requests to the controller. Unit tests are covering the service part because there is the biggest part of the project logic.

---
## Other considerations/future enhancements or improvements

Add more unit Tests.
Create Frontend for this application.
Use PostgreSQL or MySql database instead of H2.
Use UUID for entities id instead of int.