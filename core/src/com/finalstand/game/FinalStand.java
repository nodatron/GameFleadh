package com.finalstand.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.finalstand.game.states.PlayState;

public class FinalStand extends Game {

	//height and width of the gamecamera
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 400;
	//pixels per metre needed because of box2d scaling
	public static final float PPM = 100;

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayState(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
