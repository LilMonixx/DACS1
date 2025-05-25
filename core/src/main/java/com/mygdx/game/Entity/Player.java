package com.mygdx.game.Entity;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Logic.Keyevent;

public class Player {
    private Vector2 position;
    private float speed = 50f;
    private float stateTime = 0f;
    private float scale = 0.05f;

    private Animation<TextureRegion> upAnimation, downAnimation, leftAnimation, rightAnimation;
    private Direction currentDirection = Direction.DOWN;

    private final Keyevent input;
    private final TextureAtlas atlas;
    private boolean isMoving = false;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Player(Keyevent input) {
        this.input = input;
        this.position = new Vector2(100, 100); // Vị trí ban đầu
        this.atlas = new TextureAtlas("Character/character.atlas");

        upAnimation = createAnimation("up1", "up2", "up3");
        downAnimation = createAnimation("down1", "down2", "down3");
        leftAnimation = createAnimation("left1", "left2", "left3");
        rightAnimation = createAnimation("right1", "right2", "right3");
    }

    private Animation<TextureRegion> createAnimation(String... frameNames) {
        Array<TextureRegion> regions = new Array<>();
        for (String name : frameNames) {
            regions.add(atlas.findRegion(name));
        }
        return new Animation<>(0.2f, regions, Animation.PlayMode.LOOP);
    }

    public void update(float delta) {
        Vector2 movement = new Vector2();

        if (input.up) movement.y += 1;
        if (input.down) movement.y -= 1;
        if (input.left) movement.x -= 1;
        if (input.right) movement.x += 1;

        isMoving = movement.len() > 0;

        if (isMoving) {
            movement.nor().scl(speed * delta);
            position.add(movement);

            // Cập nhật hướng nhân vật theo hướng di chuyển chính
            if (Math.abs(movement.x) > Math.abs(movement.y)) {
                currentDirection = movement.x > 0 ? Direction.RIGHT : Direction.LEFT;
            } else {
                currentDirection = movement.y > 0 ? Direction.UP : Direction.DOWN;
            }

            stateTime += delta;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame;
        switch (currentDirection) {
            case UP:
                currentFrame = upAnimation.getKeyFrame(stateTime, true);
                break;
            case DOWN:
                currentFrame = downAnimation.getKeyFrame(stateTime, true);
                break;
            case LEFT:
                currentFrame = leftAnimation.getKeyFrame(stateTime, true);
                break;
            case RIGHT:
                currentFrame = rightAnimation.getKeyFrame(stateTime, true);
                break;
            default:
                currentFrame = downAnimation.getKeyFrame(0);
                break;
        }


        if (!isMoving) {
            currentFrame = currentFrame; // Giữ frame đầu khi đứng yên
        }

        float drawWidth = currentFrame.getRegionWidth() * scale;
        float drawHeight = currentFrame.getRegionHeight() * scale;

        batch.draw(currentFrame, position.x, position.y, drawWidth, drawHeight);
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getDrawWidth() {
        return atlas.getRegions().first().getRegionWidth() * scale;
    }

    public float getDrawHeight() {
        return atlas.getRegions().first().getRegionHeight() * scale;
    }

    public void dispose() {
        atlas.dispose();
    }
}
