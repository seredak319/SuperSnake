package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class GameFrameControllerMultiPlayer implements Initializable {

    @FXML
    private Button backToMenu;
    @FXML
    private Pane paneBackGround;
    @FXML
    private Pane paneSnake1;
    @FXML
    private Pane paneSnake2;
    @FXML
    private Pane paneSpawn1;
    @FXML
    private Pane paneSpawn2;
    @FXML
    private Pane paneShoot1;
    @FXML
    private Pane paneShoot2;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label hp1;
    @FXML
    private Label hp2;
    private final Container container;
    private final double mapSizeInt;
    private final double snakeSpeedInt;
    private final double shootSpeedInt;
    private final int whichMap;
    private int row;
    private int col;
    private double height;
    private double width;
    private int size;
    private int DELAY;
    private int shootDELAY;
    private boolean running = false;

    private final int bodyParts = 4;

    private MultiPlayerSnake snake1;
    private MultiPlayerSnake snake2;
    private boolean restart = false;

    public GameFrameControllerMultiPlayer(Container container, double mapSizeInt, double snakeSpeedInt, double shootSpeedInt, int whichMap) {
        this.container = container;
        this.mapSizeInt = mapSizeInt;
        this.snakeSpeedInt = snakeSpeedInt;
        this.shootSpeedInt = shootSpeedInt;
        this.whichMap = whichMap;
        System.out.println("New instance");
    }

    public void startGame() {
        if(restart){
            restart = false;
            snake1.shoot.setAmmo(5);
            snake2.shoot.setAmmo(5);
            snake1.setHealth(3);
            snake2.setHealth(3);
        }

        if(snake1 != null)
            snake1.startSnake();
        if(snake2 != null)
            snake2.startSnake();
    }

    public void killTheGame(){
        if(snake1 != null)
            snake1.killSnake();
        if(snake2 != null)
            snake2.killSnake();
        running = false;
        restart = true;

        Platform.runLater((() -> paneSpawn1.getChildren().clear()));
        Platform.runLater((() -> paneSpawn2.getChildren().clear()));


    }

    private void firstPlayerWon(){

    }

    private void secondPlayerWon(){

    }

    private void countSize(){
        if (mapSizeInt == 0.0) {
            size = 50;
        } else if (mapSizeInt == 25.0) {
            size = 30;
        } else if (mapSizeInt == 50.0) {
            size = 25;
        } else if (mapSizeInt == 75.0) {
            size = 20;
        } else if (mapSizeInt == 100.0) {
            size = 15;
        }
        System.out.println("Wybrano size:" + size);
        container.setSizeMP(size);
    }

    private void countSnakeSpeed(){
        if (snakeSpeedInt == 0.0) {
            DELAY = 240;
        } else if (snakeSpeedInt == 25.0) {
            DELAY = 210;
        } else if (snakeSpeedInt == 50.0) {
            DELAY = 180;
        } else if (snakeSpeedInt == 75.0) {
            DELAY = 140;
        } else if (snakeSpeedInt == 100.0) {
            DELAY = 120;
        }
        System.out.println("Wybrano size:" + size);
        container.setSizeMP(size);
    }

    private void countShootSpeed(){
        if (shootSpeedInt == 0.0) {
            shootDELAY = 80;
        } else if (shootSpeedInt == 25.0) {
            shootDELAY = 60;
        } else if (shootSpeedInt == 50.0) {
            shootDELAY = 50;
        } else if (shootSpeedInt == 75.0) {
            shootDELAY = 40;
        } else if (shootSpeedInt == 100.0) {
            shootDELAY = 30;
        }
        System.out.println("Wybrano size:" + size);
        container.setSizeMP(size);
    }

    private void init() {
        System.out.println("Map size: " + mapSizeInt);
        System.out.println("Snake speed: " + snakeSpeedInt);
        System.out.println("Shoot speed: " + shootSpeedInt);


        height = paneBackGround.getPrefHeight();
        width = paneBackGround.getPrefWidth();

        countSize();
        countSnakeSpeed();
        countShootSpeed();

        row = (int) (height / size);
        System.out.println("row: "+row);
        col = (int) (width / size);
        System.out.println("col: "+col);

        container.setRowMP(row);
        container.setColMP(col);

        for (int i = 0; i < row + 1; i++) {
            Line line = new Line(0, i * size, width, i * size);
            paneBackGround.getChildren().add(line);
        }

        for (int i = 0; i < col; i++) {
            Line line = new Line(i * size, 0, i * size, height);
            paneBackGround.getChildren().add(line);
        }


       snake1 = new MultiPlayerSnake(paneSnake1,paneShoot1,paneSpawn1, label1,hp1,container,size,DELAY, shootDELAY, 1,0,0,"Right",container.getLevelSwitcherSceneMP());
       snake2 = new MultiPlayerSnake(paneSnake2,paneShoot2,paneSpawn2, label2,hp2,container,size,300,shootDELAY,2,col*size - bodyParts*size,row*size-size,"Left",container.getLevelSwitcherSceneMP());
       container.setSnake1(snake1);
       container.setSnake2(snake2);
    }

    public void onButtonClickMenu(){
        backToMenu.getScene().getWindow().hide();
       // container.getLevelSwitcherMP().show();
        killTheGame();

    }

    public void keyboardMovesMP() {
        container.getLevelSwitcherSceneMP().setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SHIFT -> {
                    if(!running){
                        container.getGameFrameControllerMultiPlayer().startGame();
                        running = true;
                    }
                }
                case A -> {
                    if(!snake1.getCurrentDirection().equals("Right"))
                        snake1.setCurrentDirection("Left");
                }
                case W -> {
                    if(!snake1.getCurrentDirection().equals("Down"))
                        snake1.setCurrentDirection("Up");
                }
                case S -> {
                    if(!snake1.getCurrentDirection().equals("Up"))
                        snake1.setCurrentDirection("Down");
                }
                case D -> {
                    if(!snake1.getCurrentDirection().equals("Left"))
                        snake1.setCurrentDirection("Right");
                }
                case SPACE -> {
                    if(snake1.isRunning() && !snake1.shoot.isShoot()){
                        System.out.println("STRZELAM KURWO!!");
                        snake1.shoot.doShoot(snake1.getSnakeHeadX(),snake1.getSnakeHeadY(),snake1.getCurrentDirection());
                    }
                }
                case LEFT -> {
                    if(!snake2.getCurrentDirection().equals("Right"))
                        snake2.setCurrentDirection("Left");
                }
                case UP -> {
                    if(!snake2.getCurrentDirection().equals("Down"))
                        snake2.setCurrentDirection("Up");
                }
                case DOWN -> {
                    if(!snake2.getCurrentDirection().equals("Up"))
                        snake2.setCurrentDirection("Down");
                }
                case RIGHT -> {
                    if(!snake2.getCurrentDirection().equals("Left"))
                        snake2.setCurrentDirection("Right");
                }
                case CONTROL -> {
                    if(snake2.isRunning() && !snake2.shoot.isShoot()){
                        System.out.println("STRZELAM KURWO!!");
                        snake2.shoot.doShoot(snake2.getSnakeHeadX(),snake2.getSnakeHeadY(),snake2.getCurrentDirection());
                    }
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView imageBack = new ImageView(Objects.requireNonNull(getClass().getResource("img/reply.png")).toExternalForm());
        imageBack.setFitHeight(30);
        imageBack.setFitWidth(30);
        backToMenu.setGraphic(imageBack);
        init();
    }
}
