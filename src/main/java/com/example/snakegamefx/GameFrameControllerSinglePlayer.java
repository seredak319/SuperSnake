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
import javafx.stage.WindowEvent;

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
    private ImageView finishImage;
    @FXML
    private Button backToMenu;
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
        badSnake.startSnakes();
        start = System.nanoTime();
    }

    public void addPoints(){
        points++;
        Platform.runLater(() -> {
            pointsAmount.setText(Integer.toString(points));
            progress += 0.05;
            progressBar.setProgress(progress);

        });
        if(points >= 3){
            finishTheGame();
        }
    }

    public void resetLevel(){
        shoot.setAmmo(500);
        points = 0;
        progress = 0;
        Platform.runLater(() -> {
            pointsAmount.setText(Integer.toString(points));
            progressBar.setProgress(progress);
            time.setText(Double.toString(0.00));
            bulletsAmount.setText(Integer.toString(500));
        });
    }

    public void finishTheGame(){
        running = false;
        justFinished = true;
        long end = System.nanoTime();
        double timeElapsed = (double) (end - start)/1_000_000_000;
        snake.setRunning(false);
        badSnake.setRunning(false);
        System.out.println("Wow! Your time is: " + (timeElapsed));
        shoot.clearSpawnedAmmo();
        Platform.runLater(() -> time.setText(Double.toString(timeElapsed)));
        showFinishedScreen();
    }

    public void showFinishedScreen(){
        finishImage = new ImageView(Objects.requireNonNull(getClass().getResource("img/zdjece.JPG")).toExternalForm());
        finishImage.setFitHeight(600);
        finishImage.setFitWidth(600);
        Platform.runLater(() -> paneSnake.getChildren().add(finishImage));
    }

    public void onButtonClickMenu(){
        backToMenu.getScene().getWindow().hide();
        container.getLevelSwitcher().show();
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
            snake.resetOrInitLevel();
            badSnake.resetOrInitLevel();
            snake.startSnake();
            shoot.clearSpawnedAmmo();
            startBadSnakes();
        }
    }

    public void keyboardMoves() {
        container.getLevelSwitcherScene().setOnKeyPressed(e -> {
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
        shoot = new Shoot(size,paneShoot,paneSpawn, bulletsAmount,badSnake, container);
        snake = new Snake(paneSnake, container, size);
        container.setBadSnake(badSnake);
        container.setShoot(shoot);
        container.setSnake(snake);
        bulletsAmount.setText(Integer.toString(shoot.getAmmo()));
        pointsAmount.setText(Integer.toString(0));
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
        init();
    }
}
