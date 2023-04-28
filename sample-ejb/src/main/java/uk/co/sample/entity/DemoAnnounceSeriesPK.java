package uk.co.sample.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * PK class for Entity Bean: DemoAnnounceSeries
 */
@Embeddable
public class DemoAnnounceSeriesPK implements Serializable {

    private static final long serialVersionUID = 8608936544969242774L;

    private String            seriesId;

    //***** constructor *****
    public DemoAnnounceSeriesPK() {
    }

    public DemoAnnounceSeriesPK(String seriesId) {
        this.seriesId = seriesId;
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    @Override
    public boolean equals(java.lang.Object otherKey) {
        if (otherKey instanceof uk.co.sample.entity.DemoAnnounceSeriesPK) {
            uk.co.sample.entity.DemoAnnounceSeriesPK o = (uk.co.sample.entity.DemoAnnounceSeriesPK) otherKey;
            return (this.seriesId.equals(o.seriesId));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (seriesId.hashCode());
    }

    @Override
    public String toString() {
        return "["
            + uk.co.sample.constant.DBFields.DEMO_ANNOUNCE_SERIES.SERIES_ID + ":'" + seriesId + "']";

    }

    //***** getter and setter *****
    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String newseriesId) {
        seriesId = newseriesId;
    }

}
