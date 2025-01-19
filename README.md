# Bibliotheca App

## Overview

Bibliotheca is a Java-based application that allows users to search for books and authors from the Gutendex API and store them in a PostgreSQL database. The app supports searching for books by title, language, and download rank, as well as searching for authors based on birth/death dates and name fragments. The app also features many-to-many relationships between books and authors, with support for storing and retrieving language data in both English and Spanish.

## Features

- Search for books by title
- Top 10 most downloaded books
- Search for books by language (English or Spanish)
- View all stored books and authors
- Search authors by name fragment
- Find authors who were alive in a specific year
- Store and retrieve authors and books in a PostgreSQL database
- Display author and book details

## Technologies Used

- Java
- Spring Boot
- JPA (Java Persistence API)
- Hibernate
- PostgreSQL
- Gutendex API
- SLF4J for logging

## Setup

To run the app, follow these steps:

1. Clone the repository:
   ```bash
   git clone <repository-url>
   
2. Set up the database:

Create a PostgreSQL database called "biblioteca" and configure your database credentials.

3. Configure the `application.properties` file:

Set the database credentials and host:
  ```properties
  spring.datasource.url=jdbc:postgresql://<DB_HOST>:5432/biblioteca
  spring.datasource.username=<DB_USER>
  spring.datasource.password=<DB_PASSWORD>
```
4. Run the application
   ```bash
   mvn spring-boot:run

## Endpoints

This app exposes various endpoints for book and author operations.

### 1. Search for a book by title
- Input: Book title (string)
- Output: Book details from the Gutendex API or the database.

### 2. Top 10 most downloaded books
- Output: A list of the top 10 most downloaded books.

### 3. Search for books by language
- Input: Language (English or Spanish)
- Output: List of books available in the specified language.

### 4. View all stored books
- Output: List of all books stored in the database.

### 5. Search authors by name fragment
- Input: Author's name fragment
- Output: List of authors matching the name fragment.

### 6. Search for authors who were alive in a specific year
- Input: Year (integer)
- Output: List of authors who were alive in the specified year.

## Database Model

### Books
- Each book has a title, summary, total downloads, and a list of associated languages (English or Spanish).
- Books are associated with multiple authors through a many-to-many relationship.

### Authors
- Each author has a name, birth date, death date, and a list of associated books.

### Languages
- Books can be stored with one or more languages (English and Spanish).

## Logging Configuration

To disable detailed SQL and Hibernate logs, modify the `application.properties` file and comment out the following lines:
```properties
#logging.level.org.hibernate.SQL=DEBUG
#logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
