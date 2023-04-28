package uk.co.sample.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * PK class for Entity Bean: DemoAnnounceTrain
 */
@Embeddable
public class DemoAnnounceTrainPK implements Serializable {

    private static final long serialVersionUID = 4781438546353314663L;

    private String            setId;

    //***** constructor *****
    public DemoAnnounceTrainPK() {
    }

    public DemoAnnounceTrainPK(String setId) {
        this.setId = setId;
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    @Override
    public boolean equals(java.lang.Object otherKey) {
        if (otherKey instanceof uk.co.sample.entity.DemoAnnounceTrainPK) {
            uk.co.sample.entity.DemoAnnounceTrainPK o = (uk.co.sample.entity.DemoAnnounceTrainPK) otherKey;
            return (this.setId.equals(o.setId));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (setId.hashCode());
    }

    @Override
    public String toString() {
        return "["
            + uk.co.sample.constant.DBFields.DEMO_ANNOUNCE_TRAINS.SET_ID + ":'" + setId + "']";

    }

    //***** getter and setter *****
    public String getSetId() {
        return setId;
    }

    public void setSetId(String newsetId) {
        setId = newsetId;
    }

}
