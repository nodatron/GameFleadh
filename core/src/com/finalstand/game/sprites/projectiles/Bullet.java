package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.Gdx;
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
        projectileSprite = new Sprite(new Texture(Gdx.files.internal("projectiles/singleshot_projectile.png")));
        projectileSprite.setSize(w, h);
        projectileSprite.setPosition(x, y);
        damage = 13;
    }

    public void onCreepProjHit(Creep creep)
    {
        //damage creep
        creep.setHealth(creep.getHealth() - damage);
        creep.setDamaged(true);
        //remove projectile
        isDead = true;
    }
}