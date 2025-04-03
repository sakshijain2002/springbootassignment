# 🌍 Spring Boot Country API

## 🚀 Overview
This is a **RESTful web application** built with **Spring Boot, MySQL, JWT Authentication, and Docker**.  
It fetches and stores **country data** from the [REST Countries API](https://restcountries.com/v3.1/all),  
providing **user authentication, CRUD operations, and pagination**.

---


## 🛠 Tech Stack
- **Spring Boot** – REST API Development
- **Spring Security & JWT** – Authentication & Authorization
- **Spring Data JPA** – Database interaction
- **MySQL** – Database Management
- **Docker & Docker Compose** – Containerization
- **Swagger (Springdoc-OpenAPI)** – API Documentation
- **Postman** – API Testing
- **IntelliJ Idea for Project development**

---
## 🏢 **Setup Instructions**

### 🖥 **Prerequisites**
- Install Jdk 17
- Install Docker
- Install MySQLServer 8.0
- Setup Postman for API Testing


## ⚡ Features

### ✅ **User Management & Authentication**
- **User Registration & Login** using JWT (**BCrypt encrypted passwords**).
- **Spring Security** for **secured API access**.
- Users can **view, update, and delete** their profiles.

### ✅ **Country Management**
- Fetch & store **countries** from an external API.
- External Api: to fetch and save data
- http://localhost:8080/external/countries/fetch
- **CRUD operations**:
    - **GET /country** – Retrieve all countries (**with pagination**).
    - **GET /country/{id}** – Retrieve a country by ID.
    - **POST /country/add** – Add a new country manually.
    - **PUT /country/update/{id}** – Update country details.
    - **DELETE /country/delete/{id}** – Delete a country.

### ✅ **Docker Integration**
- Runs using **Docker Compose**.
- Both **Spring Boot** and **MySQL** run in **separate containers**.

### ✅ **API Documentation**
- Integrated **Swagger UI** for interactive API testing.

---

## 📝 **API Documentation**

### 🔗 **Swagger UI**
After running the application, access **Swagger UI** at:  
📌 **(http://localhost:8080/swagger-ui/index.html)**

### 🛠 **Postman Collection**
📌 **[Download Postman Collection](#)** *(https://drive.google.com/file/d/1-Id0MrZsUoXG2DqevoptyvhgOCHmBLij/view?usp=drive_link)*

---



## 🛠 **Run Without Docker (Local Setup)**

### 📌 Clone the repository
```sh
git clone https://github.com/your-username/springboot-country-api.git
cd springboot-country-api
```

### 📌 Configure the Database (MySQL)

**Create a MySQL database:**
```sql
CREATE DATABASE assignment_db;
```

**Update `application.properties`:**

spring.datasource.url=jdbc:mysql://localhost:3306/assignment_db?serverTimezone=UTC
spring.datasource.username=<your-mysql-username>
spring.datasource.password=<your-mysql-password>
```

### 📌 Build and Run the Spring Boot Application
```sh
mvn clean install
mvn spring-boot:run
```

📌 **Open the app at:http://localhost:8080

---

##  **Run Using Docker**
1.🛠 Build the Spring Boot Application
**Before running with Docker, we need to build the JAR file**: mvn clean package 
This creates a .jar file inside the target/ directory.
2.**If you have MySQL running locally, stop it to avoid conflicts**:sudo systemctl stop mysql  # For Linux/macOS  
                                                                    net stop MySQL             # For Windows (Command Prompt)  
3.**Start the containers**: docker-compose up -d
   **rebuild the containers**:docker-compose up --build --force-recreate

4.**Check if the containers are running**: docker ps

5.**Access the Application**:
Spring Boot API: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui/index.html

6.**View Logs of a Running Container**:docker logs -f springboot-app
                                       docker logs -f mysql-db
7.**Stop all running containers**: docker stop $(docker ps -q)
8.**Remove a specific container**:docker rm <container_id>
9.**Restart a specific container**:docker restart springboot-app



## ✅ **Final Notes**
- **Always rebuild containers** after making major changes:
  
  docker-compose up --build --force-recreate
  
- If MySQL **connection issues occur**, restart the database container:
  
  docker restart mysql-db
 
- To check if the **API is working**, visit:  
  📌 http://localhost:8080/swagger-ui/index.html

---
## 🛠 Postman Collection

You can test the API using the **Postman Collection**.  
📌 **Download Collection:** https://drive.google.com/file/d/1-Id0MrZsUoXG2DqevoptyvhgOCHmBLij/view?usp=drive_link

### 📌 **How to Import in Postman**
1. Open **Postman**.
2. Click **Import** (top-left corner).
3. Select the downloaded `.json` file.
4. Click **Import** and start testing the API.


