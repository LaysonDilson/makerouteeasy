# makerouteeasy
Simple app to try solve the Vehicle Routing Problem


## Table of content
- [Problem](#Problem)
- [Installation](#Installation)
- [Quickstart](#quickstart)

## Problem

You have been hired to create a completely new micro-service to route orders minimizing overall distance. This problem is well known as Vehicle Routing Problem, please find a reference for it at (https://en.wikipedia.org/wiki/Vehicle_routing_problem).

Your micro-service must provide us the following endpoints with these minimal mandatory attributes:

Restaurants: create, update and get by id {"id": 1, "lat" : "0.0", "lon" : "0.0"}
Clients: create, update and get by id {"id": 1, "lat" : "0.0", "lon" : "0.0"}
Orders: Create, get by id and query by restaurant with optional filtering by delivery time. The pickup time is the moment when the dish is ready and the delivery time is the due time to deliver the dish.
{
    "id": 1,
    "restaurantId": 1,
    "clientId": 1,
    "pickup": "2018-06-05T13:37:00Z",
    "delivery": "2018-06-05T13:54:00Z"
}
* This example ilustrates the problem. Please, use the right types for the attributes.

Your micro-service also must provide an endpoint which returns the solution proposed following the example below.

{
	"routes": [
		{
			"orders": [1, 2, 5]
		}
		{
			"orders": [3]
		}
	]
}
* Same consideration. The ids are the orders that are picked and delivered in one route by a driver.

You must consider that:
You have limitless drivers. -> but it is better to minimize the number of drivers.
A worker can do at most 3 deliveries of the same restaurant at the same time (i.e. in the same route)
A worker can only do deliveries of a single restaurant at a time (i.e. in the same route)
You should try to avoid delivering an order after the delivery time
You CAN'T pick up an order before the pickup time
The driver is on the restaurant at the pickup time
Consider that the driver goes 0.1 units in line each 5 minute.
Each order must be optimized only once
You should elaborate the algorithm to solve the Vehicle Routing Problem (do not use libraries for that)
Hints: your system is growing and the constraints can change, furthermore, new constraints can be added. Prepare your system to be easy to change/configure and easy to add new constraints.

Non-functional requeriments
Now consider that your micro-services are a success inside IFood, it must be prepared to be fault tolerant, responsive and resilient.

Use whatever tools and frameworks you feel comfortable with, and briefly elaborate on your solution, architecture details, choice of patterns and frameworks. The only restriction is to use Java.

Also, make it easy to deploy/run your service(s) locally (consider using some container/vm solution for this). We expect that you solve this test in about 8 hours. Once done, share your code with us.



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
*in Orders get is possible to use a query params (restaurantId and orderByDeliveryTime)
Routes (GET)


## Tricks
If you need a generate a test mass just run the unit tests of class bellow:
```
RoutesServiceImplTest
```
and take the jsons on folder named by "massa", they will be gerated a one of each(clients,restaurants and orders)
