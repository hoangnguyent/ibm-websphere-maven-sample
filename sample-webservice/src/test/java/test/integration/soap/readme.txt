***********************************************
Steps to create and generate SOAP JAX-WS web service
***********************************************
2021/07/24 by HoangNguyen

A. Produce WebService
1. Implement IfSoapWebService.java and AeSoapWebService.java.
Write some TC in TestWebService.java (optional)

2. Delete the folder [sample-webservice/WebContent/WEB-INF/wsdl] if exits
3. Open AeSoapWebService.java, press Ctrl + N, then type "web service" in the textbox.
4. Select [Web Service] and click [Next].
5. AeSoapWebService.java has been opened before, so you don't need to input or tick anything.
Just verify [Service implementation] is AeSoapWebService.java
and scroll the left bar down to [Deploy].

6. Click [Next].
7. The checkbox [Generate WSDL file into the project] is optional.
8. Click [Next] button. [Finish] is also okay.
9. Restart Server. Run TestWebService.java to verify new API has been working.

10. Common issues:
10.1
    Error when generate WSDL: Caused by: java.lang.NoClassDefFoundError: com/sun/tools/apt/Main
    at com.ibm.jtc.jax.tools.ws.wscompile.WsgenTool.buildModel(WsgenTool.java:223)
    The reason is not using JKD version 1.7
10.2
    Error when starting RAD: [Publishing failed. Error in delegate. java.lang.NullPointerException].
    The issue may relate to the option [Never publish automatically] on Server. It happens after generating WS.
    Just remove all:
    [.snap] file in /yourworkspace/.metadata/.plugins/org.eclipse.core.resources/
    [tmp*] folder in /yourworkspace/.metadata/.plugins/org.eclipse.wst.server.core/
    Then Clean the server.


B. Consume WebService
1. Verify source and destination
- Verify the WSDL source file is accessible: http://localhost:9080/sample-webservice/AE/AE.wsdl
- Verify the destination to generate Client code is in: [src/test/java] and package [src/test/java]

2. Generate code of Client from WSDL file. You can use the visual tool on Eclipse or use CMD like the following:
- Go to [src/test/java] folder and open CMD then run command:
<JAVA_HOME>/bin/wsimport -keep -p <your package name> "http://localhost:9080/sample-webservice/AE/AE.wsdl"
Example:
C:/IBM/WebSphere/AppServer/java/8.0/bin/wsimport -keep -p test.integration.soap "http://localhost:9080/sample-webservice/AE/AE.wsdl"

3. Sample code to connect to the WS:
IfSoapWebService ws = (new AE()).getPort(IfSoapWebService.class);

4. Sample code to call the WS endpoint:
wsReturn = ws.callSomething(wsRequest);

5. Note:
- If code is red, don't worry.
    Check if the code files are in [.apt_generated] folder, then remove the whole folder.
    Everything should be fine.

- It might take 40 seconds for the first time call. Please be patient.
- Should check AE.java and *Ws*ReturnDTO.java in [src/test/java].
    They may need to revert several lines of code before commit.

DONE