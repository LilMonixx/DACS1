package com.mygdx.game.Logic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Entity.Entity;
import com.mygdx.game.Entity.Player;
import com.mygdx.game.Entity.Enemy;

import java.util.List;

public class Moving {

    public static void handlePlayerMovement(Player player) {
        Vector2 movement = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) movement.y += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) movement.y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) movement.x -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) movement.x += 1;

        if (!movement.isZero()) {
            float delta = Gdx.graphics.getDeltaTime();
            movement.nor().scl(player.getMoveSpeed() * delta);
            player.setPosition(player.getPosition().add(movement));
        }
    }

    public static void handleEnemyAI(List<Enemy> enemies, Player player) {
        float delta = Gdx.graphics.getDeltaTime();

        for (Enemy enemy : enemies) {
            if (enemy.isDead()) continue;

            float distanceToPlayer = enemy.getPosition().dst(player.getPosition());

            // Nếu player trong tầm phát hiện
            if (distanceToPlayer <= enemy.getDetectionRange()) {
                enemy.setHasSeenPlayer(true); // đánh dấu đã từng thấy player
                enemy.setIdleTimer(0); // reset thời gian đứng yên

                // Đuổi theo player
                Vector2 direction = new Vector2(player.getPosition()).sub(enemy.getPosition()).nor();
                Vector2 movement = direction.scl(enemy.getMoveSpeed() * delta);

                Vector2 newPos = new Vector2(enemy.getPosition()).add(movement);
                if (newPos.dst(enemy.getOrigin()) <= enemy.getMaxRange()) {
                    enemy.setPosition(newPos);
                }

                if (distanceToPlayer < 20f) {
                    enemy.attack(player);
                }
            } else {
                if (enemy.hasSeenPlayer()) {
                    // Nếu đã từng thấy player, đứng yên 1 lúc rồi patrol
                    enemy.incrementIdleTimer(delta);

                    if (enemy.getIdleTimer() > 3f) { // đứng yên 3s
                        // Tuần tra giữa 2 điểm A <-> B
                        Vector2 target = enemy.isMovingToB() ? enemy.getPatrolPointB() : enemy.getPatrolPointA();
                        Vector2 direction = new Vector2(target).sub(enemy.getPosition());

                        if (direction.len() < 5f) {
                            enemy.setMovingToB(!enemy.isMovingToB()); // đổi hướng tuần tra
                        } else {
                            Vector2 movement = direction.nor().scl(enemy.getMoveSpeed() * delta);
                            Vector2 newPos = new Vector2(enemy.getPosition()).add(movement);
                            if (newPos.dst(enemy.getOrigin()) <= enemy.getMaxRange()) {
                                enemy.setPosition(newPos);
                            }
                        }
                    }
                    // nếu chưa đủ 3s thì quái đứng yên
                } else {
                    // Lang thang ngẫu nhiên
                    enemy.incrementWanderTimer(delta);

                    if (enemy.getWanderTimer() > 2f) {
                        enemy.setRandomWanderDirection(); // random hướng mới
                        enemy.resetWanderTimer();
                    }

                    Vector2 movement = new Vector2(enemy.getWanderDirection()).scl(enemy.getMoveSpeed() * delta);
                    Vector2 newPos = new Vector2(enemy.getPosition()).add(movement);
                    if (newPos.dst(enemy.getOrigin()) <= enemy.getMaxRange()) {
                        enemy.setPosition(newPos);
                    }
                }
            }
        }
    }
}


