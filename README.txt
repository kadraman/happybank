HappyBank

---------



Pre-requisites:

 

  - Sun Java JDK 5.0 (http://java.sun.com/javase/downloads/index_jdk5.jsp)
 
  - Apache Ant 1.7 (http://ant.apache.org/)
 
  - Apache Tomcat 6.0 (http://tomcat.apache.org/download-60.cgi)
 

To configure Apache Tomcat, carry out the following:

1. Create or configure a TOMCAT_HOME\conf\tomcat-users.xml with a user called
   "tomcat" similar to the following:

	

    <?xml version='1.0' encoding='utf-8'?>
	
    <tomcat-users>
	  
         <role rolename="manager"/>
	  
         <user username="tomcat" password="tomcat" roles="manager"/>
	
    </tomcat-users>


2. Startup Apache Tomcat, e.g.

    > cd %TOMCAT_HOME%\bin
    > startup.bat or startup.sh

3. Test Apache Tomcat, e.g. assuming default port of 8080 browse to
   http://localhost:8080/ and check the Tomcat home page is displayed.


To build, start up a console window and execute the following:



1. Configure and start up HSQLDB database:

	

    > cd db
	
    > ant hsqldb.start
	
    > ant bootstrap
	


2. Build, test and publish common libraries:

   > cd ..\common
   > ant junit.all
   > ant publish

3. Build and run administrative client:

	

    > cd ..\admin
	
    > ant run
	


4. Build and deploy web client

	

    > cd ..\online
	
    > ant dist
		
    > ant tomcat.deploy
	


5. Open a web browser to http://localhost:8080/HappyBankOnline and login 
   
   using the username and password of:

 cust101/password
	



Enjoy!



Kevin A. Lee

kevin@71khz.com