package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;
import java.util.Random;

public class BadSnake extends Thread{

    Pane paneBadSnake;
    private boolean running = false;
    private final int maxBadSnakes = 10;
    private final int size = 25;
    private int howManySnakes = 0;
    private String direction[] = new String[maxBadSnakes];
    private int startX[] = new int [maxBadSnakes];
    private int startY[] = new int [maxBadSnakes];
    private final int bodyParts = 4;
    private int x[][] = new int [maxBadSnakes][bodyParts];
    private int y[][] = new int [maxBadSnakes][bodyParts];
    private LinkedList linkedList = new LinkedList();
    private Random random = new Random();
    private Thread threadBadSnakes;

    BadSnake(Pane paneBadSnake){
        this.paneBadSnake = paneBadSnake;
        System.out.println(paneBadSnake.getPrefWidth());
        System.out.println(paneBadSnake.getPrefHeight());

    }

    public void newBadSnake(String direction){
        howManySnakes++;
        this.direction[howManySnakes-1] = direction;
        resetSnake(direction,howManySnakes);
        System.out.println("BadSnake: newBadSnake");
    }

    public void killSingleBadSnake(int j){
        //howManySnakes--;

        resetSnake("Right",j+1);
       // newBadSnakeRandomDirection();
//        newBadSnakeRandomDirection();
//        newBadSnakeRandomDirection();
    }

    public void resetSnake(String direction, int howManySnakesLocal){
        switch (direction) {
            case "Right" -> {
                this.startX[howManySnakesLocal-1] = -125;
                this.startY[howManySnakesLocal-1] = random.nextInt((int) (paneBadSnake.getPrefHeight()/size - 1))*size;
                for(int i=0; i<bodyParts; i++){
                    x[howManySnakesLocal-1][i] = startX[howManySnakesLocal-1] + size*i;
                    y[howManySnakesLocal-1][i] = startY[howManySnakesLocal-1];
                }
            }
            case "Up" -> {
                this.startX[howManySnakesLocal-1] = random.nextInt((int) (paneBadSnake.getPrefWidth()/size - 1))*size;
                this.startY[howManySnakesLocal-1] = -25;
                for(int i=0; i<bodyParts; i++){
                    x[howManySnakesLocal-1][i] = startX[howManySnakesLocal-1];
                    y[howManySnakesLocal-1][i] = startY[howManySnakesLocal-1] - size*i;
                }
            }
            case "Left" -> {
                this.startX[howManySnakesLocal-1] = (int) paneBadSnake.getPrefWidth();
                this.startY[howManySnakesLocal-1] = random.nextInt((int) (paneBadSnake.getPrefHeight()/size - 1))*size;
                for(int i=0; i<bodyParts; i++){
                    x[howManySnakesLocal-1][i] = startX[howManySnakesLocal-1] - size*i;
                    y[howManySnakesLocal-1][i] = startY[howManySnakesLocal-1];
                }
            }
            case "Down" -> {
                this.startX[howManySnakesLocal-1] = random.nextInt((int) (paneBadSnake.getPrefHeight()/size - 1))*size;
                this.startY[howManySnakesLocal-1] = (int) paneBadSnake.getPrefHeight();
                for(int i=0; i<bodyParts; i++){
                    x[howManySnakesLocal-1][i] = startX[howManySnakesLocal-1];
                    y[howManySnakesLocal-1][i] = startY[howManySnakesLocal-1] + size*i;
                }
            }
        }
    }

    public void newBadSnakeRandomDirection(){
        System.out.println("BadSnake: howManySnakes[" +howManySnakes+ "]");
        if(howManySnakes >= maxBadSnakes)
            return;
        howManySnakes++;
        Random random1 = new Random();
        switch (random1.nextInt(1,5)){
            case 1 -> {
                direction[howManySnakes-1] = "Up";
            }
            case 2 -> {
                direction[howManySnakes-1] = "Down";
            }
            case 3 -> {
                direction[howManySnakes-1] = "Left";
            }
            case 4 -> {
                direction[howManySnakes-1] = "Right";
            }
        }
    }


    public void startSnakes(){
        running = true;
        //Initiation of snake
        threadBadSnakes = new Thread(() -> {
            while(!SuperSnake.kill && running) {
                moveSnakes();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        threadBadSnakes.start();
    }

    private void moveSnakes(){
        //System.out.println("BadSnake: moveSnakes");
       // System.out.println("BadSnake: "+ Snake.getSnakeHeadX());
      //  System.out.println("BadSnake: [shoot]: " + Shoot.getShootX());
        for(int j=0; j<howManySnakes; j++) {
            for (int i = 0; i < bodyParts - 1; i++) {
                x[j][i] = x[j][i + 1];
                y[j][i] = y[j][i + 1];
            }

            switch (direction[j]) {
                case "Up" -> {
                    y[j][bodyParts - 1] = y[j][bodyParts - 1] + size;
                }
                case "Down" -> {
                    y[j][bodyParts - 1] = y[j][bodyParts - 1] - size;
                }
                case "Left" -> {
                    x[j][bodyParts - 1] = x[j][bodyParts - 1] - size;
                }
                case "Right" -> {
                    x[j][bodyParts - 1] = x[j][bodyParts - 1] + size;
                }
            }
            checkCollision(j);
            paintSnakes();
        }
    }

    private void checkCollision(int j){

        if (y[j][0] == paneBadSnake.getPrefHeight() && direction[j] == "Up")
            resetSnake(direction[j],j+1);
        if (y[j][0] == -50 && direction[j] == "Down")
            resetSnake(direction[j],j+1);
        if (x[j][0] == paneBadSnake.getPrefWidth() && direction[j] == "Right")
            resetSnake(direction[j],j+1);
        if (x[j][0] == -50 && direction[j] == "Left")
            resetSnake(direction[j],j+1);

    }


    public void paintSnakes(){
        Platform.runLater((() -> {
            paneBadSnake.getChildren().clear();
            for(int j=0; j<howManySnakes; j++) {
                for (int i = 0; i < bodyParts - 1; i++) {
                    Rectangle rectangle = new Rectangle();
                    rectangle.setX(x[j][i]);
                    rectangle.setY(y[j][i]);
                    rectangle.setHeight(size);
                    rectangle.setWidth(size);
                    rectangle.setFill(Color.BLACK);
                    paneBadSnake.getChildren().add(rectangle);
                }
                Rectangle rectangleHead = new Rectangle();
                rectangleHead.setX(x[j][bodyParts-1]);
                rectangleHead.setY(y[j][bodyParts-1]);
                rectangleHead.setHeight(size);
                rectangleHead.setWidth(size);
                rectangleHead.setFill(Color.RED);
                paneBadSnake.getChildren().add(rectangleHead);
            }
        }));

    }

    public void setRunning(boolean b) {
        running = b;
    }

    public int getHowManySnakes() {
        return howManySnakes;
    }

    public int getStartX(int j) {
        return startX[j];
    }

    public int getStartY(int j) {
        return startY[j];
    }

    public int getX(int j, int i) {
        return x[j][i];
    }

    public int getY(int j, int i) {
        return y[j][i];
    }

    public String getDirection(int j) {
        return direction[j];
    }
}
