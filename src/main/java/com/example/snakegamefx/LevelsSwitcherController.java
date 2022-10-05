package com.example.snakegamefx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LevelsSwitcherController {

    public static Stage SinglePlayerWindow;
    public static Scene SinglePlayerScene;
    private final SnakeDecoration snakeDecoration;

    public LevelsSwitcherController(SnakeDecoration snakeDecoration){
        this.snakeDecoration = snakeDecoration;
    }

    public void onMainButtonClick(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("GameFrameSinglePlayer.fxml"));
          //  fxmlLoader.setControllerFactory( c -> new GameFrameControllerSinglePlayer(snakeDecoration));
            SinglePlayerScene = new Scene(fxmlLoader.load());
            SinglePlayerWindow = new Stage();
            SinglePlayerWindow.setTitle("Single Player");
            SinglePlayerWindow.setScene(SinglePlayerScene);
            SinglePlayerWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onMenuButtonClick(){
        GamePanel.LevelsSwitcher.hide();
        SuperSnake.GamePanelWindow.show();
        snakeDecoration.startSnakes();
    }
}
