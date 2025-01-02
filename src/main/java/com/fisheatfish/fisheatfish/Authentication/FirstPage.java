/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.Authentication;

import com.fisheatfish.fisheatfish.GameLobby.GameMusic;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author A S U S
 */
public class FirstPage {
    public Scene createFirstScene(Stage stage) {
        GameMusic buttonEffect = new GameMusic();
        stage.setTitle("Fish Eat Fish");

        Button registerButton = new Button("Register");
        Button loginButton = new Button("Login");
        
        loginButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;"); // Orange background, white text
        registerButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;"); // Orange background, white text

        GridPane grid = new GridPane();
        grid.setVgap(100);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        
        grid.add(new Label(""), 0, 0); // Empty label in row 0
        grid.add(loginButton, 0, 1);
        grid.add(registerButton, 1, 1);
        
        Image backgroundImage = new Image("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/theme.png");

        BackgroundImage bgImage = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT, // Repeat X
            BackgroundRepeat.NO_REPEAT, // Repeat Y
            BackgroundPosition.CENTER, // Position
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true) // Sizing
        );

        grid.setBackground(new Background(bgImage));

        loginButton.setOnAction(e->{
            buttonEffect.playEffect("buttonEffect");
            LoginPage loginPage = new LoginPage();
            Scene loginScene = loginPage.createLoginScene(stage);
            stage.setScene(loginScene);
        });
        
        registerButton.setOnAction(e -> {
            // Handle the registration action (open registration window or show registration form)
             buttonEffect.playEffect("buttonEffect");
             RegisterPage registerPage = new RegisterPage();
             Scene registerScene = registerPage.createRegisterScene(stage);
             stage.setTitle("Register Page");
             stage.setScene(registerScene);
            // For now, just printing to the console
        });

        
        return new Scene(grid, 640, 480);
    }
}
