package uk.co.sample.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import uk.co.sample.constant.Status;
import uk.co.sample.util.XmlUtil;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "smsResponseXmlMessage", propOrder = {
    "announcementId",
    "trainId",
    "status",
    "failureReasonCode",
    "failureReasonDescription"
})
@XmlRootElement(name = "SmsResponseXmlMessage")
public class SmsResponseXmlMessage extends XmlBaseObject {

    @XmlElement(nillable = false)
    protected String announcementId;

    @XmlElement(nillable = false)
    protected String trainId;
    
    @XmlElement(nillable = false)
    protected Status status;

    @XmlElement(nillable = true)
    protected String failureReasonCode;
 
    @XmlElement(nillable = true)
    protected String failureReasonDescription;

    public String getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getFailureReasonCode() {
        return failureReasonCode;
    }

    public void setFailureReasonCode(String failureReasonCode) {
        this.failureReasonCode = failureReasonCode;
    }

    public String getFailureReasonDescription() {
        return failureReasonDescription;
    }

    public void setFailureReasonDescription(String failureReasonDescription) {
        this.failureReasonDescription = failureReasonDescription;
    }

    public static void main(String[] args) {
        SmsResponseXmlMessage ojectInXmlFormat = new SmsResponseXmlMessage();
        ojectInXmlFormat.setAnnouncementId("0000010");
        ojectInXmlFormat.setTrainId("dummyTrainId");
        ojectInXmlFormat.setStatus(Status.FAILED);
        ojectInXmlFormat.setFailureReasonCode("dummyFailedCode");
        ojectInXmlFormat.setFailureReasonDescription("For test");

        System.out.println(XmlUtil.marshalXmlObjectToString(ojectInXmlFormat));
        //<?xml version="1.0" encoding="UTF-8" standalone="yes"?><SmsResponseXmlMessage><announcementId>0000010</announcementId><trainId>dummyTrainId</trainId><status>FAILED</status><failureReasonCode>dummyFailedCode</failureReasonCode><failureReasonDescription>For test</failureReasonDescription></SmsResponseXmlMessage>
    }
}
