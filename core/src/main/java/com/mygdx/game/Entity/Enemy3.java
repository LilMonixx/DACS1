package com.mygdx.game.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Enemy3 {
    private final Vector2 position;
    private final Rectangle hitbox;
    private final float speed = 20f; // tốc độ di chuyển ngang
    private float stateTime = 0f;
    private boolean movingRight = true;
    private boolean isAlive = true;


    private final TextureAtlas atlas;
    private final Animation<TextureRegion> animation;
    private final float frameDuration = 0.1f;

    private final float scale = 0.9f;
    private final float minX = 200f;
    private final float maxX = 420f;

    public Enemy3() {
        this.atlas = new TextureAtlas(Gdx.files.internal("Enemy3/E3.atlas"));
        this.position = new Vector2(200, 165); // vị trí bắt đầu

        Array<TextureRegion> frames = new Array<>();
        frames.add(atlas.findRegion("E3.1"));
        frames.add(atlas.findRegion("E3.2"));
        frames.add(atlas.findRegion("E3.3"));
        frames.add(atlas.findRegion("E3.4"));
        frames.add(atlas.findRegion("E3.5"));
        frames.add(atlas.findRegion("E3.6"));
        frames.add(atlas.findRegion("E3.7"));
        frames.add(atlas.findRegion("E3.8"));

        animation = new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
        TextureRegion firstFrame = animation.getKeyFrame(0);
        float drawWidth = firstFrame.getRegionWidth() * scale;
        float drawHeight = firstFrame.getRegionHeight() * scale;
        hitbox = new Rectangle(position.x, position.y, drawWidth, drawHeight);

    }

    public void update(float delta) {
        if (!isAlive) return;

        stateTime += delta;
        // Di chuyển ngang
        if (movingRight) {
            position.x += speed * delta;
            if (position.x >= maxX) {
                position.x = maxX;
                movingRight = false;
            }
        } else {
            position.x -= speed * delta;
            if (position.x <= minX) {
                position.x = minX;
                movingRight = true;
            }
        }
        hitbox.setPosition(position.x, position.y);
    }

    public void render(SpriteBatch batch) {
        if (!isAlive) return;
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        float drawWidth = currentFrame.getRegionWidth() * scale;
        float drawHeight = currentFrame.getRegionHeight() * scale;

        batch.draw(currentFrame, position.x, position.y, drawWidth, drawHeight);
    }

    public void dispose() {
        atlas.dispose();
    }

    public Rectangle getHitbox() {
        if (!isAlive) return new Rectangle(0, 0, 0, 0);
        return hitbox;
    }
    public void kill() {
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
