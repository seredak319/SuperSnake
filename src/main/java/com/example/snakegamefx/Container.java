package com.example.snakegamefx;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Container {

    private GameFrameControllerSinglePlayer gameFrameControllerSinglePlayer;

    private Stage GamePanelWindow;

    private SnakeDecoration snakeDecoration;

    private Stage LevelSwitcher;

    private Scene LevelSwitcherScene;

    private Stage timeChallenge;

    public Scene getLevelSwitcherScene() {
        return LevelSwitcherScene;
    }

    public void setLevelSwitcherScene(Scene levelSwitcherScene) {
        if(notSetYet(levelSwitcherScene)) {
            LevelSwitcherScene = levelSwitcherScene;
        }
    }

    public Stage getLevelSwitcher() {
        return LevelSwitcher;
    }

    public void setLevelSwitcher(Stage levelSwitcher) {
        if(notSetYet(levelSwitcher))
        LevelSwitcher = levelSwitcher;
    }

    public SnakeDecoration getSnakeDecoration() {
        return snakeDecoration;
    }

    public void setSnakeDecoration(SnakeDecoration snakeDecoration) {
        if(notSetYet(snakeDecoration))
        this.snakeDecoration = snakeDecoration;
    }

    public Stage getGamePanelWindow() {
        return GamePanelWindow;
    }

    public void setGamePanelWindow(Stage gamePanelWindow) {
        if(notSetYet(gamePanelWindow))
        GamePanelWindow = gamePanelWindow;
    }

    public void setGameFrameControllerSinglePlayer(GameFrameControllerSinglePlayer gameFrameControllerSinglePlayer){
        if(notSetYet(gameFrameControllerSinglePlayer))
        this.gameFrameControllerSinglePlayer = gameFrameControllerSinglePlayer;
    }

    public GameFrameControllerSinglePlayer getGameFrameControllerSinglePlayer() {
        return gameFrameControllerSinglePlayer;
    }

    public Stage getTimeChallenge() {
        return timeChallenge;
    }

    public void setTimeChallenge(Stage timeChallenge) {
        if(notSetYet(timeChallenge))
        this.timeChallenge = timeChallenge;
    }



    private <T> boolean notSetYet(T t){
        return t != null;
    }
}
