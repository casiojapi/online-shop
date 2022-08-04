## Online Shop RESTFul API project for Vidflex

Using Java Spring Boot, MySQL, Swagger Documentation and containerization with docker-compose.

### Requirements:
+ Java version 18
+ Maven version 3.8.6
+ Docker

### Steps for executing :

+ Clone/Download the repository.


+ Open the terminal and generate the executable .jar file for the application using Maven.


    $ cd online-shop

    $ mvn clean install

+ Now build the Service and DB containers, running:


    $ docker-compose up -d --build

+ Now, your application should be running on port: 8080


## APIs demo


[optional] - You can read some basic documentation on the available endpoints on:

+ http://localhost:8080/swagger-ui.html#/

### Get the available products sending

+ GET REQUEST to: http://localhost:8080/product/

### Add some products to your cart

+ POST REQUEST to: http://localhost:8080/api/cart/products/{product-id}

[example request]


    POST to: http://localhost:8080/api/cart/products/8

    Response:
    
        
    {
        "success": true,
        "message": "added product to cart",
        "timestamp": "2022-08-04T17:42:47.063285549"
    }

### Retrieve the list of products in the cart

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

### Create an order from the cart


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

// the response will return the id of the order. In this case, it is 6 - { "id": 6 }

### Retrieve the list of products in the order


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

// the response will return a list with all the products included in the order with the id given. In this case, we just have one product in our order, with id: 8.
