package com.finalstand.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;

/**
 * Created by Niall PC on 10/02/2016.
 */
public class PlayState implements Screen {

    private OrthographicCamera gameCam;
    private Viewport viewport;
    private FinalStand game;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

//    Texture texture;

    public PlayState(FinalStand game) {
        this.game = game;
//        texture = new Texture("background.jpg");
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(game.V_WIDTH, game.V_HEIGHT, gameCam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map1b.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        gameCam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
//        game.batch.setProjectionMatrix(gameCam.combined);
//        game.batch.begin();
//        game.batch.draw(texture, 0, 0);
//        game.batch.end();
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

    public void update(float deltaTime) {
        handleInput();
        gameCam.update();
        renderer.setView(gameCam);
    }

    public void handleInput() {

    }


}
