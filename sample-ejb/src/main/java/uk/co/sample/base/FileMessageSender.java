package uk.co.sample.base;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.jms.Message;

import org.slf4j.Logger;

public class FileMessageSender implements IfMessageSender, Serializable {

    private static final long serialVersionUID = 2283906424359060489L;

    private transient Logger  logger;

    //***** constructor *****
    public FileMessageSender(Logger logger) {
        this.logger = logger;
    }

    //***** public method *****
    @Override
    public PrintWriter createWriter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public PrintWriter createWriter(boolean appendFlag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(Message message) {
        String text = message.toString();
        logger.debug(text);
    }

    @Override
    public void send(String messageText) {
        logger.debug(messageText);
    }

    @Override
    public void send(Message message, int priority) {
        logger.debug("Priority: {}, {}", priority, message);
    }

    @Override
    public void send(String messageText, int priority) {
        logger.debug("Priority: {}, {}", priority, messageText);
    }

    @Override
    public void send(String messageText, PrintWriter writer) {
        throw new UnsupportedOperationException();
    }

    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****
}
