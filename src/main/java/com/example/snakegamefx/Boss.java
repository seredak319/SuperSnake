package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class Boss {
    private final Container container;
    private final int size = 25;
    private final Pane paneBoss;
    String currentDirection = "Left";
    int bodyParts = 7;
    private final int DELAY;
    private int[] x;
    private int[] y;
    private boolean running = false;
    private Thread threadSnake;
    private final BossMovementAlgorithm badSnakesMovementAlgorithm;
    public int healthPoints = 10;
    private int i = 0;

    Boss(Container container, Pane paneBoss, int delay){
        this.container = container;
        this.paneBoss = paneBoss;
        this.DELAY = delay;
        badSnakesMovementAlgorithm = new BossMovementAlgorithm(container);
        resetBoss();
    }

    public void resetBoss() {
            x = new int[bodyParts];
            y = new int[bodyParts];
            for(int i=0; i<bodyParts; i++){
                x[i] = 10*size;
                y[i] = size*i + 10*size;
            }
    }

    public void killBoss(){
        Platform.runLater((() -> { paneBoss.getChildren().clear(); }));
        if(threadSnake != null) {
           threadSnake.interrupt();
        }
        running = false;
        container.getShoot().setBossFight(false);
        resetBoss();
    }

    private void paintSnake(){
        Platform.runLater((() -> {
            paneBoss.getChildren().clear();
            for (int i = 0; i < bodyParts - 1; i++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setX(x[i]);
                rectangle.setY(y[i]);
                rectangle.setHeight(size);
                rectangle.setWidth(size);
                rectangle.setFill(Color.BLACK);
                paneBoss.getChildren().add(rectangle);
            }
            Rectangle rectangleHead = new Rectangle();
            rectangleHead.setX(x[6]);
            rectangleHead.setY(y[6]);
            rectangleHead.setHeight(size);
            rectangleHead.setWidth(size);
            rectangleHead.setFill(Color.CRIMSON);
            paneBoss.getChildren().add(rectangleHead);
        }));
    }

    public void startBoss(){
        if(!running) {
            running = true;
            threadSnake = new Thread(() -> {
                while (running) {
                    moveBoss();
                    try {
                        sleep(DELAY);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                Platform.runLater((() -> {
                    paneBoss.getChildren().clear();
                }));
                try {
                    currentThread().join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threadSnake.start();
        }
    }

    private void moveBoss(){
        checkCollisionsOfBoss();
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
        i++;
        if(i == 2){
            currentDirection = badSnakesMovementAlgorithm.setCurrentDirection(getSnakeHeadX(),getSnakeHeadY());
            i=0;
        }
    }

    private void checkCollisionsOfBoss() {
        for(int i =0; i<container.getSnake().bodyParts; i++)
            if(getSnakeHeadX() == container.getSnake().getSnakeX(i) && getSnakeHeadY() == container.getSnake().getSnakeY(i)){
             container.getGameFrameControllerSinglePlayer().finishTheGame();
        }
    }

    public boolean isRunning(){
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
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

    public int getBossX(int i) { return x[i];}
    public int getBossY(int i) { return y[i];}

    public void hitBoss(){
        healthPoints--;
        container.getGameFrameControllerSinglePlayer().addPoints();
    }
}
