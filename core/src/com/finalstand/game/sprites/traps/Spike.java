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
public class Spike extends Trap
{
    public Spike(float x, float y, World world)
    {
        super(x, y);
        texture = new Texture("traps/spikes3.png");
        image = new Sprite(texture);
        image.setSize(64, 64);
        cost = 10;
        this.world = world;
    }

    public void defineTrap()
    {
        BodyDef bdef = new BodyDef();
        // this is temporary
        bdef.position.set(10 / FinalStand.PPM, 355 / FinalStand.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef(); // Needed for collision detection
        CircleShape shape = new CircleShape();
        shape.setRadius(5/ FinalStand.PPM);

        fdef.filter.categoryBits = FinalStand.SPIKE_BIT;
        fdef.filter.maskBits = FinalStand.DEFAULT | FinalStand.RIGHT_BOUND_BIT | FinalStand.LEFT_BOUND_BIT
                | FinalStand.TOP_BOUND_BIT | FinalStand.BOT_BOUND_BIT;

        fdef.shape = shape;
        b2Body.createFixture(fdef);
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
                creep.health--;
            }
        }*/
    }

    public void update()
    {
        //checkCollisions();
    }

    @Override
    public void onCreepHit(Creep creep)
    {
        creep.health--;
        hits++;

        if(hits >= 300)
        {
            PlayScreen.traps.remove(this);
            world.destroyBody(b2Body);
        }
    }

    @Override
    public void onCreepRelease(Creep creep)
    {

    }
}
