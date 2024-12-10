/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.GameLobby;

/**
 *
 * @author A S U S
 */
import com.fisheatfish.fisheatfish.GameLobby.Player;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveCsvPage {

    // Method to save game data to CSV file
    public void saveGameDataToCSV(Player playerFish, String username) {
        // Define the file path (you can change the file path as needed)
        String filePath = "src/main/java/com/fisheatfish/fisheatfish/Database/game_data.csv";

        // Create the file if it doesn't exist
        File file = new File(filePath);
        boolean fileExists = file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Write header if the file doesn't exist
            if (!fileExists) {
                writer.write("Date,Username,Score,Level,Eaten(1),Eaten(2),Eaten(3),Eaten(4)\n");
            }

            // Get the current date and time
            String dateTime = getCurrentDateTime();

            // Write the game data (username, score, date/time)
            writer.write(dateTime + "," + username + "," + playerFish.getScore() + "," + playerFish.getLevel() + ","
                    + playerFish.getFishEatenLevel_1() + "," + playerFish.getFishEatenLevel_2() + ","
                    + playerFish.getFishEatenLevel_3() + "," + playerFish.getFishEatenLevel_4() + "\n");

            System.out.println("Game data saved to CSV: " + dateTime + "," + username + "," + playerFish.getScore() + ","
                    + playerFish.getLevel() + "," + playerFish.getFishEatenLevel_1() + ","
                    + playerFish.getFishEatenLevel_2() + "," + playerFish.getFishEatenLevel_3() + ","
                    + playerFish.getFishEatenLevel_4() + "," + filePath);
        } catch (IOException e) {
            // Handle any exceptions that occur during file writing
            showAlert("Error saving game data: " + e.getMessage());
        }
    }

    // Method to get the current date and time in a specific format
    private String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    // Method to show an alert with an error message
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
//        alert.showAndWait();
    }
}
