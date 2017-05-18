# TeamPlayer
This is an example Spring Boot application that utilizes Spring Rest and Spring Data with MongoDB.

I obtained all team and player data from https://fantasydata.com through their REST API and reduced the data
to fit into the scope of this application.

## Build
From the base project directory, invoke "mvn clean package"

## Prerequisite for running the application
Ensure that MongoDB is running with defaults on the local machine

## Initialize MongoDB with data for the app
From the base project directory, invoke "java -jar target/boot-rest-data-1.0.0-SNAPSHOT.jar TeamPlayerDataInitializerApp"

## Run the application
From the base project directory, invoke "java -jar target/boot-rest-data-1.0.0-SNAPSHOT.jar"

Navigate to http://localhost:8080/swagger-ui.html to use the generated user interface to interact with the REST endpoints.
