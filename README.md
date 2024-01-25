# Booking Application - REST API Backend

This application is implemented as a REST API backend for a reservation system. It utilizes Spring Boot as the core framework for development.

## Students

| Student       | Index       |
| -------------- | -------------- |
| Nemanja Stjepanović | SV75-2021 |
| Vladimir Čornenki | SV53-2021 |
| Ognjen Gligorić | SV79-2021 |

## Technologies

- **Spring Boot:** Core framework for application development.
- **Spring Data JPA:** Data handling and database interaction.
- **Spring Security:** API protection with an authentication and authorization system.
- **RESTful API:** Implementation of API following REST principles.
- **Maven:** Dependency management and project build.

## Getting Started

1. **Clone the repository:**
    ```bash
    git clone https://github.com/BookingAppTIm11/ISS.git
    ```

2. **Configure the database:**
    - Create a Postgres database named `bookingdb`.
    - Set database information in the `application.properties` file.

3. **Run the application:**

    - Run the app with terminal:
    
    ```bash
    ./mvnw spring-boot:run
    ```
    - Run the app in your IDE

## Test data

### Admin
- **Email:** `admin@gmail.com`  **Password:** `123`
- **Email:** `nemanjaadmin@gmail.com`  **Password:** `123`
### Guest
- **Email:** `guest@gmail.com`  **Password:** `123`
- **Email:** `ognjen_guest@gmail.com`  **Password:** `123`
### Owner
- **Email:** `owner@gmail.com`  **Password:** `123`
- **Email:** `ognjen_owner@gmail.com`  **Password:** `123`

