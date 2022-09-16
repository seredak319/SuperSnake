package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;

import javax.net.ssl.KeyManager;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.snakegamefx.GamePanel.SinglePlayerWindow;

public class GameFrameControllerSinglePlayer implements Initializable {




    @FXML
    private Pane paneSP;
    @FXML
    private Button backToMenu;

    private final int hight =  575;
    private final int width = 700;
    private final int size = 25;
    private final int row = hight / size;
    private final int col = width / size;
    private String currentDirection = "Right";
    private int bodyParts = 4;
    private final int DELAY = 100;
    private int x[] = new int[bodyParts];
    private int y[] = new int[bodyParts];
    public static Timer timer;
    public static TimerTask timerTask;
    private boolean running = false;
    private void paintSnake(){
        paneSP.getChildren().removeAll();
        Platform.runLater((new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < bodyParts - 1; i++) {
                    Rectangle rectangle = new Rectangle();
                    rectangle.setX(x[i]);
                    rectangle.setY(y[0]);
                    rectangle.setHeight(size);
                    rectangle.setWidth(size);
                    rectangle.setFill(Color.BLACK);
                    paneSP.getChildren().add(rectangle);
                }
                Rectangle rectangleHead = new Rectangle();
                rectangleHead.setX(x[3]);
                rectangleHead.setY(y[3]);
                rectangleHead.setHeight(size);
                rectangleHead.setWidth(size);
                rectangleHead.setFill(Color.CYAN);
                paneSP.getChildren().add(rectangleHead);
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
        paintSnake();
        running = true;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("DUPA");
                moveSnake();
            }
        };
        timer.schedule(timerTask,0,500);
    }

    private void moveSnake(){


        for(int i=bodyParts-1; i >0; i--){
            x[i-1] = x[i];
            y[i-1] = y[i];
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
        HelloApplication.GamePanelWindow.show();
        if(running) {
            timer.cancel();
            timerTask.cancel();
        }
    }


    public void keyboardMoves() {
        GamePanel.SinglePlayerScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
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
                case SHIFT -> {
                    if(!running) {
                        System.out.println("ENTER, stands for start");
                        startGame();
                    }
                }
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i=0; i<row+1; i++){
            Line line = new Line(0,i*size,width,i*size);
            paneSP.getChildren().add(line);
        }

        for(int i=0; i<col; i++){
            Line line = new Line(i*size,0,i*size,hight);
            paneSP.getChildren().add(line);
        }
        reset();
    }
}
