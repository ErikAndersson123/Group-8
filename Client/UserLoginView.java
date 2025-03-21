package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UserLoginView extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton switchModeButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private char ogEcho;
    private boolean loginMode = true;

    public UserLoginView() {
        setSize(500, 400);
        setLayout(null);

        usernameLabel = new JLabel("Enter Username:");
        usernameLabel.setBounds(40, 50, 200, 30);

        usernameField = new JTextField("");
        usernameField.setBounds(40, 80, 200, 30);

        passwordLabel = new JLabel("Enter Password:");
        passwordLabel.setBounds(40, 110, 200, 30);

        passwordField = new JPasswordField("");
        passwordField.setBounds(40, 140, 200, 30);
        ogEcho = passwordField.getEchoChar();

        loginButton = new JButton("Login");
        loginButton.setBounds(160, 180, 80, 30);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setFocusable(false);

        switchModeButton = new JButton("Sign up");
        switchModeButton.setBounds(40, 180, 80, 30);
        switchModeButton.setFocusable(false);

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(switchModeButton);

        passwordField.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                passwordField.setEchoChar((char) 0);
            }

            public void mouseExited(MouseEvent e) {
                passwordField.setEchoChar(ogEcho);
            }

            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getSwitchModeButton() {
        return switchModeButton;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void setLoginMode(boolean login) {
        this.loginMode = login;
        if (login) {
            usernameLabel.setText("Enter Username:");
            passwordLabel.setText("Enter Password:");
            loginButton.setText("Login");
            switchModeButton.setText("Sign up");
        } else {
            usernameLabel.setText("Set Username:");
            passwordLabel.setText("Set Password:");
            loginButton.setText("Create");
            switchModeButton.setText("Back");
        }
    }

    public boolean isLoginMode() {
        return loginMode;
    }

    public void setStatusTitle(String title) {
        setTitle(title);
    }
}
