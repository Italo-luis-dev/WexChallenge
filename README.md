
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

### How to Run a Spring Boot Application with Maven

1. **Download the Project from GitHub**:  
   If you don't have the project yet, you can download it from GitHub using the following steps:
   - Open your terminal or command prompt.
   - Navigate to the directory where you want to clone the project.
   - Use the following Git command to clone the repository:
     ```bash
     git clone https://github.com/your-username/your-repository-name.git
     ```
     Replace `your-username` and `your-repository-name` with the correct GitHub username and repository name.

2. **Ensure Prerequisites**:  
   - Make sure you have **Java** (JDK 21 or higher) installed. You can check your Java version with:  
     ```bash
     java -version
     ```
   - Ensure **Maven** is installed on your system. Verify with:  
     ```bash
     mvn -version
     ```

3. **Navigate to Your Project**:  
   In your terminal, go to the root directory of your Spring Boot project where the `pom.xml` file is located.

4. **Build the Project**:  
   Before running the application, you need to build the project. Use the following Maven command to compile and package the application:
   ```bash
   mvn clean install
   
5. **Access the Application**:
Open your browser and go to [ http://localhost:8081](http://localhost:8081/swagger-ui/index.html#/) to check if the application is running.

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


