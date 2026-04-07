const path = require("node:path");
const dotenv = require("dotenv");
const request = require("supertest");

dotenv.config({
    path: path.resolve(__dirname, "../../.env")
});

function loadFixtures() {
    return require("./fixtures/local.json");
}

const fixtures = loadFixtures();

const applicationBaseUrl = process.env.APPLICATION_BASE_URL || fixtures.application.baseUrl;

function createGetRequest(resourcePath) {
    return request(applicationBaseUrl)
        .get(resourcePath)
        .set("Content-Type", "application/json");
}

function createPostRequest(resourcePath, payload) {
    return request(applicationBaseUrl)
        .post(resourcePath)
        .set("Content-Type", "application/json")
        .send(JSON.stringify(payload));
}

function createPostRequestWithRawBody(resourcePath, payload) {
    return request(applicationBaseUrl)
        .post(resourcePath)
        .set("Content-Type", "application/json")
        .send(payload);
}

module.exports = {
    fixtures,
    applicationBaseUrl,
    createGetRequest,
    createPostRequest,
    createPostRequestWithRawBody
};
