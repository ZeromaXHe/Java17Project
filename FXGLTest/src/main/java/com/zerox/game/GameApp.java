package com.zerox.game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.IDComponent;
import com.almasb.fxgl.entity.components.TypeComponent;
import javafx.scene.paint.Color;

import java.util.Optional;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;

/**
 * @Author: ZeromaXHe
 * @Time: 2022/6/6 23:25
 * @Description:
 * @ModifiedBy: ZeromaXHe
 */
public class GameApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {

    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameEntityFactory());
        Entity e1 = spawn("rect",
                new SpawnData(0, 0)
                        .put("w", 100)
                        .put("h", 100)
                        .put("color", Color.RED)
        );
        Entity e2 = spawn("rect",
                new SpawnData(100, 100)
                        .put("w", 100)
                        .put("h", 100)
                        .put("color", Color.BLUE)
        );
        // 计算的是 entity 坐标的距离
        System.out.println("distance: " + e1.distance(e2));
        // 计算的是实体的 bBox 之间的距离，如果有交集，那么距离就是 0
        System.out.println("distanceBBox: " + e1.distanceBBox(e2));

        // Entity 只有被添加到游戏世界当中的时候，才是活跃的
        Entity e3 = getGameWorld().create("rect",
                new SpawnData(220, 230)
                        .put("w", 50)
                        .put("h", 50)
                        .put("color", Color.GREEN)
        );

        e3.setOnActive(() -> System.out.println("e3 活跃"));
        e3.setOnNotActive(() -> System.out.println("e3 不活跃"));

        System.out.println("e3 创建 active: " + e3.isActive());
        getGameWorld().addEntity(e3);
        System.out.println("e3 添加到世界 active: " + e3.isActive());
        // 等效下面写法
//        getGameWorld().removeEntity(e3);
        e3.removeFromWorld();
        System.out.println("e3 移除后 active: " + e3.isActive());

        e1.getComponents().forEach(System.out::println);
        /// 四大核心组件
//        // 指定类型
//        e1.getTypeComponent();
//        e1.getComponent(TypeComponent.class);
//        // 指定实体大小
//        e1.getBoundingBoxComponent();
//        // 指定实体的外观
//        e1.getViewComponent();
//        // 指定实体的位置
//        e1.getTransformComponent();

        boolean b = e1.hasComponent(TypeComponent.class);
        System.out.println(b ? "有 Type 组件" : "没有 Type 组件");

        e1.addComponent(new HealthIntComponent(1));
        // 已经有的组件，就不要重复添加，会有 WARN 信息
        e1.addComponent(new TypeComponent());

        boolean removeResult = e1.removeComponent(HealthIntComponent.class);
        System.out.println("removeResult = " + removeResult);
        // 无法核心组件，会有 WARN 信息
        boolean removeResult2 = e1.removeComponent(TypeComponent.class);
        System.out.println("removeResult2 = " + removeResult2);

        Optional<IDComponent> idComponentOptional = e1.getComponentOptional(IDComponent.class);
        if (idComponentOptional.isPresent()) {
            System.out.println("存在 id 组件");
        } else {
            System.out.println("不存在 id 组件");
        }

        e1.getComponents().forEach(System.out::println);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
