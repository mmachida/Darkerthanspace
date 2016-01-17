package com.libgdxjam.darkerthanspace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdxjam.darkerthanspace.screens.GameOverScreen;
import com.libgdxjam.darkerthanspace.screens.MenuScreen;
import com.libgdxjam.darkerthanspace.utils.Assets;
import com.libgdxjam.darkerthanspace.utils.AudioManager;
import com.libgdxjam.darkerthanspace.utils.MainPreferences;

public class MainClass extends Game
{
	//General
	public SpriteBatch batch;
	public SpriteBatch fadeBatch;
	public MainPreferences prefs;
	public AudioManager audioManager;
	
	//Utils
	public Assets assets;
	public boolean isNewGame;
	
	//Screens
	public MenuScreen menuScreen;
	public GameOverScreen gameOverScreen;
	
	@Override
	public void create ()
	{
		//General
		batch = new SpriteBatch();
		fadeBatch = new SpriteBatch();
		
		//Prefs
		prefs = new MainPreferences();
		//prefs.loadFirstTime();
		
		//Utils
		assets = new Assets();
		assets.loadAssets();
		audioManager = new AudioManager(assets);
		audioManager.loadAudio();
		
		//Screens
		menuScreen = new MenuScreen(this);
		gameOverScreen = new GameOverScreen(this);
		
		//Screen Manager
		setScreen(menuScreen);
	}

	@Override
	public void render ()
	{
		super.render();
	}
	
	
	@Override
	public void dispose()
	{
		//System.out.println("MAIN DISPOSED");
		if (getScreen() != null)
			getScreen().dispose();
		batch.dispose();
		fadeBatch.dispose();
		assets.dispose();
	}
}
