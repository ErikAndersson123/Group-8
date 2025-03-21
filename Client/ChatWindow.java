package Client;

import Server.Message;
import Server.User;
import Server.Chatroom;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ChatWindow extends JPanel {
    private static final long serialVersionUID = 1L;
    private final Chatroom chatroom;

    public ChatWindow(List<Message> chatHistory, Chatroom chatroom) throws Exception {
        this.chatroom = chatroom;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (Message msg : chatHistory) {
            add(createMessagePanel(msg, "Unknown", null));
        }
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void reloadMessages(List<Message> messages, MessageController controller) {
        removeAll();
        for (Message msg : messages) {
            String senderInfo = controller != null ? controller.getMessageInfo(msg) : "Unknown";
            Action imageLoader = controller != null ? controller.getImageLoadAction(msg) : null;
            add(createMessagePanel(msg, senderInfo, imageLoader));
        }
        revalidate();
        repaint();
    }

    public void addOneMessage(Message msg, MessageController controller) {
        String senderInfo = controller != null ? controller.getMessageInfo(msg) : "Unknown";
        Action imageLoader = controller != null ? controller.getImageLoadAction(msg) : null;
        add(createMessagePanel(msg, senderInfo, imageLoader));
        revalidate();
        repaint();
    }

    private JPanel createMessagePanel(Message msg, String senderInfo, Action imageLoadAction) {
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        messagePanel.setMaximumSize(new Dimension(565, 80));

        JLabel senderLabel = new JLabel(senderInfo + ":");
        JTextArea textLabel = new JTextArea(msg.getText());
        textLabel.setEditable(false);
        textLabel.setLineWrap(true);
        textLabel.setWrapStyleWord(true);

        messagePanel.add(senderLabel, BorderLayout.NORTH);
        messagePanel.add(textLabel, BorderLayout.CENTER);

        if (msg.getImage() != null && imageLoadAction != null) {
            JButton loadImageButton = new JButton(imageLoadAction);
            loadImageButton.setPreferredSize(new Dimension(120, 40));
            messagePanel.add(loadImageButton, BorderLayout.EAST);
        }

        return messagePanel;
    }

    public static void showImage(String imagePath) {
        try {
            byte[] bytes = Files.readAllBytes(new File(imagePath).toPath());
            ImageIcon imageIcon = new ImageIcon(bytes);
            JLabel label = new JLabel(imageIcon);

            JFrame frame = new JFrame("Received Image");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(500, 500);
            frame.add(label, BorderLayout.CENTER);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
