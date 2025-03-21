package Server;

import java.io.*;

public class MessageHandler {

    private DatabaseHandler databaseHandler;

    public MessageHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    public byte[] getImageFile(Message message) {
        String imageFilename = databaseHandler.getImagePath(message);
        if (imageFilename == null) {
            System.out.println("Image path is NULL for message ID: " + message.getMessageID() + " in roomId: " + message.getRoomID());
            return null;
        }

        String imagePath = "images/room_" + message.getRoomID() + "/" + imageFilename;

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

    public void uploadImage(File image, int roomID) {
        String imageName = image.getName();
        String outputDir = "images/room_" + roomID;
        new File(outputDir).mkdirs();

        File outputFile = new File(outputDir, imageName);

        try (FileInputStream fis = new FileInputStream(image);
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] imageData = new byte[(int) image.length()];
            fis.read(imageData);
            fos.write(imageData);
            System.out.println("Image received and saved as: " + outputFile.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Error processing image: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}