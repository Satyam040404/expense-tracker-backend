# Expense Tracker Backend

## Overview

Expense Tracker Backend is a REST API built using Spring Boot that allows users to manage their expenses securely using JWT Authentication.

Users can register, log in, create expenses, update expenses, delete expenses, search expenses, filter by category, and view expense summaries.

## Features

* User Registration
* User Login
* JWT Authentication
* Create Expense
* View My Expenses
* Update Expense
* Delete Expense
* Category-wise Expense Filter
* Expense Search
* Expense Summary
* Global Exception Handling
* Request Validation
* Swagger API Documentation

## Tech Stack

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL
* JWT
* Maven
* Swagger/OpenAPI

## API Endpoints

### User APIs

POST /api/users/register

POST /api/users/login

### Expense APIs

POST /api/expenses

GET /api/expenses/me

PUT /api/expenses/{expenseId}

DELETE /api/expenses/{expenseId}

GET /api/expenses/summary

GET /api/expenses/category/{category}

GET /api/expenses/search?keyword=value

## Security

* JWT Authentication
* Expense Ownership Authorization
* Users can access only their own expenses

## Swagger

http://localhost:8080/swagger-ui/index.html

## Author

Satyam Upadhyay
