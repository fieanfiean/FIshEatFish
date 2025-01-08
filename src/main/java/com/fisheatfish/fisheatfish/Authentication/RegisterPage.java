/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.Authentication;

import com.fisheatfish.fisheatfish.Database.MongoDBConnection;
import com.fisheatfish.fisheatfish.GameLobby.GameMusic;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author A S U S
 */
public class RegisterPage {
    public Scene createRegisterScene(Stage stage) {
        GameMusic buttonEffect = new GameMusic();

        Label usernameLabel = new Label("Username: ");
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        TextField usernameField = new TextField();

        Label nameLabel = new Label("Name: ");
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        TextField nameField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        PasswordField passwordField = new PasswordField();

        Label passwordComfirmLabel = new Label("Confirmation Password: ");
        passwordComfirmLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        PasswordField passwordComfirmField = new PasswordField();
        
        Button registerButton = new Button("Register");
        Button cancelButton = new Button("Cancel");
        
        cancelButton.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: black;"); // Light blue background, white text
        registerButton.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: black;"); // Light blue background, white text

        // Parent container to hold everything
        StackPane root = new StackPane();

        // Smaller grid for details
        GridPane detailsGrid = new GridPane();
        detailsGrid.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        detailsGrid.setPadding(new Insets(20)); // Add padding for spacing inside the smaller grid
        detailsGrid.setVgap(10);
        detailsGrid.setHgap(10);
        detailsGrid.setMaxWidth(400); // Optional: Limit the width of the smaller grid
        detailsGrid.setMaxHeight(200);
        detailsGrid.setAlignment(Pos.CENTER);

        // Add labels and fields to the smaller grid
        detailsGrid.add(usernameLabel, 0, 0);
        detailsGrid.add(usernameField, 1, 0);
        detailsGrid.add(nameLabel, 0, 1);
        detailsGrid.add(nameField, 1, 1);
        detailsGrid.add(passwordLabel, 0, 2);
        detailsGrid.add(passwordField, 1, 2);
        detailsGrid.add(passwordComfirmLabel, 0, 3);
        detailsGrid.add(passwordComfirmField, 1, 3);
        detailsGrid.add(cancelButton, 0, 4);
        GridPane.setHalignment(cancelButton, HPos.RIGHT);
        detailsGrid.add(registerButton, 1, 4);

        // Set background image for the parent container
        Image backgroundImage = new Image("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/AuthenticationBackground.jpg");
        BackgroundImage bgImage = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        root.setBackground(new Background(bgImage));

        // Add the smaller grid to the parent container
        root.getChildren().add(detailsGrid);
        
        cancelButton.setOnAction(e -> {
            buttonEffect.playEffect("buttonEffect");
            FirstPage firstPage = new FirstPage();
            Scene firstScene = firstPage.createFirstScene(stage);
            stage.setScene(firstScene); 
        });
        
        registerButton.setOnAction(e ->{
            buttonEffect.playEffect("buttonEffect");
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
    
        
        return new Scene(root, 640, 480);
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
