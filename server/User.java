package server;

import java.sql.SQLException;

public class User {
	private String name;
	PortalConnection p = new PortalConnection();
	private String password;
	public String getName() {
		return name;
	}
	//This method can't be public because we use a users username to identify a user, allowing users to change this username would cause a lot of problems rn
	private void setName(String name) throws ClassNotFoundException, SQLException {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) throws ClassNotFoundException, SQLException {
		this.password = password;
		updateInfo();
		
	}
	public User(String name, String password) throws SQLException, ClassNotFoundException {
		setName(name);
		this.password = password; //we cannot use setPassword cause that would call the updateInfo function and we want to minimize the use of the database
		p.register(this.name, this.password);
	}
	private void updateInfo() {
		p.updateUserInfo(name, password);
	}
	
}
