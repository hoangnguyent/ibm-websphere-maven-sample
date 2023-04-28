package uk.co.sample.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * PK class for Entity Bean: DemoAnnounceSeriesToc
 */
@Embeddable
public class DemoAnnounceSeriesTocPK implements Serializable {

    private static final long serialVersionUID = 5354512996250303337L;

    private String            tocrefsid;

    //***** constructor *****
    public DemoAnnounceSeriesTocPK() {
    }

    public DemoAnnounceSeriesTocPK(String tocrefsid) {
        this.tocrefsid = tocrefsid;
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    @Override
    public boolean equals(java.lang.Object otherKey) {
        if (otherKey instanceof uk.co.sample.entity.DemoAnnounceSeriesTocPK) {
            uk.co.sample.entity.DemoAnnounceSeriesTocPK o = (uk.co.sample.entity.DemoAnnounceSeriesTocPK) otherKey;
            return (this.tocrefsid.equals(o.tocrefsid));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (tocrefsid.hashCode());
    }

    @Override
    public String toString() {
        return "["
            + uk.co.sample.constant.DBFields.DEMO_ANNOUNCE_SERIES_TOC.TOCREFSID + ":'" + tocrefsid + "']";

    }

    //***** getter and setter *****
    public String getTocrefsid() {
        return tocrefsid;
    }

    public void setTocrefsid(String newtocrefsid) {
        tocrefsid = newtocrefsid;
    }

}
