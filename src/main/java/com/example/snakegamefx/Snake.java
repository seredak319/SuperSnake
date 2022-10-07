package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class Snake{

    private final Pane paneSnake;
    private String currentDirection = "Right";
    private final int bodyParts = 4;
    private final int DELAY = 140;
    private final int[] x = new int[bodyParts];
    private final int[] y = new int[bodyParts];
    private final int size;
    private boolean running = false;
    private final Shoot shoot;


    public Snake(Pane paneSnake, Shoot shoot, int size){
        this.paneSnake = paneSnake;
        this.shoot = shoot;
        this.size = size;
        resetSnake();
        paintSnake();
    }


    private void paintSnake(){
        Platform.runLater((() -> {
            paneSnake.getChildren().clear();
            for (int i = 0; i < bodyParts - 1; i++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setX(x[i]);
                rectangle.setY(y[i]);
                rectangle.setHeight(size);
                rectangle.setWidth(size);
                rectangle.setFill(Color.BLACK);
                paneSnake.getChildren().add(rectangle);
            }
            Rectangle rectangleHead = new Rectangle();
            rectangleHead.setX(x[3]);
            rectangleHead.setY(y[3]);
            rectangleHead.setHeight(size);
            rectangleHead.setWidth(size);
            rectangleHead.setFill(Color.CYAN);
            paneSnake.getChildren().add(rectangleHead);
        }));
    }

    public void resetSnake(){
        for(int i=0; i<bodyParts; i++){
            x[i] = size*i;
            y[i] = 0;
        }
    }

    public void startSnake(){
        running = true;
        Thread thread = new Thread(() -> {
            while (running) {
                moveSnake();
                try {
                    sleep(DELAY);
                } catch (InterruptedException e) {
                    System.out.println("Sleep was interrupted!");
                }
            }
            System.out.println("SnakeDecoration: thread exited;");
            Platform.runLater((() -> {
                paneSnake.getChildren().clear();
            }));
            try {
                currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void moveSnake(){
        checkCollisionsAmmo();

        for(int i=0; i < bodyParts -1; i++){
            x[i] = x[i+1];
            y[i] = y[i+1];
        }

        switch(currentDirection){
            case "Up" -> y[bodyParts-1] = y[bodyParts-1] - size;
            case "Down" -> y[bodyParts-1] = y[bodyParts-1] + size;
            case "Left" -> x[bodyParts-1] = x[bodyParts-1] - size;
            case "Right" -> x[bodyParts-1] = x[bodyParts-1] + size;
        }
        paintSnake();
    }

    private void checkCollisionsAmmo(){
        if(!shoot.getSpawnedAmmo())
            return;

        if(x[bodyParts-1] == shoot.getXOfSpawnedAmmo() && y[bodyParts-1] == shoot.getYOfSpawnedAmmo()){
            shoot.addAmmo();
            shoot.spawnAmmo();
        }
    }


    public boolean isRunning(){
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setCurrentDirection(String currentDirection) {
        this.currentDirection = currentDirection;
    }

    public String getCurrentDirection() {
        return currentDirection;
    }

    public int getSnakeHeadX(){
        return x[bodyParts-1];
    }

    public int getSnakeHeadY(){
        return y[bodyParts-1];
    }
}
