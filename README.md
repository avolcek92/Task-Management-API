//Handle ENUMS?
//Unit and Integration tests
//

Here I describe some implementation decisions:

First of all, this API created for job interview, so it's not oriented to grow, so I decide:
Use Id type int instead of long or UUID.
Don't split in separate modules and use simple structure.

I hesitate between 3 ways of filtering implementation:
1. Use @GetMapping with one filter on all columns in parameter.
2. Use @GetMapping with every column filter in parameter.
3. Use @PostMapping with separate filter for every column in body
I decide to use second one, because I think Get request better solution to get data, than Post.
And for task-management-api for me is better to have filtering by column, than one search box. 

To handle responses between service and controller(not found, already exist) I decide to not use exceptions, 
to be faster and more simple.

To validate incoming data I use spring library and exception handler class.

To make my code more clear for you, I decide to add comments in service classes. 

Status modification date.

Created by Artur Volček.