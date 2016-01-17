package com.libgdxjam.darkerthanspace.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.libgdxjam.darkerthanspace.Interf.AudioId;
import com.libgdxjam.darkerthanspace.Interf.FinalStates;
import com.libgdxjam.darkerthanspace.Interf.GameItems;
import com.libgdxjam.darkerthanspace.entities.ActBlock;
import com.libgdxjam.darkerthanspace.entities.Item;
import com.libgdxjam.darkerthanspace.screens.GameScreen;
import com.libgdxjam.darkerthanspace.utils.Assets;

public class UiInGame extends Group implements FinalStates, GameItems, AudioId
{
	//General
	private GameScreen game;
	private Assets assets;
	private int resW, resH;
	
	//Image
	private Image actImage;
	private Vector3 vec3;
	
	//Type
	private int type;
	
	//Text
	private Image textBox;
	private final int textBoxPad = 20;
	private LabelStyle labelBoxStyle;
	private Label labelBox;
	
	//TextController
	private StringBuilder stringBuilder;
	private float timeController;
	private final float timeLimit = 0.08f;
	private String fullText;
	private int textIndex;
	private int textLength;
	private boolean canFastText;
	private boolean canCalcNewText;
	private boolean canCalcDesc;
	private boolean canTextSound;
	
	//ActBlock
	private ActBlock currentAct;
	private int partsIndex;
	private int scene;
	
	//Other Icons
	private Image xButton;
	private Image arrowButton;
	private Image helpButton;
	private Image exitButton;
	private boolean canDrawArrowButton;
	private float timeArrowController;
	
	//Create Items
	private ScrollPane scrollItems;
	private Table tableItems;
	private Image itemSelector;
	private Image itemSelectorLeft;
	private Image itemSelectorRight;
	private Array<Integer> listItemsId;
	private Array<Item> listItem;
	private int itemSelected;
	private Label labelItem;
	private Label labelMap;
	private Label labelConfig;
	private Label labelItemDesc;
	
	//Shoot
	private ProgressBar progressBar;
	private boolean canSetBarPos;
	private float shootingTime;
	
	//Utils
	private GlyphLayout layout;
	private int n;

	//Help
	private boolean canHelp;
	private Image helpImg;
	
	//Map
	private boolean canMap;
	private Image mapImg;
	private Image mapImgSecret;
	private Sprite whitePos;
	private float whitePosController;
	private boolean isWhitePosFadeIn;
	
	//Bomb
	private Label labelBomb;
	private float bombController;
	private boolean canStartBomb;
	
	//Menu
	private int menuState;
	private final int menuNOTHING=0, menuITEM=1, menuMAP=2, menuCONFIG=3;

	public UiInGame(GameScreen game)
	{
		this.game = game;
		assets = game.game.assets;
		resW = game.resW;
		resH = game.resH;
		initialize();
	}
	
	public void initialize()
	{
		//Image
		actImage = new Image(assets.skin.getDrawable("actArrow"));
		actImage.setScale(4f);
		actImage.getColor().a = 0.5f;

		//Act
		vec3 = new Vector3();
		
		//Type
		type = -1;
		
		//Other Icons
		xButton = new  Image(assets.skin.getDrawable("xButton"));
		xButton.setScale(4f);
		xButton.setPosition(1178, 516);
		
		arrowButton = new  Image(assets.skin.getDrawable("arrowButton"));
		arrowButton.setScale(4f);
		arrowButton.setPosition(640-arrowButton.getWidth()/2, 498);
		
		//Create Text
		createText();
		
		//Create Items
		createItems();
		
		//Create Menu Stuff
		createMenu();
		
		//Shoot
		progressBar = new ProgressBar(0f, 1f, 0.01f, false,
				new ProgressBarStyle(assets.skin.getDrawable("progressBar"), assets.skin.getDrawable("progressBarKnob")));
		progressBar.getStyle().knobBefore = progressBar.getStyle().knob;
		progressBar.setBounds(0, 0, game.player.getRec().width*4f-20, 10);
		canSetBarPos = true;
		shootingTime = 0;
		
		//Bomb
		labelBomb = new Label("0", labelBoxStyle);
		bombController = 0;
		canStartBomb = true;
		
		//Add
		addActor(actImage);
		addActor(textBox);
		addActor(labelBox);
		addActor(scrollItems);
		addActor(itemSelector);
		addActor(itemSelectorLeft);
		addActor(itemSelectorRight);
		addActor(labelItem);
		addActor(labelMap);
		addActor(labelConfig);
		addActor(labelItemDesc);
		addActor(helpButton);
		addActor(exitButton);
		addActor(helpImg);
		addActor(mapImg);
		addActor(progressBar);
		addActor(labelBomb);
	}
	
