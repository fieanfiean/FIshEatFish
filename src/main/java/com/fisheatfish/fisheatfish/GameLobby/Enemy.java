/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fisheatfish.fisheatfish.GameLobby;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 *
 * @author A S U S
 */
public class Enemy {
    private int level;
    private int size;
    private int speed;
    private String direction;
    private String leftImagePath;
    private String rightImagePath;
    private ImageView imageView;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
        updateImage();
    }

    
    public Enemy(int level, String leftImagePath,String rightImagePath) {
        this.level = level;

        // Set size and speed based on level
        switch (level) {
            case 1:
                this.size = 5;  // Small
                this.speed = 1; // Slow
                break;
            case 2:
                this.size = 8;  // Medium
                this.speed = 2; // Moderate
                break;
            case 3:
                this.size = 12; // Large
                this.speed = 3; // Fast
                break;
            case 4:
                this.size = 15; // Extra large
                this.speed = 4; // Very fast
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }
        
        this.leftImagePath = leftImagePath;
        this.rightImagePath = rightImagePath;

        this.direction = "RIGHT";
        
        try {
            Image image = new Image(rightImagePath);
            this.imageView = new ImageView(image);
        } catch (IllegalArgumentException e) {
            System.err.println("Image not found at: " + rightImagePath);
            this.imageView = new ImageView(); // Fallback
        }

        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(size * 10); // Adjust size
        this.imageView.setFitHeight(size * 5);
    }

    public int getLevel() {
        return level;
    }

    public int getSize() {
        return size;
    }

    public int getSpeed() {
        return speed;
    }
    
    public void setSpeed(int speed){
        this.speed = speed;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void move(double deltaX, double deltaY) {
        imageView.setX(imageView.getX() + deltaX);
        imageView.setY(imageView.getY() + deltaY);
    }

    public void setX(double x) {
        imageView.setX(x);
    }

    public void setY(double y) {
        imageView.setY(y);
    }
    
    public double getX(){
        return imageView.getX();
    }
    
    public double getY(){
        return imageView.getY();
    }
    
     public void moveHorizontally() {
        if ("RIGHT".equals(direction)) {
            setX(getX() + speed); // Move right
        } else if ("LEFT".equals(direction)) {
            setX(getX() - speed); // Move left
        }
    }
     
     // Method to update the image based on direction
    private void updateImage() {
        if ("RIGHT".equals(direction)) {
            imageView.setImage(new Image(rightImagePath)); // Image for moving right
        } else if ("LEFT".equals(direction)) {
            imageView.setImage(new Image(leftImagePath)); // Image for moving left
        }
    }
    
    
}
