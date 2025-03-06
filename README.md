# Task Prioritization API

This project is a RESTful API for managing tasks with built-in task prioritization logic. It is built with Spring Boot, Hibernate, and MariaDB.

## Features

- **CRUD Operations:**  
  Create, retrieve, update, and delete tasks.
  
- **Priority Calculation:**  
  Automatically calculates task priority based on:
  - Due date proximity (closer dates → higher priority)
  - A critical flag (urgent tasks receive the highest priority)
  - Completed tasks are always set to low priority.

- **Sorting & Filtering:**  
  - **Sorting:**  
    Retrieve tasks sorted by priority (with custom numeric sorting) or due date.
  - **Filtering:**  
    Filter tasks by completion status and priority level.

- **Error Handling:**  
  Consistent JSON error responses for invalid inputs, missing fields, or not found resources, using a Global Exception Handler.

- **Persistent Storage:**  
  Uses MariaDB as the database. Hibernate manages entity persistence.

## Setup Instructions

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/yourusername/task-prioritization-api.git
   cd task-prioritization-api
2. **Configure the Database:**

   Update the application.properties file with your database credentials. For example:
   ```bash
       database.url=jdbc:mariadb://localhost:3306/your_db
       database.username=your_username
       database.password=your_password
   ```
   Run the SQL scripts in the resources folder to create and populate the database.
## Testing the API
You can use Postman or any other REST client to test the endpoints. For example:

- GET /api/tasks – Retrieve all tasks.

- POST /api/tasks – Create a new task.

- PUT /api/tasks/{id} – Update an existing task.

- DELETE /api/tasks/{id} – Delete a task.

## Future Improvements
- **Authentication & Authorization:**

Implement user management and secure the endpoints.

- **Advanced Filtering:**

Expand filtering to include additional fields such as title and description.
- **User Roles & Collaboration:**

Enable multiple users to collaborate on tasks, including role-based permissions (e.g., admin, editor, viewer).
- **Swagger/OpenAPI Documentation:**

Generate interactive API documentation (using Swagger UI) so that front-end developers can easily understand and test the endpoints.

## External Libraries & Tools
- **Spring Boot**

Provides the framework for rapid API development, dependency injection.

- **Hibernate**

Used as the ORM tool for mapping your Java entities to the MariaDB database.

- **MariaDB JDBC Driver:**

Enables database connectivity with a MariaDB instance.

- **Jakarta Validation (Hibernate Validator):**

Enforces bean validation constraints (e.g., @Size, @FutureOrPresent) on DTOs and entities.

- **JUnit:**

Used for writing unit tests for the service layer.

- **Mockito:**

Provides mocking capabilities for unit testing, allowing you to simulate repository behavior.

- **Build Tools (Maven/Gradle):**

These are used to manage project dependencies, build the project, and run the application.