	public void createText()
	{
		//Utils
		layout = new GlyphLayout();
		
		//TextBox
		textBox = new Image (assets.skin.getPatch("nineBox"));
		textBox.setBounds(0+textBoxPad*1.5f, 768-250-textBoxPad/2, 1280-textBoxPad*2*1.5f, 250-textBoxPad);
		
		//LabelBox
		labelBoxStyle = new LabelStyle(assets.manager.get(assets.pathFont60, BitmapFont.class), Color.WHITE);
		labelBox = new Label("0", labelBoxStyle);
		labelBox.setPosition(textBox.getX()+40, textBox.getY()+textBox.getHeight()-130+50);
		labelBox.setWidth(1180);
		//labelBox.setHeight(300);
		labelBox.setText("");
		labelBox.setWrap(true);
		
		//Act
		partsIndex = 1;
		
		//TextController
		stringBuilder = new StringBuilder();
		timeController = 0;
		textIndex = 0;
		
		//Boolean
		canFastText = false;
		canCalcNewText = true;
		canDrawArrowButton = true;
		canCalcDesc = true;
		canTextSound = false;
	}
	
	public void createItems()
	{
		listItemsId = new Array<Integer>();
		listItem = game.player.getListItem();
		
		n = listItem.size;
		
		tableItems = new Table();
		for (int i=0; i<n; i++)
		{
			Button btItem = new Button(new ButtonStyle());
			btItem.getStyle().up = getImageFromId(listItem.get(i).getId());
			if (i < n-1)
				tableItems.add(btItem).padLeft(10).left();
			else
				tableItems.add(btItem).expand().padLeft(10).left();
			listItemsId.add(listItem.get(i).getId());
		}
		
		scrollItems = new ScrollPane(tableItems);
		//scroll.getStyle().background = assets.skin.getDrawable("nineBox");
		scrollItems.setBounds(50, resH-72-80, 328, 72);
		scrollItems.setTouchable(Touchable.disabled);
		scrollItems.setOverscroll(false, false);
		scrollItems.setScrollingDisabled(false, true);
		scrollItems.setSmoothScrolling(true);
		//scrollItems.debugAll();
		
		itemSelector = new Image(assets.skin.getDrawable("arrowButtonDown"));
		itemSelector.setScale(4f);
		itemSelector.setPosition(10+scrollItems.getX()+72/2-itemSelector.getWidth()*4/2, scrollItems.getY()-itemSelector.getHeight()*4f-10);
		
		itemSelectorLeft = new Image(assets.skin.getDrawable("arrowButtonLeft"));
		itemSelectorLeft.setScale(4f);
		itemSelectorLeft.setPosition(10+scrollItems.getX()-itemSelectorLeft.getWidth()*4-10,
				scrollItems.getY()+scrollItems.getHeight()/2-itemSelectorLeft.getHeight()*4f/2);
		
		itemSelectorRight = new Image(assets.skin.getDrawable("arrowButtonRight"));
		itemSelectorRight.setScale(4f);
		itemSelectorRight.setPosition(scrollItems.getX()+scrollItems.getWidth()+10,
				scrollItems.getY()+scrollItems.getHeight()/2-itemSelectorRight.getHeight()*4f/2);
		
		itemSelected = 0;
		
		labelItem = new Label("Items", new LabelStyle(assets.manager.get(assets.pathFont60, BitmapFont.class), Color.WHITE));
		layout.setText(labelItem.getStyle().font, labelItem.getText());
		labelItem.setPosition(10+scrollItems.getX()+scrollItems.getWidth()/2-layout.width/2, resH-layout.height-40);
		
		labelMap = new Label("Map", labelItem.getStyle());
		layout.setText(labelMap.getStyle().font, labelMap.getText());
		labelMap.setPosition(resW/2-layout.width/2, resH-layout.height-40);
		labelMap.setColor(Color.GRAY);
		
		labelConfig = new Label("Config", labelItem.getStyle());
		layout.setText(labelConfig.getStyle().font, labelConfig.getText());
		labelConfig.setPosition(resW-labelItem.getX()-layout.width, resH-layout.height-40);
		
		labelItemDesc = new Label("0", new LabelStyle(assets.manager.get(assets.pathFont60, BitmapFont.class), Color.WHITE));
	}
	
