package com.finalstand.game.creeps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;

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

    public Creep(World world) {
        this.world = world;
        defineCreep();
//        texture = new Texture("BasicCreep.png");
//        sprite = new Sprite(texture);
    }

    public void defineCreep() {
        BodyDef bdef = new BodyDef();
        // this is temporary
        bdef.position.set(10 / FinalStand.PPM, 355 / FinalStand.PPM);
        bdef.type =BodyDef.BodyType.DynamicBody;

        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/ FinalStand.PPM);

        fdef.shape = shape;
        b2Body.createFixture(fdef);
    }
    public void update() {
        //speeds will be heald by variables
        //this.b2body.getLinearVelocity().x/.y - gets the speed the object is moving at
        this.b2Body.applyLinearImpulse(new Vector2(0.2f, 0), this.b2Body.getWorldCenter(), true);
    }
    void render(SpriteBatch batch) {

    }
    void dispose() {

    }

}
