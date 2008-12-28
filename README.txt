HappyBank

---------



Pre-requisites:

 

  - Sun Java JDK 5.0 (http://java.sun.com/javase/downloads/index_jdk5.jsp)
 
  - Apache Ant 1.7 (http://ant.apache.org/)
 
  - Apache Tomcat 6.0 (http://tomcat.apache.org/download-60.cgi)
 


To build:



1. Configure and start up HSQLDB database:

	

    > cd db
	
    > ant db.start
	
    > ant bootstrap
	


2. Build and run administrative client:

	

    > cd ..\admin
	
    > ant dist
	
    > ant run
	


3. Build and test web client

	

    > cd ..\online
	
    > ant dist
	
    > ant junit-all
	


4. Configure Tomcat resources by adding the following resource to TOMCAT_HOME\conf\server.xml

	

    <GlobalNamingResources>
		
        ...
		
        <Resource auth="Container" driverClassName="org.hsqldb.jdbcDriver" 
		 
         maxActive="30" maxIdle="10" maxWait="4000" name="jdbc/hbdb" password="" 
		 
         type="javax.sql.DataSource" url="jdbc:hsqldb:hsql://localhost/xdb" 
		 
         username="sa"/> 
		 
        ...
	
    </GlobalNamingResources>
	


5. Create or configure a TOMCAT_HOME\conf\tomcat-users.xml similar to the following:

	

    <?xml version='1.0' encoding='utf-8'?>
	
    <tomcat-users>
	  
         <role rolename="manager"/>
	  
         <user username="tomcat" password="tomcat" roles="manager"/>
	
    </tomcat-users>
	


6. Install the application using the tomcat manager, or:

	

    > ant deploy
	


7. Open a web browser to http://localhost:8080/HappyBankOnline and login 
   
   using the username and password of:

	cust101/password
	



Enjoy!



Kevin A. Lee
kevin.lee@buildmeister.com