	public void createMenu()
	{
		//MenuState
		menuState = menuNOTHING;
		
		//Buttons
		helpButton = new Image(assets.skin.getDrawable("helpButton"));
		helpButton.setPosition(954, resH-72-80);
		exitButton = new Image(assets.skin.getDrawable("exitButton"));
		exitButton.setPosition(helpButton.getX()+72+20, resH-72-80);
		exitButton.addListener(new ClickListener()
				{
					@Override
					public void clicked (InputEvent event, float x, float y)
					{
						game.game.setScreen(game.game.menuScreen);
					}
				});
		
		//help
		canHelp = false;
		helpImg = new Image(assets.skin.getDrawable("helpImg"));
		helpImg.setScale(2.5f);
		helpImg.setPosition(resW/2-helpImg.getWidth()*helpImg.getScaleX()/2, resH/2-helpImg.getHeight()*helpImg.getScaleY()/2);
		
		//Map
		canMap = false;
		mapImg = new Image(assets.skin.getDrawable("mapImg"));
		mapImg.setScale(2f);
		mapImg.setPosition(resW/2-mapImg.getWidth(), resH/2-mapImg.getHeight());
		
		mapImgSecret = new Image(assets.skin.getDrawable("mapImgSecret"));
		mapImgSecret.setScale(2f);
		mapImgSecret.setPosition(mapImg.getX()+28*2, mapImg.getY()+82*2);
		
		whitePos = new Sprite(assets.skin.getRegion("whitePixel"));
		whitePosController = 0;
		isWhitePosFadeIn = false;
	}
	
