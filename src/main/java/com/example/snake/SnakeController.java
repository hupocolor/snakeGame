package com.example.snake;

import com.example.bean.Home;
import com.example.bean.Snake;
import com.example.draw.DrawSnake;
import com.example.draw.TimeLabel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SnakeController implements Initializable{
    public Label messageText;
    public Pane root;
    @FXML
    private Canvas gameCanvas;
    @FXML
    private TextField nowScoreText;
    @FXML
    private TextField maxScoreText;
    @FXML
    private ChoiceBox selectLevel;

    @FXML
    private HBox hBox;

    DrawSnake drawSnake;
    int level = 1;
    int nowLevel = level;
    int score = 0;
    static int maxscore = 0;
    static String maxScoreFile = "src/main/resources/score.txt";

    Snake.Ahead ahead = Snake.Ahead.UP;


    boolean isInGame = false;

    @FXML
    protected void beginButtonAc(){
        if(!isInGame){
            nowLevel = level;
            nowScoreText.setText("0");
            isInGame = true;
            messageText.setText("正在游戏中");
            Home.NewSnake();
            ahead = Snake.Ahead.UP;
            drawSnake = new DrawSnake(Home.getSnake(), Home.getFood(), gameCanvas);
            drawSnake.draw();
        }else {
            messageText.setText("已经在游戏中!请勿重试");
            System.out.println("已经正在游戏中");
        }
    }

    @FXML
    protected void init() { //用于初始化页面
        hBox.getChildren().add(new TimeLabel());
        maxScoreText.setText(String.valueOf(maxscore));
        selectLevel.getItems().addAll("简单","普通","困难","地狱","魔鬼","不可能");
        selectLevel.setValue("简单");
        selectLevel.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                level = t1.intValue()+1;
                System.out.println("level= "+level);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readMaxScore();
        init();
    }

    public void snakeMove() throws InterruptedException {
        if(!isInGame) return; //游戏如果未开始就不监听
        Thread drawThead = new DrawThead();
        drawThead.setDaemon(true); //设置为守护线程主线程退出时自动退出
        drawThead.start();
    }

    public void changeSnakeDirect(KeyEvent keyEvent) {
        if(!isInGame) return; //只有在游戏中才会监听键盘输入
        if(keyEvent.getCode() == KeyCode.A){
            ahead = Snake.Ahead.LEFT;
            System.out.println("向左");
        }
        else if(keyEvent.getCode() == KeyCode.W) {
            ahead = Snake.Ahead.UP;
            System.out.println("向上");
        }
        else if(keyEvent.getCode() == KeyCode.S) {
            ahead = Snake.Ahead.DOWN;
            System.out.println("向下");
        }
        else if(keyEvent.getCode() == KeyCode.D) {
            ahead = Snake.Ahead.RIGHT;
            System.out.println("向右");
//            drawSnake.clear();
//            drawSnake.getSnake().moveHead(Snake.Ahead.RIGHT);
        }

    }

    public boolean eatAndJudge(){
        if(Home.getSnake().getHead().getX()==Home.getFood().getX() && Home.getSnake().getHead().getY() == Home.getFood().getY()) {
            Home.getSnake().eat(Home.getFood());
//            Home.getSnake().eat(Home.getFood());
//            Home.getSnake().eat(Home.getFood());
//            Home.getSnake().eat(Home.getFood());
//            Home.getSnake().eat(Home.getFood());
            drawSnake.setFood(Home.getNewFood());
            score+=nowLevel;
            nowScoreText.setText(String.valueOf(score));
            System.out.println(score);
        }
        else if(Home.getSnake().getHead().getX() >= 60 || Home.getSnake().getHead().getY() > 30
                || Home.getSnake().getHead().getX() < 0 || Home.getSnake().getHead().getY() < 0
                || Home.getSnake().isEatSelf())
        {
            System.out.println("游戏失败");
            if(score>maxscore){
                maxscore = score;
            }
            maxScoreText.setText(String.valueOf(maxscore));
            drawSnake.clear();
            System.out.println("最大分数为 "+maxscore);
            isInGame = false; //将是否正在游戏设置为退出游戏
            score = 0;
            return false;
        }
        return true;
    }


    class DrawThead extends Thread{
        public void run(){
            while (isInGame){ //当退出游戏时不再调用线程
                try {
                    Thread.sleep(800/nowLevel);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                drawSnake.clear();
                Home.getSnake().moveHead(ahead);
                if(eatAndJudge()) { //调用判断是否吃到食物,是否撞到自己
                    drawSnake.draw();
                }
            }
        }
    }

    public static void saveMaxScore(){
        OutputStream writeScore = null;
        try {
            writeScore = new FileOutputStream(maxScoreFile);
                writeScore.write(String.valueOf(maxscore).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                writeScore.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void readMaxScore()  {
        StringBuffer sb = new StringBuffer();
        int data;
        InputStream readScore = null;
        try {
            readScore = new FileInputStream(maxScoreFile);
            while((data = readScore.read())!=-1){
                sb.append((char) data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                readScore.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        maxscore = Integer.parseInt(sb.toString());
    }
}
