# Credit Card Checker

This is a simple REST API application that validates different kinds of credit card numbers.  
Credit card types checked are AMEX, Discover, MasterCard and Visa. 

##Technology used
This project is built using Java Spring Boot 4.

##How to run
To build project, go to project directory and run command:
```
mvnw install
```

To run project, run jar file created in the target folder.
```
java -jar target/credit-card-checker-0.0.1-SNAPSHOT.jar
```

## API Endpoints

### POST creditcards/validate
Validates a credit card number

**Request Header**

ContentType=application/json

**Request Body**

Put the credit card number on the request body using the following json format:
```   
{
   "cardNumber": "<card-number>"
}
```

**Response**

```
// cardNumber is a valid Visa number
{
    "cardNumber": "4012888888881881",
    "cardType": "Visa",
    "valid": false
}

// cardNumber is a type Visa but invalid
{
    "cardNumber": "4111111111111",
    "cardType": "Visa",
    "valid": false
}

// Cannot determine the type of credit card
{
    "cardNumber": "9111111111111111",
    "cardType": "Unknown",
    "valid": false
}
```