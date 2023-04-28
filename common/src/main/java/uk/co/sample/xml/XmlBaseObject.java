package uk.co.sample.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xmlBaseObject")
@XmlSeeAlso({
    AnnouncemenToTrainXmlMessage.class,
    SmsResponseXmlMessage.class
})
public class XmlBaseObject {


}
