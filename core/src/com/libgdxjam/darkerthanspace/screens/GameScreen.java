package com.libgdxjam.darkerthanspace.screens;

import java.util.Random;

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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.libgdxjam.darkerthanspace.MainClass;
import com.libgdxjam.darkerthanspace.Interf.AudioId;
import com.libgdxjam.darkerthanspace.Interf.FinalStates;
import com.libgdxjam.darkerthanspace.Interf.LightColor;
import com.libgdxjam.darkerthanspace.Interf.ScreenInterface;
import com.libgdxjam.darkerthanspace.entities.ActBlock;
import com.libgdxjam.darkerthanspace.entities.Enemy;
import com.libgdxjam.darkerthanspace.entities.LightMap;
import com.libgdxjam.darkerthanspace.entities.Player;
import com.libgdxjam.darkerthanspace.entities.SolidBlock;
import com.libgdxjam.darkerthanspace.events.eventInGame;
import com.libgdxjam.darkerthanspace.ui.UiInGame;

public class GameScreen extends ScreenInterface implements FinalStates, LightColor, AudioId
{
	//Screen
	public int resW, resH;
	private int cameraScale;
	private int cameraXFix;
	public int mapH, mapW;
	private OrthographicCamera camera;
	private Viewport viewport;

	//Generals
	private SpriteBatch batch;
	private Stage stage;
	
	//Events
	public eventInGame eventInGame;	
	
	//Entities
	public Player player;
	public Array<Enemy> listEnemy;
	public Array<SolidBlock> listSolidBlock;
	public Array<ActBlock> listActBlock;
	private Array<LightMap> listLightMap;
	
	//Debug
	private ShapeRenderer shape;
	public boolean canDebug;
	
	//Maps
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	private Polyline pol;
	
	//Utils
	private int n; //size value for fast loop calc
	private Vector3 vec3; //translation screen > world or vice-versa
	public Vector3 vec3Bar;
	public int usingItem;
	public int itemToRemove;
	public boolean canDefaultText;
	public String defaultText;
	private Random random;
	
	//Some Specifics
	private Sprite s1Bed;
	private Sprite neila;
	private Sprite s13SpaceShip;
	public boolean s13canMoveSpaceShip;
	public boolean neilas13;
	private Sprite bomb;
	private Sprite jail;
	public boolean isJailOpen;
	
	//Shoot
	public boolean isShooting;
	public boolean canBullet;
	
	//Light
	private FrameBuffer lightBuffer;
	private TextureRegion lightBufferRegion;
	private TextureRegion lightTexture;
	private int lightDiam;
	private int lightRadius;
	
	//Booleans utils need clean later
	public boolean canAct;
	public boolean canChangeScene;
	public boolean canSetScene;
	public boolean canPickupItem;
	public boolean canDrawLight;
	public boolean canShowHelp;
	public boolean canSaveProgress;
	public boolean isBombPlanted;
	public boolean isBombDestroyed;
	public boolean finishGame;
	public boolean finishMenu;
	public boolean isNeila;
	public boolean canMoveBed;
	public boolean canResetGame;
	public boolean cantDieBomb;
	
	//FadeTransition
	public float fade;
	private SpriteBatch fadeBatch;
	private Sprite fadeSprite;
	public boolean firstFrame;
	public boolean fadeStart;
	
	//State
	public int gameState;
	
	public GameScreen(MainClass game)
	{
		super(game);
		firstFrame = true;
	}
	
