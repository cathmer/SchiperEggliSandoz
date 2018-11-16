package messaging;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Message implements Serializable {

    private static final long serialVersionUID = 7526471155622776147L;
    private String msg;
    private int toProcessId;
    private int fromProcessId;
    private HashMap<Integer, ArrayList<Integer>> buffer = new HashMap<>();
    private ArrayList<Integer> timestamp = new ArrayList<>();

    public Message(String msg, int toProcessId, int fromProcessId, HashMap<Integer, ArrayList<Integer>> buffer,
                   ArrayList<Integer> timestamp) {
        this.msg = msg;
        this.toProcessId = toProcessId;
        this.fromProcessId = fromProcessId;
        this.buffer = buffer;
        this.timestamp = timestamp;
    }

    public String getMessage() { return this.msg; }

    public int getToProcessId() { return toProcessId; }

    public int getFromProcessId() { return fromProcessId; }

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
