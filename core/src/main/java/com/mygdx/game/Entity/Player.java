package com.mygdx.game.Entity;

import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
    private int mana;
    private int level;
    private int atk;
    private int def;

    public Player(Vector2 position) {
        super(position, 200, 5f); // 200 HP tại lv1, 5f là moveSpeed
        this.level = 1;
        this.mana = 100; // có thể sửa tùy theo game
        this.atk = 15;
        this.def = 40;
    }

    public int getMana() { return mana; }
    public void setMana(int mana) { this.mana = mana; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getAtk() { return atk; }
    public int getDef() { return def; }

    public void levelUp() {
        level++;
        maxHealth += 25;
        atk += 4;
        def += 4;
        setHealth(maxHealth); // hồi máu khi lên cấp
    }
}
