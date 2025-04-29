package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Main.MainGame;

public class MainScreen implements Screen {
    private MainGame game;
    private Stage stage;
    private Skin skin;

    public MainScreen(MainGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load skin (bạn cần skin json + atlas hoặc dùng default skin của LibGDX)
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton playButton = new TextButton("Play", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        playButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                // Ví dụ: game.setScreen(new GameScreen(game));
                System.out.println("Play button clicked!");
                return true;
            }
            return false;
        });

        exitButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                Gdx.app.exit();
                return true;
            }
            return false;
        });

        table.add(playButton).pad(10).row();
        table.add(exitButton).pad(10);
    }

    @Override public void show() {}
    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
