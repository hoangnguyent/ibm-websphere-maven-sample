package uk.co.sample.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * PK class for Entity Bean: DemoTrainIdx
 */
@Embeddable
public class DemoTrainIdxPK implements Serializable {

    private static final long serialVersionUID = -3060462533534037472L;

    private String            orgTrainId;

    //***** constructor *****
    public DemoTrainIdxPK() {
    }

    public DemoTrainIdxPK(String orgTrainId) {
        this.orgTrainId = orgTrainId;
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    @Override
    public boolean equals(java.lang.Object otherKey) {
        if (otherKey instanceof uk.co.sample.entity.DemoTrainIdxPK) {
            uk.co.sample.entity.DemoTrainIdxPK o = (uk.co.sample.entity.DemoTrainIdxPK) otherKey;
            return (this.orgTrainId.equals(o.orgTrainId));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (orgTrainId.hashCode());
    }

    @Override
    public String toString() {
        return "["
            + uk.co.sample.constant.DBFields.DEMO_ANNOUNCE_TRAINS.SET_ID + ":'" + orgTrainId + "']";

    }

    //***** getter and setter *****
    public String getOrgTrainId() {
        return orgTrainId;
    }

    public void setOrgTrainId(String orgTrainId) {
        this.orgTrainId = orgTrainId;
    }

}
