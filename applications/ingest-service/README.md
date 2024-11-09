# Ingestion Service

This is a Java Spring Boot application that handles raw data in the data ingestion process.

## What is "raw data"?

Raw data is the data in the format that it was collected from the provider, before being parsed.

## What does the Ingestion Service do?

### Ingest raw data
Consumes data from a RabbitMQ Message Queue and stores it in a MongoDB database.

### Sends data to be parsed
Once the data have a metadata configuration, it sends the data to a RabbitMQ Message Queue 
with its metadata configuration.

This can happen when the data in ingested, the ingest service requests for the metadata 
at the Metadata Service and sends it if there a valid metadata configuration.

Or when there is a metadata update, the Ingestion Service consumes the metadata update
message queue and queries for the raw data affected by this update and send them to be processed.