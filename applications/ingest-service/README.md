# Ingestion Service

This is a Java Spring Boot application that handles raw data in the data ingestion process.

## What is "raw data"?

It is the data as it was collected, before any transformation to match the OpenDataHub's pattern

## Architecture

![Component architecture](collector-consumer-COMPONENT%20INGEST.drawio.png)

### API

HTTP API to receive raw data from active data sources.

This data is sent to the ingestion queue to be processed.

### Worker

#### Data Ingestion Consumer

Consumes raw data from a queue, persists it and sends it to be transformed if it has metadata configuration
(with it or from Metadata Service).

#### Metadata Update Consumer

Consumes metadata update messages and queries for raw data affected by this update to send them to be transformed.

## Infrastructure

It uses RabbitMQ to asynchronous communication via queues and MongoDB for persistence.