package com.finalstand.game.sprites.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.creeps.Creep;

/**
 * Created by noel on 22/02/2016.
 */
public class Bomb extends Trap
{
    Texture texture2;
    boolean bombActive;
    int delay;
    float blastRadius;
    int explosionDuration;
    private boolean isBombExploded = false;

    public Bomb(float x, float y, World world)
    {
        super(x, y);
        texture = new Texture(Gdx.files.internal("traps/bomb.png"));
        texture2 = new Texture(Gdx.files.internal("traps/explosion.png"));
        image = new Sprite(texture);
        image.setSize(trapSize.x, trapSize.y);
        image.setPosition(x, y);
        cost = 100;
        if(FinalStand.score >= cost) {
            FinalStand.score -= cost;
        } else {
            isDead = true;
        }
        this.world = world;
        bombActive = false;
        delay = 60; // two seconds @ 60fps
        blastRadius = 128.0f;
        explosionDuration = 0;
        damage = 50;
        defineTrap();
    }

    public boolean isBombActive() {
        return bombActive;
    }

    public void setBombActive(boolean bombActive) {
        this.bombActive = bombActive;
    }

    public int getExplosionDuration() {
        return explosionDuration;
    }

    public void setExplosionDuration(int explosionDuration) {
        this.explosionDuration = explosionDuration;
    }

    // making the trap in box2d
    public void defineTrap()
    {
        BodyDef bdef = new BodyDef();
        // this is temporary
        bdef.position.set(getPosition().x + (getImage().getWidth() / 2), getPosition().y + (getImage().getHeight() / 2));
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef(); // Needed for collision detection
        CircleShape shape = new CircleShape();
        shape.setRadius(24 / FinalStand.PPM);

        fdef.filter.categoryBits = FinalStand.BOMB_BIT;
        fdef.filter.maskBits = FinalStand.DEFAULT | FinalStand.CREEP_BIT | FinalStand.ROADBOUNDS_BIT;

        fdef.shape = shape;
        fdef.isSensor = true;
        b2Body.createFixture(fdef).setUserData(this);

        getImage().setSize(trapSize.x, trapSize.y);
        getImage().setOrigin(getImage().getWidth() / 2, getImage().getHeight() / 2);
        b2Body.setUserData(getImage());
    }

    public boolean isBombExploded() {
        return isBombExploded;
    }

    public void setIsBombExploded(boolean isBombExploded) {
        this.isBombExploded = isBombExploded;
    }

    public void update()
    {
        // checkCollisions();

        //checking for when the bomb should be destroyed
        if(isBombActive())
        {
            delay--;

            if(delay <= 0)
            {
                explode();
            }
        }

        if(isBombExploded()) {
            setExplosionDuration(getExplosionDuration() + 1);
        }

        if(getExplosionDuration() > 10) {
            setIsDead(true);
        }

        if(isOneLoop()) {
            if (!getRoadboundsHit()) {
                setIsDead(true);
            }
        }

        setOneLoop(true);
    }

    protected void explode()
    {
        image.setPosition(position.x - trapSize.x, position.y - trapSize.y);
        image.setSize(trapSize.x * 3, trapSize.y * 3);
        image.setTexture(texture2);

        setIsBombExploded(true);
    }

    public void render()
    {
        getImage();
    }

    @Override
    public Sprite getImage()
    {
        return image;
    }


    @Override
    public void onCreepHit(Creep creep)
    {
        //sets stuff in creep when they enter the radius
        setBombActive(true);
        creep.setBombTriggered(true);
        creep.setBombTimer(delay);
    }

    @Override
    public void onCreepRelease(Creep creep)
    {
        //sets stuff if the creep leaves the radius
        if(creep.getBombTimer() > 0) {
            creep.setBombTriggered(false);
        }
    }
}