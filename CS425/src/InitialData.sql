DELETE from account;
DELETE from customer;
DELETE from employee;
DELETE from branch;
INSERT INTO branch (add1,city,state,zip) VALUES('123 Apple St.', 'Chicago', 'IL','60604');
INSERT INTO branch (add1,city,state,zip) VALUES('456 Orange Ave.', 'New York', 'NY','12123');
INSERT INTO customer (ssn,name,add1,city,state,zip,home_branch) VALUES(123456789, 'Peter','101 North', 'Chicago', 'IL', '60604', 16);
INSERT INTO customer (ssn,name,add1,city,state,zip,home_branch) VALUES(223456789, 'Alice','36 South St.', 'New', 'NY', '12123', 17);
INSERT INTO employee (ssn,name,salary,add1,city,state,zip,branch, role) VALUES(323456789, 'Glavic', 10000, '123 Street', 'Chicago', 'IL', '60604', 16, 'Manager');
INSERT INTO employee (ssn,name,salary,add1,city,state,zip,branch, role) VALUES(423456789, 'Jeff', 12000, '43 Ave', 'Chicago', 'IL', '60604', 16, 'Teller');
INSERT INTO employee (ssn,name,salary,add1,city,state,zip,branch, role) VALUES(523456789, 'Paul', 13000, 'Empire Drive', 'New York', 'NY', '12423', 17, 'Manager');
INSERT INTO employee (ssn,name,salary,add1,city,state,zip,branch, role) VALUES(623456789, 'Greg', 15000, '68 S. St.', 'New York', 'NY', '13123', 17, 'Teller');
INSERT INTO account (ssn, balance, type, interest_rate, overdraft_fee,  monthly_fee, allow_negative) VALUES(123456789, 1000, 'Checking',0.01,20,5,false);
INSERT INTO account (ssn, balance, type, interest_rate, overdraft_fee,  monthly_fee, allow_negative) VALUES(123456789, 1000, 'Savings',0.1,20,5,false);
INSERT INTO account (ssn, balance, type, interest_rate, overdraft_fee,  monthly_fee, allow_negative) VALUES(223456789, 1000, 'Checking',0.01,20,5,false)