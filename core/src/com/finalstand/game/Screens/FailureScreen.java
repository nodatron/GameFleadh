/**
 * Shows if the player has lost the game
 * Created by Niall
 */
package com.finalstand.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;
import com.finalstand.game.buttons.BackButton;


public class FailureScreen implements Screen {

    // camera
    private final FinalStand game;
    private Texture texture;
    private static OrthographicCamera gameCam;
    private Viewport viewport;

    //custom font
    private BitmapFont font48;
    // button back to the splash screen
    private BackButton backButton;

    //stage for the text
    private Stage stage;

    public static boolean backButtonPressed;

    public FailureScreen(FinalStand game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        texture = new Texture(Gdx.files.internal("screens/menu.png"));

        //creating the custom font and then adding it to the stage as a table
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        font48 = generator.generateFont(parameter);

        stage = new Stage();
        Table table = new Table();
        table.left();
        table.setFillParent(true);
        Label text = new Label("Failure", new Label.LabelStyle(font48, Color.BLACK));
        table.add(text).expandX();

        stage.addActor(table);
        generator.dispose();

        // making the back button
        backButton = new BackButton("screens/backbutton.png",
                (FinalStand.V_WIDTH / FinalStand.PPM) * 0.85f,
                (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.005f,
                (FinalStand.V_WIDTH / FinalStand.PPM) * 0.1f,
                (FinalStand.V_WIDTH / FinalStand.PPM) * 0.05f,
                "Fail");

        backButtonPressed = false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //clearing the screen and drawing the textures
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0, 800 / FinalStand.PPM, 400 / FinalStand.PPM);
        game.batch.draw(backButton.getButtonTexture(),
                        backButton.getPosition().x,
                        backButton.getPosition().y,
                        backButton.getWidth(),
                        backButton.getHeight());
        game.batch.end();
        // checking if the button is pressed
        backButton.update();
        //drawing the text to the screen
        stage.draw();

        //changing the screen if a button is pressed
        update();
    }

    private void update() {
        if(backButtonPressed) {
            game.setScreen(new SplashScreen(game));
        }
    }

    //resize the screen if the window size is changed
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
        texture.dispose();
    }

    public static void backButtonPressed() {
        backButtonPressed = true;
    }

    public static Vector3 getWorldMousePos() {
        return gameCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }
}
