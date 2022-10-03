package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    public static Stage SinglePlayerWindow;
    public static Scene SinglePlayerScene;
    public static SnakeDecoration snakeDecoration;

    public FXMLLoader fxmlLoader;
    private GameFrameControllerSinglePlayer gameFrameControllerSinglePlayer;



    public void onSingleGamePicClick(ActionEvent event) throws IOException {

        System.out.println("Wybrano grę jedeno osobową ;)");
        try{

            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("GameFrameSinglePlayer.fxml"));
            //Parent root = fxmlLoader.load();

            //gameFrameControllerSinglePlayer = fxmlLoader.getController();
            //gameFrameControllerSinglePlayer.setSnakeDecoration(snakeDecoration);

            SinglePlayerScene = new Scene(fxmlLoader.load());
            SinglePlayerWindow = new Stage();
            SinglePlayerWindow.setTitle("Single Player");
            SinglePlayerWindow.setScene(SinglePlayerScene);
            SinglePlayerWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GamePanelWindow.hide();
        snakeDecoration.setRunning(false);



        SinglePlayerWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
              //  SuperSnake.kill = true;
                System.out.println("Shot down singlegame");
                //GameFrameControllerSinglePlayer.kill();
                //GamePanel.killDecoratingSnakes();
//                BadSnake.killDecSnakes();
//                BadSnake.killSnakeDecoration();
                Platform.exit();
                //System.exit(2);
            }
        });
    }

    public static void killDecoratingSnakes(){
        System.out.println("killing decorating snakes");
        snakeDecoration.killSnakeDecoration();
//        BadSnake.killSnakeDecoration();
//        BadSnake.killDecSnakes();
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
        snakeDecoration.newSnakeDecoration("Up",0);
        snakeDecoration.newSnakeDecoration("Right",0);
        snakeDecoration.newSnakeDecoration("Down",0);
        snakeDecoration.newSnakeDecoration("Left",0);
        snakeDecoration.startSnakes();

       // gameFrameControllerSinglePlayer = new GameFrameControllerSinglePlayer(snakeDecoration);
    }








    //getters and setters

//    public Window getGamePanelWindow() {
//        return gamePanelWindow;
//    }


}
