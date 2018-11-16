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
