package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Main.GameScreen;
import com.mygdx.game.Main.MainGame;

public class LoseScreen implements Screen {
    private MainGame game;
    private Stage stage;
    private Skin skin;
    private Texture background;
    private SpriteBatch batch;
    private GameScreen gameScreen;

    public LoseScreen(MainGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("background.jpg")); // cùng hình nền với PauseMenu

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label loseLabel = new Label("YOU LOSE", skin, "default");
        table.add(loseLabel).padBottom(40).row();

        TextButton retryButton = new TextButton("Retry", skin, "DefaultButton");
        TextButton mainMenuButton = new TextButton("Main Menu", skin, "DefaultButton");
        TextButton exitButton = new TextButton("Exit", skin, "DefaultButton");

        retryButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                game.setScreen(new GameScreen(game)); // chơi lại
                return true;
            }
            return false;
        });

        mainMenuButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                game.setScreen(new MainScreen(game)); // về menu chính
                return true;
            }
            return false;
        });

        exitButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                Gdx.app.exit(); // thoát game
                return true;
            }
            return false;
        });

        table.add(retryButton).width(200).height(50).pad(10).row();
        table.add(mainMenuButton).width(200).height(50).pad(10).row();
        table.add(exitButton).width(200).height(50).pad(10).row();
    }

    @Override public void show() {}
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        background.dispose();
        batch.dispose();
    }
}
