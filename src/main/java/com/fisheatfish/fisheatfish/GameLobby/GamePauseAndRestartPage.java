/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.GameLobby;

/**
 *
 * @author A S U S
 */
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GamePauseAndRestartPage {
    private final String username;
    private GameMusic musicManager;


    public GamePauseAndRestartPage(String username, GameMusic musicManager) {
        this.username = username;
        this.musicManager = musicManager;
    }
    
    public Scene restartGame(Stage stage) {
        // Reload the scene or create a new one
        GamePage gamePage = new GamePage(username); // Assuming you have a GamePage class
        return gamePage.createGameScene(stage);
    }

    public void pauseGame(Timeline gameLoop, Timeline enemyMovement, Timeline spawnEnemiesTimeline, Pane pane, Stage stage, Player playerFish) {
        GameMusic buttonEffect = new GameMusic();
        GridPane popupGrid = new GridPane();
        popupGrid.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        Label gamePauseLabel = new Label("Game Paused");
        gamePauseLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");

        Button continueButton = new Button("Continue");
        Button endGameButton = new Button("End Game");

        // Pause game elements
        gameLoop.pause();
        enemyMovement.pause();
        spawnEnemiesTimeline.pause();

        continueButton.setOnAction(event -> {
            // Resume game elements
            buttonEffect.playEffect("buttonEffect");
            gameLoop.play();
            enemyMovement.play();
            spawnEnemiesTimeline.play();
            pane.getChildren().remove(popupGrid);
        });

        endGameButton.setOnAction(e -> {
            // Quit the game and return to the lobby
            buttonEffect.playEffect("buttonEffect");
//            saveGameDataToCSV(playerFish, username);
            pane.getChildren().remove(popupGrid);
            GameOverPage gameOverPage = new GameOverPage(username,musicManager);
            gameOverPage.showGameOverPopup(gameLoop, enemyMovement, spawnEnemiesTimeline, pane, stage, playerFish, musicManager);
        });

        // Set up the layout for the popup
        popupGrid.setAlignment(Pos.CENTER);
        popupGrid.setHgap(60);
        popupGrid.add(gamePauseLabel, 0, 0, 2, 1); // Label spans across two columns
        popupGrid.add(continueButton, 0, 1);
        popupGrid.add(endGameButton, 1, 1);

        // Center the GridPane in the window
        popupGrid.setPrefSize(480, 360);
        double centerX = (stage.getWidth() - popupGrid.getPrefWidth()) / 2;
        double centerY = (stage.getHeight() - popupGrid.getPrefHeight()) / 2;
        popupGrid.setLayoutX(centerX);
        popupGrid.setLayoutY(centerY);

        // Add the grid to the main pane
        pane.getChildren().add(popupGrid);
    }

    private void saveGameDataToCSV(Player playerFish, String username) {
        // You can reuse the saveGameDataToCSV method you already implemented
        SaveCsvPage historyPage = new SaveCsvPage();
        historyPage.saveGameDataToCSV(playerFish, username);
    }
}
