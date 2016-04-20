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
import com.finalstand.game.sprites.traps.Bomb;


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
        health = 3000;
        speed = 1;
        score = 100;
    }

    // does the same as the super class except for the movement is done differently
    @Override
    public void update() {
        if(getHealth() <= 0) {
            setIsDead(true);
        }

        if(isBombTriggered()) {
            setBombTimer(getBombTimer() - 1);
            if(getBombTimer() <= 0) {
                setHealth(getHealth() - Bomb.getDamage());
                setDamaged(true);
                setBombTriggered(false);
            }
        } else {
            setBombTimer(120);
        }

        if(isNeeded()) {
            checkDir();
            //set the way to go
            if (movement[0]) {
                // go right
                direction.x = 20 / FinalStand.PPM;
                direction.y = 0;
            } else if (movement[1]) {
                // go left
                direction.x = -20 / FinalStand.PPM;
                direction.y = 0;
            } else if (movement[2]) {
                // go up
                direction.x = 0;
                direction.y = 20 / FinalStand.PPM;
            } else if (movement[3]) {
                //go down
                direction.x = 0;
                direction.y = -20 / FinalStand.PPM;
            }
            setIsNeeded(false);
        }


        if(isSlowed()) {
            setSpeed(getSpeed());
            setSlowedTimer(getSlowedTimer() + 1);
            setIsNeeded(true);
        }

        if(getSlowedTimer() > 300) {
            setSpeed(getInitSpeed());
            setSlowed(false);
            setIsNeeded(true);
        }

        direction.scl(speed);
        this.b2Body.setLinearVelocity(direction);

        if(isDamaged()) {
            if(elapsed == 0) {
                changeDmgSprite();
            }
            if(elapsed > 10) {
                setDamaged(false);
                setChanged(false);
            }
//            System.out.println("Changing the sprite dmg");
            elapsed++;
        }

        if(!isDamaged() && !isChanged()) {
            changeNormalSprite();
//            System.out.println("Changing the sprite normal");
            setChanged(true);
            elapsed = 0;
        }

        //if a damage over time effect is present on the creep, damage it
        if(DOTActive)
        {
            if(DOTTimer % 20 == 0)
            {
                setHealth(getHealth() - DOTDamage);
                setDamaged(true);
            }
            this.DOTTimer--;
            if(DOTTimer <= 0)
            {
                this.DOTActive = false;
                DOTTimer = 0;
                DOTDamage = 0;
                setDamaged(false);
            }
        }
//        System.out.println(speed);
    }

    // making the dox2d body and fixture for the boss creep
    @Override
    public void defineCreep() {
        BodyDef bdef = new BodyDef();
        // this is temporary
        bdef.position.set(getPosition().x / FinalStand.PPM, getPosition().y / FinalStand.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(24 / FinalStand.PPM);

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

    @Override
    public void changeDmgSprite() {
        sprite.getTexture().dispose();
        sprite.setTexture(new Texture(Gdx.files.internal("creeps/bosscrrepdmg.png")));
//        setDamaged(false);
    }

    @Override
    public void changeNormalSprite() {
        sprite.getTexture().dispose();
        sprite.setTexture(new Texture(Gdx.files.internal("creeps/BossCreep.png")));
//        setChanged(true);
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }

    public void gameOver() {
        FinalStand.gameOver = true;
    }


}
