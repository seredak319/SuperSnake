package com.example.snakegamefx;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Container {



    private MultiPlayerSnake snake1;
    private MultiPlayerSnake snake2;
    private int sizeMP;
    private int rowMP;
    private int colMP;
    private GameFrameControllerSinglePlayer gameFrameControllerSinglePlayer;
    private GameFrameControllerMultiPlayer gameFrameControllerMultiPlayer;
    private LevelOne levelOne;
    private Stage GamePanelWindow;
    private Stage levelOneStage;
    private SnakeDecoration snakeDecoration;
    private Stage LevelSwitcherSP;
    private Stage LevelSwitcherMP;
    private Scene LevelSwitcherSceneSP;
    private Scene LevelSwitcherSceneMP;
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

    public Scene getLevelSwitcherSceneSP() {
        return LevelSwitcherSceneSP;
    }

    public void setLevelSwitcherSceneSP(Scene levelSwitcherSceneSP) {
            LevelSwitcherSceneSP = levelSwitcherSceneSP;
    }

    public Scene getLevelSwitcherSceneMP() {
        return LevelSwitcherSceneMP;
    }

    public void setLevelSwitcherSceneMP(Scene levelSwitcherSceneMP) {
        this.LevelSwitcherSceneMP = levelSwitcherSceneMP;
    }

    public Stage getLevelSwitcherSP() {
        return LevelSwitcherSP;
    }

    public void setLevelSwitcherSP(Stage levelSwitcherSP) {
    LevelSwitcherSP = levelSwitcherSP;
    }

    public Stage getLevelSwitcherMP() {
        return LevelSwitcherMP;
    }

    public void setLevelSwitcherMP(Stage levelSwitcherMP) {
        LevelSwitcherMP = levelSwitcherMP;
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

    public void setGameFrameControllerMulitPlayer(GameFrameControllerMultiPlayer gameFrameControllerMulitPlayer){
        this.gameFrameControllerMultiPlayer = gameFrameControllerMulitPlayer;
    }

    public GameFrameControllerMultiPlayer getGameFrameControllerMultiPlayer() {
        return gameFrameControllerMultiPlayer;
    }

    public Stage getTimeChallenge() {
        return timeChallenge;
    }

    public void setTimeChallenge(Stage timeChallenge) {
    this.timeChallenge = timeChallenge;
    }

    public Stage getLevelOneStage() {
        return levelOneStage;
    }

    public void setLevelOneStage(Stage levelOneStage) {
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

    public int getSizeMP() {
        return sizeMP;
    }

    public void setSizeMP(int sizeMP) {
        this.sizeMP = sizeMP;
    }

    public int getRowMP() {
        return rowMP;
    }

    public void setRowMP(int rowMP) {
        this.rowMP = rowMP;
    }

    public int getColMP() {
        return colMP;
    }

    public void setColMP(int colMP) {
        this.colMP = colMP;
    }

    public MultiPlayerSnake getSnake1() {
        return snake1;
    }

    public void setSnake1(MultiPlayerSnake snake1) {
        this.snake1 = snake1;
    }

    public MultiPlayerSnake getSnake2() {
        return snake2;
    }

    public void setSnake2(MultiPlayerSnake snake2) {
        this.snake2 = snake2;
    }
}
