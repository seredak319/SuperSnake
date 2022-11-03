package com.example.snakegamefx;

public class BadSnakesMovementAlgorithm {

    private Container container;
    private String currentDirection;

    BadSnakesMovementAlgorithm(Container container){
        this.container = container;
    }

    public String setCurrentDirection(int badSnakeX, int badSnakeY) {

        // XDDD pozdro tu chodzi o to że Boss będzie wybierał punkt bliżej niego spośród głowy i ostatniej częsci ciała naszego węża, 'algorytm' lepiej działa
        int x = container.getSnake().getSnakeX(3) > container.getSnake().getSnakeHeadX() ? container.getSnake().getSnakeX(3) - badSnakeX : container.getSnake().getSnakeHeadX() - badSnakeX;
        int y = container.getSnake().getSnakeY(3) > container.getSnake().getSnakeHeadY() ? container.getSnake().getSnakeY(3) - badSnakeY : container.getSnake().getSnakeHeadY() - badSnakeY;


        if(x >= 0 && y >= 0){
            if( x >= y){
                currentDirection = "Right";
            } else {
                currentDirection = "Down";
            }
        }

        if(x>=0 && y<=0){
            if(x>=Math.abs(y)){
                currentDirection = "Right";
            } else {
                currentDirection = "Up";
            }
        }

        if(x<=0 && y>=0){
            if(Math.abs(x) >= y){
                currentDirection = "Left";
            } else {
                currentDirection = "Down";
            }
        }

        if(x<=0 && y<=0){
            if(Math.abs(x) >= Math.abs(y)){
                currentDirection = "Left"; //
            } else {
                currentDirection = "Up";
            }
        }

        return currentDirection;
    }
}
