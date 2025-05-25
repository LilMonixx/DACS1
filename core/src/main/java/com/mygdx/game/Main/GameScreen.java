package com.mygdx.game.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.Input;

import com.mygdx.game.Entity.Player;
import com.mygdx.game.Entity.Enemy1;
import com.mygdx.game.Entity.Enemy2;
import com.mygdx.game.Entity.Enemy3;
import com.mygdx.game.Entity.Enemy4;
import com.mygdx.game.Entity.Enemy5;
import com.mygdx.game.Entity.Enemy6;
import com.mygdx.game.Entity.Enemy7;
import com.mygdx.game.Logic.Keyevent;

public class GameScreen implements Screen {
    private final MainGame game;
    private final SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Player player;
    private Enemy1 enemy;
    private Enemy2 enemy2;
    private Enemy3 enemy3;
    private Enemy4 enemy4;
    private Enemy5 enemy5;
    private Enemy6 enemy6;
    private Enemy7 enemy7;
    private final Keyevent input;
    private Map map;
    private OrthographicCamera camera;

    public GameScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.input = new Keyevent();

        Gdx.input.setInputProcessor(input);
    }
    @Override
    public void show() {
        // Viewport: 20x15 tiles (tile size 16)
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 20 * 16, 15 * 16);
        // Load map (Map.java handles flipY)
        map = new Map("MapGame.tmx");
        TiledMapTileLayer ground = map.getGround();
        player = new Player(input, ground);
        enemy = new Enemy1(); // tạo con quái ở vị trí (430, 430)
        enemy2 = new Enemy2();
        enemy3 = new Enemy3();
        enemy4 = new Enemy4();
        enemy5 = new Enemy5();
        enemy6 = new Enemy6();
        enemy7 = new Enemy7();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        // Kiểm tra nhấn phím ESC để mở menu tạm dừng
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new com.mygdx.game.UI.PauseMenu(game, this));
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update player movement
        player.update(delta);
        // Kiểm tra nếu máu cạn → chuyển sang màn hình LoseScreen
        if (player.getHealthIndex() == 0) {
            game.setScreen(new com.mygdx.game.UI.LoseScreen(game, this));
            return;
        }
        enemy.update(delta);
        enemy2.update(delta);
        enemy3.update(delta);
        enemy4.update(delta);
        enemy5.update(delta);
        enemy6.update(delta);
        enemy7.update(delta);
        if (player.getHitbox().overlaps(enemy.getHitbox()) && !player.isSlashing()) {
            player.decreaseHealth();
        }
        if (player.getHitbox().overlaps(enemy.getHitbox()) && player.isSlashing()) {
            enemy.kill();
        }
        if (player.getHitbox().overlaps(enemy2.getHitbox()) && !player.isSlashing()) {
            player.decreaseHealth();
        }
        if (player.getHitbox().overlaps(enemy2.getHitbox()) && player.isSlashing()) {
            enemy2.kill();
        }
        if (player.getHitbox().overlaps(enemy3.getHitbox()) && !player.isSlashing()) {
            player.decreaseHealth();
        }
        if (player.getHitbox().overlaps(enemy3.getHitbox()) && player.isSlashing()) {
            enemy3.kill();
        }
        if (player.getHitbox().overlaps(enemy4.getHitbox()) && !player.isSlashing()) {
            player.decreaseHealth();
        }
        if (player.isSlashing() &&
            !player.hasHitEnemy() &&
            player.getHitbox().overlaps(enemy4.getHitbox())) {

            enemy4.takeDamage();
            player.markHitEnemy(); // chỉ cho chém 1 enemy mỗi đòn
        }

        if (player.getHitbox().overlaps(enemy5.getHitbox()) && !player.isSlashing()) {
            player.decreaseHealth();
        }
        if (player.isSlashing() &&
            !player.hasHitEnemy() &&
            player.getHitbox().overlaps(enemy5.getHitbox())) {

            enemy5.takeDamage();
            player.markHitEnemy(); // chỉ cho chém 1 enemy mỗi đòn
        }
        if (player.getHitbox().overlaps(enemy6.getHitbox()) && !player.isSlashing()) {
            player.decreaseHealth();
        }
        if (player.isSlashing() &&
            !player.hasHitEnemy() &&
            player.getHitbox().overlaps(enemy6.getHitbox())) {

            enemy6.takeDamage();
            player.markHitEnemy(); // chỉ cho chém 1 enemy mỗi đòn
        }
        if (player.getHitbox().overlaps(enemy7.getHitbox()) && !player.isSlashing()) {
            player.decreaseHealth();
        }
        if (player.getHitbox().overlaps(enemy7.getHitbox()) && player.isSlashing()) {
            enemy7.kill();
        }
        int remainingEnemies = 0;
        if (enemy != null && enemy.isAlive()) remainingEnemies++;
        if (enemy2 != null && enemy2.isAlive()) remainingEnemies++;
        if (enemy3 != null && enemy3.isAlive()) remainingEnemies++;
        if (enemy4 != null && enemy4.isAlive()) remainingEnemies++;
        if (enemy5 != null && enemy5.isAlive()) remainingEnemies++;
        if (enemy6 != null && enemy6.isAlive()) remainingEnemies++;
        if (enemy7 != null && enemy7.isAlive()) remainingEnemies++;
        if (remainingEnemies == 0) {
            game.setScreen(new com.mygdx.game.UI.VictoryScreen(game, this));
            return;
        }

        // Center camera on player (with offset to center character)
        camera.position.set(
            player.getPosition().x + player.getDrawWidth() / 2f,
            player.getPosition().y + player.getDrawHeight() / 2f,
            0
        );
        camera.update();

        // Draw map
        map.render(camera);

        // Draw player
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.render(batch);
        enemy.render(batch);
        enemy2.render(batch);
        enemy3.render(batch);
        enemy4.render(batch);
        enemy5.render(batch);
        enemy6.render(batch);
        enemy7.render(batch);
        batch.end();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(player.getHitbox().x, player.getHitbox().y,
            player.getHitbox().width, player.getHitbox().height);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(enemy.getHitbox().x, enemy.getHitbox().y,
            enemy.getHitbox().width, enemy.getHitbox().height);
// Lặp lại cho enemy2, 3, 4 nếu muốn
        shapeRenderer.end();

    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        map.dispose();
        enemy.dispose();
        enemy2.dispose();
        enemy3.dispose();
        enemy4.dispose();
        enemy5.dispose();
        enemy6.dispose();
        enemy7.dispose();
    }
}
