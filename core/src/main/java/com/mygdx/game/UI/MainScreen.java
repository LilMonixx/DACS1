package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Main.GameScreen;
import com.mygdx.game.Main.MainGame;


public class MainScreen implements Screen {
    private MainGame game;
    private Stage stage;
    private Skin skin;
    private Texture background;
    private SpriteBatch batch;

    public MainScreen(MainGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("Background.jpg")); // Đặt ảnh vào thư mục assets

        // Load skin (bạn cần skin json + atlas hoặc dùng default skin của LibGDX)
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton playButton = new TextButton("Enter Dungeon", skin,"DefaultButton");
        TextButton loadButton = new TextButton("Load", skin,"DefaultButton");
        TextButton exitButton = new TextButton("Exit Dungeon",skin,"DefaultButton");
        playButton.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                game.setScreen(new GameScreen(game));  // <-- chuyển sang GameScreen
                return true;
            }
            return false;
        });

        loadButton.addListener(event -> {
            if(event.toString().equals("touchDown")){
                System.out.println("clicked");
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

        table.add(playButton).width(200).height(50).pad(15).row();
        table.add(loadButton).width(200).height(50).pad(15).row();
        table.add(exitButton).width(200).height(50).pad(15).row();

    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Vẽ background trước
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Vẽ stage (nút)
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
        background.dispose(); // Giải phóng ảnh
        batch.dispose();      // Giải phóng batch
    }

}
