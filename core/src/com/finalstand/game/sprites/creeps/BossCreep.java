package com.finalstand.game.sprites.creeps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;


/**
 * Created by Niall on 09/02/2016.
 */
public class BossCreep extends Creep {

    public BossCreep(float x, float y, World world) {
        super(world);
        position = new Vector2(x, y);
        texture = new Texture(Gdx.files.internal("creeps/BossCreep.png"));
        sprite = new Sprite(texture);
        defineCreep();
        health = 10000;
        speed = 0.5f;
        score = 100;
    }

    @Override
    public void defineCreep() {
        BodyDef bdef = new BodyDef();
        // this is temporary
        bdef.position.set(getPosition().x / FinalStand.PPM, getPosition().y / FinalStand.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / FinalStand.PPM);

        //setting what a creep can collide with and what bit it is
        fdef.filter.categoryBits = FinalStand.CREEP_BIT;
        fdef.filter.maskBits = FinalStand.DEFAULT | FinalStand.ROADBOUNDS_BIT | FinalStand.PROJECTILE_BIT
                | FinalStand.BARRICADE_BIT | FinalStand.GLUE_BIT | FinalStand.BOMB_BIT
                | FinalStand.SPIKE_BIT | FinalStand.WAYPOINT_BIT;
        fdef.shape = shape;
        fdef.isSensor = true;
        b2Body.createFixture(fdef).setUserData(this);

        EdgeShape rightBound = new EdgeShape();
        rightBound.set(new Vector2(8 / FinalStand.PPM, 3 / FinalStand.PPM), new Vector2(8 / FinalStand.PPM, - 3 / FinalStand.PPM));
        fdef.filter.categoryBits = FinalStand.RIGHT_BOUND_BIT;
        fdef.filter.maskBits =  FinalStand.DEFAULT | FinalStand.ROADBOUNDS_BIT | FinalStand.PROJECTILE_BIT
                | FinalStand.BARRICADE_BIT | FinalStand.GLUE_BIT | FinalStand.BOMB_BIT
                | FinalStand.SPIKE_BIT | FinalStand.WAYPOINT_BIT;
        fdef.shape = rightBound;
        fdef.isSensor = true;
        b2Body.createFixture(fdef).setUserData(this);

        EdgeShape leftBound = new EdgeShape();
        leftBound.set(new Vector2(-8 / FinalStand.PPM, 3 / FinalStand.PPM), new Vector2(-8 / FinalStand.PPM, - 3 / FinalStand.PPM));
        fdef.filter.categoryBits = FinalStand.LEFT_BOUND_BIT;
        fdef.filter.maskBits = FinalStand.DEFAULT | FinalStand.ROADBOUNDS_BIT | FinalStand.PROJECTILE_BIT
                | FinalStand.BARRICADE_BIT | FinalStand.GLUE_BIT | FinalStand.BOMB_BIT
                | FinalStand.SPIKE_BIT | FinalStand.WAYPOINT_BIT;
        fdef.shape = leftBound;
        fdef.isSensor = true;
        b2Body.createFixture(fdef).setUserData(this);

        EdgeShape topBound = new EdgeShape();
        topBound.set(new Vector2(3 / FinalStand.PPM, 8 / FinalStand.PPM), new Vector2(- 3 / FinalStand.PPM, 8 / FinalStand.PPM));
        fdef.filter.categoryBits = FinalStand.TOP_BOUND_BIT;
        fdef.filter.maskBits = FinalStand.DEFAULT | FinalStand.ROADBOUNDS_BIT | FinalStand.PROJECTILE_BIT
                | FinalStand.BARRICADE_BIT | FinalStand.GLUE_BIT | FinalStand.BOMB_BIT
                | FinalStand.SPIKE_BIT | FinalStand.WAYPOINT_BIT;
        fdef.shape = topBound;
        fdef.isSensor = true;
        b2Body.createFixture(fdef).setUserData(this);

        EdgeShape bottomBound = new EdgeShape();
        bottomBound.set(new Vector2(3 / FinalStand.PPM, - 8 / FinalStand.PPM), new Vector2(- 3 / FinalStand.PPM, -8 / FinalStand.PPM));
        fdef.filter.categoryBits = FinalStand.BOT_BOUND_BIT;
        fdef.filter.maskBits = FinalStand.DEFAULT | FinalStand.ROADBOUNDS_BIT | FinalStand.PROJECTILE_BIT
                | FinalStand.BARRICADE_BIT | FinalStand.GLUE_BIT | FinalStand.BOMB_BIT
                | FinalStand.SPIKE_BIT | FinalStand.WAYPOINT_BIT;
        fdef.shape = bottomBound;
        fdef.isSensor = true;
        b2Body.createFixture(fdef).setUserData(this);

        // sprite to the body
        sprite.setSize(48 / FinalStand.PPM, 48 / FinalStand.PPM);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        b2Body.setUserData(sprite);

        rightBound.dispose();
        leftBound.dispose();
        topBound.dispose();
        bottomBound.dispose();

    }


    @Override
    public void checkDir() {
        if(getWaypointHit() == dir.size) {
            gameOver();
        } else {
            if (dir.get(getWaypointHit()).equals("right")) {
                setMovement(0);
                unsetMovement(1);
                unsetMovement(2);
                unsetMovement(3);
            } else if (dir.get(getWaypointHit()).equals("left")) {
                setMovement(1);
                unsetMovement(0);
                unsetMovement(2);
                unsetMovement(3);
            } else if (dir.get(getWaypointHit()).equals("up")) {
                setMovement(2);
                unsetMovement(1);
                unsetMovement(0);
                unsetMovement(3);
            } else if (dir.get(getWaypointHit()).equals("down")) {
                setMovement(3);
                unsetMovement(1);
                unsetMovement(2);
                unsetMovement(0);
            }
        }
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }

    public void gameOver() {
        FinalStand.gameOver = true;
    }


}
