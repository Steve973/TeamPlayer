# TeamPlayer
This is an example Spring Boot application that utilizes Spring Rest and Spring Data with MongoDB.
I recently modernized this app to Spring Boot 3, with SpringDoc.  It uses Webflux with reactive repositories
and functional reactive REST controllers.  To test the app, you do not even need to install MongoDB.
Just get a working installation of Docker or Podman up and running, and it will stand up a MongoDB
Testcontainer for you.

I obtained all team and player data from https://fantasydata.com through their REST API and reduced the data
to fit into the scope of this application.

## Build
Ensure that you have a working Docker/Podman installation.  If you are using Podman, set up two environment
variables, e.g., in ~/.bash_profile:

    export DOCKER_HOST=unix:///run/user/1000/podman/podman.sock
    export TESTCONTAINERS_RYUK_DISABLED=true

If your user ID is not 1000, be sure to change the path for your user ID.

From the base project directory, invoke "mvn clean package"

## Prerequisite for running the application
There are two options for running this application:
 1. Ensure that MongoDB is running with defaults on the local machine (mongodb://localhost:27017)
 2. Rely on Docker/Podman and the app will spin up a MongoDB Testcontainer


## Run the application
1. If you have an instance of MongoDB running:
   From the base project directory, invoke `java -jar target/boot-rest-data-1.0.0-SNAPSHOT.jar`
2. If you want to let the app use Docker/Podman:
   From the base project directory, invoke `mvn spring-boot:test-run`

Navigate to http://localhost:8080/team-player/swagger-ui.html to use the generated user interface to interact with the REST
endpoints.

For either way that you run the application, you will need to run the `init` endpoint to populate
the database with team and player data.  You can find this on the swagger page from the link above.
After the data has been ingested, you can use any of the other endpoints to operate on the data
that you have loaded.
