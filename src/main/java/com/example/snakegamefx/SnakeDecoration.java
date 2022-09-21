package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//todo: źle działa ta redukcja węży

public class SnakeDecoration {

    private Pane paneSnake;
    private int maxSnakes = 10;
    private int size;
    private int startX[] = new int [maxSnakes];
    private int startY[] = new int [maxSnakes];
    private int bodyParts = 4;
    private int x[][] = new int [maxSnakes][bodyParts];
    private int y[][] = new int [maxSnakes][bodyParts];
    private boolean running = false;
    private static Timer timerSnakeDecoration;
    private static TimerTask timerTaskSnakeDecoration;
    private int delay;
    private String direction[] = new String[maxSnakes];
    private Random random = new Random();
    private int frequency = random.nextInt(70,100);
    private int howManySnakes = 0;
    private int howManyResets[] = new int [maxSnakes];
    private int maxResets = 4;


    public SnakeDecoration(Pane paneSnake, int size) {
        running = true;
        this.paneSnake = paneSnake;
        this.size = size;
        startSnakes();
    }

    public void newSnakeDecoration(String direction, int delay){
        howManySnakes++;
        this.delay = delay;
        this.direction[howManySnakes-1] = direction;
        resetSnake(direction,howManySnakes);
    }

    public void resetSnake(String direction, int howManySnakesLocal){
        if(howManySnakesLocal > 4 && howManyResets[howManySnakesLocal-1] >= maxResets) {
            howManySnakes--;
            howManyResets[howManySnakesLocal - 1] = 0;
        }
        else {
            howManyResets[howManySnakesLocal - 1]++;
        }
        switch (direction) {
            case "Right" -> {
                this.startX[howManySnakesLocal-1] = -125;
                this.startY[howManySnakesLocal-1] = random.nextInt(15)*size;
                for(int i=0; i<bodyParts; i++){
                    x[howManySnakesLocal-1][i] = startX[howManySnakesLocal-1] + size*i;
                    y[howManySnakesLocal-1][i] = startY[howManySnakesLocal-1];
                }
            }
            case "Up" -> {
                this.startX[howManySnakesLocal-1] = random.nextInt(11)*size;
                this.startY[howManySnakesLocal-1] = -25;
                for(int i=0; i<bodyParts; i++){
                    x[howManySnakesLocal-1][i] = startX[howManySnakesLocal-1];
                    y[howManySnakesLocal-1][i] = startY[howManySnakesLocal-1] - size*i;
                }
            }
            case "Left" -> {
                this.startX[howManySnakesLocal-1] = (int) paneSnake.getPrefHeight();
                this.startY[howManySnakesLocal-1] = random.nextInt(15)*size;
                for(int i=0; i<bodyParts; i++){
                    x[howManySnakesLocal-1][i] = startX[howManySnakesLocal-1] - size*i;
                    y[howManySnakesLocal-1][i] = startY[howManySnakesLocal-1];
                }
            }
            case "Down" -> {
                this.startX[howManySnakesLocal-1] = random.nextInt(11)*size;
                this.startY[howManySnakesLocal-1] = (int) paneSnake.getPrefHeight();
                for(int i=0; i<bodyParts; i++){
                    x[howManySnakesLocal-1][i] = startX[howManySnakesLocal-1];
                    y[howManySnakesLocal-1][i] = startY[howManySnakesLocal-1] + size*i;
                }
            }
        }
    }

    public void addRandomDecorateSnake(){
        System.out.println(howManySnakes);
        if(howManySnakes >= maxSnakes)
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


    public void killSnakeDecoration(){
        if(running) {
            timerTaskSnakeDecoration.cancel();
            timerSnakeDecoration.cancel();
        }
    }

    public void startSnakes(){
        running = true;
        //Initiation of snake
        timerSnakeDecoration = new Timer();
        timerTaskSnakeDecoration = new TimerTask() {
            @Override
            public void run() {
                moveSnakes();
            }
        };
        timerSnakeDecoration.schedule(timerTaskSnakeDecoration,delay,frequency);
    }

    void moveSnakes(){
        if(!running)
            killSnakeDecoration();

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
            if (y[j][0] == paneSnake.getPrefHeight() && direction[j] == "Up")
                resetSnake(direction[j],j+1);
            if (y[j][0] == -50 && direction[j] == "Down")
                resetSnake(direction[j],j+1);
            if (x[j][0] == paneSnake.getPrefWidth() && direction[j] == "Right")
                resetSnake(direction[j],j+1);
            if (x[j][0] == -50 && direction[j] == "Left")
                resetSnake(direction[j],j+1);

    }

    private void paintSnakes(){

            Platform.runLater((() -> {
                int temp = 0;
                paneSnake.getChildren().clear();
                for(int j=0; j<howManySnakes; j++) {
                    for (int i = 0; i < bodyParts - 1; i++) {
                        Rectangle rectangle = new Rectangle();
                        rectangle.setX(x[j][i]);
                        rectangle.setY(y[j][i]);
                        rectangle.setHeight(size);
                        rectangle.setWidth(size);
                        rectangle.setFill(Color.BLACK);
                        paneSnake.getChildren().add(rectangle);
                    }
                    Rectangle rectangleHead = new Rectangle();
                    rectangleHead.setX(x[j][3]);
                    rectangleHead.setY(y[j][3]);
                    rectangleHead.setHeight(size);
                    rectangleHead.setWidth(size);
                    if(temp < 4) {
                        rectangleHead.setFill(Color.CYAN);
                        temp++;
                    }
                    else {
                        rectangleHead.setFill(Color.YELLOW);
                    }
                    paneSnake.getChildren().add(rectangleHead);
                }
            }));

    }

    public boolean isRunning() {
        return running;
    }
}