	public void initialize()
	{
		//Screen
		resW = 1280;
		resH = 768;
		//System.out.println("Width: "+resW); System.out.println("Height: "+resH); System.out.println();
			
		//Camera and Viewport
		camera = new OrthographicCamera();
		cameraScale = 5;
		cameraXFix = 128;
		camera.setToOrtho(false, resW/cameraScale, resH/cameraScale);
		camera.update();
		viewport = new StretchViewport(resW, resH);
		viewport.apply();
		
		//General
		if (firstFrame)
			batch = game.batch;
		
		//Entities
		listEnemy = new Array<Enemy>();
		listSolidBlock = new Array<SolidBlock>();
		listActBlock = new Array<ActBlock>();
		listLightMap = new Array<LightMap>();
		
		//Debug
		shape = new ShapeRenderer();
		canDebug = false;
		
		//Utils
		vec3 = new Vector3();
		vec3Bar = new Vector3();
		usingItem = -1;
		itemToRemove = -1;
		random = new Random();
		
		//Boolean
		canAct = false;
		canChangeScene = false;
		canSetScene = false;
		canPickupItem = false;
		canShowHelp = false;
		canDefaultText = false;
		canSaveProgress = false;
		canMoveBed = false;
		finishMenu = false;
		s13canMoveSpaceShip = false;
		cantDieBomb = false;
		canResetGame = false;
		isBombDestroyed = false;
		isBombPlanted = false;
		isNeila = false;
		
		//Text
		defaultText = "";
		
		gameState = INGAME;
		
		//CheckSave
		int sceneSaved = 0;
		if (game.isNewGame)
		{
			sceneSaved = 0;
			game.prefs.reset();
		}
		else if (game.prefs.load("hassave"))
		{
			sceneSaved = game.prefs.loadScene();
		}
		
		//Map
		//map = new TmxMapLoader().load("data/scene/scene7.tmx");
		map = new TmxMapLoader().load("data/scene/scene"+sceneSaved+".tmx");
		mapW = Integer.valueOf(map.getProperties().get("width").toString())*32;
		mapH = Integer.valueOf(map.getProperties().get("height").toString())*32;
		mapRenderer = new OrthogonalTiledMapRenderer(map);

		//Objs
		Vector2 pos = new Vector2();
		for (MapObject obj : map.getLayers().get("objcollision").getObjects())
		{
			if (obj instanceof RectangleMapObject)
			{
				listSolidBlock.add(new SolidBlock(
						((RectangleMapObject) obj).getRectangle(),
						Integer.valueOf(((RectangleMapObject) obj).getProperties().get("typeid").toString())));
			}
		}
		
		for (MapObject obj : map.getLayers().get("objpath").getObjects())
		{
			if (obj instanceof PolylineMapObject)
			{
				pol = ((PolylineMapObject)obj).getPolyline();
				pos.x = (Float) ((PolylineMapObject)obj).getProperties().get("x");
				pos.y = (Float) ((PolylineMapObject)obj).getProperties().get("y");
				listEnemy.add(new Enemy(pol, pos, Integer.valueOf(map.getProperties().get("scene").toString()), this));
			}
		}
		
		for (MapObject obj : map.getLayers().get("objact").getObjects())
		{
			if (obj instanceof RectangleMapObject)
			{
					if (Integer.valueOf(obj.getProperties().get("type").toString()) == ACT)
					{
					listActBlock.add(new ActBlock(
							Integer.valueOf(((RectangleMapObject) obj).getProperties().get("type").toString()),
							Integer.valueOf(((RectangleMapObject) obj).getProperties().get("typeid").toString()),
							Integer.valueOf(((RectangleMapObject) obj).getProperties().get("parts").toString()),
							((RectangleMapObject) obj).getRectangle(),
							this
							));
					}
						else if (Integer.valueOf(obj.getProperties().get("type").toString()) == DOOR)
						{
							listActBlock.add(new ActBlock(
									Integer.valueOf(((RectangleMapObject) obj).getProperties().get("type").toString()),
									Integer.valueOf(((RectangleMapObject) obj).getProperties().get("typeid").toString()),
									Integer.valueOf(((RectangleMapObject) obj).getProperties().get("nextscene").toString()),
									Integer.valueOf(((RectangleMapObject) obj).getProperties().get("nextdoor").toString()),
									Integer.valueOf(((RectangleMapObject) obj).getProperties().get("side").toString()),
									((RectangleMapObject) obj).getRectangle(),
									this
									));
						}
							else if (Integer.valueOf(obj.getProperties().get("type").toString()) == MAPITEM)
							{
								listActBlock.add(new ActBlock(
										Integer.valueOf(((RectangleMapObject) obj).getProperties().get("type").toString()),
										Integer.valueOf(((RectangleMapObject) obj).getProperties().get("typeid").toString()),
										Integer.valueOf(((RectangleMapObject) obj).getProperties().get("parts").toString()),
										((RectangleMapObject) obj).getRectangle(),
										Integer.valueOf(((RectangleMapObject) obj).getProperties().get("itemid").toString()),
										this
										));
							}
			}
			else if (obj instanceof EllipseMapObject)
			{
				if (Integer.valueOf(obj.getProperties().get("type").toString()) == LIGHT)
				{
					listLightMap.add(new LightMap(((EllipseMapObject) obj).getEllipse()));
				}
			}
		}
		
		//Event
		if (Integer.valueOf(map.getProperties().get("scene").toString()) == 0)
			canDrawLight = false;
		else
			canDrawLight = true;
		eventInGame = new eventInGame(this, Integer.valueOf(map.getProperties().get("scene").toString()));
		eventInGame.loadFirstTime();
		
		//Player
		player = new Player(this, listSolidBlock, listActBlock, eventInGame.getStartPosition());
		
		//Stage
		stage = new Stage(viewport);
		stage.addActor(new UiInGame(this));
		
		//Light
		if (firstFrame)
		{
			lightTexture = game.assets.skin.getRegion("light1");
			if (lightBuffer!=null)
				lightBuffer.dispose();
			lightBuffer = new FrameBuffer(Format.RGBA8888, mapW, mapH, false);
			lightBuffer.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(), mapW, mapH);
			lightBufferRegion.flip(false, true);
		}
		
