package Client;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatroomObserver extends UnicastRemoteObject implements Observer {
    private MessageController currentMessageController;

    public ChatroomObserver() throws RemoteException {
        super();
    }

    public void setMessageController(MessageController controller) {
        this.currentMessageController = controller;
    }

    @Override
    public void update() throws RemoteException {
        if (currentMessageController != null) {
            SwingUtilities.invokeLater(currentMessageController::reloadChat);
        }
    }
}