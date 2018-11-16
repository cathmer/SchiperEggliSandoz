package messaging;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class starts the registry for a process with a registry name and the SendMessage object.
 */
public class StartRegistry {

    /**
     * Method to start the registry.
     * @param registryName : the name of the registry.
     * @param sendMsg : the SendMessage object which can be invoked by a different process.
     */
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
