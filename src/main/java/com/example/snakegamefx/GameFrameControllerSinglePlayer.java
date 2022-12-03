package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.*;

public class GameFrameControllerSinglePlayer implements Initializable {

//TODO: niepotrzebnie tworzymy cały czas new Rectangle w shoot i snake; Można by było to stowrzyć raz i tylko zmieniac X i Y;   ++ optymalizacja


    @FXML
    private Pane paneBackGround;
    @FXML
    protected Pane paneSnake;
    @FXML
    protected Pane paneShoot;
    @FXML
    protected Pane paneSpawn;
    @FXML
    protected Pane paneBadSnakes;
    @FXML
    protected Label bulletsAmount;
    @FXML
    Label pointsAmount;
    @FXML
    ProgressBar progressBar;
    @FXML
    private Label time;
    @FXML
    protected ImageView finishImage;
    @FXML
    private Button backToMenu;
    @FXML
    protected Pane finishPane;
    private final Container container;
    Snake snake;
    BadSnake badSnake;
    Shoot shoot;
    int points = 0;
    double progress = 0;
    public long start;
    boolean justFinished = false;
    boolean running = false;

    GameFrameControllerSinglePlayer(Container container){
        this.container = container;
    }

    public void startBadSnakes(){
        badSnake.newBadSnake("Right");
        badSnake.newBadSnake("Left");
        badSnake.newBadSnakeRandomDirection();
        badSnake.startBadSnakes();
        start = System.nanoTime();
    }

    public void addPoints(){
        int n =10;
        points++;
        Platform.runLater(() -> {
            pointsAmount.setText(Integer.toString(points));
            progress += 1./n;
            progressBar.setProgress(progress);

        });
        if(points >= n){
            finishTheGame();
        }
    }

    public void resetLevel(){
        shoot.setAmmo(container.getShoot().START_VALUE);
        points = 0;
        progress = 0;
        Platform.runLater(() -> {
            pointsAmount.setText(Integer.toString(points));
            progressBar.setProgress(progress);
            time.setText("");
            bulletsAmount.setText(Integer.toString(container.getShoot().START_VALUE));
        });
    }

    public void finishTheGame(){
        container.getGameFrameControllerSinglePlayer().running = false;
        container.getGameFrameControllerSinglePlayer().justFinished = true;
        if(container.getSnake() != null)
            container.getSnake().killSnake();

        if(container.getBadSnake() != null){
            container.getBadSnake().killBadSnakes();
        }
        if(container.getObstacles() != null){
            container.getObstacles().killObstacles();
        }
        if(container.getBoss() != null){
            container.getBoss().killBoss();
        }
        if(time !=null){
            long end = System.nanoTime();
            double timeElapsed = (double) (end - start)/1_000_000_000;
            System.out.println("Wow! Your time is: " + (timeElapsed));
            Platform.runLater(() -> time.setText(Double.toString(timeElapsed)));
            finishTheGame(true);
        }
        container.getShoot().clearSpawnedAmmo();
    }

    public void finishTheGame(boolean win){
        if(win){
            ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("img/winner.png")).toExternalForm());
            imageView.setFitHeight(256);
            imageView.setFitWidth(256);
            imageView.setX(210);
            imageView.setY(210);
            Platform.runLater(() -> paneSnake.getChildren().add(imageView));
        }
    }

    public void onButtonClickMenu(){
        backToMenu.getScene().getWindow().hide();
        container.getLevelSwitcherSP().show();
        snake.setRunning(false);
        badSnake.setRunning(false);
        running = false;
    }

    private void startGame(){
        if(!snake.isRunning() && !justFinished) {
            running = true;
            System.out.println("ENTER, stands for start");
            snake.startSnake();
            startBadSnakes();
        }
        if(!snake.isRunning() && justFinished){
            running = true;
            resetLevel();
            justFinished = false;
            System.out.println("Great! Try to improve your time!");
            snake.startSnake();
            shoot.clearSpawnedAmmo();
            startBadSnakes();
        }
    }

    public void keyboardMoves() {
        container.getLevelSwitcherSceneSP().setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SHIFT -> {
                    if(!running){
                        startGame();
                        running = true;
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

    public void init(){
        int size = 25;
        badSnake = new BadSnake(paneBadSnakes,container);
        shoot = new Shoot(size,paneShoot,paneSpawn, bulletsAmount,badSnake, container,999);
        snake = new Snake(paneSnake, container, size);
        container.setBadSnake(badSnake);
        container.setShoot(shoot);
        container.setSnake(snake);
        bulletsAmount.setText(Integer.toString(shoot.getAmmo()));
        pointsAmount.setText(Integer.toString(0));
        finishImage = new ImageView(Objects.requireNonNull(getClass().getResource("img/timeChallengeStartPic.png")).toExternalForm());
        finishImage.setFitHeight(575);
        finishImage.setFitWidth(700);
        Platform.runLater(() -> finishPane.getChildren().add(finishImage));
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
        ImageView imageBack = new ImageView(Objects.requireNonNull(getClass().getResource("img/reply.png")).toExternalForm());
        imageBack.setFitHeight(30);
        imageBack.setFitWidth(30);
        backToMenu.setGraphic(imageBack);
        init();
    }
}
