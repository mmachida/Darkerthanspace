package com.libgdxjam.darkerthanspace.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.libgdxjam.darkerthanspace.Interf.AudioId;
import com.libgdxjam.darkerthanspace.screens.MenuScreen;
import com.libgdxjam.darkerthanspace.utils.Assets;

public class UiMenu extends Group implements AudioId
{
	//General
	private MenuScreen game;
	private Assets assets;
	private int resW, resH;
	
	//Menu Items
	private ScrollPane scroll;
	private TextButton btNewGame;
	private TextButton btContinue;
	//private TextButton btHowToPlay;
	private TextButton btInfo;
	private int itemIndex;
	
	//Menu Icons
	private Image arrowButtonUp;
	private Image arrowButtonDown;
	private Image arrowYesNow;
	private boolean canDrawArrowUp;
	private boolean canDrawArrowDown;
	
	//Labels
	private Label labelX;
	private Label labelEsc;
	private Label labelInfo;
	private Label labelNewGameMsg;
	private Label labelYesNo;
	private Label labelFullScreen;
	private Label labelVersion;
	
	//Images
	private Image title;
	
	//Bollean
	private boolean hasSave;
	private boolean isInfo;
	private boolean isNewGame;
	
	public UiMenu(MenuScreen game)
	{
		this.game = game;
		assets = game.game.assets;
		resW = game.resW;
		resH = game.resH;
		
		initialize();
	}
	
	public void initialize()
	{
		//Images
		title = new Image(assets.skin.getDrawable("menuTitle"));
		title.setScale(1.5f);
		title.setPosition(resW/2-title.getWidth()/2*title.getScaleX(), resH-title.getHeight()-100);

		//Buttons
		createList();
		
		//Icons
		arrowButtonUp = new Image(assets.skin.getDrawable("arrowButtonDown"));
		arrowButtonUp.setScale(4f);
		arrowButtonUp.setPosition(scroll.getX()+scroll.getWidth()/2-arrowButtonUp.getWidth()*4f/2, scroll.getY()+scroll.getHeight());
		canDrawArrowUp = false;
		arrowButtonDown = new Image(assets.skin.getDrawable("arrowButton"));
		arrowButtonDown.setScale(4f);
		arrowButtonDown.setPosition(scroll.getX()+scroll.getWidth()/2-arrowButtonDown.getWidth()*4f/2, scroll.getY()-arrowButtonDown.getHeight()-18);
		canDrawArrowDown = true;
		
		addActor(title);
		addActor(scroll);
		addActor(labelX);
		addActor(labelEsc);
		addActor(labelInfo);
		addActor(labelNewGameMsg);
		addActor(labelFullScreen);
		addActor(labelVersion);
		addActor(labelYesNo);
		addActor(arrowButtonUp);
		addActor(arrowButtonDown);
	}
	
