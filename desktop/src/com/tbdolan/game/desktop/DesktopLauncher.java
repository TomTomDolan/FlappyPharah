package com.tbdolan.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tbdolan.game.FlappyPharah;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FlappyPharah.WIDTH;
		config.height = FlappyPharah.HEIGHT;
		config.title = FlappyPharah.TITLE;
		new LwjglApplication(new FlappyPharah(), config);
	}
}
