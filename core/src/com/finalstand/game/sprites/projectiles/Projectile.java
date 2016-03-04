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
    protected Vector2 forward;
    protected float speed;
    protected int level;
    protected float angle;
    protected Body b2Body;
    protected World world;
    protected float width;
    protected float height;
    protected int damage;
    protected boolean isDead = false;

    public boolean isDead() {
        return isDead;
    }

    public Body getB2Body() {
        return b2Body;
    }

    public void setIsDead(boolean isDead) {

        this.isDead = isDead;
    }

    protected Sprite projectileSprite;

    public Projectile(float x, float y, float angle, int level, float speed, World world, float width, float height)
    {
        position = new Vector2(x, y);
        this.level = level;
        this.speed = speed;
        this.angle = angle;
        forward = new Vector2(MathUtils.sinDeg(angle) * speed, -MathUtils.cosDeg(angle) * speed);
        this.world = world;
        this.width = width;
        this.height = height;

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
        position.add(forward);
        Vector2 boxForward = new Vector2(forward.x, forward.y);
        boxForward.scl(FinalStand.PPM / 2 + 10);
        projectileSprite.setPosition(position.x, position.y);
        b2Body.setLinearVelocity(boxForward);
    }

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

//    public void checkCollision()
//    {
//        for(int counter = 0; counter < PlayScreen.spawnableCreeps.size(); counter ++)
//        {
//            if()
//        }
//    }

    public Vector2 getPosition() {
        return position;
    }

    public Sprite getSprite() {
        return projectileSprite;
    }

    public float getAngle() {
        return angle;
    }
}