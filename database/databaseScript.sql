DROP DATABASE BankATM;

CREATE DATABASE IF NOT EXISTS BankATM;

USE BankATM;

CREATE TABLE IF NOT EXISTS Users (
    id VARCHAR(200) PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    status VARCHAR(10) NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO Users (id, firstname, lastname, username, status, password) VALUES ("0", "dimitris", "staratzis", "dstara", "Customer", "pass");
INSERT INTO Users (id, firstname, lastname, username, status, password) VALUES ("1", "dimi", "stara", "stara", "Customer", "password");
INSERT INTO Users (id, firstname, lastname, username, status, password) VALUES ("2", "Michael", "Jackson", "mjack", "Manager", "pass");

CREATE TABLE IF NOT EXISTS Loans (
    id INT NOT NULL AUTO_INCREMENT,
    user_id VARCHAR(200) NOT NULL,
    collateral VARCHAR(255) NOT NULL,
    date_issued VARCHAR(255) NOT NULL,
    termInMonths INT NOT NULL,
    initial_principal DOUBLE NOT NULL,
    number_of_payments INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES  BankATM.Users (id)
);

INSERT INTO Loans (user_id, collateral, date_issued, termInMonths, initial_principal, number_of_payments) VALUES ("0", "house", "17 November", 5, 1.5, 0);
INSERT INTO Loans (user_id, collateral, date_issued, termInMonths, initial_principal, number_of_payments) VALUES ("1", "car", "18 November", 6, 1.2, 0);


CREATE TABLE IF NOT EXISTS security_accounts (
	user_id VARCHAR(200) NOT NULL,
    iban VARCHAR(100) NOT NULL,
    balance_in_local_currency FLOAT NOT NULL,
    routing_number INT NOT NULL,
    account_number INT NOT NULL,
    is_active VARCHAR(10) NOT NULL,
    currency VARCHAR(100) NOT NULL,
    opening_charge FLOAT NOT NULL,
    closing_charge FLOAT NOT NULL,
    PRIMARY KEY (iban),
    FOREIGN KEY (user_id)
        REFERENCES  BankATM.Users (id)
);

INSERT INTO security_accounts VALUES ("0", "123BB", 2.5, 123, 456, "TRUE", "USD", 1.5, 1);
INSERT INTO security_accounts VALUES ("1", "123CC", 1.5, 423, 356, "TRUE", "EUR", 4.5, 2);

CREATE TABLE IF NOT EXISTS savings_accounts (
	user_id VARCHAR(200) NOT NULL,
    iban VARCHAR(100) NOT NULL,
    balance_in_local_currency FLOAT NOT NULL,
    routing_number INT NOT NULL,
    account_number INT NOT NULL,
    is_active VARCHAR(10) NOT NULL,
    currency VARCHAR(100) NOT NULL,
    opening_charge FLOAT NOT NULL,
    closing_charge FLOAT NOT NULL,
    interest FLOAT NOT NULL,
    PRIMARY KEY (iban),
    FOREIGN KEY (user_id)
        REFERENCES  BankATM.Users (id)
);

INSERT INTO savings_accounts VALUES ("0", "123BBB", 5.5, 1233, 4566, "TRUE", "USD", 1.5, 1, 0.5);
INSERT INTO savings_accounts VALUES ("1", "123CCC", 6.5, 12334, 45667, "TRUE", "USD", 2.5, 2, 1.5);

CREATE TABLE IF NOT EXISTS checkings_accounts (
	user_id VARCHAR(200) NOT NULL,
    iban VARCHAR(100) NOT NULL,
    balance_in_local_currency FLOAT NOT NULL,
    routing_number INT NOT NULL,
    account_number INT NOT NULL,
    is_active VARCHAR(10) NOT NULL,
    currency VARCHAR(100) NOT NULL,
    opening_charge FLOAT NOT NULL,
    closing_charge FLOAT NOT NULL,
    transferFee FLOAT NOT NULL,
    withdrawalFee FLOAT NOT NULL,
    PRIMARY KEY (iban),
    FOREIGN KEY (user_id)
        REFERENCES  BankATM.Users (id)
);

INSERT INTO checkings_accounts VALUES ("0", "123BBBB", 7.5, 12333, 45666, "TRUE", "USD", 1.5, 1, 0.5, 1.5);
INSERT INTO checkings_accounts VALUES ("1", "123BBBBC", 8.5, 123334, 456667, "TRUE", "USD", 3.5, 2, 1.5, 6.5);

CREATE TABLE IF NOT EXISTS Stocks (
	name VARCHAR(20) NOT NULL,
    available_shares INT NOT NULL,
    total_shares INT NOT NULL,
    current_price FLOAT NOT NULL,
    PRIMARY KEY (name)
);

INSERT INTO Stocks VALUES ("Apple", "2", "4", 7.2);
INSERT INTO Stocks VALUES ("Samsung", "4", "4", 10.2);

CREATE TABLE IF NOT EXISTS users_stocks (
    user_id VARCHAR(200) NOT NULL,
    stock_id VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES BankATM.Users (id),
    FOREIGN KEY (stock_id) REFERENCES BankATM.Stocks (name),
    PRIMARY KEY (user_id, stock_id)
);

INSERT INTO users_stocks VALUES ("0", "Apple");
INSERT INTO users_stocks VALUES ("1", "Apple");

CREATE TABLE IF NOT EXISTS single_checking_account_transactions (
    account_id VARCHAR(100) NOT NULL,
    transaction_id INT NOT NULL AUTO_INCREMENT,
    amount FLOAT NOT NULL,
    type VARCHAR(30) NOT NULL,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (account_id) REFERENCES BankATM.checkings_accounts (iban)
);

INSERT INTO single_checking_account_transactions (account_id, amount, type) VALUES ("123BBBB", 10.5, "Deposit");
INSERT INTO single_checking_account_transactions (account_id, amount, type) VALUES ("123BBBB", 11.5, "Withdraw");

CREATE TABLE IF NOT EXISTS single_security_account_transactions (
    account_id VARCHAR(100) NOT NULL,
    transaction_id INT NOT NULL AUTO_INCREMENT,
    amount FLOAT NOT NULL,
    type VARCHAR(30) NOT NULL,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (account_id) REFERENCES BankATM.security_accounts (iban)
);

INSERT INTO single_security_account_transactions (account_id, amount, type) VALUES ("123BB", 15.5, "Deposit");
INSERT INTO single_security_account_transactions (account_id, amount, type) VALUES ("123BB", 14.5, "Deposit");

CREATE TABLE IF NOT EXISTS single_saving_account_transactions (
    account_id VARCHAR(100) NOT NULL,
    transaction_id INT NOT NULL AUTO_INCREMENT,
    amount FLOAT NOT NULL,
    type VARCHAR(30) NOT NULL,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (account_id) REFERENCES BankATM.savings_accounts (iban)
);

INSERT INTO single_saving_account_transactions (account_id, amount, type) VALUES ("123BBB", 16.5, "Transfer");
INSERT INTO single_saving_account_transactions (account_id, amount, type) VALUES ("123BBB", 18.5, "Deposit");