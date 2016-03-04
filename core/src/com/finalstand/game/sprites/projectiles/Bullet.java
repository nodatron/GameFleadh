package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.creeps.Creep;

/**
 * Created by Keith on 14/02/2016.
 */
public class Bullet extends Projectile{

    public Bullet(float x, float y, float angle, int level, float w, float h, World world)
    {
        super(x, y, angle, level, 6 / FinalStand.PPM, world, w, h);
        projectileSprite = new Sprite(new Texture("projectiles/singleshot_projectile.png"));
        projectileSprite.setSize(w, h);
        projectileSprite.setPosition(x, y);
        damage = 1;
    }

    public void onCreepProjHit(Creep creep)
    {
        System.out.println("Bullet hitting creep");
        creep.setHealth(creep.getHealth() - damage);
        isDead = true;
//        PlayScreen.projectiles.remove(this);
//        world.destroyBody(b2Body);
    }
}