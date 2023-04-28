package uk.co.sample.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PK class for Entity Bean: DemoAnnounceInst
 */
@Embeddable
public class DemoAnnounceInstPK implements Serializable {

    private static final long serialVersionUID = -5538102250456254291L;

    @Column(name = "TEXT_ID")
    private String            textId;
    @Column(name = "INSTANT_NO")
    private String            instantNo;

    //***** constructor *****
    public DemoAnnounceInstPK() {
    }

    public DemoAnnounceInstPK(String textId, String instantNo) {
        this.textId = textId;
        this.instantNo = instantNo;
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    @Override
    public boolean equals(java.lang.Object otherKey) {
        if (otherKey instanceof uk.co.sample.entity.DemoAnnounceInstPK) {
            uk.co.sample.entity.DemoAnnounceInstPK o = (uk.co.sample.entity.DemoAnnounceInstPK) otherKey;
            return (this.textId.equals(o.textId) && this.instantNo.equals(o.instantNo));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (textId.hashCode() + instantNo.hashCode());
    }

    @Override
    public String toString() {
        return "["
            + uk.co.sample.constant.DBFields.DEMO_ANNOUNCE_INST.TEXT_ID + ":'" + textId + "', "
            + uk.co.sample.constant.DBFields.DEMO_ANNOUNCE_INST.INSTANT_NO + ":'" + instantNo + "']";

    }

    //***** getter and setter *****
    public String getTextId() {
        return textId;
    }

    public void setTextId(String newtextId) {
        textId = newtextId;
    }

    public String getInstantNo() {
        return instantNo;
    }

    public void setInstantNo(String newinstantNo) {
        instantNo = newinstantNo;
    }

}
