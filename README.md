# 📚 Bibliodesk

A comprehensive digital library platform designed for librarians to efficiently handle books, authors, categories, and publishers.

---

## 🚀 Project Description

**Bibliodesk** is a robust backend application built with **Spring Boot** and **MySQL** that streamlines library operations.  
This **RESTful API-based** system eliminates the need for manual record-keeping by providing librarians with a secure,
efficient platform to handle all library-related tasks digitally.
The application focuses on core library functionalities while maintaining security through 
**Spring Security-based authentication**, ensuring that only authorized librarians can access and modify library data.

## ✨ Features

### 🔧 Core Functionality

- 📖 **Book Operations**: Complete CRUD operations for book records including title, ISBN, publication details, and availability status  
- 👤 **Author Operations**: Add, update, view, and delete author information with biographical details  
- 📂 **Category Operations**: Organize books into categories for better classification and searchability  
- 🏢 **Publisher Operations**: Maintain publisher records with contact information and publication history  

### 🔐 Security & Authentication

- 🔐 **Spring Security Integration**: Secure authentication system for librarians  
- 🛡️ **Role-based Access Control**: Restricted access to ensure only authorized personnel can modify records  
- 🔑 **Session Management**: Secure session handling for authenticated users

## 🛠️ Technical Features

- 🌐 **RESTful API**: Well-structured API endpoints for seamless integration with frontend applications  
- 🔄 **Data Validation**: Comprehensive input validation and error handling to ensure data integrity  

## 🛠️ Tech Stack

| Technology       | Version   | Purpose                              |
|------------------|-----------|--------------------------------------|
| Java             | 17+       | Core programming language            |
| Spring Boot      | 3.x       | Application framework                |
| Spring Security  | 6.x       | Authentication and authorization     |
| Spring Data JPA  | 3.x       | Data persistence layer               |
| MySQL            | 8.0+      | Primary database                     |
| Maven            | 3.8+      | Dependency management                |
| Hibernate        | 6.x       | ORM framework                        |

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- ☕ **Java Development Kit (JDK)** 17 or higher  
- 📦 **Apache Maven** 3.8 or higher  
- 🐬 **MySQL Server** 8.0 or higher  
- 🧰 **Git** (for cloning the repository)  
- 🖥️ **IDE** (e.g., IntelliJ IDEA — *recommended*)  


## 🚀 Getting Started
Follow below steps to set up and run the Bibliodesk backend application locally:

---

### 1️⃣ Clone the Repository
```
git clone https://github.com/yourusername/bibliodesk.git
cd bibliodesk
```
### 2️⃣ Database Setup
Make sure MySQL is running, then execute the following commands in your MySQL console:
Create database
```
CREATE DATABASE bibliodesk;
```
### 3️⃣ Configure Application Properties
Open the file:
src/main/resources/application.properties
And update it with your database credentials:

##### Database Configuration
```
spring.datasource.url=jdbc:mysql://localhost:3306/bibliodesk
spring.datasource.username=bibliodesk_user
spring.datasource.password=your_password
```
#### JPA Configuration
````
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
````
#### Server Port (Optional)
```
server.port=8080
```
---

---

## 📝 License
This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

---

> Developed with ❤️ by **Piyush Kumar**





