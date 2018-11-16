package messaging;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Process1 {

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        // Start registry
        try {
            LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }

        MessageCenter msgCenter = new MessageCenter(1, 3);

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

//        msgCenter.sendMessage(2, "localhost", "Message to process 2!", 10);
//        msgCenter.sendMessage(3, "localhost", "Message to process 3!", 0);
    }
}
