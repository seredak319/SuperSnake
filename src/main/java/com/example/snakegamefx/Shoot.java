package com.example.snakegamefx;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;

import static java.lang.Thread.sleep;


public class Shoot {

    private boolean shot = false;
    private int ammo;
    private final int START_VALUE = 500;
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
    private final BadSnake badSnake;
    private final int bodyParts =4;
    private Thread thread;


    Shoot(int size, Pane paneShoot, Pane paneSpawn, Label bulletsAmount, BadSnake badSnake){
        this.size = size;
        this.paneShoot = paneShoot;
        this.paneSpawn = paneSpawn;
        this.bulletsAmount = bulletsAmount;
        this.badSnake = badSnake;
        ammo = START_VALUE;
    }

    private void initShooting(){
        System.out.println("InitShooting");
        thread = new Thread(() -> {
            while (isShot()) {
                moveShoot(direction);
                try {
                    sleep(50);
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
            rectangle.setFill(Color.YELLOWGREEN);
            rectangle.setHeight(size);
            rectangle.setWidth(size);
            paneShoot.getChildren().add(rectangle);
        }));
    }

    private void checkCollisionOfShoot(){
        if(shootX < 0 || shootX > 675 || shootY < 0 || shootY > 550){
            System.out.println("Uderzyło w scianę");
            killShooting();
        }

        for(int j=0; j<badSnake.getHowManySnakes(); j++)
            switch (badSnake.getDirection(j)){
                case "Right" -> {
                    if (shootY == badSnake.getStartY(j))
                        if (shootX <= badSnake.getX(j, bodyParts - 1) && shootX >= badSnake.getX(j, 0)) {
                            System.out.println("Trafiono! [RIGHT]");
                            badSnake.killSingleBadSnake(j);
                            killShooting();
                        }
                }
                case "Left" -> {
                    if (shootY == badSnake.getStartY(j))
                        if (shootX >= badSnake.getX(j, bodyParts - 1) && shootX <= badSnake.getX(j, 0)) {
                            System.out.println("Trafiono! [LEFT]");
                            badSnake.killSingleBadSnake(j);
                            killShooting();
                        }
                }
                case "Up" -> {
                    if (shootX == badSnake.getStartX(j))
                        if (shootY <= badSnake.getY(j,bodyParts-1) && shootY >= badSnake.getY(j,0)){
                            System.out.println("Trafiono! [Up]");
                            badSnake.killSingleBadSnake(j);
                            killShooting();
                        }
                }
                case "Down" -> {
                    if (shootX == badSnake.getStartX(j))
                        if (shootY >= badSnake.getY(j,bodyParts-1) && shootY <= badSnake.getY(j,0)){
                            System.out.println("Trafiono! [Down]");
                            badSnake.killSingleBadSnake(j);
                            killShooting();
                        }
                }
            }
    }

    public void spawnAmmo(){
        spawnedAmmo = true;
        Random random = new Random();
        rectangleAmmo = new Rectangle();
        rectangleAmmo.setWidth(size);
        rectangleAmmo.setHeight(size);
        shootSpawnX = random.nextInt(27) * size;
        shootSpawnY = random.nextInt(22) * size;
        rectangleAmmo.setX(shootSpawnX);
        rectangleAmmo.setY(shootSpawnY);
        rectangleAmmo.setFill(Color.LIGHTGOLDENRODYELLOW);
        Platform.runLater((() -> paneSpawn.getChildren().add(rectangleAmmo)));
    }

    public void addAmmo(){
        spawnedAmmo = false;
        Platform.runLater((() -> {
            paneSpawn.getChildren().clear();
            bulletsAmount.setText(Integer.toString(++ammo));
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

}
//
//
//    private void initShooting(){
//        String direction = currentDirection;
//        timerShoot = new Timer();
//        timerTaskShoot = new TimerTask() {
//            @Override
//            public void run() {
//                moveShot(direction);
//            }
//        };
//        timerShoot.schedule(timerTaskShoot,0,SHOOT_DELAY);
//    }
//
//    private void killShooting(){
//        Platform.runLater((() -> {
//            paneShoot.getChildren().clear();
//        }));
//        timerTaskShoot.cancel();
//        timerShoot.cancel();
//
//        shot = false;
//    }
//
//    private void shoot(){
//        if(!checkAmmo())
//            return;
//        shot = true;
//        initShooting();
//        shootX = x[bodyParts-1];
//        shootY = y[bodyParts-1];
//    }
//
//    private boolean checkAmmo(){
//        if(ammo > 0) {
//            ammo--;
//            bulletsAmount.setText(Integer.toString(ammo));
//            return true;
//        }
//        return false;
//    }
//
//
//    private void moveShot(String direction){
//        switch (direction){
//            case "Up" -> {
//                shootY -= size;
//            }
//            case "Down" -> {
//                shootY += size;
//            }
//            case "Left" -> {
//                shootX -= size;
//            }
//            case "Right" -> {
//                shootX += size;
//            }
//        }
//        printShoot();
//        checkCollisionOfShoot();
//    }
//
//
//    private void printShoot(){
//        Platform.runLater((() -> {
//            paneShoot.getChildren().clear();
//            Rectangle rectangle = new Rectangle();
//            rectangle.setX(shootX);
//            rectangle.setY(shootY);
//            rectangle.setFill(Color.YELLOWGREEN);
//            rectangle.setHeight(size);
//            rectangle.setWidth(size);
//            paneShoot.getChildren().add(rectangle);
//        }));
//    }
//
//
//    private void checkCollisionOfShoot(){
//        if(shootX < 0 || shootX > 675 || shootY < 0 || shootY > 550){
//            System.out.println("Uderzyło w scianę");
//            killShooting();
//        }
//    }
//
//    private void SpawnAmmo(){
//        random = new Random();
//        int ammoX = random.nextInt(27) * size;
//        int ammoY = random.nextInt(22) * size;
//
//    }

