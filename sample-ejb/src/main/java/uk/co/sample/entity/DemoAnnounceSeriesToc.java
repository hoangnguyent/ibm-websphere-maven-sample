package uk.co.sample.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "DEMO_ANNOUNCE_SERIES_TOC")
public class DemoAnnounceSeriesToc implements Serializable {

    private static final long       serialVersionUID = -2512527692443478401L;

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "tocrefsid", column = @Column(name = "TOCREFSID", nullable = false)),
    })
    private DemoAnnounceSeriesTocPK id;

    @Column(name = "SERIES_ID")
    private String                  seriesId;

    @Column(name = "TOC")
    private String                  toc;

    @Version
    private long                    version;

    @ManyToOne
    @JoinColumn(name = "SERIES_ID", insertable = false, updatable = false)
    private DemoAnnounceSeries      series;

    // ***** constructor *****
    public DemoAnnounceSeriesToc() {
        super();
    }

    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** call back method *****
    // ***** getter and setter *****
    public DemoAnnounceSeriesTocPK getId() {
        return id;
    }

    public void setId(DemoAnnounceSeriesTocPK id) {
        this.id = id;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getToc() {
        return toc;
    }

    public void setToc(String toc) {
        this.toc = toc;
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

}
