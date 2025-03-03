package Client;

import javax.swing.*;

import Server.Message;
import Server.User;

import java.awt.*;
import java.util.LinkedList;

public class ChatWindow extends JPanel {
    private static final long serialVersionUID = 1L;
    LinkedList<User> Users;

    public ChatWindow(LinkedList<Message> messages, RMIClient c) throws Exception {
         //RMIClient rmiClient = c;
         Users = c.getClientLogic().getUsers();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Vertical stacking

        for (Message msg : messages) {
            //System.out.println(msg.getText());
            add(createMessagePanel(msg)); // Add each message as a JPanel
        }
    }
    public void ReloadChat(LinkedList<Message> messages) {
        removeAll();
        for (Message msg : messages) {
            System.out.println(msg.getText());
            add(createMessagePanel(msg)); // Add each message as a JPanel
        }
        
        revalidate();
        repaint();
        
    }
    public void addOneMessage(Message message) {
        add(createMessagePanel(message));
        revalidate();
        repaint();
    }
    private JPanel createMessagePanel(Message msg) {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        messagePanel.setMaximumSize(new Dimension(565, 80));
        messagePanel.setMinimumSize(new Dimension(565, 80));

        

        String username = "Unknown";
        for (User user : Users) {
            if (user.getUserID() == msg.getSenderID()) {
                username = user.getUsername();
                break;
            }
        }
        
        JLabel senderLabel = new JLabel(username + ":");
        JTextArea textLabel = new JTextArea(msg.getText());
        textLabel.setEditable(false);
        textLabel.setLineWrap(true);
        textLabel.setWrapStyleWord(true);
        
        messagePanel.add(senderLabel, BorderLayout.NORTH);
        messagePanel.add(textLabel, BorderLayout.CENTER);

        return messagePanel;
    }
}
