package Client;

import javax.swing.*;
import Server.User;

public class UserLoginController {
    private final UserLoginView view;
    private final RMIClient rmiClient;
    private final ChatroomObserver observer;

    public UserLoginController(UserLoginView view, RMIClient rmiClient, ChatroomObserver observer) {
        this.view = view;
        this.rmiClient = rmiClient;
        this.observer = observer;
        attachListeners();
    }

    private void attachListeners() {
        view.getLoginButton().addActionListener(e -> handleLogin());
        view.getSwitchModeButton().addActionListener(e -> toggleMode());
    }

    private void handleLogin() {
        String username = view.getUsername();
        String password = view.getPassword();
        User user = new User(username, password);

        try {
            user.setUserID(rmiClient.getClientLogic().getUserID(user));

            if (view.isLoginMode()) {
                if (rmiClient.getClientLogic().authenticateUser(user)) {
                    new ChatroomController(rmiClient, user, observer);
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials.");
                }
            } else {
                rmiClient.getClientLogic().createUser(user);
                view.setStatusTitle("User created. You may now log in.");
                view.setLoginMode(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Login error: " + ex.getMessage());
        }
    }

    private void toggleMode() {
        boolean login = view.isLoginMode();
        view.setLoginMode(!login);
    }
}
