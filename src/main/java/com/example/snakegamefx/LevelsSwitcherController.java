package com.example.snakegamefx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
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
            singlePlayerWindow.setTitle("Single Player");
            singlePlayerWindow.setScene(singlePlayerScene);
            singlePlayerWindow.show();
            container.setLevelSwitcherScene(singlePlayerScene);
            container.setGameFrameControllerSinglePlayer(fxmlLoader.getController());
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
