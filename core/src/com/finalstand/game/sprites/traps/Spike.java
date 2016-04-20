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
 * Created by Carl on 16/02/2016.
 */
public class Spike extends Trap
{
    public Spike(float x, float y, World world)
    {
        super(x, y);
        texture = new Texture(Gdx.files.internal("traps/spikes3.png"));
        image = new Sprite(texture);
        image.setSize(trapSize.x, trapSize.y);
        image.setPosition(x, y);
        cost = 150;
        if(FinalStand.score >= cost) {
            FinalStand.score -= cost;
        } else {
            isDead = true;
        }
        this.world = world;
        damage = 20;

        defineTrap();
    }

    // make the creep in box2d
    public void defineTrap()
    {
        BodyDef bdef = new BodyDef();
        // this is temporary
        bdef.position.set(getPosition().x + (getImage().getWidth() / 2), getPosition().y + (getImage().getHeight() / 2));
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef(); // Needed for collision detection
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / FinalStand.PPM);

        fdef.filter.categoryBits = FinalStand.SPIKE_BIT;
        fdef.filter.maskBits = FinalStand.DEFAULT | FinalStand.CREEP_BIT | FinalStand.ROADBOUNDS_BIT;

        fdef.shape = shape;
        fdef.isSensor = true;
        b2Body.createFixture(fdef).setUserData(this);

        getImage().setSize(trapSize.x, trapSize.y);
        getImage().setOrigin(getImage().getWidth() / 2, getImage().getHeight() / 2);
        b2Body.setUserData(getImage());
    }

    public void render()
    {
        getImage();
    }

    public void update()
    {
        // checks if the trap shoiuld be destroyed
        if(hits >= 5)
        {
            setIsDead(true);
        }

        if(isOneLoop()) {
            if (!getRoadboundsHit()) {
                setIsDead(true);
            }
        }

        setOneLoop(true);
    }

    @Override
    public void onCreepHit(Creep creep)
    {
        //does damage to the creep if it hits a creep
        creep.setHealth(creep.getHealth() - getDamage());
        creep.setDamaged(true);
        hits++;
    }

    @Override
    public void onCreepRelease(Creep creep)
    {
    }
}
