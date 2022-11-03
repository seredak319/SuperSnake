package com.example.snakegamefx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LevelsSwitcherController {

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onMenuButtonClick(){
        container.getLevelSwitcher().hide();
        container.getGamePanelWindow().show();
        container.getSnakeDecoration().startSnakes();
    }
}
