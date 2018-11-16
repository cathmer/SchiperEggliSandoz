package messaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Message implements Serializable {

    private static final long serialVersionUID = 7526471155622776147L;
    private String msg;
    private int processId;
    private HashMap<Integer, ArrayList<Integer>> buffer = new HashMap<>();
    private ArrayList<Integer> timestamp = new ArrayList<>();

    public Message(String msg, int processId, HashMap<Integer, ArrayList<Integer>> buffer,
                   ArrayList<Integer> timestamp) {
        this.msg = msg;
        this.processId = processId;
        this.buffer = buffer;
        this.timestamp = timestamp;
    }

    public String getMessage() { return this.msg; }
}
