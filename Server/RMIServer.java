package Server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    
    public static void main(String[] args) {
        try {
            DataLogic dataLogic = new DataLogic();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost/Subject", dataLogic);
            System.out.println("Server is ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}