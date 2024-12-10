/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.GameLobby;

/**
 *
 * @author A S U S
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameHistoryPage {

    private static final String FILE_PATH = "src/main/java/com/fisheatfish/fisheatfish/Database/game_data.csv";

    public void showGameHistoryPopup(String username, Stage parentStage) {
        // Get the latest 20 games
        List<String[]> latestGames = getLatestGames(username);

        // Create the TableView
        TableView<String[]> tableView = new TableView<>();

        // Define columns
        TableColumn<String[], String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));

        TableColumn<String[], String> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));

        TableColumn<String[], String> levelColumn = new TableColumn<>("Level");
        levelColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3]));

        TableColumn<String[], String> eatenColumn = new TableColumn<>("Eaten Stats (1, 2, 3, 4)");
        eatenColumn.setCellValueFactory(data -> new SimpleStringProperty(
            data.getValue()[4] + ", " + data.getValue()[5] + ", " + data.getValue()[6] + ", " + data.getValue()[7]));

        // Add columns to the table
        tableView.getColumns().addAll(dateColumn, scoreColumn, levelColumn, eatenColumn);

        // Add data to the table
        tableView.getItems().addAll(latestGames);

        // Set up the popup
        Stage popupStage = new Stage();
        popupStage.setTitle("Game History: " + username);

        VBox layout = new VBox(10, tableView);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        Scene popupScene = new Scene(layout, 600, 400);
        popupStage.setScene(popupScene);

        // Set modality to make it a true popup
        popupStage.initOwner(parentStage);
        popupStage.show();
    }

    private List<String[]> readGameData(String username) {
        List<String[]> playerGames = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine(); // Read the header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 1 && data[1].equals(username)) { // Match username
                    playerGames.add(data);
                }
            }
        } catch (IOException e) {
            showAlert("Error reading game data: " + e.getMessage());
        }

        return playerGames;
    }

    private List<String[]> getLatestGames(String username) {
        List<String[]> playerGames = readGameData(username);

        // Sort by date (assuming date is the first column and formatted correctly)
        playerGames.sort((a, b) -> b[0].compareTo(a[0]));

        // Limit to the latest 20 games
        return playerGames.stream().limit(20).collect(Collectors.toList());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

