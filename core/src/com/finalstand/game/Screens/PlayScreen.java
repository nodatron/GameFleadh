package com.finalstand.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;
import com.finalstand.game.buttons.Button;
import com.finalstand.game.buttons.ExitButton;
import com.finalstand.game.buttons.ResumeButton;
import com.finalstand.game.buttons.UpgradeButton;
import com.finalstand.game.hud.Hud;
import com.finalstand.game.sprites.creeps.BasicCreep;
import com.finalstand.game.sprites.creeps.Creep;
import com.finalstand.game.sprites.creeps.HeavyCreep;
import com.finalstand.game.sprites.creeps.MediumCreep;
import com.finalstand.game.sprites.towers.AOETower;
import com.finalstand.game.sprites.towers.LaserTower;
import com.finalstand.game.sprites.towers.SingleShotTower;
import com.finalstand.game.sprites.towers.Tower;
import com.finalstand.game.tools.B2WorldCreator;
import com.finalstand.game.tools.Waypoint;
import com.finalstand.game.tools.WorldContactListener;
import com.finalstand.game.ui.OptionTexture;
import com.finalstand.game.ui.UI;

import java.util.ArrayList;

/**
 * Created by Niall PC on 20/02/2016.
 */
public class PlayScreen implements Screen {
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

//    private Creep player;

    private Array<Creep> creeps;
    private Array<Creep> spawnableCreeps;
    private boolean keepSpawning;
    private int creepsSpawned;
    private int elapsed;

    private Hud hud;

//    private ArrayList<Tower> towers;
//    Texture texture;

    public static Array<Waypoint> waypoints;

    public static ArrayList<Tower> towers;
    public static Button upgradeButton;
    public static Button sellButton;
    public static boolean displayButtons;

    public static UI ui;
    public static OptionTexture optionTexture;
    public static boolean optionChosen;

    public enum State
    {
        EXIT_GAME_CONFIRMATION,
        RUN,
        RESUME,
        PAUSE,
        PLANNING_PHASE
    }
    private State state;

    private Texture background;
    private ExitButton exitButton;
    private ResumeButton resumeButton;
    private Vector2 exitConfPosition;

    public PlayScreen(FinalStand game){
        this.game = game;
        state = State.RUN;
        //      texture = new Texture("background.jpg");
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map1c.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FinalStand.PPM);

        // centers the camera
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);


        waypoints = new Array<Waypoint>();

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(world, map);
        world.setContactListener(new WorldContactListener());

//        player = new BasicCreep(10, 360, world);

        creeps = new Array<Creep>();
        creeps = randomCreeps(5);
        spawnableCreeps = new Array<Creep>();
        spawnableCreeps.add(creeps.get(0));
        creepsSpawned = 0;
        keepSpawning = true;

        towers = new ArrayList<Tower>();
        displayButtons = false;

        ui = new UI(0, 0, 8, 1);
        optionTexture = new OptionTexture(ui.getTextureWidth(), ui.getTextureHeight(), world);
        optionChosen = false;

//        towers.add(new SingleShotTower(0,0));
//        towers.add(new AOETower((FinalStand.V_WIDTH / 6) / FinalStand.PPM, 0));
        //towers.add(new DOTTower(game.V_WIDTH / 3, 0));
