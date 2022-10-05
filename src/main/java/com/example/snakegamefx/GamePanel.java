package com.example.snakegamefx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import static com.example.snakegamefx.SuperSnake.GamePanelWindow;

public class GamePanel implements Initializable {

    @FXML
    private Pane paneSnakeGamePanel;
    @FXML
    private Button buttonSP;
    @FXML
    private Button buttonMP;
    public static Stage LevelsSwitcher;
    public static Scene SinglePlayerScene;
    public SnakeDecoration snakeDecoration;
    public FXMLLoader fxmlLoader;


    public void onSingleGamePicClick() {
        System.out.println("Wybrano grę jedeno osobową ;)");
//        try{
//            fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("GameFrameSinglePlayer.fxml"));
//            fxmlLoader.setControllerFactory( c -> new GameFrameControllerSinglePlayer(snakeDecoration));
//            SinglePlayerScene = new Scene(fxmlLoader.load());
//            SinglePlayerWindow = new Stage();
//            SinglePlayerWindow.setTitle("Single Player");
//            SinglePlayerWindow.setScene(SinglePlayerScene);
//            SinglePlayerWindow.show();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        try{
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("LevelsSwitcherSinglePlayer.fxml"));
            fxmlLoader.setControllerFactory( c -> new LevelsSwitcherController(snakeDecoration));
            SinglePlayerScene = new Scene(fxmlLoader.load());
            LevelsSwitcher = new Stage();
            LevelsSwitcher.setTitle("Choose level!");
            LevelsSwitcher.setScene(SinglePlayerScene);
            LevelsSwitcher.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GamePanelWindow.hide();
        snakeDecoration.setRunning(false);
    }


    public void addSnake(){
        snakeDecoration.addRandomDecorateSnake();
    }

    public void onDobleGamePicClick(){
        System.out.println("Wybrano grę dwu osobową ;o");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Images on buttons (single and multiplayer)
        ImageView imageSP = new ImageView(Objects.requireNonNull(getClass().getResource("img/impSP.png")).toExternalForm());
        imageSP.setFitHeight(80);
        imageSP.setFitWidth(80);
        buttonSP.setGraphic(imageSP);
        ImageView imageMP = new ImageView(Objects.requireNonNull(getClass().getResource("img/imgMP.png")).toExternalForm());
        imageMP.setFitHeight(80);
        imageMP.setFitWidth(80);
        buttonMP.setGraphic(imageMP);
        System.out.println("Inited SnakeDecotation");
        snakeDecoration = new SnakeDecoration(paneSnakeGamePanel,25,60);
        snakeDecoration.newSnakeDecoration("Up");
        snakeDecoration.newSnakeDecoration("Right");
        snakeDecoration.newSnakeDecoration("Down");
        snakeDecoration.newSnakeDecoration("Left");
        snakeDecoration.startSnakes();

    }
}
