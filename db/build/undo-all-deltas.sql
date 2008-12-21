-- Script generated at 20-Dec-2008 14:13:20



-- Change script: #4: 4 Create CustAcct table.sql

DROP TABLE CUSTACCT;

DELETE FROM changelog WHERE change_number = 4 AND delta_set = 'Main';
COMMIT;
--------------- Fragment ends: #4: 4 Create CustAcct table.sql ---------------


-- Change script: #3: 3 Create TransRecord table.sql

DROP TABLE TRANSRECORD;

DELETE FROM changelog WHERE change_number = 3 AND delta_set = 'Main';
COMMIT;
--------------- Fragment ends: #3: 3 Create TransRecord table.sql ---------------


-- Change script: #2: 2 Create Account table.sql

DROP TABLE ACCOUNT;

DELETE FROM changelog WHERE change_number = 2 AND delta_set = 'Main';
COMMIT;
--------------- Fragment ends: #2: 2 Create Account table.sql ---------------


-- Change script: #1: 1 Create Customer table.sql

DROP TABLE CUSTOMER;

DELETE FROM changelog WHERE change_number = 1 AND delta_set = 'Main';
COMMIT;
--------------- Fragment ends: #1: 1 Create Customer table.sql ---------------

