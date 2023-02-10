# <div align="center">Alco-meeting</div> 

### Description

This pet project was created to practice writing a web application. It is written using:
 - Java 17
 - Spring boot (version 2.7.5)
 - Spring Security
 - Spring Data JPA
 - Maven
 - Hibernate
 - Thymeleaf
 - Junit
 - AssertJ

The idea of the app is to help people find friends to hang out with. App allows you to create meetings setting your favorite drinks, place and time or join other people's meetings.


### Launching 🔌

**There are two ways to run the project with different databases:**

#### - Firs way is the easy way using embedded H2 database

   1. Open project in IntelliJ IDEA and run it as spring boot app (using AlcomeetingApplication) or via command line from the project root: mvn spring-boot:run
   2. Go to the entry point: http://localhost:8080/thyme/main

 - URL for H2 Console: http://localhost:8080/h2
   - USERNAME - sa
   - PASSWORD - password
   
   The settings for this database are located by the path: src/main/resources/application.properties


#### - Second way is to use the PostgreSQL database

   
   1. Launch DB with default settings:
      - PORT - 5433
      - DATABASENAME - postgres
      - USERNAME - postgres
      - PASSWORD - password
      
      You can change the settings in the "application.properties" file by the path: src/main/resources/application.properties
   
   2. Open the project in intelliJ IDEA, comment out settings for the H2 database and uncomment for PostgreSQL in the
      “application.properties” file by the path: src/main/resources/application.properties and after that run it 
      as spring boot app (using AlcomeetingApplication) or via command line from the project root: mvn spring-boot:run

   3. Go to the entry point: http://localhost:8080/thyme/main




