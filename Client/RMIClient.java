package Client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import Server.Subject;

public class RMIClient {

    private final ClientLogic clientLogic;

    public RMIClient(Observer observer) throws RemoteException {
        try {
            // Connect to the RMI server
            Subject dataLogic = (Subject) Naming.lookup("rmi://10.0.1.21/Subject");

            // Create client logic layer
            this.clientLogic = new ClientLogic(dataLogic);

            // Register observer with the server
            dataLogic.addSubscriber(observer);

            System.out.println("Connected to RMI server.");
            System.out.println("Client is waiting for updates.");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Failed to initialize RMIClient", e);
        }
    }

    public ClientLogic getClientLogic() {
        return this.clientLogic;
    }
}
