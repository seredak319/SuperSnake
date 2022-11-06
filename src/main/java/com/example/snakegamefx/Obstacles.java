package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import static java.lang.Thread.sleep;

public class Obstacles<T extends GameFrameControllerSinglePlayer> {

    Pane paneObstacles;
    private final int size = 25;
    private final int tableSizeHorizontal = 27;
    private final int tableSizeVertical = 22;
    private final int gapTime; // time of delay between every move ov obstacles in certain direction.
    private final String direction = "Right";
    private int n; // number of squares to be moved, depend on obstacle's type.
    private int[] x;
    private int[] y;
    private final int maxObstacles = 100;
    private Thread threadObstacles;
    private boolean running = false;
    private final Container container;
    private T t;

    Obstacles(Pane pane, Container container, int gapTime,T t){
        this.paneObstacles = pane;
        this.container = container;
        this.gapTime = gapTime;
        if(pane == null){
            System.exit(2137);
        }
        x = new int[maxObstacles];
        y = new int[maxObstacles];
        this.t = t;
    }


    //............
    //
    //  []
    //  []
    //  []  <- x,y
    //
    //............
    public void addObstacleType1(int xPos, int yPos){ // three squares in a row vertically , start x,y is about the lowest square.

        if(n>= maxObstacles)
            return;

        for(int i =n; i<n+3; i++){
            x[i] = xPos*size;
            y[i] = yPos*size - (i-n)*size;
        }
        n+=3;
    }

    //............
    //
    // [n][n+1] <- x,y
    //    [n+2]
    //
    //............
    public void addObstacleType2(int xPos, int yPos){ // three squares making curvature

        if(n>= maxObstacles)
            return;

        xPos *=size;
        yPos *=size;

        x[n] = xPos - size; //n
        y[n] = yPos;
        n++;
        x[n] = xPos; // n+1
        y[n] = yPos;
        n++;
        x[n] = xPos; // n+2
        y[n] = yPos + size;
        n++;
    }

    //............
    //
    //    [] <- x,y
    //
    //............
    public void addObstacleType3(int xPos, int yPos){ // single square

        if(n>= maxObstacles)
            return;

        x[n] = xPos*size;
        y[n] = yPos*size;
        n++;
    }

    //............
    //
    //   [x,y][][]
    //
    //............
    public void addObstacleType4(int xPos, int yPos){

        if(n>= maxObstacles)
            return;

        xPos *=size;
        yPos *=size;

        x[n] = xPos;
        y[n] = yPos;
        n++;
        x[n] = xPos - size;
        y[n] = yPos;
        n++;
        x[n] = xPos - 2*size;
        y[n] = yPos;
        n++;
    }

    public void makeObstaclesMove(){
        running = true;
        threadObstacles = new Thread(() -> {
            while (running) {
                moveObstacles(direction);
                paintObstacles();
                try {
                    sleep(gapTime);
                } catch (InterruptedException e) {
                    System.out.println("Sleep was interrupted!");
                }
            }
            Platform.runLater((() -> {
                paneObstacles.getChildren().clear();
            }));
            System.out.println("Obstacle: thread exited;");
        });
        threadObstacles.start();
    }

    public void stopMakingObstaclesMoving(){
        running = false;
        Platform.runLater((() -> { paneObstacles.getChildren().clear(); }));
        n = 0;
        x = new int[maxObstacles];
        y = new int[maxObstacles];
        if(threadObstacles != null){
            try {
                threadObstacles.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }

    private void moveObstacles(String direction){
        if( direction.equals("Right")){
            for(int i =0; i<n; i++){
                checkCollisionOfObstacles(i);
                x[i] -= size;
            }
        }

    }

    private void paintObstacles(){
        Platform.runLater((() -> {
            paneObstacles.getChildren().clear();
            for(int i =0; i<n; i++){
                Rectangle rectangle = new Rectangle();
                rectangle.setX(x[i]);
                rectangle.setY(y[i]);
                rectangle.setFill(Color.DARKBLUE);
                rectangle.setHeight(size);
                rectangle.setWidth(size);
                paneObstacles.getChildren().add(rectangle);
            }
        }));
    }

    private void checkCollisionOfObstacles(int i){
        if(x[i] < 0 ){ // obstacle goes of from range of table
            n--;
                x[i] = x[n];
                y[i] = y[n];
                return;
        }

        for(int p =0; p<n; p++){
            if(x[p] == container.getSnake().getSnakeHeadX() && y[p] == container.getSnake().getSnakeHeadY()){
                System.out.println("Uderzyles w sciane");
                Platform.runLater((() -> {
                    paneObstacles.getChildren().clear();
                }));
                container.getShoot().clearSpawnedAmmo();
                running =false;
                container.getBadSnake().setRunning(false);
                container.getSnake().setRunning(false);
//                container.getLevelOne().running = false;
//                container.getLevelOne().justFinished = true;
                t.running = false;
                t.justFinished = true;
                container.getGameFrameControllerSinglePlayer().showFinishedScreen();


                return;
            }
        }
    }

    public int getN() {
        return n;
    }

    public void setRunning(boolean b){
        running = b;
    }

    public boolean isRunning(){
        return running;
    }
}
