package com.finalstand.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;
import com.finalstand.game.buttons.Button;
import com.finalstand.game.buttons.ExitButton;
import com.finalstand.game.buttons.ResumeButton;
import com.finalstand.game.hud.Hud;
import com.finalstand.game.sprites.creeps.BasicCreep;
import com.finalstand.game.sprites.creeps.BossCreep;
import com.finalstand.game.sprites.creeps.Creep;
import com.finalstand.game.sprites.creeps.HeavyCreep;
import com.finalstand.game.sprites.creeps.MediumCreep;
import com.finalstand.game.sprites.projectiles.Projectile;
import com.finalstand.game.sprites.towers.AOETower;
import com.finalstand.game.sprites.towers.DOTTower;
import com.finalstand.game.sprites.towers.LaserTower;
import com.finalstand.game.sprites.towers.SingleShotTower;
import com.finalstand.game.sprites.towers.Tower;
import com.finalstand.game.tools.B2WorldCreator;
import com.finalstand.game.tools.Waypoint;
import com.finalstand.game.tools.WorldContactListener;
import com.finalstand.game.traps.Trap;
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

        public static ArrayList<Creep> creeps;
        public static ArrayList<Creep> spawnableCreeps;
        private boolean keepSpawning;
        private int creepsSpawned;
        private int elapsed;

        private Hud hud;


        public static Array<Waypoint> waypoints;

        public static ArrayList<Tower> towers;
        public static Button upgradeButton;
        public static Button sellButton;
        public static boolean displayButtons;

        public static ArrayList<Projectile> projectiles;

        public static UI ui;
        public static OptionTexture optionTexture;
        public static boolean optionChosen;

        public enum State {
            EXIT_GAME_CONFIRMATION,
            RUN,
            BOSS,
            PAUSE,
            PLANNING_PHASE,
            VICTORY
        }
        private State state;

        private Texture background;
        private ExitButton exitButton;
        private ResumeButton resumeButton;
        private Vector2 exitConfPosition;

        private int plannignPhaseCounter;

        private Sprite play;
        private Sprite pause;

        private float challengerRating;

        private boolean firstRoundDone;

        private BossCreep bossCreep;

        private Texture base;
        private Vector2 basePos;
        private Vector2 baseDimensions;

        private boolean run;

        private Array<Body> bodies = new Array<Body>();

        public static ArrayList<Trap> traps;

        public PlayScreen(FinalStand game) {
            this.game = game;
            run = false;
            state = State.PLANNING_PHASE;
            firstRoundDone = false;

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

            hud = new Hud(game.round, game.mapNumber, game.health, game.score);

            challengerRating = game.mapNumber * 10 + game.round;

            creeps = new ArrayList<Creep>();
            creeps = randomCreeps(challengerRating);
            spawnableCreeps = new ArrayList<Creep>();
            spawnableCreeps.add(creeps.get(0));
            creepsSpawned = 0;
            keepSpawning = true;

            towers = new ArrayList<Tower>();
            displayButtons = false;

            background = new Texture("screens/menu.png");
            exitConfPosition = new Vector2((FinalStand.V_WIDTH / 2) / FinalStand.PPM, (FinalStand.V_HEIGHT / 2) / FinalStand.PPM);
            resumeButton = new ResumeButton("screens/resume.png", exitConfPosition.x, exitConfPosition.y, 50 / FinalStand.PPM, 25 / FinalStand.PPM);
            exitButton = new ExitButton("screens/exit.png", exitConfPosition.x, exitConfPosition.y + (50 / FinalStand.PPM), 50 / FinalStand.PPM, 25 / FinalStand.PPM);

            elapsed = 0;
            plannignPhaseCounter = 0;

            play = new Sprite(new Texture("screens/play.png"));
            pause = new Sprite(new Texture("screens/pause.png"));

            play.setPosition(5 / FinalStand.PPM, (FinalStand.V_HEIGHT / 4) / FinalStand.PPM);
            pause.setPosition(5 / FinalStand.PPM, (FinalStand.V_HEIGHT / 4) / FinalStand.PPM);

            bossCreep = new BossCreep(10, 360, world);
            base = new Texture("base.png");
            basePos = waypoints.get(waypoints.size - 1).getPos();
            baseDimensions = waypoints.get(waypoints.size - 1).getBaseDimensions();

            projectiles = new ArrayList<Projectile>();

            ui = new UI(0, 0, (FinalStand.V_WIDTH / 2)/ FinalStand.PPM, (FinalStand.V_HEIGHT / 6) / FinalStand.PPM);
            optionTexture = new OptionTexture(world);
            optionChosen = false;

            traps = new ArrayList<Trap>();
    }

    @Override
    public void show() {
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map1c.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FinalStand.PPM);
    }

    @Override
    public void render(float delta) {

        if(plannignPhaseCounter >= 1000) {
            if(game.mapNumber == 4) {
                setGameState(State.BOSS);
                plannignPhaseCounter = 0;
            } else {
                plannignPhaseCounter = 0;
                setGameState(State.RUN);
            }
        }

        if(firstRoundDone) {
            update();
        }

        if(game.health <= 0) {
            game.setScreen(new FailureScreen(game));
        }

        switch (state) {
            case RUN: {
//                    update();
                if(Gdx.input.justTouched()) {
                    Vector3 mouse = getWorldMousePos();
                    if (mouse.x > pause.getX() && mouse.x < pause.getX() + 20 / FinalStand.PPM &&
                            mouse.y > pause.getY() && mouse.y < pause.getY() + 10 / FinalStand.PPM) {
                        setGameState(State.PAUSE);
                    }
                }

                // Clearing the screen
                Gdx.gl.glClearColor(1, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                //renders the map
                renderer.render();
                game.batch.setProjectionMatrix(gameCam.combined);
                game.batch.begin();
                handleInput();
                world.step(1 / 60f, 6, 2);
                gameCam.update();
//                game.batch.begin();
                world.getBodies(bodies);
                for(Body body : bodies) {
                    if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
                        Sprite ssprite = (Sprite) body.getUserData();
                        ssprite.setPosition(body.getPosition().x - ssprite.getWidth() / 2, body.getPosition().y - ssprite.getHeight() / 2);
                        ssprite.draw(game.batch);
                    }
                }

                for (int i = 0 ; i < spawnableCreeps.size() ; i ++) {
//                    spawnableCreeps.get(i).render(game.batch);
                    spawnableCreeps.get(i).update();
                }

                if (keepSpawning && elapsed > 100) {
                    System.out.println("Before spawnCreep " + creepsSpawned);
                    spawnNewCreep();
                    elapsed = 0;
                }
//                game.batch.end();
//                renderGame();
                renderer.setView(gameCam);
                game.batch.draw(pause.getTexture(), pause.getX(), pause.getY(), 20 / FinalStand.PPM, 10 / FinalStand.PPM);
                game.batch.draw(base, 100 / FinalStand.PPM, 100 / FinalStand.PPM, baseDimensions.x, baseDimensions.y);
                elapsed++;

                //rendering projectiles
                for (int counter = 0; counter < projectiles.size(); counter++) {
                    projectiles.get(counter).getSprite().setOriginCenter();
                    projectiles.get(counter).getSprite().setRotation(projectiles.get(counter).getAngle() - 180);
                    projectiles.get(counter).getSprite().draw(game.batch);
                    projectiles.get(counter).update();
                }

                //rendering towers
                for(Tower tower: towers)
                {
                    tower.update();
                    tower.getTowerSprite().setOriginCenter();
                    tower.getTowerSprite().setRotation(tower.getTowerAngle() - 180);
                    tower.getTowerSprite().draw(game.batch);
                }

                //render UI
                game.batch.draw(ui.getBackground(), ui.getPosition().x, ui.getPosition().y, ui.getWidth(), ui.getHeight());
                game.batch.draw(ui.getOption1Texture(), ui.getOption1Pos().x, ui.getOption1Pos().y, SingleShotTower.size.x, SingleShotTower.size.y);
                game.batch.draw(ui.getOption2Texture(), ui.getOption2Pos().x, ui.getOption2Pos().y, AOETower.size.x, AOETower.size.y);
                game.batch.draw(ui.getOption3Texture(), ui.getOption3Pos().x, ui.getOption3Pos().y, DOTTower.size.x, DOTTower.size.y);
                game.batch.draw(ui.getOption4Texture(), ui.getOption4Pos().x, ui.getOption4Pos().y, LaserTower.size.x, LaserTower.size.y);
                game.batch.draw(ui.getOption5Texture(), ui.getOption5Pos().x, ui.getOption5Pos().y, SingleShotTower.size.x, SingleShotTower.size.y);
                game.batch.draw(ui.getOption6Texture(), ui.getOption6Pos().x, ui.getOption6Pos().y, AOETower.size.x, AOETower.size.y);
                game.batch.draw(ui.getOption7Texture(), ui.getOption7Pos().x, ui.getOption7Pos().y, DOTTower.size.x, DOTTower.size.y);
                game.batch.draw(ui.getOption8Texture(), ui.getOption8Pos().x, ui.getOption8Pos().y, LaserTower.size.x, LaserTower.size.y);

                if(displayButtons == true)
                {
                    upgradeButton.update();
                    sellButton.update();

                    upgradeButton.getButtonSprite().draw(game.batch);
                    sellButton.getButtonSprite().draw(game.batch);

//            upgradeButton.getButtonLabel().draw(game.batch, 1);

//            upgradeButton.getBitmapFonttext().draw(game.batch, "hello", upgradeButton.getPosition().x, upgradeButton.getPosition().y);
                }
                checkTowerPressed();

                if(optionChosen == true)
                {
                    optionTexture.update();
                    optionTexture.getSprite().draw(game.batch);
                }
//                game.batch.draw(base, 100 / FinalStand.PPM, 100 / FinalStand.PPM, baseDimensions.x, baseDimensions.y);
                game.batch.end();

                if(Gdx.input.justTouched())
                {
                    ui.optionClicked(getWorldMousePos());
                }

                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
//                hud.update(game.round, game.mapNumber, game.health, game.score);
                hud.stage.draw();
                if(spawnableCreeps.size() == 0) {
                    firstRoundDone = true;
                }
            } break;

            // TODO(niall) Make the positions of the button look right but the functionality is done
            // TODO(niall) add a exit to mainmenu option
            case EXIT_GAME_CONFIRMATION: {
                if(Gdx.input.justTouched()) {
                    Vector3 mouse = getWorldMousePos();
                    if (mouse.x > resumeButton.getPosition().x && mouse.x < resumeButton.getPosition().x + resumeButton.getPosition().x &&
                            mouse.y > resumeButton.getPosition().y && mouse.y < resumeButton.getPosition().y + resumeButton.getHeight()) {
                        setGameState(State.RUN);
                        run = true;
                    }
                }
                exitButton.update();

                game.batch.setProjectionMatrix(gameCam.combined);
                game.batch.begin();
                game.batch.draw(getBackground(), getExitConfPosition().x - 50 / FinalStand.PPM, getExitConfPosition().y - 100 / FinalStand.PPM,  100 / FinalStand.PPM, 200 / FinalStand.PPM);
                game.batch.draw(resumeButton.getButtonTexture(), resumeButton.getPosition().x - 50 / FinalStand.PPM, resumeButton.getPosition().y, resumeButton.getWidth(), resumeButton.getHeight());
                game.batch.draw(exitButton.getButtonTexture(), exitButton.getPosition().x, exitButton.getPosition().y, exitButton.getWidth(), exitButton.getHeight());
                game.batch.end();

            } break;

            //During this phase of the game the player will be aloud to place towers and traps before the creeps start to spawn
            case PLANNING_PHASE: {
                if(Gdx.input.justTouched()) {
                    Vector3 mouse = getWorldMousePos();
                    if (mouse.x > play.getX() && mouse.x < play.getX() + 20 / FinalStand.PPM &&
                            mouse.y > play.getY() && mouse.y < play.getY() + 10 / FinalStand.PPM) {

                        plannignPhaseCounter = 1000;
                        run = true;
                    }
                }
                Gdx.gl.glClearColor(1, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                //renders the map
                renderer.render();
                //renders the debug lines for box2d
                b2dr.render(world, gameCam.combined);
                game.batch.setProjectionMatrix(gameCam.combined);
                game.batch.begin();
                handleInput();
                world.step(1 / 60f, 6, 2);
                gameCam.update();
                renderer.setView(gameCam);
                game.batch.draw(base, basePos.x, basePos.y, baseDimensions.x, baseDimensions.y);
                elapsed++;
                //rendering projectiles
                for (int counter = 0; counter < projectiles.size(); counter++) {
                    projectiles.get(counter).getSprite().setOriginCenter();
                    projectiles.get(counter).getSprite().setRotation(projectiles.get(counter).getAngle() - 180);
                    projectiles.get(counter).getSprite().draw(game.batch);
                    projectiles.get(counter).update();
                }

                //rendering towers
                for(Tower tower: towers)
                {
                    tower.update();
                    tower.getTowerSprite().setOriginCenter();
                    tower.getTowerSprite().setRotation(tower.getTowerAngle() - 180);
                    tower.getTowerSprite().draw(game.batch);
                }

                //render UI
                game.batch.draw(ui.getBackground(), ui.getPosition().x, ui.getPosition().y, ui.getWidth(), ui.getHeight());
                game.batch.draw(ui.getOption1Texture(), ui.getOption1Pos().x, ui.getOption1Pos().y, SingleShotTower.size.x, SingleShotTower.size.y);
                game.batch.draw(ui.getOption2Texture(), ui.getOption2Pos().x, ui.getOption2Pos().y, AOETower.size.x, AOETower.size.y);
                game.batch.draw(ui.getOption3Texture(), ui.getOption3Pos().x, ui.getOption3Pos().y, DOTTower.size.x, DOTTower.size.y);
                game.batch.draw(ui.getOption4Texture(), ui.getOption4Pos().x, ui.getOption4Pos().y, LaserTower.size.x, LaserTower.size.y);
                game.batch.draw(ui.getOption5Texture(), ui.getOption5Pos().x, ui.getOption5Pos().y, SingleShotTower.size.x, SingleShotTower.size.y);
                game.batch.draw(ui.getOption6Texture(), ui.getOption6Pos().x, ui.getOption6Pos().y, AOETower.size.x, AOETower.size.y);
                game.batch.draw(ui.getOption7Texture(), ui.getOption7Pos().x, ui.getOption7Pos().y, DOTTower.size.x, DOTTower.size.y);
                game.batch.draw(ui.getOption8Texture(), ui.getOption8Pos().x, ui.getOption8Pos().y, LaserTower.size.x, LaserTower.size.y);

                if(displayButtons == true)
                {
                    upgradeButton.update();
                    sellButton.update();

                    upgradeButton.getButtonSprite().draw(game.batch);
                    sellButton.getButtonSprite().draw(game.batch);

//            upgradeButton.getButtonLabel().draw(game.batch, 1);

//            upgradeButton.getBitmapFonttext().draw(game.batch, "hello", upgradeButton.getPosition().x, upgradeButton.getPosition().y);
                }
                checkTowerPressed();

                if(optionChosen == true)
                {
                    optionTexture.update();
                    optionTexture.getSprite().draw(game.batch);
                }
//                game.batch.draw(base, 100 / FinalStand.PPM, 100 / FinalStand.PPM, baseDimensions.x, baseDimensions.y);
                game.batch.end();

                if(Gdx.input.justTouched())
                {
                    ui.optionClicked(getWorldMousePos());
                }
                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
                hud.update(game.round, game.mapNumber, game.health, game.score);
                hud.stage.draw();
                plannignPhaseCounter++;
            } break;

            case PAUSE: {
                if(Gdx.input.justTouched()) {
                    Vector3 mouse = getWorldMousePos();
                    if (mouse.x > play.getX() && mouse.x < play.getX() + 20 / FinalStand.PPM &&
                            mouse.y > play.getY() && mouse.y < play.getY() + 10 / FinalStand.PPM) {
                        setGameState(State.RUN);
                    }
                }

                game.batch.begin();
                game.batch.draw(play.getTexture(), play.getX(), play.getY(), 20 / FinalStand.PPM, 10 / FinalStand.PPM);
                game.batch.end();
            }

            case BOSS: {
                if(Gdx.input.justTouched()) {
                    Vector3 mouse = getWorldMousePos();
                    if (mouse.x > pause.getX() && mouse.x < pause.getX() + 20 / FinalStand.PPM &&
                            mouse.y > pause.getY() && mouse.y < pause.getY() + 10 / FinalStand.PPM) {
                        setGameState(State.PAUSE);
                    }
                }
                // Clearing the screen
                Gdx.gl.glClearColor(1, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                //renders the map
                renderer.render();

                //renders the debug lines for box2d
        b2dr.render(world, gameCam.combined);


                game.batch.setProjectionMatrix(gameCam.combined);
                game.batch.begin();
                handleInput();

                world.step(1 / 60f, 6, 2);

                gameCam.update();

                bossCreep.render(game.batch);
                bossCreep.update();

//        player.update();
                renderer.setView(gameCam);

                game.batch.draw(pause.getTexture(), pause.getX(), pause.getY(), 20 / FinalStand.PPM, 10 / FinalStand.PPM);

                elapsed++;

                //rendering projectiles
                for (int counter = 0; counter < projectiles.size(); counter++) {
                    projectiles.get(counter).getSprite().setOriginCenter();
                    projectiles.get(counter).getSprite().setRotation(projectiles.get(counter).getAngle() - 180);
                    projectiles.get(counter).getSprite().draw(game.batch);
                    projectiles.get(counter).update();
                }

                //rendering towers
                for(Tower tower: towers)
                {
                    tower.update();
                    tower.getTowerSprite().setOriginCenter();
                    tower.getTowerSprite().setRotation(tower.getTowerAngle() - 180);
                    tower.getTowerSprite().draw(game.batch);
                }

                //render UI
                game.batch.draw(ui.getBackground(), ui.getPosition().x, ui.getPosition().y, ui.getWidth(), ui.getHeight());
                game.batch.draw(ui.getOption1Texture(), ui.getOption1Pos().x, ui.getOption1Pos().y, SingleShotTower.size.x, SingleShotTower.size.y);
                game.batch.draw(ui.getOption2Texture(), ui.getOption2Pos().x, ui.getOption2Pos().y, AOETower.size.x, AOETower.size.y);
                game.batch.draw(ui.getOption3Texture(), ui.getOption3Pos().x, ui.getOption3Pos().y, DOTTower.size.x, DOTTower.size.y);
                game.batch.draw(ui.getOption4Texture(), ui.getOption4Pos().x, ui.getOption4Pos().y, LaserTower.size.x, LaserTower.size.y);
                game.batch.draw(ui.getOption5Texture(), ui.getOption5Pos().x, ui.getOption5Pos().y, SingleShotTower.size.x, SingleShotTower.size.y);
                game.batch.draw(ui.getOption6Texture(), ui.getOption6Pos().x, ui.getOption6Pos().y, AOETower.size.x, AOETower.size.y);
                game.batch.draw(ui.getOption7Texture(), ui.getOption7Pos().x, ui.getOption7Pos().y, DOTTower.size.x, DOTTower.size.y);
                game.batch.draw(ui.getOption8Texture(), ui.getOption8Pos().x, ui.getOption8Pos().y, LaserTower.size.x, LaserTower.size.y);

                if(displayButtons == true)
                {
                    upgradeButton.update();
                    sellButton.update();

                    upgradeButton.getButtonSprite().draw(game.batch);
                    sellButton.getButtonSprite().draw(game.batch);

//            upgradeButton.getButtonLabel().draw(game.batch, 1);

//            upgradeButton.getBitmapFonttext().draw(game.batch, "hello", upgradeButton.getPosition().x, upgradeButton.getPosition().y);
                }
                checkTowerPressed();

                if(optionChosen == true)
                {
                    optionTexture.update();
                    optionTexture.getSprite().draw(game.batch);
                }
//                game.batch.draw(base, 100 / FinalStand.PPM, 100 / FinalStand.PPM, baseDimensions.x, baseDimensions.y);
                game.batch.end();

                if(Gdx.input.justTouched())
                {
                    ui.optionClicked(getWorldMousePos());
                }


                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
                hud.update(game.round, game.mapNumber, game.health, game.score);
                hud.stage.draw();



                if(FinalStand.gameOver) {
                    game.setScreen(new FailureScreen(game));
                }
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
//        background.dispose();
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
//            game.setScreen(new MenuScreen(game, this));
        }
    }

    public void spawnNewCreep() {
        if(creepsSpawned == creeps.size()) {
            keepSpawning = false;
            return;
        }
        spawnableCreeps.add(creeps.get(creepsSpawned));
        creepsSpawned++;
    }

    public void setGameState(State s) {
        this.state = s;
    }


    public ArrayList<Creep> randomCreeps(float challengeRating) {
        ArrayList<Creep> levelCreeps = new ArrayList<Creep>();
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
                if (mouse.x > tower.getPosition().x && mouse.x < tower.getPosition().x + tower.getTowerSprite().getWidth() &&
                        mouse.y > tower.getPosition().y && mouse.y < tower.getPosition().y + tower.getTowerSprite().getHeight())
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


    public void update() {
        // there is no more creeps in the wave
        if(game.round == game.roundsPerMap) {
            // Move to a new map
            System.out.println("i am here");
            game.mapNumber ++;
            game.round = 1;
            firstRoundDone = false;
            waypoints.clear();
            towers.clear();
            for(Creep c : creeps) {
                world.destroyBody(c.getB2Body());
            }
            spawnableCreeps.clear();
            creeps.clear();

        }

        if(spawnableCreeps.size() == 0) {

            game.round ++;
            elapsed = 0;
            for(Creep c : creeps) {
                world.destroyBody(c.getB2Body());
            }
            creeps.clear();
            spawnableCreeps.clear();
            challengerRating = game.mapNumber * 10 + game.round;

            creeps = randomCreeps(challengerRating);
            spawnableCreeps.add(creeps.get(0));

            creepsSpawned = 0;
            keepSpawning = true;
            plannignPhaseCounter = 0;

            setGameState(State.PLANNING_PHASE);
        }
    }
}
