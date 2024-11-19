# Makefile

SCRIPT_DIR := $(shell cd "$(dirname "$$0")" && pwd)
METADATA_REQUEST_FILE := $(SCRIPT_DIR)/metadata-configuration-request-body.json
INGEST_REQUEST_FILE := $(SCRIPT_DIR)/raw-data-to-be-ingested.json

.PHONY: run clean build

build:
	@echo "Building gradle modules..."
	./gradlew clean build

run: build
	./run.sh

configure-metadata:
	@echo "Configuring metadata with data from ${METADATA_REQUEST_FILE}."
	@STATUS_CODE=$$(curl -s -o /dev/null -w "%{http_code}" --location 'http://localhost:8082/metadata' \
		--header 'Content-Type: application/json' \
		--data "@$(METADATA_REQUEST_FILE)"); \
	if [ "$${STATUS_CODE}" -ge 200 ] && [ "$${STATUS_CODE}" -lt 300 ]; then \
		echo "Metadata configuration success"; \
	else \
		echo "Failure: Metadata service response status code $${STATUS_CODE}"; \
	fi

ingest-data:
	@echo "Sending data from ${INGEST_REQUEST_FILE} to be ingested."
	@STATUS_CODE=$$(curl --location 'http://localhost:8081/ingest/austrian-geosphere' \
		--header 'Content-Type: application/json' \
		--data "@$(INGEST_REQUEST_FILE)" -s -o /dev/null -w "%{http_code}"); \
	if [ "$${STATUS_CODE}" -ge 200 ] && [ "$${STATUS_CODE}" -lt 300 ]; then \
		echo "Data sent to be ingested"; \
	else \
		echo "Failure: Ingest service response status code $${STATUS_CODE}"; \
	fi

