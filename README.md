
//Add comments on methods
//Validators
//Responses
//Exception Handler
//Tests
//Subtask get by task id/remove/create
//Update Task
//Insert Script
//Implement duration
//Check Sorting

Useful links:
https://idratherbewriting.com/learnapidoc/docapis_doc_parameters.html
https://habr.com/ru/post/536612/
https://medium.com/@mwaysolutions/10-best-practices-for-better-restful-api-cbe81b06f291
https://stackoverflow.blog/2020/03/02/best-practices-for-rest-api-design/
https://codeburst.io/spring-boot-rest-microservices-best-practices-2a6e50797115
https://www.baeldung.com/spring-response-entity

API More oriented to by simple tha to grow
null 
exceptions

Here I describe some implementation decisions:

First of all, this API created for job interview, so it's not oriented to grow, so I decide:
Use Id type int instead of long or UUID.
Don't split in separate modules and use simple structure.

I hesitate between two ways of filtering implementation:
1. Use @GetMapping with one filter on all columns in parameter.
2. Use @PostMapping with separate filter for every column in body
I decide to use first one, because for me is more convenient use one search box to search what I need.

To handle responses between service and controller(not found, already exist) I decide to not use exceptions, 
to be faster and more simple.

To validate incoming data I use spring library and exception handler class.

To make my code more clear for you, I decide to add comments in service classes. 

Status modification date.

Created by Artur Volček.