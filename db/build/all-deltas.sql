-- Script generated at 20-Dec-2008 14:13:20



--------------- Fragment begins: #1: 1 Create Customer table.sql ---------------
INSERT INTO changelog (change_number, delta_set, start_dt, applied_by, description) VALUES (1, 'Main', CURRENT_TIMESTAMP, USER(), '1 Create Customer table.sql');
COMMIT;


-- Change script: #1: 1 Create Customer table.sql
CREATE TABLE CUSTOMER (
		customerid INTEGER NOT NULL,
		title CHAR(3) NOT NULL,
		firstname VARCHAR(30) NOT NULL,
		lastname VARCHAR(30) NOT NULL,
		userid CHAR(8),
		password CHAR(8)
	);

CREATE UNIQUE INDEX IDX_CUSTOMER ON CUSTOMER (customerid ASC);

ALTER TABLE CUSTOMER ADD CONSTRAINT PK_CUSTOMER PRIMARY KEY (customerid);


UPDATE changelog SET complete_dt = CURRENT_TIMESTAMP WHERE change_number = 1 AND delta_set = 'Main';
COMMIT;
--------------- Fragment ends: #1: 1 Create Customer table.sql ---------------


--------------- Fragment begins: #2: 2 Create Account table.sql ---------------
INSERT INTO changelog (change_number, delta_set, start_dt, applied_by, description) VALUES (2, 'Main', CURRENT_TIMESTAMP, USER(), '2 Create Account table.sql');
COMMIT;


-- Change script: #2: 2 Create Account table.sql
CREATE TABLE ACCOUNT (
		accid CHAR(8) NOT NULL,
		balance DECIMAL(22 , 2) NOT NULL,
		interest DECIMAL NOT NULL,
		acctype VARCHAR(8) NOT NULL,
		discriminator CHAR(1) NOT NULL,
		overdraft DECIMAL(22 , 2) NOT NULL,
		minamount DECIMAL(22 , 2) NOT NULL
	);

CREATE UNIQUE INDEX IDX_ACCOUNT ON ACCOUNT (accid ASC);

ALTER TABLE ACCOUNT ADD CONSTRAINT PK_ACCOUNT PRIMARY KEY (accid);


UPDATE changelog SET complete_dt = CURRENT_TIMESTAMP WHERE change_number = 2 AND delta_set = 'Main';
COMMIT;
--------------- Fragment ends: #2: 2 Create Account table.sql ---------------


--------------- Fragment begins: #3: 3 Create TransRecord table.sql ---------------
INSERT INTO changelog (change_number, delta_set, start_dt, applied_by, description) VALUES (3, 'Main', CURRENT_TIMESTAMP, USER(), '3 Create TransRecord table.sql');
COMMIT;


-- Change script: #3: 3 Create TransRecord table.sql
CREATE TABLE TRANSRECORD (
        transid TIMESTAMP NOT NULL,
        accid CHAR(8) NOT NULL,
        transtype CHAR(1) NOT NULL,
        transamt DECIMAL(10, 2) NOT NULL
	);

CREATE UNIQUE INDEX IDX_TRANSRECORD ON TRANSRECORD (transid ASC);

ALTER TABLE TRANSRECORD ADD CONSTRAINT PK_TRANSID PRIMARY KEY (transid);

ALTER TABLE TRANSRECORD ADD CONSTRAINT FK_ACC_TO_TRANSREC FOREIGN KEY (accid)
	REFERENCES ACCOUNT (accid);


UPDATE changelog SET complete_dt = CURRENT_TIMESTAMP WHERE change_number = 3 AND delta_set = 'Main';
COMMIT;
--------------- Fragment ends: #3: 3 Create TransRecord table.sql ---------------


--------------- Fragment begins: #4: 4 Create CustAcct table.sql ---------------
INSERT INTO changelog (change_number, delta_set, start_dt, applied_by, description) VALUES (4, 'Main', CURRENT_TIMESTAMP, USER(), '4 Create CustAcct table.sql');
COMMIT;


-- Change script: #4: 4 Create CustAcct table.sql
CREATE TABLE CUSTACCT (
		customerid INTEGER NOT NULL,
		accid CHAR(8) NOT NULL
	);

CREATE UNIQUE INDEX IDX_CUSTACCT ON CUSTACCT (customerid ASC, accid ASC);

ALTER TABLE CUSTACCT ADD CONSTRAINT PK_CUSTACCT PRIMARY KEY (customerid, accid);

ALTER TABLE CUSTACCT ADD CONSTRAINT FK_CA_TO_CUST FOREIGN KEY (customerid, accid)
	REFERENCES CUSTACCT (customerid, accid);


UPDATE changelog SET complete_dt = CURRENT_TIMESTAMP WHERE change_number = 4 AND delta_set = 'Main';
COMMIT;
--------------- Fragment ends: #4: 4 Create CustAcct table.sql ---------------

