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
    protected int armour;
    protected boolean[] status;

    protected World world;
    protected Body b2Body;

    protected Array<Body> bodies = new Array<Body>();

    protected boolean[] movement;

    protected short timeElapsed;
    protected boolean setDirection;
    protected Vector2 direction;

    protected Array<String> dir;
    protected int waypointHit;

    public Creep(World world) {
        this.world = world;
        movement = new boolean[4];
        movement[0] = true;
        setDirection = false;
        direction = new Vector2(0.2f / FinalStand.PPM, 0);
        timeElapsed = 0;
        dir = Waypoint.readWaypoints("map1.txt");
        waypointHit = 0;
//        texture = new Texture("BasicCreep.png");
//        sprite = new Sprite(texture);
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
                               | FinalStand.TRAP_BIT | FinalStand.BASE_BIT | FinalStand.WAYPOINT_BIT;
        fdef.shape = shape;
        b2Body.createFixture(fdef);

        EdgeShape rightBound = new EdgeShape();
        rightBound.set(new Vector2(8 / FinalStand.PPM, 3 / FinalStand.PPM), new Vector2(8 / FinalStand.PPM, - 3 / FinalStand.PPM));
        fdef.filter.categoryBits = FinalStand.RIGHT_BOUND_BIT;
        fdef.shape = rightBound;
        fdef.isSensor = true;
        b2Body.createFixture(fdef).setUserData(this);

        EdgeShape leftBound = new EdgeShape();
        leftBound.set(new Vector2(-8 / FinalStand.PPM, 3 / FinalStand.PPM), new Vector2(-8 / FinalStand.PPM, - 3 / FinalStand.PPM));
        fdef.filter.categoryBits = FinalStand.LEFT_BOUND_BIT;
        fdef.shape = leftBound;
        fdef.isSensor = true;
        b2Body.createFixture(fdef).setUserData(this);

        EdgeShape topBound = new EdgeShape();
        topBound.set(new Vector2(3 / FinalStand.PPM, 8 / FinalStand.PPM), new Vector2(- 3 / FinalStand.PPM, 8 / FinalStand.PPM));
        fdef.filter.categoryBits = FinalStand.TOP_BOUND_BIT;
        fdef.shape = topBound;
        fdef.isSensor = true;
        b2Body.createFixture(fdef).setUserData(this);

        EdgeShape bottomBound = new EdgeShape();
        bottomBound.set(new Vector2(3 / FinalStand.PPM, - 8 / FinalStand.PPM), new Vector2(- 3 / FinalStand.PPM, -8 / FinalStand.PPM));
        fdef.filter.categoryBits = FinalStand.BOT_BOUND_BIT;
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
        //speeds will be held by variables
        //this.b2body.getLinearVelocity().x/.y - gets the speed the object is moving at
        // This moves the creep in positive x direction
//        this.b2Body.applyLinearImpulse(new Vector2(0.2f / FinalStand.PPM, 0), this.b2Body.getWorldCenter(), true);
//        if(timeElapsed > 100) {
//            setDirection = true;
//        }

        checkDir();

        //set the way to go
        if(movement[0]) {
            // go right
            direction = new Vector2(300f / FinalStand.PPM, 0);
        } else if(movement[1]) {
            // go left
            direction = new Vector2(- 300f / FinalStand.PPM, 0);
        } else if(movement[2]) {
            // go up
            direction = new Vector2(0, 300f / FinalStand.PPM);
        } else if(movement[3]){
            //go down
            direction = new Vector2(0, - 300f / FinalStand.PPM);
        }
//        this.b2Body.applyLinearImpulse(direction, this.b2Body.getWorldCenter(), true);
        this.b2Body.setLinearVelocity(direction);
//        timeElapsed++;
    }

    public void render(SpriteBatch batch) {
        world.getBodies(bodies);
        for(Body body : bodies) {
            if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite ssprite = (Sprite) body.getUserData();
                ssprite.setPosition(body.getPosition().x - ssprite.getWidth() / 2, body.getPosition().y - ssprite.getHeight() / 2);
                ssprite.draw(batch);
            }
        }
    }

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
    }


    void dispose() {

    }

}
