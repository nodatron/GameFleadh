/**
 * Menu for the game when the pause button is pressed
 * Created by Niall
 */
package com.finalstand.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;
import com.finalstand.game.buttons.ControlButton;
import com.finalstand.game.buttons.ExitButton;
import com.finalstand.game.buttons.ResumeButton;
import com.finalstand.game.hud.Controls;

public class MenuScreen implements Screen {


    private Texture background;
    private Vector2 backgroundPos;

    private static OrthographicCamera gameCam;
    private Viewport viewport;

    private FinalStand game;
    // holds a reference to the playscreen to use to resume the screen if the option is chosen
    private Screen play;

    // Buttons for the menu
    private ResumeButton resumeButton;
    private ExitButton exitButton;
    private ControlButton controlButton;
    private static boolean exitButtonPressed;
    private static boolean resumeButtonPressed;
    private static boolean controlButtonPressed;
    private Texture backgroundControl;
    private ExitButton toMainMenu;
    private ExitButton toDesktop;
    private ResumeButton exitConfResumeButton;
    private Vector2 exitConfPosition;
    private Vector2 getExitConfPosition;
    private Texture backToMenu;
    private Vector2 backToMenuPos;
    private float width;
    private float height;

    // states for the screen
    public enum State {
        MENU_CONTROL,
        MENU,
        EXIT_CONFIRMATION
        }

    private State state;
    //Instructions for the control part of the screen
    private Controls controls;

