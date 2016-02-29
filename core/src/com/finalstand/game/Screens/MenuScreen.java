package com.finalstand.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;

/**
 * Created by Niall PC on 20/02/2016.
 */
public class MenuScreen implements Screen {

    private Texture background;
    private Vector2 backgroundPos;
    private OrthographicCamera gameCam;
    private Viewport viewport;

    private FinalStand game;

    public MenuScreen(FinalStand game){
        this.game = game;
    }

    @Override
    public void show() {
        gameCam = new OrthographicCamera();
        background = new Texture("badlogic.jpg");
        backgroundPos = new Vector2(0, 0);
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, gameCam);
        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        game.batch.draw(getBackground(), getBackgroundPos().x, getBackgroundPos().y);
        game.batch.end();

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
        if(Gdx.input.isKeyPressed(Input.Keys.P))
            game.setScreen(new PlayScreen(game));
    }
}