package uk.co.sample.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "DEMO_TRAIN_IDX")
public class DemoTrainIdx implements Serializable {

    private static final long  serialVersionUID = 1458022574148231332L;

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "orgTrainId", column = @Column(name = "ORG_TRAIN_ID", nullable = false)),
    })
    private DemoTrainIdxPK     id;

    @Column(name = "SCHEDULE_UID")
    private String             schedulerUid;

    @Column(name = "ORG_TRUST_ID")
    private String             orgTrustId;

    @Column(name = "TRAIN_TERMINATED")
    private String             trainTerminated;

    @Column(name = "DAY_OF_RUN")
    private java.sql.Timestamp dayOfRun;

    @Version
    private long               version;

    // ***** constructor *****
    public DemoTrainIdx() {
        super();
    }

    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** call back method *****
    // ***** getter and setter *****
    public DemoTrainIdxPK getId() {
        return id;
    }

    public void setId(DemoTrainIdxPK id) {
        this.id = id;
    }

    public String getSchedulerUid() {
        return schedulerUid;
    }

    public void setSchedulerUid(String schedulerUid) {
        this.schedulerUid = schedulerUid;
    }

    public String getOrgTrustId() {
        return orgTrustId;
    }

    public void setOrgTrustId(String orgTrustId) {
        this.orgTrustId = orgTrustId;
    }

    public String getTrainTerminated() {
        return trainTerminated;
    }

    public void setTrainTerminated(String trainTerminated) {
        this.trainTerminated = trainTerminated;
    }

    public java.sql.Timestamp getDayOfRun() {
        return dayOfRun;
    }

    public void setDayOfRun(java.sql.Timestamp dayOfRun) {
        this.dayOfRun = dayOfRun;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

}
