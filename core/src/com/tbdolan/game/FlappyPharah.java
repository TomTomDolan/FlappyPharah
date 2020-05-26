package com.tbdolan.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tbdolan.game.states.GameStateManager;
import com.tbdolan.game.states.MenuState;

public class FlappyPharah extends ApplicationAdapter {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Flappy Pharah";

	private GameStateManager gsm;
	public SpriteBatch batch;
	Texture img;

	private Music music;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		img = new Texture("badlogic.jpg");
		gsm.push(new MenuState(gsm, false));
		music = Gdx.audio.newMusic(Gdx.files.internal("anubis.mp3"));
		music.setLooping(true);
		music.setVolume(0.3f);
		music.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		/*batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		music.dispose();
	}
}
