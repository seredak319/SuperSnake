package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Obstacles {

    Pane paneObstacles;
    private final int size = 25;
    private final int tableSizeHorizontal = 27;
    private final int tableSizeVertical = 22;
    private final int gapTime = 500; // time of delay between every move ov obstacles in certain direction.
    private final String direction = "Right";
    private int n; // number of squares to be moved, depend on obstacle's type.
    private int[] x;
    private int[] y;
    private final int maxObstacles = 20;
    private Thread threadObstacles;
    private boolean running = false;

    Obstacles(Pane pane){
        this.paneObstacles = pane;
        if(pane == null){
            System.exit(2137);
        }
        x = new int[maxObstacles];
        y = new int[maxObstacles];
    }


    //............
    //
    //  []
    //  []
    //  []  <- x,y
    //
    //............
    public void addObstacleType1(int xPos, int yPos){ // three squares in a row vertically , start x,y is about the lowest square.

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

        xPos *=size;
        yPos *=size;

        x[n++] = xPos - size; //n
        y[n] = yPos;
        x[n++] = xPos; // n+1
        y[n] = yPos;
        x[n++] = xPos; // n+2
        y[n] = yPos + size;
    }

    //............
    //
    //    [] <- x,y
    //
    //............
    public void addObstacleType3(int xPos, int yPos){ // single square

        x[n++] = xPos*size;
        y[n++] = yPos*size;
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
            System.out.println("SnakeDecoration: thread exited;");
        });
        threadObstacles.start();
    }

    public void stopMakingObstaclesMoving(){
        running = false;
        Platform.runLater((() -> { paneObstacles.getChildren().clear(); }));
        n = 0;
        x = new int[maxObstacles];
        y = new int[maxObstacles];
        try {
            threadObstacles.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void moveObstacles(String direction){
        if( direction.equals("Right")){
            for(int i =0; i<n; i++){
               // checkCollisionOfObstacles(i);
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
        if(x[i] < 0 || x[i] > tableSizeVertical*size || y[i] > tableSizeVertical* size || y[i] < 0){ // obstacle goes of from range of table
            for(int j =i; j+1<n; j++){
                x[j] = x[j+1];
            }
            n--;

//            Random random = new Random();
//            int drawnNum = random.nextInt(0,4);
//            System.out.println(drawnNum);
//            int y = random.nextInt(1,22);
//            System.out.println(y);
//            if(drawnNum == 1){
//                addObstacleType1(27,y);
//            }
//            if(drawnNum == 2){
//                addObstacleType2(27,y);
//            }
//            if(drawnNum == 3){
//                addObstacleType3(27,y);
//            }

        }
    }

}
