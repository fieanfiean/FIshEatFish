/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author A S U S
 */
public class RegisterPage {
    public Scene createRegisterScene(Stage stage) {
        
        Label usernameLabel = new Label("Username: ");
        TextField usernameField = new TextField();

        Label nameLabel = new Label("Name: ");
        TextField nameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Label passwordComfirmLabel = new Label("Confirmation Password: ");
        PasswordField passwordComfirmField = new PasswordField();
        
        Button registerButton = new Button("Register");
        Button cancelButton = new Button("Cancel");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(passwordComfirmLabel, 0, 3);
        grid.add(passwordComfirmField, 1, 3);
        grid.add(cancelButton, 0, 4);
        GridPane.setHalignment(cancelButton, HPos.RIGHT);
        grid.add(registerButton, 1, 4);
        
        cancelButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage();
            Scene loginScene = loginPage.createLoginScene(stage);
            stage.setTitle("Login Page");
            stage.setScene(loginScene);
        });
        
    
        
        return new Scene(grid, 640, 480);
    }
    
}