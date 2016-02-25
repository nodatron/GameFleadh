package com.finalstand.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;
import com.finalstand.game.buttons.Button;
import com.finalstand.game.buttons.PlayButton;

/**
 * Created by Niall PC on 25/02/2016.
 */
public class SplashScreen implements Screen{

    private final FinalStand game;
    private Viewport viewport;
    private static OrthographicCamera gameCam;
    private Texture background;

    private PlayButton playButton;

    private static boolean playButtonPressed;
    private static boolean controlButtonPressed;
    private static boolean quitButtonPressed;

    public SplashScreen(FinalStand game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        background = new Texture("screens/title.png");

        playButton = new PlayButton("screens/playbutton.png", 300 / FinalStand.PPM, 100 / FinalStand.PPM, 50 / FinalStand.PPM, 50 / FinalStand.PPM);

        playButtonPressed = false;
        controlButtonPressed = false;
        quitButtonPressed = false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        playButton.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
//        playButton.getButtonSprite().draw(game.batch);
        game.batch.draw(background, 0, 0, 800 / FinalStand.PPM, 400 / FinalStand.PPM);
        game.batch.draw(playButton.getButtonTexture(), 300 / FinalStand.PPM, 100 / FinalStand.PPM, 50 / FinalStand.PPM, 50 / FinalStand.PPM);
        game.batch.end();

        if(playButtonPressed) {
            game.setScreen(new PlayScreen(game));
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

    public static void playButtonPressed() {
        playButtonPressed = true;
        System.out.println("here");
    }

    public static void controlButtonPressed() {
        controlButtonPressed = true;
    }

    public static void quitButtonPressed() {
        quitButtonPressed = true;
    }

    public static Vector3 getWorldMousePos() {
        return gameCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

}
