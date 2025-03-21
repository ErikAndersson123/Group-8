package Client;

import Server.Chatroom;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatroomView extends JFrame {
    private final DefaultListModel<String> listModel;
    private final JList<String> chatroomList;
    private final JButton joinButton;
    private final JButton leaveButton;
    private final JButton createButton;
    private final JPanel mainPanel;

    public ChatroomView(List<Chatroom> chatrooms) {
        setTitle("Chatroom");
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        // Sidebar panel
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(200, 600));
        sidePanel.setBackground(Color.LIGHT_GRAY);

        listModel = new DefaultListModel<>();
        chatroomList = new JList<>(listModel);
        chatroomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(chatroomList);
        sidePanel.add(listScroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        joinButton = new JButton("Join Chatroom");
        leaveButton = new JButton("Leave Chatroom");
        createButton = new JButton("Create Chatroom");

        for (JButton button : new JButton[]{joinButton, leaveButton, createButton}) {
            button.setMaximumSize(new Dimension(200, 40));
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
            buttonPanel.add(button);
        }

        sidePanel.add(buttonPanel, BorderLayout.SOUTH);
        add(sidePanel, BorderLayout.WEST);

        // Main panel for chat
        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Load initial chatroom names
        for (Chatroom chatroom : chatrooms) {
            listModel.addElement(chatroom.getName());
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public JButton getJoinButton() {
        return joinButton;
    }

    public JButton getLeaveButton() {
        return leaveButton;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JList<String> getChatroomList() {
        return chatroomList;
    }

    public String getSelectedChatroomName() {
        return chatroomList.getSelectedValue();
    }

    public void selectChatroom(String name) {
        chatroomList.setSelectedValue(name, true);
    }

    public void addChatroomToList(String name) {
        listModel.addElement(name);
    }

    public void setChatPanel(PanelView panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
