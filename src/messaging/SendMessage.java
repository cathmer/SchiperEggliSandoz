package messaging;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the SendMessage_RMI interface, which allows for the sendMessage method to be invoked by another
 * process.
 */
public class SendMessage implements SendMessage_RMI {

    private MessageCenter messageCenter;

    /**
     * Constructs an object with the messagecenter of the receiving process.
     * @param messageCenter : the messagecenter of the receiving process.
     */
    public SendMessage(MessageCenter messageCenter) {
        this.messageCenter = messageCenter;
    }

    /**
     * This method can be invoked by another process to send a message to this process. This method starts a new thread.
     * @param message : the message to send.
     * @param delay : the delay in seconds before the message should actually be received.
     */
    public void sendMessage(Message message, int delay) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(delay);
                messageCenter.receiveMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Method to send a message to a different process.
     * @param registryName : registry name of the other process.
     * @param receiver : the name of the ip of the receiver.
     * @param message : the message to be sent.
     * @param delay : the delay.
     */
    public void sendMessage(String registryName, String receiver, Message message, int delay) {
        // Sends a message using the remote method of another host
        try {
            String name = registryName;
            Registry remoteReg = LocateRegistry.getRegistry(receiver);
            SendMessage_RMI sendMsg = (SendMessage_RMI) remoteReg.lookup(name);
            sendMsg.sendMessage(message, delay);
        } catch (Exception e) {
            System.err.println("Sending message exception:");
            e.printStackTrace();
        }
    }
}
