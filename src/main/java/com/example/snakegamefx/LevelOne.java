package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Objects;

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

    LevelOne(Container container)
    {
       super(container);
       this.container = container;

    }

    @Override
    public void startBadSnakes() {
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
        container.getBoss().healthPoints = 5;
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
        int n = 3;
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
        finishImage = new ImageView(Objects.requireNonNull(getClass().getResource("img/levelOneStartPic.png")).toExternalForm());
        finishImage.setFitHeight(575);
        finishImage.setFitWidth(700);
        Platform.runLater(() -> finishPane.getChildren().add(finishImage));
        int size = 25;
        int bossDelay = 300;
        int obstaclesDelay = 250;
        obstacles = new Obstacles(paneObstacles, container, obstaclesDelay);
        boss = new Boss(container,paneBoss,bossDelay);
        container.setBoss(boss);
        badSnake = new BadSnake(paneBadSnakes,container);
        shoot = new Shoot(size,paneShoot,paneSpawn, bulletsAmount,badSnake, container,30);
        snake = new Snake(paneSnake, container, size);
        container.setBadSnake(badSnake);
        container.setShoot(shoot);
        container.setSnake(snake);
        container.setObstacles(obstacles);
        bulletsAmount.setText(Integer.toString(shoot.getAmmo()));
        pointsAmount.setText(Integer.toString(0));
    }

    private void fightTheBoss(){
        shoot.setBossFight(true);
        boss.startBoss();
        container.getObstacles().killObstacles();
        badSnake.killBadSnakes();
    }
}
