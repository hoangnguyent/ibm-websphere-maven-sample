package uk.co.sample.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "DEMO_ANNOUNCE_INST")
public class DemoAnnounceInst implements Serializable {

    private static final long    serialVersionUID = 7164914834905071906L;

    @EmbeddedId
    private DemoAnnounceInstPK   id;

    @Column(name = "SET_ID")
    private String               setId;

    @Column(name = "REQUEST_DATETIME")
    private java.sql.Timestamp   requestDatetime;

    @Column(name = "PLAY_DATETIME")
    private java.sql.Timestamp   playDatetime;

    @Column(name = "STATUS")
    private String               status;

    @Column(name = "FAILURE_CODE")
    private java.math.BigDecimal failureCode;

    @Column(name = "FAILURE_DESC")
    private String               failureDesc;

    @Version
    private long                 version;

    @ManyToOne
    @JoinColumn(name = "TEXT_ID", insertable = false, updatable = false)
    private DemoAnnounceText     text;

    @ManyToOne
    @JoinColumn(name = "SET_ID", insertable = false, updatable = false)
    private DemoAnnounceTrain    train;

    // ***** constructor *****
    public DemoAnnounceInst() {
        super();
    }

    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** call back method *****
    // ***** getter and setter *****
    public DemoAnnounceInstPK getId() {
        return id;
    }

    public void setId(DemoAnnounceInstPK id) {
        this.id = id;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public java.sql.Timestamp getRequestDatetime() {
        return requestDatetime;
    }

    public void setRequestDatetime(java.sql.Timestamp requestDatetime) {
        this.requestDatetime = requestDatetime;
    }

    public java.sql.Timestamp getPlayDatetime() {
        return playDatetime;
    }

    public void setPlayDatetime(java.sql.Timestamp playDatetime) {
        this.playDatetime = playDatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.math.BigDecimal getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(java.math.BigDecimal failureCode) {
        this.failureCode = failureCode;
    }

    public String getFailureDesc() {
        return failureDesc;
    }

    public void setFailureDesc(String failureDesc) {
        this.failureDesc = failureDesc;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public DemoAnnounceTrain getTrain() {
        return train;
    }

    public void setTrain(DemoAnnounceTrain train) {
        this.train = train;
    }

    public DemoAnnounceText getText() {
        return text;
    }

    public void setText(DemoAnnounceText text) {
        this.text = text;
    }

}