		//Transition
		fade = 1.0f;
		if (firstFrame)
		{
			fadeBatch = game.fadeBatch;
			fadeSprite = new Sprite(lightBufferRegion);
		}
		fadeStart = true;
		
		//Music
		if (eventInGame.getScene() == 8 || eventInGame.getScene() == 9)
			game.audioManager.playMusic(musicSAVE);
		else if (eventInGame.getScene() == 13)
			game.audioManager.playMusic(musicCOMMANDROOM);
			
		//Specific
		s1Bed = new Sprite(game.assets.skin.getRegion("bed"));
		if (eventInGame.getScene() == 1)
		{
			if (eventInGame.s1t3id2 || game.prefs.load("s1t3id2"))
				s1Bed.setPosition(100, 32);
			else
				s1Bed.setPosition(32, 32);
		}
		
		jail = new Sprite(game.assets.skin.getRegion("jail"));
		jail.setPosition(224-jail.getWidth(), 32);
		jail.setSize(jail.getWidth(), 64);
		isJailOpen = game.prefs.load("s1t1id0");
		
		s13SpaceShip = new Sprite(game.assets.skin.getRegion("spaceshipOpen"));
		bomb = new Sprite(game.assets.skin.getRegion("item7"));
		if (eventInGame.getScene() == 13)
		{
			s13SpaceShip.setPosition(0, 32);
			bomb.setPosition(496-bomb.getWidth()/2, 64-bomb.getHeight()/2);
		}
			
