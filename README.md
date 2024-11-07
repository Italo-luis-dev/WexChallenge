
# Wex Gateway Product Challenge

Developed by: Italo Luis <br>
Linkedin: https://www.linkedin.com/in/italoluisdev/

# Quick Start Demo

This system allows for storing and retrieving purchase transactions in US dollars (USD), including converting them to other countries' currencies based on the exchange rates of the transaction date. It enables users to record purchases with a description, transaction date, and amount, and later retrieve these transactions converted to a specified country's currency using the exchange rates provided by the U.S. Treasury Exchange Rate API.
Requirements:

## 1. Store a Purchase Transaction:   

The system must store the provided information and assign a unique identifier to each transaction.

   - Fields:
     - Description: Maximum of 50 characters.
     - Transaction Date: Must be in a valid date format.
     - Purchase Amount: Positive value, rounded to the nearest cent (USD).
     - Unique Identifier: Each transaction must have a unique identifier.

 
## 2. Retrieve a Purchase Transaction in a Specified Country’s Currency: 

The system must retrieve stored transactions and convert the purchase amount to a specified country’s currency using the exchange rate for the date of the transaction, as provided by the U.S. Treasury Exchange Rate API.

- Data to Retrieve
  - Transaction identifier.
  - Description.
  - Transaction date.
  - Original purchase amount (USD).
  - Exchange rate used.
  - Converted amount based on the exchange rate for the transaction date.



U.S. Treasury Exchange Rate API:
https://fiscaldata.treasury.gov/datasets/treasury-reporting-rates-exchange/treasury-reporting-rates-of-exchange


<br>

# Usage
[(Back to top)](#table-of-contents)

###  Prerequisite:
Java 21

### Technologies used

- Spring Boot (3.3.5)
 
- Database:
  - h2 (In-memory database)

- Testing:
  - reactor-test
  - spring-boot-starter-test

- API Documentation:
  - springdoc-openapi-starter-webflux-ui
  - springdoc-openapi-starter-webmvc-ui

- Data Mapping:
  - mapstruct
  - mapstruct-processor

- Utilities:
  - commons-math3
  - commons-text
  - gson

- Development:
  - lombok

- Code Coverage:
  - jacoco-maven-plugin

- Java Version: 21


