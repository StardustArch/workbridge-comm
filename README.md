# Workbridge Chat Service

## Overview

The **Workbridge Chat Service** is a standalone microservice responsible for the chat functionality within the Workbridge platform. It enables real-time messaging using Kafka for message streaming and WebSocket (STOMP) for live updates.

---

## Technologies

- Java 23
- Spring Boot
- Spring Kafka
- PostgreSQL
- Apache Kafka + Zookeeper
- WebSocket with STOMP protocol
- Docker & Docker Compose
- Maven

---

## Running Locally

### Prerequisites

- Docker and Docker Compose installed
- Java 23 (for running without Docker)
- Maven (for building locally)

### Run with Docker Compose

```bash
docker-compose up --build
```
The service will be available at `http://localhost:8081`

### Run without Docker
```bash
mvn clean package
java -jar target/workbridge-chat-service.jar
```

| Method | Endpoint        | Description                                                |
| ------ | --------------- | ---------------------------------------------------------- |
| POST   | `/api/messages` | Sends a chat message (via Kafka)                           |
| GET    | `/api/messages` | Retrieves messages between users (senderId and receiverId) |

### WebSocket / Real-Time Messaging
The service supports real-time communication using WebSocket with STOMP.

WebSocket URL: `ws://localhost:8081/ws`

Subscribe to topic: `/topic/messages/{senderId}-{receiverId}`

Example JavaScript to connect and listen to messages:

```
const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, () => {
  const topic = `/topic/messages/user1-user2`;
  stompClient.subscribe(topic, message => {
    console.log('New message received:', JSON.parse(message.body));
  });
});
```
