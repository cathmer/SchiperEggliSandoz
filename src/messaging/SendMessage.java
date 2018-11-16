package messaging;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.TimeUnit;

public class SendMessage implements SendMessage_RMI {

    MessageCenter messageCenter;

    public SendMessage(MessageCenter messageCenter) {
        this.messageCenter = messageCenter;
    }

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

    public void sendMessage(String registryName, String receiver, Message message, int delay) {
//        new Thread(() -> {
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
//        }).start();
    }
}
