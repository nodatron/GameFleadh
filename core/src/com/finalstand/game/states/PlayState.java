package com.finalstand.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;
import com.finalstand.game.sprites.creeps.*;
import com.finalstand.game.sprites.towers.*;
import com.finalstand.game.tools.B2WorldCreator;

import java.util.ArrayList;

/**
 * Created by Niall PC on 10/02/2016.
 */
public class PlayState implements Screen {

    private World world;
    private Box2DDebugRenderer b2dr;

    // camera for the game
    private OrthographicCamera gameCam;
    //used to scale the screen with aspect ratio
    private Viewport viewport;
    private FinalStand game;

    // Used to load the map
    private TmxMapLoader mapLoader;
    // holds the map
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Creep player;

    private ArrayList<Tower> towers;
//    Texture texture;

    public PlayState(FinalStand game) {
        this.game = game;
//        texture = new Texture("background.jpg");
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map1c.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FinalStand.PPM);

        // centers the camera
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);


        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        player = new Creep(world);

        towers = new ArrayList<Tower>();
        towers.add(new SingleShotTower(0,0));
        towers.add(new AOETower((FinalStand.V_WIDTH / 6) / FinalStand.PPM, 0));
        //towers.add(new DOTTower(game.V_WIDTH / 3, 0));
        towers.add(new LaserTower((FinalStand.V_WIDTH / 3) / FinalStand.PPM, 0));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        // Clearing the screen
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //renders the map
        renderer.render();

        //renders the debug lines for box2d
        b2dr.render(world, gameCam.combined);
//        game.batch.setProjectionMatrix(gameCam.combined);
//        game.batch.begin();
//        game.batch.draw(texture, 0, 0);
//        game.batch.end();
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        for(Tower tower: towers)
        {
            game.batch.draw(tower.getCurrentTexture(), tower.getPosition().x, tower.getPosition().y, gameCam.viewportWidth / 6, gameCam.viewportHeight / 2);
        }
        game.batch.end();
        if(Gdx.input.justTouched())
        {
            for(Tower tower : towers)
            {
                tower.upgrade();
            }
        }
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
        //dispose o resources that are not used anymore
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }

    public void update(float deltaTime) {
        handleInput();

        world.step(1 / 60f, 6, 2);

        gameCam.update();
        player.update();
        renderer.setView(gameCam);
    }

    public void handleInput() {

    }


}
