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

    private int creepHits;
    private int maxCreepHits;

    public Laser(float x, float y, float angle, int level, float w, float h, World world)
    {
        super(x, y, angle, level, 8 / FinalStand.PPM, world, w, h);
        if(level < 3) {
            projectileSprite = new Sprite(new Texture("projectiles/laser_projectile_level1.png"));
            maxCreepHits = 3;
            if(level == 1)
            {
                damage = 1;
            }
            else
            {
                damage = 2;
            }
        }
        else {
            projectileSprite = new Sprite(new Texture("projectiles/laser_projectile_level3.png"));
            maxCreepHits = 6;
            damage = 2;
        }
        projectileSprite.setSize(w, h);
        projectileSprite.setPosition(x, y);
        creepHits = 0;
    }

    public void onCreepProjHit(Creep creep)
    {
        System.out.println("Laser hitting creep");
        creep.setHealth(creep.getHealth() - damage);
        creepHits++;
        if(creepHits >= maxCreepHits) {
            isDead = true;
        }
    }
}