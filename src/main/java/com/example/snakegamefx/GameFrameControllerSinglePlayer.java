package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.*;
import static com.example.snakegamefx.GamePanel.SinglePlayerWindow;

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

    private final int hight =  575;
    private final int width = 700;
    private final int size = 25;
    private final int row = hight / size;
    private final int col = width / size;
    private Snake snake;
    private BadSnake badSnake;
    private Shoot shoot;
    private SnakeDecoration snakeDecoration;


//    public GameFrameControllerSinglePlayer(SnakeDecoration snakeDecoration){
//        this.snakeDecoration = snakeDecoration;
//    }

    public void setSnakeDecoration(SnakeDecoration snakeDecoration){
        this.snakeDecoration = snakeDecoration;
    }

    private void startBadSnakes(){
       // badSnake.newBadSnake("Right");
        badSnake.newBadSnakeRandomDirection();
        badSnake.newBadSnakeRandomDirection();
        badSnake.newBadSnakeRandomDirection();
        badSnake.startSnakes();
        badSnake.start();
    }


    public void onButtonClickMenu(){
        SinglePlayerWindow.hide();
        SuperSnake.GamePanelWindow.show();
//
        SnakeDecoration.startSnakes();
        snake.setRunning(false);
        badSnake.setRunning(false);

    }


    public void keyboardMoves() {
        GamePanel.SinglePlayerScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SHIFT -> {
                    if(!snake.isRunning()) {
                        System.out.println("ENTER, stands for start");
                        snake.startSnake();
                        startBadSnakes();
                    }
                }
                case A -> {
                    System.out.println("A");
                    if(snake.getCurrentDirection() != "Right")
                        snake.setCurrentDirection("Left");
                }
                case W -> {
                    System.out.println("W");
                    if(snake.getCurrentDirection() != "Down")
                        snake.setCurrentDirection("Up");
                }
                case S -> {
                    System.out.println("S");
                    if(snake.getCurrentDirection() != "Up")
                        snake.setCurrentDirection("Down");
                }
                case D -> {
                    System.out.println("D");
                    if(snake.getCurrentDirection() != "Left")
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
        for(int i=0; i<row+1; i++){
            Line line = new Line(0,i*size,width,i*size);
            paneBackGround.getChildren().add(line);
        }

        for(int i=0; i<col; i++){
            Line line = new Line(i*size,0,i*size,hight);
            paneBackGround.getChildren().add(line);
        }

        badSnake = new BadSnake(paneBadSnakes);
        shoot = new Shoot(size,paneShoot,paneSpawn, bulletsAmount,badSnake);
        snake = new Snake(paneSnake, shoot, size );
        bulletsAmount.setText(Integer.toString(shoot.getAmmo()));
    }
}
