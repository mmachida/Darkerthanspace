package com.libgdxjam.darkerthanspace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.libgdxjam.darkerthanspace.MainClass;
import com.libgdxjam.darkerthanspace.Interf.AudioId;
import com.libgdxjam.darkerthanspace.Interf.ScreenInterface;
import com.libgdxjam.darkerthanspace.ui.UiGameOver;

public class GameOverScreen extends ScreenInterface implements AudioId
{
	//General
	public int resW, resH;
	private Stage stage;
	
	//Transition
	private float fade;
	private SpriteBatch fadeBatch;
	public boolean isFadeIn;
	public boolean isFadeOut;
	private FrameBuffer lightBuffer;
	private TextureRegion lightBufferRegion;
	private Sprite fadeSprite;
	
	public GameOverScreen(MainClass game)
	{
		super(game);
	}
	
	public void initialize()
	{
		//General
		resW = 1280;
		resH = 768;
		
		//Stage
		stage = new Stage(new StretchViewport(resW, resH));
		stage.addActor(new UiGameOver(this));
		
		//Transition
		fade = 1;
		isFadeIn = true;
		isFadeOut = false;
		fadeBatch = game.fadeBatch;
		
		if (lightBuffer!=null)
			lightBuffer.dispose();
		lightBuffer = new FrameBuffer(Format.RGBA8888, resW, resH, false);
		lightBuffer.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(), resW, resH);
		lightBufferRegion.flip(false, true);
		fadeSprite = new Sprite(lightBufferRegion);
		
		game.audioManager.playMusic(musicCREDITS);
	}
	
	public void logic(float delta)
	{
		stage.act(delta);
	}
	
	public void draw()
	{
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		drawFade();
	}
	
	public void drawFade()
	{
		if (isFadeIn)
		{
			fadeBatch.begin();
				fadeSprite.setColor(fadeSprite.getColor().r, fadeSprite.getColor().g, fadeSprite.getColor().b, fade);
				fadeSprite.draw(fadeBatch);
			fadeBatch.end();
			if (fade > 0)
			{
				fade = Math.max(fade - Gdx.graphics.getDeltaTime()/2f, 0);
				game.audioManager.setMusicVolume(1-fade);
			}
			else
			{
				fade = 0f;
				isFadeIn = false;
			}
		}
		else if (isFadeOut)
		{
			fadeBatch.begin();
				fadeSprite.setColor(fadeSprite.getColor().r, fadeSprite.getColor().g, fadeSprite.getColor().b, fade);
				fadeSprite.draw(fadeBatch);
			fadeBatch.end();
			if (fade < 1)
			{
				fade = Math.min(fade + Gdx.graphics.getDeltaTime()/5f, 1);
				game.audioManager.setMusicVolume(1-fade);
			}
			else
			{
				fade = 1.0f;
				//isFadeOut = false;
				game.setScreen(game.menuScreen);
			}
		}
	}

	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height);
		stage.getViewport().apply();
		
		if (lightBuffer!=null)
			lightBuffer.dispose();
		lightBuffer = new FrameBuffer(Format.RGBA8888, resW, resH, false);
		lightBuffer.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(), resW, resH);
		lightBufferRegion.flip(false, true);
	}
	
	@Override
	public void dispose()
	{
		//System.out.println("GAMEOVER DISPOSED");
		stage.dispose();
		lightBuffer.dispose();
	}
}
