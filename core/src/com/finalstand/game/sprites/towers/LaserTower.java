package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.projectiles.Laser;
import com.finalstand.game.sprites.projectiles.Projectile;

/**
 * Created by Keith on 09/02/2016.
 */
public class LaserTower extends Tower{

    public static Vector2 size = new Vector2(16 / FinalStand.PPM, 32 / FinalStand.PPM);

    public LaserTower(float x, float y, World world, float angle)
    {
        super(x, y, world, angle);

        level1 = new Texture("towers/laserTower_level1.png");
        level2 = new Texture("towers/laserTower_level2.png");
        level3 = new Texture("towers/laserTower_level3_notFiring.png");
        //Texture level3Firing = new Texture("laserTower_level3_firing.png");
        currentTexture = level1;
        towerSprite = new Sprite(currentTexture);
        towerSprite.setPosition(x, y);
        towerSprite.setSize(size.x, size.y);

        projectilePos =  new Vector2(x + ((getCurrentTexture().getWidth() / 2.5f) / FinalStand.PPM),
                                     y + ((getTowerSprite().getHeight() / 2.0f) / FinalStand.PPM));
        towerRange = (getCurrentTexture().getHeight() * 2.0f) / FinalStand.PPM;
    }

    @Override
    public void createProjectile()
    {
        Projectile p = new Laser(projectilePos.x, projectilePos.y, towerAngle, level,
                                (getCurrentTexture().getWidth() / FinalStand.PPM) / 4.0f, getCurrentTexture().getHeight() / FinalStand.PPM);
        PlayScreen.projectiles.add(p);
    }
}
