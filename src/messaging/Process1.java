package messaging;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
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

//        new Thread(() -> {
            msgCenter.sendMessage(2, "localhost", "Message to process 2!", 10);
//        }).start();

//        new Thread(() -> {
            msgCenter.sendMessage(3, "localhost", "Message to process 3!", 0);
//        }).start();
    }
}
