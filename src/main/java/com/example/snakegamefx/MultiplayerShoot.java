package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

import static java.lang.Thread.sleep;

public class MultiplayerShoot {

    private boolean shot = false;
    private int ammo;
    int START_VALUE;
    private final int size;
    private String direction;
    private final Pane paneShoot;
    private int shootX;
    private int shootY;
    private int shootSpawnX;
    private int shootSpawnY;
    private Rectangle rectangleAmmo;
    private final Label bulletsAmount;
    private boolean spawnedAmmo;
    private final Pane paneSpawn;
    private final int bodyParts =4;
    private Thread thread;
    private final Container container;
    private final Color shootColor;
    private final Color spawnColor;
    private final int shootDELAY;
    private int whichPlayer;



    MultiplayerShoot(int size, Pane paneShoot, Pane paneSpawn, Label bulletsAmount,Container container,int START_VALUE,int whichPlayer, int shootDELAY){
        this.size = size;
        this.paneShoot = paneShoot;
        this.paneSpawn = paneSpawn;
        this.bulletsAmount = bulletsAmount;
        this.container = container;
        this.START_VALUE = START_VALUE;
        this.shootDELAY = shootDELAY;
        this.whichPlayer = whichPlayer;
        ammo = START_VALUE;

        if(whichPlayer == 1){
            shootColor = Color.BLUE;
            spawnColor = Color.DODGERBLUE;
        }
        else if(whichPlayer == 2){
            shootColor = Color.DARKRED;
            spawnColor = Color.MEDIUMVIOLETRED;

        } else {
            shootColor = Color.BLACK;
            spawnColor = Color.BLACK;
        }

        Platform.runLater((() -> bulletsAmount.setText(Integer.toString(START_VALUE))));
    }

    private void initShooting(){
        System.out.println("InitShooting");
        thread = new Thread(() -> {
            while (isShot()) {
                moveShoot(direction);
                try {
                    sleep(shootDELAY);
                } catch (InterruptedException e) {
                    System.out.println("Sleep was interrupted!");
                }
            }
            System.out.println("SnakeDecoration: thread exited;");
        });
        thread.start();
    }

    private void killShooting() {
        Platform.runLater((() -> paneShoot.getChildren().clear()));
        shot = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doShoot(int shootX, int shootY, String direction){
        System.out.println("DoShoot");
        if(!checkAmmo())
            return;
        shot = true;
        this.shootX = shootX;
        this.shootY = shootY;
        this.direction = direction;
        initShooting();
    }

    private boolean checkAmmo(){
        System.out.println("CheckAmmo");
        if(ammo > 0) {
            ammo--;
            bulletsAmount.setText(Integer.toString(ammo));
            return true;
        }
        return false;
    }

    private void moveShoot(String direction){
        switch (direction){
            case "Up" -> shootY -= size;
            case "Down" -> shootY += size;
            case "Left" -> shootX -= size;
            case "Right" -> shootX += size;
        }
        printShoot();
        checkCollisionOfShoot();
    }

    private void printShoot(){
        Platform.runLater((() -> {
            paneShoot.getChildren().clear();
            Rectangle rectangle = new Rectangle();
            rectangle.setX(shootX);
            rectangle.setY(shootY);
            rectangle.setFill(shootColor);
            rectangle.setHeight(size);
            rectangle.setWidth(size);
            paneShoot.getChildren().add(rectangle);
        }));
    }

    private void checkCollisionOfShoot() {
        if (shootX < 0 || shootX >= size * container.getColMP() || shootY < 0 || shootY >= size * container.getRowMP()) {
            System.out.println("Uderzyło w scianę");
            killShooting();
            return;
        }

        if (whichPlayer == 1) {
            if (!container.getSnake2().isJustShot()){
                for (int i = 0; i < bodyParts; i++) {
                    if (shootX == container.getSnake2().getSnakeX(i) && shootY == container.getSnake2().getSnakeY(i)) {
                        container.getSnake2().snakeShot();
                        killShooting();
                    }
                }
            }
        }

        if(whichPlayer == 2) {
            if (!container.getSnake2().isJustShot()) {
                for (int i = 0; i < bodyParts; i++) {
                    if (shootX == container.getSnake1().getSnakeX(i) && shootY == container.getSnake1().getSnakeY(i)) {
                        container.getSnake1().snakeShot();
                        killShooting();
                    }
                }
            }
        }

    }

    public void spawnAmmo(){
        System.out.println("Spawning ammo");
        spawnedAmmo = true;
        Random random = new Random();
        rectangleAmmo = new Rectangle();
        rectangleAmmo.setWidth(size);
        rectangleAmmo.setHeight(size);
        shootSpawnX = random.nextInt(container.getColMP()) * size;
        shootSpawnY = random.nextInt(container.getRowMP()) * size;
        rectangleAmmo.setX(shootSpawnX);
        rectangleAmmo.setY(shootSpawnY);
        rectangleAmmo.setFill(spawnColor);
        Platform.runLater((() -> paneSpawn.getChildren().add(rectangleAmmo)));
    }

    public void addAmmo(){
        spawnedAmmo = false;
        ammo++;
        Platform.runLater((() -> {
            paneSpawn.getChildren().clear();
            bulletsAmount.setText(Integer.toString(ammo));
        }));
        shootSpawnX = -1;
        shootSpawnY = -1;
    }

    public boolean getSpawnedAmmo(){
        return spawnedAmmo;
    }

    public int getXOfSpawnedAmmo(){
        return shootSpawnX;
    }

    public int getYOfSpawnedAmmo(){
        return shootSpawnY;
    }

    public int getAmmo(){
        return ammo;
    }

    public boolean isShoot(){
        return shot;
    }

    public boolean isShot() {
        return shot;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
