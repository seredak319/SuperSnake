package com.example.snakegamefx;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Container {

    private GameFrameControllerSinglePlayer gameFrameControllerSinglePlayer;

    private LevelOne levelOne;

    private Stage GamePanelWindow;

    private Stage levelOneStage;

    private SnakeDecoration snakeDecoration;

    private Stage LevelSwitcher;

    private Scene LevelSwitcherScene;

    private Stage timeChallenge;

    private Shoot shoot;
    private BadSnake badSnake;
    private Obstacles obstacles;

    private Snake snake;
    private Boss boss;
    private LevelTwo levelTwo;
    private Stage levelTwoStage;

    private LevelThree levelThree;
    private Stage levelThreeStage;



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

    public Stage getLevelOneStage() {
        return levelOneStage;
    }

    public void setLevelOneStage(Stage levelOneStage) {
        if(notSetYet(levelOneStage))
        this.levelOneStage = levelOneStage;
    }

    public LevelOne getLevelOne() {
        return levelOne;
    }

    public void setLevelOne(LevelOne levelOne) {
        this.levelOne = levelOne;
    }

    public Shoot getShoot() {
        return shoot;
    }

    public void setShoot(Shoot shoot) {
        this.shoot = shoot;
    }

    public BadSnake getBadSnake() {
        return badSnake;
    }

    public void setBadSnake(BadSnake badSnake) {
        this.badSnake = badSnake;
    }

    public Obstacles getObstacles() {
        return obstacles;
    }

    public void setObstacles(Obstacles obstacles) {
        this.obstacles = obstacles;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }


    public LevelTwo getLevelTwo() {
        return levelTwo;
    }

    public void setLevelTwo(LevelTwo levelTwo) {
        this.levelTwo = levelTwo;
    }

    public Stage getLevelTwoStage() {
        return levelTwoStage;
    }

    public void setLevelTwoStage(Stage  levelTwoStage) {
        this.levelTwoStage = levelTwoStage;
    }


    public LevelThree getLevelThree() {
        return levelThree;
    }

    public void setLevelThree(LevelThree levelThree) {
        this.levelThree = levelThree;
    }

    public Stage getLevelThreeStage() {
        return levelThreeStage;
    }

    public void setLevelThreeStage(Stage levelThreeStage) {
        this.levelThreeStage = levelThreeStage;
    }



    private <T> boolean notSetYet(T t){
        return t != null;
    }
}
