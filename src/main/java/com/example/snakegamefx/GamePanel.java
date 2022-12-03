package com.example.snakegamefx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GamePanel implements Initializable {

    @FXML
    private Button snake;
    @FXML
    private Pane paneSnakeGamePanel;
    @FXML
    private Button buttonSP;
    @FXML
    private Button buttonMP;
    private SnakeDecoration snakeDecoration;
    private final Container container;
    public GamePanel(Container container) {
        this.container = container;
    }

    public void onSingleGamePicClick() {
        System.out.println("Wybrano grę jedeno osobową ;)");
        try{
            container.setSnakeDecoration(snakeDecoration);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("LevelsSwitcherSinglePlayer.fxml"));
            fxmlLoader.setControllerFactory(c -> new LevelsSwitcherControllerSP(container));
            Scene singlePlayerScene = new Scene(fxmlLoader.load());
            Stage levelsSwitcher = new Stage();
            levelsSwitcher.setResizable(false);
            levelsSwitcher.setTitle("Choose level!");
            levelsSwitcher.setScene(singlePlayerScene);
            levelsSwitcher.show();
            container.setLevelSwitcherSP(levelsSwitcher);
            container.getLevelSwitcherSP().getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/icon.png")).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        container.getGamePanelWindow().hide();
        snakeDecoration.setRunning(false);
    }

    public void addSnake(){
        snakeDecoration.addRandomDecorateSnake();
    }

    public void onDobleGamePicClick(){
        System.out.println("Wybrano grę dwu osobową ;o");
        try{
            container.setSnakeDecoration(snakeDecoration);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("MultiPlayerSwitcher.fxml"));
            fxmlLoader.setControllerFactory(c -> new MultiPlayerSwitcherController(container));
            Scene multiPlayerScene = new Scene(fxmlLoader.load());
            Stage levelsSwitcher = new Stage();
            levelsSwitcher.setResizable(false);
            levelsSwitcher.setTitle("Choose map!");
            levelsSwitcher.setScene(multiPlayerScene);
            levelsSwitcher.show();
            container.setLevelSwitcherSP(levelsSwitcher);
            container.getLevelSwitcherSP().getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/icon.png")).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        container.getGamePanelWindow().hide();
        snakeDecoration.setRunning(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView imageSP = new ImageView(Objects.requireNonNull(getClass().getResource("img/people.png")).toExternalForm());
        imageSP.setFitHeight(80);
        imageSP.setFitWidth(80);
        buttonSP.setGraphic(imageSP);
        ImageView imageMP = new ImageView(Objects.requireNonNull(getClass().getResource("img/two-players.png")).toExternalForm());
        imageMP.setFitHeight(80);
        imageMP.setFitWidth(80);
        buttonMP.setGraphic(imageMP);
        System.out.println("Inited SnakeDecotation");
        snakeDecoration = new SnakeDecoration(paneSnakeGamePanel,25,60);
        snakeDecoration.newSnakeDecoration("Up");
        snakeDecoration.newSnakeDecoration("Right");
        snakeDecoration.newSnakeDecoration("Down");
        snakeDecoration.newSnakeDecoration("Left");
        snakeDecoration.startSnakes();
        ImageView imageSnake = new ImageView(Objects.requireNonNull(getClass().getResource("img/snake.png")).toExternalForm());
        imageSnake.setFitHeight(30);
        imageSnake.setFitWidth(30);
        snake.setGraphic(imageSnake);
    }
}
