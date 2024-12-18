/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.Authentication;

import com.fisheatfish.fisheatfish.Database.MongoDBConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;


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
        
        registerButton.setOnAction(e ->{
            String username = usernameField.getText().trim();
            String name = nameField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = passwordComfirmField.getText().trim();
            
            if (username.isEmpty() || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showAlert("All fields must be filled!");
                return;
            }
            
            if(!password.equals(confirmPassword)){
                showAlert("Psswords do not match");
            }
            
            try{
                String hashedPassword = hashPassword(password);
                MongoDatabase database = MongoDBConnection.connectToDatabase();
                MongoCollection<Document>  userCollection = database.getCollection("Users");
                
                Document existingUser = userCollection.find(new Document("username", username)).first();

                if (existingUser != null) {
                    showAlert("Username is already taken. Please choose another one.");
                    return;
                }

                Document userDocument = new Document("username",username)
                        .append("name",name)
                        .append("password",hashedPassword);
                userCollection.insertOne(userDocument);
                showAlert("Registration successful");
                
                LoginPage loginPage = new LoginPage();
                Scene loginScene = loginPage.createLoginScene(stage);
                stage.setTitle("Login Page");
                stage.setScene(loginScene);
                
                FindIterable<Document> users = userCollection.find();
        
                // Iterate through the result set and print each document
                for (Document user : users) {
                    System.out.println(user.toJson());
                }
                
            }catch(Exception ex){
                showAlert("Error When Register User: " + ex.getMessage());
            }
       });
    
        
        return new Scene(grid, 640, 480);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registrtion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verify the entered password against the stored hashed password
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
    
}
