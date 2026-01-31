# Todo List Application

This is a simple Todo List application built using Java and the MVC design pattern. The application uses Swing for the graphical user interface, JSON for data storage, and JUnit for unit testing.

## Features
- Add, remove, and list tasks
- Save tasks to a JSON file
- Load tasks from a JSON file

## Requirements
- Java 17
- Maven

## Setup
1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd test_ia
   ```
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.todolist.Main"
   ```

## Testing
Run the unit tests using Maven:
```bash
mvn test
```