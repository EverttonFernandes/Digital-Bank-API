.PHONY: up down logs clean-build unit-test functional-test

GRADLE_DOCKER_IMAGE = gradle:8.14.3-jdk17
GRADLE_CONTAINER_EXECUTE = docker run --rm -e GRADLE_USER_HOME=/tmp/gradle-home -v "$$(pwd)":/workspace:ro $(GRADLE_DOCKER_IMAGE) bash -lc

up:
	docker compose up --build -d

down:
	docker compose down

logs:
	docker compose logs -f

clean-build:
	rm -rf build

unit-test: clean-build
	$(GRADLE_CONTAINER_EXECUTE) 'gradle --no-daemon -g /tmp/gradle-home -p /workspace -Dorg.gradle.project.buildDir=/tmp/gradle-build --project-cache-dir /tmp/gradle-project-cache test'

functional-test: clean-build
	@if [ -d "__functional_tests__" ]; then \
		cd __functional_tests__ && npm test; \
	else \
		echo "A suite funcional em Jest ainda nao existe."; \
		exit 1; \
	fi
