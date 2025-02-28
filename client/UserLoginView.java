package client;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;
import server.User;
import server.PortalConnection;
import server.Subject;

import java.sql.*;

public class UserLoginView extends JFrame implements View{
	private String pswrd = "";
	ClientLogic cl;
	char ogEcho;
	public static void main(String[] args) {
		UserLoginView d = new UserLoginView();
		
		
	}
	@SuppressWarnings("deprecation")
	private UserLoginView(){
		try {
			cl = new ClientLogic((Subject) Naming.lookup("rmi://10.0.49.80/Subject"));
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			System.out.println("oj");
			e.printStackTrace();
		}
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
		JPasswordField k = new JPasswordField();
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
				
					try {
						if (cl.authenticateUser(user)) {
								new ChatroomView(user,cl);
						       this.dispose();
						    }
					} catch (Exception e1) {
						System.out.println("oj");
						e1.printStackTrace();
					}
            
			
		});
		b1.setCursor(new Cursor(HAND_CURSOR));
		b1.setFocusable(false);
		add(b1);
		add(tx1);
		add(l1);
		add(l2);
		add(k);
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
