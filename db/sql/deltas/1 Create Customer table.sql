CREATE TABLE CUSTOMER (
        customerid INTEGER NOT NULL,
        title CHAR(3) NOT NULL,
        firstname VARCHAR(30) NOT NULL,
        lastname VARCHAR(30) NOT NULL,
        userid CHAR(8),
        password CHAR(8),
	CONSTRAINT PK_CUSTOMER PRIMARY KEY (customerid)
    );

CREATE INDEX IDX_USERID ON CUSTOMER (userid);

--//@UNDO

DROP TABLE CUSTOMER;
