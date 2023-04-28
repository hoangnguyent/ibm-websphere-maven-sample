package uk.co.sample.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "DEMO_ANNOUNCE_TRAINS")
public class DemoAnnounceTrain implements Serializable {

    private static final long     serialVersionUID = -8454057605880628611L;

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "setId", column = @Column(name = "SET_ID", nullable = false)),
    })
    private DemoAnnounceTrainPK   id;

    @Column(name = "SERIES_ID")
    private String                seriesId;

    @Column(name = "SCHEDULE_UID")
    private String                scheduleUid;

    @Column(name = "DAY_OF_RUN")
    private java.sql.Timestamp    dayOfRun;

    @Column(name = "TRUST_ID")
    private String                trustId;

    @Column(name = "TRIGGER_TYPE")
    private String                triggerType;

    @Column(name = "TRIGGER_LOCATION_TYPE")
    private String                triggerLocationType;

    @Column(name = "TRIGGER_LOCATION")
    private String                triggerLocation;

    @Column(name = "BASE_TRIGGER_TIME")
    private java.sql.Timestamp    baseTriggerTime;

    @Column(name = "NEXT_TRIGGER_TIME")
    private java.sql.Timestamp    nextTriggerTime;

    @Column(name = "REPEAT_SECONDS")
    private java.math.BigDecimal  repeatSeconds;

    @Column(name = "REPEAT_UNTIL")
    private java.sql.Timestamp    repeatUntil;

    @Column(name = "REPEAT_COUNT")
    private java.math.BigDecimal  repeatCount;

    @Column(name = "MSISDN_REF")
    private String                msisdnRef;

    @Column(name = "DELETE_STATUS")
    private String                deleteStatus;

    @Version
    private long                  version;

    @ManyToOne
    @JoinColumn(name = "SERIES_ID", insertable = false, updatable = false)
    private DemoAnnounceSeries    series;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SET_ID")
    private Set<DemoAnnounceText> texts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SET_ID")
    private Set<DemoAnnounceInst> instances;

    // ***** constructor *****
    public DemoAnnounceTrain() {
        super();
    }

    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** call back method *****
    // ***** getter and setter *****
    public DemoAnnounceTrainPK getId() {
        return id;
    }

    public void setId(DemoAnnounceTrainPK id) {
        this.id = id;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getScheduleUid() {
        return scheduleUid;
    }

    public void setScheduleUid(String scheduleUid) {
        this.scheduleUid = scheduleUid;
    }

    public java.sql.Timestamp getDayOfRun() {
        return dayOfRun;
    }

    public void setDayOfRun(java.sql.Timestamp dayOfRun) {
        this.dayOfRun = dayOfRun;
    }

    public String getTrustId() {
        return trustId;
    }

    public void setTrustId(String trustId) {
        this.trustId = trustId;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getTriggerLocationType() {
        return triggerLocationType;
    }

    public void setTriggerLocationType(String triggerLocationType) {
        this.triggerLocationType = triggerLocationType;
    }

    public String getTriggerLocation() {
        return triggerLocation;
    }

    public void setTriggerLocation(String triggerLocation) {
        this.triggerLocation = triggerLocation;
    }

    public java.sql.Timestamp getBaseTriggerTime() {
        return baseTriggerTime;
    }

    public void setBaseTriggerTime(java.sql.Timestamp baseTriggerTime) {
        this.baseTriggerTime = baseTriggerTime;
    }

    public java.sql.Timestamp getNextTriggerTime() {
        return nextTriggerTime;
    }

    public void setNextTriggerTime(java.sql.Timestamp nextTriggerTime) {
        this.nextTriggerTime = nextTriggerTime;
    }

    public java.math.BigDecimal getRepeatSeconds() {
        return repeatSeconds;
    }

    public void setRepeatSeconds(java.math.BigDecimal repeatSeconds) {
        this.repeatSeconds = repeatSeconds;
    }

    public java.sql.Timestamp getRepeatUntil() {
        return repeatUntil;
    }

    public void setRepeatUntil(java.sql.Timestamp repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public java.math.BigDecimal getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(java.math.BigDecimal repeatCount) {
        this.repeatCount = repeatCount;
    }

    public String getMsisdnRef() {
        return msisdnRef;
    }

    public void setMsisdnRef(String msisdnRef) {
        this.msisdnRef = msisdnRef;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public DemoAnnounceSeries getSeries() {
        return series;
    }

    public void setSeries(DemoAnnounceSeries series) {
        this.series = series;
    }

    public Set<DemoAnnounceText> getTexts() {
        return texts;
    }

    public void setTexts(Set<DemoAnnounceText> texts) {
        this.texts = texts;
    }

    public Set<DemoAnnounceInst> getInstances() {
        return instances;
    }

    public void setInstances(Set<DemoAnnounceInst> instances) {
        this.instances = instances;
    }

}
