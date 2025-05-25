package com.mygdx.game.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Enemy1 {
    private final Vector2 position;
    private final Rectangle hitbox;
    private final float speed = 30f; // tốc độ di chuyển
    private float stateTime = 0f;
    private boolean movingUp = true;
    private boolean isAlive = true;

    private final TextureAtlas atlas;
    private final Animation<TextureRegion> animation;
    private final float frameDuration = 0.2f;

    private final float scale = 0.9f; // scale lên vì sprite nhỏ
    private final float minY = 430f;
    private final float maxY = 550f;

    public Enemy1() {
        this.atlas = new TextureAtlas(Gdx.files.internal("Enemy1/E1.atlas"));
        this.position = new Vector2(430, 430);

        Array<TextureRegion> frames = new Array<>();
        frames.add(atlas.findRegion("E1.1"));
        frames.add(atlas.findRegion("E1.2"));
        frames.add(atlas.findRegion("E1.5"));
        frames.add(atlas.findRegion("E1.6"));
        frames.add(atlas.findRegion("E1.7"));
        frames.add(atlas.findRegion("E1.8"));
        frames.add(atlas.findRegion("E1.3"));
        frames.add(atlas.findRegion("E1.4"));

        animation = new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
        TextureRegion firstFrame = animation.getKeyFrame(0);
        float drawWidth = firstFrame.getRegionWidth() * scale;
        float drawHeight = firstFrame.getRegionHeight() * scale;
        hitbox = new Rectangle(position.x, position.y, drawWidth, drawHeight);

    }

    public void update(float delta) {
        if (!isAlive) return;

        stateTime += delta;
        // Di chuyển dọc
        if (movingUp) {
            position.y += speed * delta;
            if (position.y >= maxY) {
                position.y = maxY;
                movingUp = false;
            }
        } else {
            position.y -= speed * delta;
            if (position.y <= minY) {
                position.y = minY;
                movingUp = true;
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
