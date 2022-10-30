package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private final Container container;

    private Snake snake;
    private BadSnake badSnake;
    private Shoot shoot;
    private int points = 0;
    private double progress = 0;
    public long start;
    private boolean justFinished = false;

    GameFrameControllerSinglePlayer(Container container){
        this.container = container;
    }

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
       // badSnake.start();
    }

    public void addPoints(){
        points++;
        Platform.runLater(() -> {
            pointsAmount.setText(Integer.toString(points));
            progress += 0.1;
            progressBar.setProgress(progress);

        });

        if(points >= 2){
            finishTheGame();
        }
    }

    private void resetLevel(){
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

    private void showFinishedScreen(){
        finishImage = new ImageView(Objects.requireNonNull(getClass().getResource("img/zdjece.JPG")).toExternalForm());
        finishImage.setFitHeight(600);
        finishImage.setFitWidth(600);
        Platform.runLater(() -> paneSnake.getChildren().add(finishImage));
    }

    public void onButtonClickMenu(){
        container.getLevelSwitcher().hide();
        container.getLevelSwitcher().show();
        snake.setRunning(false);
        badSnake.setRunning(false);
    }

    public void keyboardMoves() {
        container.getLevelSwitcherScene().setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SHIFT -> {
                    if(!snake.isRunning() && !justFinished) {
                        System.out.println("ENTER, stands for start");
                        snake.startSnake();
                        startBadSnakes();
                        start = System.nanoTime();
                    }
                    if(!snake.isRunning() && justFinished){
                        resetLevel();
                        justFinished = false;
                        System.out.println("Great! Try to improve your time!");
                        snake.resetOrInitLevel();
                        badSnake.resetOrInitLevel();
                        snake.startSnake();
                        startBadSnakes();
                        start = System.nanoTime();
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
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        try {
//            FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GameFrameSinglePlayer.fxml")));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
////        try {
////            Pane p = fxmlLoader.load(Objects.requireNonNull(getClass().getResource("src/main/resources/com/example/snakegamefx/GameFrameSinglePlayer.fxml")).openStream());
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//        GameFrameControllerSinglePlayer controller = fxmlLoader.getController();
        badSnake = new BadSnake(paneBadSnakes,container);
        shoot = new Shoot(size,paneShoot,paneSpawn, bulletsAmount,badSnake);
        snake = new Snake(paneSnake, shoot, size);
        bulletsAmount.setText(Integer.toString(shoot.getAmmo()));
        pointsAmount.setText(Integer.toString(0));
    }
}
