package com.example.draw;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class TimeLabel extends Label {

    // 构造函数
    public TimeLabel() {
        // 设置标签的字体和颜色
        this.setFont(new Font("Arial", 20));
        this.setStyle("-fx-text-fill: black;");

        // 设置标签的大小和位置
        this.setPrefSize(100, 50);
        this.setLayoutX(250);
        this.setLayoutY(250);

        // 创建一个时间格式化器，用于显示24小时制的时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // 创建一个时间线动画，每秒更新一次标签的文本
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // 获取当前时间并格式化为字符串
                LocalTime currentTime = LocalTime.now();
                String timeString = formatter.format(currentTime);

                // 设置标签的文本为当前时间字符串
                setText(timeString);
            }
        }), new KeyFrame(Duration.seconds(1)));

        // 设置时间线动画为无限循环
        timeline.setCycleCount(Animation.INDEFINITE);

        // 启动时间线动画
        timeline.play();
    }
}
