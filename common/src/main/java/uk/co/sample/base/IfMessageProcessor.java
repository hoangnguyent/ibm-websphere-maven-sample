package uk.co.sample.base;

import javax.jms.TextMessage;

public interface IfMessageProcessor {
    boolean isEnableListenerProcess();

    void onListenerMessage(TextMessage message);

    void onListenerRetryableMaxCount(TextMessage message, IfMessageSender messageSender);

}
