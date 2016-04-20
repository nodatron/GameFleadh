package com.finalstand.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.sprites.creeps.Creep;

/**
 * Created by Niall on 2/15/2016.
 */
public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    protected Fixture fixture;

    //creates box2d for the interactive tiles of the map
    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this. map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / FinalStand.PPM, (bounds.getY() + bounds.getHeight() / 2) / FinalStand.PPM);

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / FinalStand.PPM, (bounds.getHeight() / 2) / FinalStand.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);

        fixture = body.createFixture(fdef);
    }

    public abstract void onRightHit(Creep creep);
    public abstract void onLeftHit(Creep creep);
    public abstract void onTopHit(Creep creep);
    public abstract void onBottomHit(Creep creep);

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
}
