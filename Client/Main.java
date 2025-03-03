package Client;

import java.awt.BorderLayout;
import java.io.FileOutputStream;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class Main {
    public static void main(String[] args) {
        try {
            RMIClient server = new RMIClient();
            
            byte[] imageData = server.getClientLogic().getImageFile(1); 

            if (imageData != null) {
                String outputPath = "received_image"+ 1 +".jpg";
                FileOutputStream fos = new FileOutputStream(outputPath);
                fos.write(imageData);
                fos.close();
                System.out.println("Image received and saved as: " + outputPath);
            } else {
                System.out.println("No image found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        showImage("received_image1.jpg");

        
    }
    public static void showImage(String imagePath) {
        JFrame frame = new JFrame("Received Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel label = new JLabel(imageIcon);

        frame.add(label, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
