package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class Boss {

    private Container container;
    private int size = 25;
    private Pane paneBoss;
    String currentDirection = "Left";
    int bodyParts = 7;
    private final int DELAY = 300;
    private int[] x;
    private int[] y;
    private boolean running = false;
    private Thread threadSnake;
    private BadSnakesMovementAlgorithm badSnakesMovementAlgorithm;
    public int healthPoints = 5;

    Boss(Container container, Pane paneBoss){
        this.container = container;
        this.paneBoss = paneBoss;
        badSnakesMovementAlgorithm = new BadSnakesMovementAlgorithm(container);
    }

    public void resetOrInitLevel() {
            if(running) {
                try {
                    threadSnake.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            x = new int[bodyParts];
            y = new int[bodyParts];

            for(int i=0; i<bodyParts; i++){
                x[i] = 10*size;
                y[i] = size*i + 10*size;
            }
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

    public void startSnake(){
        running = true;
        threadSnake = new Thread(() -> {
            while (running) {
                moveSnake();
                try {
                    sleep(DELAY);
                } catch (InterruptedException e) {
                    System.out.println("Sleep was interrupted!");
                }
            }
            System.out.println("Boss: thread exited;");
            Platform.runLater((() -> {
                paneBoss.getChildren().clear();
            }));
            try {
                currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadSnake.start();
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
        currentDirection = badSnakesMovementAlgorithm.setCurrentDirection(getSnakeHeadX(),getSnakeHeadY());
    }

    private void checkCollisions() {

        for(int i =0; i<container.getSnake().bodyParts; i++)
            if(getSnakeHeadX() == container.getSnake().getSnakeX(i) && getSnakeHeadY() == container.getSnake().getSnakeY(i)){
             System.out.println("Koniec gry");
             container.getShoot().clearSpawnedAmmo();
             running =false;
             container.getBadSnake().setRunning(false);
             container.getSnake().setRunning(false);
             container.getLevelOne().running = false;
             container.getLevelOne().justFinished = true;
             container.getGameFrameControllerSinglePlayer().showFinishedScreen();
        }
    }


//    public void setCurrentDirection() {
//
//        // XDDD pozdro tu chodzi o to że Boss będzie wybierał punkt bliżej niego spośród głowy i ostatniej częsci ciała naszego węża, 'algorytm' lepiej działa
//        int x = container.getSnake().getSnakeX(3) > container.getSnake().getSnakeHeadX() ? container.getSnake().getSnakeX(3) - getSnakeHeadX() : container.getSnake().getSnakeHeadX() - getSnakeHeadX();
//        int y = container.getSnake().getSnakeY(3) > container.getSnake().getSnakeHeadY() ? container.getSnake().getSnakeY(3) - getSnakeHeadY() : container.getSnake().getSnakeHeadY() - getSnakeHeadY();
//
//
//        if(x >= 0 && y >= 0){
//            if( x >= y){
//                currentDirection = "Right";
//            } else {
//                currentDirection = "Down";
//            }
//        }
//
//        if(x>=0 && y<=0){
//            if(x>=Math.abs(y)){
//                currentDirection = "Right";
//            } else {
//                currentDirection = "Up";
//            }
//        }
//
//        if(x<=0 && y>=0){
//            if(Math.abs(x) >= y){
//                currentDirection = "Left";
//            } else {
//                currentDirection = "Down";
//            }
//        }
//
//        if(x<=0 && y<=0){
//            if(Math.abs(x) >= Math.abs(y)){
//                currentDirection = "Left"; //
//            } else {
//                currentDirection = "Up";
//            }
//        }
//    }

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

    public int getSnakeX(int i) { return x[i];}
    public int getSnakeY(int i) { return y[i];}

    public void hitBoss(){
        healthPoints--;
        container.getLevelOne().addPoints();
    }


}
