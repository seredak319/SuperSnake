package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.Random;


public class LevelOne extends GameFrameControllerSinglePlayer{

    @FXML
    Pane paneObstacles;
    private Obstacles obstacles;
    private final int gapTime = 4000;

    LevelOne(Container container) {
        super(container);
    }
    private void check(){
        if(paneObstacles == null){
            System.out.println("dupa1");
        }
        if(paneBadSnakes == null){
            System.out.println("Dupa2");
        }
    }




    private void addObstaclesToBackGround(){

        Random random = new Random();

       // while(running){
//            try {
//                sleep(gapTime);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            int drawnNum = random.nextInt(1,3);
        System.out.println(drawnNum);
            int y = random.nextInt(0,23);
        System.out.println(y);
            if(drawnNum == 1){
                obstacles.addObstacleType1(26,y);
            }
            if(drawnNum == 2){
                obstacles.addObstacleType2(26,y);
            }
            if(drawnNum == 3){
                obstacles.addObstacleType3(26,y);
            }


    }


    @Override
    public void startBadSnakes() {
        badSnake.newBadSnake("Right");
        badSnake.newBadSnake("Left");
        //badSnake.newBadSnakeRandomDirection();
        badSnake.startSnakes();
        obstacles = new Obstacles(paneObstacles);
        addObstaclesToBackGround();
        obstacles.makeObstaclesMove();
    }

    @Override
    public void resetLevel() {
        shoot.setAmmo(500);
        points = 0;
        progress = 0;
        Platform.runLater(() -> {
            pointsAmount.setText(Integer.toString(points));
            progressBar.setProgress(progress);
            bulletsAmount.setText(Integer.toString(500));
            paneObstacles.getChildren().clear();
        });
    }

    @Override
    public void finishTheGame() {
        running = false;
        justFinished = true;
        long end = System.nanoTime();
        double timeElapsed = (double) (end - start)/1_000_000_000;
        snake.setRunning(false);
        badSnake.setRunning(false);
        System.out.println("Wow! Your time is: " + (timeElapsed));
        shoot.clearSpawnedAmmo();
        obstacles.stopMakingObstaclesMoving();
        showFinishedScreen();
    }
}
