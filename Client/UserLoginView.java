package Client;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.*;
import Server.User;

public class UserLoginView extends JFrame {

    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pswrd = "";
    private static RMIClient rmiClient;
    private char ogEcho;
    private boolean login = true;
    public static void main(String[] args) throws RemoteException {
        rmiClient = new RMIClient();
        new UserLoginView();
    }
    
    @SuppressWarnings("deprecation")
    private UserLoginView() throws RemoteException {
        setSize(500, 400);
        setLayout(null);
        JLabel l1 = new JLabel("Enter Username:");
        l1.setBounds(40, 50, 200, 30);
        JTextField tx1 = new JTextField("");
        tx1.setBounds(40, 80, 200, 30);
        JLabel l2 = new JLabel("Enter Password:");
        l2.setBounds(40, 110, 200, 30);
        JPasswordField k = new JPasswordField("");
        k.setBounds(40, 140, 200, 30);
        ogEcho = k.getEchoChar();
        JButton b1 = new JButton("Login");
        b1.setBounds(160, 180, 80, 30);
        b1.addActionListener(e->{
            pswrd = new String(k.getPassword());
            System.out.println(pswrd);
            System.out.println(tx1.getText());
            String username = tx1.getText();
            String password = pswrd;
            User user = new User(username, password);
            if (login) {
            
            try {
                user.setUserID(rmiClient.getClientLogic().getUserID(user));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
                
            try {
                if (rmiClient.getClientLogic().authenticateUser(user)) {
                new ChatroomView(user,rmiClient);
                this.dispose();
                }
            } catch (Exception e1) {
                
                e1.printStackTrace();
            }
            
            } else {
            	try {
					rmiClient.getClientLogic().createUser(user);
					setTitle("User succesfully Created");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        b1.setCursor(new Cursor(HAND_CURSOR));
        b1.setFocusable(false);
        JButton b2 = new JButton("Sign up");
        b2.setBounds(40, 180, 80, 30);
        b2.setFocusable(false);
        b2.addActionListener(e->{
        	if (login) {
        		login = false;
        		l1.setText("Set Username:");
        		l2.setText("Set Password:");
        		b1.setText("Create");
        		b2.setText("Back");
        	} else {
        		login = true;
        		l1.setText("Enter Username:");
        		l2.setText("Enter Password:");
        		b1.setText("Login");
        		b2.setText("Sign up");
        	}
        });
        add(b1);
        add(tx1);
        add(l1);
        add(l2);
        add(k);
        add(b2);
        k.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                k.setEchoChar((char)0);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                k.setEchoChar(ogEcho);    
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    protected boolean isIn(Point point, Point location, Dimension size) {
        System.out.println(point);
        if(point.x > location.x && point.x < location.x + size.width)
            if (point.y > location.y && point.y < location.y + size.height)
                return true;
        return false;
    }
}