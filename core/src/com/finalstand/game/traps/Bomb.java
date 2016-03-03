package com.finalstand.game.traps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.creeps.Creep;

import java.awt.geom.Point2D;

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

    public Bomb(float x, float y, World world)
    {
        super(x, y);
        texture = new Texture("traps/bomb.png");
        texture2 = new Texture("traps/explosion.png");
        image = new Sprite(texture);
        image.setSize(trapSize.x, trapSize.y);
        image.setPosition(x, y);
        cost = 10;
        this.world = world;
        bombActive = false;
        delay = 120; // two seconds @ 60fps
        blastRadius = 128.0f;
        explosionDuration = 0;
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
        shape.setRadius(24/ FinalStand.PPM);

        fdef.filter.categoryBits = FinalStand.BOMB_BIT;
        fdef.filter.maskBits = FinalStand.DEFAULT | FinalStand.RIGHT_BOUND_BIT | FinalStand.LEFT_BOUND_BIT
                | FinalStand.TOP_BOUND_BIT | FinalStand.BOT_BOUND_BIT;

        fdef.shape = shape;
        b2Body.createFixture(fdef);
    }

    public void update()
    {
        // checkCollisions();

        if(bombActive)
        {
            delay--;

            if(delay <= 0)
            {
                explode();
            }
        }
    }

    protected void explode()
    {
        image.setPosition(position.x - trapSize.x, position.y - trapSize.y);
        image.setSize(trapSize.x * 3, trapSize.y * 3);
        image.setTexture(texture2);
//        if(explosionDuration < 60)
//        {
//            for(Creep c : creep)
//            {
//                if(Point2D.distance(position.x, position.y, c.position.x, c.position.y) < blastRadius)
//                {
//                    c.health--;
//                }
//            }
//        }
//        else
//        {
        PlayScreen.traps.remove(this); // ArrayList object removal
//        }

        explosionDuration++;
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

    protected void checkCollisions()
    {
        /*
        if(!bombActive)
        {
            for(Creep c : creep)
            {
                if(Point2D.distance(position.x, position.y, c.position.x, c.position.y) < radius)
                {
                    bombActive = true;
                }
            }
        }
        */
    }

    @Override
    public void onCreepHit(Creep creep)
    {
        System.out.println("in creep hit bomb");
        bombActive = true;
        creep.setBombTriggered(true);
    }

    @Override
    public void onCreepRelease(Creep creep)
    {
        if(creep.getBombTimer() > 0) {
            creep.setBombTriggered(false);
        }
    }
}