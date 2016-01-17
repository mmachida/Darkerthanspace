package com.libgdxjam.darkerthanspace.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;

public class Assets
{
	//General
	public AssetManager manager;
	public Skin skin;
	
	//Path
	private final String pathSpritesheetUi;
	private final String pathSpritesheetChar;
	private final String pathSpritesheetEnemy;
	private final String pathBundle;
	public final String pathFont60;
	
	//TextFile
	public I18NBundle bundle;
	
	//Animation
	public Animation animationRight;
	public Animation animationLeft;
	public Animation animationBigEnemy;
	public Animation animationNormalEnemyLeft;
	public Animation animationNormalEnemyRight;
	public static Array<TextureRegion[]> listSheetFrames = new Array<TextureRegion[]>();
	public static TextureRegion[][] temp;
	
	public Assets ()
	{
		manager = new AssetManager();
		pathSpritesheetUi = "data/images/spritesheet/spritesheet_ui.png";
		pathSpritesheetChar = "data/images/spritesheet/spritesheet_character.png";
		pathSpritesheetEnemy = "data/images/spritesheet/spritesheet_enemy.png";
		pathFont60 = "data/font/font_box.fnt";
		pathBundle = "data/properties/textfile";
	}
	
	public void loadAssets()
	{
		//Load Spritesheet/fonts/Bundle
		manager.load(pathSpritesheetUi, Texture.class);
		manager.load(pathSpritesheetChar, Texture.class);
		manager.load(pathSpritesheetEnemy, Texture.class);
		manager.load(pathFont60, BitmapFont.class);
		manager.load(pathBundle, I18NBundle.class);
		
		//Audio
		//Music
		manager.load("data/audio/music/music_menu.mp3", Music.class);
		manager.load("data/audio/music/music_save.mp3", Music.class);
		manager.load("data/audio/music/music_credits.mp3", Music.class);
		manager.load("data/audio/music/music_commandroom.mp3", Music.class);
		//SFX
		manager.load("data/audio/sfx/dead_big.mp3", Sound.class);
		manager.load("data/audio/sfx/dead.mp3", Sound.class);
		manager.load("data/audio/sfx/ice_broken.mp3", Sound.class);
		manager.load("data/audio/sfx/mainmenu_move.mp3", Sound.class);
		manager.load("data/audio/sfx/mainmenu_select.mp3", Sound.class);
		manager.load("data/audio/sfx/menu_select.mp3", Sound.class);
		manager.load("data/audio/sfx/random_scare1.mp3", Sound.class);
		manager.load("data/audio/sfx/random_scare2.mp3", Sound.class);
		manager.load("data/audio/sfx/random_scare3.mp3", Sound.class);
		manager.load("data/audio/sfx/random_scare4.mp3", Sound.class);
		manager.load("data/audio/sfx/random_scare5.mp3", Sound.class);
		manager.load("data/audio/sfx/scare_wind.mp3", Sound.class);
		//manager.load("data/audio/sfx/walk_heavy.mp3", Sound.class);
		manager.load("data/audio/sfx/ice_on.mp3", Sound.class);
		manager.load("data/audio/sfx/bomb_destroyed.mp3", Sound.class);
		manager.load("data/audio/sfx/room_bigenemy.mp3", Sound.class);
		manager.load("data/audio/sfx/walk_bigenemy.mp3", Sound.class);
		manager.load("data/audio/sfx/hit.mp3", Sound.class);
		manager.load("data/audio/sfx/heart_beat.mp3", Sound.class);
		manager.load("data/audio/sfx/door.mp3", Sound.class);
		manager.load("data/audio/sfx/shoot.mp3", Sound.class);
		manager.load("data/audio/sfx/text.mp3", Sound.class);
		manager.load("data/audio/sfx/light_on.mp3", Sound.class);
		
		//Check if is loaded
		manager.finishLoading();

		//Manage files, apply filter
		manager.get(pathSpritesheetUi, Texture.class).setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		manager.get(pathFont60, BitmapFont.class).getData().markupEnabled = true;
		bundle = manager.get(pathBundle, I18NBundle.class);
		I18NBundle.setSimpleFormatter(true);
		
		//Skin
		skin = new Skin();
		
		//Act
		skin.add("actArrow", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 0, 0, 15, 17));
		skin.add("actCloud", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 19, 0, 16, 14));
		//Nine
		skin.add("nineBox", new NinePatch(new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 39, 0, 42, 42), 12, 12, 12, 12));
		//Progress
		skin.add("progressBar", new NinePatch(new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 141, 64, 25, 25), 5, 10, 5, 10));
		skin.add("progressBarKnob", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 141, 103, 1, 15));
		//ChatBox
		skin.add("xButton", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 85, 0, 16, 14));
		skin.add("arrowButton", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 85, 18, 11, 6));
		skin.add("arrowButtonDown", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 85, 28, 11, 6));
		//InGame
		skin.add("arrowButtonRight", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 85, 38, 6, 11));
		skin.add("arrowButtonLeft", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 95, 38, 6, 11));
		skin.add("door", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 105, 0, 32, 80));
		skin.add("backDoor", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 105, 84, 32, 80));
		skin.add("cornerDoor", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 85, 53, 8, 80));
		skin.add("jail", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 157, 93, 5, 32));
		skin.add("bed", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 85, 168, 96, 40));
		skin.add("neila1", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 206, 48, 22, 34));
		skin.add("neila2", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 206, 86, 19, 42));
		skin.add("bullet", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 7, 21, 6, 6));
		skin.add("spaceshipOpen", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 146, 434, 142, 104));
		skin.add("spaceshipClose", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 7, 434, 142, 56));
		
		//Menu
		skin.add("menuTitle", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 254, 0, 740, 80));
		skin.add("menuBg", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 0, 542, 640, 384));
		
		//Menu InGame
		skin.add("helpButton", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 0, 282, 72, 72));
		skin.add("exitButton", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 76, 282, 72, 72));
		skin.add("helpImg", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 254, 84, 176, 172));
		skin.add("mapImg", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 434, 84, 333, 270));
		skin.add("mapImgSecret", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 771, 84, 15, 99));
		skin.add("whitePixel", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 0, 21, 3, 3));
		
		//Items
		skin.add("item1", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 0, 51, 16, 16));
		skin.add("item2", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 0, 71, 16, 6));
		skin.add("item3", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 0, 31, 20, 16));
		skin.add("item4", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 20, 51, 16, 16));
		skin.add("item5", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 20, 71, 19, 19));
		skin.add("item6", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 40, 51, 16, 16));
		skin.add("item7", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 43, 71, 25, 25));
		skin.add("item8", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 0, 81, 16, 19));
		skin.add("item9", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 20, 94, 8, 8));
		skin.add("menuItem1", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 0, 358, 72, 72));
		skin.add("menuItem2", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 76, 358, 72, 72));
		skin.add("menuItem3", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 152, 358, 72, 72));
		skin.add("menuItem4", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 228, 358, 72, 72));
		skin.add("menuItem5", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 304, 358, 72, 72));
		skin.add("menuItem6", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 380, 358, 72, 72));
		skin.add("menuItem7", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 456, 358, 72, 72));
		skin.add("menuItem8", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 532, 358, 72, 72));
		
		//Player Regions
		skin.add("playerGunRight", new TextureRegion(manager.get(pathSpritesheetChar, Texture.class), 146, 327, 95, 163));
		skin.add("playerGunFireRight", new TextureRegion(manager.get(pathSpritesheetChar, Texture.class), 246, 326, 95, 163));
		skin.add("playerGunLeft", new TextureRegion(manager.get(pathSpritesheetChar, Texture.class), 346, 327, 95, 163));
		skin.add("playerGunFireLeft", new TextureRegion(manager.get(pathSpritesheetChar, Texture.class), 446, 326, 95, 163));
		
		//Light
		skin.add("light1", new TextureRegion(manager.get(pathSpritesheetUi, Texture.class), 644, 542, 256, 256));
		
		
		//Animation
		loadAnimation();
	}
	
	public void loadAnimation()
	{
		int index = 0;
		
		//Texture
		temp = TextureRegion.split(manager.get(pathSpritesheetChar, Texture.class), 73, 163);

		//Character
		listSheetFrames.add(new TextureRegion[12]);
		for (int i=0; i<1; i++) //rows
			for (int j=0; j<12; j++) //columns
				listSheetFrames.peek()[index++] = temp[i][j];
		animationRight = new Animation(0.4f, listSheetFrames.peek());
		
		index = 0;
		
		//Inverted
		listSheetFrames.add(new TextureRegion[12]);
		for (int i=1; i<2; i++) //rows
			for (int j=11; j>-1; j--) //columns
				listSheetFrames.peek()[index++] = temp[i][j];
		animationLeft = new Animation(0.4f, listSheetFrames.peek());
		
		index = 0;
		
		for (int i=2; i<3; i++) //rows
			for (int j=0; j<1; j++) //columns
		skin.add("charStopRight", temp[i][j]);
		for (int i=2; i<3; i++) //rows
			for (int j=1; j<2; j++) //columns
		skin.add("charStopLeft", temp[i][j]);
		
		//Enemy 10 frames
		animationBigEnemy = new Animation(0.1f, 
		//Row
		new TextureRegion(manager.get(pathSpritesheetEnemy, Texture.class), 0, 0, 177, 183),
		new TextureRegion(manager.get(pathSpritesheetEnemy, Texture.class), 182, 0, 183, 183),
		new TextureRegion(manager.get(pathSpritesheetEnemy, Texture.class), 369, 0, 193, 183),
		new TextureRegion(manager.get(pathSpritesheetEnemy, Texture.class), 566, 0, 201, 183),
		new TextureRegion(manager.get(pathSpritesheetEnemy, Texture.class), 771, 0, 201, 183),
		//Row
		new TextureRegion(manager.get(pathSpritesheetEnemy, Texture.class), 0, 183, 192, 183),
		new TextureRegion(manager.get(pathSpritesheetEnemy, Texture.class), 196, 183, 200, 183),
		new TextureRegion(manager.get(pathSpritesheetEnemy, Texture.class), 400, 183, 201, 183),
		new TextureRegion(manager.get(pathSpritesheetEnemy, Texture.class), 605, 183, 193, 183),
		new TextureRegion(manager.get(pathSpritesheetEnemy, Texture.class), 802, 183, 183, 183));
		
		animationNormalEnemyRight = new Animation(0.1f,
				new TextureRegion(manager.get(pathSpritesheetChar, Texture.class), 0, 489, 43, 98),
				new TextureRegion(manager.get(pathSpritesheetChar, Texture.class), 47, 489, 43, 98),
				new TextureRegion(manager.get(pathSpritesheetChar, Texture.class), 94, 489, 39, 98),
				new TextureRegion(manager.get(pathSpritesheetChar, Texture.class), 137, 489, 43, 98));
		
		animationNormalEnemyLeft = new Animation(0.1f,
				new TextureRegion(manager.get(pathSpritesheetChar, Texture.class), 184, 489, 43, 98),
				new TextureRegion(manager.get(pathSpritesheetChar, Texture.class), 231, 489, 39, 98),
				new TextureRegion(manager.get(pathSpritesheetChar, Texture.class), 274, 489, 43, 98),
				new TextureRegion(manager.get(pathSpritesheetChar, Texture.class), 321, 489, 43, 98));
		
		listSheetFrames.clear();
	}

	public void dispose()
	{
		//System.out.println("ASSETS DISPOSED");
		skin.dispose();
		manager.dispose();
	}
}