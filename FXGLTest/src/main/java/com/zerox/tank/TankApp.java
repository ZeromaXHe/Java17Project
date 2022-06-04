package com.zerox.tank;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.LocalTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Map;

/**
 * @Author: ZeromaXHe
 * @Time: 2022/6/4 3:18
 * @Description:
 * @ModifiedBy: ZeromaXHe
 */
public class TankApp extends GameApplication {
    private Entity tankEntity;

    private boolean isMoving;

    private LocalTimer shootTimer;

    private Dir dir = Dir.RIGHT;
    private final Duration shootDelay = Duration.seconds(0.25);

    enum Dir {
        UP(new Point2D(0, -1)),

        DOWN(new Point2D(0, 1)),

        LEFT(new Point2D(-1, 0)),

        RIGHT(new Point2D(1, 0));

        private Point2D p;

        Dir(Point2D p) {
            this.p = p;
        }

        public Point2D getP() {
            return p;
        }
    }

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
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
    }

    @Override
    protected void initGame() {
        shootTimer = FXGL.newLocalTimer();
        Canvas canvas = new Canvas(100, 100);
        GraphicsContext g2d = canvas.getGraphicsContext2D();
        g2d.setFill(Color.web("#ffec03"));
        g2d.fillRect(0, 0, 80, 30);
        g2d.fillRect(0, 70, 80, 30);
        g2d.setFill(Color.web("#cebc17"));
        g2d.fillRect(15, 30, 50, 40);
        g2d.setFill(Color.web("#f9ee8a"));
        g2d.fillRect(40, 40, 60, 20);
        // IDEA ctrl + alt + f 快速提取成员变量
        tankEntity = FXGL.entityBuilder()
                // view 决定的是游戏实体的外观
                .view(canvas)
                .bbox(BoundingShape.box(100, 100))
                .build();
        tankEntity.setRotationOrigin(new Point2D(50, 50));
        FXGL.getGameWorld().addEntity(tankEntity);

        createEnemy();
    }

    private void createEnemy() {
        FXGL.entityBuilder()
                .type(GameType.ENEMY)
                .at(FXGLMath.random(60, 740), FXGLMath.random(60, 540))
                .viewWithBBox(new Rectangle(60, 60, Color.BLUE))
                // 等于 .collidable()
//                .with(new CollidableComponent())
                .collidable()
                .buildAndAttach();
    }

    @Override
    protected void initUI() {
        Text text = FXGL.getUIFactoryService()
                .newText(FXGL.getip("score").asString("score:%d"));
        text.setLayoutX(30);
        text.setLayoutY(30);
        FXGL.addUINode(text);
//        Text text = FXGL.addVarText("score", 30, 30);
        text.setFill(Color.BLUE);
        text.fontProperty().unbind();
        text.setFont(Font.font(35));
    }

    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Move up") {
            @Override
            protected void onAction() {
                if (isMoving) {
                    return;
                }
                isMoving = true;
                dir = Dir.UP;
                tankEntity.translateY(-5);
                tankEntity.setRotation(270);
            }
        }, KeyCode.UP);
        FXGL.getInput().addAction(new UserAction("Move down") {
            @Override
            protected void onAction() {
                if (isMoving) {
                    return;
                }
                isMoving = true;
                dir = Dir.DOWN;
                tankEntity.translateY(5);
                tankEntity.setRotation(90);
            }
        }, KeyCode.DOWN);
        FXGL.getInput().addAction(new UserAction("Move left") {
            @Override
            protected void onAction() {
                if (isMoving) {
                    return;
                }
                isMoving = true;
                dir = Dir.LEFT;
                tankEntity.translateX(-5);
                tankEntity.setRotation(180);
            }
        }, KeyCode.LEFT);
        FXGL.getInput().addAction(new UserAction("Move right") {
            @Override
            protected void onAction() {
                if (isMoving) {
                    return;
                }
                isMoving = true;
                dir = Dir.RIGHT;
                tankEntity.translateX(5);
                tankEntity.setRotation(0);
            }
        }, KeyCode.RIGHT);

        FXGL.getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onAction() {
                // 判断发射的时间间隔，是否大于等于 0.25 秒（可以发射）
                if (!shootTimer.elapsed(shootDelay)) {
                    return;
                }
                shootTimer.capture();
                // 子弹实体
                FXGL.entityBuilder()
                        .type(GameType.BULLET)
                        .at(tankEntity.getCenter().getX() + dir.getP().getX() * 50 - 10,
                                tankEntity.getCenter().getY() + dir.getP().getY() * 50 - 10)
                        .viewWithBBox(new Rectangle(20, 20))
                        // 发射物组件
                        .with(new ProjectileComponent(dir.getP(), 600))
                        // 屏幕外移除组件
                        .with(new OffscreenCleanComponent())
                        .collidable()
                        .buildAndAttach();
            }
        }, KeyCode.SPACE);
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.BULLET, GameType.ENEMY) {
            @Override
            protected void onCollisionBegin(Entity bullet, Entity enemy) {
//                int score = FXGL.geti("score") + 10;
//                FXGL.set("score", score);
                FXGL.inc("score", 10);

                bullet.removeFromWorld();
                Point2D center = enemy.getCenter();
                enemy.removeFromWorld();

                Circle circle = new Circle(10, Color.RED);
                Duration duration = Duration.seconds(.35);
                // 创建一个爆炸实体
                Entity boom = FXGL.entityBuilder()
                        .at(center)
                        .view(circle)
                        // 超过指定时间将实体移除的组件
                        .with(new ExpireCleanComponent(duration))
                        .buildAndAttach();

                ScaleTransition st = new ScaleTransition(duration, circle);
                st.setToX(10);
                st.setToY(10);
                FadeTransition ft = new FadeTransition(duration, circle);
                ft.setToValue(0);
                ParallelTransition pt = new ParallelTransition(st, ft);
                pt.play();

//                pt.setOnFinished(actionEvent -> boom.removeFromWorld());
            }
        });
    }

    @Override
    protected void onUpdate(double tpf) {
        isMoving = false;
        System.out.println(FXGL.getGameWorld().getEntities().size());
    }
}
