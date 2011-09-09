CREATE TABLE ACCOUNT (
        accid CHAR(8) NOT NULL,
        balance DECIMAL(22 , 2) NOT NULL,
        interest DECIMAL NOT NULL,
        acctype VARCHAR(8) NOT NULL,
        discriminator CHAR(1) NOT NULL,
        overdraft DECIMAL(22 , 2) NOT NULL,
        minamount DECIMAL(22 , 2) NOT NULL,
	CONSTRAINT PK_ACCOUNT PRIMARY KEY (accid)
    );

--//@UNDO

DROP TABLE ACCOUNT;
