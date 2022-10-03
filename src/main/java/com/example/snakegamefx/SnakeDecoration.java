package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//todo: źle działa ta redukcja węży

public class SnakeDecoration extends Thread{

    private Pane paneBadSnake;
    private static int maxSnakes = 20;
    private int size;
    private int startX[] = new int [maxSnakes];
    private int startY[] = new int [maxSnakes];
    private static int bodyParts = 4;
    private static int[][] x = new int [maxSnakes][bodyParts];
    private int y[][] = new int [maxSnakes][bodyParts];

    public void setRunning(boolean b) {
        running = b;
        System.out.println("SnakeDecoration: running = " + b);
    }

    private boolean running = false;

    public static Timer getTimerSnakeDecoration() {
        return timerSnakeDecoration;
    }

    public static TimerTask getTimerTaskSnakeDecoration() {
        return timerTaskSnakeDecoration;
    }

    private static Timer timerSnakeDecoration;
    private static TimerTask timerTaskSnakeDecoration;
    private Thread thread;
    private int delay;
    private String direction[] = new String[maxSnakes];
    private Random random = new Random();
    private int frequency;
    private static int howManySnakes = 0;
    private int howManyResets[] = new int [maxSnakes];
    private int maxResets = 4;
    private Rectangle rectangle;
    private Rectangle rectangleHead;


    SnakeDecoration(Pane paneSnake, int size, int frequency) {
        this.frequency = frequency;
        this.paneBadSnake = paneSnake;
        this.size = size;
        System.out.println(paneSnake.getPrefWidth());
        System.out.println(paneSnake.getPrefHeight());
    }

    public void newSnakeDecoration(String direction, int delay){
        howManySnakes++;
        this.delay = delay;
        this.direction[howManySnakes-1] = direction;
        resetSnake(direction,howManySnakes);
        System.out.println("NEW NEW NEW NEW NEW NEW NEW @@@@@@@@@@@@");
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


    public static void killSnakeDecoration(){
       // if(isRunning()) {
        timerSnakeDecoration.cancel();
            timerTaskSnakeDecoration.cancel();


            getTimerSnakeDecoration().cancel();
        getTimerTaskSnakeDecoration().cancel();
    //    }
    }

    public void startSnakes(){
        running = true;
        //Initiation of snake
//        timerSnakeDecoration = new Timer();
//        timerTaskSnakeDecoration = new TimerTask() {
//            @Override
//            public void run() {
//                moveSnakes();
//            }
//        };
//        timerSnakeDecoration.schedule(timerTaskSnakeDecoration,delay,frequency);

        thread = new Thread(){
            @Override
            public void run(){
                while (running /*&& !SuperSnake.kill*/){
                   // System.out.println("SnakeDecoration: run();");
                    moveSnakes();
                    try {
                        sleep(frequency);
                    } catch (InterruptedException e) {
                        System.out.println("Przerwano spanie");
                    }
                }
                System.out.println("SnakeDecoration: thread exited;");
            }
        };

        thread.start();
    }

    public void pauseThread() {
        running=false;
    }


    void moveSnakes(){
//        if(!running)
//            killSnakeDecoration();

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
                int temp = 0;
                paneBadSnake.getChildren().clear();
                for(int j=0; j<howManySnakes; j++) {
                    for (int i = 0; i < bodyParts - 1; i++) {
                        rectangle = new Rectangle();
                        rectangle.setX(x[j][i]);
                        rectangle.setY(y[j][i]);
                        rectangle.setHeight(size);
                        rectangle.setWidth(size);
                        rectangle.setFill(Color.BLACK);
                        paneBadSnake.getChildren().add(rectangle);
                    }
                    rectangleHead = new Rectangle();
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
                    paneBadSnake.getChildren().add(rectangleHead);
                }
            }));
    }

    public int getX(int j, int i) {
        return x[j][i];
    }

    public int getY(int j, int i) {
        return y[j][i];
    }

    public static int getHowManySnakes() {
        return howManySnakes;
    }

    public boolean isRunning() {
        return running;
    }

}
