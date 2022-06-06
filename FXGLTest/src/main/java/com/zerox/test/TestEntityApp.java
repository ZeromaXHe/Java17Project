package com.zerox.test;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.collection.PropertyMap;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.paint.Color;

import java.io.Serializable;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @Author: ZeromaXHe
 * @Time: 2022/6/6 22:29
 * @Description:
 * @ModifiedBy: ZeromaXHe
 */
public class TestEntityApp extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {

    }

    @Override
    protected void initGame() {
//        Entity entity = FXGL.entityBuilder().build();
//        FXGL.getGameWorld().addEntity(entity);

//        FXGL.entityBuilder(new SpawnData(100, 50)).buildAndAttach();

        getGameWorld().addEntityFactory(new TestEntityFactory());
//        getGameWorld().spawn("rect", new SpawnData(50, 80));
//        // spawn 创建实体并且添加到游戏世界当中
//        spawn("rect", new SpawnData(80, 180));
//        // create 方法只创建实体，不会自动添加到游戏世界当中
//        Entity entity = getGameWorld().create("rect", new SpawnData(200, 200));
//        getGameWorld().addEntity(entity);
//        spawn("square", new SpawnData(0, 10));

        Entity entity = spawn("square",
                new SpawnData(50, 50)
                        .put("w", 60)
                        .put("h", 60)
                        .put("color", Color.YELLOW)
        );

        Color color = entity.getObject("color");
        System.out.println("color = " + color);
        int w = entity.getInt("w");
        System.out.println("w = " + w);

        System.out.println("---------------------------");
        PropertyMap properties = entity.getProperties();
        properties.keys().forEach(k -> System.out.println(k + " = " + properties.getValue(k)));

        System.out.println("---------------------------");
        Object type = properties.getValue("type");
        System.out.println("type = " + type);
        Serializable type1 = entity.getType();
        System.out.println("type1 = " + type1);
        System.out.println(type == type1);
        System.out.println(type.equals(type1));
    }
}
