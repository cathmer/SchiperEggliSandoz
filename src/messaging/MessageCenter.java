package messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MessageCenter {

    private int processId;
    private ArrayList<Integer> vectorClock = new ArrayList<>();
    private HashMap<Integer, ArrayList<Integer>> buffer = new HashMap<>();
    private SendMessage sender;
    private ArrayList<Message> messageBuffer = new ArrayList<>();

    public MessageCenter(int processId, int numberOfProcesses) {
        this.processId = processId;
        for (int i = 0; i < numberOfProcesses; i++) {
            vectorClock.add(0);
        }

        sender = new SendMessage(this);

        StartRegistry startReg = new StartRegistry();
        startReg.startRegistry("SendMessage" + processId, sender);
    }

    public void sendMessage(int toProcessId, String receiver, String message, int delay) {
        // TODO: Increment vectorClock
        vectorClock.set(processId - 1, vectorClock.get(processId - 1) + 1);

        Message msg = new Message(message, toProcessId, processId, new HashMap<>(buffer), new ArrayList<>(vectorClock));
        String registryName = "SendMessage" + toProcessId;
        System.out.println("Sending:\n" + msg);

        buffer.put(toProcessId, new ArrayList<>(vectorClock));
//        System.out.println("Buffer after sending message: " + buffer);

        sender.sendMessage(registryName, receiver, msg, delay);
    }

    public synchronized void receiveMessage(Message message) {
        System.out.println("Receiving:\n" + message);
        // TODO: Check if message can be delivered
        // If my clock > buffer clock for my process id.
        // If so, deliver. Else, store in MessageBuffer
        ArrayList<Integer> bufferClock = message.getBuffer().get(processId);
        if (vectorClockIsAtLeastAsBig(bufferClock)) {
            deliver(message);
        } else {
            messageBuffer.add(message);
        }

        // Then check for all other messages if they can be delivered
        for (Message msg : messageBuffer) {
            if (vectorClockIsAtLeastAsBig(msg.getBuffer().get(msg.getToProcessId()))) {
                deliver(msg);
            }
        }
    }

    private boolean vectorClockIsAtLeastAsBig(ArrayList<Integer> bufferClock) {
        if (bufferClock == null) {
            return true;
        }

        for (int i = 0; i < bufferClock.size(); i++) {
            if (bufferClock.get(i) > vectorClock.get(i)) {
                return false;
            }
        }

        return true;
    }

    private void deliver(Message message) {
        // Increment vector clock
        vectorClock.set(processId - 1, vectorClock.get(processId - 1) + 1);

        System.out.println("Delivered:\n" + message);

        // For each buffer id, merge the buffer with this buffer
        HashMap<Integer, ArrayList<Integer>> msgBuffer = message.getBuffer();
        mergeBuffers(msgBuffer);
        vectorClock = mergeTimestamps(vectorClock, message.getTimestamp());

//        System.out.println("Buffer and timestamp after delivery: \nbuffer: " + buffer + "\ntimestamp: " + vectorClock);
    }

    private void mergeBuffers(HashMap<Integer, ArrayList<Integer>> msgBuffer) {
        for (Integer processId : msgBuffer.keySet()) {
            ArrayList<Integer> timestamp1 = msgBuffer.get(processId);
            if (buffer.containsKey(processId)) {
                ArrayList<Integer> timestamp2 = buffer.get(processId);
                ArrayList<Integer> newTimestamps = mergeTimestamps(timestamp1, timestamp2);
                buffer.put(processId, newTimestamps);
            } else {
                buffer.put(processId, timestamp1);
            }

            vectorClock = mergeTimestamps(vectorClock, timestamp1);
        }
    }

    private ArrayList<Integer> mergeTimestamps(ArrayList<Integer> timestamp1, ArrayList<Integer> timestamp2) {
        ArrayList<Integer> newTimestamp = new ArrayList<>();
        if (timestamp1 == null) {
            return timestamp2;
        }
        if (timestamp2 == null) {
            return timestamp1;
        }

        for (int i = 0; i < timestamp1.size(); i++) {
            if (timestamp1.get(i) > timestamp2.get(i)) {
                newTimestamp.add(timestamp1.get(i));
            } else {
                newTimestamp.add(timestamp2.get(i));
            }
        }

        return newTimestamp;
    }
}
