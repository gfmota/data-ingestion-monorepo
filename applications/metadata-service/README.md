# Metadata Service

This is a Java Spring Boot application that handles metadata in the data ingestion process.

## What is metadata?

It is a json configuration file that describes the structure of the raw data.
This is used furthermore to identify valuable fields in the raw data 
and transform it to match the OpenDataHub's pattern.

Metadata configuration can be sent along side the data but can also be configured for a data source.

This service handles this Metadata configuration and its lifecycle.

## Architecture

![Component architecture](collector-consumer-COMPONENT%20METADATA.drawio.png)

It contains a HTTP API to receive metadata configuration and retrieve metadata for a data source.

Once a new metadata is configured, it sends a message via message queue 
to notify the data affected by this configuration.

## Infrastructure

It uses RabbitMQ to asynchronous communication via queues and MongoDB for persistence.