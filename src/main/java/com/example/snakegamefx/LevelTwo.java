package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class LevelTwo extends GameFrameControllerSinglePlayer{

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

    LevelTwo(Container container)
    {
        super(container);
        this.container = container;
    }

    @Override
    public void startBadSnakes() {
        container.setBoss(boss);
        badSnake.newBadSnake("Right");
        badSnake.newBadSnake("Left");
        badSnake.newBadSnakeRandomDirection();
        badSnake.startBadSnakes();
        obstacles.addObstaclesToBackGround();
        container.setObstacles(obstacles);
        obstacles.makeObstaclesMove();
    }

    @Override
    public void resetLevel() {
        shoot.setAmmo(container.getShoot().START_VALUE);
        container.getBoss().healthPoints = 10;
        labelTitle.setText("Killed snakes:");
        points = 0;
        progress = 0;
        shoot.clearSpawnedAmmo();
        Platform.runLater(() -> {
            pointsAmount.setText(Integer.toString(points));
            progressBar.setProgress(progress);
            bulletsAmount.setText(Integer.toString(container.getShoot().START_VALUE));
            paneObstacles.getChildren().clear();
        });
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
                finishTheGame(true);
            }
        }
    }

    @Override
    public void init() {
        finishImage = new ImageView(Objects.requireNonNull(getClass().getResource("img/levelTwoStartPic.png")).toExternalForm());
        finishImage.setFitHeight(575);
        finishImage.setFitWidth(700);
        Platform.runLater(() -> finishPane.getChildren().add(finishImage));
        int size = 25;
        int obstaclesDelay = 150;
        int bossDelay = 400;
        boss = new Boss(container,paneBoss,bossDelay);
        bossShoot = new BossShoot(boss,paneBossShoot,container,60);
        obstacles = new Obstacles(paneObstacles, container, obstaclesDelay);
        badSnake = new BadSnake(paneBadSnakes,container);
        shoot = new Shoot(size,paneShoot,paneSpawn, bulletsAmount,badSnake, container,15);
        snake = new Snake(paneSnake, container, size);
        container.setBadSnake(badSnake);
        container.setShoot(shoot);
        container.setSnake(snake);
        container.setBoss(boss);
        container.setObstacles(obstacles);
        bulletsAmount.setText(Integer.toString(shoot.getAmmo()));
        pointsAmount.setText(Integer.toString(0));
    }

    private void fightTheBoss(){
        bossShoot.bossShoot();
        shoot.setBossFight(true);
        boss.startBoss();
        container.getObstacles().killObstacles();
        obstacles.killObstacles();
        badSnake.killBadSnakes();
    }
}
