package messaging;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class StartRegistry {

    public void startRegistry(String registryName, SendMessage sendMsg) {
        // Initiate registry
        try {
            String name = registryName;
            SendMessage_RMI stub =
                    (SendMessage_RMI) UnicastRemoteObject.exportObject(sendMsg, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("SendMessage bound");
        } catch (Exception e) {
            System.err.println("SendMessage exception:");
            e.printStackTrace();
        }
    }
}
