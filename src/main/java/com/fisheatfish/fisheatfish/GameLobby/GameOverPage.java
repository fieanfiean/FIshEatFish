/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.GameLobby;

/**
 *
 * @author A S U S
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameOverPage {

    private final String username;

    public GameOverPage(String username) {
        this.username = username;
    }

    private static final String FILE_PATH = "src/main/java/com/fisheatfish/fisheatfish/Database/game_data.csv";

    public void showGameOverPopup(Timeline gameLoop, Timeline enemyMovement, Timeline spawnEnemiesTimeline, Pane pane, Stage stage, Player playerFish) {
        // Create a new GridPane for the pop-up
        GridPane popupGrid = new GridPane();
        popupGrid.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Semi-transparent background

        // Stop game elements
        gameLoop.stop();
        enemyMovement.stop();
        spawnEnemiesTimeline.stop();
        saveGameDataToCSV(playerFish, username);


        // Create a label for the "Game Over" message
        Label gameOverLabel = new Label("Game Over!");
        gameOverLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");
        
        Label congratLabel = new Label("Congrat!!\nYou have achieve a new highest score");
        gameOverLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: black;");
        
        Label currentHighestScore = new Label("Highest Score: " + Integer.toString(getHighestScore()));
        currentHighestScore.setStyle("-fx-font-size: 30px; -fx-text-fill: black;");

        Label currentScore = new Label();
        currentScore.setText("Current Score: " + Integer.toString(playerFish.getScore()));
        currentScore.setStyle("-fx-font-size: 30px; -fx-text-fill: black;");

        // Create the "Restart" and "Quit" buttons
        Button restartButton = new Button("Restart");
        Button quitButton = new Button("Quit");

        // Add the label and buttons to the grid (place them in specific rows and columns)
        popupGrid.setAlignment(Pos.CENTER);
        popupGrid.setHgap(65);
        
            System.out.println("PlayerScore: " + playerFish.getScore());
            System.out.println("HighestScore: " + getHighestScore());
        
        if(playerFish.getScore() > getHighestScore()){
            System.out.println("highest score if statement");
            currentHighestScore.setText("Highest Score: " + Integer.toString(playerFish.getScore()));
            popupGrid.add(congratLabel, 0, 1, 2, 1);
        }
        
        popupGrid.add(gameOverLabel, 0, 0, 2, 1); // Label spans across two columns
        popupGrid.add(currentHighestScore, 0, 2);
        popupGrid.add(currentScore, 0, 3);
        popupGrid.add(restartButton, 0, 4);
        popupGrid.add(quitButton, 1, 4);
        
        // Center the GridPane in the window
        popupGrid.setPrefSize(480, 360);
        double centerX = (stage.getWidth() - popupGrid.getPrefWidth()) / 2;
        double centerY = (stage.getHeight() - popupGrid.getPrefHeight()) / 2;
        popupGrid.setLayoutX(centerX);
        popupGrid.setLayoutY(centerY);

        // Add the grid to the main pane
        pane.getChildren().add(popupGrid);

        // Set up button actions
        restartButton.setOnAction(e -> {
            // Restart the game
            Scene newGameScene = restartGame(stage);  // Get the new game scene
            stage.setScene(newGameScene);  // Set the new scene on the stage
            pane.getChildren().remove(popupGrid); // Remove the popup
        });

        quitButton.setOnAction(e -> {
            // Quit the game
            LobbyMainPage lobbyPage = new LobbyMainPage(username);
            Scene lobbyScene = lobbyPage.createLobbyScene(stage);
            stage.setScene(lobbyScene);
        });
    }

    private Scene restartGame(Stage stage) {
        // Reload the scene or create a new one
        GamePage gamePage = new GamePage(username); // Assuming you have a GamePage class
        return gamePage.createGameScene(stage); 
    }

    private void saveGameDataToCSV(Player playerFish, String username) {
        // Reuse the saveGameDataToCSV method
        SaveCsvPage historyPage = new SaveCsvPage();
        historyPage.saveGameDataToCSV(playerFish, username);
    }
    
    public int getHighestScore() {
        int highestScore = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                // Skip the first line (header)
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                // Split the line by the tab or comma separator (depending on your CSV format)
                String[] values = line.split(","); // Use "\t" for tab-separated values or change to "," if comma-separated
                if (values.length < 8) {
                    continue;  // Skip malformed lines with missing values
                }

                try {
                    // Assuming the correct indices based on your CSV structure:
                    String usernameFromCSV = values[1]; // Username is in index 1
                    int score = Integer.parseInt(values[2]); // Score is in index 2

                    if (username.equals(usernameFromCSV)) {
                        if (score > highestScore) {
                            highestScore = score;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing score: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return highestScore;
    }
}
