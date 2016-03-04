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
public class Laser extends Projectile{

    public Laser(float x, float y, float angle, int level, float w, float h, World world)
    {
        super(x, y, angle, level, 8 / FinalStand.PPM, world, w, h);
        if(level < 3) {
            projectileSprite = new Sprite(new Texture("projectiles/laser_projectile_level1.png"));
        }
        else {
            projectileSprite = new Sprite(new Texture("projectiles/laser_projectile_level3.png"));
        }
        projectileSprite.setSize(w, h);
        projectileSprite.setPosition(x, y);
        damage = 1;
    }

    public void onCreepProjHit(Creep creep)
    {
        creep.setHealth(creep.getHealth() - damage);
        PlayScreen.projectiles.remove(this);
    }
}