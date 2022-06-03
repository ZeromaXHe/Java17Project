package com.zerox.hello;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

/**
 * @Author: ZeromaXHe
 * @Time: 2022/6/4 3:08
 * @Description:
 * @ModifiedBy: ZeromaXHe
 */
public class HelloWorldApp extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("HelloWorld");
        gameSettings.setVersion("0.1");
        gameSettings.setWidth(600);
        gameSettings.setHeight(400);
    }
}
