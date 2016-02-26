package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    public SingleShotTower(float x, float y, World world)
    {
        super(x, y, world);
//        bounds = new Rectangle(x, y, level1.getWidth(), level1.getHeight());

        level1 = new Texture("towers/singleshot_level1.png");
        level2 = new Texture("towers/singleshot_level2_alt.png");
        level3 = new Texture("towers/singleshot_level3.png");
        currentTexture = level1;

        midProjectilePos = new Vector2(x + ((getCurrentTexture().getWidth() / 2.6f) / FinalStand.PPM),
                                       y + ((getCurrentTexture().getHeight() / 2.5f) / FinalStand.PPM));

        leftProjectilePos = new Vector2(x + ((getCurrentTexture().getWidth() / 9.3f) / FinalStand.PPM),
                                        y + ((getCurrentTexture().getHeight() / 2.5f) / FinalStand.PPM));

        rightProjectilePos = new Vector2(x + ((getCurrentTexture().getWidth() / 1.45f) / FinalStand.PPM),
                                         y + ((getCurrentTexture().getHeight() / 2.5f) / FinalStand.PPM));

        towerAngle = Math.PI;
    }

    @Override
    public void update()
    {
        if(Gdx.input.justTouched())
        {
            createProjectile();
        }
    }

    @Override
    public void createProjectile()
    {
        if(level == 1 || level == 3) {
            Projectile p1 = new Bullet(midProjectilePos.x, midProjectilePos.y, towerAngle, level,
                    (getCurrentTexture().getWidth() / FinalStand.PPM) / 5.0f,
                    (getCurrentTexture().getHeight() / FinalStand.PPM) / 5.0f);
            PlayScreen.projectiles.add(p1);
        }

        if(level > 1)
        {
            Projectile p2 = new Bullet(leftProjectilePos.x, leftProjectilePos.y, towerAngle, level,
                    (getCurrentTexture().getWidth() / FinalStand.PPM) / 5.0f,
                    (getCurrentTexture().getHeight() / FinalStand.PPM) / 5.0f);
            PlayScreen.projectiles.add(p2);

            Projectile p3 = new Bullet(rightProjectilePos.x, rightProjectilePos.y, towerAngle, level,
                    (getCurrentTexture().getWidth() / FinalStand.PPM) / 5.0f,
                    (getCurrentTexture().getHeight() / FinalStand.PPM) / 5.0f);
            PlayScreen.projectiles.add(p3);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
