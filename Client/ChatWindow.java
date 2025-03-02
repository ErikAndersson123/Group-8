package Client;

import javax.swing.*;

import Server.Message;

import java.awt.*;
import java.util.LinkedList;

public class ChatWindow extends JPanel {
    private static final long serialVersionUID = 1L;

    public ChatWindow(LinkedList<Message> messages) {
        
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
    }
    private JPanel createMessagePanel(Message msg) {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        JLabel senderLabel = new JLabel("User " + msg.getSenderID() + ":");
        JTextArea textLabel = new JTextArea(msg.getText());
        textLabel.setEditable(false);
        textLabel.setLineWrap(true);
        textLabel.setWrapStyleWord(true);
        
        messagePanel.add(senderLabel, BorderLayout.NORTH);
        messagePanel.add(textLabel, BorderLayout.CENTER);

        return messagePanel;
    }
}
