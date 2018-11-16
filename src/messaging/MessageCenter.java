package messaging;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class handles all the logic relating to sending and receiving messages.
 */
public class MessageCenter {

    private int processId;
    private ArrayList<Integer> vectorClock = new ArrayList<>();
    private HashMap<Integer, ArrayList<Integer>> buffer = new HashMap<>();
    private SendMessage sender;
    private ArrayList<Message> messageBuffer = new ArrayList<>();

    /**
     * Creates a new message center with the process id of this process and the total number of processes. It also
     * starts the registry and registers the SendMessage object of this process with the name SendMessage[process id].
     * @param processId : the id of this process.
     * @param numberOfProcesses : the total number of processes.
     */
    public MessageCenter(int processId, int numberOfProcesses) {
        this.processId = processId;
        for (int i = 0; i < numberOfProcesses; i++) {
            vectorClock.add(0);
        }

        sender = new SendMessage(this);

        StartRegistry startReg = new StartRegistry();
        startReg.startRegistry("SendMessage" + processId, sender);
    }

    /**
     * Sends a message to another process. Before sending the message, the timestamp of this process is incremented by one.
     * After sending the message, the buffer is updated by putting the timestamp of this process with the id of the
     * process sending to (meaning, the process that receives the message should know the timestamp of the sender at this time).
     * @param toProcessId : the process to send the message to.
     * @param receiver : the string specifying the ip address of the receiver.
     * @param message : the string with the message.
     * @param delay : an int specifying the delay in seconds. The message will only be received after this delay has been
     *              waited for.
     */
    public void sendMessage(int toProcessId, String receiver, String message, int delay) {
        vectorClock.set(processId - 1, vectorClock.get(processId - 1) + 1);

        Message msg = new Message(message, toProcessId, processId, new HashMap<>(buffer), new ArrayList<>(vectorClock));
        String registryName = "SendMessage" + toProcessId;
        System.out.println("Sending:\n" + msg);

        buffer.put(toProcessId, new ArrayList<>(vectorClock));

        sender.sendMessage(registryName, receiver, msg, delay);
    }

    /**
     * Receives a message. It checks if the timestamp of this process is at least as big as the timestamp contained in the
     * buffer of the message. If this is the case, the message is delivered, else it is stored in the messagebuffer.
     * @param message : the message to receive.
     */
    public synchronized void receiveMessage(Message message) {
        System.out.println("Receiving:\n" + message);
        // If my clock > buffer clock for my process id.
        // If so, deliver. Else, store in MessageBuffer
        ArrayList<Integer> bufferClock = message.getBuffer().get(processId);
        if (vectorClockIsAtLeastAsBig(bufferClock)) {
            deliver(message);
        } else {
            messageBuffer.add(message);
        }

        // Then check for all other messages if they can be delivered
        ArrayList<Message> newMessageBuffer = new ArrayList<>();
        for (Message msg : messageBuffer) {
            if (vectorClockIsAtLeastAsBig(msg.getBuffer().get(msg.getToProcessId()))) {
                deliver(msg);
            } else {
                newMessageBuffer.add(msg);
            }
        }

        messageBuffer = newMessageBuffer;
    }

    /**
     * Method to check if the timestamp of this process is at least as big as another timestamp.
     * @param bufferClock : the timestamp to compare this processes' timestamp with.
     * @return : true if this timestamp is at least as big as the other timestamp, false otherwise.
     */
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

    /**
     * Delivers a message to this process. When delivered, the timestamp is incremented by one, and the buffers of the
     * message and of the receiver are merged.
     * @param message
     */
    private void deliver(Message message) {
        // Increment vector clock
        vectorClock.set(processId - 1, vectorClock.get(processId - 1) + 1);

        System.out.println("Delivered:\n" + message);

        // For each buffer id, merge the buffer with this buffer
        HashMap<Integer, ArrayList<Integer>> msgBuffer = message.getBuffer();
        mergeBuffers(msgBuffer);
        vectorClock = mergeTimestamps(vectorClock, message.getTimestamp());
    }

    /**
     * Merges the buffer of a message with the buffer of this process, meaning it takes the highest values everywhere.
     * @param msgBuffer : the buffer to compare with.
     */
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

    /**
     * Merges two lists with timestamps, meaning it takes the biggest element for each index.
     * @param timestamp1 : timestamp1.
     * @param timestamp2 : timestamp2.
     * @return : a new list consisting of the merged timestamps.
     */
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
