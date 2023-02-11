package com.example.bean;

public class Home {
    private static Snake snake;
    private static Food food = new Food(300,600);
    public static Snake getSnake(){
        return snake;
    }
    public static Food getFood(){
        return food;
    }

    //获取一个新的食物,如果食物的位置与蛇的位置冲突,则创建一个新的食物
    public static Food getNewFood(){
        Body node = snake.head;
        while(node!=null){
            if(node.x == food.getX()&&node.y==food.getY()){
                node = snake.head;
                food = new Food(300,600);
            }
            node = node.next;
        }
        return food;
    }
    public static void NewSnake(){
        snake = new Snake(30,15);
    }
}
