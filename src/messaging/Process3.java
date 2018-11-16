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
            while (scan.hasNextLine()) {
                int toProcess = scan.nextInt();
//                String receiver = scan.next();
//                String message = scan.next();
                int delay = scan.nextInt();
                msgCenter.sendMessage(toProcess, "localhost", "Message to " + toProcess, delay);
            }
        } finally {
            scan.close();
        }

//            msgCenter.sendMessage(2, "localhost", "Message to process 2!", 0);
    }
}
