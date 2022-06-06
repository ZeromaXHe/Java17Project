package com.zerox.game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.shape.Rectangle;

/**
 * @Author: ZeromaXHe
 * @Time: 2022/6/6 23:25
 * @Description:
 * @ModifiedBy: ZeromaXHe
 */
public class GameEntityFactory implements EntityFactory {
    @Spawns("rect")
    public Entity newRect(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox(new Rectangle(data.<Integer>get("w"), data.<Integer>get("h"), data.get("color")))
                .build();
    }
}
