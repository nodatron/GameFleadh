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

    public DOTTower(float x, float y, World world, float angle)
    {
        super(x, y, world, angle);
        maxTime = 250.0f;

        level1 = new Texture("towers/dot_level1.png");
        level2 = new Texture("towers/dot_level2.png");
        level3 = new Texture("towers/dot_level3.png");
        currentTexture = level1;
        towerSprite = new Sprite(currentTexture);
        towerSprite.setPosition(x, y);
        towerSprite.setSize(size.x, size.y);

        projectilePos = new Vector2(x, y);
        towerRange = (getCurrentTexture().getHeight() * 2.0f) / FinalStand.PPM;
        System.out.println(towerAngle);
    }

    @Override
    public void createProjectile()
    {
        Projectile p = new DOTGas(projectilePos.x, projectilePos.y, towerAngle, level,
                                  towerSprite.getWidth(), towerSprite.getHeight());
        PlayScreen.projectiles.add(p);
    }
}
