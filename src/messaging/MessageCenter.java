package messaging;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageCenter {

    private int processId;
    private ArrayList<Integer> vectorClock = new ArrayList<>();
    private HashMap<Integer, ArrayList<Integer>> buffer = new HashMap<>();
    private SendMessage sender;
    private ReceiveMessage receiver;
    private ArrayList<Message> messageBuffer = new ArrayList<>();

    public MessageCenter(int processId, int numberOfProcesses) {
        this.processId = processId;
        for (int i = 0; i < numberOfProcesses; i++) {
            vectorClock.add(0);
        }

        sender = new SendMessage(this);
        receiver = new ReceiveMessage();

        StartRegistry startReg = new StartRegistry();
        startReg.startRegistry("SendMessage" + processId, sender);
    }

    public void sendMessage(int toProcessId, String receiver, String message) {
        // TODO: Increment vectorClock

        Message msg = new Message(message, toProcessId, buffer, vectorClock);
        String registryName = "SendMessage" + toProcessId;
        sender.sendMessage(registryName, receiver, msg);

        // TODO: increment buffer
    }

    public void receiveMessage(Message message) {
        // TODO: Check if message can be delivered
        // If my clock > buffer clock for my process id.
        // If so, deliver. Else, store in MessageBuffer
        // Then check for all other messages if they can be delivered

        receiver.receive(message);
    }
}
