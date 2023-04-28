package uk.co.sample.base;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;

import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.ApplicationSystemException;

public class MQMessageSender implements IfMessageSender, Serializable {

    private static final long serialVersionUID = 7820603373731987819L;

    private transient Logger  logger;
    private String            factoryName;
    private String            queueName;

    //***** injection field *****
    //***** constructor *****
    public MQMessageSender(String factoryName, String queueName, Logger logger) {
        this.factoryName = factoryName;
        this.queueName = queueName;
        this.logger = logger;
    }

    //***** public method *****
    @Override
    public void send(Message message) {
        send(message, Message.DEFAULT_PRIORITY);
    }

    @Override
    public void send(Message message, int priority) {
        QueueConnection connection = createQueueConnection(getConnectionFactory());
        try {
            QueueSession session = createQueueSession(connection);
            QueueSender sender = createSender(getQueue(), session);
            startConnection(connection);
            send(message, sender, priority);
        } finally {
            clearConnection(connection);
        }
    }

    @Override
    public void send(String messageText) {
        send(messageText, Message.DEFAULT_PRIORITY);
    }

    @Override
    public void send(String messageText, int priority) {

        QueueConnection connection = createQueueConnection(getConnectionFactory());
        try {
            QueueSession session = createQueueSession(connection);
            QueueSender sender = createSender(getQueue(), session);
            startConnection(connection);
            send(messageText, session, sender, priority);
        } finally {
            clearConnection(connection);
        }
    }

    @Override
    public void send(String messageText, PrintWriter writer) {
        send(messageText);
    }

    @Override
    public PrintWriter createWriter() {
        return createWriter(true);
    }

    @Override
    public PrintWriter createWriter(boolean appendFlag) {
        return null;
    }

    //***** protected method *****
    //***** private method *****
    private Context getEnvironmentContext() {
        try {
            InitialContext initCtx = new InitialContext();
            return (Context) initCtx.lookup("java:comp/env");
        } catch (NamingException e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_SYSTEM_NAMING, e);
        }
    }

    private Queue getQueue() {
        try {
            return (Queue) getEnvironmentContext().lookup(queueName);
        } catch (NamingException e) {
            logger.debug("The queue name {} is not found", queueName);
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_SYSTEM_NAMING, e);
        }
    }

    private QueueConnectionFactory getConnectionFactory() {
        try {
            return (QueueConnectionFactory) getEnvironmentContext().lookup(factoryName);
        } catch (NamingException e) {
            logger.debug("The ConnectionFactory {} is not found", factoryName);
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_SYSTEM_NAMING, e);
        }
    }

    private QueueSession createQueueSession(QueueConnection connection) {
        try {
            return connection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_JMS_ACCESS, e);
        }
    }

    private QueueSender createSender(Queue queue, QueueSession session) {
        try {
            return session.createSender(queue);
        } catch (JMSException e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_JMS_ACCESS, e);
        }
    }

    private void clearConnection(QueueConnection connection) {
        try {
            connection.close();
        } catch (JMSException e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_JMS_ACCESS, e);
        }
    }

    private void send(String messageText, QueueSession session, QueueSender sender, int priority) {
        try {
            Message message = session.createTextMessage(messageText);
            sender.setPriority(priority);
            sender.send(message);
            logger.debug("Has sent the message: {}", messageText);
        } catch (JMSException e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_JMS_ACCESS, e);
        }
    }

    private void send(Message message, QueueSender sender, int priority) {
        try {
            sender.setPriority(priority);
            sender.send(message);
        } catch (JMSException e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_JMS_ACCESS, e);
        }
    }

    private void startConnection(QueueConnection connection) {
        try {
            connection.start();
        } catch (JMSException e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_JMS_ACCESS, e);
        }
    }

    private QueueConnection createQueueConnection(QueueConnectionFactory queueConnectionFactory) {
        try {
            return queueConnectionFactory.createQueueConnection();
        } catch (JMSException e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_JMS_ACCESS, e);
        }
    }

    //***** call back method *****
    //***** getter and setter *****
}
