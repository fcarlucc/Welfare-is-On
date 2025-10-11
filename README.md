# WellfareIsOn - Well4You

WellfareIsOn is a challenge that aims to innovate the Wellfare website of Leonardo, and this is our creation: **Well4You**.

📸 Project images and mockups can be found in:
/Documentazione/immagini/*

## Project Overview

Well4You is a modernized and scalable web application designed to enhance the welfare services provided by Leonardo. The project utilizes a microservices architecture to ensure flexibility and scalability, with a backend developed in Spring Boot, a PostgreSQL database, and Docker for containerization. The frontend is built using TypeScript and Angular, and the application is designed to be secure and efficient.

## Prerequisites

Before you can run the application, ensure that you have the following installed:

- Docker and Docker Compose correctly installed
- A terminal to execute commands that will start the application
- Internet connection

## Project Structure

The project repository contains the following folders:

- **Well4You_jar**: This folder contains the web app project with a lightweight Docker structure where the Spring Boot services are already compiled into jar files.

- **Well4You**: This folder contains the web app project with the Docker structure containing all the Spring Boot service source codes, which are compiled during the creation of the Docker images.

## Configuration

To configure the application:

1. Enter the folder you want to use to start the web app (`Well4You_jar` or `Well4You`).
2. Modify the environmental variables in the `.env` file to insert your credentials and data:
   - Change database (PostgreSQL) credentials
   - Change Well4You utilities settings
   - Change admin credentials

## Starting the Application

To start the application:

1. Open a terminal and navigate to the root of the project repository.
2. Run the following command:
"make"
or
"make up"

This command will automatically build the container images, run them, create volumes, and set up the necessary network.

3. After the setup is complete, you can access the application via the client IP address (e.g., `https://localhost:4200`).

Enjoy the Well4You application!

---

**Note:** Ensure that you have configured all necessary environment variables correctly before running the application to avoid any runtime issues.

