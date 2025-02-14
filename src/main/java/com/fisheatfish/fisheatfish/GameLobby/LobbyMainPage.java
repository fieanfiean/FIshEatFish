/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.GameLobby;
import com.fisheatfish.fisheatfish.Authentication.FirstPage;
import com.fisheatfish.fisheatfish.Authentication.LoginPage;
import com.fisheatfish.fisheatfish.GameLobby.GamePage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


/**
 *
 * @author A S U S
 */
public class LobbyMainPage {
    
    private String username;
    
    public LobbyMainPage(String username) {
        this.username = username;
    }
    
    public Scene createLobbyScene(Stage stage){
        stage.setTitle("LobbyMainPage");
        GameMusic buttonEffect = new GameMusic();

//        Image playButton = new Image("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/playButton.png");
        
//        ImageView imageView = new ImageView(playButton);
//        imageView.setFitWidth(200); 
//        imageView.setFitHeight(100);
        
        Button playButton = new Button("Play");
        Button gameHistoryButton = new Button("History");
        Button leaderboardButton = new Button("Leaderboard");
        Button logoutButton = new Button("Logout");
//        playImageButton.setGraphic(imageView);
//        playImageButton.setStyle("-fx-background-color: white;");

        // Set background and text color using inline style
        playButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Green background, white text
        gameHistoryButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;"); // Blue background, white text
        leaderboardButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;"); // Orange background, white text
        logoutButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;"); // Red background, white text

        
        double buttonWidth = 100; // Desired button width
        double buttonHeight = 30; // Desired button height

        playButton.setPrefWidth(buttonWidth);
        playButton.setPrefHeight(buttonHeight);

        gameHistoryButton.setPrefWidth(buttonWidth);
        gameHistoryButton.setPrefHeight(buttonHeight);

        leaderboardButton.setPrefWidth(buttonWidth);
        leaderboardButton.setPrefHeight(buttonHeight);

        logoutButton.setPrefWidth(buttonWidth);
        logoutButton.setPrefHeight(buttonHeight);

        playButton.setOnAction(e -> {
        buttonEffect.playEffect("buttonEffect");
//            ScaleTransition scale = new ScaleTransition(Duration.millis(200), playButton);
//            scale.setFromX(1.0);
//            scale.setFromY(1.0);
//            scale.setToX(1.2); // Slightly enlarge the button
//            scale.setToY(1.2);
//            scale.setAutoReverse(true);
//            scale.setCycleCount(2); // Shrink back after enlarging
//            scale.play();
            // Add your logic here, e.g., navigating to another scene
            GamePage gamePage = new GamePage(username);
            Scene gameScene = gamePage.createGameScene(stage);
            stage.setScene(gameScene);
            System.out.print("Button Pressed");
        });
        
        gameHistoryButton.setOnAction(event->{
            buttonEffect.playEffect("buttonEffect");
            showGameHistoryPopup(username);
        });
        
        leaderboardButton.setOnAction(event ->{
            buttonEffect.playEffect("buttonEffect");
            LeaderboardPage leaderboardPage = new LeaderboardPage();
            leaderboardPage.showLeaderboardPopup(stage);
        });
        
        logoutButton.setOnAction(event ->{
            buttonEffect.playEffect("buttonEffect");
            FirstPage firstPage = new FirstPage();
            Scene firstScene = firstPage.createFirstScene(stage);
            stage.setScene(firstScene);
        });

        GridPane grid = new GridPane();
        
        Image backgroundImage = new Image("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/LobbyBackground.jpg");

        BackgroundImage bgImage = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT, // Repeat X
            BackgroundRepeat.NO_REPEAT, // Repeat Y
            BackgroundPosition.CENTER, // Position
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true) // Sizing
        );

        grid.setBackground(new Background(bgImage));
        
        grid.setVgap(10);
        grid.setHgap(10);
        
        grid.setAlignment(Pos.CENTER);
        grid.add(playButton, 0, 0);
        grid.add(gameHistoryButton, 0, 1);
        grid.add(leaderboardButton,0,2);
        grid.add(logoutButton,0,3);
        
        
        
        return new Scene(grid, 640, 480);
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    private List<String[]> readGameData(String username) {
        String filePath = "src/main/java/com/fisheatfish/fisheatfish/Database/game_data.csv";
        List<String[]> playerGames = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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

        // Limit to latest 20 games
        return playerGames.stream().limit(20).collect(Collectors.toList());
    }
    
    private void showGameHistoryPopup(String username) {
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

        TableColumn<String[], String> eatenColumn = new TableColumn<>("Eaten Stats(1,2,3,4)");
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
        popupStage.show();
    }

    private void showAlert(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
