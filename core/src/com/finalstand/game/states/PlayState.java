package com.finalstand.game.states;

/**
 * Created by Keith on 11/02/2016.
 */

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;
import com.finalstand.game.towers.AOETower;
import com.finalstand.game.towers.DOTTower;
import com.finalstand.game.towers.LaserTower;
import com.finalstand.game.towers.SingleShotTower;
import com.finalstand.game.towers.Tower;

import java.util.ArrayList;

public class PlayState implements Screen {

    private OrthographicCamera gameCam;
    private Viewport viewport;
    private FinalStand game;
    Texture texture;

    private ArrayList<Tower> towers;

    public PlayState(FinalStand game) {
        this.game = game;
        texture = new Texture("background.jpg");
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(game.V_WIDTH, game.V_HEIGHT, gameCam);

        towers = new ArrayList<Tower>();
        towers.add(new SingleShotTower(0,0));
        towers.add(new AOETower(game.V_WIDTH / 6, 0));
        //towers.add(new DOTTower(game.V_WIDTH / 3, 0));
        towers.add(new LaserTower(game.V_WIDTH / 3, 0));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0);
        for(Tower tower: towers)
        {
            game.batch.draw(tower.getCurrentTexture(), tower.getPosition().x, tower.getPosition().y, gameCam.viewportWidth / 6, gameCam.viewportHeight / 2);
        }
        game.batch.end();
        if(Gdx.input.justTouched())
        {
            for(Tower tower : towers)
            {
                tower.upgrade();
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

    }

    @Override
    public void dispose() {

    }

    public void update() {
        handleInput();
        gameCam.update();

    }

    public void handleInput() {

    }
}
