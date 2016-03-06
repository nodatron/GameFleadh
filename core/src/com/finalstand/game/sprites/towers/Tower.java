package com.finalstand.game.sprites.towers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.buttons.SellButton;
import com.finalstand.game.buttons.UpgradeButton;

/**
 * Created by Keith on 09/02/2016.
 */
public class Tower {
    protected Vector2 position;

    protected int level;
    protected Texture level1;
    protected Texture level2;
    protected Texture level3;
    protected Texture currentTexture;
    protected Sprite towerSprite;

    protected Body b2Body;
    protected World world;

    protected Vector2 projectilePos;
    protected float towerAngle;
    protected float towerRange;
    protected float elapsedTime;
    protected float maxTime;

    public Tower(float x, float y, World world, float angle)
    {
        position = new Vector2(x, y);
        level = 1;
        maxTime = 40.0f;
        elapsedTime = maxTime / 2.0f;
        towerAngle = angle;

        this.world = world;
    }

    public void update(){
        if(targetCreep() && elapsedTime == maxTime)
        {
            createProjectile();
        }

        elapsedTime++;
        if(elapsedTime > maxTime)
        {
            elapsedTime = 0;
        }
    }

    public Texture getCurrentTexture(){return currentTexture;}
    public Vector2 getPosition(){return position;}
    public Sprite getTowerSprite() {
        return towerSprite;
    }
    public float getTowerAngle() {
        return towerAngle;
    }

    public void upgrade()
    {
        if(level < 3)
        {
            level++;
        }
        if(level == 2)
        {
            currentTexture = level2;
        }
        if(level == 3)
        {
            currentTexture = level3;
        }
        towerSprite.setTexture(currentTexture);
    }

    public void defineTower() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x, position.y);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / FinalStand.PPM);
        fdef.filter.categoryBits = FinalStand.TOWER_BIT;
        fdef.filter.maskBits = FinalStand.DEFAULT | FinalStand.ROADBOUNDS_BIT;

        fdef.isSensor = true;
        fdef.shape = shape;
        b2Body.createFixture(fdef).setUserData(this);

//        b2Body.setUserData(towerSprite);

    }

    public void createProjectile()
    {
    }

    public void TowerOptions()
    {
        PlayScreen.upgradeButton = new UpgradeButton(100, this);
        PlayScreen.sellButton = new SellButton(200, this);
        PlayScreen.displayButtons = true;
    }

    public boolean targetCreep()
    {
        for(int counter = 0; counter < PlayScreen.creeps.size(); counter++) {
            Vector2 creepPos = new Vector2(PlayScreen.creeps.get(counter).getSprite().getX(),
                    PlayScreen.creeps.get(counter).getSprite().getY());

            if (position.dst(creepPos) < towerRange) {

                return true;
            }
        }
        return false;
    }
}

