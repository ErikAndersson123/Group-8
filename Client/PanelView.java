package Client;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PanelView extends JPanel {
    private final JTextField messageInput;
    private final JButton uploadImageButton;
    private final ChatWindow chatWindow;
    private File selectedFile;

    public PanelView(int width, int height, ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
        this.selectedFile = null;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(width, height));

        JTextArea headerBar = new JTextArea(chatWindow.getChatroom().getName());
        headerBar.setEditable(false);
        headerBar.setBackground(Color.LIGHT_GRAY);
        headerBar.setPreferredSize(new Dimension(150, 20));
        add(headerBar);

        JScrollPane chatScroll = new JScrollPane(chatWindow);
        chatScroll.setPreferredSize(new Dimension(width - 20, height - 150));
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(chatScroll);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(Color.LIGHT_GRAY);

        messageInput = new JTextField();
        messageInput.setPreferredSize(new Dimension(width - 200, 50));
        messageInput.setFont(new Font("Arial", Font.PLAIN, 16));

        uploadImageButton = new JButton("Upload Image");
        uploadImageButton.setBackground(Color.CYAN);

        inputPanel.add(messageInput);
        inputPanel.add(uploadImageButton);
        add(inputPanel);
    }

    public JTextField getMessageInput() {
        return messageInput;
    }

    public JButton getUploadImageButton() {
        return uploadImageButton;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File file) {
        this.selectedFile = file;
    }

    public ChatWindow getChatWindow() {
        return chatWindow;
    }
}
