package uk.co.sample.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * PK class for Entity Bean: DemoAnnounceText
 */
@Embeddable
public class DemoAnnounceTextPK implements Serializable {

    private static final long serialVersionUID = 8661900425896695069L;

    private String            textId;

    //***** constructor *****
    public DemoAnnounceTextPK() {
    }

    public DemoAnnounceTextPK(String textId) {
        this.textId = textId;
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    @Override
    public boolean equals(java.lang.Object otherKey) {
        if (otherKey instanceof uk.co.sample.entity.DemoAnnounceTextPK) {
            uk.co.sample.entity.DemoAnnounceTextPK o = (uk.co.sample.entity.DemoAnnounceTextPK) otherKey;
            return (this.textId.equals(o.textId));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (textId.hashCode());
    }

    @Override
    public String toString() {
        return "["
            + uk.co.sample.constant.DBFields.DEMO_ANNOUNCE_TEXT.TEXT_ID + ":'" + textId + "']";

    }

    //***** getter and setter *****
    public String getTextId() {
        return textId;
    }

    public void setTextId(String newtextId) {
        textId = newtextId;
    }

}
