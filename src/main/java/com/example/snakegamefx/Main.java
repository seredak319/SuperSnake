package com.example.snakegamefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main extends Application {

    private final Container container = new Container();
    private final ExecutorService exec = Executors.newCachedThreadPool();

    @Override
    public void stop() throws InterruptedException {
        exec.shutdown();
        if (exec.awaitTermination(2, TimeUnit.SECONDS)) {
            System.exit(11);
        } else {
            exec.shutdownNow();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GamePanel.fxml"));
        fxmlLoader.setControllerFactory( c -> new GamePanel(container));
        Scene gamePanelScene = new Scene(fxmlLoader.load());
        stage.setTitle("Super Snake!");
        stage.setScene(gamePanelScene);
        stage.setResizable(false);
        stage.setOpacity(0.95);
        stage.show();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/icon.png")).toString()));

        container.setGamePanelWindow(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}