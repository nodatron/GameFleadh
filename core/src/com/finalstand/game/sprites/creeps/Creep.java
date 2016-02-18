package com.finalstand.game.sprites.creeps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
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

    protected Array<Body> bodies = new Array<Body>();

    public Creep(World world) {
        this.world = world;

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
        shape.setRadius(5/ FinalStand.PPM);

        fdef.shape = shape;
        b2Body.createFixture(fdef);

        // sprite to the body
        sprite.setSize(16 / FinalStand.PPM, 16/ FinalStand.PPM);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        b2Body.setUserData(sprite);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update() {
        //speeds will be heald by variables
        //this.b2body.getLinearVelocity().x/.y - gets the speed the object is moving at
        // This moves the creep in positive x direction
        this.b2Body.applyLinearImpulse(new Vector2(0.2f / FinalStand.PPM, 0), this.b2Body.getWorldCenter(), true);
    }

    public void render(SpriteBatch batch) {
        world.getBodies(bodies);
        for(Body body : bodies) {
            if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite ssprite = (Sprite) body.getUserData();
                ssprite.setPosition(body.getPosition().x  - ssprite.getWidth() / 2 , body.getPosition().y - ssprite.getHeight() / 2);
                ssprite.draw(batch);
            }
        }
    }
    void dispose() {

    }

}
