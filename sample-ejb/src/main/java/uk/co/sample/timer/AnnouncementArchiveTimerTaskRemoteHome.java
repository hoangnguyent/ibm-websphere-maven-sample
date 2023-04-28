package uk.co.sample.timer;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.ibm.websphere.scheduler.TaskHandler;
import com.ibm.websphere.scheduler.TaskHandlerHome;

public interface AnnouncementArchiveTimerTaskRemoteHome extends TaskHandlerHome {

    TaskHandler create() throws CreateException, RemoteException;
}
