package com.libgdxjam.darkerthanspace.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.libgdxjam.darkerthanspace.screens.GameOverScreen;
import com.libgdxjam.darkerthanspace.utils.Assets;

public class UiGameOver extends Group
{
	//General
	private GameOverScreen game;
	private int resW, resH;
	private Assets assets;
	
	//Labes
	private Label labelTitle;
	private Label labelCreated;
	private Label labelLibgdx;
	private Label labelAnyKey;
	
	//Booleans
	private boolean canLabelTitle;
	private boolean canLabelCreated;
	private boolean canLabellibgdx;
	private boolean canLabelAnyKey;
	
	//Fade
	private float fade;
	private boolean canFade;
	
	public UiGameOver(GameOverScreen game)
	{
		this.game = game;
		initialize();
	}
	
	public void initialize()
	{
		//General
		resW = game.resW;
		resH = game.resH;
		assets = game.game.assets;
		
		//Label
		labelTitle = new Label("thanks for playing", new LabelStyle(assets.manager.get(assets.pathFont60, BitmapFont.class), Color.WHITE));
		labelTitle.setAlignment(1, 1);
		labelTitle.setFontScale(2f);
		GlyphLayout layout = new GlyphLayout(labelTitle.getStyle().font, labelTitle.getText());
		labelTitle.setPosition(resW/2-labelTitle.getWidth()/2, resH-labelTitle.getHeight()-120);
		
		labelCreated = new Label("this demo was created by\n'Marcio Machida'", labelTitle.getStyle());
		labelCreated.setAlignment(1, 1);
		layout.setText(labelTitle.getStyle().font, labelCreated.getText());
		labelCreated.setPosition(resW/2-labelCreated.getWidth()/2, resH/2-labelCreated.getHeight()/2+30);
		
		labelLibgdx = new Label("for the first\n#libgdxjam", labelTitle.getStyle());
		labelLibgdx.setAlignment(1, 1);
		layout.setText(labelTitle.getStyle().font, labelLibgdx.getText());
		labelLibgdx.setPosition(resW/2-labelLibgdx.getWidth()/2, labelCreated.getY()-140);
		
		labelAnyKey = new Label("press any key", labelTitle.getStyle());
		labelAnyKey.setAlignment(1, 1);
		layout.setText(labelTitle.getStyle().font, labelAnyKey.getText());
		labelAnyKey.setPosition(resW/2-layout.width/2, 60);
		
		//Booleans
		canLabelTitle = false;
		canLabelCreated = false;
		canLabellibgdx = false;
		canLabelAnyKey = false;
		
		//Fade
		fade = 0;
		canFade = true;
		
		//Add
		addActor(labelTitle);
		addActor(labelCreated);
		addActor(labelLibgdx);
	}

	@Override
	public void act(float delta)
	{
		if (!game.isFadeIn)
		{
			if (canFade)
			{
				fade += delta/4f;
				
				if (fade > 1f)
				{
					if (!canLabelTitle)
					{
						canLabelTitle = true;
						fade = 0;
					}
						else if (!canLabelCreated)
						{
							canLabelCreated = true;
							fade = 0;
						}
							else if (!canLabellibgdx)
							{
								canLabellibgdx = true;
								fade = 0;
							}
								else if (!canLabelAnyKey)
								{
									canLabelAnyKey = true;
									fade = 0;
									canFade = false;
								}
				}
			}
			else
			{
				if (Gdx.input.isKeyJustPressed(Keys.ANY_KEY))
				{
					game.isFadeOut = true;
				}
			}
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if (canLabelTitle)
			labelTitle.draw(batch, 1f);
		else
			labelTitle.draw(batch, fade);
		
		if (canLabelCreated)
			labelCreated.draw(batch, 1f);
		else if (canLabelTitle)
			labelCreated.draw(batch, fade);
		
		if (canLabellibgdx)
			labelLibgdx.draw(batch, 1f);
		else if (canLabelCreated)
			labelLibgdx.draw(batch, fade);
		
		if (canLabelAnyKey)
			labelAnyKey.draw(batch, 1f);
		else if (canLabellibgdx)
			labelAnyKey.draw(batch, fade);
	}
}
