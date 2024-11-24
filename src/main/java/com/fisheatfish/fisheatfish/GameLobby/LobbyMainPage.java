/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.GameLobby;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.util.Duration;


/**
 *
 * @author A S U S
 */
public class LobbyMainPage {
    public Scene createLobbyScene(Stage stage){
        Image playButton = new Image("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/playButton.png");
        
        ImageView imageView = new ImageView(playButton);
        imageView.setFitWidth(200); 
        imageView.setFitHeight(100);
        
        Button playImageButton = new Button();
        playImageButton.setGraphic(imageView);
        playImageButton.setStyle("-fx-background-color: white;");
        
        playImageButton.setOnAction(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), playImageButton);
            scale.setFromX(1.0);
            scale.setFromY(1.0);
            scale.setToX(1.2); // Slightly enlarge the button
            scale.setToY(1.2);
            scale.setAutoReverse(true);
            scale.setCycleCount(2); // Shrink back after enlarging
            scale.play();
            // Add your logic here, e.g., navigating to another scene
        });
        

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        
        grid.setAlignment(Pos.CENTER);
        grid.add(playImageButton, 0, 0);
        
        
        
        
        return new Scene(grid, 640, 480);
    }
}