	public void createList()
	{
		//Table
		Table mainTable;
		mainTable = new Table();
		
		TextButtonStyle a;
		a = new TextButtonStyle(
				assets.skin.getDrawable("nineBox"), 
				assets.skin.getDrawable("nineBox"), 
				assets.skin.getDrawable("nineBox"), 
				assets.manager.get(assets.pathFont60, BitmapFont.class));
		
		//Bt
		btNewGame = new TextButton("new game", a);
		btContinue = new TextButton("continue", new TextButtonStyle(
				assets.skin.getDrawable("nineBox"), 
				assets.skin.getDrawable("nineBox"), 
				assets.skin.getDrawable("nineBox"), 
				assets.manager.get(assets.pathFont60, BitmapFont.class)));
		//btHowToPlay = new TextButton("how to", a);
		btInfo = new TextButton("info", a);
		
		mainTable.add(btNewGame).expand().fill().padTop(9);
		mainTable.row();
		mainTable.add(btContinue).expand().fill().padTop(9);
		mainTable.row();
		//mainTable.add(btHowToPlay).expand().fill().padTop(9);
		//mainTable.row();
		mainTable.add(btInfo).expand().fill().padTop(9).padBottom(9);
		
		scroll = new ScrollPane(mainTable);
		//scroll.getStyle().background = assets.skin.getDrawable("nineBox");
		scroll.setBounds(resW/2-150, resH/3+25, 300, 100);
		scroll.setTouchable(Touchable.disabled);
		scroll.setOverscroll(false, false);
		scroll.setScrollingDisabled(true, false);
		scroll.setSmoothScrolling(true);
		scroll.setScrollPercentY(0);
		
		itemIndex = 0;
		
		//Check Save
		hasSave = game.game.prefs.load("hassave");
		if (!hasSave)
		{
			btContinue.setColor(Color.DARK_GRAY);
			btContinue.getStyle().fontColor = Color.DARK_GRAY;
		}
		
		isInfo = false;
		isNewGame = false;
		
		//Labels
		labelX = new Label("x-select", new LabelStyle(assets.manager.get(assets.pathFont60, BitmapFont.class), Color.WHITE));
		GlyphLayout layout = new GlyphLayout(labelX.getStyle().font, labelX.getText());
		labelX.setAlignment(1, 1);
		labelX.setPosition(resW/4-layout.width/2, 32);
		
		labelEsc = new Label("esc-exit", labelX.getStyle());
		layout.setText(labelX.getStyle().font, labelEsc.getText());
		labelEsc.setAlignment(1, 1);
		labelEsc.setPosition(resW-resW/4-layout.width/2, 32);
		
		labelInfo = new Label("this demo was created by\n'Marcio Machida'\n\nfor the first\n#libgdxjam", labelX.getStyle());
		layout.setText(labelX.getStyle().font, labelInfo.getText());
		labelInfo.setAlignment(1, 1);
		labelInfo.setPosition(resW/2-layout.width/2, (title.getY()-labelX.getY()+labelX.getHeight())/2-layout.height/2);
	
		labelNewGameMsg = new Label("[YELLOW]saved data will be erased\nare you sure?[]", labelX.getStyle());
		layout.setText(labelX.getStyle().font, labelNewGameMsg.getText());
		labelNewGameMsg.setAlignment(1, 1);
		labelNewGameMsg.setPosition(resW/2-layout.width/2, (title.getY()-labelX.getY()+labelX.getHeight())/2-layout.height/2+80);
		
		labelFullScreen = new Label("alt+enter: fullscreen", labelX.getStyle());
		labelFullScreen.setFontScale(0.5f);
		layout.setText(labelX.getStyle().font, labelFullScreen.getText());
		labelFullScreen.setAlignment(1, 1);
		labelFullScreen.setPosition(resW/2-layout.width/2, resH-layout.height-12);
		
		labelVersion = new Label("v1.0", labelX.getStyle());
		labelVersion.setFontScale(0.4f);
		layout.setText(labelX.getStyle().font, labelVersion.getText());
		labelVersion.setAlignment(1, 1);
		labelVersion.setPosition(resW-layout.width, 3);
		
		labelYesNo = new Label("no  yes", labelX.getStyle());
		layout.setText(labelX.getStyle().font, labelYesNo.getText());
		labelYesNo.setAlignment(1, 1);
		labelYesNo.setPosition(resW/2-layout.width/2, labelNewGameMsg.getX()-20);
		
		//Yes Now
		arrowYesNow = new Image(assets.skin.getDrawable("arrowButtonDown"));
		arrowYesNow.setScale(4f);
		arrowYesNow.setPosition(534, labelYesNo.getY()-30);
	}
	
