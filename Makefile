.PHONY: up down logs clean-build unit-test functional-test

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
	mvn test
	@if [ -f "target/site/jacoco/jacoco.csv" ]; then \
		awk -F, 'BEGIN { \
			instructionMissed=0; instructionCovered=0; \
			branchMissed=0; branchCovered=0; \
			lineMissed=0; lineCovered=0; \
		} \
		NR > 1 { \
			instructionMissed += $$4; instructionCovered += $$5; \
			branchMissed += $$6; branchCovered += $$7; \
			lineMissed += $$8; lineCovered += $$9; \
		} \
		END { \
			instructionTotal = instructionMissed + instructionCovered; \
			branchTotal = branchMissed + branchCovered; \
			lineTotal = lineMissed + lineCovered; \
			instructionCoverage = instructionTotal ? (instructionCovered * 100 / instructionTotal) : 0; \
			branchCoverage = branchTotal ? (branchCovered * 100 / branchTotal) : 0; \
			lineCoverage = lineTotal ? (lineCovered * 100 / lineTotal) : 0; \
			printf "\nResumo de cobertura JaCoCo:\n"; \
			printf "  Instrucoes: %.2f%% (%d/%d)\n", instructionCoverage, instructionCovered, instructionTotal; \
			printf "  Branches: %.2f%% (%d/%d)\n", branchCoverage, branchCovered, branchTotal; \
			printf "  Linhas: %.2f%% (%d/%d)\n", lineCoverage, lineCovered, lineTotal; \
			printf "  Relatorio HTML: target/site/jacoco/index.html\n\n"; \
		}' target/site/jacoco/jacoco.csv; \
	else \
		echo "Relatorio JaCoCo nao encontrado em target/site/jacoco/jacoco.csv"; \
		exit 1; \
	fi

functional-test: clean-build
	@if [ -d "__functional_tests__" ]; then \
		cd __functional_tests__ && npm test; \
	else \
		echo "A suite funcional em Jest ainda nao existe."; \
		exit 1; \
	fi
