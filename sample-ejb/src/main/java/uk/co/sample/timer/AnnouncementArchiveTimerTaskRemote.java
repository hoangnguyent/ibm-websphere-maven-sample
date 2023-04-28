package uk.co.sample.timer;

import com.ibm.websphere.scheduler.TaskStatus;

public interface AnnouncementArchiveTimerTaskRemote {

    public void process(TaskStatus taskStatus);
}
