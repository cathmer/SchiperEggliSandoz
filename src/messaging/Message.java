package messaging;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class holds all the variables that are sent with a message. Including the actual message (a string), the processId
 * of the process the message is sent to, the processId of the process that sent the message, the buffer sent by the sender
 * and the timestamp sent by the sender.
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 7526471155622776147L;
    private String msg;
    private int toProcessId;
    private int fromProcessId;
    private HashMap<Integer, ArrayList<Integer>> buffer;
    private ArrayList<Integer> timestamp;

    /**
     * Constructs a message.
     * @param msg : a string specifying the message.
     * @param toProcessId : the id of the process to send to.
     * @param fromProcessId : the id of the process that sent the message.
     * @param buffer : the buffer sent by the sender.
     * @param timestamp : the timestamp sent by the sender.
     */
    public Message(String msg, int toProcessId, int fromProcessId, HashMap<Integer, ArrayList<Integer>> buffer,
                   ArrayList<Integer> timestamp) {
        this.msg = msg;
        this.toProcessId = toProcessId;
        this.fromProcessId = fromProcessId;
        this.buffer = buffer;
        this.timestamp = timestamp;
    }

    public int getToProcessId() { return toProcessId; }

    public HashMap<Integer, ArrayList<Integer>> getBuffer() { return buffer; }

    public ArrayList<Integer> getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        LocalDateTime time = LocalDateTime.now();
        String res = "from " + fromProcessId + ", to " + toProcessId + "\n";
        res += msg + "\n";
        res += "buffer: " + buffer + "\n";
        res += "timestamp: " + timestamp + "\n";
        res += "at: " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + "\n---------------------";
        return res;
    }
}
