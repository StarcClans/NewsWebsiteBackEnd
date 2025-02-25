# Article Management System (AMS) Backend

This repository contains the backend code for the Article Management System (AMS), a web application designed for managing and displaying news articles. The backend is built using Spring Boot and provides RESTful APIs for various functionalities such as fetching articles from external sources, storing them in a database, managing full articles (detailed content), and handling user authentication and authorization.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [Architecture](#architecture)
- [API Endpoints](#api-endpoints)
- [Technologies Used](#technologies-used)
- [Database Configuration](#database-configuration)
- [Security](#security)
- [Environment Variables](#environment-variables)
- [Testing](#testing)
- [Dockerization](#dockerization)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

These instructions will guide you through setting up and running the AMS backend on your local machine.

### Prerequisites

Before you begin, ensure you have the following installed:

*   **Java Development Kit (JDK):** Version 23 or later.
*   **Maven:** Version 3.x or later.
*   **MariaDB:**  A MariaDB database server.
*   **An IDE:** (e.g., IntelliJ IDEA, Eclipse)

### Installation

1.  **Clone the repository:**

    ```bash
    git clone <repository_url>
    cd article-management-system/article-management/ams-backend
    ```

2.  **Build the project using Maven:**

    ```bash
    ./mvnw clean install
    ```

    or

    ```bash
    mvn clean install
    ```

### Configuration

1.  **Database Configuration:**

    *   Modify the `src/main/resources/application.properties` file to configure the database connection.  Make sure the database exists and the user specified has sufficient privileges.
    ```properties
    spring.datasource.url=jdbc:mariadb://localhost:3306/YOUR_DATA_BASE
    spring.datasource.username=ADMIN_NAME #If needed - not recomended use docker
    spring.datasource.password=ADMIN_PASSWORD #Also not Recommended use docker
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
    spring.jpa.hibernate.ddl-auto=update
    ```
    *   Replace `YOUR_DATA_BASE`, `ADMIN_NAME`, and `ADMIN_PASSWORD` with your actual database name, username, and password.

2.  **API Key Configuration:**

    *   You'll need a New York Times API key to fetch articles.  Obtain one from the [NYT Developer Portal](https://developer.nytimes.com/).

    *   Update `application.properties` with your API key:
    ```properties
    nytimes.api.key=YOUR_NYT_API_KEY
    ```

3.  **JWT Secret and Expiration:**

    *   Configure the JWT secret and expiration time in `application.properties`.  **Important:** For production environments, use a strong, randomly generated secret.

    ```properties
    app.jwt-secret=JWT_KEY_SHA256
    app.jwt-expiration-milliseconds=64800000
    ```

### Running the Application

1.  **Run the Spring Boot application:**

    *   Using Maven:

        ```bash
        ./mvnw spring-boot:run
        ```

        or

        ```bash
        mvn spring-boot:run
        ```
    *   From your IDE, run the `AmsBackendApplication` class.
    *   After building the JAR, run the application with:

        ```bash
        java -jar target/ams-backend-0.0.1-WebProject.jar
        ```
2.  **Access the application:**

    The backend application will be running on `http://localhost:8080`.

## Architecture

The AMS backend follows a layered architecture:

*   **Controller Layer:** Handles incoming HTTP requests and routes them to the appropriate service.
*   **Service Layer:** Contains the business logic of the application.  Interacts with the data access layer.
*   **Repository Layer (Data Access Layer):**  Provides an abstraction over the database and handles data access operations.
*   **Entity Layer:** Represents the data model (database tables).
*   **DTO (Data Transfer Object) Layer:** Used to transfer data between layers, often between the service and controller layers, avoiding exposing internal entity structures.
*   **Security Layer:** Handles authentication and authorization.

## API Endpoints

The backend exposes the following RESTful API endpoints:

*   **Authentication:**
    *   `POST /api/auth/register`: Registers a new user.
    *   `POST /api/auth/login`: Logs in an existing user and returns a JWT.
    *   `GET /api/auth/csrf`: Returns CSRF token.

*   **Articles (Publicly accessible GET):**
    *   `GET /api/articles`: Retrieves a list of articles.

*   **Full Articles (Admin Role Required):**
    *   `GET /api/fullarticles`: Retrieves a list of full articles.
    *   `GET /api/fullarticles/{id}`: Retrieves a full article by ID.
    *   `POST /api/fullarticles`: Creates a new full article.
    *   `PUT /api/fullarticles/{id}`: Updates an existing full article.
    *   `DELETE /api/fullarticles/{id}`: Deletes a full article.
    *   `POST /api/fullarticles/{id}/uploadImage`: Uploads an image for a full article.

## Technologies Used

*   **Spring Boot:**  Framework for building Java-based web applications.
*   **Spring Data JPA:** Simplifies database access using JPA repositories.
*   **Spring Security:**  Provides authentication and authorization mechanisms.
*   **JWT (JSON Web Tokens):** Used for secure authentication and authorization.
*   **MariaDB:**  Relational database for storing article data.
*   **Lombok:** Reduces boilerplate code (getters, setters, constructors).
*   **Jackson:**  For JSON processing.
*   **Maven Wrapper:** For consistent build environment.

## Database Configuration

The application uses MariaDB as its database.  The database connection details are configured in the `application.properties` file.  Ensure the database server is running and accessible from your application.

## Security

The backend implements security measures to protect against unauthorized access:

*   **JWT (JSON Web Token) Authentication:**  Users are authenticated using JWTs.  A JWT is issued upon successful login and must be included in the `Authorization` header of subsequent requests.

*   **Role-Based Access Control (RBAC):**  Access to certain API endpoints is restricted based on user roles. The `FullArticleController` requires the `ADMIN` role.

*   **CSRF Protection:** Enabled using Spring Security.

## Environment Variables

The following environment variables can be used to configure the application:

*   `SPRING_DATASOURCE_URL`:  The JDBC URL for the MariaDB database.
*   `SPRING_DATASOURCE_USERNAME`:  The database username.
*   `SPRING_DATASOURCE_PASSWORD`:  The database password.
*   `NYTIMES_API_KEY`:  The New York Times API key.
*   `APP_JWT_SECRET`:  The secret key used to sign JWTs.
*   `APP_JWT_EXPIRATION_MILLISECONDS`:  The expiration time (in milliseconds) for JWTs.
*   `TZ`: Timezone for scheduler

These environment variables can be set directly in your operating system or defined in a Docker Compose file.

## Testing

The project includes integration tests to verify the functionality of the API endpoints.

*   **Run tests using Maven:**

    ```bash
    ./mvnw test
    ```

    or

    ```bash
    mvn test
    ```

    The test results will be available in the `target/surefire-reports` directory.  The integration tests in `FullArticleControllerIntegrationTest.java` require a running database instance to be available.

## Dockerization

The project includes a `Dockerfile` for easy containerization.

1.  **Build the Docker image:**

    ```bash
    docker build -t ams-backend .
    ```

2.  **Run the Docker container:**

    ```bash
    docker run -p 8080:8080 -e SPRING_DATASOURCE_URL="jdbc:mariadb://<db_host>:3306/<db_name>" -e SPRING_DATASOURCE_USERNAME=<db_user> -e SPRING_DATASOURCE_PASSWORD=<db_password> -e NYTIMES_API_KEY=<nyt_api_key> ams-backend
    ```

    *   Replace `<db_host>`, `<db_name>`, `<db_user>`, `<db_password>`, and `<nyt_api_key>` with your actual database host, database name, database username, database password, and NYT API key.  You may also need to link this container to your database container, depending on your setup.

You can also use Docker Compose to define and manage the application and its dependencies.

## Docker-Compose

1. Create docker-compose.yml in the parent directory of your project.
2. Edit with a text editor vim/nano/emacs/kate etc...
3. Type in it:
   ```bash
    version: "3.8"
    services:
      db:
        image: mariadb:latest
        restart: always
        environment:
          MYSQL_ROOT_PASSWORD: ROOT_PASSWORD
          MYSQL_DATABASE: YOUR_DATA_BASE
          MYSQL_USER: MYSQL_NAME
          MYSQL_PASSWORD: MYSQL_USER_PASSWORD
        ports:
          - "3306:3306"
        volumes:
          - db_data:/var/lib/mysql
    
      app:
        build: ./project-folder
        restart: always
        ports:
          - "8080:8080"
        depends_on:
          - db
        environment:
          SPRING_DATASOURCE_URL: jdbc:mariadb://db:3306/YOUR_DATA_BASE
          SPRING_DATASOURCE_USERNAME: ADMIN_NAME
          SPRING_DATASOURCE_PASSWORD: ADMIN_PASSWORD
          NYTIMES_API_KEY: RqN6tCoSo8bRY8VKX1o8dtF9BAqaGwSJ
          APP_JWT_SECRET: JWT_KEY_SHA256
          APP_JWT_EXPIRATION_MILLISECONDS: 64800000
          TZ: TimeZone
    
    volumes:
      db_data:
    ```
## Contributing

Contributions to the AMS backend are welcome!  Please follow these guidelines:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Implement your changes and write tests.
4.  Submit a pull request.

## License

This project is licensed under the [Apache License 2.0](LICENSE).
