package com.zerox.test;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.shape.Rectangle;

/**
 * @Author: ZeromaXHe
 * @Time: 2022/6/6 22:31
 * @Description:
 * @ModifiedBy: ZeromaXHe
 */
public class TestEntityFactory implements EntityFactory {
    @Spawns("rect,square")
    public Entity newRect(SpawnData data) {
        int w = data.get("w");
        return FXGL.entityBuilder(data)
                .type(TestGameType.RECT)
                .view(new Rectangle(w, data.<Integer>get("h"), data.get("color")))
                .build();
    }
}