    public MenuScreen(FinalStand game, Screen play){

        this.game = game;
        //reference to the play screen
        this.play = play;
        // camera related stuff
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // background image
        background = new Texture(Gdx.files.internal("screens/menu.png"));
        backgroundPos = new Vector2(0, 0);
        backgroundControl = new Texture(Gdx.files.internal("screens/menu2.png"));

        // all the buttons for the screen
        resumeButton = new ResumeButton("screens/resume.png",
                                        (FinalStand.V_WIDTH / FinalStand.PPM) * 0.3f,
                                        (FinalStand.V_HEIGHT / FinalStand.PPM) *0.7f,
                                        (FinalStand.V_WIDTH / FinalStand.PPM) *0.4f,
                                        (FinalStand.V_HEIGHT / FinalStand.PPM) *0.2f);
        controlButton = new ControlButton("screens/controlbutton.png",
                                        (FinalStand.V_WIDTH / FinalStand.PPM) * 0.3f,
                                        (FinalStand.V_HEIGHT / FinalStand.PPM) *0.4f,
                                        (FinalStand.V_WIDTH / FinalStand.PPM) *0.4f,
                                        (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.2f);
        exitButton = new ExitButton("screens/exit.png",
                                    (FinalStand.V_WIDTH / FinalStand.PPM) * 0.3f,
                                    (FinalStand.V_HEIGHT / FinalStand.PPM) *0.1f,
                                    (FinalStand.V_WIDTH / FinalStand.PPM) *0.4f,
                                    (FinalStand.V_HEIGHT / FinalStand.PPM) *0.2f);
        exitConfPosition = new Vector2((FinalStand.V_WIDTH / FinalStand.PPM) * 0.35f,
                (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.35f);
        exitConfResumeButton = new ResumeButton("screens/backbutton.png",
                exitConfPosition.x + ((FinalStand.V_WIDTH / FinalStand.PPM) * 0.1f),
                exitConfPosition.y + ((FinalStand.V_HEIGHT / FinalStand.PPM) * 0.3f),
                (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.2f,
                (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.1f);
        toMainMenu = new ExitButton("screens/mainmenu.png",
                exitConfPosition.x + ((FinalStand.V_WIDTH / FinalStand.PPM) * 0.1f) ,
                exitConfPosition.y + ((FinalStand.V_HEIGHT / FinalStand.PPM) * 0.15f),
                (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.2f,
                (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.1f);
        toDesktop = new ExitButton("screens/desktop.png",
                exitConfPosition.x + ((FinalStand.V_WIDTH / FinalStand.PPM) * 0.1f),
                exitConfPosition.y,
                (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.2f,
                (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.1f);
        backToMenu = new Texture(Gdx.files.internal("screens/backbutton.png"));
        backToMenuPos = new Vector2((FinalStand.V_WIDTH / FinalStand.PPM) * 0.85f,
                (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.005f);
        width = (FinalStand.V_WIDTH / FinalStand.PPM) * 0.1f;
        height = (FinalStand.V_WIDTH / FinalStand.PPM) * 0.05f;

        exitButtonPressed = false;
        resumeButtonPressed = false;
        controlButtonPressed = false;

        //state of the screen
        state = State.MENU;
        // the instructions of the game
        controls = new Controls("controls.txt");
    }

    @Override
    public void show() {

    }

    // rendering the screen
    @Override
    public void render(float delta) {
        // checking which state the game is in
        switch (state) {
            // menu state of the screen this is just the main menu screen
            case MENU: {
                // checking if a button was pressed
                update(delta);
                resumeButton.update();
                if(Gdx.input.justTouched()) {
                    Vector3 mouse = getWorldMousePos();
                    if (mouse.x > exitButton.getPosition().x && mouse.x < exitButton.getPosition().x + exitButton.getPosition().x &&
                            mouse.y > exitButton.getPosition().y && mouse.y < exitButton.getPosition().y + exitButton.getHeight()) {
                        setGameState(State.EXIT_CONFIRMATION);
                    }
                    if (mouse.x > controlButton.getPosition().x && mouse.x < controlButton.getPosition().x + controlButton.getPosition().x &&
                            mouse.y > controlButton.getPosition().y && mouse.y < controlButton.getPosition().y + controlButton.getHeight()) {
                        setGameState(State.MENU_CONTROL);
                    }
                }
                //clearing the screen
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                game.batch.setProjectionMatrix(gameCam.combined);
                //drawing all the images to the screen
                game.batch.begin();
                game.batch.draw(getBackground(), getBackgroundPos().x, getBackgroundPos().y, FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM);
                game.batch.draw(resumeButton.getButtonTexture(), resumeButton.getPosition().x, resumeButton.getPosition().y, resumeButton.getWidth(), resumeButton.getHeight());
                game.batch.draw(controlButton.getButtonTexture(), controlButton.getPosition().x, controlButton.getPosition().y, controlButton.getWidth(), controlButton.getHeight());
                game.batch.draw(exitButton.getButtonTexture(), exitButton.getPosition().x, exitButton.getPosition().y, exitButton.getWidth(), exitButton.getHeight());
                game.batch.end();

                // changing the screen if a certain button was pressed
                if (resumeButtonPressed) {
                    game.setScreen(play);
                }

                if (controlButtonPressed) {
                    game.setScreen(new ControlScreen(game));
                }
            } break;

            // case when the exit button is pressed
            case EXIT_CONFIRMATION: {
                //checking if a button was pressed
                if(Gdx.input.justTouched()) {
                    Vector3 mouse = getWorldMousePos();
                    if (mouse.x > exitConfResumeButton.getPosition().x && mouse.x < exitConfResumeButton.getPosition().x + exitConfResumeButton.getPosition().x &&
                            mouse.y > exitConfResumeButton.getPosition().y && mouse.y < exitConfResumeButton.getPosition().y + exitConfResumeButton.getHeight()) {
                        setGameState(State.MENU);
                    }
                    if (mouse.x > toMainMenu.getPosition().x && mouse.x < toMainMenu.getPosition().x + toMainMenu.getPosition().x &&
                            mouse.y > toMainMenu.getPosition().y && mouse.y < toMainMenu.getPosition().y + toMainMenu.getHeight()) {
                        game.setScreen(new SplashScreen(game));
                        dispose();
                    }
                }
                toDesktop.update();

                //drawing to the screen
                game.batch.setProjectionMatrix(gameCam.combined);
                game.batch.begin();
                game.batch.draw(getBackground(), getExitConfPosition().x, getExitConfPosition().y,
                        (FinalStand.V_WIDTH / FinalStand.PPM) * 0.3f, (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.4f);
                game.batch.draw(exitConfResumeButton.getButtonTexture(), exitConfResumeButton.getPosition().x, exitConfResumeButton.getPosition().y, exitConfResumeButton.getWidth(), exitConfResumeButton.getHeight());
                game.batch.draw(toMainMenu.getButtonTexture(), toMainMenu.getPosition().x, toMainMenu.getPosition().y, toMainMenu.getWidth(), toMainMenu.getHeight());
                game.batch.draw(toDesktop.getButtonTexture(), toDesktop.getPosition().x, toDesktop.getPosition().y, toDesktop.getWidth(), toDesktop.getHeight());
                game.batch.end();
            } break;

            // when the user clicks on the control option of the menu
            case MENU_CONTROL: {
                if(Gdx.input.justTouched()) {
                    Vector3 mouse = getWorldMousePos();
                    System.out.println(mouse);
                    System.out.println(backToMenuPos.x);
                    System.out.println(backToMenuPos.x + width);
                    System.out.println(backToMenuPos.y );
                    System.out.println(backToMenuPos.y + height);
                    if (mouse.x > backToMenuPos.x && mouse.x < backToMenuPos.x + width &&
                            mouse.y > backToMenuPos.y && mouse.y < backToMenuPos.y + height + 14 / FinalStand.PPM) {
                        setGameState(State.MENU);
                    }
                }

                // clearing the screen
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                // getting all the images into the spritebatch and then drawing them
                game.batch.setProjectionMatrix(gameCam.combined);
                game.batch.begin();
                game.batch.draw(backgroundControl, 0, 0, 800 / FinalStand.PPM, 400 / FinalStand.PPM);
                game.batch.draw(backToMenu, backToMenuPos.x, backToMenuPos.y, width, height);
                game.batch.end();

                //drawing the text of the controls to the screen
                game.batch.setProjectionMatrix(controls.stage.getCamera().combined);
                controls.stage.draw();
            } break;
        }
    }

    //called when the screen is resized
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

    //called when the screen goes off the screen
    @Override
    public void hide() {
        dispose();
    }

    //dipsoing of al the textures correctly
    @Override
    public void dispose() {
        background.dispose();
        exitConfResumeButton.getButtonSprite().getTexture().dispose();
        toMainMenu.getButtonSprite().getTexture().dispose();
        toDesktop.getButtonSprite().getTexture().dispose();
        resumeButton.getButtonSprite().getTexture().dispose();
        exitButton.getButtonSprite().getTexture().dispose();
        controlButton.getButtonSprite().getTexture().dispose();
        background.dispose();
    }

    public void update(float delta) {
        handleInput();
    }

    //checking user input
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            game.setScreen(play);
            dispose();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            setGameState(State.EXIT_CONFIRMATION);
        }
    }

    public void setGameState(State s) {
        this.state = s;
    }

    public static Vector3 getWorldMousePos() {
        return gameCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }
    public Vector2 getBackgroundPos() {
        return backgroundPos;
    }

    public Texture getBackground() {
        return background;
    }
    public Vector2 getExitConfPosition() {
        return exitConfPosition;
    }

    public static void resumeButtonPressed() {
        resumeButtonPressed = true;
    }

    public static void controlButtonPressed() {
        controlButtonPressed = true;
    }
}