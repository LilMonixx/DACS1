package com.mygdx.game.Entity;

import com.badlogic.gdx.math.Vector2;
public abstract class Entity {
    protected Vector2 position;
    protected int health;
    protected int maxHealth;
    protected float moveSpeed;

    public Entity(Vector2 position, int health, float moveSpeed) {
        this.position = position;
        this.health = health;
        this.maxHealth = health;
        this.moveSpeed = moveSpeed;
    }

    public Vector2 getPosition() { return position; }
    public void setPosition(Vector2 position) { this.position = position; }

    public int getHealth() { return health; }
    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }
    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth); // để đảm bảo ko vượt quá HP tối đa
    }


    public boolean isDead() {
        return health <= 0;
    }

    public float getMoveSpeed() { return moveSpeed; }
}

