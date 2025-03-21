package Client;

public class Main {
    public static void main(String[] args) {
        try {
            ChatroomObserver observer = new ChatroomObserver();
            RMIClient rmiClient = new RMIClient(observer); 

            UserLoginView loginView = new UserLoginView();
            UserLoginController userLoginController = new UserLoginController(loginView, rmiClient, observer);

            loginView.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
