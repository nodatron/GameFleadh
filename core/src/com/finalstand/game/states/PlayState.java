package com.finalstand.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;
import com.finalstand.game.creeps.Creep;

/**
 * Created by Niall PC on 10/02/2016.
 */
public class PlayState implements Screen {

    private World world;
    private Box2DDebugRenderer b2dr;

    private OrthographicCamera gameCam;
    private Viewport viewport;
    private FinalStand game;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Creep player;

//    Texture texture;

    public PlayState(FinalStand game) {
        this.game = game;
//        texture = new Texture("background.jpg");
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map1c.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FinalStand.PPM);

        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FinalStand.PPM, (rect.getY() + rect.getHeight() / 2) / FinalStand.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / FinalStand.PPM, (rect.getHeight() / 2) / FinalStand.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

//        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
//
//            body = world.createBody(bdef);
//
//            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
//            fdef.shape = shape;
//            body.createFixture(fdef);
//        }

        player = new Creep(world);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gameCam.combined);
//        game.batch.setProjectionMatrix(gameCam.combined);
//        game.batch.begin();
//        game.batch.draw(texture, 0, 0);
//        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void update(float deltaTime) {
        handleInput();

        world.step(1/60f, 6, 2);

        gameCam.update();
        player.update();
        renderer.setView(gameCam);
    }

    public void handleInput() {

    }


}
