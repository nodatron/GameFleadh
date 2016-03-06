package com.finalstand.game.sprites.traps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.sprites.InteractiveTileObject;
import com.finalstand.game.sprites.creeps.Creep;

/**
 * Created by Carl on 16/02/2016.
 */
public abstract class Trap extends Sprite
{
    protected int hits;
    protected Vector2 position;
    protected Texture texture;
    protected Sprite image;
    protected float radius;
    protected int cost;

    protected World world;
    protected Body b2Body;

    protected boolean isDead = false;
    protected boolean isHit = false;

    protected int health;

    protected static int damage;

    public static int getDamage() {
        return damage;
    }

    public static void setDamage(int d) {
        damage = d;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getHealth() {

        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public int getHits() {

        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public Body getB2Body() {
        return b2Body;
    }

    public void setB2Body(Body b2Body) {
        this.b2Body = b2Body;
    }

    public static Vector2 trapSize = new Vector2(16 / FinalStand.PPM, 16 / FinalStand.PPM);

    protected Trap(float x, float y)
    {
        position = new Vector2(x, y);
        radius = 32.0f; // diameter is 64px
        cost = 0;
        hits = 0;
    }

    public abstract void defineTrap();

    public abstract void update();
    public abstract void render();

    public abstract void onCreepHit(Creep creep);
    public abstract void onCreepRelease(Creep creep);

    public Sprite getImage()
    {
        return image;
    }
}