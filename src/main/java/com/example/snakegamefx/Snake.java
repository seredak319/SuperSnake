package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class Snake{

    private final Pane paneSnake;
    String currentDirection;
    int bodyParts = 4;
    private final int DELAY = 140;
    protected int[] x;
    protected int[] y;
    protected final int size;
    protected boolean running = false;
    public Thread threadSnake;
    private final Container container;


    public Snake(Pane paneSnake, Container container, int size){
        this.paneSnake = paneSnake;
        this.container = container;
        this.size = size;
        resetLevel();
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

    private void resetLevel(){
        x = new int[bodyParts];
        y = new int[bodyParts];
        currentDirection = "Right";
        for(int i=0; i<bodyParts; i++){
            x[i] = size*i;
            y[i] = 0;
        }
    }

    public void killSnake(){
        Platform.runLater((() -> paneSnake.getChildren().clear()));
        if(threadSnake!=null){
            threadSnake.interrupt();
        }
        running = false;
        resetLevel();
    }

    public void startSnake(){
        if(!running) {
            running = true;
            threadSnake = new Thread(() -> {
                while (running) {
                    moveSnake();
                    try {
                        sleep(DELAY);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                Platform.runLater((() -> {
                    paneSnake.getChildren().clear();
                }));
                try {
                    currentThread().join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threadSnake.start();
            container.getShoot().spawnAmmo();
        }
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
        if(getSnakeHeadX() < 0 || getSnakeHeadX() > 27*size || getSnakeHeadY() < 0 || getSnakeHeadY() > 22*size){
            container.getGameFrameControllerSinglePlayer().finishTheGame();
        }

        if(!container.getShoot().getSpawnedAmmo())
            return;

        if(x[bodyParts-1] == container.getShoot().getXOfSpawnedAmmo() && y[bodyParts-1] == container.getShoot().getYOfSpawnedAmmo()){
            container.getShoot().addAmmo();
            container.getShoot().spawnAmmo();
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

    public int getSnakeX(int i) { return x[i];}
    public int getSnakeY(int i) { return y[i];}
}
