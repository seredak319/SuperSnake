package com.example.snakegamefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {

    public static Stage GamePanelWindow;
    public static Scene GamePanelScene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GamePanel.fxml"));
        GamePanelScene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(GamePanelScene);
        stage.setResizable(false);
        stage.setOpacity(0.95);
        stage.show();

        GamePanelWindow = stage;
    }


    public static void main(String[] args) {
        launch();
    }
}