//        towers.add(new LaserTower((FinalStand.V_WIDTH / 3) / FinalStand.PPM, 0));

        hud = new Hud(game.batch);

        background = new Texture("screens/menu.png");
        exitConfPosition = new Vector2((FinalStand.V_WIDTH / 2) / FinalStand.PPM, (FinalStand.V_HEIGHT / 2) / FinalStand.PPM);
        resumeButton = new ResumeButton("screens/playbutton.png", exitConfPosition.x, exitConfPosition.y, 50 / FinalStand.PPM, 25 / FinalStand.PPM);
        exitButton = new ExitButton("screens/controlbutton.png", exitConfPosition.x, exitConfPosition.y + (50 / FinalStand.PPM), 50 / FinalStand.PPM, 25 / FinalStand.PPM);

        elapsed = 0;
    }

    @Override
    public void show() {
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map1c.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FinalStand.PPM);
    }

    @Override
    public void render(float delta) {

        switch (state) {
            case RUN: {
                // Clearing the screen
                Gdx.gl.glClearColor(1, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                //renders the map
                renderer.render();

                //renders the debug lines for box2d
//        b2dr.render(world, gameCam.combined);


                game.batch.setProjectionMatrix(gameCam.combined);
                game.batch.begin();
                handleInput();

                world.step(1 / 60f, 6, 2);

                gameCam.update();

                for (Creep c : spawnableCreeps) {
                    c.update();
                    c.render(game.batch);
                }

                if (keepSpawning && elapsed > 100) {
                    spawnNewCreep();
                    elapsed = 0;
                }

//        player.update();
                renderer.setView(gameCam);


                elapsed++;

                for (Tower tower : towers) {
                    //tower.checkPressed();
                    game.batch.draw(tower.getCurrentTexture(), tower.getPosition().x, tower.getPosition().y, tower.getCurrentTexture().getWidth() / FinalStand.PPM, tower.getCurrentTexture().getHeight() / FinalStand.PPM);
                }

                //render UI
                game.batch.draw(ui.getBackground(), ui.getPosition().x, ui.getPosition().y, ui.getWidth(), ui.getHeight());
                game.batch.draw(ui.getOption1Texture(), ui.getOption1Pos().x, ui.getOption1Pos().y, ui.getTextureWidth(), ui.getTextureHeight());
                game.batch.draw(ui.getOption2Texture(), ui.getOption2Pos().x, ui.getOption2Pos().y, ui.getTextureWidth(), ui.getTextureHeight());
                game.batch.draw(ui.getOption3Texture(), ui.getOption3Pos().x, ui.getOption3Pos().y, ui.getTextureWidth(), ui.getTextureHeight());
                game.batch.draw(ui.getOption4Texture(), ui.getOption4Pos().x, ui.getOption4Pos().y, ui.getTextureWidth(), ui.getTextureHeight());

                if (displayButtons == true) {
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

                if (optionChosen == true) {
                    optionTexture.update();
                    game.batch.draw(optionTexture.getTexture(), optionTexture.getPosition().x, optionTexture.getPosition().y, ui.getTextureWidth(), ui.getTextureHeight());
                }
                game.batch.end();

                if (Gdx.input.justTouched()) {
                    ui.optionClicked(getWorldMousePos());
                }


                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
                hud.stage.draw();
            } break;

            // TODO(niall) Make the positions of the button look right but the functionality is done
            case EXIT_GAME_CONFIRMATION: {
                if(Gdx.input.justTouched()) {
                    Vector3 mouse = getWorldMousePos();
                    if (mouse.x > resumeButton.getPosition().x && mouse.x < resumeButton.getPosition().x + resumeButton.getPosition().x &&
                            mouse.y > resumeButton.getPosition().y && mouse.y < resumeButton.getPosition().y + resumeButton.getHeight()) {
                        setGameState(State.RUN);
                    }
                }
                exitButton.update();
//                Gdx.gl.glClearColor(1, 0, 0, 1);
//                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                game.batch.setProjectionMatrix(gameCam.combined);
                game.batch.begin();
                game.batch.draw(getBackground(), getExitConfPosition().x - 50 / FinalStand.PPM, getExitConfPosition().y - 100 / FinalStand.PPM,  100 / FinalStand.PPM, 200 / FinalStand.PPM);
                game.batch.draw(resumeButton.getButtonTexture(), resumeButton.getPosition().x - 50 / FinalStand.PPM, resumeButton.getPosition().y, resumeButton.getWidth(), resumeButton.getHeight());
                game.batch.draw(exitButton.getButtonTexture(), exitButton.getPosition().x, exitButton.getPosition().y, exitButton.getWidth(), exitButton.getHeight());
                game.batch.end();

            } break;

            case PLANNING_PHASE: {

            } break;
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
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
    }

    public Texture getBackground() {
        return background;
    }

    public Vector2 getExitConfPosition() {
        return exitConfPosition;
    }

    public void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.M)) {
            game.setScreen(new MenuScreen(game, this));
//            dispose();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            setGameState(State.EXIT_GAME_CONFIRMATION);
        }
    }

    public void setGameState(State s) {
        this.state = s;
    }

    public void spawnCreep() {
        Creep creep = new BasicCreep(10, 360, world);
        creeps.add(creep);
    }

    public void spawnNewCreep() {
        if(creepsSpawned == creeps.size) {
            keepSpawning = false;
            return;
        }
        spawnableCreeps.add(creeps.get(creepsSpawned));
        creepsSpawned++;
    }

    public Array<Creep> randomCreeps(float challengeRating) {
        Array<Creep> levelCreeps = new Array<Creep>();
        int randomNumber = 0;
        float currentChallengeRating = 0;

        while(currentChallengeRating < challengeRating) {
            randomNumber = randomWithinRange(0, 100);
            if(challengeRating - currentChallengeRating > 1) {
                if(randomNumber <= 55) {
                    levelCreeps.add(new BasicCreep(10, 360, world));
                    currentChallengeRating += 0.25f;
                } else if(randomNumber <= 85 && randomNumber > 55) {
                    levelCreeps.add(new MediumCreep(10, 360, world));
                    currentChallengeRating += 0.75f;
                } else {
                    levelCreeps.add(new HeavyCreep(10, 360, world));
                    currentChallengeRating += 1.0f;
                }
            } else if(challengeRating - currentChallengeRating >= 0.75) {
                if(randomNumber <= 65) {
                    levelCreeps.add(new BasicCreep(10, 360, world));
                    currentChallengeRating += 0.25f;
                } else if(randomNumber > 65) {
                    levelCreeps.add(new MediumCreep(10, 360, world));
                    currentChallengeRating += 0.75f;
                }
            } else {
                levelCreeps.add(new BasicCreep(10, 360, world));
                currentChallengeRating += 0.25f;
            }
        }

        return levelCreeps;
    }

    public int randomWithinRange(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public static Vector3 getWorldMousePos() {
        return gameCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

    public void checkTowerPressed()
    {
        if(Gdx.input.justTouched())
        {
            boolean towerPressed = false;
            Vector3 mouse = PlayScreen.getWorldMousePos();
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
