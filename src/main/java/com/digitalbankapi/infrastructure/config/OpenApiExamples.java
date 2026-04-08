package com.digitalbankapi.infrastructure.config;

public final class OpenApiExamples {

    public static final String ACCOUNT_CREATE_REQUEST = """
            {
              "ownerName": "Maria Silva",
              "initialBalance": 350.00
            }
            """;

    public static final String ACCOUNT_CREATE_RESPONSE = """
            {
              "id": 4,
              "ownerName": "Maria Silva",
              "balance": 350.00,
              "_links": {
                "self": {
                  "href": "http://localhost:8080/accounts/4"
                },
                "accounts": {
                  "href": "http://localhost:8080/accounts"
                },
                "movements": {
                  "href": "http://localhost:8080/accounts/4/movements"
                },
                "notifications": {
                  "href": "http://localhost:8080/accounts/4/notifications"
                }
              }
            }
            """;

    public static final String ACCOUNT_LIST_RESPONSE = """
            {
              "_embedded": {
                "accounts": [
                  {
                    "id": 1,
                    "ownerName": "Ana Souza",
                    "balance": 1000.00,
                    "_links": {
                      "self": {
                        "href": "http://localhost:8080/accounts/1"
                      },
                      "accounts": {
                        "href": "http://localhost:8080/accounts"
                      },
                      "movements": {
                        "href": "http://localhost:8080/accounts/1/movements"
                      },
                      "notifications": {
                        "href": "http://localhost:8080/accounts/1/notifications"
                      }
                    }
                  }
                ]
              },
              "_links": {
                "self": {
                  "href": "http://localhost:8080/accounts"
                }
              }
            }
            """;

    public static final String TRANSFER_REQUEST = """
            {
              "sourceAccountId": 1,
              "targetAccountId": 2,
              "amount": 200.00
            }
            """;

    public static final String TRANSFER_RESPONSE = """
            {
              "sourceAccountId": 1,
              "targetAccountId": 2,
              "transferReference": "4d2f91fb-daf5-4ea7-8db2-757ca1b89c30",
              "transferredAmount": 200.00,
              "sourceAccountBalance": 800.00,
              "targetAccountBalance": 700.00,
              "_links": {
                "self": {
                  "href": "http://localhost:8080/transfers"
                },
                "sourceAccount": {
                  "href": "http://localhost:8080/accounts/1"
                },
                "targetAccount": {
                  "href": "http://localhost:8080/accounts/2"
                },
                "sourceAccountMovements": {
                  "href": "http://localhost:8080/accounts/1/movements"
                },
                "targetAccountMovements": {
                  "href": "http://localhost:8080/accounts/2/movements"
                },
                "sourceAccountNotifications": {
                  "href": "http://localhost:8080/accounts/1/notifications"
                },
                "targetAccountNotifications": {
                  "href": "http://localhost:8080/accounts/2/notifications"
                }
              }
            }
            """;

    public static final String MOVEMENT_LIST_RESPONSE = """
            {
              "_embedded": {
                "movements": [
                  {
                    "accountId": 1,
                    "transferReference": "4d2f91fb-daf5-4ea7-8db2-757ca1b89c30",
                    "movementType": "DEBIT",
                    "amount": 200.00,
                    "description": "Debito gerado pela transferencia para a conta 2.",
                    "createdAt": "2026-04-07T10:15:30Z",
                    "_links": {
                      "self": {
                        "href": "http://localhost:8080/accounts/1/movements"
                      },
                      "account": {
                        "href": "http://localhost:8080/accounts/1"
                      }
                    }
                  }
                ]
              },
              "_links": {
                "self": {
                  "href": "http://localhost:8080/accounts/1/movements"
                },
                "account": {
                  "href": "http://localhost:8080/accounts/1"
                }
              }
            }
            """;

    public static final String NOTIFICATION_LIST_RESPONSE = """
            {
              "_embedded": {
                "notifications": [
                  {
                    "accountId": 1,
                    "transferReference": "4d2f91fb-daf5-4ea7-8db2-757ca1b89c30",
                    "notificationStatus": "REGISTERED",
                    "message": "Transferencia enviada com sucesso para a conta 2.",
                    "createdAt": "2026-04-07T10:15:30Z",
                    "_links": {
                      "self": {
                        "href": "http://localhost:8080/accounts/1/notifications"
                      },
                      "account": {
                        "href": "http://localhost:8080/accounts/1"
                      }
                    }
                  }
                ]
              },
              "_links": {
                "self": {
                  "href": "http://localhost:8080/accounts/1/notifications"
                },
                "account": {
                  "href": "http://localhost:8080/accounts/1"
                }
              }
            }
            """;

    public static final String ACCOUNT_NOT_FOUND_ERROR = """
            {
              "key": "ACCOUNT_NOT_FOUND",
              "value": "Conta nao encontrada para o identificador 99."
            }
            """;

    public static final String INVALID_ACCOUNT_REQUEST_ERROR = """
            {
              "key": "INVALID_REQUEST_DATA",
              "value": "O campo ownerName e obrigatorio."
            }
            """;

    public static final String INVALID_TRANSFER_REQUEST_ERROR = """
            {
              "key": "TRANSFER_AMOUNT_MUST_BE_POSITIVE",
              "value": "O valor da transferencia deve ser maior que zero."
            }
            """;

    public static final String ACCOUNT_RESOURCE_BUSY_ERROR = """
            {
              "key": "ACCOUNT_RESOURCE_BUSY",
              "value": "Uma das contas envolvidas esta temporariamente em processamento. Tente novamente em instantes."
            }
            """;

    private OpenApiExamples() {
    }
}
