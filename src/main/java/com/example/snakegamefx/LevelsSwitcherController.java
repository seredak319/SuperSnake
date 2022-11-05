package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LevelsSwitcherController implements Initializable {

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

    public void onMenuButtonClick(){
        container.getLevelSwitcher().hide();
        container.getGamePanelWindow().show();
        container.getSnakeDecoration().startSnakes();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("img/IMG_5444.png")).toExternalForm());
        imageView.setFitHeight(400);
        imageView.setFitWidth(600);
        Platform.runLater(() -> screen.getChildren().add(imageView));
    }
}
