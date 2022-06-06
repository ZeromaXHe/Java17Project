package com.zerox.chat;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.cutscene.Cutscene;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @Author: ZeromaXHe
 * @Time: 2022/6/6 22:54
 * @Description:
 * @ModifiedBy: ZeromaXHe
 */
public class ChatApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(816);
        settings.setHeight(624);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new ChatFactory());
        spawn("bg");

        runOnce(() -> {
            // 必须在 FXGL 线程中运行
            List<String> lines = getAssetLoader().loadText("chat/chat.txt");
            Cutscene cutscene = new Cutscene(lines);
            getCutsceneService().startCutscene(cutscene);
        }, Duration.ONE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
