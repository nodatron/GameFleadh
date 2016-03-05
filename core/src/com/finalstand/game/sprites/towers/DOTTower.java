package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.projectiles.DOTGas;
import com.finalstand.game.sprites.projectiles.Projectile;

/**
 * Created by Keith on 09/02/2016.
 */
public class DOTTower extends Tower{

    public static Vector2 size = new Vector2(32 / FinalStand.PPM, 32 / FinalStand.PPM);
    private Vector2 DOTProjectileSize;
    private boolean adjustlevel3 = false;
    private float level3leftAngle;
    private float level3rightAngle;

    public DOTTower(float x, float y, World world, float angle)
    {
        super(x, y, world, angle);
        maxTime = 200.0f;

        level1 = new Texture("towers/dot_level1.png");
        level2 = new Texture("towers/dot_level2.png");
        level3 = new Texture("towers/dot_level3.png");
        currentTexture = level1;
        towerSprite = new Sprite(currentTexture);
        towerSprite.setPosition(x, y);
        towerSprite.setSize(size.x, size.y);

        projectilePos = new Vector2(x, y);
        DOTProjectileSize = new Vector2(towerSprite.getWidth(), towerSprite.getHeight());
        towerRange = (getCurrentTexture().getHeight() * 2.0f) / FinalStand.PPM;
        level3leftAngle = towerAngle + 90;
        level3rightAngle = towerAngle - 90;

        if(level3leftAngle >= 180)
        {
            level3leftAngle = -180;
        }

        if(level3rightAngle <= -270)
        {
            level3rightAngle = 90;
        }

        defineTower();
    }

    @Override
    public void update(){
        if(targetCreep() && elapsedTime == maxTime / 2)
        {
            createProjectile();
        }

        elapsedTime++;
        if(elapsedTime > maxTime)
        {
            elapsedTime = 0;
            if(level == 3)
            {
                towerSprite.setTexture(level3);
            }
        }

        if(level == 3 && adjustlevel3 == false)
        {
            towerSprite.setSize(48 / FinalStand.PPM, 32 / FinalStand.PPM);
            towerSprite.setPosition(towerSprite.getX() - 8 / FinalStand.PPM, towerSprite.getY());
            adjustlevel3 = true;
        }
    }

    @Override
    public void createProjectile()
    {
        Projectile p1 = new DOTGas(projectilePos.x, projectilePos.y, towerAngle, level,
                                  DOTProjectileSize.x, DOTProjectileSize.y, world);
        PlayScreen.projectiles.add(p1);

        if(level == 3)
        {
            Projectile p2 = new DOTGas(projectilePos.x, projectilePos.y, level3rightAngle, level,
                                       DOTProjectileSize.x, DOTProjectileSize.y, world);
            Projectile p3 = new DOTGas(projectilePos.x, projectilePos.y, level3leftAngle, level,
                                       DOTProjectileSize.x, DOTProjectileSize.y, world);

            PlayScreen.projectiles.add(p2);
            PlayScreen.projectiles.add(p3);
        }
    }
}
