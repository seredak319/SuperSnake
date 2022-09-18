package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

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
    private Label bulletsAmount;

    private final int hight =  575;
    private final int width = 700;
    private final int size = 25;
    private final int row = hight / size;
    private final int col = width / size;
    private String currentDirection = "Right";
    private int bodyParts = 4;
    private final int DELAY = 200;
    private int x[] = new int[bodyParts];
    private int y[] = new int[bodyParts];
    public static Timer timer;
    public static TimerTask timerTask;
    public static boolean running = false;

    private Shoot shoot;



    private void paintSnake(){
        Platform.runLater((new Runnable() {
            @Override
            public void run() {
                paneSnake.getChildren().clear();
                for (int i = 0; i < bodyParts - 1; i++) {
                    Rectangle rectangle = new Rectangle();
                    rectangle.setX(x[i]);
                    rectangle.setY(y[i]);
                    rectangle.setHeight(size);
                    rectangle.setWidth(size);
                    rectangle.setFill(Color.BLACK);
                    paneSnake.getChildren().add(rectangle);
                }
                Rectangle rectangleHead = new Rectangle();
                rectangleHead.setX(x[3]);
                rectangleHead.setY(y[3]);
                rectangleHead.setHeight(size);
                rectangleHead.setWidth(size);
                rectangleHead.setFill(Color.CYAN);
                paneSnake.getChildren().add(rectangleHead);
            }
        }));
    }

    private void reset(){
        for(int i=0; i<bodyParts; i++){
            x[i] = size*i;
            y[i] = 0;
        }
    }

    private void startGame(){
        running = true;
        //Initiation of snake
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                moveSnake();
            }
        };
        timer.schedule(timerTask,0,DELAY);
    }

    private void moveSnake(){
        for(int i=0; i < bodyParts -1; i++){
            x[i] = x[i+1];
            y[i] = y[i+1];
        }

        switch(currentDirection){
            case "Up" -> {
                y[bodyParts-1] = y[bodyParts-1] - size;
            }
            case "Down" -> {
                y[bodyParts-1] = y[bodyParts-1] + size;
            }
            case "Left" -> {
                x[bodyParts-1] = x[bodyParts-1] - size;
            }
            case "Right" -> {
                x[bodyParts-1] = x[bodyParts-1] + size;
            }
        }
        paintSnake();
    }




    public void onButtonClickMenu(){
        SinglePlayerWindow.hide();
        SuperSnake.GamePanelWindow.show();
        if(running) {
            timer.cancel();
            timerTask.cancel();
        }
        running = false;
    }


    public void keyboardMoves() {
        GamePanel.SinglePlayerScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SHIFT -> {
                    if(!running) {
                        System.out.println("ENTER, stands for start");
                        startGame();
                    }
                }
                case A -> {
                    System.out.println("A");
                    if(currentDirection != "Right")
                        currentDirection = "Left";
                }
                case W -> {
                    System.out.println("W");
                    if(currentDirection != "Down")
                        currentDirection = "Up";
                }
                case S -> {
                    System.out.println("S");
                    if(currentDirection != "Up")
                        currentDirection = "Down";
                }
                case D -> {
                    System.out.println("D");
                    if(currentDirection != "Left")
                        currentDirection = "Right";
                }
                case SPACE -> {
                    if(running && !shoot.isShot()){
                        System.out.println("STRZELAM KURWO!!");
                        shoot.doShoot(x[bodyParts-1], y[bodyParts-1], currentDirection);
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

        shoot = new Shoot( size, paneShoot, bulletsAmount );

        bulletsAmount.setText(Integer.toString(shoot.getSTART_VALUE()));
        reset();
        paintSnake();
    }
}
