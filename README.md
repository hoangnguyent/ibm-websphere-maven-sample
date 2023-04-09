# ibm-websphere-maven-sample
## Introduction 
This is a sample project for J2EE using the Maven build tool.

**Technique stack:**
```sh
IBM Websphere Application Server 9.0.5.7
IBM MQ server (for developer) 9.2.5
IBM scheduler
Rational Application Developer 9.0.1.4
Oracle database
Enterprise JavaBeans 3.1
Open JPA 2.0
OpenAPI (Swagger) 1.5.4
Both RESTful and SOAP web service
```

## Getting Started
1. First, we must locate the file was_public.jar in the IBM root folder. We will see a file pom nearby. Rename that pom to be: [was_public-9.0.0.pom].

After that, run the following cmd to register those files to the local Maven repository:
```sh
mvn install:install-file -Dfile="C:\IBM96\WebSphere\AppServer\dev\was_public.jar" -DpomFile="C:\IBM96\WebSphere\AppServer\dev\was_public-9.0.0.pom"
```

Now we already had this dependency:
```sh
<dependency>
    <groupId>com.ibm.websphere.appserver</groupId>
    <artifactId>was_public</artifactId>
    <version>9.0.0</version>
</dependency>
```

2. We may need the following dependency later on:
```sh
<dependency>
    <groupId>javax</groupId>
    <artifactId>javaee-api</artifactId>
    <version>7.0</version>
    <scope>provided</scope>
</dependency>
```

## Build and Test
3. Before importing code to Eclipse, make sure to go into the Maven root project and run the following commands:
```sh
mvn install
mvn eclipse:eclipse (for Eclipse or Rational Application Developer using)
```

4. Before importing EAR to the server, ensure [ae-EAR/src/main/application] contains all modules.

If it doesn't, go into the Maven root project and run the command again:
```sh
mvn install
```

5. The Maven plugin on Eclipse may show an error: "Plugin execution not covered by lifecycle configuration". Just go to:
```sh
Window > Preferences > Maven > Errors/Warnings > Plugin execution not covered by lifecycle configuration.
```
Then change the value from "Error" to "Ignore".

6. Remember to "restore" the IBM Websphere Application Server configuration with the /file/dev.car before importing any application to the Server.

Otherwise, you have to configure everything manually.

After finishing, start the Server without any application.

7. When the server is started, open Administrative Console and remember to check:
```sh
WORKSPACE_ROOT environment value is correct.
Resources/JDBC: all data sources are connected.
Resources/JMS: all Queue connection factories have the correct urls and authentication.
Resources/Schedulers: create tables for all Schedulers.
```

8. Import our application and restart the Server. It should be started successfully.

9. On the browser, Swagger API for testing is:
```sh
http://<your_host>:<your_port>/ae-webservice/docs/
http://localhost:9080/ae-webservice/docs/
```

10. Put a message to the queue ANNOUNCEMENTTOTRAINQ to see if it is processed.
Data will be inserted into table ANNOUNCEMENT_SAMPLE.

## Contribute
