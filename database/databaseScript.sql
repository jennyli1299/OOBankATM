CREATE TABLE IF NOT EXISTS Users (
    id INT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    status VARCHAR(10) NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Loans (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    collateral VARCHAR(255) NOT NULL,
    date_issued VARCHAR(255) NOT NULL,
    termInMonths INT NOT NULL,
    initial_principal DOUBLE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id)
        REFERENCES  BankATM.User (id)
);


CREATE TABLE IF NOT EXISTS Accounts (
	user_id INT NOT NULL,
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

CREATE TABLE IF NOT EXISTS Stocks (
	name VARCHAR(20) NOT NULL,
    available_shares INT NOT NULL,
    total_shares INT NOT NULL,
    current_price FLOAT NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS users_stocks (
    user_id INTEGER NOT NULL,
    stock_id VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES BankATM.Users (id),
    FOREIGN KEY (stock_id) REFERENCES BankATM.Stocks (name),
    PRIMARY KEY (user_id, stock_id)
);

CREATE TABLE IF NOT EXISTS single_account_transactions (
    account_id VARCHAR(100) NOT NULL,
    transaction_id INT NOT NULL AUTO_INCREMENT,
    ammount FLOAT NOT NULL,
    type VARCHAR(30) NOT NULL,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (account_id) REFERENCES BankATM.Accounts (iban)
);


