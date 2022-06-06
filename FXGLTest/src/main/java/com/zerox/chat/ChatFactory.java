package com.zerox.chat;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

/**
 * @Author: ZeromaXHe
 * @Time: 2022/6/6 22:54
 * @Description:
 * @ModifiedBy: ZeromaXHe
 */
public class ChatFactory implements EntityFactory {
    @Spawns("bg")
    public Entity newBg(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(FXGL.texture("chat/bg.png"))
                .build();
    }
}
