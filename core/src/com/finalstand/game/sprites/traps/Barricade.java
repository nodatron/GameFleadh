package com.finalstand.game.sprites.traps;

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
public class Barricade extends Trap
{

    public Barricade(float x, float y, World world)
    {
        super(x, y);
        texture = new Texture("traps/barricade2.png");
        image = new Sprite(texture);
        image.setSize(trapSize.x, trapSize.y);
        image.setPosition(x, y);
        health = 120; // should last for two seconds
        cost = 50;
        if(FinalStand.score >= cost) {
            FinalStand.score -= cost;
        } else {
            isDead = true;
        }
        this.world = world;
        defineTrap();
    }

    //change the categorybit for each one to the classes bit so for Glue change it to GLUE_BIT
    // this one

    public void defineTrap()
    {
        BodyDef bdef = new BodyDef();
        // this is temporary
        bdef.position.set(getPosition().x + (getImage().getWidth() / 2), getPosition().y + (getImage().getHeight() / 2));
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef(); // Needed for collision detection
        CircleShape shape = new CircleShape();
        shape.setRadius(5/ FinalStand.PPM);

        fdef.filter.categoryBits = FinalStand.BARRICADE_BIT;
        fdef.filter.maskBits = FinalStand.DEFAULT | FinalStand.CREEP_BIT | FinalStand.ROADBOUNDS_BIT;

        fdef.shape = shape;
        fdef.isSensor = true;
        b2Body.createFixture(fdef).setUserData(this);

        getImage().setSize(trapSize.x, trapSize.y);
        getImage().setOrigin(getImage().getWidth() / 2, getImage().getHeight() / 2);
        b2Body.setUserData(getImage());
    }

    public void update()
    {
        if(isHit()) {
            setHealth(getHealth() - 10);
            if(getHealth() <= 0) {
                setIsDead(true);
            }
        }
    }

    //Written by Niall
    @Override
    public void onCreepHit(Creep creep)
    {
        creep.setInitSpeed(creep.getSpeed());
        creep.setSpeed(0);
        setIsHit(true);

    }

    @Override
    public void onCreepRelease(Creep creep)
    {
        creep.setSpeed(creep.getInitSpeed());
        creep.setIsNeeded(true);
    }

    public void render()
    {
        getImage();
    }

    protected void checkCollisions()
    {
        /*for(Creep c : PlayScreen.spawnableCreeps)
        {
            if(Point2D.distance(position.x, position.y, c.position.x, c.position.y) < radius + c.radius)
            {
                c.direction.x = c.direction.x * 0;
                c.direction.y = c.direction.y * 0;
                health--;
                if(health <= 0)
                {
                    PlayScreen.traps.remove(this);
                }
            }
        }*/
    }
}