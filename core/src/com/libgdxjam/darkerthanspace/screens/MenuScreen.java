package com.libgdxjam.darkerthanspace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.libgdxjam.darkerthanspace.MainClass;
import com.libgdxjam.darkerthanspace.Interf.AudioId;
import com.libgdxjam.darkerthanspace.Interf.ScreenInterface;
import com.libgdxjam.darkerthanspace.backgroundscrolling.ParallaxBackground;
import com.libgdxjam.darkerthanspace.backgroundscrolling.TextureRegionParallaxLayer;
import com.libgdxjam.darkerthanspace.backgroundscrolling.Utils.WH;
import com.libgdxjam.darkerthanspace.ui.UiMenu;

public class MenuScreen extends ScreenInterface implements AudioId
{
	//General
	public int resW, resH;
	private SpriteBatch fadeBatch;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	//Stage
	private Stage stage;
	
	//Transition
	private float fade;
	public boolean isFadeIn;
	public boolean isFadeOut;
	private FrameBuffer lightBuffer;
	private TextureRegion lightBufferRegion;
	private Sprite fadeSprite;
	
	//pb
	private ParallaxBackground pb;
	
	//New Game or Continue
	public boolean isNewGame;
	
	public MenuScreen(MainClass game)
	{
		super(game);
	}

	public void initialize()
	{
		//General
		resW = 1280;
		resH = 768;
		
		camera = new OrthographicCamera(resW, resH);
		camera.setToOrtho(false, resW, resH);
		camera.update();
		
		//Stage
		stage = new Stage(new StretchViewport(resW, resH));
		stage.addActor(new UiMenu(this));
		
		isNewGame = false;
		
		batch = game.batch;
		batch.setColor(Color.WHITE);
		
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
		
		game.audioManager.playMusic(musicMENU);
		
		//Background
		TextureRegionParallaxLayer bg = new TextureRegionParallaxLayer(game.assets.skin.getRegion("menuBg"), resW, new Vector2(10f, 1f), WH.width);
		pb = new ParallaxBackground();
    	pb.addLayers(bg);
	}
	
	public void logic(float delta)
	{
		camera.position.x += 10*delta;
		camera.update();
		
		if (Gdx.input.isKeyPressed(Keys.ALT_LEFT) && Gdx.input.isKeyJustPressed(Keys.ENTER))
		{
			if (!Gdx.graphics.isFullscreen())
			{
					Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);
					//Hide mouse
					Gdx.input.setCursorCatched(true);
			}
			else
			{
					Gdx.graphics.setDisplayMode(1280, 768, false);
					//Hide mouse
					Gdx.input.setCursorCatched(false);
			}
		}
		else if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			Gdx.app.exit();
		}
		
		stage.act(delta);
		
		camera.update();
	}
	
	public void draw()
	{
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			pb.draw(camera, batch);
		batch.end();
		
		stage.draw();
		
		if (isFadeIn)
		{
			fadeBatch.begin();
				fadeSprite.setColor(fadeSprite.getColor().r, fadeSprite.getColor().g, fadeSprite.getColor().b, fade);
				fadeSprite.draw(fadeBatch);
			fadeBatch.end();
			if (fade > 0)
			{
				fade = Math.max(fade - Gdx.graphics.getDeltaTime()/2.5f, 0);
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
				fade = Math.min(fade + Gdx.graphics.getDeltaTime()/3f, 1);
				game.audioManager.setMusicVolume(1-fade);
			}
			else
			{
				fade = 1.0f;
				//isFadeOut = false;
				game.audioManager.stopMusic();
				game.isNewGame = isNewGame;
				game.setScreen(new GameScreen(game));
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
		//System.out.println("MENUSCREEN DISPOSED");
		stage.dispose();
		lightBuffer.dispose();
		lightBufferRegion.getTexture().dispose();
	}
}
