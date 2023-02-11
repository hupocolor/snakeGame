package com.example.bean;

import java.util.Random;

public class Food {
    int x;
    int y;
    int size = 10;

    public Food(int height,int width) {
        y = new Random().nextInt(height/10);
        x = new Random().nextInt(width/10);
    }


    public int getX() {
        return this.x;
    }


    public int getY() {
        return this.y;
    }

    public int getSize(){
        return this.size;
    }
}
