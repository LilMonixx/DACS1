package com.mygdx.game.Logic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class Keyevent implements InputProcessor {
    public boolean left, right, up, down, shift, mouseLeft;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A: left = true; break;
            case Input.Keys.D: right = true; break;
            case Input.Keys.W: up = true; break;
            case Input.Keys.S: down = true; break;
            case Input.Keys.SHIFT_LEFT: shift = true; break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A: left = false; break;
            case Input.Keys.D: right = false; break;
            case Input.Keys.W: up = false; break;
            case Input.Keys.S: down = false; break;
            case Input.Keys.SHIFT_LEFT: shift = false; break;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            mouseLeft = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            mouseLeft = false;
        }
        return true;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    // Không xử lý các sự kiện này
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
}