	@Override
	public void act(float delta)
	{
		//Actors
		scrollItems.act(delta);
		progressBar.act(delta);
		//Item selector values
		/* 0 = 74
		 * 1 = 156
		 * 2 = 238
		 * 3 = 320
		 */
		if (game.gameState == MENU)
		{
			if (Gdx.input.isKeyJustPressed(Keys.C))
			{
				game.gameState = INGAME;
				menuState = menuNOTHING;
				if (!canCalcDesc)
					canCalcDesc = true;
				game.game.audioManager.playSound(sfxMENUSELECT);
			}
			
			if (menuState == menuNOTHING)
			{
				if (Gdx.input.isKeyJustPressed(Keys.Z))
				{
					game.gameState = INGAME;
					menuState = menuNOTHING;
					if (!canCalcDesc)
						canCalcDesc = true;
					game.game.audioManager.playSound(sfxMENUSELECT);
				}
				else if (Gdx.input.isKeyJustPressed(Keys.RIGHT))
				{
					if (game.player.hasMap)
					{
						if (itemSelector.getX() == 200)
						{
							itemSelector.setX(616);
							game.game.audioManager.playSound(sfxMENUSELECT);
						}
						else if (itemSelector.getX() == 616)
						{
							itemSelector.setX(1016);
							game.game.audioManager.playSound(sfxMENUSELECT);
						}
					}
					else
					{
						if (itemSelector.getX() == 200)
						{
							itemSelector.setX(1016);
							game.game.audioManager.playSound(sfxMENUSELECT);
						}
					}
				}
				else if (Gdx.input.isKeyJustPressed(Keys.LEFT))
				{
					if (game.player.hasMap)
					{
						if (itemSelector.getX() == 1016)
						{
							itemSelector.setX(616);
							game.game.audioManager.playSound(sfxMENUSELECT);
						}
						else if (itemSelector.getX() == 616)
						{
							itemSelector.setX(200);
							game.game.audioManager.playSound(sfxMENUSELECT);
						}
					}
					else
					{
						if (itemSelector.getX() == 1016)
						{
							itemSelector.setX(200);
							game.game.audioManager.playSound(sfxMENUSELECT);
						}
					}
				}
				else if (Gdx.input.isKeyJustPressed(Keys.X))
				{
					if (itemSelector.getX() == 200)
					{
						menuState = menuITEM;
						if (listItem.size > 0)
							setItemDesc();
						itemSelector.setPosition(74, scrollItems.getY()-itemSelector.getHeight()*4f-10);
						game.game.audioManager.playSound(sfxMENUSELECT);
					}
					else if (itemSelector.getX() == 616)
					{
						setWhiteMap();
						menuState = menuMAP;
						canMap = true;
						game.game.audioManager.playSound(sfxMENUSELECT);
					}
					else if (itemSelector.getX() == 1016)
					{
						menuState = menuCONFIG;
						itemSelector.setPosition(968, helpButton.getY()-itemSelector.getHeight()*4f-10);
						game.game.audioManager.playSound(sfxMENUSELECT);
					}
				}
			}
			//Menu Items
			else if (menuState == menuITEM)
			{
				if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && itemSelected+1 < game.player.listItem.size)
				{
					if (itemSelector.getX() < 320)
					{
						itemSelector.setX(itemSelector.getX()+82);
						itemSelected++;
						setItemDesc();
					}
					else
					{
						if (scrollItems.getScrollPercentX() != 1.0f)
						{
							scrollItems.setScrollX(scrollItems.getScrollX()+72+10);
							itemSelected++;
							setItemDesc();
						}
					}
					game.game.audioManager.playSound(sfxMENUSELECT);
					//System.out.println(itemSelected);
				}
				else if (Gdx.input.isKeyJustPressed(Keys.LEFT) && itemSelected-1 > -1)
				{
					if (itemSelector.getX() > 74)
					{
						itemSelector.setX(itemSelector.getX()-82);
						itemSelected--;
						setItemDesc();
					}
					else
					{
						if (scrollItems.getScrollPercentX() != 0)
						{
							scrollItems.setScrollX(scrollItems.getScrollX()-72-10);
							itemSelected--;
							setItemDesc();
						}
					}
					game.game.audioManager.playSound(sfxMENUSELECT);
					//System.out.println(itemSelected);
				}
				else if (Gdx.input.isKeyJustPressed(Keys.X))
				{
					if (listItem.size > 0)
					{
						selectItem();
						game.game.audioManager.playSound(sfxMENUSELECT);
					}
				}
				else if (Gdx.input.isKeyJustPressed(Keys.Z))
				{
					itemSelector.setPosition(200, 672);
					itemSelected = 0;
					scrollItems.setScrollPercentX(0);
					menuState = menuNOTHING;
					game.game.audioManager.playSound(sfxMENUSELECT);
				}
			}
			else if (menuState == menuMAP)
			{
				if (isWhitePosFadeIn)
				{
					whitePosController += delta;
					if (whitePosController >= 1f)
					{
						whitePosController = 1f;
						isWhitePosFadeIn = false;
					}
				}
				else
				{
					whitePosController -= delta;
					if (whitePosController <= 0)
					{
						whitePosController = 0;
						isWhitePosFadeIn = true;
					}
				}
				whitePos.setColor(1f, 1f, 1f, 1-whitePosController);
					
				if (Gdx.input.isKeyJustPressed(Keys.ANY_KEY))
				{
					whitePosController = 0;
					isWhitePosFadeIn = false;
					canMap = false;
					menuState = menuNOTHING;
					game.game.audioManager.playSound(sfxMENUSELECT);
				}
			}
			else if (menuState == menuCONFIG)
			{
				if (canHelp)
				{
					if (Gdx.input.isKeyJustPressed(Keys.ANY_KEY))
					{
						canHelp = false;
						game.game.audioManager.playSound(sfxMENUSELECT);
					}
				}
				else
				{
					if (Gdx.input.isKeyJustPressed(Keys.RIGHT))
					{
						if (itemSelector.getX() == 968)
						{
							itemSelector.setX(1060);
							game.game.audioManager.playSound(sfxMENUSELECT);
						}
					}
					else if (Gdx.input.isKeyJustPressed(Keys.LEFT))
					{
						if (itemSelector.getX() == 1060)
						{
							itemSelector.setX(968);
							game.game.audioManager.playSound(sfxMENUSELECT);
						}
					}
					else if (Gdx.input.isKeyJustPressed(Keys.X))
					{
						if (itemSelector.getX() == 968)
						{
							canHelp = true;
						}
						else if (itemSelector.getX() == 1060)
						{
							game.finishMenu = true;
							game.gameState = FINISHMENU;
						}
						game.game.audioManager.playSound(sfxMENUSELECT);
					}
					else if (Gdx.input.isKeyJustPressed(Keys.Z))
					{
						itemSelector.setPosition(1016, 672);
						menuState = menuNOTHING;
						game.game.audioManager.playSound(sfxMENUSELECT);
					}
				}
			}
		}
		else
		{
			if (game.gameState == INGAME && !game.isShooting && Gdx.input.isKeyJustPressed(Keys.C))
			{
				game.gameState = MENU;
				menuState = menuNOTHING;
				itemSelector.setPosition(200, 672);
				itemSelected = 0;
				scrollItems.setScrollPercentX(0);
				canHelp = false;
				
				if (game.player.hasMap)
				{
					if (labelMap.getColor() != Color.WHITE)
					{
						labelMap.setColor(Color.WHITE);
					}
				}
				game.game.audioManager.playSound(sfxMENUSELECT);
				//itemSelector.setPosition(616, 672);
				//itemSelector.setPosition(1016, 672);
			}
		}
		
