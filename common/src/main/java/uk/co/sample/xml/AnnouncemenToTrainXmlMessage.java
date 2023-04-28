package uk.co.sample.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xmlAnnouncemenToTrainMessage", propOrder = {
    "announcement"
})
@XmlRootElement(name = "XmlAnnouncemenToTrainMessage")
public class AnnouncemenToTrainXmlMessage
    extends XmlBaseObject
{

    @XmlElement(nillable = false)
    protected String announcement;

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }


}
