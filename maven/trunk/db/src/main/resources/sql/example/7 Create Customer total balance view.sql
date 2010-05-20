CREATE VIEW CUSTBALANCES AS
    SELECT ca.customerid, SUM(a.balance) AS baltotal
        FROM ACCOUNT a, CUSTACCT ca 
        WHERE a.accid = ca.accid 
        GROUP BY ca.customerid

--//@UNDO

DROP VIEW CUSTBALANCES;