		if (game.canDefaultText)
		{
			game.gameState = TEXT;
		}
		
		//Can Act
		if (game.canAct && !game.canDefaultText)
		{
			if (canCalcDesc)
			{
				if (game.gameState == INGAME)
				{
					currentAct = game.eventInGame.getCurrentAct();
					scene = game.eventInGame.getScene();
					setItemDesc();
					canCalcDesc = false;
				}
			}
			
			if (type != game.eventInGame.getType())
			{
				type = game.eventInGame.getType();
				if (type == ACT || type == MAPITEM)
				{
					actImage.setDrawable(assets.skin.getDrawable("actCloud"));
					actImage.setWidth(16);
					actImage.setHeight(14);
				}
					else if (type == DOOR)
					{
						actImage.setDrawable(assets.skin.getDrawable("actArrow"));
						actImage.setWidth(15);
						actImage.setHeight(17);
					}
			}

			vec3 = game.getActPos();
			actImage.setPosition(vec3.x-actImage.getWidth()*2, vec3.y);
			
			if (game.gameState == TEXT)
			{
				if (canCalcNewText)
				{
					calcNewText();
				}
				
				if (currentAct.getParts() > 0)
				{
					updateTextBox(delta);
				}
			}
		}
		else
		{
			if (!canCalcDesc)
				canCalcDesc = true;
			
			if (game.gameState == TEXT)
			{
				if (canCalcNewText && game.canDefaultText)
				{
					calcDefaultText();
				}
				
				if (game.canDefaultText)
				{
					updateTextBox(delta);
				}
			}
		}
		
		if (game.canPickupItem)
		{
			addItem();
			game.canPickupItem = false;
		}
		
		if (game.itemToRemove > -1)
		{
			removeItem(game.itemToRemove);
			game.itemToRemove = -1;
		}
		
		if (game.isShooting)
		{
			if (game.gameState == INGAME)
			{
				if (canSetBarPos)
				{
					progressBar.setPosition(game.vec3Bar.x+20, game.vec3Bar.y-15);
					canSetBarPos = false;
					shootingTime = 0;
				}
				
				shootingTime += delta;
				progressBar.setValue(shootingTime);
				if (1-shootingTime <= 0)
				{
					canSetBarPos = true;
					shootingTime = 0;
					game.isShooting = false;
					game.canBullet = true;
					game.game.audioManager.playSound(sfxSHOOT);
				}
				else if (1-shootingTime <=0.05f)
				{
					game.player.playerShoot();
				}
			}	
			else
			{
				canSetBarPos = true;
				game.isShooting = false;
				game.canBullet = false;
				shootingTime = 0;
			}
		}
		else
		{
			if (game.canBullet)
			{
				shootingTime += delta;
				progressBar.setValue(1-shootingTime/game.player.cooldown);
				progressBar.setPosition(game.vec3Bar.x+20, game.vec3Bar.y-15);
				if (game.player.cooldown-shootingTime <= 0)
				{
					game.canBullet = false;
					shootingTime = 0;
				}
			}
			else if (game.canBullet && game.gameState == CHANGESCENE)
			{
				game.canBullet = false;
				shootingTime = 0;
				game.player.bullet.setDead(true);
			}
		}
		
