# Java Bank Management System

A desktop application for managing bank accounts, built with Java Swing and MySQL.

## Features

- Account Creation with UID and UPIN
- Secure Login System
- Money Transfer between Accounts
- Balance Inquiry
- UPIN Reset Functionality

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Server 5.7 or higher
- MySQL Connector/J (JDBC driver)
- NetBeans IDE (recommended) or any Java IDE

## Setup Instructions

1. **Database Setup**
   - Install MySQL Server if not already installed
   - Log in to MySQL
   - Run the `database.sql` script to create the database and tables:
     ```bash
     mysql -u root -p < database.sql
     ```

2. **Configure Database Connection**
   - Open `src/utils/DatabaseConnector.java`
   - Update the following constants with your MySQL credentials:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/BankDB";
     private static final String USERNAME = "your_username";
     private static final String PASSWORD = "your_password";
     ```

3. **Project Setup**
   - Clone or download this repository
   - Open the project in NetBeans IDE or your preferred Java IDE
   - Add MySQL Connector/J to your project's classpath
   - Build and run the project

## Usage

1. **Creating a New Account**
   - Click on "New Account" button
   - Fill in the required details:
     - Name
     - Email
     - Phone Number
     - Aadhar Card Number
     - PAN Card Number
     - Initial Deposit Amount
     - Set a 4-6 digit UPIN
   - Submit the form
   - Note down your UID for future login

2. **Logging In**
   - Enter your UID and UPIN
   - Click "Login"

3. **Money Transfer**
   - Log in to your account
   - Click "Transfer Money"
   - Enter recipient's UID and amount
   - Confirm the transfer

4. **Checking Balance**
   - Log in to your account
   - Your current balance is displayed on the dashboard

## Security Features

- Secure UPIN storage
- Transaction integrity with SQL transactions
- Input validation for all fields
- Session management

## Contributing

Feel free to fork this repository and submit pull requests for any improvements.

## License

This project is open source and available under the MIT License. 