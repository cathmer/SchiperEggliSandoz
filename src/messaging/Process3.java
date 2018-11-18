package messaging;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Process3 {

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        MessageCenter msgCenter = new MessageCenter(3, 3);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MessageSender(msgCenter).startSender();
    }
}
