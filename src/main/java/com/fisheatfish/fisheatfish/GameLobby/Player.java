/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.GameLobby;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author A S U S
 */
public class Player {
    private int level;
    private int size;
    private int speed;
    private int score;
    private ImageView imageView;
    private String currentDirection;
    
    private final Map<Integer, Integer> levelUpThresholdMap = new HashMap<>();
    
    public Player(int level,String imagePath){
        this.level = level;
        this.size = size;
        this.speed = speed;
        
        switch(level){
            case 1:
                this.size = 8;  // Small
                this.speed = 2; // Slow
                break;
            case 2:
                this.size = 11;  // Medium
                this.speed = 3; // Moderate
                break;
            case 3:
                this.size = 15; // Large
                this.speed = 4; // Fast
                break;
            case 4:
                this.size = 18; // Extra large
                this.speed = 5; // Very fast
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }
        
        levelUpThresholdMap.put(1, 50);
        levelUpThresholdMap.put(2, 100);
        levelUpThresholdMap.put(3, 200);
        levelUpThresholdMap.put(4, 400);
        
        try {
            Image image = new Image(imagePath); // Attempt to load the image
            this.imageView = new ImageView(image);
        } catch (IllegalArgumentException e) {
            System.err.println("Image not found at: " + imagePath);
            this.imageView = new ImageView(); // Create an empty ImageView as a fallback
        }
        
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(size * 10); // Adjust width based on size
        this.imageView.setFitHeight(size * 5);
        this.currentDirection = "Right";
    }
    
    public int getLevel(){
        return level;
    }
    
    public void setLevel(int level){
        this.level = level;
    }
    
    public int getSize(){
        return size;
    }
    
    public void setSize(int size){
        this.size = size;
    }
    
    public int getSpeed(){
        return speed;
    }
    
    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    public ImageView getImageView(){
        return imageView;
    }
    
    public void grow(int growthFactor) {
        this.size += growthFactor; // Increase size
        this.imageView.setFitWidth(size * 10); // Adjust image size
        this.imageView.setFitHeight(size * 5);
    }   
    
    public void move(double deltaX, double deltaY, double paneWidth, double paneHeight) {
        double newX = imageView.getX() + deltaX * speed;
        double newY = imageView.getY() + deltaY * speed;

        // Ensure the fish stays within the pane's bounds
        if (newX >= 0 && newX + imageView.getFitWidth() <= paneWidth) {
            imageView.setX(newX);
        }
        if (newY >= 0 && newY + imageView.getFitHeight() <= paneHeight) {
            imageView.setY(newY);
        }
    }   
    
    public double getX() {
        return imageView.getX();
    }

    public void setX(double x) {
        imageView.setX(x);
    }

    public double getY() {
        return imageView.getY();
    }

    public void setY(double y) {
        imageView.setY(y);
    }
    
    public void changeDirection(String direction) {
        // Change the image based on the direction
        String imagePath = "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/player" + direction + ".jpeg";
        Image newImage = new Image(imagePath);
        imageView.setImage(newImage);
        currentDirection = direction;
    }
    
    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
        checkLevelUp(level);
    }
    
    private void checkLevelUp(int level) {
        int threshold = levelUpThresholdMap.getOrDefault(level, 50*(1<<level));
        if (score >= threshold) {
            levelUp();
        }
    }
    
    private void levelUp() {
        this.setLevel(this.getLevel() + 1); // Increment player level
        System.out.println("Level Up! New level: " + this.getLevel());
    }
}


