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

        if(resumeButtonPressed) {
            game.setScreen(play);
        }

        if(controlButtonPressed) {
            game.setScreen(new ControlScreen(game));
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
