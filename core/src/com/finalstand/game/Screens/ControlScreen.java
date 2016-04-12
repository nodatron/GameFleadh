/// Make a new contol screen for the menu control screen

package com.finalstand.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;
import com.finalstand.game.buttons.BackButton;
import com.finalstand.game.buttons.PlayButton;
import com.finalstand.game.hud.Controls;

/**
 * Created by Niall PC on 25/02/2016.
 */
public class ControlScreen implements Screen {

    private final FinalStand game;
    private Viewport viewport;
    private static OrthographicCamera gameCam;
    private Texture background;
    private Controls controls;

    private PlayButton playButton;
    private BackButton backButton;

    private static boolean playButtonPressed;
    private static boolean backButtonPressed;

    public ControlScreen(FinalStand game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        background = new Texture(Gdx.files.internal("screens/menu.png"));

        playButton = new PlayButton("screens/playbutton.png", (FinalStand.V_WIDTH / FinalStand.PPM) * 0.3f, (FinalStand.V_HEIGHT / FinalStand.PPM) *0.04f,
                (FinalStand.V_WIDTH / FinalStand.PPM) *0.1f, (FinalStand.V_HEIGHT / FinalStand.PPM) *0.1f);
        backButton = new BackButton("screens/backbutton.png", (FinalStand.V_WIDTH / FinalStand.PPM) * 0.6f, (FinalStand.V_HEIGHT / FinalStand.PPM) *0.04f,
                (FinalStand.V_WIDTH / FinalStand.PPM) *0.1f, (FinalStand.V_HEIGHT / FinalStand.PPM) *0.1f);
        controls = new Controls("controls.txt");

        playButtonPressed = false;
        backButtonPressed = false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        playButton.update();
        backButton.update();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 800 / FinalStand.PPM, 400 / FinalStand.PPM);
        game.batch.draw(playButton.getButtonTexture(),
                        playButton.getPosition().x,
                        playButton.getPosition().y,
                        playButton.getWidth(),
                        playButton.getHeight());
        game.batch.draw(backButton.getButtonTexture(),
                        backButton.getPosition().x,
                        backButton.getPosition().y,
                        backButton.getWidth(),
                        backButton.getHeight());
        game.batch.end();

        game.batch.setProjectionMatrix(controls.stage.getCamera().combined);
        controls.stage.draw();

        if(playButtonPressed) {
            game.setScreen(new PlayScreen(game));
        }

        if(backButtonPressed) {
            game.setScreen(new SplashScreen(game));
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
    }

    public static void backButtonPressed() {
        backButtonPressed = true;
    }

    public static Vector3 getWorldMousePos() {
        return gameCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }
}
