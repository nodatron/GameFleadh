/**
 * Select screen gives the user options for which map they want to play
 * Created by Niall
 */
package com.finalstand.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;

public class SelectScreen implements Screen {

    // Camera stuff for the screen
    private final FinalStand game;
    private Viewport viewport;
    private OrthographicCamera gameCam;
    private Texture background;

    // custom font and stage for the text
    private Stage stage;
    private BitmapFont font32;

    Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundMusic.wav"));

    public SelectScreen(FinalStand game) {

        this.game = game;
        // stuff for the camera
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        background = new Texture(Gdx.files.internal("screens/menu.png"));

        stage = new Stage();

        // maling the custom font for the labels
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        font32 = generator.generateFont(parameter);

        //creating a table and making the labels for the table
        Table table = new Table();
        table.center();

        table.setFillParent(true);
        Label map1 = new Label("Map 1:   Press the '1' key", new Label.LabelStyle(font32, Color.BLACK));
        Label map2 = new Label("Map 2:   Press the '2' key", new Label.LabelStyle(font32, Color.BLACK));
        Label map3 = new Label("Map 3:   Press the '3' key", new Label.LabelStyle(font32, Color.BLACK));
        Label boss = new Label("Boss:    Press the '4' key", new Label.LabelStyle(font32, Color.BLACK));

        table.add(map1).expandX();
        table.row();
        table.add(map2).expandX();
        table.row();
        table.add(map3).expandX();
        table.row();
        table.add(boss).expandX();

        stage.addActor(table);
        // memory management
        generator.dispose();
    }

    @Override
    public void show() {
        backgroundMusic.play();
        backgroundMusic.setLooping(true);
    }

    @Override
    public void render(float delta) {
        // checking for user input
        update();

        //clearing the screen and drawing the textures
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 800 / FinalStand.PPM, 400 / FinalStand.PPM);
        game.batch.end();

        //drawing the text to the screen
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }

    private void update() {
        // Checking for specific input from the user
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1) ||
                Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1)) {
            game.setScreen(new PlayScreen(game, 1, 1));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2) ||
                Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2)) {
            game.setScreen(new PlayScreen(game, 1, 2));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_3) ||
                Gdx.input.isKeyPressed(Input.Keys.NUMPAD_3)) {
            game.setScreen(new PlayScreen(game, 1, 3));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_4) ||
                Gdx.input.isKeyPressed(Input.Keys.NUMPAD_3)) {
            game.setScreen(new PlayScreen(game, 1, 4));
        }
    }

    // used to resize the window of the camera
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

    //memory management
    @Override
    public void dispose() {
        background.dispose();
        backgroundMusic.dispose();
    }
}
