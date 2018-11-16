package messaging;

import java.util.concurrent.TimeUnit;

public class Process2 {

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        MessageCenter msgCenter = new MessageCenter(2, 3);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        new Thread(() -> {
//            msgCenter.sendMessage(2, "localhost", "Message to process 2!", 5);
//        }).start();
    }
}
