package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MultiPlayerSwitcherController implements Initializable {
    @FXML
    private Button back;
    @FXML
    private Button lev1;
    @FXML
    private Button lev2;
    @FXML
    private Button lev3;
    @FXML
    private Pane screen;
    @FXML
    private Slider mapSize;
    @FXML
    private Slider snakeSpeed;
    @FXML
    private Slider shootSpeed;
    private double mapSizeInt;
    private double snakeSpeedInt;
    private double shootSpeedInt;
    private final Container container;

    public MultiPlayerSwitcherController(Container container){
        this.container = container;
    }


    public void onLevelOneClick(){
        readUserChoice();
        try {
            loadMultiLevel(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLevelTwoClick(){
        readUserChoice();
        try {
            loadMultiLevel(2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLevelThreeClick(){
        readUserChoice();
        try {
            loadMultiLevel(3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readUserChoice(){
        mapSizeInt = mapSize.getValue();
        snakeSpeedInt = snakeSpeed.getValue();
        shootSpeedInt = shootSpeed.getValue();

        System.out.println(mapSizeInt);
        System.out.println(snakeSpeedInt);
        System.out.println(shootSpeedInt);
    }

    private void loadMultiLevel(int whichMap) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("GameFrameMultiPlayer.fxml"));
        fxmlLoader.setControllerFactory(c -> new GameFrameControllerMultiPlayer(container, mapSizeInt, snakeSpeedInt, shootSpeedInt, whichMap));
        Scene multiPlayerScene = new Scene(fxmlLoader.load());
        Stage multiPlayerWindow = new Stage();
        multiPlayerWindow.setTitle("Multiplayer");
        multiPlayerWindow.setScene(multiPlayerScene);
        multiPlayerWindow.show();
        multiPlayerWindow.setResizable(false);
        multiPlayerWindow.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/icon.png")).toString()));
        container.setLevelSwitcherSceneMP(multiPlayerScene);
        container.setLevelSwitcherMP(multiPlayerWindow);
        container.setGameFrameControllerMulitPlayer(fxmlLoader.getController());
    }

    public void onMenuButtonClick(){
        back.getScene().getWindow().hide();
        container.getGamePanelWindow().show();
        container.getSnakeDecoration().startSnakes();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("img/back.jpg")).toExternalForm());
        imageView.setFitHeight(400);
        imageView.setFitWidth(600);
        Platform.runLater(() -> screen.getChildren().add(imageView));
        ImageView imageBack = new ImageView(Objects.requireNonNull(getClass().getResource("img/reply.png")).toExternalForm());
        imageBack.setFitHeight(30);
        imageBack.setFitWidth(30);
        back.setGraphic(imageBack);
        ImageView imageStart = new ImageView(Objects.requireNonNull(getClass().getResource("img/start-button.png")).toExternalForm());
        imageStart.setFitHeight(70);
        imageStart.setFitWidth(70);
        lev2.setGraphic(imageStart);
    }
}
