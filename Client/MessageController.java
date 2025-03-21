package Client;

import Server.Chatroom;
import Server.Message;
import Server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;

public class MessageController {
    private final PanelView panel;
    private final RMIClient rmiClient;
    private final User user;
    private final Chatroom chatroom;

    public MessageController(PanelView panel, RMIClient rmiClient, User user, Chatroom chatroom) {
        this.panel = panel;
        this.rmiClient = rmiClient;
        this.user = user;
        this.chatroom = chatroom;

        setupListeners();
    }

    private void setupListeners() {
        panel.getMessageInput().addActionListener(e -> sendMessage());

        panel.getUploadImageButton().addActionListener(e -> {
            if (panel.getSelectedFile() == null) {
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    panel.setSelectedFile(file);
                    panel.getUploadImageButton().setText("Image Uploaded!");
                }
            } else {
                panel.setSelectedFile(null);
                panel.getUploadImageButton().setText("Upload Image");
            }
        });
    }

    private void sendMessage() {
        String text = panel.getMessageInput().getText().trim();
        if (text.isEmpty()) return;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String imageName = null;

        try {
            if (!rmiClient.getClientLogic().inChatroom(user, chatroom)) return;

            File file = panel.getSelectedFile();
            if (file != null) {
                rmiClient.getClientLogic().uploadImage(file, chatroom.getRoomID());
                imageName = file.getName();
                panel.setSelectedFile(null);
                panel.getUploadImageButton().setText("Upload Image");
                panel.getUploadImageButton().setBackground(Color.CYAN);
            }

            
            Message msg = new Message(
                rmiClient.getClientLogic().getUserID(user),
                chatroom.getRoomID(),
                timestamp,
                text,
                imageName
            );
 
            
            rmiClient.getClientLogic().createMessage(msg);
            panel.getChatWindow().addOneMessage(msg, this);
            panel.getMessageInput().setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessageInfo(Message msg) {
        try {
            return rmiClient.getClientLogic().messageInfo(msg);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    public Action getImageLoadAction(Message msg) {
        return new AbstractAction("Load Image") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    byte[] imageData = rmiClient.getClientLogic().getImageFile(msg);

                    if (imageData != null && imageData.length > 0) {
                        String folderPath = "images/room_" + msg.getRoomID();
                        new File(folderPath).mkdirs();
                        String outputPath = folderPath + "/" + msg.getImage();

                        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                            fos.write(imageData);
                        }

                        System.out.println("Saved image: " + outputPath);
                        ChatWindow.showImage(outputPath);
                    } else {
                        JOptionPane.showMessageDialog(null, "Image not found.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to load image: " + ex.getMessage());
                }
            }
        };
    }

    public void reloadChat() {
        try {
            List<Message> messages = rmiClient.getClientLogic().getChatHistory(chatroom);
            panel.getChatWindow().reloadMessages(messages, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
