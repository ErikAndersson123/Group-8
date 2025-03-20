package Server;

import java.util.*;
import java.io.*;

public class MessageHandler {
    
    private DatabaseHandler databaseHandler;
    
    public MessageHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }
    
    public byte[] getImageFile(Message message) {
        String imageFilename = databaseHandler.getImagePath(message);
        System.out.println(imageFilename);

        if (imageFilename == null) {
            System.out.println("Image path is NULL for message ID: " + message.getMessageID()+ " in roomId: " + message.getRoomID());
            return null;
        }
        
        //Change this
        String baseDir = "C:\\Users\\Per\\Desktop\\Labb Objektorienterade Applikationer\\Labb Objektorienterade Applikationer v.17\\Labb Objektorienterade Applikationer v.17\\";

        String imagePath = baseDir +imageFilename;

        System.out.println(imagePath);
    
        File file = new File(imagePath);
        if (!file.exists()) {
            System.out.println("Image file not found at: " + imagePath);
            return null;
        }
    
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileData = new byte[(int) file.length()];
            fis.read(fileData);
            return fileData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void uploadImage(File image){
        String imageName = image.getName();
        String outputPath = imageName;

        try (FileInputStream fis = new FileInputStream(image);
             FileOutputStream fos = new FileOutputStream(outputPath)) {

            byte[] imageData = new byte[(int) image.length()];
            fis.read(imageData);
            fos.write(imageData);

            System.out.println("Image received and saved as: " + outputPath);

        } catch (IOException ex) {
            System.out.println("Error processing image: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}