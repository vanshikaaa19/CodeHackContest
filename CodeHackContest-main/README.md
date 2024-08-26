# CodeHackContest Application

## Refer Problem Statement Here
- Problem Statement: [CodeHack Contest Problem Statement](./External-Resources/Problem-Statement.pdf)

## Description
While coding platforms usually host multiple contests while maintaining numerous leaderboards, this assignment requires you to design a service for managing the leaderboard of a specific contest. Assume the platform has only one contest with a single leaderboard. The platform also gives virtual awards to the users called Badges based on their score.

## Features
- User management: Create, update, and delete user profiles.
- Contest management: Create and manage coding contests.
- Leaderboard: Display the leaderboard with user scores.
- Badges: Assign badges to users based on their achievements.

## Technologies Used
- Java
- Spring Boot
- MongoDB
- Lombok
- RESTful API
- Swagger
- JUnit

## Setup Instructions
1. Clone the repository.
2. Configure MongoDB connection in the application properties.
3. Run the application using Maven or your preferred IDE.
4. Access the application through the provided endpoints.

## API Endpoints
- `/users`: User management endpoints.
- `/contests`: Contest management endpoints.
- `/leaderboard`: Leaderboard display endpoint.
- Swagger UI: [Swagger UI](http://localhost:8081/swagger-ui/index.html#/code-contest-controller)
- Postman Collection: [CodeHack Contest Postman Collection](./External-Resources/CodeHack%20Contest.postman_collection.json)



