If this is the first time using com.ibm.websphere.scheduler.Scheduler, this note might be helpful.

2022/05/25 by HoangNguyen
1. So far, I found several guides for setup and configure Scheduler on IBM RAD with help of Oracle DB:
https://www.ibm.com/docs/en/was/8.5.5?topic=extensions-example-using-scripting-create-configure-schedulers
https://seenukarthi.com/websphere/2010/05/31/sheduling-job-in-websphere-appserver-6/

However there are seems no documents or visual tools to start, stop or manipulate those Schedule Tasks.
We must create a Screen for this if required.

2. A sample Scheduler Task has been configured in Application Console, under scope [HN-SD-0680-WKNode19Cell\HN-SD-0680-WKNode19\server1]
When you do this, should remember the information. It will be used later.

3. Sometimes, the Task is woke up, but does not run at all!
You will see a Console log message like this:
SCHD0133I: Scheduler AnnouncementArchiveTimer (sched/AnnouncementArchiveTimer) has lost its lease and will no longer run tasks on this application server.  Tasks will now run on server hostNode04Cell\hostNode04\server1.

The reason may be:
- Several developer machines that start and take control on the same record in the table _LMGR
- Task is created and stored in the table _LMGR. But it will never be released automatically. Whenever starting Server, an new instance will be added more!
For more details, refer: https://www.ibm.com/support/pages/schd0134i-scheduler-has-lost-its-lease-and-will-no-longer-run-tasks-application-server

In this case, there are two way to resolve
a) just go to Database, and set _LMGR.LEASEOWNER = 'HN-SD-0680-WKNode19Cell\HN-SD-0680-WKNode19\server1' (your server information)
After that, you will see a Console log message like this:
SCHD0133I: Scheduler AnnouncementArchiveTimer (sched/AnnouncementArchiveTimer) has acquired the lease and will run all tasks on this application server.
Then the Task will run on the next interval without restarting

b) You can also go to Administrative Console/Resources/Schedulers, drop tables and re-create them.
Then the Task will run on the next interval without restarting

4. For that reason, I have created a tool to wake up and release tasks automatically. See ScheduleRunner.java
You need to do step 3 only when the first time create/configure/re-configure the Scheduler/Timer. 
Should recheck this carefully when Scaling!!!