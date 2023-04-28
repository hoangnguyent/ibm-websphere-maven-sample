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
@Table(name = "DEMO_ANNOUNCE_TEXT")
public class DemoAnnounceText implements Serializable {

    private static final long     serialVersionUID = 3498500582937988553L;

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "textId", column = @Column(name = "TEXT_ID", nullable = false)),
    })
    private DemoAnnounceTextPK    id;

    @Column(name = "SET_ID")
    private String                setId;

    @Column(name = "ANNOUNCE_TEXT")
    private String                announceText;

    @Column(name = "ANNOUNCE_LANG")
    private java.math.BigDecimal  announceLang;

    @Column(name = "SEQUENCE_NO")
    private java.math.BigDecimal  sequenceNo;

    @Column(name = "WAITFOR_TEXT_ID")
    private String                waitforTextId;

    @Version
    private long                  version;

    @ManyToOne
    @JoinColumn(name = "SET_ID", insertable = false, updatable = false)
    private DemoAnnounceTrain     train;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "TEXT_ID")
    private Set<DemoAnnounceInst> instances;

    // ***** constructor *****
    public DemoAnnounceText() {
        super();
    }

    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** call back method *****
    // ***** getter and setter *****
    public DemoAnnounceTextPK getId() {
        return id;
    }

    public void setId(DemoAnnounceTextPK id) {
        this.id = id;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public String getAnnounceText() {
        return announceText;
    }

    public void setAnnounceText(String announceText) {
        this.announceText = announceText;
    }

    public java.math.BigDecimal getAnnounceLang() {
        return announceLang;
    }

    public void setAnnounceLang(java.math.BigDecimal announceLang) {
        this.announceLang = announceLang;
    }

    public java.math.BigDecimal getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(java.math.BigDecimal sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getWaitforTextId() {
        return waitforTextId;
    }

    public void setWaitforTextId(String waitforTextId) {
        this.waitforTextId = waitforTextId;
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

    public Set<DemoAnnounceInst> getInstances() {
        return instances;
    }

    public void setInstances(Set<DemoAnnounceInst> instances) {
        this.instances = instances;
    }

}
