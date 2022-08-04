## Online Shop RESTFul API project for Vidflex

Using Java Spring Boot, MySQL, Swagger Documentation and containerization with docker-compose.

### Requirements:
+ Java version 18
+ Maven version 3.8.6
+ Docker
+ MySql 8

### Steps for executing :

+ Clone/Download the repository.


+ Open MySql (port:3306) 

 -- _if you have to use another port, change "spring.datasource.url=" on application properties to point to the desired port_.


(this is just for making sure you can compile the project, then we will use docker to build the proper MySql-db container and the properties for the db url will update automatically)

+ Open the terminal and generate the executable .jar file for the application using Maven.


    $ cd online-shop

    $ mvn clean install

+ Now build the Service and DB containers, running:


    $ docker-compose up -d --build

+ Now, your application should be running on port: 8080. Docker will automatically build your service and MySql DB containers. Initializing the db with a fixture (which is on ./migrations/init.sql) with some sample data in order to test the APIs.


## APIs demo


[optional] - You can see some basic documentation on the available endpoints on:

+ http://localhost:8080/swagger-ui.html#/

### 1 - Get the available products sending

+ GET REQUEST to: http://localhost:8080/product/

### 2 - Add some products to your cart

+ POST REQUEST to: http://localhost:8080/api/cart/products/{product-id}

[example request]


    POST to: http://localhost:8080/api/cart/products/8

    Response:
    
        
    {
        "success": true,
        "message": "added product to cart",
        "timestamp": "2022-08-04T17:42:47.063285549"
    }

### 3 - Retrieve the list of products in the cart

+ GET REQUEST to: http://localhost:8080/api/cart/


[example request]


    GET REQUEST to: http://localhost:8080/api/cart

    Response:
    
        
    {
        "cartItems": [
            {
                "id": 25,
                "quantity": 1,
                "product": {
                    "id": 8,
                    "label": "chevrolet",
                    "physical": true,
                    "downloadUrl": "updated.com",
                    "weight": 3600.0,
                    "price": 110000,
                    "category": {
                        "id": 5,
                        "label": "cars"
                    }
                }
            }
        ],
        "totalCost": 110000.0
    }

### 4 - Create an order from the cart


+ POST REQUEST to: http://localhost:8080/api/order/

[example request]


    POST to: http://localhost:8080/api/order/

    Response:
    
        
    {
        "id": 6,
        "creationDate": "2022-08-04",
        "payed": false,
        "price": 110000.0,
        "orderItemsIds": [
            8
        ]
    }

_the response will return the id of the order. In this case, it is 6 - { "id": 6 }_

### 5 - Retrieve the list of products in your order


+ GET REQUEST to: http://localhost:8080/api/order/{order-id}

[example request]


    GET REQUEST to: http://localhost:8080/api/order/6

    Response:

    {
        "id": 8,
        "label": "chevrolet",
        "physical": true,
        "downloadUrl": "updated.com",
        "weight": 3600.0,
        "price": 110000,
        "categoryId": 5
    }

_the response will return a list with all the products included in the order with the id given. In this case, we just have one product in our order, with id: 8._
