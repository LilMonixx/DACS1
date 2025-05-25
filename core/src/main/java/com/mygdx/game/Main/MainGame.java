package com.mygdx.game.Main;

import com.badlogic.gdx.Game;
import com.mygdx.game.UI.MainScreen;


public class MainGame extends Game {
    @Override
    public void create(){
        this.setScreen(new MainScreen(this));//hiển thị cửa sổ giao diện chính
    }

    @Override
    public void dispose(){
        super.dispose();
        if(getScreen() != null){
            getScreen().dispose();
        }
    }
}
