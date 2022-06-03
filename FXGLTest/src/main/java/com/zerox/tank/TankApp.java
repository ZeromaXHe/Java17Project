package com.zerox.tank;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @Author: ZeromaXHe
 * @Time: 2022/6/4 3:18
 * @Description:
 * @ModifiedBy: ZeromaXHe
 */
public class TankApp extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Tank");
        gameSettings.setVersion("0.1");
        // FXGL 路径是直接读取 assets/textures 下
        // assets 是所有资源的父目录
        // textures 是专门用于存储图片的目录
        gameSettings.setAppIcon("tank.png");
    }

    @Override
    protected void initGame() {
        Canvas canvas = new Canvas(100, 100);
        GraphicsContext g2d = canvas.getGraphicsContext2D();
        g2d.setFill(Color.web("#ffec03"));
        g2d.fillRect(0, 0, 80, 30);
        g2d.fillRect(0, 70, 80, 30);
        g2d.setFill(Color.web("#cebc17"));
        g2d.fillRect(15, 30, 50, 40);
        g2d.setFill(Color.web("#f9ee8a"));
        g2d.fillRect(40, 40, 60, 20);
        Entity tankEntity = FXGL.entityBuilder()
                .view(canvas)
                .build();
        FXGL.getGameWorld().addEntity(tankEntity);
    }
}
