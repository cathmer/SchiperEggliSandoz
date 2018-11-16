package messaging;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SendMessage implements SendMessage_RMI {

    MessageCenter messageCenter;

    public SendMessage(MessageCenter messageCenter) {
        this.messageCenter = messageCenter;
    }

    public void sendMessage(Message message) {
        messageCenter.receiveMessage(message);
    }

    public void sendMessage(String registryName, String receiver, Message message) {
        // Sends a message using the remote method of another host
        try {
            String name = registryName;
            Registry remoteReg = LocateRegistry.getRegistry(receiver);
            SendMessage_RMI sendMsg = (SendMessage_RMI) remoteReg.lookup(name);
            sendMsg.sendMessage(message);
        } catch (Exception e) {
            System.err.println("Sending message exception:");
            e.printStackTrace();
        }
    }
}
