package com.mygdx.game.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Logic.Keyevent;

public class Player {
    private Vector2 position;
    private float speed = 50f;
    private float stateTime = 0f;
    private float scale = 0.05f;

    private TextureAtlas hbAtlas;
    private TextureRegion[] healthBars;
    private int healthIndex = 6; // 6 = đầy máu, 0 = cạn máu

    private Animation<TextureRegion> upAnimation, downAnimation, leftAnimation, rightAnimation;
    private Direction currentDirection = Direction.DOWN;
    private Rectangle hitbox;

    private TextureAtlas slashAtlas;
    private Animation<TextureRegion> slashAnimation;
    private boolean isSlashing = false;
    private float slashStateTime = 0f;
    private boolean hasHitEnemy = false;

    private float hitCooldownTimer = 0f; // thời gian đợi trước khi nhận sát thương tiếp theo
    private final float hitCooldownDuration = 1f; // 1 giây


    private final Keyevent input;
    private final TextureAtlas atlas;
    private boolean isMoving = false;

    private final TiledMapTileLayer ground;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Player(Keyevent input, TiledMapTileLayer ground) {
        this.input = input;
        this.ground = ground;
        this.position = new Vector2(25, 655); // Vị trí ban đầu
        this.atlas = new TextureAtlas("Character/character.atlas");

        slashAtlas = new TextureAtlas(Gdx.files.internal("Slash/Sl.atlas")); // đường dẫn atlas slash
        slashAnimation = createSlashAnimation("Slash1", "Slash2", "Slash3", "Slash4", "Slash5", "Slash6", "Slash7");

        // Load thanh máu
        hbAtlas = new TextureAtlas(Gdx.files.internal("HB/HB.atlas"));
        healthBars = new TextureRegion[7];
        for (int i = 0; i < 7; i++) {
            healthBars[i] = hbAtlas.findRegion("hb" + i); // Hoặc "hb-" nếu atlas đặt tên vậy
        }

        upAnimation = createAnimation("up1", "up2", "up3");
        downAnimation = createAnimation("down1", "down2", "down3");
        leftAnimation = createAnimation("left1", "left2", "left3");
        rightAnimation = createAnimation("right1", "right2", "right3");

        float width = atlas.getRegions().first().getRegionWidth() * scale;
        float height = atlas.getRegions().first().getRegionHeight() * scale;
        hitbox = new Rectangle(position.x, position.y, width, height);

    }

    private Animation<TextureRegion> createAnimation(String... frameNames) {
        Array<TextureRegion> regions = new Array<>();
        for (String name : frameNames) {
            regions.add(atlas.findRegion(name));
        }
        return new Animation<>(0.2f, regions, Animation.PlayMode.LOOP);
    }
    private Animation<TextureRegion> createSlashAnimation(String... frameNames) {
        Array<TextureRegion> regions = new Array<>();
        for (String name : frameNames) {
            regions.add(slashAtlas.findRegion(name));
        }
        return new Animation<>(0.3f / frameNames.length, regions, Animation.PlayMode.NORMAL);
    }

    public void update(float delta) {
        if (input.t && !isSlashing) {
            isSlashing = true;
            slashStateTime = 0;
            hasHitEnemy = false;  // ← reset tại đây
        }
        Vector2 movement = new Vector2();

        if (input.up) movement.y += 1;
        if (input.down) movement.y -= 1;
        if (input.left) movement.x -= 1;
        if (input.right) movement.x += 1;

        isMoving = movement.len() > 0;

        if (isMoving) {
            movement.nor().scl(speed * delta);
            float newX = position.x + movement.x;
            float newY = position.y + movement.y;

            if (!isCellBlocked(newX, newY)) position.add(movement);
            hitbox.setPosition(position.x, position.y);


            // Cập nhật hướng nhân vật theo hướng di chuyển chính
            if (Math.abs(movement.x) > Math.abs(movement.y)) {
                currentDirection = movement.x > 0 ? Direction.RIGHT : Direction.LEFT;
            } else {
                currentDirection = movement.y > 0 ? Direction.UP : Direction.DOWN;
            }

            stateTime += delta;
        }

        // Giảm thời gian cooldown va chạm
        if (hitCooldownTimer > 0) {
            hitCooldownTimer -= delta;
        }
        // Cập nhật animation chém
        if (isSlashing) {
            slashStateTime += delta;
            if (slashAnimation.isAnimationFinished(slashStateTime)) {
                isSlashing = false;
            }
        }
    }
    private boolean isCellBlocked(float x, float y) {
        int tileX = (int) (x / ground.getTileWidth());
        int tileY = (int) (y / ground.getTileHeight());

        // Nếu không có tile ở vị trí này trên lớp ground, thì chặn
        return ground.getCell(tileX, tileY) == null;
    }

    public void render(SpriteBatch batch) {
        // 1. Vẽ nhân vật bình thường
        TextureRegion playerFrame;
        switch (currentDirection) {
            case UP:
                playerFrame = upAnimation.getKeyFrame(stateTime, true);
                break;
            case DOWN:
                playerFrame = downAnimation.getKeyFrame(stateTime, true);
                break;
            case LEFT:
                playerFrame = leftAnimation.getKeyFrame(stateTime, true);
                break;
            case RIGHT:
                playerFrame = rightAnimation.getKeyFrame(stateTime, true);
                break;
            default:
                playerFrame = downAnimation.getKeyFrame(0);
                break;
        }

        if (!isMoving) {
            playerFrame = playerFrame;
        }

        float playerWidth = playerFrame.getRegionWidth() * scale;
        float playerHeight = playerFrame.getRegionHeight() * scale;

        // 2. Vẽ nhân vật tại vị trí đứng
        batch.draw(playerFrame, position.x, position.y, playerWidth, playerHeight);

        // 3. Nếu đang chém → vẽ Slash ở bên phải nhân vật
        if (isSlashing) {
            TextureRegion slashFrame = slashAnimation.getKeyFrame(slashStateTime, false);

            float slashWidth = slashFrame.getRegionWidth() * scale;
            float slashHeight = slashFrame.getRegionHeight() * scale;

            float slashX = position.x + getDrawWidth()/3; // bên phải nhân vật
            float slashY = position.y;

            batch.draw(slashFrame, slashX, slashY, slashWidth, slashHeight);
        }

        // 4. Vẽ thanh máu như cũ
        TextureRegion currentHealthBar = healthBars[healthIndex];

        float barWidth = currentHealthBar.getRegionWidth() * 0.5f;
        float barHeight = currentHealthBar.getRegionHeight() * 0.5f;

        float barX = position.x + (getDrawWidth() - barWidth) / 2;
        float barY = position.y + getDrawHeight() + 5;

        batch.draw(currentHealthBar, barX, barY, barWidth, barHeight);
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
        hbAtlas.dispose();
        slashAtlas.dispose();
    }
    public Rectangle getHitbox() {
        return hitbox;
    }

    public void decreaseHealth() {
        if (hitCooldownTimer <= 0f) {
            if (healthIndex > 0) {
                healthIndex--;
            }
            hitCooldownTimer = hitCooldownDuration;
        }
    }
    public boolean isSlashing() {
        return isSlashing;
    }
    public boolean hasHitEnemy() {
        return hasHitEnemy;
    }
    public void markHitEnemy() {
        hasHitEnemy = true;
    }
    public int getHealthIndex() {
        return healthIndex;
    }
}
