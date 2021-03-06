package com.finalstand.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.finalstand.game.buttons.CancelButton;
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
import com.finalstand.game.sprites.traps.Trap;
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

        //variables for the creeps
        public static ArrayList<Creep> creeps;
        private boolean keepSpawning;
        private int creepsSpawned;
        private int elapsed;
        // hud for the game
        private Hud hud;
        // arraylist of the position of the waypoints
        public static Array<Waypoint> waypoints;

        // towers arraylist
        public static ArrayList<Tower> towers;
        // buttons for the playscreem
        public static Button upgradeButton;
        public static Button sellButton;
        public static Button cancelButton;
        public static boolean displayButtons;

        // projectiles for the game
        public static ArrayList<Projectile> projectiles;

        // UI for the game and variables on how to place the towers or traps
        public static UI ui;
        public static OptionTexture optionTexture;
        public static boolean optionChosen;
        // Music for the game
        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundMusic.wav"));

        //states of the game
        public enum State {
            EXIT_GAME_CONFIRMATION,
            RUN,
            BOSS,
            PAUSE,
            PLANNING_PHASE
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

        //base variables
        private Texture base;
        private Vector2 basePos;
        private Vector2 baseDimensions;

        private boolean run;

        //used to draw certain sprites
        private Array<Body> bodies = new Array<Body>();
        // arraylist for the traps
        public static ArrayList<Trap> traps;

        // Starting position of the creeps
        private Vector2 startingPos;
        // filename for the map
        private String mapFileName;


        public PlayScreen(FinalStand game, int round, int mapNumber) {
            this.game = game;
            // making sure the static variables are reset
            game.round = round;
            game.mapNumber = mapNumber;
            switch(game.mapNumber) {
                case 1: { game.score = 600; } break;
                case 2: { game.score = 1000; } break;
                case 3: { game.score = 1500; } break;
                case 4: { game.score = 2500; } break;
            }
            game.health = 10;

            run = false;
            // getting the map
            mapFileName = findMapFile(FinalStand.mapNumber);
            // starts off in the planning state of the game
            state = State.PLANNING_PHASE;
            firstRoundDone = false;
            // creating a new Box2d world
            world = new World(new Vector2(0, 0), true);
            //camera stuff
            gameCam = new OrthographicCamera();
            viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);
            // loading the map
            mapLoader = new TmxMapLoader();
            map = mapLoader.load(mapFileName);
            renderer = new OrthogonalTiledMapRenderer(map, 1 / FinalStand.PPM);
            // centers the camera
            gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

            waypoints = new Array<Waypoint>();
            //debug renderer for the game
            b2dr = new Box2DDebugRenderer();
            //used to make box2d bodies for the road and waypoints
            new B2WorldCreator(world, map);
            // collision detection
            world.setContactListener(new WorldContactListener());
            // hud for the game
            hud = new Hud(game.round, game.mapNumber, game.health, game.score);

            // getting the creeps set up
            challengerRating = game.mapNumber * 10 + game.round;
            startingPos = findCreepStartingPos(FinalStand.mapNumber);
            if(FinalStand.mapNumber == 1) {
                bossCreep = new BossCreep(startingPos.x, startingPos.y, world);
            } else if(FinalStand.mapNumber == 2) {
                bossCreep = new BossCreep(startingPos.x, startingPos.y, world);
            }
            else if (FinalStand.mapNumber == 3 || FinalStand.mapNumber == 4){
                bossCreep = new BossCreep(startingPos.x, startingPos.y, world);
            }
            creeps = new ArrayList<Creep>();
            creeps.add(bossCreep);
            if(game.mapNumber != 4) {
                randomCreeps(challengerRating);
                creepsSpawned = 0;
                keepSpawning = true;
            }

            towers = new ArrayList<Tower>();
            displayButtons = false;
            //buttons and menu for the game
            background = new Texture(Gdx.files.internal("screens/menu.png"));
            exitConfPosition = new Vector2((FinalStand.V_WIDTH / 2) / FinalStand.PPM, (FinalStand.V_HEIGHT / 2) / FinalStand.PPM);
            resumeButton = new ResumeButton("screens/resume.png", exitConfPosition.x, exitConfPosition.y, 50 / FinalStand.PPM, 25 / FinalStand.PPM);
            exitButton = new ExitButton("screens/exit.png", exitConfPosition.x, exitConfPosition.y + (50 / FinalStand.PPM), 50 / FinalStand.PPM, 25 / FinalStand.PPM);

            elapsed = 0;
            plannignPhaseCounter = 0;

            //play and pause button for the game
            play = new Sprite(new Texture(Gdx.files.internal("screens/play.png")));
            pause = new Sprite(new Texture(Gdx.files.internal("screens/pause.png")));

            play.setPosition(((FinalStand.V_WIDTH / 2) + 20) / FinalStand.PPM, (FinalStand.V_HEIGHT / 10) / FinalStand.PPM);
            play.setSize(20 / FinalStand.PPM, 10 / FinalStand.PPM);
            pause.setPosition(((FinalStand.V_WIDTH / 2) + 40) / FinalStand.PPM, (FinalStand.V_HEIGHT / 10) / FinalStand.PPM);
            pause.setSize(20 / FinalStand.PPM, 10 / FinalStand.PPM);

            // base variables
            base = new Texture(Gdx.files.internal("base.png"));
            basePos = waypoints.get(waypoints.size - 1).getPos();
            baseDimensions = waypoints.get(waypoints.size - 1).getBaseDimensions();

            projectiles = new ArrayList<Projectile>();

            // UI variables
            ui = new UI(0, 0, (FinalStand.V_WIDTH / 2)/ FinalStand.PPM, (FinalStand.V_HEIGHT / 6) / FinalStand.PPM);
            optionTexture = new OptionTexture(world);
            optionChosen = false;
            cancelButton = new CancelButton();

            traps = new ArrayList<Trap>();

        }

    //called when the screen is shown
    @Override
    public void show() {
        // music reset and map reset
        backgroundMusic.play();
        backgroundMusic.setLooping(true);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapFileName);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FinalStand.PPM);
    }

    // main game loop
    @Override
    public void render(float delta) {
        // Clearing the screen to a certain colour
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // counter for the planning phase
        if(plannignPhaseCounter >= 1000) {
            if(game.mapNumber == 4) {
                setGameState(State.BOSS);
                plannignPhaseCounter = 0;
            } else if(game.mapNumber == 4){
                plannignPhaseCounter = 0;
                setGameState(State.BOSS);
            } else {
                plannignPhaseCounter = 0;
                setGameState(State.RUN);
            }
        }
        // checking user input
        update();

        // checking if the game is over
        if(game.health <= 0) {
            game.setScreen(new FailureScreen(game));
        }

        switch (state) {
            case RUN: {
                // renders most of the game stuff
                renderGame();
                //keep spanning creeps
                if (keepSpawning && elapsed > 40) {
                    spawnNewCreep();
                    elapsed = 0;
                }

                //update for the creeps
                for (int i = 0 ; i < creepsSpawned ; i ++) {
                    if(creeps.get(i).isDead()) {
                        world.destroyBody(creeps.get(i).getB2Body());
                        FinalStand.score += creeps.get(i).getScore();
                        creeps.remove(i);
                        creepsSpawned --;
                    } else {
                        if(!(creeps.get(i) instanceof BossCreep)) {
                            creeps.get(i).update();
                        }
                    }
                }
            } break;

            // when the user clicks to exit the game
            case EXIT_GAME_CONFIRMATION: {
                game.batch.setProjectionMatrix(gameCam.combined);
                game.batch.begin();
                game.batch.draw(getBackground(), getExitConfPosition().x - 50 / FinalStand.PPM, getExitConfPosition().y - 100 / FinalStand.PPM,  100 / FinalStand.PPM, 200 / FinalStand.PPM);
                game.batch.draw(resumeButton.getButtonTexture(), resumeButton.getPosition().x - 50 / FinalStand.PPM, resumeButton.getPosition().y, resumeButton.getWidth(), resumeButton.getHeight());
                game.batch.draw(exitButton.getButtonTexture(), exitButton.getPosition().x, exitButton.getPosition().y, exitButton.getWidth(), exitButton.getHeight());
                game.batch.end();
            } break;

            //During this phase of the game the player will be aloud to place towers and traps before the creeps start to spawn
            case PLANNING_PHASE: {
                renderGame();
                plannignPhaseCounter++;
            } break;

            // when the user presses the pause button
            case PAUSE: {
                game.setScreen(new MenuScreen(game, this));
                if(game.mapNumber == 4) {
                    setGameState(State.BOSS);
                } else {
                    setGameState(State.RUN);
                }
            } break;

            //boss round
            case BOSS: {
                renderGame();
                if(bossCreep.isDead()) {
                    world.destroyBody(bossCreep.getB2Body());
                    FinalStand.score += bossCreep.getScore();
                    game.setScreen(new VictoryScreen(game));
                } else {
                    bossCreep.update();
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
        background.dispose();
        backgroundMusic.dispose();
    }

    public Texture getBackground() {
        return background;
    }

    public Vector2 getExitConfPosition() {
        return exitConfPosition;
    }

    //  checking if certain button where pressed
    public void handleInput() {
        Vector3 mouse = getWorldMousePos();
        if(Gdx.input.isKeyPressed(Input.Keys.M)) {
            game.setScreen(new MenuScreen(game, this));
//            dispose();
        }

        if(state == State.RUN || state == State.PLANNING_PHASE || state == State.BOSS ||
                state == State.PAUSE) {
            if (Gdx.input.justTouched()) {
                if (mouse.x > play.getX() && mouse.x < play.getX() + play.getWidth() &&
                        mouse.y > play.getY() + play.getHeight() && mouse.y < play.getY() + (play.getHeight() * 3)) {
                    plannignPhaseCounter = 1000;
                    run = true;
                }

                if (mouse.x > pause.getX() && mouse.x < pause.getX() + 20 / FinalStand.PPM &&
                        mouse.y > pause.getY() + pause.getHeight() && mouse.y < pause.getY() + (pause.getHeight() * 3)) {
                    setGameState(State.PAUSE);
                }
            }
        }

        if(state == State.EXIT_GAME_CONFIRMATION) {
            if(Gdx.input.justTouched()) {
                if (mouse.x > resumeButton.getPosition().x && mouse.x < resumeButton.getPosition().x + resumeButton.getPosition().x &&
                        mouse.y > resumeButton.getPosition().y && mouse.y < resumeButton.getPosition().y + resumeButton.getHeight()) {
                    if(game.mapNumber == 4) {
                        setGameState(State.BOSS);
                    } else {
                        setGameState(State.RUN);
                    }
                    run = true;
                }
            }
            exitButton.update();
        }
    }

    // spawns a new creep in the array of creeps
    public void spawnNewCreep() {
        if(creepsSpawned == creeps.size()) {
            keepSpawning = false;
            return;
        }
        creepsSpawned++;
    }

    //change the state of the game
    public void setGameState(State s) {
        this.state = s;
    }


    // randomly create the list of creeps in the game
    public void randomCreeps(float challengeRating) {
        int randomNumber = 0;
        float currentChallengeRating = 0;

        while(currentChallengeRating < challengeRating) {
            randomNumber = randomWithinRange(0, 100);
            if(challengeRating - currentChallengeRating > 1) {
                if(randomNumber <= 55) {
                    creeps.add(new BasicCreep(startingPos.x, startingPos.y, world));
                    currentChallengeRating += 0.25f;
                } else if(randomNumber <= 85 && randomNumber > 55) {
                    creeps.add(new MediumCreep(startingPos.x, startingPos.y, world));
                    currentChallengeRating += 0.75f;
                } else {
                    creeps.add(new HeavyCreep(startingPos.x, startingPos.y, world));
                    currentChallengeRating += 1.0f;
                }
            } else if(challengeRating - currentChallengeRating >= 0.75) {
                if(randomNumber <= 65) {
                    creeps.add(new BasicCreep(startingPos.x, startingPos.y, world));
                    currentChallengeRating += 0.25f;
                } else if(randomNumber > 65) {
                    creeps.add(new MediumCreep(startingPos.x, startingPos.y, world));
                    currentChallengeRating += 0.75f;
                }
            } else {
                creeps.add(new BasicCreep(startingPos.x, startingPos.y, world));
                currentChallengeRating += 0.25f;
            }
        }
    }
    public int randomWithinRange(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public static Vector3 getWorldMousePos() {
        return gameCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

    // checks if a placed tower has been clicked
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
        handleInput();

        // there is no more creeps in the wave
        if(game.round == game.roundsPerMap) {
            game.setScreen(new SelectScreen(game));
        }

        // go to next round if the size of the creep array is 1
        if(creeps.size() == 1) {
            game.round ++;
            elapsed = 0;
//            creeps.clear();
            for(Projectile p : projectiles) {
                p.setIsDead(true);
            }
//            projectiles.clear();
            challengerRating = game.mapNumber * 10 + game.round;

            randomCreeps(challengerRating);
            keepSpawning = true;
            plannignPhaseCounter = 0;
            FinalStand.score += 100;
            creepsSpawned = 0;
            setGameState(State.PLANNING_PHASE);
        }
    }

    //does all the rendering of the textures for the game
    public void renderGame() {
        renderer.render();
        game.batch.setProjectionMatrix(gameCam.combined);
        world.step(1 / 60f, 6, 2);
        gameCam.update();
        renderer.setView(gameCam);
        //b2dr.render(world, gameCam.combined);
        game.batch.begin();
        game.batch.draw(play.getTexture(), play.getX(), play.getY(), play.getWidth(), play.getHeight());
        game.batch.draw(pause.getTexture(), pause.getX(), pause.getY(), pause.getWidth(), pause.getHeight());
        game.batch.draw(base, basePos.x / FinalStand.PPM, (basePos.y - 16) / FinalStand.PPM, baseDimensions.x * 3, baseDimensions.y * 3);

        world.getBodies(bodies);
        for(int i = 0 ; i < bodies.size ; i ++) {
            if(bodies.get(i).getUserData() != null && bodies.get(i).getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) bodies.get(i).getUserData();
                sprite.setPosition(bodies.get(i).getPosition().x - sprite.getWidth() / 2, bodies.get(i).getPosition().y - sprite.getHeight() / 2);
                sprite.draw(game.batch);
            }
        }

        //rendering projectiles
        for (int counter = 0; counter < projectiles.size(); counter++) {
            if(!projectiles.get(counter).isDead() && state != State.PLANNING_PHASE) {
                projectiles.get(counter).getSprite().setOriginCenter();
                projectiles.get(counter).getSprite().setRotation(projectiles.get(counter).getAngle() - 180);
                projectiles.get(counter).getSprite().draw(game.batch);
                projectiles.get(counter).update();
            } else {
                world.destroyBody(projectiles.get(counter).getB2Body());
                projectiles.remove(counter);
            }
        }

        //rendering towers
        for(int counter = 0; counter < towers.size(); counter++)
        {
            if(!towers.get(counter).isDead()) {
                towers.get(counter).update();
                towers.get(counter).getTowerSprite().setOriginCenter();
                towers.get(counter).getTowerSprite().setRotation(towers.get(counter).getTowerAngle() - 180);
                towers.get(counter).getTowerSprite().draw(game.batch);
            }
            else
            {
                if(towers.get(counter).getRoadboundsHit()) {
                    FinalStand.score += (towers.get(counter).getSellPrice() * 2);
                }
                world.destroyBody(towers.get(counter).getB2Body());
                towers.remove(counter);
            }
        }

        //render UI
        game.batch.draw(ui.getBackground(), ui.getPosition().x, ui.getPosition().y, ui.getWidth(), ui.getHeight());
        game.batch.draw(ui.getOption1Texture(), ui.getOption1Pos().x, ui.getOption1Pos().y, SingleShotTower.size.x, SingleShotTower.size.y);
        game.batch.draw(ui.getOption2Texture(), ui.getOption2Pos().x, ui.getOption2Pos().y, AOETower.size.x, AOETower.size.y);
        game.batch.draw(ui.getOption3Texture(), ui.getOption3Pos().x, ui.getOption3Pos().y, DOTTower.size.x, DOTTower.size.y);
        game.batch.draw(ui.getOption4Texture(), ui.getOption4Pos().x, ui.getOption4Pos().y, LaserTower.size.x, LaserTower.size.y);
        game.batch.draw(ui.getOption5Texture(), ui.getOption5Pos().x, ui.getOption5Pos().y, Trap.trapSize.x * 2, Trap.trapSize.y * 2);
        game.batch.draw(ui.getOption6Texture(), ui.getOption6Pos().x, ui.getOption6Pos().y, Trap.trapSize.x * 2, Trap.trapSize.y * 2);
        game.batch.draw(ui.getOption7Texture(), ui.getOption7Pos().x, ui.getOption7Pos().y, Trap.trapSize.x * 2, Trap.trapSize.y * 2);
        game.batch.draw(ui.getOption8Texture(), ui.getOption8Pos().x, ui.getOption8Pos().y, Trap.trapSize.x * 2, Trap.trapSize.y * 2);

        game.batch.draw(ui.getTag100(), ui.getBoundsWidth() * 0, 0, ui.getBoundsWidth(), ui.getTagHeight());
        game.batch.draw(ui.getTag200(), ui.getBoundsWidth() * 1, 0, ui.getBoundsWidth(), ui.getTagHeight());
        game.batch.draw(ui.getTag250(), ui.getBoundsWidth() * 2, 0, ui.getBoundsWidth(), ui.getTagHeight());
        game.batch.draw(ui.getTag400(), ui.getBoundsWidth() * 3, 0, ui.getBoundsWidth(), ui.getTagHeight());
        game.batch.draw(ui.getTag50(), ui.getBoundsWidth() * 4, 0, ui.getBoundsWidth(), ui.getTagHeight());
        game.batch.draw(ui.getTag100(), ui.getBoundsWidth() * 5, 0, ui.getBoundsWidth(), ui.getTagHeight());
        game.batch.draw(ui.getTag100(), ui.getBoundsWidth() * 6, 0, ui.getBoundsWidth(), ui.getTagHeight());
        game.batch.draw(ui.getTag150(), ui.getBoundsWidth() * 7, 0, ui.getBoundsWidth(), ui.getTagHeight());

        if(displayButtons == true)
        {
            upgradeButton.update();
            sellButton.update();
            upgradeButton.getButtonSprite().draw(game.batch);
            sellButton.getButtonSprite().draw(game.batch);
        }
        checkTowerPressed();

        if(optionChosen == true)
        {
            optionTexture.update();
            optionTexture.getSprite().draw(game.batch);
//            System.out.println(cancelButton.getPosition());
            game.batch.draw(cancelButton.getButtonTexture(), cancelButton.getPosition().x, cancelButton.getPosition().y,
                            cancelButton.getWidth(), cancelButton.getHeight());
        }

        game.batch.end();

        if(Gdx.input.justTouched())
        {
            ui.optionClicked(getWorldMousePos());
        }

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        for(int i = 0 ; i < traps.size() ; i ++) {
            if(traps.get(i).isDead()) {
                if(!traps.get(i).getRoadboundsHit()) {
                    FinalStand.score += traps.get(i).getCost();
                }
                world.destroyBody(traps.get(i).getB2Body());
                traps.remove(i);
            } else {
                traps.get(i).update();
            }
        }
        elapsed++;

        if(FinalStand.getHealth() <= 0) {
            FinalStand.gameOver = true;
        }

        if(FinalStand.gameOver) {
            game.setScreen(new FailureScreen(game));
        }

        if(FinalStand.victory) {
            game.setScreen(new VictoryScreen(game));
        }

        hud.update();
    }

    //finds the name of the map file
    public String findMapFile(int mapNumber) {
        String mapFileName = "";
        switch(mapNumber) {
            case 1: { mapFileName =  "map1c.tmx"; } break;
            case 2: { mapFileName =  "map2.tmx"; } break;
            case 3:
            case 4: { mapFileName =  "map3.tmx"; } break;
        }
        return mapFileName;
    }

    //gets the starting position of the creeps
    public Vector2 findCreepStartingPos(int mapNumber) {
        Vector2 pos = null;
        switch(mapNumber) {
            case 1: { pos = new Vector2(10, 360); } break;
            case 2: { pos = new Vector2(648, 392); } break;
            case 3:
            case 4: { pos = new Vector2(552, 392); } break;
        }
        return pos;
    }
}
