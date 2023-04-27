# Introduction 
This is a sample project for J2EE using the Maven build tool.

The logic is simple. When a Train is activated, there is a event message sent to ACTIVATE queue. The application will take the message, and store a record in the database. After a period of time, a Scheduler Job will process this record to change its status and forward the message to another queue. Finally, another Scheduler Job will logically delete the record.

**Technique stack:**

```sh
IBM Websphere Application Server Traditional 9.0.5.7
IBM MQ server (for developer) 9.2.5
IBM Scheduler
Rational Application Developer 9.0.1.4
Oracle database
Enterprise JavaBeans 3.1
Open JPA 2.0
OpenAPI (Swagger) 1.5.4
Both RESTful and SOAP web services
```

# Getting Started
## 1. Required Softwares.

In order to start the application, you must have all the Softwares that have been listed above.
The DDL for the table's schema is placed in the DDL file.

## 2. Server configuration

You must create IBM Websphere Application Server before configuring.

I have provided a sample configuration in the /file/dev.car. You can restore it using Rational Application Developer or Command Line or configure everything yourself.

**Windows Command Line:**

```sh
C:/IBM/WebSphere/AppServer/profiles/sample_profile/bin/wsadmin.bat -lang jython -username wsadmin -password your_password sample_profile -conntype NONE

AdminTask.importWasprofile(['-archive', 'C:/workspaces/ibm_maven_sample/dev.car']),AdminConfig.save()

exit
```

**Linux Command Line:**

```sh
/opt/IBM/WebSphere/AppServer/profiles/sample_profile/bin/wsadmin.sh -lang jython -username wsadmin -password your_password sample_profile -conntype NONE

AdminTask.importWasprofile(['-archive', '/tmp/workspaces/ibm_maven_sample/dev.car']),AdminConfig.save()

exit
```

## 3. Start server at the first time

Start the Server without any application.

When the server is started, open Administrative Console and remember to check:

```sh
WORKSPACE_ROOT environment value is correct.
Resources/JDBC: all data sources are connected.
Resources/JMS: all Queue connection factories have the correct urls and authentication.
Resources/Schedulers: create tables for all Schedulers.
```

Restart the server.

## 4. Maven setting

- **Register IBM dependency to the Local Maven Repository.**

You will see the file was_public.jar in the IBM root folder as well as a file pom nearby. Rename that pom to be: [was_public-9.0.0.pom].
After that, run the following cmd to register those files to the local Maven repository:

```sh
mvn install:install-file -Dfile="C:/IBM/WebSphere/AppServer/dev/was_public.jar" -DpomFile="C:/IBM/WebSphere/AppServer/dev/was_public-9.0.0.pom"
```

The dependency is available to use.

```sh
<dependency>
    <groupId>com.ibm.websphere.appserver</groupId>
    <artifactId>was_public</artifactId>
    <version>9.0.0</version>
</dependency>
```

- **Prepare the source code**

Download the source code. It contains several modules. At the root module, run the following commands:

```sh
mvn install
mvn eclipse:eclipse (for Eclipse or Rational Application Developer using)
```

- **Import the source code to your IDE**

Import source code to the Rational Application Developer or Eclipse. Ensure [sample-EAR/src/main/application] contains all sub-modules that are packaged.

If it doesn't, go into the Maven root project and re-run the command:

```sh
mvn install
```

The Maven plugin on the Rational Application Developer or Eclipse may show an error: "Plugin execution not covered by lifecycle configuration". Just go to:

```sh
Window > Preferences > Maven > Errors/Warnings > Plugin execution not covered by lifecycle configuration.

```
Then change the value from "Error" to "Ignore".


# Build and Test
## 5. Add our application to the Server and restart it. It should be successful.

## 6. Verify Swagger API on the browser:

```sh
http://<your_host>:<your_port>/sample-webservice/docs/
http://localhost:9080/sample-webservice/docs/
```

## 7. Verify queue messages are consumed and processed:
Put a message to the queue TRAIN_ACTIVATION_EVENT_Q.
It will be pull to processed. One record will be inserted into table ANNOUNCEMENT_SAMPLE.
The status of this record will be changed after a period of time(60s) by the IBM Schedulers.

## 8. Verify log files
There are 3 log files will be created. Find their location in the DEV.properties.

# Common issues and how to fix
## 1. The application started failed
There are errors related to Listening Messages. That is because we have not set up the Queue server correctly or the Queues do not exist.

You should correct all Queues setting. Or you can go into Administrative Console and disable all Listeners (not recommended).

After that, restart the Server.

## 2. The application started failed
There is an NPE in the Console log about the properties file. The reason may be [sample-EAR/src/main/application] does not contain all sub-modules that are packaged.

You should re-run Maven install command and refresh the EAR module. After that, re-publish the application.

## 3. The application started failed
There is no useful information in the Console log. It usually relates to the WORKSPACE_ROOT environment value being incorrect. 

You should check, re-enter it and restart the Server.

## 4. The application started successfully. But the Swagger API on the browser does not work
There is an error related to "resources not found" or javax.servlet.ServletException: Coult not find endpoint information. There is a misspelling at the word "Could", so weird!

This may relate to the version of the IBM Websphere Application Server Traditional. This source code is built for IBM Websphere Application Server Traditional version 9.0.5.7, which the JAX-RS provider 1.1 is available. However, from WAS version 9.0.5.11, JAX-RS provider 1.1 does not work anymore but 2.0.

You should go into Administration Console/Servers/Server Type/WebSphere application servers/server1. Expand [Container Services] and select Default JAXRS provider settings.

Set the correct value: 1.1 for WAS 9.0.5.11 and the older; 2.0 for the newer. Save the setting and restart the Server.

Refer: https://www.ibm.com/docs/en/siffs/2.0.0?topic=troubleshooting-javaxservletservletexceptioncould-not-find-endpoint-information

# Contribute
Hoang Nguyen. Skype: hoang.nt124
