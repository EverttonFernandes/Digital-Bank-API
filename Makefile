.PHONY: up down logs clean-build unit-test functional-test

MAVEN_DOCKER_IMAGE = maven:3.9.9-eclipse-temurin-17
MAVEN_CONTAINER_EXECUTE = docker run --rm -v "$$(pwd)":/workspace:ro -w /workspace $(MAVEN_DOCKER_IMAGE) bash -lc

up:
	mvn -q -DskipTests package
	docker compose up --build -d

down:
	docker compose down

logs:
	docker compose logs -f

clean-build:
	rm -rf build target

unit-test: clean-build
	$(MAVEN_CONTAINER_EXECUTE) 'mvn test'

functional-test: clean-build
	@if [ -d "__functional_tests__" ]; then \
		cd __functional_tests__ && npm test; \
	else \
		echo "A suite funcional em Jest ainda nao existe."; \
		exit 1; \
	fi
