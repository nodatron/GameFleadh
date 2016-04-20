package com.finalstand.game.sprites.creeps;

import com.badlogic.gdx.Gdx;
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
public class Creep {

    //look of the creep
    protected Texture texture;
    protected Sprite sprite;
    //position of creep
    protected Vector2 position;

    //points given when dead
    protected int score;

    protected int health;
    protected float speed;

    //box2d stuff
    protected World world;
    protected Body b2Body;

    //for checking which direction to go
    protected boolean[] movement;

    protected short timeElapsed;
    protected boolean setDirection;
    protected Vector2 direction;

    // gets the direction the creeps need to go when they have hit a waypoint
    protected Array<String> dir;
    protected int waypointHit;

    // for glue interaction
    protected boolean slowed;
    protected int timer;
    protected float initSpeed;

    // for bomb interaction
    protected boolean bombTriggered;
    protected int bombTimer;

    protected int slowedTimer = 0;

    protected boolean isDead = false;
    protected boolean isNeeded = false;

    protected boolean damaged;
    protected boolean changed;
    protected int elapsed;

    public Creep(World world) {
        this.world = world;
        movement = new boolean[4];
        movement[0] = true;
        setDirection = false;
        timeElapsed = 0;
        switch(FinalStand.mapNumber) {
            case 1: {
                dir = Waypoint.readWaypoints("map1.txt");
                direction = new Vector2(50 / FinalStand.PPM, 0);
            } break;

            case 2: {
                dir = Waypoint.readWaypoints("map2.txt");
                direction = new Vector2(0, - 50 / FinalStand.PPM);
            } break;

            case 3:
            case 4: {
                dir = Waypoint.readWaypoints("map3.txt");
                direction = new Vector2(0, - 50 / FinalStand.PPM);
            } break;
        }

        waypointHit = 0;
        speed = 1;
        initSpeed = speed;
        bombTimer = 120;
        DOTActive = false;
        DOTTimer = 0;
        damaged = false;
        changed = true;
        elapsed = 0;
    }

    //creates the box2d fiture for the creep
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
        //if the creep has no health they are dead
        if(getHealth() <= 0) {
            setIsDead(true);
        }

        //checks if the creep needs to be damaged by the bomb
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

        //checks if the direction that the creep moves needs to be updated
        if(isNeeded()) {
            checkDir();
            //set the way to go
            if (movement[0]) {
                // go right
                direction.x = 50 / FinalStand.PPM;
                direction.y = 0;
            } else if (movement[1]) {
                // go left
                direction.x = -50 / FinalStand.PPM;
                direction.y = 0;
            } else if (movement[2]) {
                // go up
                direction.x = 0;
                direction.y = 50 / FinalStand.PPM;
            } else if (movement[3]) {
                //go down
                direction.x = 0;
                direction.y = -50 / FinalStand.PPM;
            }
            setIsNeeded(false);
        }

        // slow if it is hit by glue
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

        // animation if the sprite is damaged
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

    }

    public void changeDmgSprite() {

    }

    public void changeNormalSprite() {

    }

//    public void render(SpriteBatch batch) {

    //checks the direction the creep should travel
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

    void dispose() {
        sprite.getTexture().dispose();
    }

    // reached the end of the map
    public void reachedEnd() {
//        PlayScreen.creeps.remove(this);
        setIsDead(true);
        FinalStand.setHealth(FinalStand.getHealth() - 1);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isNeeded() {
        return isNeeded;
    }

    public void setIsNeeded(boolean isNeeded) {
        this.isNeeded = isNeeded;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public int getSlowedTimer() {
        return slowedTimer;
    }

    public void setSlowedTimer(int getSlowedTimer) {
        this.slowedTimer = getSlowedTimer;
    }
    protected boolean DOTActive;
    protected float DOTTimer;
    protected int DOTDamage;

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

    public void setDOTActive(int time, int DOTDamage) {
        this.DOTActive = true;
        DOTTimer = time;
        this.DOTDamage = DOTDamage;
    }

    public void setMovement(int index) {
        movement[index] = true;
    }

    public void unsetMovement(int index) {
        movement[index] = false;
    }

    public void setWaypointHit() { waypointHit++; }

    public int getWaypointHit() { return waypointHit; }

    public Body getB2Body() {
        return b2Body;
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

    public void setDamaged(boolean damaged) { this.damaged = damaged; }
    public boolean isDamaged() { return damaged; }
    public void setChanged(boolean changed) { this.changed = changed; }
    public boolean isChanged() { return changed;}
}
