package com.finalstand.game.sprites.creeps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.traps.Bomb;
import com.finalstand.game.tools.Waypoint;

/**
 * Created by Niall on 09/02/2016.
 */
public class Creep extends Sprite{

    protected Texture texture;
    protected Sprite sprite;
    protected SpriteBatch batch;

    protected Vector2 position;

    protected int health;
    protected float speed;

    protected World world;
    protected Body b2Body;

    protected Array<Body> bodies = new Array<Body>();

    protected boolean[] movement;

    protected short timeElapsed;
    protected boolean setDirection;
    protected Vector2 direction;

    protected Array<String> dir;
    protected int waypointHit;

    protected boolean slowed;
    protected int timer;
    protected float initSpeed;

    protected boolean bombTriggered;
    protected int bombTimer;

    protected int slowedTimer = 0;

    public int getSlowedTimer() {
        return slowedTimer;
    }

    public void setSlowedTimer(int getSlowedTimer) {
        this.slowedTimer = getSlowedTimer;
    }

    public int getBombTimer() {
        return bombTimer;
    }

    public void setBombTimer(int bombTimer) {
        this.bombTimer = bombTimer;
    }

    public boolean isBombTriggered() {
        return bombTriggered;
    }

    public void setBombTriggered(boolean bombTriggered) {
        this.bombTriggered = bombTriggered;
    }

    public float getInitSpeed() {
        return initSpeed;
    }

    public void setInitSpeed(float initSpeed) {
        this.initSpeed = initSpeed;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public boolean isSlowed() {
        return slowed;
    }

    public void setSlowed(boolean slowed) {
        this.slowed = slowed;
    }

    public Creep(World world) {
        this.world = world;
        movement = new boolean[4];
        movement[0] = true;
        setDirection = false;
        direction = new Vector2(50 / FinalStand.PPM, 0);
        timeElapsed = 0;
        if(FinalStand.mapNumber == 1) {
            dir = Waypoint.readWaypoints("map1.txt");
        } else if(FinalStand.mapNumber == 2) {
            dir = Waypoint.readWaypoints("map2.txt");
        } else if(FinalStand.mapNumber == 3 || FinalStand.mapNumber == 4){
            dir = Waypoint.readWaypoints("map3.txt");
        }
        waypointHit = 0;
        speed = 1;
        initSpeed = speed;
        bombTimer = 120;
    }

    /**
     * Creates box2d fiture for the creep
     */
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
        fdef.filter.maskBits = FinalStand.DEFAULT | FinalStand.ROADBOUNDS_BIT | FinalStand.PROJECTILE_BIT
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
        sprite.setSize(16 / FinalStand.PPM, 16 / FinalStand.PPM);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        b2Body.setUserData(sprite);

        rightBound.dispose();
        leftBound.dispose();
        topBound.dispose();
        bottomBound.dispose();

    }

    public Vector2 getPosition() {
        return position;
    }

    public void update() {

        if(isBombTriggered()) {
            setBombTimer(getBombTimer() - 1);
            if(getBombTimer() <= 0) {
                setHealth(getHealth() - Bomb.getDamage());
            }
        } else {
            setBombTimer(120);
        }

        checkDir();

        //set the way to go
        if(movement[0]) {
            // go right
            direction = new Vector2(50 / FinalStand.PPM, 0);
        } else if(movement[1]) {
            // go left
            direction = new Vector2(- 50 / FinalStand.PPM, 0);
        } else if(movement[2]) {
            // go up
            direction = new Vector2(0, 50 / FinalStand.PPM);
        } else if(movement[3]){
            //go down
            direction = new Vector2(0, - 50 / FinalStand.PPM);
        }

        if(isSlowed()) {
            setSpeed(getSpeed());
            setSlowedTimer(getSlowedTimer() + 1);
        }

        if(getSlowedTimer() > 180) {
            setSpeed(getInitSpeed());
            setSlowed(false);
        }
        direction.scl(speed);
//        this.b2Body.applyLinearImpulse(direction, this.b2Body.getWorldCenter(), true);
        this.b2Body.setLinearVelocity(direction);
//        timeElapsed++;
    }

//    public void render(SpriteBatch batch) {
//
//    }

    public void setMovement(int index) {
        movement[index] = true;
    }

    public void unsetMovement(int index) {
        movement[index] = false;
    }

    public void setWaypointHit() { waypointHit++; }

    public int getWaypointHit() { return waypointHit; }

    public void checkDir() {
        if(getWaypointHit() == dir.size) {
            reachedEnd();
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


    public void reachedEnd() {
//        PlayScreen.creeps.remove(this);
        PlayScreen.spawnableCreeps.remove(this);
//        world.destroyBody(b2Body);
    }

    public Body getB2Body() {
        return b2Body;
    }

    void dispose() {

    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
