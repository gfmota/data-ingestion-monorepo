# Makefile

SCRIPT_DIR := $(shell cd "$(dirname "$$0")" && pwd)
REQUEST_FILE := $(SCRIPT_DIR)/metadata-configuration-request-body.json

.PHONY: run clean build

build:
	@echo "Building gradle modules..."
	./gradlew clean build

run: build
	./run.sh

configure-metadata:
	@echo "Configuring metadata with data from ${REQUEST_FILE}."
	@STATUS_CODE=$$(curl -s -o /dev/null -w "%{http_code}" --location 'http://localhost:8082/metadata' \
		--header 'Content-Type: application/json' \
		--data "@$(REQUEST_FILE)"); \
	if [ "$${STATUS_CODE}" -ge 200 ] && [ "$${STATUS_CODE}" -lt 300 ]; then \
		echo "Metadata configuration success"; \
	else \
		echo "Failure: Metadata service response status Code $${STATUS_CODE}"; \
	fi
