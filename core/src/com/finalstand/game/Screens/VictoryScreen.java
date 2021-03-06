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

/**
 * Created by Niall PC on 29/02/2016.
 */
public class VictoryScreen implements Screen {

    private final FinalStand game;
    private Texture texture;
    private static OrthographicCamera gameCam;
    private Viewport viewport;

    private Stage stage;
    private BitmapFont font48;

    private BackButton back;
    private static boolean backButtonPressed;

    public VictoryScreen(FinalStand game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        texture = new Texture(Gdx.files.internal("screens/menu.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        font48 = generator.generateFont(parameter);

        stage = new Stage();
        Table table = new Table();
        table.center();
        table.setFillParent(true);
        Label text = new Label("Victory", new Label.LabelStyle(font48, Color.BLACK));
        table.add(text).expandX();
        stage.addActor(table);
        generator.dispose();

        back = new BackButton("screens/backbutton.png",
                (FinalStand.V_WIDTH / FinalStand.PPM) * 0.85f,
                (FinalStand.V_HEIGHT / FinalStand.PPM) * 0.005f,
                (FinalStand.V_WIDTH / FinalStand.PPM) * 0.1f,
                (FinalStand.V_WIDTH / FinalStand.PPM) * 0.05f,
                "Victory");
        backButtonPressed = false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0, 800 / FinalStand.PPM, 400 / FinalStand.PPM);
        game.batch.draw(back.getButtonTexture(),
                        back.getPosition().x,
                        back.getPosition().y,
                        back.getWidth(),
                        back.getHeight());
        game.batch.end();
        back.update();
        stage.draw();
        update();
    }

    private void update() {
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
        texture.dispose();
    }

    public static void backButtonPressed() {
        backButtonPressed = true;
    }

    public static Vector3 getWorldMousePos() {
        return gameCam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }
}
