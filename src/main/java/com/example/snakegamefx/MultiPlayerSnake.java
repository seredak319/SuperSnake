package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class MultiPlayerSnake {

    private final Pane paneSnake;
    private final Pane paneShoot;
    private final Pane paneSpawn;
    private final Label bullAmount;
    private final Label healthPoints;
    String currentDirection;
    int bodyParts = 4;
    private final int DELAY;
    protected int[] x;
    protected int[] y;
    protected int size;
    protected boolean running = false;
    public Thread threadSnake;
    private final Container container;
    private final int whichPlayer;
    private final int startX;
    private final int startY;
    private int health = 3;
    private final String startDirection;
    private final Scene scene;
    public MultiplayerShoot shoot;
    private final int startValueOfBullets = 5;
    private final int startValueOfHealth = 3;
    private Color headColor;
    private final int shootDELAY;
    private boolean justShot = false;
    private int count = 0;
    private int howManyTicksInvisible=8;




    public MultiPlayerSnake(Pane paneSnake,Pane paneShoot,Pane paneSpawn,Label ammo, Label healthPoints, Container container, int size, int DELAY, int shootDELAY, int whichPlayer, int startX, int startY, String startDirection, Scene scene){
        this.paneSnake = paneSnake;
        this.container = container;
        this.size = size;
        this.DELAY = DELAY;
        this.whichPlayer = whichPlayer;
        this.startX = startX;
        this.startY = startY;
        this.startDirection = startDirection;
        this.scene = scene;
        this.paneShoot = paneShoot;
        this.paneSpawn = paneSpawn;
        this.bullAmount = ammo;
        this.shootDELAY = shootDELAY;
        this.healthPoints = healthPoints;

        if(whichPlayer == 1){
            headColor = Color.CYAN;
        } else if(whichPlayer == 2){
            headColor = Color.RED;
        } else {
            headColor = Color.BLACK;
        }

        initLevel();
    }

    private void initLevel(){
        shoot = new MultiplayerShoot(size,paneShoot,paneSpawn, bullAmount, container, startValueOfBullets,whichPlayer, shootDELAY);
        Platform.runLater((() -> healthPoints.setText(Integer.toString(health))));
        resetLevel();
        paintSnake();
    }


    private void paintSnake(){
        if(!justShot){
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
                rectangleHead.setX(x[bodyParts-1]);
                rectangleHead.setY(y[bodyParts-1]);
                rectangleHead.setHeight(size);
                rectangleHead.setWidth(size);
                rectangleHead.setFill(headColor);
                paneSnake.getChildren().add(rectangleHead);
            }));
        } else {
            if(count == howManyTicksInvisible){
                justShot = false;
                count = -1;
            }
            count++;
            Platform.runLater((() -> {
                paneSnake.getChildren().clear();
                for (int i = 0; i < bodyParts - 1; i++) {
                    Rectangle rectangle = new Rectangle();
                    rectangle.setX(x[i]);
                    rectangle.setY(y[i]);
                    rectangle.setHeight(size);
                    rectangle.setWidth(size);
                    rectangle.setFill(Color.LIGHTGREY);
                    paneSnake.getChildren().add(rectangle);
                }
                Rectangle rectangleHead = new Rectangle();
                rectangleHead.setX(x[bodyParts-1]);
                rectangleHead.setY(y[bodyParts-1]);
                rectangleHead.setHeight(size);
                rectangleHead.setWidth(size);
                rectangleHead.setFill(headColor);
                paneSnake.getChildren().add(rectangleHead);
            }));
        }

    }

    private void resetLevel(){
        justShot = false;
        count = 0;
        x = new int[bodyParts];
        y = new int[bodyParts];
        currentDirection = startDirection;
        if(whichPlayer == 1){
            for(int i=0; i<bodyParts; i++){
                x[i] = startX + size*i;
                y[i] = startY;
            }
        } else if (whichPlayer == 2){
            System.out.println("DID");
            for(int i=bodyParts-1; i>=0; i--){
                x[i] = startX + (bodyParts-1)*size - size*i;
                y[i] = startY;
            }
        } else{
            throw new IllegalArgumentException("Snake has to have his number <1,2>");
        }


        shoot.setAmmo(startValueOfBullets);
    }

    public void killSnake(){
        Platform.runLater((() -> paneSnake.getChildren().clear()));
        if(threadSnake!=null){
            threadSnake.interrupt();
        }
        running = false;
        resetLevel();
    }

    public void snakeShot(){
        if(decreaseHealthPoints()) {
            showSnakeShot();
            startSnake();
            justShot = true;
        }
    }

    private void showSnakeShot(){
        Platform.runLater((() -> paneSnake.getChildren().clear()));
        if(threadSnake!=null){
            threadSnake.interrupt();
        }
        running = false;
        if(health > 0){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            resetLevel();
            startSnake();
        }

    }

    private boolean decreaseHealthPoints(){
        health--;
        Platform.runLater((() -> healthPoints.setText(Integer.toString(health))));
        if(health > 0){
            return true;
        } else {
            killSnake();
            container.getGameFrameControllerMultiPlayer().killTheGame();
            return false;
        }
    }

    public void startSnake(){
        System.out.println("snake"+whichPlayer+"started ;)");
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
                System.out.println("SnakeDecoration: thread exited;");
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
            if(!shoot.getSpawnedAmmo()){
                shoot.spawnAmmo();
            }
        }
    }

    private void moveSnake(){
        checkCollisions();
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

    private void checkCollisions(){
//        if(getSnakeHeadX() < 0 || getSnakeHeadX() >= container.getColMP()*size || getSnakeHeadY() < 0 || getSnakeHeadY() >= container.getRowMP()*size){
//            container.getGameFrameControllerMultiPlayer().killTheGame();
//            System.out.println("FINISH THE GAME");
//        }
        if(getSnakeHeadX() < 0){
            x[bodyParts-1] = container.getColMP()*size - size;
        }

        if(getSnakeHeadX() >= container.getColMP()*size){
            x[bodyParts-1] = 0;
        }

        if(getSnakeHeadY() < 0){
            y[bodyParts-1] = container.getRowMP()*size - size;
        }


        if(getSnakeHeadY() >= container.getRowMP()*size){
            y[bodyParts-1] = 0;
        }




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

    public int getSnakeX(int i) { return x[i];}
    public int getSnakeY(int i) { return y[i];}

    public int whichSnake(){
        return whichPlayer;
    }
    public boolean isJustShot(){
        return justShot;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public int getHealth(){
        return health;
    }
}
