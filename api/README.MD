## TODO API in several platform
each folder contains an implementation of `todo api` in the following technologies
* .Net
* NodeJS
* Java

## Data Model
    todo:String
    isDone: Boolean

## API
* GET /api/todo

    return all items in the collection 

* GET /api/todo/:id

    return 1 item specified in the id

* POST /api/todo

    If the id is specified in the body then it is update otherwise it is in insert

* DELETE /api/todo

    Removes the item from the todo collection 