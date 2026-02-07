# Journal Application — Event-Driven Mood Analytics System

A backend-focused journal application built with Spring Boot and MongoDB that allows users to store personal journal entries and receive automated weekly mood insights using sentiment analysis.

The system leverages Redis caching for performance optimization and an event-driven Kafka pipeline for asynchronous sentiment processing and notification delivery.

---


## Tech Stack

Backend:
- Java
- Spring Boot
- MongoDB (NoSQL)
- Redis

Event Processing:
- Apache Kafka

Notifications:
- Email service integration

---

## Key Features

### Journal Management
Users can create profiles and securely store journal entries using MongoDB for flexible document-based storage.

### Redis Caching Layer
Integrated Redis caching to reduce redundant API calls and improve read performance for frequently accessed journal data.

### Event-Driven Sentiment Pipeline
Kafka-based asynchronous pipeline processes journal entries for sentiment analysis without blocking API request flows

### Weekly Mood Analytics
A scheduled workflow aggregates user journal entries weekly and determines overall emotional trends.

If negative sentiment is detected, the system automatically sends an email containing suggested remedies and well-being recommendations.

### Asynchronous Processing
Heavy computation tasks like sentiment analysis and notification delivery are decoupled from the main request path using Kafka consumers.

---

## Performance Design

- Asynchronous event-driven architecture
- Redis-backed caching layer
- Non-blocking sentiment processing pipeline
- MongoDB document storage for flexible schema evolution

---

## Future Improvements

- Real-time sentiment dashboard
- ML-based sentiment classification model
- User mood trend visualization
- Dockerized deployment
- Kubernetes-based scaling

---

## Project Goal

This project demonstrates event-driven backend system design using Kafka, caching strategies with Redis, and NoSQL data modeling with MongoDB, combined with scheduled analytics workflows.



---

## Author

Tanmay Akhil Devikar
- GitHub: https://github.com/TanmayDevikar
- LinkedIn: https://www.linkedin.com/in/td1101/


