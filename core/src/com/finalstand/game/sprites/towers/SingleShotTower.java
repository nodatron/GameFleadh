package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.projectiles.Bullet;
import com.finalstand.game.sprites.projectiles.Projectile;

/**
 * Created by Keith on 09/02/2016.
 */
public class SingleShotTower extends Tower{

    private Vector2 midProjectilePos;
    private Vector2 leftProjectilePos;
    private Vector2 rightProjectilePos;
    public static Vector2 size = new Vector2(16 / FinalStand.PPM, 32 / FinalStand.PPM);

    public SingleShotTower(float x, float y, World world, float angle)
    {
        super(x, y, world, angle);

        level1 = new Texture("towers/singleshot_level1.png");
        level2 = new Texture("towers/singleshot_level2.png");
        level3 = new Texture("towers/singleshot_level3.png");
        currentTexture = level1;
        towerSprite = new Sprite(currentTexture);
        towerSprite.setPosition(x, y);
        towerSprite.setSize(size.x, size.y);

        midProjectilePos = new Vector2(x + ((getTowerSprite().getWidth() / 2.6f)),
                                       y + ((getTowerSprite().getHeight() / 2.5f)));

        leftProjectilePos = new Vector2(x + ((getTowerSprite().getWidth() / 9.3f)),
                                        y + ((getTowerSprite().getHeight() / 2.5f)));

        rightProjectilePos = new Vector2(x + ((getTowerSprite().getWidth() / 1.45f)),
                                         y + ((getTowerSprite().getHeight() / 2.5f)));
        towerRange = (getCurrentTexture().getHeight() * 2.0f) / FinalStand.PPM;

        defineTower();
    }

    @Override
    public void createProjectile()
    {
        if(level == 1 || level == 3) {
            Projectile p1 = new Bullet(midProjectilePos.x, midProjectilePos.y, towerAngle, level,
                    towerSprite.getWidth() / 5.0f,
                    towerSprite.getHeight() / 5.0f, world);
            PlayScreen.projectiles.add(p1);
        }

        if(level > 1)
        {
            Projectile p2 = new Bullet(leftProjectilePos.x, leftProjectilePos.y, towerAngle, level,
                    towerSprite.getWidth() / 5.0f,
                    towerSprite.getHeight() / 5.0f, world);
            PlayScreen.projectiles.add(p2);

            Projectile p3 = new Bullet(rightProjectilePos.x, rightProjectilePos.y, towerAngle, level,
                    towerSprite.getWidth() / 5.0f,
                    towerSprite.getHeight() / 5.0f, world);
            PlayScreen.projectiles.add(p3);
        }
    }

    @Override
    public boolean targetCreep()
    {
        for(int counter = 0; counter < PlayScreen.creeps.size(); counter++) {
            Vector2 creepPos = new Vector2(PlayScreen.creeps.get(counter).getSprite().getX(),
                                            PlayScreen.creeps.get(counter).getSprite().getY());

            if (position.dst(creepPos) < towerRange) {
                towerAngle = (float)Math.atan2(creepPos.y - position.y, creepPos.x - position.x );
                towerAngle *= (180/Math.PI);
                if(towerAngle < 0)
                {
                    towerAngle = 360 - (-towerAngle);
                }
                towerAngle += 90;
                return true;
            }
        }
        return false;
    }
}
