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

import javafx.scene.control.MenuButton;

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
    private MenuButton menuButton;

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

    public MenuScreen(FinalStand game, Screen play){

        this.game = game;
        this.play = play;
        gameCam = new OrthographicCamera();
        background = new Texture("screens/menu.png");
        backgroundPos = new Vector2(0, 0);
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        resumeButton = new ResumeButton("screens/playbutton.png", 300 / FinalStand.PPM, 300 / FinalStand.PPM, 50 / FinalStand.PPM, 25 / FinalStand.PPM);
        controlButton = new ControlButton("screens/controlbutton.png", 300 / FinalStand.PPM, 200 / FinalStand.PPM, 50 / FinalStand.PPM, 25 / FinalStand.PPM);
        exitButton = new ExitButton("screens/controlbutton.png", 300 / FinalStand.PPM, 100 / FinalStand.PPM, 50 / FinalStand.PPM, 25 / FinalStand.PPM);

        exitButtonPressed = false;
        resumeButtonPressed = false;
        controlButtonPressed = false;

        state = State.MENU;
        background = new Texture("screens/menu.png");
        exitConfPosition = new Vector2((FinalStand.V_WIDTH / 2) / FinalStand.PPM, (FinalStand.V_HEIGHT / 2) / FinalStand.PPM);
        exitConfResumeButton = new ResumeButton("screens/playbutton.png", exitConfPosition.x, exitConfPosition.y, 50 / FinalStand.PPM, 25 / FinalStand.PPM);
        toMainMenu = new ExitButton("screens/controlbutton.png", exitConfPosition.x, exitConfPosition.y + (50 / FinalStand.PPM), 50 / FinalStand.PPM, 25 / FinalStand.PPM);
        toDesktop = new ExitButton("screens/controlbutton.png", exitConfPosition.x, exitConfPosition.y + (50 / FinalStand.PPM), 50 / FinalStand.PPM, 25 / FinalStand.PPM);
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
                exitButton.update();
                controlButton.update();

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

            case EXIT_CONFIRMATION: {
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
        dispose();
    }

    @Override
    public void dispose() {
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
    }

    public static Vector3 getWorldMousePos() {
        return gameCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

    public static void resumeButtonPressed() {
        resumeButtonPressed = true;
    }

    public static void controlButtonPressed() {
        controlButtonPressed = true;
    }

    public static void exitButtonPressed() {
        exitButtonPressed = true;
    }
}
