package com.example.snakegamefx;

import javafx.application.Platform;
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

public class LevelsSwitcherController implements Initializable {

    @FXML
    private Button back;
    @FXML
    private Button lev1;
    @FXML
    private Button lev2;
    @FXML
    private Button lev3;
    @FXML
    private Button tc;
    @FXML
    private Pane screen;
    private final Container container;
    public LevelsSwitcherController(Container container){
        this.container = container;
    }

    public void onMainButtonClick(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("GameFrameSinglePlayer.fxml"));
            fxmlLoader.setControllerFactory( c -> new GameFrameControllerSinglePlayer(container));
            Scene singlePlayerScene = new Scene(fxmlLoader.load());
            Stage singlePlayerWindow = new Stage();
            singlePlayerWindow.setTitle("Time challenge");
            singlePlayerWindow.setScene(singlePlayerScene);
            singlePlayerWindow.show();
            singlePlayerWindow.setResizable(false);
            container.setLevelSwitcherScene(singlePlayerScene);
            container.setGameFrameControllerSinglePlayer(fxmlLoader.getController());
            container.setTimeChallenge(singlePlayerWindow);
            container.getTimeChallenge().setOnCloseRequest(event -> container.getGameFrameControllerSinglePlayer().running = false);
            singlePlayerWindow.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/icon.png")).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLevelOneClick(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("LevelOne.fxml"));
            fxmlLoader.setControllerFactory( c -> new LevelOne(container));
            Scene singlePlayerScene = new Scene(fxmlLoader.load());
            Stage singlePlayerWindow = new Stage();
            singlePlayerWindow.setTitle("Level 1");
            singlePlayerWindow.setScene(singlePlayerScene);
            singlePlayerWindow.show();
            singlePlayerWindow.setResizable(false);
            container.setLevelSwitcherScene(singlePlayerScene);
            container.setGameFrameControllerSinglePlayer(fxmlLoader.getController());
            container.setLevelOne(fxmlLoader.getController());
            container.setLevelOneStage(singlePlayerWindow);
            container.getLevelOneStage().setOnCloseRequest(event -> container.getGameFrameControllerSinglePlayer().running = false);
            singlePlayerWindow.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/icon.png")).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onLevelTwoClick(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("LevelTwo.fxml"));
            fxmlLoader.setControllerFactory( c -> new LevelTwo(container));
            Scene singlePlayerScene = new Scene(fxmlLoader.load());
            Stage singlePlayerWindow = new Stage();
            singlePlayerWindow.setTitle("Level 2");
            singlePlayerWindow.setScene(singlePlayerScene);
            singlePlayerWindow.show();
            singlePlayerWindow.setResizable(false);
            container.setLevelSwitcherScene(singlePlayerScene);
            container.setGameFrameControllerSinglePlayer(fxmlLoader.getController());
            container.setLevelTwo(fxmlLoader.getController());
            container.setLevelTwoStage(singlePlayerWindow);
            container.getLevelTwoStage().setOnCloseRequest(event -> container.getGameFrameControllerSinglePlayer().running = false);
            singlePlayerWindow.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/icon.png")).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLevelThreeClick(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("LevelThree.fxml"));
            fxmlLoader.setControllerFactory( c -> new LevelThree(container));
            Scene singlePlayerScene = new Scene(fxmlLoader.load());
            Stage singlePlayerWindow = new Stage();
            singlePlayerWindow.setTitle("Level 3");
            singlePlayerWindow.setScene(singlePlayerScene);
            singlePlayerWindow.show();
            singlePlayerWindow.setResizable(false);
            container.setLevelSwitcherScene(singlePlayerScene);
            container.setGameFrameControllerSinglePlayer(fxmlLoader.getController());
            container.setLevelThree(fxmlLoader.getController());
            container.setLevelThreeStage(singlePlayerWindow);
            container.getLevelThreeStage().setOnCloseRequest(event -> container.getGameFrameControllerSinglePlayer().running = false);
            singlePlayerWindow.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/icon.png")).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onMenuButtonClick(){
        container.getLevelSwitcher().hide();
        container.getGamePanelWindow().show();
        container.getSnakeDecoration().startSnakes();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("img/back.jpg")).toExternalForm());
        imageView.setFitHeight(400);
        imageView.setFitWidth(600);
        Platform.runLater(() -> screen.getChildren().add(imageView));
        ImageView imageMP1 = new ImageView(Objects.requireNonNull(getClass().getResource("img/1.png")).toExternalForm());
        imageMP1.setFitHeight(70);
        imageMP1.setFitWidth(70);
        lev1.setGraphic(imageMP1);
        ImageView imageMP2 = new ImageView(Objects.requireNonNull(getClass().getResource("img/2.png")).toExternalForm());
        imageMP2.setFitHeight(70);
        imageMP2.setFitWidth(70);
        lev2.setGraphic(imageMP2);
        ImageView imageMP3 = new ImageView(Objects.requireNonNull(getClass().getResource("img/3.png")).toExternalForm());
        imageMP3.setFitHeight(70);
        imageMP3.setFitWidth(70);
        lev3.setGraphic(imageMP3);
        ImageView imageMP = new ImageView(Objects.requireNonNull(getClass().getResource("img/stopwatch.png")).toExternalForm());
        imageMP.setFitHeight(70);
        imageMP.setFitWidth(70);
        tc.setGraphic(imageMP);
        ImageView imageBack = new ImageView(Objects.requireNonNull(getClass().getResource("img/reply.png")).toExternalForm());
        imageBack.setFitHeight(30);
        imageBack.setFitWidth(30);
        back.setGraphic(imageBack);
    }
}
