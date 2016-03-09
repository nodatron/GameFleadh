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
public class Glue extends Trap
{
    public Glue(float x, float y, World world)
    {
        super(x, y);
        texture = new Texture("traps/glue2.png");
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

        defineTrap();
    }

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

        fdef.filter.categoryBits = FinalStand.GLUE_BIT;
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

    protected void checkCollisions()
    {
        /*for(Creep c : creep)
        {
            if(Point2D.distance(position.x, position.y, c.position.x, c.position.y) < radius + c.radius)
            {
                creep.speed = creep.speed / 2;
            }
        }*/
    }

    public void update()
    {
        //checkCollisions();
        if(hits >= 5)
        {
            setIsDead(true);
        }

    }

    @Override
    public void onCreepRelease(Creep creep)
    {
        creep.setSlowed(true);
    }

    @Override
    public void onCreepHit(Creep creep)
    {
//        creep.setIsNeeded(true);
        creep.setInitSpeed(creep.getSpeed());
        creep.setSpeed(creep.getSpeed() / 2);
        setHits(getHits() + 1);
        creep.setSlowed(true);
//        creep.setSlowed(true);

    }
}