		if (game.isBombPlanted)
		{
			if (canStartBomb)
			{
				bombController = 0;
				labelBomb.setPosition(10, 5);
				canStartBomb = false;
			}
			else if (!game.neilas13)
			{
				bombController += delta;
				if (150-bombController < 0)
				{
					if (!game.cantDieBomb)
					{
						game.canResetGame = true;
						game.gameState = RESETGAME;
					}
					else
					{
						game.isBombDestroyed = true;
						game.game.audioManager.playSound(sfxBOMBDESTROYED);
						game.isBombPlanted = false;
						game.defaultText = "[GREEN]you[]\nall electronic devices were damaged.";
						game.canDefaultText = true;
					}
				}
				else
				{
					labelBomb.setText("time: "+String.valueOf(getRound(150-bombController)));
				}
			}
			else
			{
				bombController = 145;
			}
		}
		else if (game.isBombDestroyed && !game.canDefaultText && bombController != -1)
		{
			game.defaultText = "[GREEN]you[]\nI need to find a way to get out\nof here.";
			game.canDefaultText = true;
			bombController = -1;
		}
	}
	
	private float getRound(float number)
	{
		number *= 100;
		number = Math.round(number);
		return number/100;
	}
	
	private void setWhiteMap()
	{
		switch (game.eventInGame.getScene())
		{
		case 1: whitePos.setBounds(mapImg.getX()+20*2, mapImg.getY()+53*2, 52*2, 29*2); break;
		case 2: whitePos.setBounds(mapImg.getX()+53*2, mapImg.getY()+86*2, 90*2, 29*2); break;
		case 3: whitePos.setBounds(mapImg.getX()+80*2, mapImg.getY()+62*2, 33*2, 20*2); break;
		case 4: whitePos.setBounds(mapImg.getX()+146*2, mapImg.getY()+86*2, 30*2, 114*2); break;
		case 5: whitePos.setBounds(mapImg.getX()+57*2, mapImg.getY()+175*2, 85*2, 33*2); break;
		case 6: whitePos.setBounds(mapImg.getX()+25*2, mapImg.getY()+181*2, 28*2, 21*2); break;
		case 7: whitePos.setBounds(mapImg.getX()+146*2, mapImg.getY()+53*2, 105*2, 29*2); break;
		case 8: whitePos.setBounds(mapImg.getX()+211*2, mapImg.getY()+20*2, 40*2, 29*2); break;
		case 9: whitePos.setBounds(mapImg.getX()+255*2, mapImg.getY()+20*2, 50*2, 29*2); break;
		case 10: whitePos.setBounds(mapImg.getX()+255*2, mapImg.getY()+53*2, 31*2, 122*2); break;
		case 11: whitePos.setBounds(mapImg.getX()+289*2, mapImg.getY()+79*2, 24*2, 48*2); break;
		case 12: whitePos.setBounds(mapImg.getX()+289*2, mapImg.getY()+131*2, 24*2, 22*2); break;
		case 13: whitePos.setBounds(mapImg.getX()+258*2, mapImg.getY()+179*2, 25*2, 71*2); break;
		case 14: whitePos.setBounds(mapImg.getX()+29*2, mapImg.getY()+86*2, 13*2, 91*2); break;
		
		default:
		break;
		}
	}

	private void setItemDesc()
	{
		if (menuState == menuITEM && listItem.size > 0)
		{
			labelItemDesc.setText(listItem.get(itemSelected).getDesc());
			layout.setText(labelItemDesc.getStyle().font, labelItemDesc.getText());
			labelItemDesc.setPosition(resW/2-layout.width/2, 30);
		}
		else
		{
			stringBuilder.delete(0, stringBuilder.length());
			stringBuilder.append("scene"+scene+"t"+currentAct.getType()+"id"+currentAct.getTypeId());
			labelItemDesc.setText(assets.bundle.get(stringBuilder.toString()));
			layout.setText(labelItemDesc.getStyle().font, labelItemDesc.getText());
			labelItemDesc.setPosition(resW/2-layout.width/2, 30);
		}
	}

	private void removeItem(int removeId)
	{
		int toRemove = -1;
		listItem = game.player.getListItem();
		
		n = listItem.size;
		for (int i=0; i<n; i++)
		{
			if (removeId == listItem.get(i).getId())
			{
				toRemove = i;
				break;
			}
		}
		
		if (toRemove != -1)
		{
			//Add new Item
			tableItems = (Table) scrollItems.getWidget();
			Table holdItems = new Table();
			
			n = tableItems.getCells().size;
			for (int i=0; i < n; i++)
			{
				if (i >= toRemove)
				{
					if (tableItems.getChildren().size > 1)
						holdItems.add(tableItems.getChildren().get(0+1)).padLeft(10).left();
					else if (n > 1)
						holdItems.getCells().peek().expand();
				}
				else
				{
					holdItems.add(tableItems.getChildren().get(0)).padLeft(10).left();
				}
			}
			listItem.removeIndex(toRemove);
			listItemsId.removeIndex(toRemove);
			scrollItems.setWidget(holdItems);
		}
		else
		{
			//System.out.println("No items to remove");
		}
		
		//Check Things
		/*System.out.println(itemSelected);
		System.out.println(listItem.size);
		System.out.println(listItemsId.size);
		System.out.println(((Table)scrollItems.getWidget()).getCells().size);*/
	}
	
	private void addItem()
	{
		//Add new Item
		tableItems = (Table) scrollItems.getWidget();
		Table holdItems = new Table();
		listItem = game.player.getListItem();
		
		n = tableItems.getCells().size;
		for (int i=0; i < n; i++)
		{
			holdItems.add(tableItems.getChildren().get(0)).padLeft(10).left();
		}
		Button btItem = new Button(new ButtonStyle());
		btItem.getStyle().up = getImageFromId(listItem.peek().getId());
		holdItems.add(btItem).expand().padLeft(10).left();
		listItemsId.add(listItem.peek().getId());
		
		scrollItems.setWidget(holdItems);
	}

	private void selectItem()
	{
		game.usingItem = listItem.get(itemSelected).getId();
		menuState = menuNOTHING;
		game.gameState = INGAME;
	}
	
	public void calcNewText()
	{
		scene = game.eventInGame.getScene();
		
		textIndex = 0;
		timeController = 0;
		labelBox.setText("");
		canFastText = false;
		canCalcNewText = false;

		if (partsIndex > currentAct.getParts())
		{
			game.gameState = INGAME;
			canCalcNewText = true;
			partsIndex = 1;
		}
		else
		{
			//Set Full Text
			stringBuilder.delete(0, stringBuilder.length());
			if(currentAct.getType() == ACT)
				stringBuilder.append("scene"+scene+"a"+currentAct.getTypeId()+"p"+partsIndex);
			else if (currentAct.getType() == MAPITEM)
				stringBuilder.append("scene"+scene+"i"+currentAct.getTypeId()+"p"+partsIndex);
			fullText = assets.bundle.get(stringBuilder.toString());
			stringBuilder.delete(0, stringBuilder.length());
			textIndex = fullText.indexOf("\n");
			labelBox.setText((fullText.substring(0, textIndex)));
			labelBox.setY(textBox.getY()+textBox.getHeight()-130+50);
			
			textLength = fullText.length();
		}
	}
	
	public void calcDefaultText()
	{
		textIndex = 0;
		timeController = 0;
		labelBox.setText("");
		canFastText = false;
		canCalcNewText = false;
		
		//Set Full Text
		stringBuilder.delete(0, stringBuilder.length());
		fullText = game.defaultText;
		textIndex = fullText.indexOf("\n");
		labelBox.setText((fullText.substring(0, textIndex)));
		labelBox.setY(textBox.getY()+textBox.getHeight()-130+50);
		
		textLength = fullText.length();
	}
	
	public void updateTextBox(float delta)
	{
		timeArrowController += delta;
		if (0.5f-timeArrowController < 0)
		{
			timeArrowController = 0;
			canDrawArrowButton = !canDrawArrowButton;
			if (canDrawArrowButton)
			{
				xButton.setY(xButton.getY()+2);
			}
			else
			{
				xButton.setY(xButton.getY()-2);
			}
		}
		if (textIndex < textLength)
		{
			timeController += delta;
			if (timeLimit-timeController < 0)
			{
				stringBuilder.append(labelBox.getText());
				stringBuilder.append(fullText.charAt(textIndex));
				labelBox.setText(stringBuilder.toString());
				stringBuilder.delete(0, stringBuilder.length());
				
				if (!canTextSound)
				{
					canTextSound = true;
				}
				else
				{
					canTextSound = false;
					game.game.audioManager.playSound(sfxTEXT);
				}
				
				fixTextHeight();
				
				textIndex++;
				timeController = 0;
				canFastText = true;
			}
			if (Gdx.input.isKeyJustPressed(Keys.X) && canFastText)
			{
				labelBox.setText(fullText);
				fixTextHeight();
				textIndex = textLength;
				canFastText = false;
			}
		}
		else if (Gdx.input.isKeyJustPressed(Keys.X))
		{
			if (game.canDefaultText)
			{
				game.canDefaultText = false;
				game.usingItem = -1;
				game.gameState = INGAME;
			}
			else
			{
				partsIndex++;
			}
			canCalcNewText = true;
		}
	}
	
	public void fixTextHeight()
	{
		layout.setText(labelBoxStyle.font, labelBox.getText());
		/*if (layout.height == 32)
		{
			labelBox.setY(textBox.getY()+textBox.getHeight()-130+50);
		}
		else*/ if (layout.height == 84)
		{
			labelBox.setY(textBox.getY()+textBox.getHeight()-130+24);
		}
		else if (layout.height == 136)
		{
			labelBox.setY(textBox.getY()+textBox.getHeight()-132);
		}
	}
	
	public Drawable getImageFromId(int id)
	{
		Drawable draw = assets.skin.getDrawable("menuItem"+id);
		
		return draw;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if (!game.canChangeScene && !game.fadeStart && game.gameState != PAUSE)
		{
			//Act
			if (game.canAct && game.gameState != MENU)
			{
				actImage.draw(batch, parentAlpha);
				labelItemDesc.draw(batch, parentAlpha);
			}
			
			//Text Box
			if (game.gameState == TEXT)
			{
				textBox.draw(batch, parentAlpha);
				labelBox.draw(batch, parentAlpha);
				xButton.draw(batch, parentAlpha);
				if (canDrawArrowButton)
					arrowButton.draw(batch, parentAlpha);
			}
			
			if (game.canShowHelp)
			{
				helpImg.draw(batch, parentAlpha);
			}
			
			if (game.isShooting || game.canBullet)
			{
				progressBar.draw(batch, parentAlpha);
			}
			
			//Items
			if (game.gameState == MENU)
			{
				labelItem.draw(batch, parentAlpha);
				labelMap.draw(batch, parentAlpha);
				labelConfig.draw(batch, parentAlpha);
				itemSelector.draw(batch, parentAlpha);
				
				if (menuState == menuITEM)
				{
					scrollItems.draw(batch, parentAlpha);
					if (scrollItems.getScrollPercentX() > 0)
						itemSelectorLeft.draw(batch, parentAlpha);
					if (scrollItems.getScrollPercentX() < 1.0f)
						itemSelectorRight.draw(batch, parentAlpha);
					if (listItem.size > 0)
						labelItemDesc.draw(batch, parentAlpha);
				}
				else if (menuState == menuCONFIG)
				{
					helpButton.draw(batch, parentAlpha);
					exitButton.draw(batch, parentAlpha);
					
					if (canHelp)
					{
						helpImg.draw(batch, parentAlpha);
					}
				}
				else if (menuState == menuMAP)
				{
					if (canMap)
					{
						mapImg.draw(batch, parentAlpha);
						if (game.eventInGame.getScene() == 14)
							mapImgSecret.draw(batch, parentAlpha);
						whitePos.draw(batch);
					}
				}
			}
		}
		
		if (game.isBombPlanted && !game.neilas13 && game.gameState != RESETGAME)
		{
			labelBomb.draw(batch, parentAlpha);
		}
	}
}
