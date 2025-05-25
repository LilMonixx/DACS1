package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Main.GameScreen;
import com.mygdx.game.Main.MainGame;

public class PauseMenu implements Screen {
    private MainGame game;
    private GameScreen gameScreen;
    private Stage stage;
    private Skin skin;
    private Texture background;
    private SpriteBatch batch;

    public PauseMenu(MainGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("background.jpg")); // sử dụng lại hình nền

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton saveButton = new TextButton("Save", skin, "DefaultButton");
        TextButton returnButton = new TextButton("Return", skin, "DefaultButton");
        TextButton mainMenuButton = new TextButton("Main Menu", skin, "DefaultButton");
        TextButton exitButton = new TextButton("Exit Dungeon", skin, "DefaultButton");

        saveButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                System.out.println("Save clicked (chưa thực hiện tính năng)");
                return true;
            }
            return false;
        });

        returnButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                game.setScreen(gameScreen); // quay lại màn hình chơi
                return true;
            }
            return false;
        });

        mainMenuButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                game.setScreen(new MainScreen(game)); // trở về Main Menu
                return true;
            }
            return false;
        });

        exitButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                Gdx.app.exit(); // hoặc logic tương tự Exit Dungeon
                return true;
            }
            return false;
        });

        table.add(saveButton).width(200).height(50).pad(15).row();
        table.add(returnButton).width(200).height(50).pad(15).row();
        table.add(mainMenuButton).width(200).height(50).pad(15).row();
        table.add(exitButton).width(200).height(50).pad(15).row();
    }

    @Override
    public void show() {}

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

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        background.dispose();
        batch.dispose();
    }
}
