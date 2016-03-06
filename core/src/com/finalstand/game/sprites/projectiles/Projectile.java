package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.creeps.Creep;

/**
 * Created by Keith on 14/02/2016.
 */
public class Projectile extends Sprite{
    protected Vector2 position;
    //added to position to make the projectile move
    protected Vector2 forward;
    protected float speed;
    //the projectile may vary depending on the level of the tower
    protected int level;
    protected float angle;
    //box2d body
    protected Body b2Body;
    protected World world;

    protected Sprite projectileSprite;
    protected float width;
    protected float height;

    protected int damage;
    //when the projectile needs to be removed
    protected boolean isDead = false;

    //getters and setters
    public boolean isDead() {
        return isDead;
    }

    public Body getB2Body() {
        return b2Body;
    }

    public void setIsDead(boolean isDead) {

        this.isDead = isDead;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Sprite getSprite() {
        return projectileSprite;
    }

    public float getAngle() {
        return angle;
    }

    public Projectile(float x, float y, float angle, int level, float speed, World world, float width, float height)
    {
        position = new Vector2(x, y);
        this.level = level;
        this.speed = speed;
        this.angle = angle;
        //calculating the forward vector
        forward = new Vector2(MathUtils.sinDeg(angle) * speed, -MathUtils.cosDeg(angle) * speed);
        this.world = world;
        this.width = width;
        this.height = height;

        //create box2d
        defineProjectile();
    }

    public void defineProjectile() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x, position.y);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2);
        fdef.filter.categoryBits = FinalStand.PROJECTILE_BIT;
        fdef.filter.maskBits = FinalStand.DEFAULT | FinalStand.CREEP_BIT;

        fdef.isSensor = true;
        fdef.shape = shape;
        b2Body.createFixture(fdef).setUserData(this);
    }

    public void update()
    {
        //adjust the position in relation to the forward vector
        position.add(forward);
        //create a forward vector for the movement of the box2d
        Vector2 boxForward = new Vector2(forward.x, forward.y);
        //rescale boxForward
        boxForward.scl(FinalStand.PPM / 2 + 10);
        //reset the sprite position
        projectileSprite.setPosition(position.x, position.y);
        //adjust the box2d position
        b2Body.setLinearVelocity(boxForward);
        //remove the projectile if it goes off screen
        if(position.x > FinalStand.V_WIDTH / FinalStand.PPM || position.x < 0 ||
                position.y > FinalStand.V_HEIGHT / FinalStand.PPM || position.y < 0)
        {
            this.isDead = true;
        }
    }

    //method for dealing with a projectile colliding with a creep
    public void onCreepProjHit(Creep creep, Projectile projectile) {
        if(projectile instanceof Bullet) {
            ((Bullet) projectile).onCreepProjHit(creep);
        } else if(projectile instanceof DOTGas)
        {
            ((DOTGas) projectile).onCreepProjHit(creep);
        } else if(projectile instanceof AOERing)
        {
            ((AOERing) projectile).onCreepProjHit(creep);
        } else if(projectile instanceof Laser)
        {
            ((Laser) projectile).onCreepProjHit(creep);
        }
    }

    public void onCreepProjRelease(Creep creep, Projectile projectile)
    {
        if(projectile instanceof DOTGas)
        {
            ((DOTGas) projectile).onCreepProjHit(creep);
        }
        else if(projectile instanceof AOERing)
        {
            ((AOERing) projectile).onCreepProjHit(creep);
        }

    }
}