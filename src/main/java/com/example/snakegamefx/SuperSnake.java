package com.example.snakegamefx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class SuperSnake extends Application {


    public static Stage GamePanelWindow;
    public static Scene GamePanelScene;
    private static GamePanel gamePanel;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SuperSnake.class.getResource("GamePanel.fxml"));
        GamePanelScene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(GamePanelScene);
        stage.setResizable(false);
        stage.setOpacity(0.95);
        stage.show();
        GamePanelWindow = stage;
       // SuperSnake.GamePanelWindow.hide();

        GamePanelWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                GamePanel.killDecoratingSnakes();
            }
        });

    }


    public static void main(String[] args) {
        launch();

    }
}