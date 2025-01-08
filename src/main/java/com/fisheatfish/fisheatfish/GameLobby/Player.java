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
    private double size;
    private int speed;
    private int score;
    private int fishEatenLevel_1;
    private int fishEatenLevel_2;
    private int fishEatenLevel_3;
    private int fishEatenLevel_4;
    private ImageView imageView;
    private String currentDirection;
    
    private final Map<Integer, Integer> levelUpThresholdMap = new HashMap<>();
    private final Map<String, Image> cachedImages = new HashMap<>(); 
    
    public Player(int level,String imagePath){
        this.level = level;
        this.size = size;
        this.speed = speed;
        
        switch(level){
            case 1:
                this.size = 3;  // Small
                this.speed = 2; // Slow
                break;
            case 2:
                this.size = 8;  // Medium
                this.speed = 3; // Moderate
                break;
            case 3:
                this.size = 13; // Large
                this.speed = 4; // Fast
                break;
            case 4:
                this.size = 18; // Extra large
                this.speed = 5; // Very fast
                break;
            default:
                // Add additional levels if needed
                this.size += 5;  // Increment size for levels beyond 4
                this.speed += 1; // Increment speed for levels beyond 4
                break;
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
        
        cachedImages.put("LEFT", new Image("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/playerLeft.png"));
        cachedImages.put("RIGHT", new Image("file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/playerRight.png"));
    }
    
 
   
    private void adjustAttributesForLevel(int newLevel) {
        switch (newLevel) {
            case 1:
                this.size = 3;  // Small
                this.speed = 2;  // Slow
                break;
            case 2:
                this.size = 8;  // Medium
                this.speed = 3;  // Moderate
                break;
            case 3:
                this.size = 13;  // Large
                this.speed = 4;  // Fast
                break;
            case 4:
                this.size = 18;  // Extra large
                this.speed = 5;  // Very fast
                break;
            default:
                // Add additional levels if needed
                this.size += 5;  // Increment size for levels beyond 4
                this.speed += 1; // Increment speed for levels beyond 4
                break;
        }

        // Update the image size to reflect the new size
        this.imageView.setFitWidth(this.size * 10);
        this.imageView.setFitHeight(this.size * 5);
    }
    
    public int getLevel(){
        return level;
    }
    
    public void setLevel(int level){
        this.level = level;
    }
    
    public double getSize(){
        return size;
    }
    
    public void setSize(double size){
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
    
   
    
    public void grow(double growthFactor) {
        this.size += growthFactor; // Increase size
        this.imageView.setFitWidth(size *10); // Adjust image size
        this.imageView.setFitHeight(size * 5);
    }   
    
    public void move(double deltaX, double deltaY, double paneWidth, double paneHeight) {
    double newX = imageView.getX() + deltaX;
    double newY = imageView.getY() + deltaY;

    // Ensure the fish stays within the pane's bounds
    if (newX < 0) {
        newX = 0; // Prevent going out of the left boundary
    } else if (newX + imageView.getFitWidth() > paneWidth) {
        newX = paneWidth - imageView.getFitWidth(); // Prevent going out of the right boundary
    }

    if (newY < 0) {
        newY = 0; // Prevent going out of the top boundary
    } else if (newY + imageView.getFitHeight() > paneHeight) {
        newY = paneHeight - imageView.getFitHeight(); // Prevent going out of the bottom boundary
    }

    imageView.setX(newX);
    imageView.setY(newY);

    System.out.println("Current position：X:" + newX + " Y: " + newY);
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
    
//    public void changeDirection(String direction) {
//        // Change the image based on the direction
//        String imagePath = "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/player" + direction + ".png";
//        Image newImage = new Image(imagePath);
//        imageView.setImage(newImage);
//        currentDirection = direction;
//        setCurrentDirection(direction);
//    }
    
    public void changeDirection(String direction) {
        Image newImage = cachedImages.get(direction);
        System.out.println("Current file path: " + newImage);
        if (newImage != null) {
            imageView.setImage(newImage);
            setCurrentDirection(direction);
        } else {
            System.err.println("Image for direction " + direction + " not found!");
        }
    }

    
    public void setCurrentDirection(String direction){
        this.currentDirection = direction;
    }
    
    public String getCurrentDirection(){
        return this.currentDirection;
    }
    
    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
        checkLevelUp(level);
    }
    
    public void countFishEaten(int level){
        switch(level){
            case 1: this.fishEatenLevel_1 += 1; break;
            case 2: this.fishEatenLevel_2 += 1; break;
            case 3: this.fishEatenLevel_3 += 1; break;
            case 4: this.fishEatenLevel_4 += 1; break;
        }
    }
    
    public int getFishEatenLevel_1(){
        return fishEatenLevel_1;
    }
    
    public int getFishEatenLevel_2(){
        return fishEatenLevel_2;
    }
    
    public int getFishEatenLevel_3(){
        return fishEatenLevel_3;
    }
    
    public int getFishEatenLevel_4(){
        return fishEatenLevel_4;
    }
    
    private void checkLevelUp(int level) {
        int threshold = levelUpThresholdMap.getOrDefault(level, 50*(1<<level));
        if (score >= threshold) {
            levelUp();
        }
    }
    
    private void levelUp() {
        String imagePath = "file:src/main/java/com/fisheatfish/fisheatfish/Asset/Image/player" + getCurrentDirection() + ".png";
        this.setLevel(this.getLevel() + 1); // Increment player level
        adjustAttributesForLevel(this.getLevel());
        System.out.println("Level Up! New level: " + this.getLevel());
        System.out.println("Current speed: " + this.getSpeed());
    }
}


