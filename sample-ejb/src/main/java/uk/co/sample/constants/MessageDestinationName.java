package uk.co.sample.constants;

public enum MessageDestinationName {
    ANN_SEND_TO_TRAIN_Q("ANN_SEND_TO_TRAIN_Q"),
    RESPONSE_AFTER_PLAYING_ANN_DEADQ("RESPONSE_AFTER_PLAYING_ANN_DEADQ"),
    TRAIN_ACTIVATION_EVENT_DEADQ("TRAIN_ACTIVATION_EVENT_DEADQ"),
    ;

    private String target;

    //***** constructor *****
    MessageDestinationName(String target) {
        this.target = target;
    }

    //***** public method *****
    public String getTarget() {
        return target;
    }
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****

}
