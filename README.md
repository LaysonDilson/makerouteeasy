# makerouteeasy
Simple app to try solve the Vehicle Routing Problem


## Table of content

- [Requirements](#Requirements)
- [Quickstart](#quickstart)

## Installation

Java - jdk 11
MongoDB - latest

### MongoDB

need to create a db with name of "makerouteeasy"

to create user use this command in mongodb shell
```bash
    db.createUser(
    {
     user: "makerouteeasy",
     pwd: "makerouteeasy",
     roles: [ { role: "readWrite" ,db: "makerouteeasy"} ],
     passwordDigestor : "server" 
    }
    )
```

## Quickstart

You can use this project to create a clients and restaurants, both with a lat an long, and create orders with those clients and restaurants.
You can get a routes for delivery per restaurants, limited to 3 delivery for route.

Bellow a collection of PostMan with 

'https://www.getpostman.com/collections/41c4b3207a0ca10bce63'

Clients Services ( POST, GET, PUT ) and a bonus POST(if you want create a list of clients)
Restaurants Services ( POST, GET, PUT ) and a bonus POST(if you want create a list of restaurants)
Orders Services ( POST, GET, PUT ) and a bonus POST(if you want create a list of orders)
Routes (GET)


## Tricks
If you need a generate a test mass just run the unit tests of class bellow:
```
RoutesServiceImplTest
```
and take the jsons on folder named by "massa", they will be gerated a one of each(clients,restaurants and orders)
