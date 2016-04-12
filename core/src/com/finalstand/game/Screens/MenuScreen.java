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

import javafx.scene.control.MenuButton;
import sun.text.resources.cldr.ia.FormatData_ia;

/**
 * Created by Niall PC on 20/02/2016.
 */
public class MenuScreen implements Screen {

    private Texture background;
    private Vector2 backgroundPos;
    private static OrthographicCamera gameCam;
    private Viewport viewport;

    private FinalStand game;
    private Screen play;

    private ResumeButton resumeButton;
    private ExitButton exitButton;
    private ControlButton controlButton;


    private static boolean exitButtonPressed;
    private static boolean resumeButtonPressed;
    private static boolean controlButtonPressed;

    public enum State {
        MENU_CONTROL,
        MENU,
        EXIT_CONFIRMATION
    }
    private State state;
    private Texture exitConfBackground;
    private ExitButton toMainMenu;
    private ExitButton toDesktop;
    private ResumeButton exitConfResumeButton;
    private Vector2 exitConfPosition;
    private Vector2 getExitConfPosition;

    private Texture backToMenu;
    private Vector2 backToMenuPos;
    private float width;
    private float height;

    private Controls controls;

    public MenuScreen(FinalStand game, Screen play){

        this.game = game;
        this.play = play;
        gameCam = new OrthographicCamera();
        background = new Texture(Gdx.files.internal("screens/menu.png"));
        backgroundPos = new Vector2(0, 0);
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

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

        exitButtonPressed = false;
        resumeButtonPressed = false;
        controlButtonPressed = false;

        state = State.MENU;
        background = new Texture(Gdx.files.internal("screens/menu.png"));
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

        controls = new Controls("controls.txt");
    }

    @Override
    public void show() {

    }

    public Vector2 getBackgroundPos() {
        return backgroundPos;
    }

    public Texture getBackground() {
        return background;
    }

    @Override
    public void render(float delta) {
        switch (state) {
            case MENU: {
                update(delta);
                resumeButton.update();
//                exitButton.update();
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
//                controlButton.update();

                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                game.batch.setProjectionMatrix(gameCam.combined);

                game.batch.begin();
                game.batch.draw(getBackground(), getBackgroundPos().x, getBackgroundPos().y, FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM);
                game.batch.draw(resumeButton.getButtonTexture(), resumeButton.getPosition().x, resumeButton.getPosition().y, resumeButton.getWidth(), resumeButton.getHeight());
                game.batch.draw(controlButton.getButtonTexture(), controlButton.getPosition().x, controlButton.getPosition().y, controlButton.getWidth(), controlButton.getHeight());
                game.batch.draw(exitButton.getButtonTexture(), exitButton.getPosition().x, exitButton.getPosition().y, exitButton.getWidth(), exitButton.getHeight());
                game.batch.end();

                if (resumeButtonPressed) {
                    game.setScreen(play);
                }

                if (controlButtonPressed) {
                    game.setScreen(new ControlScreen(game));
                }
            } break;

            //TODO Fix the position of the buttons and create new textures for the buttons like return to main menu and to desktop
            case EXIT_CONFIRMATION: {
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
//                Gdx.gl.glClearColor(1, 0, 0, 1);
//                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                game.batch.setProjectionMatrix(gameCam.combined);
                game.batch.begin();
                game.batch.draw(getBackground(), getExitConfPosition().x, getExitConfPosition().y,
                        (FinalStand.V_WIDTH / FinalStand.PPM) * 0.3f, (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.4f);
                game.batch.draw(exitConfResumeButton.getButtonTexture(), exitConfResumeButton.getPosition().x, exitConfResumeButton.getPosition().y, exitConfResumeButton.getWidth(), exitConfResumeButton.getHeight());
                game.batch.draw(toMainMenu.getButtonTexture(), toMainMenu.getPosition().x, toMainMenu.getPosition().y, toMainMenu.getWidth(), toMainMenu.getHeight());
                game.batch.draw(toDesktop.getButtonTexture(), toDesktop.getPosition().x, toDesktop.getPosition().y, toDesktop.getWidth(), toDesktop.getHeight());
                game.batch.end();
            } break;

            // TODO(niall) Get the controls of the game to display here and change the position of the buttons to make it nicer looking
            // TODO(niall) make the buttons for toDesktop and toMainMenu and the background for the control pages
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

//                System.out.println(backToMenuPos.x + width);

                Gdx.gl.glClearColor(1, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                game.batch.setProjectionMatrix(gameCam.combined);
                game.batch.begin();
//        playButton.getButtonSprite().draw(game.batch);
                game.batch.draw(background, 0, 0, 800 / FinalStand.PPM, 400 / FinalStand.PPM);
                game.batch.draw(backToMenu, backToMenuPos.x, backToMenuPos.y, width, height);
                game.batch.end();

                game.batch.setProjectionMatrix(controls.stage.getCamera().combined);
                controls.stage.draw();
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
        background.dispose();
        exitConfResumeButton.getButtonSprite().getTexture().dispose();
        toMainMenu.getButtonSprite().getTexture().dispose();
        toDesktop.getButtonSprite().getTexture().dispose();
        resumeButton.getButtonSprite().getTexture().dispose();
        exitButton.getButtonSprite().getTexture().dispose();
        controlButton.getButtonSprite().getTexture().dispose();
        background.dispose();
    }

    public void setGameState(State s) {
        this.state = s;
    }

    public void update(float delta) {
        handleInput();
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            game.setScreen(play);
            dispose();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            setGameState(State.EXIT_CONFIRMATION);
        }
    }

    public static Vector3 getWorldMousePos() {
        return gameCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
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