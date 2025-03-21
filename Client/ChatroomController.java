package Client;

import Server.Chatroom;
import Server.Message;
import Server.User;

import javax.swing.*;
import java.util.List;

public class ChatroomController {
    private final ChatroomView view;
    private final RMIClient rmiClient;
    private final User user;
    private final ChatroomObserver observer;
    private List<Chatroom> allChatrooms;
    private Chatroom selectedChatroom;
    private MessageController messageController;

    public ChatroomController(RMIClient rmiClient, User user, ChatroomObserver observer) throws Exception {
        this.rmiClient = rmiClient;
        this.user = user;
        this.observer = observer;

        allChatrooms = rmiClient.getClientLogic().getChatrooms();
        this.view = new ChatroomView(allChatrooms);

        attachListeners();
        loadInitialChatroom();

        view.setVisible(true);
    }

    private void attachListeners() {
        view.getChatroomList().addListSelectionListener(e -> {
            String selectedName = view.getSelectedChatroomName();
            if (selectedName == null || (selectedChatroom != null && selectedChatroom.getName().equals(selectedName))) return;

            selectedChatroom = allChatrooms.stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst()
                    .orElse(null);

            if (selectedChatroom != null) {
                loadChatroomView();
            }
        });

        view.getJoinButton().addActionListener(e -> {
            if (selectedChatroom == null) return;
            int confirm = JOptionPane.showConfirmDialog(null, "Join this chatroom?");
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    rmiClient.getClientLogic().addChatroomUser(user, selectedChatroom);
                    JOptionPane.showMessageDialog(null, "Joined chatroom.");
                    loadChatroomView();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        view.getLeaveButton().addActionListener(e -> {
            if (selectedChatroom == null) return;
            int confirm = JOptionPane.showConfirmDialog(null, "Leave this chatroom?");
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    rmiClient.getClientLogic().removeChatroomUser(user, selectedChatroom);
                    JOptionPane.showMessageDialog(null, "Left chatroom.");
                    PanelView blankPanel = new PanelView(600, 600, new ChatWindow(List.of(), selectedChatroom));
                    view.setChatPanel(blankPanel);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        view.getCreateButton().addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter new chatroom name:");
            if (name != null && !name.trim().isEmpty()) {
                try {
                    Chatroom newRoom = new Chatroom(name);
                    rmiClient.getClientLogic().createChatroom(newRoom);
                    newRoom.setRoomID(rmiClient.getClientLogic().getRoomID(newRoom));
                    rmiClient.getClientLogic().addChatroomUser(user, newRoom);

                    allChatrooms.add(newRoom);
                    view.addChatroomToList(name);
                    selectedChatroom = newRoom;
                    view.selectChatroom(name);
                    loadChatroomView();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error creating chatroom.");
                }
            }
        });
    }

    private void loadInitialChatroom() throws Exception {
        if (!allChatrooms.isEmpty()) {
            selectedChatroom = allChatrooms.get(0);
            view.selectChatroom(selectedChatroom.getName());
            loadChatroomView();
        }
    }

    private void loadChatroomView() {
        try {
            boolean isMember = rmiClient.getClientLogic().inChatroom(user, selectedChatroom);

            ChatWindow chatWindow;
            if (isMember) {
                List<Message> messages = rmiClient.getClientLogic().getChatHistory(selectedChatroom);
                chatWindow = new ChatWindow(messages, selectedChatroom);
            } else {
                chatWindow = new ChatWindow(List.of(), selectedChatroom);
            }

            PanelView panelView = new PanelView(600, 600, chatWindow);
            view.setChatPanel(panelView);

            if (isMember) {
                messageController = new MessageController(panelView, rmiClient, user, selectedChatroom);
                panelView.getChatWindow().reloadMessages(
                    rmiClient.getClientLogic().getChatHistory(selectedChatroom),
                    messageController
                );
                observer.setMessageController(messageController);
            } else {
                observer.setMessageController(null);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