		neila = new Sprite(game.assets.skin.getRegion("neila1"));
		neilas13 = false;
		if (eventInGame.getScene() == 9)
		{
			neila = new Sprite(game.assets.skin.getRegion("neila1"));
			neila.setPosition(mapW-5-neila.getWidth(), 32);
		}
		else if (eventInGame.getScene() == 13)
		{
			neila = new Sprite(game.assets.skin.getRegion("neila2"));
		}
	}
	
	public void logic(float delta)
	{
		//FullScreen
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
					//Unhide mouse
					Gdx.input.setCursorCatched(false);
			}
		}
		
		//Save
		if (canSaveProgress)
		{
			if (!isBombPlanted)
			{
				game.isNewGame = false;
				
				gameState = TEXT;
				game.prefs.save(
						true,
						9,
						player.hasFlashLight,
						player.hasMap, 
						player.hasGun,
						
						//S1
						eventInGame.s1t1id0,
						eventInGame.s1t1id1,
						eventInGame.s1t3id0,
						eventInGame.s1t3id1,
						eventInGame.s1t3id2,
						
						//S2
						eventInGame.s2t1id0,
						
						//S3
						eventInGame.s3t3id0,
						
						//S5
						eventInGame.s5t3id0,
						
						//S6
						eventInGame.s6t3id0,
						
						//S7
						eventInGame.s7t0id2,
						
						//S8
						eventInGame.s8t3id0,
						
						//S9
						eventInGame.s9t1id1,
						eventInGame.s9t1id2,
						eventInGame.s9t1id3,
						
						//S10
						eventInGame.s10t0id2,
						
						//S12
						eventInGame.s12t3id0,
						eventInGame.s12t3id1
						);
				
				game.prefs.saveInventory(player.getListItem().size, player.getListItem());
			}
			else
			{
				defaultText = "[YELLOW]system:[]\ncan't save if the bomb is planted.";
				canDefaultText = true;
			}
			canSaveProgress = false;
		}
		
		//Not Paused
		if (gameState != PAUSE)
		{
			if (canShowHelp && Gdx.input.isKeyJustPressed(Keys.ANY_KEY))
					canShowHelp = false;
			else if(Gdx.input.isKeyJustPressed(Keys.F1) && gameState == INGAME)
				canShowHelp = true;
			
			//Debug
			/*if (Gdx.input.isKeyJustPressed(Keys.D))
			{
				canDebug = !canDebug;
			}*/
			
			/*if (Gdx.input.isKeyJustPressed(Keys.R))
			{
				game.prefs.reset();
			}*/
			
			//Event
			eventInGame.updateSpecificScene();
			
			//Entities Update
			player.update(delta);
			n = listEnemy.size;
			for (int i=0; i<n; i++)
				listEnemy.get(i).update(delta);
			
			//Camera
			updateCamera();
			
			n = listActBlock.size;
			for (int i=0; i<n; i++)
			{
				listActBlock.get(i).update(delta, camera.position.x);
			}
			
			//Specific
			if (canMoveBed)
			{
				s1Bed.setX(s1Bed.getX()+30*delta);
				if (s1Bed.getX() > 100)
				{
					s1Bed.setX(100);
					canMoveBed = false;
					n = listActBlock.size;
					for (int i=0; i<n; i++)
					{
						if (listActBlock.get(i).getType() == DOOR && listActBlock.get(i).getTypeId() == 1)
						{
							listActBlock.get(i).setY(32);
							break;
						}
					}
				}
			}
			else if (s13canMoveSpaceShip)
			{
				s13SpaceShip.setY(s13SpaceShip.getY()+50*delta);
				if (s13SpaceShip.getY() > 180)
					s13canMoveSpaceShip = false;
			}
			
			//Stage
			stage.act();
		}
		else
		{
			//Pause
			if (Gdx.input.isKeyJustPressed(Keys.P))
			{
				gameState = INGAME;
			}
		}
	}
	
	public void updateCamera()
	{
		if (camera.position.x < cameraXFix) //Left
		{
			camera.position.set(cameraXFix, 76.5f, 0);
		}
			else if (camera.position.x > mapW-cameraXFix) //Right
			{
				camera.position.set(mapW-cameraXFix, 76.5f, 0);
			}
				else if (player.getX() > cameraXFix && player.getX() < mapW-cameraXFix) //Player
				{
					camera.position.set(player.getX(), 76.5f, 0);
				}
		camera.update();
		mapRenderer.setView(camera);
	}
	
	public void draw()
	{
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		//TileMap
		mapRenderer.render();
		
		//Draw
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			//specific
			if (eventInGame.getScene() == 1)
			{
				s1Bed.draw(batch);
				if (!isJailOpen)
					jail.draw(batch);
			}
			else if (eventInGame.getScene() == 9)
			{
				if (!isNeila)
					neila.draw(batch);
			}
			else if (eventInGame.getScene() == 13)
			{
				s13SpaceShip.draw(batch);
				if (neilas13)
				{
					neila.setPosition(player.getX()-30, 30);
					if (gameState == TEXT)
						neila.draw(batch);
				}
				if (isBombPlanted)
				{
					bomb.draw(batch);
				}
			}
			
			//Act
			n = listActBlock.size;
			for (int i=0; i<n; i++)
			{
				if (listActBlock.get(i).getType() == DOOR && listActBlock.get(i).getSideDoor() == 0)
				{
					listActBlock.get(i).drawDoor(batch);
				}
				else if (listActBlock.get(i).getType() == MAPITEM)
				{
					if (listActBlock.get(i).getItemId() > 1)
						listActBlock.get(i).drawItem(batch);
				}
			}
			
			//Player
			player.draw(batch);
			
			//Enemies
			n = listEnemy.size;
			for (int i=0; i<n; i++)
			{
				listEnemy.get(i).draw(batch);
			}
			
			//Back/side door
			n = listActBlock.size;
			for (int i=0; i<n; i++)
			{
				if (listActBlock.get(i).getType() == DOOR && listActBlock.get(i).getSideDoor() != 0)
				{
					listActBlock.get(i).drawDoor(batch);
				}
			}
		batch.end();
		
		//Light system
		drawFBO();

		//Debug
		if (canDebug)
		{
			shape.setProjectionMatrix(camera.combined);
			shape.begin(ShapeType.Line);
			n = listEnemy.size;
				for (int i=0; i<n; i++)
					listEnemy.get(i).drawDebug(shape);
				n = listSolidBlock.size;
				for (int i=0; i<n; i++)
					listSolidBlock.get(i).drawDebug(shape);
				n = listActBlock.size;
				for (int i=0; i<n; i++)
					listActBlock.get(i).drawDebug(shape);
				shape.setColor(Color.ORANGE);
				n = listLightMap.size;
				for (int i=0; i<n; i++)
				{
					listLightMap.get(i).drawDebug(shape);
				}
				player.drawDebug(shape);
			shape.end();
		}
		
		//Draw
		stage.draw();
		
		//Transition
		if (fadeStart)
		{
			fadeBatch.setProjectionMatrix(camera.combined);
			fadeBatch.begin();
				fadeSprite.setColor(fadeSprite.getColor().r, fadeSprite.getColor().g, fadeSprite.getColor().b, fade);
				fadeSprite.draw(fadeBatch);
			fadeBatch.end();
			if (fade > 0)
			{
				if (firstFrame)
					fade = Math.max(fade - Gdx.graphics.getDeltaTime()/3f, 0);
				else
					fade = Math.max(fade - Gdx.graphics.getDeltaTime(), 0);
				
				game.audioManager.setMusicVolume(1-fade);
			}
			else
			{
				fadeStart = false;
				firstFrame = false;
			}
		}
		else if (canChangeScene)
		{
			fadeBatch.setProjectionMatrix(camera.combined);
			fadeBatch.begin();
				fadeSprite.setColor(fadeSprite.getColor().r, fadeSprite.getColor().g, fadeSprite.getColor().b, fade);
				fadeSprite.draw(fadeBatch);
			fadeBatch.end();
			
			if (fade < 1)
			{
				if (finishGame)
					fade = Math.min(fade + Gdx.graphics.getDeltaTime()/4f, 1);
				else
					fade = Math.min(fade + Gdx.graphics.getDeltaTime()/2f, 1);
				game.audioManager.setMusicVolume(1-fade);
			}
			else
			{
				if (!finishGame)
				{
					fadeStart = true;
					eventInGame.changeScene();
					fade = 1.0f;
				}
				else
				{
					if (finishMenu)
						game.setScreen(game.menuScreen);
					else
						game.setScreen(game.gameOverScreen);
				}
			}
		}
		else if (finishMenu)
		{
			fadeBatch.setProjectionMatrix(camera.combined);
			fadeBatch.begin();
				fadeSprite.setColor(fadeSprite.getColor().r, fadeSprite.getColor().g, fadeSprite.getColor().b, fade);
				fadeSprite.draw(fadeBatch);
			fadeBatch.end();
			
			if (fade < 1)
			{
				fade = Math.min(fade + Gdx.graphics.getDeltaTime()/2f, 1);
				game.audioManager.setMusicVolume(1-fade);
			}
			else
			{
				game.setScreen(game.menuScreen);
			}
		}
		else if (canResetGame)
		{
			fadeBatch.setProjectionMatrix(camera.combined);
			fadeBatch.begin();
				fadeSprite.setColor(fadeSprite.getColor().r, fadeSprite.getColor().g, fadeSprite.getColor().b, fade);
				fadeSprite.draw(fadeBatch);
			fadeBatch.end();
			
			if (fade < 1)
			{
				fade = Math.min(fade + Gdx.graphics.getDeltaTime()/4f, 1);
			}
			else
			{
				game.isNewGame = false;
				game.setScreen(new GameScreen(game));
			}
		}
	}
	
	public void drawFBO()
	{
		//LightBuffer
     	lightBuffer.begin();
     		
     		Gdx.gl.glClearColor(0.00f, 0.00f, 0.02f, 1f); //bg
			Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
			
			batch.begin();
			batch.setBlendFunction(-1, -1);
			Gdx.gl.glBlendFuncSeparate(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA, GL30.GL_ONE, GL30.GL_ONE);
			if (canDrawLight && !isBombDestroyed)
			{
				if (player.getLives() < 3)
				{
					batch.setColor(hitCOLOR);
				}
				
				lightDiam = 40;
				lightRadius = lightDiam/2;
				//Door light
				n = listActBlock.size;
				for (int i=0; i<n; i++)
				{
					if (listActBlock.get(i).getType() == DOOR)
					{
						if (listActBlock.get(i).getSideDoor() != 3)
						{
							if (batch.getColor() != doorLIGHTGREEN && player.getLives() == 3)
							{
								batch.setColor(doorLIGHTGREEN);
							}
							batch.draw(
									lightTexture,
									camera.position.x-cameraXFix+listActBlock.get(i).getCenterX()/cameraScale-lightRadius/2,
									listActBlock.get(i).getY()/cameraScale-lightRadius+32/cameraScale,
									lightDiam/2, 
									lightDiam);
						}
						else
						{
							if (batch.getColor() != Color.WHITE && player.getLives() == 3)
							{
								batch.setColor(Color.WHITE);
							}
							batch.draw(
									lightTexture,
									camera.position.x-cameraXFix+listActBlock.get(i).getCenterX()/cameraScale-lightRadius/4,
									listActBlock.get(i).getY()/cameraScale-lightRadius+64/cameraScale,
									lightDiam/4,
									lightDiam/2);
						}
					}
				}

				//Map Lights

				if (player.getLives() == 3)
				{
					if (eventInGame.getScene() == 0)
					{
						batch.setColor(mapYELLOW);
					}
						else if (eventInGame.getScene() == 13 || eventInGame.getScene() == 9)
						{
							batch.setColor(Color.WHITE);
						}
							else if (eventInGame.getScene() != 14)
							{
								batch.setColor(mapLIGHTBLUELOW);
							}
				}
				n = listLightMap.size;
				for (int i=0; i<n; i++)
				{
					if (eventInGame.getScene() == 14 && player.getLives() == 3)
					{
						switch (i)
						{
							case 0: batch.setColor(map14COLOR0);break;
							case 1: batch.setColor(map14COLOR1);break;
							case 2: batch.setColor(map14COLOR2);break;
							case 3: batch.setColor(map14COLOR3);break;
							case 4: batch.setColor(map14COLOR4);break;
							case 5: batch.setColor(map14COLOR5);break;
							case 6: batch.setColor(map14COLOR6);break;
							default: batch.setColor(map14COLOR0); ;break;
						}
						LightMap lightMap = listLightMap.get(i);
						batch.draw(
								lightTexture,
								camera.position.x-cameraXFix+lightMap.getX()/cameraScale-lightMap.getWidth()/2,
								lightMap.getY()/cameraScale-lightMap.getHeight()/2,
								lightMap.getWidth(),
								lightMap.getHeight());
					}
					else
					{
						LightMap lightMap = listLightMap.get(i);
						batch.draw(
								lightTexture,
								camera.position.x-cameraXFix+lightMap.getX()/cameraScale-lightMap.getWidth()/2,
								lightMap.getY()/cameraScale-lightMap.getHeight()/2,
								lightMap.getWidth(),
								lightMap.getHeight());
					}
				}
			}
			else if (isBombDestroyed && canDrawLight && eventInGame.getScene() == 13)
			{
				batch.setColor(Color.WHITE);
				n = listLightMap.size;
				for (int i=0; i<n; i++)
				{
					LightMap lightMap = listLightMap.get(i);
					if (lightMap.getWidth()*2 == 192)
					{
						batch.draw(
								lightTexture,
								camera.position.x-cameraXFix+lightMap.getX()/cameraScale-lightMap.getWidth()/2, 
								lightMap.getY()/cameraScale-lightMap.getHeight()/2, 
								lightMap.getWidth(),
								lightMap.getHeight());
					}
				}
			}
			
			//Character
			lightDiam = 30;
			lightRadius = lightDiam/2;
				if (player.getLives() == 3)
				{
					if (player.isFlashLight() && !isBombDestroyed)
						batch.setColor(flashLightCOLOR);
					else
						batch.setColor(noFlashLightCOLOR);
				}
				batch.draw(
						lightTexture,
						camera.position.x-cameraXFix+player.getX()/cameraScale+player.getWidth()/cameraScale/2-lightRadius,
						(player.getY()+player.getYHeight()/2)/cameraScale/2-lightRadius/1.5f, 
						lightDiam, 
						lightDiam);
			
			if (player.getLives() == 3)
				batch.setColor(iceCOLOR);
			
			if (!player.bullet.isDead())
			{
				batch.draw(
						lightTexture,
						camera.position.x-cameraXFix+player.bullet.getX()/cameraScale-player.bullet.getWidth(),
						player.bullet.getY()/cameraScale-player.bullet.getHeight(), 
						12, 
						12);
			}
			n = listEnemy.size;
			for (int i=0; i<n; i++)
			{
				if (listEnemy.get(i).isFreeze())
				{
					batch.draw(
							lightTexture,
							camera.position.x-cameraXFix+listEnemy.get(i).getX()/cameraScale-listEnemy.get(i).getWidth()/4, 
							listEnemy.get(i).getY()/cameraScale-listEnemy.get(i).getHeight()/4,
							listEnemy.get(i).getWidth()/1.5f, 
							listEnemy.get(i).getHeight()/1.5f);
				}
			}
			
			batch.end();
				
		lightBuffer.end();
		Gdx.gl.glBlendFunc(GL30.GL_DST_COLOR, GL30.GL_ZERO);
		batch.begin();
			if (gameState != PAUSE)
				batch.setColor(1-fade, 1-fade, 1-fade, 1f);
			else
				batch.setColor(0.4f, 0.4f, 0.4f, 1f);
			batch.draw(lightBufferRegion, 0, 0, resW, resH);   
		batch.end();
	}
	
	@Override
	public void resize(int width, int height)
	{
		//Camera
		camera.setToOrtho(false, resW/cameraScale, resH/cameraScale);
		camera.update();
		viewport.update(width, height);
		viewport.apply();
		
		//Framebuffer
		if (lightBuffer!=null)
			lightBuffer.dispose();
		lightBuffer = new FrameBuffer(Format.RGBA8888, mapW, mapH, false);
		lightBuffer.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(), mapW, mapH);
		lightBufferRegion.flip(false, true);
	
		//Fix fullscreen change in some maps
		camera.position.set(player.getX(), 76.5f, 0);
		
		//System.out.println("resize");
	}
	
	public Vector3 getActPos()
	{
		return vec3;
	}
	
	public void setAction(ActBlock currentAct)
	{
		eventInGame.setEvent(currentAct);
		setActionPos(currentAct.getCenterX(), currentAct.getYHeight());
	}
	
	public void setActionPos(float x , float y)
	{
		vec3.set(x, y, 0);
		camera.project(vec3, viewport.getScreenX(), viewport.getScreenY(), viewport.getWorldWidth(), viewport.getWorldHeight()); //World to Screen
	}
	
	public void setBarPos(float x , float y)
	{
		vec3Bar.set(x, y, 0);
		camera.project(vec3Bar, viewport.getScreenX(), viewport.getScreenY(), viewport.getWorldWidth(), viewport.getWorldHeight()); //World to Screen
	}
	
	public void setScene(int scene, int typeId)
	{
		//Reset things
		canChangeScene = false;
		canSetScene = false;
		canAct = false;
		gameState = INGAME;
		listSolidBlock.clear();
		listEnemy.clear();
		listActBlock.clear();
		listLightMap.clear();
		
		map = new TmxMapLoader().load("data/scene/scene"+scene+".tmx");
		mapW = Integer.valueOf(map.getProperties().get("width").toString())*32;
		mapH = Integer.valueOf(map.getProperties().get("height").toString())*32;
		mapRenderer = new OrthogonalTiledMapRenderer(map);

		//Objs
		Vector2 pos = new Vector2();
		for (MapObject obj : map.getLayers().get("objcollision").getObjects())
		{
			if (obj instanceof RectangleMapObject)
			{
				listSolidBlock.add(new SolidBlock(
						((RectangleMapObject) obj).getRectangle(),
						Integer.valueOf(((RectangleMapObject) obj).getProperties().get("typeid").toString())));
			}
		}
		
		for (MapObject obj : map.getLayers().get("objpath").getObjects())
		{
			if (obj instanceof PolylineMapObject)
			{
				pol = ((PolylineMapObject)obj).getPolyline();
				pos.x = (Float) ((PolylineMapObject)obj).getProperties().get("x");
				pos.y = (Float) ((PolylineMapObject)obj).getProperties().get("y");
				listEnemy.add(new Enemy(pol, pos, scene, this));
			}
		}
		
		for (MapObject obj : map.getLayers().get("objact").getObjects())
		{
			if (obj instanceof RectangleMapObject)
			{
					if (Integer.valueOf(obj.getProperties().get("type").toString()) == ACT)
					{
					listActBlock.add(new ActBlock(
							Integer.valueOf(((RectangleMapObject) obj).getProperties().get("type").toString()),
							Integer.valueOf(((RectangleMapObject) obj).getProperties().get("typeid").toString()),
							Integer.valueOf(((RectangleMapObject) obj).getProperties().get("parts").toString()),
							((RectangleMapObject) obj).getRectangle(),
							this
							));
					}
						else if (Integer.valueOf(obj.getProperties().get("type").toString()) == DOOR)
						{
							listActBlock.add(new ActBlock(
									Integer.valueOf(((RectangleMapObject) obj).getProperties().get("type").toString()),
									Integer.valueOf(((RectangleMapObject) obj).getProperties().get("typeid").toString()),
									Integer.valueOf(((RectangleMapObject) obj).getProperties().get("nextscene").toString()),
									Integer.valueOf(((RectangleMapObject) obj).getProperties().get("nextdoor").toString()),
									Integer.valueOf(((RectangleMapObject) obj).getProperties().get("side").toString()),
									((RectangleMapObject) obj).getRectangle(),
									this
									));
						}
							else if (Integer.valueOf(obj.getProperties().get("type").toString()) == MAPITEM)
							{
								listActBlock.add(new ActBlock(
										Integer.valueOf(((RectangleMapObject) obj).getProperties().get("type").toString()),
										Integer.valueOf(((RectangleMapObject) obj).getProperties().get("typeid").toString()),
										Integer.valueOf(((RectangleMapObject) obj).getProperties().get("parts").toString()),
										((RectangleMapObject) obj).getRectangle(),
										Integer.valueOf(((RectangleMapObject) obj).getProperties().get("itemid").toString()),
										this
										));
							}
			}
			else if (obj instanceof EllipseMapObject)
			{
				if (Integer.valueOf(obj.getProperties().get("type").toString()) == LIGHT)
				{
					listLightMap.add(new LightMap(((EllipseMapObject) obj).getEllipse()));
				}
			}
		}
		
		//Event
		eventInGame.load();
		
		//Player
		player.setLists(listSolidBlock, listActBlock, typeId);
		camera.position.set(player.getX()+8, 96, 0);
		
		if (lightBuffer!=null)
			lightBuffer.dispose();
		lightBuffer = new FrameBuffer(Format.RGBA8888, mapW, mapH, false);
		lightBuffer.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(), mapW, mapH);
		lightBufferRegion.flip(false, true);
		
		//Specific
		if (eventInGame.getScene() == 1)
		{
			if (eventInGame.s1t3id2 || game.prefs.load("s1t3id2"))
				s1Bed.setPosition(100, 32);
			else
				s1Bed.setPosition(32, 32);
			
			jail.setPosition(224-jail.getWidth(), 32);
		}
		else if (eventInGame.getScene() == 9)
		{
			neila = new Sprite(game.assets.skin.getRegion("neila1"));
			neila.setPosition(mapW-5-neila.getWidth(), 32);
		}
		else if (eventInGame.getScene() == 13)
		{
			neila = new Sprite(game.assets.skin.getRegion("neila2"));
			s13SpaceShip.setPosition(0, 32);
			
			bomb.setPosition(496-bomb.getWidth()/2, 64-bomb.getHeight()/2);
		}
		
		//Play random scare sound
		if (scene != 8 && scene != 9 && scene != 13 && scene != 5)
		{
			if (random.nextInt(10) > 7) //20%
			{
				switch (random.nextInt(5))
				{
				case 0: game.audioManager.playSound(sfxRANDOMSCARE1);break;
				case 1: game.audioManager.playSound(sfxRANDOMSCARE2);break;
				case 2: game.audioManager.playSound(sfxRANDOMSCARE3);break;
				case 3: game.audioManager.playSound(sfxRANDOMSCARE4);break;
				case 4: game.audioManager.playSound(sfxRANDOMSCARE5);break;
				default: break;
				}
			}
		}
	}
	
	public void changeSpaceShip()
	{
		s13SpaceShip = new Sprite(game.assets.skin.getRegion("spaceshipClose"));
		s13SpaceShip.setPosition(0, 32);
	}
	
	@Override
	public void dispose()
	{
		//System.out.println("GAMESCREEN DISPOSED");
		stage.dispose();
		lightBuffer.dispose();
		lightBufferRegion.getTexture().dispose();
	}
}