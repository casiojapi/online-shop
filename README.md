Building RESTFul API Services using spring boot, MySQL and Swagger Documentation with containerization using Docker

Steps for executing :

Clone/Download the repository.

Open the project in the IDE (Netbeans/Intellij Idea/Eclipse) and generate the executable .jar file for the application. The alternate method to generate the .jar file is through Maven.

Open the terminal and go to the root directory of the project, where docker-compose.yml is located and run the below command in -d (Detach Mode) and will build the MySQL and Spring Boot Rest API Containers.

`docker-compose up -d`

Run the below command to get the list of running containers :


http://localhost:9090/swagger-ui.html#/
DEMO

Deployed to Heroku Cloud:

https://polar-thicket-63660.herokuapp.com/swagger-ui.html
Troubleshooting

Any errors related to "connection link failure" is seen while starting/running containers then it might be due to the MySQL hostname use in the application database connection. Run the below command to get the hostname of the MySQL and replace it

docker inspect {CONTAINER-ID}
