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
    private Label bulletsAmount;

    private final int hight =  575;
    private final int width = 700;
    private final int size = 25;
    private final int row = hight / size;
    private final int col = width / size;
    private static Snake snake;

    public static void kill(){
        if(snake.isRunning())
        snake.killSnake();
    }

    public void onButtonClickMenu(){
        SinglePlayerWindow.hide();
        SuperSnake.GamePanelWindow.show();
        if(snake.isRunning()) {
            snake.killSnake();
        }
        snake.setRunning(false);


    }

    public void keyboardMoves() {
        GamePanel.SinglePlayerScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SHIFT -> {
                    if(!snake.isRunning()) {
                        System.out.println("ENTER, stands for start");
                        snake.startSnake();
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
                    if(snake.isRunning() && !snake.isShoot()){
                        System.out.println("STRZELAM KURWO!!");
                        snake.doShot();
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

        snake = new Snake(paneSnake, paneShoot, paneSpawn, bulletsAmount, size );
        bulletsAmount.setText(Integer.toString(snake.getAmountOfAmmo()));
    }
}
