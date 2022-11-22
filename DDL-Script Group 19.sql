CREATE TABLE branch (
branch_id SERIAL UNIQUE PRIMARY KEY,
add1 varchar NOT NULL,   
add2 varchar,
city varchar NOT NULL,
state varchar NOT NULL,
zip numeric(5) NOT NULL
);

CREATE TABLE employee (
name VARCHAR(32) NOT NULL,
employee_id SERIAL UNIQUE,
ssn numeric(9) UNIQUE PRIMARY KEY,
add1 varchar NOT NULL,
add2 varchar,
city varchar NOT NULL,
state varchar NOT NULL,
zip numeric(5) NOT NULL,
role VARCHAR(32) NOT NULL,
salary INT,
branch INT NOT NULL,
FOREIGN KEY (branch) REFERENCES branch ON DELETE CASCADE,
CONSTRAINT check_salary CHECK (salary > 0),
CONSTRAINT role_type_check CHECK (role IN ('Teller', 'LoanSpecialist', 'Manager'))
);

CREATE TABLE account (
account_id SERIAL UNIQUE PRIMARY KEY,
balance INT NOT NULL,
type VARCHAR(16) NOT NULL,
interest_rate INT, 
allow_negative boolean NOT NULL, 
overdraft_fee INT,
monthly_fee INT,
CONSTRAINT account_type_check CHECK (type IN ('Checking', 'Savings'))
);

CREATE TABLE customer (
name VARCHAR(32) NOT NULL,
customer_id SERIAL UNIQUE,
ssn numeric(9) UNIQUE PRIMARY KEY,
add1 varchar NOT NULL,
add2 varchar,
city varchar NOT NULL,
state varchar NOT NULL,
zip numeric(5) NOT NULL,
account_id INT,
home_branch INT NOT NULL,
FOREIGN KEY (home_branch) REFERENCES branch ON DELETE CASCADE,
FOREIGN KEY (account_id) REFERENCES account ON DELETE SET NULL
);

CREATE TABLE transaction (
transaction_id SERIAL UNIQUE PRIMARY KEY,
account_id INT NOT NULL,
type VARCHAR(16) NOT NULL,
amount INT NOT NULL,
description VARCHAR(255),
account_recipient INT,
account_sender INT,
FOREIGN KEY (account_id) REFERENCES account,
FOREIGN KEY (account_recipient) REFERENCES account,
FOREIGN KEY (account_sender) REFERENCES account,
CONSTRAINT transaction_type_check CHECK (type IN ('Deposit', 'Withdrawal', 'Transfer', 'ExternalTransfer'))
);

CREATE TABLE loan (
loan_id SERIAL UNIQUE PRIMARY KEY,
account_id INT,
amount INT NOT NULL,
runtime INT NOT NULL,
interest_schedule DATE NOT NULL,
FOREIGN KEY(account_id) REFERENCES account ON DELETE CASCADE
);