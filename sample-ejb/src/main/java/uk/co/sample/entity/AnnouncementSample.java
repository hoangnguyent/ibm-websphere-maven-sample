package uk.co.sample.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "ANNOUNCEMENT_SAMPLE")
public class AnnouncementSample implements Serializable {

    private static final long  serialVersionUID = -8519509121585239465L;

    /**
     * Implementation field for persistent attribute: announcementSampleKey
     */
    @Id
    @Column(name = "ANNOUNCEMENT_SAMPLE_KEY")
    private String             announcementSampleKey;

    @Column(name = "REG_DATE")
    private java.sql.Timestamp regDate;

    @Column(name = "MOD_DATE")
    private java.sql.Timestamp modDate;

    @Column(name = "STATUS")
    private String             status;

    @Column(name = "TEXT")
    private String             text;

    @Version
    private long               version;

    //***** constructor *****
    /**
     * Constructor.<br>
     */
    public AnnouncementSample() {
        super();
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****
    public String getAnnouncementSampleKey() {
        return announcementSampleKey;
    }

    public void setAnnouncementSampleKey(String announcementSampleKey) {
        this.announcementSampleKey = announcementSampleKey;
    }

    public java.sql.Timestamp getRegDate() {
        return regDate;
    }

    public void setRegDate(java.sql.Timestamp regDate) {
        this.regDate = regDate;
    }

    public java.sql.Timestamp getModDate() {
        return modDate;
    }

    public void setModDate(java.sql.Timestamp modDate) {
        this.modDate = modDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

}
