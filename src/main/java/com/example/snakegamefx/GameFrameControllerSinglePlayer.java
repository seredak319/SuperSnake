package com.example.snakegamefx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameFrameControllerSinglePlayer implements Initializable {

//TODO: niepotrzebnie tworzymy cały czas new Rectangle w shoot i snake; Można by było to stowrzyć raz i tylko zmieniac X i Y;   ++ optymalizacja


    @FXML
    private Pane paneBackGround;
    @FXML
    private Pane paneSnake;
    @FXML
    private Pane paneShoot;
    @FXML
    private Pane paneSpawn;
    @FXML
    private Pane paneBadSnakes;
    @FXML
    private Label bulletsAmount;
    @FXML
    private Label pointsAmount;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label time;
    @FXML
    private ImageView finishImage;

    private Snake snake;
    private BadSnake badSnake;
    private Shoot shoot;
    private int points = 0;
    private double progress = 0;
    private long timeElapsed;


    private void startBadSnakes(){
        badSnake.newBadSnake("Right");
        badSnake.newBadSnake("Left");
        badSnake.newBadSnakeRandomDirection();
      //  badSnake.newBadSnake("Up");
       // badSnake.newBadSnake("Down");
        //badSnake.newBadSnake("Up");
        //badSnake.newBadSnakeRandomDirection();
        //badSnake.newBadSnakeRandomDirection();
       // badSnake.newBadSnakeRandomDirection();
        badSnake.startSnakes();
        badSnake.start();
    }



    public void onButtonClickMenu(){
        LevelsSwitcherController.SinglePlayerWindow.hide();
        GamePanel.LevelsSwitcher.show();
        snake.setRunning(false);
        badSnake.setRunning(false);
    }


    public void keyboardMoves() {
        LevelsSwitcherController.SinglePlayerScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SHIFT -> {
                    if(!snake.isRunning()) {
                        System.out.println("ENTER, stands for start");
                        snake.startSnake();
                        startBadSnakes();
                        badSnake.start = System.nanoTime();
                    }
                }
                case A -> {
                    System.out.println("A");
                    if(!snake.getCurrentDirection().equals("Right"))
                        snake.setCurrentDirection("Left");
                }
                case W -> {
                    System.out.println("W");
                    if(!snake.getCurrentDirection().equals("Down"))
                        snake.setCurrentDirection("Up");
                }
                case S -> {
                    System.out.println("S");
                    if(!snake.getCurrentDirection().equals("Up"))
                        snake.setCurrentDirection("Down");
                }
                case D -> {
                    System.out.println("D");
                    if(!snake.getCurrentDirection().equals("Left"))
                        snake.setCurrentDirection("Right");
                }
                case SPACE -> {
                    if(snake.isRunning() && !shoot.isShoot()){
                        System.out.println("STRZELAM KURWO!!");
                        shoot.doShoot(snake.getSnakeHeadX(),snake.getSnakeHeadY(),snake.getCurrentDirection());
                    }
                }
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int height = 575;
        int size = 25;
        int row = height / size;
        int width = 700;

        for(int i = 0; i< row +1; i++){
            Line line = new Line(0,i* size, width,i* size);
            paneBackGround.getChildren().add(line);
        }

        int col = width / size;
        for(int i = 0; i< col; i++){
            Line line = new Line(i* size,0,i* size, height);
            paneBackGround.getChildren().add(line);
        }

        badSnake = new BadSnake(paneBadSnakes, pointsAmount, progressBar);
        shoot = new Shoot(size,paneShoot,paneSpawn, bulletsAmount,badSnake);
        snake = new Snake(paneSnake, shoot, size);
        badSnake.setSnake(snake);
        badSnake.setTimeLabel(time);
        badSnake.setFinishImage(finishImage);
        bulletsAmount.setText(Integer.toString(shoot.getAmmo()));
        pointsAmount.setText(Integer.toString(0));
    }
}