	@Override
	public void act(float delta)
	{
		if (getStage().getScrollFocus() != scroll)
		{
			getStage().setScrollFocus(scroll);
		}

		
		if (!game.isFadeIn && !game.isFadeOut)
		{
			if (!isInfo && !isNewGame)
			{
				if (Gdx.input.isKeyJustPressed(Keys.DOWN))
				{
					//Logic to change scroll Y
					if (scroll.getScrollPercentY() != 1)
					{
						scroll.setScrollY(scroll.getScrollY()+82+9);
						itemIndex++;
						game.game.audioManager.playSound(sfxMAINMENUSELECT);
					}
				}
				else if (Gdx.input.isKeyJustPressed(Keys.UP))
				{
					if (scroll.getScrollPercentY() != 0)
					{
						scroll.setScrollY(scroll.getScrollY()-82-9);
						itemIndex--;
						game.game.audioManager.playSound(sfxMAINMENUSELECT);
					}
				}
				else if (Gdx.input.isKeyJustPressed(Keys.X))
				{
					switch (itemIndex)
					{
					case 0:
						//New Game
						if (!hasSave)
						{
							game.isNewGame = true;
							game.isFadeOut = true;
						}
						else
						{
							isNewGame = true;
							arrowYesNow.setX(534);
						}
					break;
					case 1:
						//Continue
						if (hasSave)
						{
							game.isNewGame = false;
							game.isFadeOut = true;
						}
					break;
					case 2:
						//Info
						isInfo = true;
					break;
					default:
						itemIndex = 0;
						scroll.setScrollPercentY(0);
					break;
					}
					game.game.audioManager.playSound(sfxMAINMENUSELECT);
				}
			}
			else if (isInfo)
			{
				if (Gdx.input.isKeyJustPressed(Keys.ANY_KEY))
				{
					isInfo = false;
					game.game.audioManager.playSound(sfxMAINMENUSELECT);
				}
			}
			else if (isNewGame)
			{
				if (Gdx.input.isKeyJustPressed(Keys.RIGHT))
				{
					if (arrowYesNow.getX() == 534)
					{
						arrowYesNow.setX(678);
						game.game.audioManager.playSound(sfxMAINMENUSELECT);
					}
				}
				else if (Gdx.input.isKeyJustPressed(Keys.LEFT))
				{
					if (arrowYesNow.getX() == 678)
					{
						arrowYesNow.setX(534);
						game.game.audioManager.playSound(sfxMAINMENUSELECT);
					}
				}
				else if (Gdx.input.isKeyJustPressed(Keys.X))
				{
					if (arrowYesNow.getX() == 534)
					{
						isNewGame = false;
					}
					else if (arrowYesNow.getX() == 678)
					{
						game.isNewGame = true;
						game.isFadeOut = true;
					}
					game.game.audioManager.playSound(sfxMAINMENUSELECT);
				}
			}
		}

		//Icon Up
		if (scroll.getScrollPercentY() == 0)
		{
			if (canDrawArrowUp)
				canDrawArrowUp = false;
		}
		else
		{
			if (!canDrawArrowUp)
				canDrawArrowUp = true;
		}
		//Icon Down
		if (scroll.getScrollPercentY() == 1)
		{
			if (canDrawArrowDown)
				canDrawArrowDown = false;
		}
		else
		{
			if (!canDrawArrowDown)
				canDrawArrowDown = true;
		}
		
		scroll.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		title.draw(batch, parentAlpha);
		labelX.draw(batch, parentAlpha);
		labelEsc.draw(batch, parentAlpha);
		labelFullScreen.draw(batch, parentAlpha);
		labelVersion.draw(batch, parentAlpha);
		
		if (isInfo)
		{
			labelInfo.draw(batch, parentAlpha);
		}
		else if (isNewGame)
		{
			labelNewGameMsg.draw(batch, parentAlpha);
			labelYesNo.draw(batch, parentAlpha);
			arrowYesNow.draw(batch, parentAlpha);
		}
		else
		{
			scroll.draw(batch, parentAlpha);
			
			if (!game.isFadeIn)
			{
				if (canDrawArrowUp)
					arrowButtonUp.draw(batch, parentAlpha);
				if (canDrawArrowDown)
					arrowButtonDown.draw(batch, parentAlpha);
			}
		}
	}
}
