package com.mygdx.game.Entity;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {
    private float detectionRange;
    private int attackPower;
    private Vector2 origin;
    private float maxRange = 300f; // mặc định phạm vi hoạt động

    // Thêm cho AI nâng cao
    private boolean hasSeenPlayer = false;
    private float idleTimer = 0f;

    private Vector2 patrolPointA;
    private Vector2 patrolPointB;
    private boolean movingToB = true;

    private Vector2 wanderDirection = new Vector2(1, 0);
    private float wanderTimer = 0f;

    public Enemy(Vector2 position, int health, float moveSpeed, float detectionRange, int attackPower) {
        super(position, health, moveSpeed);
        this.detectionRange = detectionRange;
        this.attackPower = attackPower;
        this.origin = new Vector2(position);

        // Mặc định điểm tuần tra là quanh vị trí origin
        this.patrolPointA = new Vector2(position).add(-50, 0);
        this.patrolPointB = new Vector2(position).add(50, 0);
    }

    public float getDetectionRange() { return detectionRange; }
    public int getAttackPower() { return attackPower; }

    public void setDetectionRange(float detectionRange) {
        this.detectionRange = detectionRange;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public Vector2 getOrigin() { return origin; }
    public float getMaxRange() { return maxRange; }

    public boolean hasSeenPlayer() { return hasSeenPlayer; }
    public void setHasSeenPlayer(boolean seen) { this.hasSeenPlayer = seen; }

    public float getIdleTimer() { return idleTimer; }
    public void incrementIdleTimer(float delta) { this.idleTimer += delta; }
    public void setIdleTimer(float value) { this.idleTimer = value; }

    public Vector2 getPatrolPointA() { return patrolPointA; }
    public Vector2 getPatrolPointB() { return patrolPointB; }
    public boolean isMovingToB() { return movingToB; }
    public void setMovingToB(boolean value) { this.movingToB = value; }

    public Vector2 getWanderDirection() { return wanderDirection; }
    public void setRandomWanderDirection() {
        float angle = (float) (Math.random() * Math.PI * 2);
        this.wanderDirection.set((float) Math.cos(angle), (float) Math.sin(angle));
    }

    public float getWanderTimer() { return wanderTimer; }
    public void incrementWanderTimer(float delta) { this.wanderTimer += delta; }
    public void resetWanderTimer() { this.wanderTimer = 0f; }

    public void attack(Player player) {
        if (!player.isDead()) {
            player.takeDamage(attackPower);
        }
    }
}
