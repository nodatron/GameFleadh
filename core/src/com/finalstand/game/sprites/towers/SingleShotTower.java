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

    //different positions for the projectiles spawning
    private Vector2 midProjectilePos;
    private Vector2 leftProjectilePos;
    private Vector2 rightProjectilePos;

    //default size of singleshot tower
    public static Vector2 size = new Vector2(16 / FinalStand.PPM, 32 / FinalStand.PPM);

    public SingleShotTower(float x, float y, World world, float angle)
    {
        super(x, y, world, angle);

        //loading in the different textures for the different levels
        level1 = new Texture("towers/singleshot_level1.png");
        level2 = new Texture("towers/singleshot_level2.png");
        level3 = new Texture("towers/singleshot_level3.png");
        currentTexture = level1;
        //creating the tower sprite
        towerSprite = new Sprite(currentTexture);
        towerSprite.setPosition(x, y);
        towerSprite.setSize(size.x, size.y);

        //setting the different projectile positions
        midProjectilePos = new Vector2(x + ((getTowerSprite().getWidth() / 2.6f)),
                                       y + ((getTowerSprite().getHeight() / 2.5f)));

        leftProjectilePos = new Vector2(x + ((getTowerSprite().getWidth() / 9.3f)),
                                        y + ((getTowerSprite().getHeight() / 2.5f)));

        rightProjectilePos = new Vector2(x + ((getTowerSprite().getWidth() / 1.45f)),
                                         y + ((getTowerSprite().getHeight() / 2.5f)));
        towerRange = (getCurrentTexture().getHeight() * 2.0f) / FinalStand.PPM;

        //make the towers box2d
        defineTower();
    }

    @Override
    public void createProjectile()
    {
        //for level 1 & 3 the middle gun will fire
        if(level == 1 || level == 3) {
            Projectile p1 = new Bullet(midProjectilePos.x, midProjectilePos.y, towerAngle, level,
                    towerSprite.getWidth() / 5.0f,
                    towerSprite.getHeight() / 5.0f, world);
            PlayScreen.projectiles.add(p1);
        }

        //for level 2 only the left and right gun will fire
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
        //checking the distance from the tower between all the creeps
        for(int counter = 0; counter < PlayScreen.spawnableCreeps.size(); counter++) {
            Vector2 creepPos = new Vector2(PlayScreen.spawnableCreeps.get(counter).getSprite().getX(),
                                            PlayScreen.spawnableCreeps.get(counter).getSprite().getY());

            //if the creep is close enough
            if (position.dst(creepPos) < towerRange) {
                //calculate the angle between the creep and the tower
                towerAngle = (float)Math.atan2(creepPos.y - position.y, creepPos.x - position.x );
                //converting the angle to degrees
                towerAngle *= (180/Math.PI);
                //converting the angle to be between 0 and 360
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
