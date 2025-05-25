package com.mygdx.game.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.Entity.Player;
import com.mygdx.game.Logic.Keyevent;

public class GameScreen implements Screen {
    private final MainGame game;
    private final SpriteBatch batch;
    private final Player player;
    private final Keyevent input;
    private Map map;
    private OrthographicCamera camera;

    public GameScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.input = new Keyevent();
        this.player = new Player(input);

        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void show() {
        // Viewport: 20x15 tiles (tile size 16)
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 20 * 16, 15 * 16);

        // Load map (Map.java handles flipY)
        map = new Map("MapGame.tmx");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update player movement
        player.update(delta);

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
        batch.end();
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
    }
}
