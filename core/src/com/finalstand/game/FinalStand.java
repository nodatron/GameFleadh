/*
	background music created by levelclearer at freesound.org
 */
package com.finalstand.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.finalstand.game.Screens.FailureScreen;
import com.finalstand.game.Screens.SelectScreen;
import com.finalstand.game.Screens.SplashScreen;
import com.finalstand.game.Screens.VictoryScreen;


public class FinalStand extends Game {

	//height and width of the gamecamera
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 400;

	public static int getHealth() {
		return health;
	}

	public static void setHealth(int health) {
		FinalStand.health = health;
	}

	//pixels per metre needed because of box2d scaling
	public static final float PPM = 100;

	public SpriteBatch batch;

	public static Integer round = 1;
	public static Integer mapNumber = 1;
	public static final int roundsPerMap = 11;

	public static boolean gameOver = false;
	public static boolean victory = false;
	public static int score = 1000;
	public static int health = 10;


	//Bits for collision in box2d
	public static final short DEFAULT = 1;
	public static final short SPIKE_BIT = 2;
	public static final short TOWER_BIT = 4;
	public static final short ROADBOUNDS_BIT = 8;
	public static final short PROJECTILE_BIT = 16;
	public static final short CREEP_BIT = 32;
	public static final short BOMB_BIT = 64;
	public static final short WAYPOINT_BIT = 128;
	public static final short BARRICADE_BIT = 256;
	public static final short RIGHT_BOUND_BIT = 512;
	public static final short LEFT_BOUND_BIT = 1024;
	public static final short TOP_BOUND_BIT = 2048;
	public static final short BOT_BOUND_BIT = 4096;
	public static final short GLUE_BIT = 8192;

	@Override
	public void create () {
		batch = new SpriteBatch(30);

		setScreen(new VictoryScreen(this));
//		System.out.println(Gdx.graphics.isGL30Available());
	}

	@Override
	public void render () {
		super.render();
	}
}
