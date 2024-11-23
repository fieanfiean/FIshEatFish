/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.Authentication;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/**
 *
 * @author A S U S
 */
public class LoginPage {
    public Scene createLoginScene(Stage stage) {
        // Create labels and input fields
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        
        Button loginButton = new Button("Login");
        Label statusLabel = new Label();

        // Create the Register hyperlink
        Hyperlink registerLink = new Hyperlink("Register");
        registerLink.setOnAction(e -> {
            // Handle the registration action (open registration window or show registration form)
             RegisterPage registerPage = new RegisterPage();
             Scene registerScene = registerPage.createRegisterScene(stage);
             stage.setTitle("Register Page");
             stage.setScene(registerScene);
            // For now, just printing to the console
        });

        // Create the GridPane for the layout
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        
        grid.setAlignment(Pos.CENTER);
        
        // Add controls to the grid
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(statusLabel, 1, 3);
        grid.add(registerLink, 1, 4); // Add the Register hyperlink to the grid

        // Handle login button click
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            if (username.equals("admin") && password.equals("password")) {
                statusLabel.setText("Login Successful!");
            } else {
                statusLabel.setText("Invalid Credentials. Try again.");
            }
        });

        // Return the Scene
        return new Scene(grid, 640, 480);
    }
}
