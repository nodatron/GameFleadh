package com.finalstand.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.finalstand.game.Screens.MenuScreen;

public class FinalStand extends Game {

	//height and width of the gamecamera
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 400;
	//pixels per metre needed because of box2d scaling
	public static final float PPM = 100;

	public SpriteBatch batch;

	//Bits for collision in box2d
	public static final short DEFAULT = 1;
	public static final short CREEP_BIT = 2;
	public static final short TOWER_BIT = 4;
	public static final short ROADBOUNDS_BIT = 8;
	public static final short PROJECTILE_BIT = 16;
	public static final short TRAP_BIT = 32;
	public static final short BASE_BIT = 64;
	public static final short WAYPOINT_BIT = 128;
	public static final short TOUCHING_BIT = 256;

	@Override
	public void create () {
		batch = new SpriteBatch();

		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
