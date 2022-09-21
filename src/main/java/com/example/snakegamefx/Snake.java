package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

public class Snake{

    private final Pane paneSnake;
    private final Pane paneShoot;
    private final Label bulletsAmount;
    private final Pane paneSpawn;
    private String currentDirection = "Right";
    private final int bodyParts = 4;
    private final int DELAY = 200;
    private int x[] = new int[bodyParts];
    private int y[] = new int[bodyParts];
    private static Timer timerSnake;
    private static TimerTask timerTaskSnake;
    private final int size;
    private boolean running = false;
    private Shoot shoot;


    public Snake(Pane paneSnake, Pane paneShoot, Pane paneSpawn, Label bulletsAmount, int size){
        this.paneSnake = paneSnake;
        this.paneShoot = paneShoot;
        this.paneSpawn = paneSpawn;
        this.bulletsAmount = bulletsAmount;
        this.size = size;
        shoot = new Shoot(size,paneShoot,paneSpawn, bulletsAmount);
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
        shoot.spawnAmmo();
        //Initiation of snake
        timerSnake = new Timer();
        timerTaskSnake = new TimerTask() {
            @Override
            public void run() {
                moveSnake();
            }
        };
        timerSnake.schedule(timerTaskSnake,0,DELAY);
    }

    void moveSnake(){
        if(!running)
            killSnake();

        checkCollisionsAmmo();

        for(int i=0; i < bodyParts -1; i++){
            x[i] = x[i+1];
            y[i] = y[i+1];
        }

        switch(currentDirection){
            case "Up" -> {
                y[bodyParts-1] = y[bodyParts-1] - size;
            }
            case "Down" -> {
                y[bodyParts-1] = y[bodyParts-1] + size;
            }
            case "Left" -> {
                x[bodyParts-1] = x[bodyParts-1] - size;
            }
            case "Right" -> {
                x[bodyParts-1] = x[bodyParts-1] + size;
            }
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

    public void killSnake(){
        timerTaskSnake.cancel();
        timerSnake.cancel();
    }

    public void doShot(){
        shoot.doShoot(x[bodyParts-1],y[bodyParts-1],currentDirection);
    }

    public TimerTask getTimerTaskSnake() {
        return timerTaskSnake;
    }

    public Timer getTimerSnake() {
        return timerSnake;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getAmountOfAmmo(){
        return shoot.getSTART_VALUE();
    }

    public void setCurrentDirection(String currentDirection) {
        this.currentDirection = currentDirection;
    }

    public String getCurrentDirection() {
        return currentDirection;
    }

    public boolean isShoot(){
        return shoot.isShot();
    }

    public int getSnakeHeadX(){
        return x[bodyParts-1];
    }

    public int getSnakeHeadY(){
        return y[bodyParts-1];
    }
}
