# Kalah Game
Please refer to (https://en.wikipedia.org/wiki/Kalah) for more details about Kalah Game.

# Requirement

Java 8 to execute application.

# Running Application

mvn install "please refer to Maven documentation for setting up mvn on your computer" http://maven.apache.org/install.html

mvn spring-boot:run Starts the application

# Api Methods
You can access swagger documentation via localhost:8080/swagger-ui.html

## Available Api Methods

POST localhost:8080/games/ to create a new game instance which will return resourceId

PUT localhost:8080/games/:resourceId to update the board any time with rest end point.

PUT localhost:8080/games/:resourceId?moveId=numeric value between 1 to 6 to move coins of active player.