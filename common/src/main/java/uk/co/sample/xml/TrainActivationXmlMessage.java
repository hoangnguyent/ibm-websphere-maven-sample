package uk.co.sample.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import uk.co.sample.util.XmlUtil;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trainActivationXmlMessage", propOrder = {
    "trainId",
    "location",
})
@XmlRootElement(name = "TrainActivationXmlMessage")
public class TrainActivationXmlMessage extends XmlBaseObject {

    @XmlElement(nillable = false)
    protected String trainId;

    @XmlElement(nillable = true)
    protected String location;
 
    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static void main(String[] args) {
        TrainActivationXmlMessage ojectInXmlFormat = new TrainActivationXmlMessage();
        ojectInXmlFormat.setTrainId("dummyTrainId");
        ojectInXmlFormat.setLocation("Somewhere over the rainbow");

        System.out.println(XmlUtil.marshalXmlObjectToString(ojectInXmlFormat));
    }
}
