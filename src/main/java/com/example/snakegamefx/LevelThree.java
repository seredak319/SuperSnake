package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;
import java.util.Random;

import static java.lang.Thread.sleep;

public class LevelThree extends GameFrameControllerSinglePlayer{

    @FXML
    Pane paneObstacles;
    @FXML
    Pane paneBoss;
    @FXML
    Label labelTitle;
    @FXML
    Pane paneBossShoot;
    private Obstacles obstacles;
    private final Container container;
    private Boss boss;
    private BossShoot bossShoot;

    LevelThree(Container container)
    {
        super(container);
        this.container = container;

    }

    private void addObstaclesToBackGround(){
        System.out.println("Starting obstacles");
        Thread threadAddObstacles = new Thread(() -> {
            while (obstacles.isRunning()) {

                System.out.println(obstacles.getN());

                Random random = new Random();
                int howManyObstacles;
                int xGapBetweenObstacles;
                int witchObstacle;
                int gapTime;
                int y;
                int timeGapBetweenObstacles;

                howManyObstacles = random.nextInt(1,5);
                gapTime = random.nextInt(1,3);
                timeGapBetweenObstacles = random.nextInt(1,3);

                for(int i = 0; i< howManyObstacles;i++){
                    y = random.nextInt(0,21);
                    xGapBetweenObstacles = random.nextInt(1,7);
                    witchObstacle = random.nextInt(1,5);
                    System.out.println("witchObstacle: " + witchObstacle);
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
        int bossDelay = 330;
        boss = new Boss<>(container,paneBoss,bossDelay, container.getLevelThree());
        boss.resetOrInitLevel();
        container.setBoss(boss);
        badSnake.newBadSnake("Right");
        badSnake.newBadSnake("Left");
        badSnake.newBadSnakeRandomDirection();
        badSnake.startSnakes();
        int obstaclesDelay = 110;
        obstacles = new Obstacles<>(paneObstacles, container, obstaclesDelay,container.getLevelThree());
        addObstaclesToBackGround();
        container.setObstacles(obstacles);
        obstacles.makeObstaclesMove();
    }

    @Override
    public void resetLevel() {
        shoot.setAmmo(container.getShoot().START_VALUE);
        container.getBoss().healthPoints = 5;
        boss.resetOrInitLevel();
        labelTitle.setText("Killed snakes:");
        points = 0;
        progress = 0;
        shoot.clearSpawnedAmmo();
        obstacles.stopMakingObstaclesMoving();
        Platform.runLater(() -> {
            pointsAmount.setText(Integer.toString(points));
            progressBar.setProgress(progress);
            bulletsAmount.setText(Integer.toString(container.getShoot().START_VALUE));
            paneObstacles.getChildren().clear();
        });
    }

    @Override
    public void finishTheGame() {
        //   fightTheBoss();
        running = false;
        showFinishedScreen();
        container.getObstacles().setRunning(false);
        container.getShoot().clearSpawnedAmmo();
        boss.setRunning(false);
        justFinished = true;
        long end = System.nanoTime();
        double timeElapsed = (double) (end - start)/1_000_000_000;
        snake.setRunning(false);
        badSnake.setRunning(false);
        System.out.println("Wow! Your time is: " + (timeElapsed));
    }

    @Override
    public void addPoints() {
        points++;
        int n = 10;
        if(points <= n){
            Platform.runLater(() -> {
                pointsAmount.setText(Integer.toString(points));
                progress += 1.00/n;
                progressBar.setProgress(progress);

            });
        }
        if(points == n){
            fightTheBoss();
            bossShoot = new BossShoot<>(boss,paneBossShoot,container,container.getLevelThree());
            bossShoot.bossShoot();
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
                progress += 1.0/10;
                progressBar.setProgress(progress);
            });
            if(points == 2*n){
                finishTheGame();
            }
        }
    }

    @Override
    public void init() {
        finishImage = new ImageView(Objects.requireNonNull(getClass().getResource("img/levelThreeStartPic.png")).toExternalForm());
        finishImage.setFitHeight(575);
        finishImage.setFitWidth(700);
        Platform.runLater(() -> finishPane.getChildren().add(finishImage));
        int size = 25;
        badSnake = new BadSnake(paneBadSnakes,container);
        shoot = new Shoot(size,paneShoot,paneSpawn, bulletsAmount,badSnake, container,30);
        snake = new Snake(paneSnake, container, size);
        container.setBadSnake(badSnake);
        container.setShoot(shoot);
        container.setSnake(snake);
        bulletsAmount.setText(Integer.toString(shoot.getAmmo()));
        pointsAmount.setText(Integer.toString(0));
    }

    @Override
    public void showFinishedScreen() {
        obstacles.setRunning(false);
        finishImage = new ImageView(Objects.requireNonNull(getClass().getResource("img/levelThreeStartPic.png")).toExternalForm());
        finishImage.setFitHeight(575);
        finishImage.setFitWidth(700);
        Platform.runLater(() -> finishPane.getChildren().add(finishImage));
    }

    private void fightTheBoss(){
        shoot.setBossFight(true);
        boss.resetOrInitLevel();
        boss.startSnake();

        //container.getObstacles().setRunning(false);
        badSnake.setRunning(false);
    }
}

