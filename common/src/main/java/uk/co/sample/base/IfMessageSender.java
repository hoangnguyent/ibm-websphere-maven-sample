package uk.co.sample.base;

import java.io.PrintWriter;

import javax.ejb.Local;
import javax.jms.Message;

@Local
public interface IfMessageSender {
    /**
     * send the message.<br>
     * @param message Message.
     */
    public void send(final Message message);
    /**
     * send the message with priority.<br>
     * <br>
     * @param message
     * @param priority
     */
    void send(final Message message, int priority);

    /**
     * send the message.<br>
     * @param messageText Message.
     */
    public void send(final String messageText);

    /**
     * send the message with priority.<br>
     * @param messageText String.
     * @param priority Priority.
     */
    public void send(final String messageText, int priority);

    /**
     * send the message.<br>
     * @param messageText String.
     * @param writer PrintWriter.
     */
    public void send(String messageText, PrintWriter writer);

    /**
     * create Writer using file name<br>
     * @return writer.
     */
    public PrintWriter createWriter();

    /**
     * create Writer using file name<br>
     * @param appendFlag    true:(append mode)then bytes will be written to the end of the file rather than the beginning.  false:(new file mode)make a file newly.
     * @return writer.
     */
    public PrintWriter createWriter(boolean appendFlag);

}
