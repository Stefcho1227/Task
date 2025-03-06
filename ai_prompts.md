# AI Assistance Prompts Documentation

## Initial API Design 
For this project, I leveraged the proven layered architecture from a previous project as a foundation. This allowed me to quickly establish a scalable and maintainable structure by reusing key components such as rest controllers, services, repositories, and exception handling. By adapting the existing architecture to the new requirements—like the task prioritization logic, custom sorting, and enhanced error handling—I was able to ensure consistency across projects while reducing development time and risk. This approach also facilitated unit testing and future maintenance, as the separation of concerns remained clear.

## Priority calculation
**Prompt:**  
"Help me build calculate task priority based on dueDate, isCritical flag, and completion status."

**Response Summary:**  
Received code snippets and design ideas for priority calculation logic.


## Sorting and Filtering Customization
**Prompt:**  
"How can I sort my tasks by priority where HIGH maps to 3, MEDIUM to 2, and LOW to 1?"

**Response Summary:**  
Provided an approach using a CASE expression in the Criteria API to sort tasks numerically without altering the Task class.

## Global Exception Handling and Custom Exceptions
**Prompt:**  
"Can you help me create a GlobalExceptionHandler for my Spring Boot application that handles validation errors, missing fields, and not found errors? Also, how can I use a custom ResourceNotFoundException instead of ResponseStatusException?"

**Response Summary:**  
Received complete examples for a GlobalExceptionHandler with custom exception handling, including a ResourceNotFoundException example.

## Unit Testing the Service Layer
**Prompt:**  
"Show me an example of unit tests for a TaskService in Spring Boot that covers CRUD operations, priority calculation logic, and sorting/filtering functionality."

**Response Summary:**  
Detailed example unit test class (`TaskServiceImplTest`) was provided covering each method and scenario using JUnit and Mockito.

