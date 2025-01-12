
# To-Do List Application - Spring Boot
![image](https://github.com/user-attachments/assets/4306565d-8403-43ee-a226-f7ec3376150f)

This is a To-Do list application built with Spring Boot 6, which integrates Redis for rate limiting and PostgreSQL for storing data. The application uses Docker Compose to manage the PostgreSQL and Redis services.

## Features
- User authentication and authorization (Spring Security)
- Task management (CRUD operations)
- Redis for rate limiting task creation requests
- PostgreSQL for persistent data storage

## Technologies Used
- **Spring Boot 6**: Backend framework
- **Spring Data JPA**: For ORM with PostgreSQL
- **Redis**: For rate limiting API requests
- **PostgreSQL**: For storing user and task data
- **Docker Compose**: To run PostgreSQL and Redis as services
## Spring Security 6 Diagram:
![image](https://github.com/user-attachments/assets/985357c9-a5c3-42a0-9263-aa4cfa2a1f37)

## DataBase Diagram:
![image](https://github.com/user-attachments/assets/3be38c34-be34-42aa-ab90-46f106ffcb8f)

## Database Models

### Task
- `id`: Long - Unique identifier for each task.
- `title`: String - The title of the task (unique).
- `description`: String - A detailed description of the task.
- `user`: User - The user who created the task (many-to-one relationship with the User entity).

### User
- `id`: Long - Unique identifier for each user.
- `username`: String - Unique username of the user.
- `fullName`: String - Full name of the user.
- `email`: String - User's email address.
- `password`: String - User's password (stored securely).

## Endpoints
- **POST /api/auth/register**: Register a new user.
- **POST /api/auth/login**: Login and receive a JWT token.
- **GET /api/todos?page=1&limit=10**: Get a to-do list paginated it's start from index 1 
- **POST /api/todos**: Create a new task (rate-limited with Redis).
- **PUT /api/todos/{id}**: Update an existing task.
- **DELETE /api/todos/{id}**: Delete a task.

## Docker Services

- **Redis**: Used for rate limiting the `POST /api/tasks` requests to prevent spamming the task creation endpoint.
- **PostgreSQL**: Used for persistent data storage of tasks and users.

## Step 1: Clone the Repository

```bash
git clone https://github.com/7roubb/RESTful-API-for-User-Management-and-To-Do-List
cd RESTful-API-for-User-Management-and-To-Do-List
```
## Step 2: Run Docker Compose
Make sure Docker is running, then execute:

```bash
docker-compose up
```
## Step 4: Build the Spring Boot Application
You can build the project using Maven or Gradle, depending on your preference.

Using Maven:

```bash
mvn clean install
```
## Step 5: Run the Application
Run the Spring Boot application using the command:

Using Maven:

```bash
mvn spring-boot:run
```
