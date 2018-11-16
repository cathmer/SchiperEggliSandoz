package messaging;

public class Process2 {

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        MessageCenter msgCenter = new MessageCenter(2, 2);
        msgCenter.sendMessage(1, "localhost", "Message to process 1!");
    }
}
