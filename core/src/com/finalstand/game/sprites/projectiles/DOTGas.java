package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.creeps.Creep;
import com.finalstand.game.sprites.towers.DOTTower;

/**
 * Created by Keith on 14/02/2016.
 */
public class DOTGas extends Projectile{
    //keep track of time before removing projectile
    private float counter;
    //max time the projectile stays out
    private float removeTime;
    private Vector2 initialPos;
    //distance the projectile moves
    private float range;
    private float angle;
    //length of time the damage over time effects the creep
    private int DOTTime;

    public DOTGas(float x, float y, float angle, int level, float w, float h, World world, float range)
    {
        super(x, y, angle, level, 2 / FinalStand.PPM, world, w, h);
        this.angle = angle;
        projectileSprite = new Sprite(new Texture("projectiles/dot_projectile.png"));
        projectileSprite.setSize(w, h);
        projectileSprite.setPosition(x, y);
        initialPos = new Vector2(x, y);
        counter = 0.0f;
        removeTime = 1.5f;
        this.range = range;

        //set variables in relation to level
        if(level == 1)
        {
            DOTTime = 60;
            damage = 1;
        } else if(level == 2)
        {
            DOTTime = 100;
            damage = 1;
        } else
        {
            DOTTime = 140;
            damage = 2;
        }
    }

    @Override
    public void update()
    {
        counter += 0.01f;
        //remove the projectile when it reachs the length of time it can stay out
        if(counter > removeTime)
        {
            PlayScreen.projectiles.remove(this);
        }

        //different ways the projectile will move in relation to the angle of the tower
        switch((int)angle)
        {
            //tower directed upwards
            case -180:
            {
                if(projectileSprite.getY() < initialPos.y + range) {
                    position.add(forward);
                    projectileSprite.setPosition(position.x, position.y);
                }
                break;
            }
            //tower directed to the left
            case -90:
            {
                if(projectileSprite.getX() > initialPos.x - range) {
                    position.add(forward);
                    projectileSprite.setPosition(position.x, position.y);
                }
                break;
            }
            //tower directed down
            case 0:
            {
                if(projectileSprite.getY() > initialPos.y - range) {
                    position.add(forward);
                    projectileSprite.setPosition(position.x, position.y);
                }
                break;
            }
            //tower directed to the right
            case 90:
            {
                if(projectileSprite.getX() < initialPos.x + range) {
                    position.add(forward);
                    projectileSprite.setPosition(position.x, position.y);
                }
                break;
            }
        }
    }

    //when a creep gets hit by the projectile
    public void onCreepProjHit(Creep creep)
    {
        //set the creep to be damaged over time
        creep.setDOTActive(DOTTime, damage);
    }
}