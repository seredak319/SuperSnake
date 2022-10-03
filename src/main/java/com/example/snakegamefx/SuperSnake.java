package com.example.snakegamefx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class SuperSnake extends Application {


    public static Stage GamePanelWindow;
    public static Scene GamePanelScene;
    private static GamePanel gamePanel;
    public static boolean kill = false;

    private final ExecutorService exec = Executors.newCachedThreadPool();

    @Override
    public void stop() throws InterruptedException {
        System.out.println("Stop called: try to let background threads complete...");
        exec.shutdown();
        if (exec.awaitTermination(2, TimeUnit.SECONDS)) {
            System.out.println("Background threads exited");
            System.exit(11);
        } else {
            System.out.println("Background threads did not exit, trying to force termination (via interruption)");
            exec.shutdownNow();
        }
    }


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
                //GamePanel.killDecoratingSnakes();
                kill = true;
            }
        });

    }


    public static void main(String[] args) {
        launch();

    }
}