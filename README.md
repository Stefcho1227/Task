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
