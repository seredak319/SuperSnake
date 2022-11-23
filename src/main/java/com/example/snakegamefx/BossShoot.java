package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static java.lang.Thread.sleep;

public class BossShoot {

    private final Boss boss;
    private final Pane paneBossShoot;
    private int x;
    private int y;
    private String currDirection;
    private Thread threadBossShoot;
    private final int delay = 80;
    private final int size = 25;
    private final Container container;
    private boolean shot;
    private final int tick_val;
    int tick = 1;


    BossShoot(Boss boss, Pane paneBossShoot, Container container,int tick) {
        this.boss = boss;
        this.paneBossShoot = paneBossShoot;
        this.container = container;
        this.tick_val = tick;
    }

    public void start() {
        tick++;
        if (tick % tick_val == 0) {
            currDirection = boss.getCurrentDirection();
            x = boss.getSnakeHeadX();
            y = boss.getSnakeHeadY();
            tick = 1;
            System.out.println("@@ PIF PAF @@");
            shot = true;
        }
        if(shot)
    moveShoot();
    }

    public void bossShoot() {
        System.out.println("bossShoot");
        threadBossShoot = new Thread(() -> {
            while (boss.isRunning()) {
                Platform.runLater((() -> paneBossShoot.getChildren().clear()));
                start();
                try {
                    sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            Platform.runLater((() -> paneBossShoot.getChildren().clear()));
            System.out.println("bossShoot: thread exited;");
        });
        threadBossShoot.start();
    }

    private void moveShoot() {
        switch (currDirection) {
            case "Up", "Down" -> {
                for(int i =0; i<container.getSnake().bodyParts-1; i++)
            if(y == container.getSnake().getSnakeY(i)){
                    doBoom();
                    return;
                }
            }
            case "Left", "Right" -> {
                for(int i =0; i<container.getSnake().bodyParts-1; i++)
            if(x == container.getSnake().getSnakeX(i) ){
                    doBoom();
                    return;
                }
            }
        }
        switch (currDirection) {
            case "Up" -> y -= size;
            case "Down" -> y += size;
            case "Left" -> x -= size;
            case "Right" -> x += size;
        }
        printShoot();
    }



    private void printShoot(){
            Platform.runLater((() -> {
                paneBossShoot.getChildren().clear();
                Rectangle rectangle = new Rectangle();
                rectangle.setX(x);
                rectangle.setY(y);
                rectangle.setFill(Color.LIGHTGOLDENRODYELLOW);
                rectangle.setHeight(size);
                rectangle.setWidth(size);
                paneBossShoot.getChildren().add(rectangle);
            }));
    }

    private void checkCollisionWithSnake(){
        for(int i = 0; i< container.getSnake().bodyParts; i++){
        if(x >= container.getSnake().getSnakeX(i) - size && x <= container.getSnake().getSnakeX(i) + size && y>=container.getSnake().getSnakeY(i)-size && y<= container.getSnake().getSnakeY(i) + size )
            container.getGameFrameControllerSinglePlayer().finishTheGame();
        if(x >= container.getSnake().getSnakeX(i) - size && x <= container.getSnake().getSnakeX(i) + size && y>=container.getSnake().getSnakeY(i)-size && y<= container.getSnake().getSnakeY(i) + size )
            container.getGameFrameControllerSinglePlayer().finishTheGame();
        }
    }

    private void doBoom(){
        shot = false;
        checkCollisionWithSnake();
        Platform.runLater((() -> {
            paneBossShoot.getChildren().clear();

            for(int i =0; i<5; i++){
                if(i==0){
                    Rectangle rectangle = new Rectangle();
                    rectangle.setX(x-size);
                    rectangle.setY(y);
                    rectangle.setFill(Color.ORANGERED);
                    rectangle.setHeight(size);
                    rectangle.setWidth(size);
                    paneBossShoot.getChildren().add(rectangle);
                }
                if(i==1){
                    Rectangle rectangle = new Rectangle();
                    rectangle.setX(x);
                    rectangle.setY(y);
                    rectangle.setFill(Color.ORANGERED);
                    rectangle.setHeight(size);
                    rectangle.setWidth(size);
                    paneBossShoot.getChildren().add(rectangle);
                }
                if(i==2){
                    Rectangle rectangle = new Rectangle();
                    rectangle.setX(x+size);
                    rectangle.setY(y);
                    rectangle.setFill(Color.ORANGERED);
                    rectangle.setHeight(size);
                    rectangle.setWidth(size);
                    paneBossShoot.getChildren().add(rectangle);
                }
                if(i==3){
                    Rectangle rectangle = new Rectangle();
                    rectangle.setX(x);
                    rectangle.setY(y-size);
                    rectangle.setFill(Color.ORANGERED);
                    rectangle.setHeight(size);
                    rectangle.setWidth(size);
                    paneBossShoot.getChildren().add(rectangle);
                }
                if(i==4){
                    Rectangle rectangle = new Rectangle();
                    rectangle.setX(x);
                    rectangle.setY(y+size);
                    rectangle.setFill(Color.ORANGERED);
                    rectangle.setHeight(size);
                    rectangle.setWidth(size);
                    paneBossShoot.getChildren().add(rectangle);
                }
            }
        }));
    }
}
