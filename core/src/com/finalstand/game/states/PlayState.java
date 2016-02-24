package com.finalstand.game.states;

/**
 * Created by Keith on 11/02/2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;
import com.finalstand.game.buttons.Button;
import com.finalstand.game.buttons.SellButton;
import com.finalstand.game.buttons.UpgradeButton;
import com.finalstand.game.sprites.creeps.*;
import com.finalstand.game.sprites.towers.*;
import com.finalstand.game.tools.B2WorldCreator;
import com.finalstand.game.ui.OptionTexture;
import com.finalstand.game.ui.UI;

import java.util.ArrayList;


/**
 * Created by Niall PC on 10/02/2016.
 */
public class PlayState implements Screen {

    private World world;
    private Box2DDebugRenderer b2dr;

    // camera for the game
    private static OrthographicCamera gameCam;
    //used to scale the screen with aspect ratio
    private Viewport viewport;
    private FinalStand game;

    // Used to load the map
    private TmxMapLoader mapLoader;
    // holds the map
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Creep player;

    public static ArrayList<Tower> towers;
    public static Button upgradeButton;
    public static Button sellButton;
    public static boolean displayButtons;
    //BitmapFont font;
    //private TextArea buttonText;
    Texture texture;

    public static UI ui;
    public static OptionTexture optionTexture;
    public static boolean optionChosen;

    public PlayState(FinalStand game) {
        this.game = game;
        texture = new Texture("background.jpg");
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
        displayButtons = false;

        ui = new UI(0, 0, 8, 1);
        optionTexture = new OptionTexture(ui.getTextureWidth(), ui.getTextureHeight(), world);
        optionChosen = false;
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
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        for(Tower tower: towers)
        {
            //tower.checkPressed();
            game.batch.draw(tower.getCurrentTexture(), tower.getPosition().x, tower.getPosition().y, tower.getCurrentTexture().getWidth() / FinalStand.PPM, tower.getCurrentTexture().getHeight() / FinalStand.PPM);
        }

        //render UI
        game.batch.draw(ui.getBackground(), ui.getPosition().x, ui.getPosition().y, ui.getWidth(), ui.getHeight());
        game.batch.draw(ui.getOption1Texture(), ui.getOption1Pos().x, ui.getOption1Pos().y, ui.getTextureWidth(), ui.getTextureHeight());
        game.batch.draw(ui.getOption2Texture(), ui.getOption2Pos().x, ui.getOption2Pos().y, ui.getTextureWidth(), ui.getTextureHeight());
        game.batch.draw(ui.getOption3Texture(), ui.getOption3Pos().x, ui.getOption3Pos().y, ui.getTextureWidth(), ui.getTextureHeight());
        game.batch.draw(ui.getOption4Texture(), ui.getOption4Pos().x, ui.getOption4Pos().y, ui.getTextureWidth(), ui.getTextureHeight());

        if(displayButtons == true)
        {
            upgradeButton.update();
            sellButton.update();
            /*game.batch.draw(upgradeButton.getButtonTexture(), upgradeButton.getPosition().x, upgradeButton.getPosition().y,
                    upgradeButton.getWidth(), upgradeButton.getHeight());
            game.batch.draw(sellButton.getButtonTexture(), sellButton.getPosition().x, sellButton.getPosition().y,
                    sellButton.getWidth(), sellButton.getHeight());*/
            upgradeButton.getButtonSprite().draw(game.batch);
            sellButton.getButtonSprite().draw(game.batch);
            //font.draw(game.batch, upgradeButton.getButtonText(), upgradeButton.getPosition().x, upgradeButton.getPosition().y);
        }
        checkTowerPressed();

        if(optionChosen == true)
        {
            optionTexture.update();
            game.batch.draw(optionTexture.getTexture(), optionTexture.getPosition().x, optionTexture.getPosition().y, ui.getTextureWidth(), ui.getTextureHeight());
        }
        game.batch.end();

        if(Gdx.input.justTouched())
        {
            ui.optionClicked(getWorldMousePos());
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

    }

    public void update() {
        handleInput();
        gameCam.update();


        //dispose of resources that are not used anymore
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

    public static Vector3 getWorldMousePos() {
        return gameCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

    public void checkTowerPressed()
    {
        if(Gdx.input.justTouched())
        {
            boolean towerPressed = false;
            Vector3 mouse = PlayState.getWorldMousePos();
            for(Tower tower : towers) {
                if (mouse.x > tower.getPosition().x && mouse.x < tower.getPosition().x + (tower.getCurrentTexture().getWidth() / FinalStand.PPM) &&
                    mouse.y > tower.getPosition().y && mouse.y < tower.getPosition().y + (tower.getCurrentTexture().getHeight() / FinalStand.PPM))
                {
                    tower.TowerOptions();
                    towerPressed = true;
                }
            }
            if(!towerPressed)
            {
                displayButtons = false;
            }
        }
    }
}
