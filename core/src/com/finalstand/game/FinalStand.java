package com.finalstand.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.finalstand.game.states.PlayState;

public class FinalStand extends Game {

	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 400;

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
