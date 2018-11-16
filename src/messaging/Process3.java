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

        Scanner scan = new Scanner(System.in);
        try {
            int msgCounter = 0;
            while (scan.hasNextLine()) {
                msgCounter++;
                int toProcess = scan.nextInt();
//                String receiver = scan.next();
                int delay = scan.nextInt();
                msgCenter.sendMessage(toProcess, "localhost", "Message number " + msgCounter, delay);
            }
        } finally {
            scan.close();
        }
    }
}
