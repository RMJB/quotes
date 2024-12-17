# Quotes App

This project is a simple quote application with an Angular frontend and a Spring Boot backend.
It has four modes a basic quote browser which serves a random quote each time
A like/dislike mode where you can like quotes
A most liked list of the top most liked quotes
And lastly a trivia mode which lets you guess who said it.

#Installation
##Prerequisites

Make sure you have the following installed on your machine:

    Node.js and npm
    Angular CLI
    Java Development Kit (JDK 21)
    Maven
    Docker (Optional)

Download/clone this repo
##Option 1
Running the Backend (Spring Boot)
Navigate to the quoteservice directory.
Run the following commands

    cd quoteservice
    mvn clean install
    java -jar target/quoteservice-0.0.1-SNAPSHOT.jar

The backend will be accessible at http://localhost:8080.

Running the Frontend (Angular)
Navigate to the quote-generator directory.
Run the following commands

    cd quote-generator
    npm install
    ng serve

The frontend will be accessible at http://localhost:4200.

##Option 2
Package JAR
Run the following commands

    cd quoteservice
    mvn clean install

Run with docker compose
Using the provided docker-compose.yml
Run the following command

    docker compose up -d

Go to http://localhost:4200
Choose from different quote modes with the hamburger menu in the top

Potential improvements

    Add more documentation
    Implement frontend tests
    Implement more features
    Deploy in cloud
    
