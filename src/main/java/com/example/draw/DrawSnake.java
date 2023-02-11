package com.example.draw;

import com.example.bean.Body;
import com.example.bean.Food;
import com.example.bean.Snake;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawSnake{
    Snake snake;
    Food food;
    Canvas canvas;
    public DrawSnake(Snake snake,Food food,Canvas canvas){
        this.snake = snake;
        this.food = food;
        this.canvas = canvas;
    }

    //遍历整个蛇身并画出蛇
    public void draw(){
        GraphicsContext context;
        Body b = snake.getHead();
        while(b!=null){
            context = canvas.getGraphicsContext2D();
            context.fillRect(b.getX()*10,b.getY()*10,b.getSize(),b.getSize());
            context.setFill(Color.BLUE);
            b = b.next;
        }
        context = canvas.getGraphicsContext2D();
        context.fillRect(food.getX()*10,food.getY()*10,food.getSize(),food.getSize());
        context.setFill(Color.RED);
    }

    //遍历整个蛇身并清空画布
    public void clear(){
        GraphicsContext context;
        Body b = snake.getHead();
        while(b!=null){
            context = canvas.getGraphicsContext2D();
            context.clearRect(b.getX()*10,b.getY()*10,b.getSize(),b.getSize());
            b = b.next;
        }
        context = canvas.getGraphicsContext2D();
        context.clearRect(food.getX()*10,food.getY()*10,food.getSize(),food.getSize());
    }
    public void setFood(Food food){
        this.food = food;
    }

}
