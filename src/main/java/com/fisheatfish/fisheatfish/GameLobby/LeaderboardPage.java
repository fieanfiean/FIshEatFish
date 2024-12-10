/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.GameLobby;

/**
 *
 * @author A S U S
 */
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LeaderboardPage {
    
    private static final String FILE_PATH = "src/main/java/com/fisheatfish/fisheatfish/Database/game_data.csv";

    public void showLeaderboardPopup(Stage parentStage) {
        // Fetch leaderboard data
        List<PlayerRecord> leaderboard = getLeaderboard(FILE_PATH);

        // Create a TableView
        TableView<PlayerRecord> table = new TableView<>();
        table.setPrefSize(400, 300);

        // Define columns
        TableColumn<PlayerRecord, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));

        TableColumn<PlayerRecord, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getScore()).asObject());

        // Add columns to the table
        table.getColumns().addAll(usernameColumn, scoreColumn);

        // Add leaderboard data to the table
        table.getItems().addAll(leaderboard);

        // Create a VBox to hold the table
        VBox layout = new VBox(10, table);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        // Create a popup stage
        Stage popupStage = new Stage();
        popupStage.initOwner(parentStage);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.setTitle("Leaderboard");

        // Add the VBox to the scene
        Scene scene = new Scene(layout, 450, 350);
        popupStage.setScene(scene);
        popupStage.show();
    }

    private List<PlayerRecord> getLeaderboard(String filePath) {
        Map<String, PlayerRecord> playerScores = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // Skip the header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 3) { // Ensure there are enough columns
                    String username = parts[1].trim();
                    int score = Integer.parseInt(parts[2].trim());

                    // Update highest score for the player
                    playerScores.putIfAbsent(username, new PlayerRecord(username, score));
                    if (playerScores.get(username).getScore() < score) {
                        playerScores.put(username, new PlayerRecord(username, score));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading leaderboard data: " + e.getMessage());
        }

        // Return the sorted leaderboard
        return playerScores.values().stream()
                .sorted(Comparator.comparingInt(PlayerRecord::getScore).reversed())
                .collect(Collectors.toList());
    }

    private static class PlayerRecord {
        private final String username;
        private final int score;

        public PlayerRecord(String username, int score) {
            this.username = username;
            this.score = score;
        }

        public String getUsername() {
            return username;
        }

        public int getScore() {
            return score;
        }
    }
}

