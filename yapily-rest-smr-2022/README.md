Payment example

```java
curl --location --request POST 'http://localhost:8080/payment/' \
        --header 'Content-Type: application/json' \
        --data-raw '{
        "applicationUserId": "single-payment-tutorial",
        "institutionId": "modelo-sandbox",
        "callback": "http://sme.yapily.local:8080/payment/collect",
        "paymentRequest": {
        "paymentIdempotencyId": "paymentIdempotencyId",
        "type": "DOMESTIC_PAYMENT",
        "reference": "Bills Coffee Shop",
        "amount": {
        "amount": 8.70,
        "currency": "GBP"
        },
        "payee": {
        "name": "BILLS COFFEE LTD",
        "accountIdentifications": [
        {
        "type": "ACCOUNT_NUMBER",
        "identification": "11111111"
        },
        {
        "type": "SORT_CODE",
        "identification": "111111"
        }
        ],
        "address": {
        "country": "GB"
        }
        }
        }
        }'
```