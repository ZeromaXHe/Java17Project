package com.zerox.test;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;

import java.util.Map;

/**
 * @Author: ZeromaXHe
 * @Time: 2022/6/6 22:15
 * @Description:
 * @ModifiedBy: ZeromaXHe
 */
public class TestMethodApp extends GameApplication {
    private LocalTimer printTimer;

    public TestMethodApp() {
        System.out.println("构造器==>" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        System.out.println("TestMethodApp.main ===>" + Thread.currentThread().getName());
        launch(args);
    }

    /**
     * 初始化游戏的设置，比如 宽高、版本、图标、菜单等
     *
     * @param settings
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setMainMenuEnabled(true);
        System.out.println("TestMethodeApp.initSettings ===>" + Thread.currentThread().getName());
    }

    /**
     * 游戏的预处理
     */
    @Override
    protected void onPreInit() {
        System.out.println("TestMethodeApp.onPreInit ===>" + Thread.currentThread().getName());
    }

    /**
     * 设置输入，键盘，鼠标
     */
    @Override
    protected void initInput() {
        System.out.println("TestMethodeApp.initInput ===>" + Thread.currentThread().getName());
    }

    /**
     * 设置一些游戏的变量，可以方便的在其他类进行访问
     *
     * @param vars map containing CVars (global variables)
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        System.out.println("TestMethodeApp.initGameVars ===>" + Thread.currentThread().getName());
    }

    /**
     * 初始化游戏
     */
    @Override
    protected void initGame() {
        System.out.println("TestMethodeApp.initGame ===>" + Thread.currentThread().getName());
    }

    /**
     * 初始化物理设置，比如碰撞检测等
     */
    @Override
    protected void initPhysics() {
        System.out.println("TestMethodeApp.initPhysics ===>" + Thread.currentThread().getName());
    }

    /**
     * 初始化一些界面上的组件
     */
    @Override
    protected void initUI() {
        System.out.println("TestMethodeApp.initUI ===>" + Thread.currentThread().getName());
        printTimer = FXGL.newLocalTimer();
    }

    /**
     * 游戏开始后，每一帧都会调用该方法
     *
     * @param tpf time per frame
     */
    @Override
    protected void onUpdate(double tpf) {
        if (printTimer.elapsed(Duration.seconds(1))) {
            System.out.println("TestMethodApp.onUpdate ===>" + Thread.currentThread().getName());
            printTimer.capture();
        }
    }
}
