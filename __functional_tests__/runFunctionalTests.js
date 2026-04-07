const { spawnSync } = require("node:child_process");
const { applicationBaseUrl } = require("./src/functionalTestHelper");

function runCommand(command, args) {
    const result = spawnSync(command, args, {
        cwd: __dirname,
        env: process.env,
        stdio: "inherit"
    });

    if (result.error) {
        throw result.error;
    }

    return result.status ?? 1;
}

let jestExitCode = 1;

async function waitForApplicationReadiness() {
    const readinessUrl = `${applicationBaseUrl}/actuator/health`;
    const maximumAttempts = 30;

    for (let attempt = 1; attempt <= maximumAttempts; attempt += 1) {
        try {
            const response = await fetch(readinessUrl);

            if (response.ok) {
                return;
            }
        } catch (error) {
            if (attempt === maximumAttempts) {
                throw error;
            }
        }

        await new Promise((resolve) => setTimeout(resolve, 1000));
    }

    throw new Error("A aplicacao nao ficou pronta para os testes funcionais.");
}

async function runFunctionalSuite() {
    const seedExitCode = runCommand("node", ["seed.js"]);

    if (seedExitCode !== 0) {
        process.exit(seedExitCode);
    }

    await waitForApplicationReadiness();

    try {
        jestExitCode = runCommand("npx", ["jest", "--runInBand"]);
    } finally {
        runCommand("node", ["rollback.js"]);
    }

    process.exit(jestExitCode);
}

runFunctionalSuite().catch((error) => {
    console.error("Falha ao executar a suite funcional.", error);
    process.exit(1);
});
