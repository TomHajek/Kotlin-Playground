# Get started

## Clone the repository

```sh
git clone -b files/pdf --single-branch https://github.com/TomHajek/Kotlin-Playground.git
```

## Endpoints

This demo API allows you to generate PDF reports for `transaction` and `account` entities.

### Get all transactions

Generates a PDF report containing all transactions.

Query Parameters:

- `type` (required): Must be `TRANSACTION`

Request:

```sh
curl -X GET "http://localhost:8080/api/pdf/generate?type=TRANSACTION" -o transactions.pdf
```

Response:

- Content-Type: `application/pdf`
- Attachment Name: `report.pdf`
- Status Code: `200 OK`

### Get transactions by account id

Generates a PDF report containing transactions for a specific account.

Query Parameters:

- `type` (required): Must be `TRANSACTION`
- `id` (required): Account ID whose transactions will be included.

Request:

```sh
curl -X GET "http://localhost:8080/api/pdf/generate?type=TRANSACTION&id=1" -o account_1_transactions.pdf
```

Response:

- Content-Type: `application/pdf`
- Attachment Name: `report.pdf`
- Status Code: `200 OK`

### Get an account report

Generates a PDF report containing details of a specific account.

Query Parameters:

- `type` (required): Must be `ACCOUNT`
- `id` (required): Account ID whose details will be included.

Request:

```sh
curl -X GET "http://localhost:8080/api/pdf/generate?type=ACCOUNT&id=1" -o account_1_report.pdf
```

Response:

- Content-Type: `application/pdf`
- Attachment Name: `report.pdf`
- Status Code: `200 OK`

## Errors

`400 Bad Request`: 

- Missing required parameters (Account id for reports).
- Unsupported template type.

`404 Not Found`: 

- Account with given id was not found (does not exist in the database).

`500 Internal Server Error`: 

- Unexpected server error while generating the PDF.
