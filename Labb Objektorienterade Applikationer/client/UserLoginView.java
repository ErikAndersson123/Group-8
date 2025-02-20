package client;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import server.User;
import server.PortalConnection;
import java.sql.*;

public class UserLoginView extends JFrame implements View {
    private String pswrd = "";
    public static void main(String[] args) {
        UserLoginView d = new UserLoginView();
        
        
    }
    @SuppressWarnings("deprecation")
    private UserLoginView() {
        setSize(500, 400);
        setLayout(null);
        JLabel l1 = new JLabel("Enter Username:");
        l1.setBounds(40, 50, 200, 30);
        JTextField tx1 = new JTextField("");
        tx1.setBounds(40, 80, 200, 30);
        tx1.addActionListener(e->{
            System.out.println(tx1.getText());
        });
        JLabel l2 = new JLabel("Enter Password:");
        l2.setBounds(40, 110, 200, 30);
        JTextField tx2 = new JTextField("");
        tx2.setBounds(40, 140, 200, 30);
        tx2.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent e) {
                tx2.setText(pswrd);
                
            }

            @Override
            public void focusLost(FocusEvent e) {
                pswrd = tx2.getText();
                tx2.setText("");
                for (int i = 0; i < pswrd.length(); i++) {
                    tx2.setText("*"+tx2.getText());
                }
            }
            
        });
    
        JButton b1 = new JButton("Login");
        b1.setBounds(160, 180, 80, 30);
        b1.addActionListener(e->{
            System.out.println(tx2.getText());
            System.out.println(tx1.getText());
            String username = tx1.getText();
            String password = tx2.getText();
            PortalConnection p = new PortalConnection();
            User user = new User(username, password);
            if (p.authenticateUser(user)) {
                new ChatroomView();
                this.dispose();
            }
        });
        b1.setCursor(new Cursor(HAND_CURSOR));
        b1.setFocusable(false);
        add(b1);
        add(tx1);
        add(l1);
        add(tx2);
        add(l2);
        addMouseListener(new MouseListener() {
            
            
        

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if(isIn(e.getPoint(),tx2.getLocation(),tx2.getSize())) {
                    tx2.requestFocus();
                    System.out.println("d");
                }
                else if(isIn(e.getPoint(),tx1.getLocation(),tx1.getSize())) {
                    tx1.requestFocus();
                
                }    else
                    requestFocus();
                    
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                
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
