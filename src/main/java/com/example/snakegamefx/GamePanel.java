package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GamePanel implements Initializable {

    @FXML
    private ImageView panelSingleGamePic;
    @FXML
    private Button buttonSP;
    @FXML
    private Button buttonMP;
    public static Stage SinglePlayerWindow;
    public static Scene SinglePlayerScene;


    public void onSingleGamePicClick(ActionEvent event) throws IOException {
        System.out.println("Wybrano grę jedeno osobową ;)");
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("GameFrameSinglePlayer.fxml"));
            SinglePlayerScene = new Scene(fxmlLoader.load());
            SinglePlayerWindow = new Stage();
            SinglePlayerWindow.setTitle("Single Player");
            SinglePlayerWindow.setScene(SinglePlayerScene);
            SinglePlayerWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HelloApplication.GamePanelWindow.hide();


        SinglePlayerWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                GameFrameControllerSinglePlayer.timerTask.cancel();
                GameFrameControllerSinglePlayer.timer.cancel();
                Platform.exit();
            }
        });
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

    }

    //getters and setters

//    public Window getGamePanelWindow() {
//        return gamePanelWindow;
//    }


}
