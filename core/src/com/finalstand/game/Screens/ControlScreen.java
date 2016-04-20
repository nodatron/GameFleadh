/**
 * Instructions for the game
 * Created by Niall
 */

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
import com.finalstand.game.buttons.BackButton;
import com.finalstand.game.buttons.PlayButton;
import com.finalstand.game.hud.Controls;

/**
 * Created by Niall PC on 25/02/2016.
 */
public class ControlScreen implements Screen {

    // camera stuff
    private final FinalStand game;
    private Viewport viewport;
    private static OrthographicCamera gameCam;
    private Texture background;
    private Controls controls;

    //buttons for the screen
    private PlayButton playButton;
    private BackButton backButton;

    private static boolean playButtonPressed;
    private static boolean backButtonPressed;

    public ControlScreen(FinalStand game) {
        this.game = game;
        // making the camera and the viewport for the camera
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        background = new Texture(Gdx.files.internal("screens/menu2.png"));

        //creating the buttons for the screen
        playButton = new PlayButton("screens/playbutton.png", (FinalStand.V_WIDTH / FinalStand.PPM) * 0.3f, (FinalStand.V_HEIGHT / FinalStand.PPM) *0.01f,
                (FinalStand.V_WIDTH / FinalStand.PPM) *0.1f, (FinalStand.V_HEIGHT / FinalStand.PPM) *0.1f);
        backButton = new BackButton("screens/backbutton.png", (FinalStand.V_WIDTH / FinalStand.PPM) * 0.6f, (FinalStand.V_HEIGHT / FinalStand.PPM) *0.01f,
                (FinalStand.V_WIDTH / FinalStand.PPM) *0.1f, (FinalStand.V_HEIGHT / FinalStand.PPM) *0.1f,
                "Control");
        //getting the controls for the game
        controls = new Controls("controls.txt");

        playButtonPressed = false;
        backButtonPressed = false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //checking if a button was pressed
        playButton.update();
        backButton.update();

        //clearing the screen and drawing the textures
        Gdx.gl.glClearColor(0, 0, 0, 1);
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

        //drawing the controls to the screen
        game.batch.setProjectionMatrix(controls.stage.getCamera().combined);
        controls.stage.draw();

        //chaning screen if a button is pressed
        if(playButtonPressed) {
            game.setScreen(new SelectScreen(game));
        }

        if(backButtonPressed) {
            game.setScreen(new SplashScreen(game));
        }
    }

    // changing the size of the viewport if the window size is changed
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
