Building RESTFul API Services using spring boot, MySQL and Swagger Documentation with containerization using Docker

Steps for executing :

Clone/Download the repository.

Open the project in the IDE (Netbeans/Intellij Idea/Eclipse) and generate the executable .jar file for the application. The alternate method to generate the .jar file is through Maven.

Rename docker-compose-sample.yml file to docker-compose.yml.

Open docker-compose.yml file and add the MySQL (db) environment parameter values and Spring REST API (spring-rest-api) environment parameter values for database connection from the the application.

Open the terminal and go to the directory where docker-compose.yml is located and run the below command in -d (Detach Mode) and will build the MySQL and Spring Boot Rest API Containers.

docker-compose up -d
Run the below command to get the list of running containers :

docker ps
After executing above steps without any errors and docker containers are up and running, open the browser and navigate to below url:

http://localhost:9090/swagger-ui.html#/
DEMO

Deployed to Heroku Cloud:

https://polar-thicket-63660.herokuapp.com/swagger-ui.html
Troubleshooting

Any errors related to "connection link failure" is seen while starting/running containers then it might be due to the MySQL hostname use in the application database connection. Run the below command to get the hostname of the MySQL and replace it

docker inspect {CONTAINER-ID}
