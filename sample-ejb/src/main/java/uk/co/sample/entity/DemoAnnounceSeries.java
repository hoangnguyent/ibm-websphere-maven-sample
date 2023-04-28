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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "DEMO_ANNOUNCE_SERIES")
public class DemoAnnounceSeries implements Serializable {

    private static final long          serialVersionUID = 2355986143957985139L;

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "seriesId", column = @Column(name = "SERIES_ID", nullable = false)),
    })
    private DemoAnnounceSeriesPK       id;

    @Column(name = "UI_REST_RESPONSE_URL")
    private String                     uiRestResponseUrl;

    @Column(name = "UI_REST_TOKEN")
    private String                     uiRestToken;

    @Column(name = "SUBMIT_DATETIME")
    private java.sql.Timestamp         submitDatetime;

    @Column(name = "CALLER_REF")
    private String                     callerRef;

    @Version
    private long                       version;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SERIES_ID")
    private Set<DemoAnnounceTrain>     trains;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SERIES_ID")
    private Set<DemoAnnounceSeriesToc> tocs;

    // ***** constructor *****
    public DemoAnnounceSeries() {
        super();
    }

    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** call back method *****
    // ***** getter and setter *****
    public DemoAnnounceSeriesPK getId() {
        return id;
    }

    public void setId(DemoAnnounceSeriesPK id) {
        this.id = id;
    }

    public String getUiRestResponseUrl() {
        return uiRestResponseUrl;
    }

    public void setUiRestResponseUrl(String uiRestResponseUrl) {
        this.uiRestResponseUrl = uiRestResponseUrl;
    }

    public String getUiRestToken() {
        return uiRestToken;
    }

    public void setUiRestToken(String uiRestToken) {
        this.uiRestToken = uiRestToken;
    }

    public java.sql.Timestamp getSubmitDatetime() {
        return submitDatetime;
    }

    public void setSubmitDatetime(java.sql.Timestamp submitDatetime) {
        this.submitDatetime = submitDatetime;
    }

    public String getCallerRef() {
        return callerRef;
    }

    public void setCallerRef(String callerRef) {
        this.callerRef = callerRef;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Set<DemoAnnounceTrain> getTrains() {
        return trains;
    }

    public void setTrains(Set<DemoAnnounceTrain> trains) {
        this.trains = trains;
    }

    public Set<DemoAnnounceSeriesToc> getTocs() {
        return tocs;
    }

    public void setTocs(Set<DemoAnnounceSeriesToc> tocs) {
        this.tocs = tocs;
    }

}
