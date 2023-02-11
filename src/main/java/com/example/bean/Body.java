package com.example.bean;

public class Body{
    int x;
    int y;
    int size = 10;
    public Body next;
    public Body pre;

    public Body(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }


    public int getY() {
        return  this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getSize(){
        return this.size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Body body = (Body) o;
        return x == body.x && y == body.y;
    }

}
