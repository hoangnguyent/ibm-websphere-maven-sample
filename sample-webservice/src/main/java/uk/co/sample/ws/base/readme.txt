2022/05/31 by HoangNguyen

1. To enable RESFful in JEE, I have followed this guide:
https://www.ibm.com/docs/en/was-nd/9.0.5?topic=applications-getting-started-jax-rs

2. At the scope of this sample, using WAS version 9.0.5.7, the IBMRestServlet supports JAXRS provider version 1.1 only.
    Go to Administrative Console, Servers/Server Types/WebSphere Application servers/Container Services/Default JAXRS provider settings. And select 1.1.
    Otherwise, you may get the warning: javax.servlet.ServletException: Coult not find endpoint information.
    It is misspelling the word "could", so weird!
    Refer to this link: https://www.ibm.com/docs/en/siffs/2.0.0?topic=troubleshooting-javaxservletservletexceptioncould-not-find-endpoint-information
    I have put this in the .car file for DEV.

    2023/04/25: I have just received issue "resources not found" for the APIs from the Client.
    After changing JAXRS provider back to 2.0, it works on the Client machine with WAS version 9.0.5.17.
    So you must set the correct version according to the IBM patch and mention it in the Deployment Guide for the Client.

3. Swagger: http://localhost:9080/sample-webservice/docs/
DONE