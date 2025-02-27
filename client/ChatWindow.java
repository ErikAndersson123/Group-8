package client;

import javax.swing.*;

import server.Message;

import java.awt.*;
import java.sql.Timestamp;
import java.util.LinkedList;

public class ChatWindow extends JPanel {
    
    public ChatWindow(LinkedList<Message> messages) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Vertical stacking
        
        for (Message msg : messages) {
            add(createMessagePanel(msg)); // Add each message as a JPanel
        }
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

    public static void main(String[] args) {
        LinkedList<Message> messages = new LinkedList<>();
        messages.add(new Message(1, 1001, 2001, new Timestamp(System.currentTimeMillis()), "Hello!"));
        messages.add(new Message(2, 1002, 2001, new Timestamp(System.currentTimeMillis()), "Hey there!"));
        messages.add(new Message(3, 1003, 2001, new Timestamp(System.currentTimeMillis()), "How are you?"));

        new ChatWindow(messages);
    }
}

