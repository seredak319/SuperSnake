package com.example.snakegamefx;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Container {

    private GameFrameControllerSinglePlayer gameFrameControllerSinglePlayer;

    private Stage GamePanelWindow;

    private SnakeDecoration snakeDecoration;

    private Stage LevelSwitcher;

    private Scene LevelSwitcherScene;


    public Scene getLevelSwitcherScene() {
        return LevelSwitcherScene;
    }

    public void setLevelSwitcherScene(Scene levelSwitcherScene) {
        LevelSwitcherScene = levelSwitcherScene;
    }

    public Stage getLevelSwitcher() {
        return LevelSwitcher;
    }

    public void setLevelSwitcher(Stage levelSwitcher) {
        LevelSwitcher = levelSwitcher;
    }

    public SnakeDecoration getSnakeDecoration() {
        return snakeDecoration;
    }

    public void setSnakeDecoration(SnakeDecoration snakeDecoration) {
        this.snakeDecoration = snakeDecoration;
    }

    public Stage getGamePanelWindow() {
        return GamePanelWindow;
    }

    public void setGamePanelWindow(Stage gamePanelWindow) {
        GamePanelWindow = gamePanelWindow;
    }

    public void setGameFrameControllerSinglePlayer(GameFrameControllerSinglePlayer gameFrameControllerSinglePlayer){
        this.gameFrameControllerSinglePlayer = gameFrameControllerSinglePlayer;
    }

    public GameFrameControllerSinglePlayer getGameFrameControllerSinglePlayer() {
        return gameFrameControllerSinglePlayer;
    }
}
