# Transaction Money

![Java](https://img.shields.io/badge/Java-21-blue) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-green) ![Kafka](https://img.shields.io/badge/Kafka-3.0.0-orange) ![H2](https://img.shields.io/badge/H2%20Database-2.2.224-lightgrey)

A Spring Boot application designed to simulate financial transactions between wallets, integrating with external APIs for transaction authorization and Apache Kafka for asynchronous notification processing.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Kafka Integration](#kafka-integration)
- [Database](#database)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Overview
The **Transaction Money** project is a microservices-oriented application that facilitates secure and efficient money transfers between user wallets. It integrates with an external API to authorize transactions and leverages Apache Kafka for sending notifications asynchronously. The application uses Spring Boot for its robust framework and H2 as an in-memory database for development and testing.

## Features
- **Wallet-to-Wallet Transactions**: Perform secure money transfers between user wallets.
- **External API Authorization**: Validates transactions using an external service.
- **Asynchronous Notifications**: Sends transaction notifications via Kafka topics.
- **In-Memory Database**: Uses H2 database for quick setup and testing.
- **RESTful API**: Exposes endpoints for transaction management and status checking.
- **Developer-Friendly**: Includes Spring Boot DevTools for rapid development.

## Technologies
- **Java 21**: Modern Java version for enhanced performance and features.
- **Spring Boot 3.5.3**: Framework for building robust, production-ready applications.
- **Spring Data JDBC**: Simplified data access with JDBC.
- **Apache Kafka**: Messaging system for asynchronous notification handling.
- **H2 Database**: Lightweight, in-memory database for development and testing.
- **Maven**: Dependency management and build automation.

## Architecture
The application follows a layered architecture:
- **Controller Layer**: Handles HTTP requests and responses.
- **Service Layer**: Contains business logic, including transaction processing and API calls.
- **Repository Layer**: Manages data persistence using Spring Data JDBC.
- **Kafka Integration**: Consumes and produces messages for transaction notifications.
- **External API Client**: Communicates with an external service for transaction authorization.

The flow of a transaction is as follows:
1. A user initiates a transaction via a REST API call.
2. The service validates the transaction and checks with an external API for authorization.
3. If authorized, the transaction is recorded in the H2 database.
4. A notification is sent to a Kafka topic for asynchronous processing.

## Setup
### Prerequisites
- **Java 21** or higher
- **Maven 3.8+**
- **Kafka** (with ZooKeeper) running locally or in a container
- **Docker** (optional, for running Kafka)
- An external API for transaction authorization (mock or real endpoint)

### Installation
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/lucasolasz/transaction-money.git
   cd transaction-money
   ```

2. **Configure Kafka**:
   Ensure Kafka is running. You can use Docker to set up Kafka and ZooKeeper:
   ```bash
   docker-compose -f docker-compose.yml up -d
   ```
   Update `application.properties` with your Kafka broker details:
   ```properties
   spring.kafka.bootstrap-servers=localhost:9092
   spring.kafka.consumer.group-id=transaction-group
   ```

3. **Configure H2 Database**:
   The application uses an in-memory H2 database by default. Update `application.properties` if needed:
   ```properties
   spring.datasource.url=jdbc:h2:mem:transactiondb
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=
   spring.h2.console.enabled=true
   ```
## Environment Configuration
To configure the external API key, create a `.env` file in the project root and add the following line:

```env
API_KEY=your_external_api_key
```

Replace `your_external_api_key` with the actual API key provided by the external authorization service. The key is obtained in https://www.random.org/

4. **External API**:
   Configure the external API endpoint in `application.properties`:
   ```properties
   external.api.url=https://api.example.com/authorize
   ```

5. **Build the Project**:
   ```bash
   mvn clean install
   ```

## Running the Application
Run the Spring Boot application:
```bash
mvn spring-boot:run
```
The application will start on `http://localhost:8080`. Access the H2 console at `http://localhost:8080/h2-console` for database inspection.

## API Endpoints
The application exposes the following REST endpoints:
- **POST /api/transactions**: Initiate a new transaction.
  ```json
   {
      "value":  100,
      "payer": 1,
      "payee": 2
   }
  ```

## Kafka Integration
The application uses Kafka to send transaction notifications:
- **Topic**: `transaction-notifications`
- **Producer**: Sends a message to the topic after a transaction is processed.
- **Consumer**: Listens to the topic and processes notifications (e.g., sending emails or logging).

To monitor Kafka messages, use a tool like **Offset Explorer** or **Kafka Tool**.

## Database
The H2 database schema includes:
- **wallets**: Stores wallet information (ID, balance).
- **transactions**: Stores transaction details (ID, from_wallet, to_wallet, amount, status).

The schema is automatically initialized on startup via `schema.sql` and populated with sample data from `data.sql`.

## Testing
Run unit and integration tests:
```bash
mvn test
```
The project includes:
- **Spring Boot Test**: For testing REST controllers and services.
- **Spring Kafka Test**: For testing Kafka producer and consumer logic.
