package Client;

import javax.swing.*;
import java.rmi.RemoteException;

import Server.Message;
import Server.User;
import Server.Chatroom;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileOutputStream;
import java.util.LinkedList;

public class ChatWindow extends JPanel {
    private static final long serialVersionUID = 1L;
    //LinkedList<User> Users;
    private RMIClient rmiClient;
    private User user;
    private Chatroom chatroom;
    
    public ChatWindow(RMIClient rmiClient, User user, Chatroom chatroom) throws Exception {
        this.rmiClient = rmiClient;
        this.user = user;
        this.chatroom = chatroom;
        
        //Users = c.getClientLogic().getUsers();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Vertical stacking
        
        if (rmiClient.getClientLogic().inChatroom(user, chatroom)) {
            for (Message msg : rmiClient.getClientLogic().getChatHistory(chatroom)) {
                //System.out.println(msg.getText());
                add(createMessagePanel(msg)); // Add each message as a JPanel
            }
        }
    }
    
    public void ReloadChat() throws Exception{
        
        removeAll();
        
        if (rmiClient.getClientLogic().inChatroom(user, chatroom)) {
            //removeAll();
            for (Message msg : rmiClient.getClientLogic().getChatHistory(chatroom)) {
                System.out.println(msg.getText());
                add(createMessagePanel(msg)); // Add each message as a JPanel
            }
        
            revalidate();
            repaint();
        }
        
    }
    
    
    public void addOneMessage(Message message) throws Exception {
        
        if (rmiClient.getClientLogic().inChatroom(user, chatroom)) {
            add(createMessagePanel(message));
            revalidate();
            repaint();
        }
    }

    
    private JPanel createMessagePanel(Message msg) throws Exception {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        messagePanel.setMaximumSize(new Dimension(565, 80));
        messagePanel.setMinimumSize(new Dimension(565, 80));

        String messageInfo = rmiClient.getClientLogic().messageInfo(msg);
        
        /*
        String username = "Unknown";
        for (User u : rmiClient.getClientLogic().getUsers()) {
            if (u.getUserID() == msg.getSenderID()) {
                username = user.getUsername();
                break;
            }
        }
        */
    
        JLabel senderLabel = new JLabel(messageInfo + ":");
        JTextArea textLabel = new JTextArea(msg.getText());
        textLabel.setEditable(false);
        textLabel.setLineWrap(true);
        textLabel.setWrapStyleWord(true);
        
        messagePanel.add(senderLabel, BorderLayout.NORTH);
        messagePanel.add(textLabel, BorderLayout.CENTER);
        if (msg.getImage() != null) {
            JButton LoadImageButton = new JButton("Load Image");
            LoadImageButton.setPreferredSize(new Dimension(120, 40));

            LoadImageButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        byte[] imageData = rmiClient.getClientLogic().getImageFile(msg.getMessageID()); 
                        if (imageData != null) {
                            String outputPath = "received_image_" + msg.getMessageID() + ".jpg";
                            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                                fos.write(imageData);
                            }
                            System.out.println("Image received and saved as: " + outputPath);
                            showImage(outputPath);
                        } else {
                            System.out.println("No image found!");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            messagePanel.add(LoadImageButton, BorderLayout.EAST);
        }

        

        return messagePanel;
    }

    public static void showImage(String imagePath) {
        JFrame frame = new JFrame("Received Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel label = new JLabel(imageIcon);

        frame.add(label, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
