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
	$(GRADLE_CONTAINER_EXECUTE) 'cp -R /workspace /tmp/project && cd /tmp/project && gradle --no-daemon --project-cache-dir /tmp/gradle-project-cache test'

functional-test: clean-build
	@if $(GRADLE_CONTAINER_EXECUTE) 'cp -R /workspace /tmp/project && cd /tmp/project && gradle --no-daemon --project-cache-dir /tmp/gradle-project-cache tasks --all' | grep -q "^functionalTest"; then \
		$(GRADLE_CONTAINER_EXECUTE) 'cp -R /workspace /tmp/project && cd /tmp/project && gradle --no-daemon --project-cache-dir /tmp/gradle-project-cache functionalTest'; \
	else \
		echo "A task funcional ainda nao existe no Gradle."; \
		echo "Crie a suite funcional completa antes de executar este alvo."; \
		exit 1; \
	fi
