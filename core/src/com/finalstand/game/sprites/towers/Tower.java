package com.finalstand.game.sprites.towers;


import com.badlogic.gdx.Gdx;
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
import com.finalstand.game.sprites.creeps.BossCreep;

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

    //prices
    protected int level1cost;
    protected int level2cost;
    protected int level3cost;
    protected int upgradeCost;  //stores the value of the next upgrade cost
    protected int sellPrice;

    //box2d for checking if tower can be placed
    protected Body b2Body;
    protected World world;

    //the position where the towers projectile will spawn
    protected Vector2 projectilePos;
    protected float towerAngle;
    protected float towerRange;

    //keep track of time between projectiles being fired
    protected float elapsedTime;
    protected float maxTime;

    protected boolean isDead = false;
    protected boolean canFireAtBoss = false;

    public Tower(float x, float y, World world, float angle)
    {
        position = new Vector2(x, y);
        level = 1;
        maxTime = 40.0f;
        elapsedTime = maxTime / 2.0f;   //starts at half so that the second a tower is placed
        towerAngle = angle;             //it doesnt wait too long before firing

        this.world = world;

        roadboundsHit = false;
        spriteChanged = false;
        placementErrorCounter = 0;
    }

    public void update(){
        //fire projectile
        if(targetCreep() && elapsedTime == maxTime)
        {
            createProjectile();
        }

        elapsedTime++;
        //reset timer
        if(elapsedTime > maxTime)
        {
            elapsedTime = 0;
        }

        if(getRoadboundsHit()) {

            placementErrorCounter ++;

            if(!getSpriteChanged()) {
                towerSprite.getTexture().dispose();
                towerSprite.setTexture(new Texture(Gdx.files.internal("x.png")));
                setSpriteChanged(true);
            }
            if(getPlacementErrorCounter() == 15) {
                setIsDead(true);
            }

        }
    }

    protected short placementErrorCounter;
    protected boolean spriteChanged;

    public short getPlacementErrorCounter() { return placementErrorCounter; }
    public void setPlacementErrorCounter(short placementErrorCounter) {
        this.placementErrorCounter = placementErrorCounter;
    }
    public boolean getSpriteChanged() {
        return spriteChanged;
    }
    public void setSpriteChanged(boolean spriteChanged) {
        this.spriteChanged = spriteChanged;
    }

    //getters
    public Texture getCurrentTexture(){return currentTexture;}
    public Vector2 getPosition(){return position;}
    public Sprite getTowerSprite() {
        return towerSprite;
    }
    public float getTowerAngle() {
        return towerAngle;
    }
    public int getLevel1cost() {
        return level1cost;
    }
    public int getUpgradeCost() {
        return upgradeCost;
    }
    public int getSellPrice() {
        return sellPrice;
    }

    public boolean isDead() {
        return isDead;
    }
    public void setIsDead(boolean isDead) { this.isDead = isDead; }

    public Body getB2Body() {
        return b2Body;
    }

    //tower upgrade
    public void upgrade()
    {
        if(level < 3)
        {
            level++;
        }
        //if it leveled up to level 2
        if(level == 2)
        {
            currentTexture = level2;
            upgradeCost = level3cost;
        }
        //if it leveled up to level 3
        if(level == 3)
        {
            currentTexture = level3;
            upgradeCost = 0;
        }
        //set the tower's texture
        towerSprite.setTexture(currentTexture);
    }

    //creating the box2d
    public void defineTower() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x + (towerSprite.getWidth() / 2), position.y + (towerSprite.getHeight() / 2));
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
    }

    public void createProjectile()
    {
    }

    //displaying options when a tower is clicked
    public void TowerOptions()
    {
        PlayScreen.upgradeButton = new UpgradeButton(upgradeCost, this);
        PlayScreen.sellButton = new SellButton(sellPrice, this);
        PlayScreen.displayButtons = true;
    }

    //check if a creep is near enough to starting firing projectiles
    public boolean targetCreep()
    {
        for(int counter = 0; counter < PlayScreen.creeps.size(); counter++) {
            Vector2 creepPos = new Vector2(PlayScreen.creeps.get(counter).getSprite().getX(),
                    PlayScreen.creeps.get(counter).getSprite().getY());

            if(FinalStand.mapNumber == 4) {
                canFireAtBoss = true;
            }

            if(canFireAtBoss) {
                if (position.dst(creepPos) < towerRange) {

                    return true;
                }
            } else {
                if(!(PlayScreen.creeps.get(counter) instanceof BossCreep)) {
                    if (position.dst(creepPos) < towerRange) {

                        return true;
                    }
                }
            }

        }
        return false;
    }

    protected boolean roadboundsHit;

    public boolean getRoadboundsHit() {
        return roadboundsHit;
    }

    public void setRoadboundsHit(boolean roadboundsHit) {
        this.roadboundsHit = roadboundsHit;
    }

}
