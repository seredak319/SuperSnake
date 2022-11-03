package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.Random;

import static java.lang.Thread.sleep;


public class LevelOne extends GameFrameControllerSinglePlayer{

    @FXML
    Pane paneObstacles;
    @FXML
    Pane paneBoss;
    @FXML
    Label labelTitle;
    private Obstacles obstacles;
    private final Container container;
    private Boss boss;
  //  private BossAlgorithm bossAlgorithm;

    LevelOne(Container container)
    {
       super(container);
       this.container = container;

    }

    private void addObstaclesToBackGround(){
    System.out.println("Starting obstacles");
    Thread threadAddObstacles = new Thread(() -> {
            while (this.running) {

                System.out.println(obstacles.getN());

                Random random = new Random();
                int howManyObstacles;
                int xGapBetweenObstacles;
                int witchObstacle;
                int gapTime;
                int y;
                int timeGapBetweenObstacles;

                howManyObstacles = random.nextInt(1,3);
                gapTime = random.nextInt(3,5);
                timeGapBetweenObstacles = random.nextInt(1,5);

                for(int i = 0; i< howManyObstacles;i++){
                    y = random.nextInt(0,21);
                    xGapBetweenObstacles = random.nextInt(3,7);
                    witchObstacle = random.nextInt(1,5);
                    switch (witchObstacle){
                        case 1 -> obstacles.addObstacleType1(30+xGapBetweenObstacles,y);
                        case 2 -> obstacles.addObstacleType2(30+xGapBetweenObstacles,y);
                        case 3 -> obstacles.addObstacleType3(30+xGapBetweenObstacles,y);
                        case 4 -> obstacles.addObstacleType4(30+xGapBetweenObstacles,y);
                    }
                    try {
                        sleep(timeGapBetweenObstacles*1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    sleep(gapTime*1000L);
                } catch (InterruptedException e) {
                    System.out.println("Sleep was interrupted!");
                }
            }
        System.out.println("Obstacles thread exited;");
        });

        threadAddObstacles.start();
    }


    @Override
    public void startBadSnakes() {
        badSnake.newBadSnake("Right");
        badSnake.newBadSnake("Left");
        badSnake.newBadSnakeRandomDirection();
        badSnake.startSnakes();
        obstacles = new Obstacles(paneObstacles, container);
        addObstaclesToBackGround();
        container.setObstacles(obstacles);
        obstacles.makeObstaclesMove();
    }

    @Override
    public void resetLevel() {
        shoot.setAmmo(500);
        container.getBoss().healthPoints = 5;
        labelTitle.setText("Killed snakes:");
        points = 0;
        progress = 0;
        shoot.clearSpawnedAmmo();
        obstacles.stopMakingObstaclesMoving();
        Platform.runLater(() -> {
            pointsAmount.setText(Integer.toString(points));
            progressBar.setProgress(progress);
            bulletsAmount.setText(Integer.toString(500));
            paneObstacles.getChildren().clear();
        });
    }

    @Override
    public void finishTheGame() {
     //   fightTheBoss();
        running = false;
        container.getObstacles().setRunning(false);
        container.getShoot().clearSpawnedAmmo();
        boss.setRunning(false);
        justFinished = true;
        long end = System.nanoTime();
        double timeElapsed = (double) (end - start)/1_000_000_000;
        snake.setRunning(false);
        badSnake.setRunning(false);
        System.out.println("Wow! Your time is: " + (timeElapsed));
        showFinishedScreen();
    }

    @Override
    public void addPoints() {
        points++;
        int n = 5;
        if(points <= n){
            Platform.runLater(() -> {
                pointsAmount.setText(Integer.toString(points));
                progress += 1.00/n;
                progressBar.setProgress(progress);

            });
        }
        if(points == 5){
            fightTheBoss();
            Platform.runLater(() -> {
                labelTitle.setText("Boss's HP");
                pointsAmount.setText(Integer.toString(container.getBoss().healthPoints));
                progress = 0;
                progressBar.setProgress(progress);
            });
        }
        if(points >= n+1){
            Platform.runLater(() -> {
                pointsAmount.setText(Integer.toString(container.getBoss().healthPoints));
                progress += 1.0/n;
                progressBar.setProgress(progress);
            });
            if(points == 2*n){
                finishTheGame();
            }
        }
    }

    @Override
    public void init() {
        int size = 25;
        boss = new Boss(container,paneBoss);
        container.setBoss(boss);
        badSnake = new BadSnake(paneBadSnakes,container);
        shoot = new Shoot(size,paneShoot,paneSpawn, bulletsAmount,badSnake, container);
        snake = new Snake(paneSnake, container, size);
        container.setBadSnake(badSnake);
        container.setShoot(shoot);
        container.setSnake(snake);
        bulletsAmount.setText(Integer.toString(shoot.getAmmo()));
        pointsAmount.setText(Integer.toString(0));
    }

    private void fightTheBoss(){
        shoot.setBossFight(true);
        boss.resetOrInitLevel();
        boss.startSnake();

            container.getObstacles().setRunning(false);
            badSnake.setRunning(false);
    }
}
