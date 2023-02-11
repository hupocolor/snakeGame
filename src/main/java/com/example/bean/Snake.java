package com.example.bean;

public class Snake {
    Body head;
    Body tail;
    public Snake(int x, int y){
       head = new Body(x,y);
       tail = new Body(x,y+1);
       head.next = tail;
       tail.pre = head;
       tail.next = null;
    }

    //蛇的移动方法
    public void moveHead(Ahead ahead){ //往某一个方向上运动
        Body newBody;
        switch (ahead){
            case LEFT:newBody = new Body(head.x-1,head.y);break;
            case RIGHT:newBody = new Body(head.x+1,head.y);break;
            case UP:newBody = new Body(head.x,head.y-1);break;
            default: newBody = new Body(head.x,head.y+1);
        }
        newBody.next = head;
        head.pre = newBody;
        head = head.pre;
        tail = tail.pre;
        tail.next = null;
    }

    //给一个食物让蛇吃掉
    public void eat(Food food){
        Body body = new Body(food.x,food.y);
        body.next = head;
        head.pre = body;
        head = head.pre;
    }
    public enum Ahead{
        UP,DOWN,LEFT,RIGHT
    }

    public Body getHead() {
        return head;
    }




    //蛇自身判断是否吃到自己
    public boolean isEatSelf(){
        Body body = head.next;
        while (body!=null){
            if(body.equals(head))
                return true;
            body = body.next;
        }
        return false;
    }
}

