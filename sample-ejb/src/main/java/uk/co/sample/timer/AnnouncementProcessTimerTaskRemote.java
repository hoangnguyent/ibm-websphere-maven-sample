package uk.co.sample.timer;

import com.ibm.websphere.scheduler.TaskStatus;

public interface AnnouncementProcessTimerTaskRemote {

    public void process(TaskStatus taskStatus);
}
