# Orchestrator

This is a Java Spring Boot application that uses metadata to transform raw data to match the OpenDataHub's pattern.

## Architecture

![Component architecture](collector-consumer-COMPONENT%20ORCHESTRATOR.drawio.png)

### Worker

Consumes raw data with metadata configuration from a queue and send it to be transformed on the core module.

## Core

Orchestrates Transformers, a set of classes dedicated to do only ONE transformation in the data at a time.

The Orchestrator is capable of choosing the right Transformer and the order by reading the metadata configuration to understand the data structure.

## Infrastructure

It uses RabbitMQ to asynchronous communication via queues.