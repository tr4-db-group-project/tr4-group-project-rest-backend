# RestapiApplication

## Table of Contents

- [Introduction](#introduction)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
- [Usage](#usage)
- [Error Handling](#error-handling)
- [Testing](#testing)
- [Improvements](#improvements)

## Introduction

RestapiApplication is a Spring Boot application designed to receive booking data through a RESTful API and publish the data to a Google Cloud Pub/Sub topic for further processing by subscribers. This application ensures data integrity and seamless integration with Google Cloud services.

## Architecture

### Microservice Layout

RestapiApplication follows a microservice architecture pattern, where each service is designed to perform a specific business function and communicate with other services over well-defined APIs. The main components of this service are:

1. **Controller**: Handles HTTP POST requests to receive booking data.
2. **Model**: Defines the Booking data structure with validation annotations.
3. **Configuration**: Sets up necessary beans and configurations for ObjectMapper and CORS.

### Request Handling

The application receives booking data through a POST request at the `/api/booking` endpoint. The `BookingController` is responsible for this endpoint, which:

1. Validates the incoming booking data.
2. Converts the booking data to a JSON string.
3. Publishes the JSON string to a Google Cloud Pub/Sub topic using the `PubsubOutboundGateway`.

### Google Cloud Pub/Sub Integration

Google Cloud Pub/Sub is a fully-managed real-time messaging service that allows you to send and receive messages between independent applications. In this application:

- **Producer (RestapiApplication)**: Publishes booking messages to a Pub/Sub topic.
- **Consumer**: Any number of subscribers can receive messages from the topic and process them independently.

### Benefits of this Approach

- **Decoupling**: By using Pub/Sub, the producer (API service) and consumers (processing services) are decoupled. This means the producer does not need to know about the consumers, leading to a more flexible and scalable architecture.
- **Scalability**: Pub/Sub can handle high-throughput messages, allowing your application to scale seamlessly as the number of booking requests grows.
- **Reliability**: Pub/Sub provides at-least-once delivery, ensuring that messages are not lost even if there are temporary failures.
- **Asynchronous Processing**: Consumers can process messages at their own pace without affecting the producer's performance. This is useful for long-running tasks like sending confirmation emails or updating other systems.


## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- Google Cloud SDK (for Pub/Sub)
- Google Cloud Project with Pub/Sub enabled
- An environment with proper Google Cloud credentials set up

### Installation

1. **Clone the repository**:
    ```sh
    git clone https://github.com/yourusername/RestapiApplication.git
    cd RestapiApplication
    ```

2. **Set up Google Cloud Pub/Sub**:
    - Create a Pub/Sub topic.
    - Create a Pub/Sub subscription for the topic.
    - Set up Google Cloud credentials.

3. **Configure application properties**:
   Update the `application.properties` file with your Pub/Sub topic details:
   ```properties
   booking.event.topic=your-topic-name
   ```
   
### Building the Apllication

1. **Build the project:**
```shell
mvn clean install
```

2. **Run the application**
```shell
mvn springboot:run
```
The application will start at `http://localhost:8080`.
To check if your application is up, go to `http://localhost:8080/actuator/heath`

### Usage
To create a new booking, send a POST request to the `/api/booking` endpoint with a JSON payload:
```json
{
  "eventId": "d1b9c2e0-3e1f-4e4a-9c8a-6e5f7a8b3f7a",
  "eventName": "Classical Symphony Gala",
  "numberOfTickets": 5,
  "email": "test@test.com"
}
```
### Improvements

1. **Retry Mechanism:** Implement a retry mechanism for Pub/Sub message publishing in case of transient failures. 
2. **Enhanced Logging:** Add more detailed logging for better traceability and debugging. 
3. **Security:** Implement security best practices such as OAuth2 for securing the endpoints. 
4. **Configuration Management:** Externalize configurations using Spring Cloud Config or a similar tool.
