package com.libgdxjam.darkerthanspace.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.libgdxjam.darkerthanspace.MainClass;

public class DarkerThanSpaceDesktop {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		//Config
		config.title = "Darker Than Space v1.0";
		config.width = 1280;
		config.height = 768;
		config.fullscreen = false;
		config.vSyncEnabled = true;
		config.resizable = true;
		//config.foregroundFPS = 0;
		
		new LwjglApplication(new MainClass(), config);
	}
}
