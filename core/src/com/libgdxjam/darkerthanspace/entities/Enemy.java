package com.libgdxjam.darkerthanspace.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.libgdxjam.darkerthanspace.Interf.AudioId;
import com.libgdxjam.darkerthanspace.Interf.LightColor;
import com.libgdxjam.darkerthanspace.screens.GameScreen;
import com.libgdxjam.darkerthanspace.utils.Assets;

public class Enemy implements LightColor, AudioId
{
	//General
	private GameScreen game;
	private Assets assets;
	private Rectangle enemyRec;
	private Vector2 startPos;
	//private float oldX;
	private Sprite enemySprite;
	private int type;
	
	//Path
	private Polyline pol;
	
	//Bools
	private boolean isTarget; //Targeting player
	private boolean isFreeze;
	private boolean isHorizontal;
	public boolean isLeft;
	private boolean isPlayerLeft;
	//private boolean isUp;
	
	//TimeController
	private float freezeController;
	private float freezeTime;
	
	//Range
	private int normalRange;
	private int targetRange;
	
	//Speed
	private float speed;
	
	//Animation
	private float stateTime;
	private float soundController;
	
	//Scene
	private int scene;
	
	private final int enemyNORMAL = 0, enemyBIG = 1;
	
	public Enemy(Polyline pol, Vector2 pos, int scene, GameScreen game)
	{
		this.game = game;
		this.pol = pol;
		startPos = pos;
		assets = game.game.assets;
		this.scene = scene;
		
		initialize();
	}
	
	private void initialize()
	{
		switch (scene)
		{
		case 5:
			enemySprite = new Sprite(assets.animationBigEnemy.getKeyFrame(0.1f));
			enemySprite.setSize(enemySprite.getWidth()/2, enemySprite.getHeight()/2);
			speed = 5f;
			type = enemyBIG;
			enemyRec = new Rectangle(startPos.x, startPos.y, enemySprite.getWidth()-10, enemySprite.getHeight());
			//Range
			normalRange = 500;
			targetRange = 500;
			//Freeze
			freezeTime = 1;
			soundController = 0;
		break;
		
		default:
			enemySprite = new Sprite(assets.animationNormalEnemyRight.getKeyFrame(0.1f));
			enemySprite.setSize(enemySprite.getWidth()/2, enemySprite.getHeight()/2);
			speed = 8f;
			type = enemyNORMAL;
			enemyRec = new Rectangle(startPos.x, startPos.y, enemySprite.getWidth()-5, enemySprite.getHeight());
			//Range
			normalRange = 50;
			targetRange = 80;
			freezeTime = 3;
		break;
		}
		
		stateTime = 0;
		
		isTarget = false;
		isFreeze = false;
		freezeController = 0;
		
		//Horizontal
		if (pol.getVertices()[0] != pol.getVertices()[2])
		{
			isHorizontal = true;
			if (pol.getVertices()[2] < 0)
			{
				isLeft = true;
			}
		}
		//Vertical
		else if (pol.getVertices()[1] != pol.getVertices()[3])
		{
			isHorizontal = false;
			//isUp = true;
			if (this.pol.getVertices()[3] < 0)
			{
				this.pol.getVertices()[3] *= -1;
			}
		}
	}

	public void update(float delta)
	{
		//Check player side
		if (game.player.getCenterX() < enemyRec.x)
		{
			isPlayerLeft = true;
		}
		else if (game.player.getCenterX() > enemyRec.x+enemyRec.width)
		{
			isPlayerLeft = false;
		}
		
		if (!isFreeze)
		{
			//No target
			if (!isTarget)
			{
				//Horizontal
				if (isHorizontal)
				{
					if (isLeft)
					{
						enemyRec.x -= pol.getVertices()[2]/speed*delta;
						
						//Change Direction
						if (enemyRec.x-startPos.x <= 0)
						{
							isLeft = false;
						}
						
						//Player range
						if (isPlayerLeft)
						{
							if (game.player.isFlashLight())
							{
								if (enemyRec.x - game.player.getCenterX() < normalRange*1.5f)
								{
									isTarget = true;
									isLeft = true;
								}
							}
							else
							{
								if (enemyRec.x - game.player.getCenterX() < normalRange)
								{
									isTarget = true;
									isLeft = true;
								}
							}
						}
						else if (game.player.isFlashLight() || type == enemyBIG)
						{
							if (game.player.getCenterX() - (enemyRec.x + enemyRec.width) < normalRange/2)
							{
								isTarget = true;
								isLeft = false;
							}
						}
					}
					else
					{
						enemyRec.x += pol.getVertices()[2]/speed*delta;
						
						//Change Direction
						if (enemyRec.x-startPos.x >= pol.getVertices()[2]-32)
						{
							isLeft = true;
						}
						
						//Player range
						if (!isPlayerLeft)
						{
							if (game.player.isFlashLight())
							{
								if (game.player.getCenterX() - (enemyRec.x+enemyRec.width) < normalRange*1.2f)
								{
									isTarget = true;
									isLeft = false;
								}
							}
							else
							{
								if (game.player.getCenterX() - (enemyRec.x+enemyRec.width) < normalRange)
								{
									isTarget = true;
									isLeft = false;
								}
							}
						}
						else if (game.player.isFlashLight() || type == enemyBIG)
						{
							if (enemyRec.x - game.player.getCenterX() < normalRange/2)
							{
								isTarget = true;
								isLeft = true;
							}
						}
					}
				}
				//Veritical
				/*else
				{
					if (isUp)
					{
						enemyRec.y += pol.getVertices()[3]/speed*delta;
						if (enemyRec.y-startPos.y >= pol.getVertices()[3]-64)
						{
							isUp = false;
						}
					}
					else
					{
						enemyRec.y -= pol.getVertices()[3]/speed*delta;
						
						if (enemyRec.y-startPos.y <= 0)
						{
							isUp = true;
						}
					}
				}*/
			}
			else //isTargeting
			{
				if (type == enemyBIG)
				{
					if (isPlayerLeft)
						isLeft = true;
					else
						isLeft = false;
				}
				if (isPlayerLeft)
				{
					if (game.player.isFlashLight())
					{
						if (enemyRec.x - game.player.getCenterX() < targetRange*1.5f) //Left
						{
								if (enemyRec.x <= game.player.getXWidth()-1)
									enemyRec.x = game.player.getXWidth()-1;
								else
									enemyRec.x -= pol.getVertices()[2]/speed*2f*delta;
						}
						else
						{
							isTarget = false;
						}
					}
					else
					{
						if (enemyRec.x - game.player.getCenterX() < targetRange) //Left
						{
								if (enemyRec.x <= game.player.getXWidth()-1)
									enemyRec.x = game.player.getXWidth()-1;
								else
									enemyRec.x -= pol.getVertices()[2]/speed*2f*delta;
						}
						else
						{
							isTarget = false;
						}
					}
				}
				else
				{
					if (game.player.isFlashLight())
					{
						if (game.player.getCenterX() - (enemyRec.x+enemyRec.width) < targetRange*1.5f)
						{
							if (enemyRec.x+enemyRec.width >= game.player.getX()+1)
								enemyRec.x = game.player.getX()+1-enemyRec.width;
							else
								enemyRec.x += pol.getVertices()[2]/speed*2f*delta;
						}
						else
						{
							isTarget = false;
						}
					}
					else
					{
						if (game.player.getCenterX() - (enemyRec.x+enemyRec.width) < targetRange)
						{
							if (enemyRec.x+enemyRec.width >= game.player.getX()+1)
								enemyRec.x = game.player.getX()+1-enemyRec.width;
							else
								enemyRec.x += pol.getVertices()[2]/speed*2f*delta;
						}
						else
						{
							isTarget = false;
						}
					}
				}
			}
			
			if (stateTime > 1000)
				stateTime = 0;
			
			stateTime += delta;
			soundController += delta;
			
			if (type == enemyNORMAL)
			{
				if (isLeft)
				{
					enemySprite.setRegion(assets.animationNormalEnemyLeft.getKeyFrame(stateTime, true));
				}
				else
				{
					enemySprite.setRegion(assets.animationNormalEnemyRight.getKeyFrame(stateTime, true));
				}
				
				enemyRec.setSize(enemySprite.getWidth()-5, enemySprite.getHeight());
			}
			else
			{
				enemySprite.setRegion(assets.animationBigEnemy.getKeyFrame(stateTime, true));
				if (0.5f-soundController < 0)
				{
					game.game.audioManager.playSound(sfxWALKBIGENEMY);
					soundController = 0;
				}
				enemyRec.setSize(enemySprite.getWidth()-10, enemySprite.getHeight());
				if (isLeft)
				{
					if (!enemySprite.isFlipX())
						enemySprite.setFlip(true, false);
				}
				else
				{
					if (enemySprite.isFlipX())
						enemySprite.setFlip(false, false);
				}
			}
		}
		else
		{
			freezeController += delta;
			if (freezeTime - freezeController <= 0)
			{
				freezeController = 0;
				isFreeze = false;
				game.game.audioManager.playSound(sfxICEBROKEN);
				enemySprite.setColor(Color.WHITE);
			}
		}
	}
	
	public void draw(Batch batch)
	{
		if (type == enemyNORMAL)
		{
			enemySprite.setPosition(enemyRec.x-2.5f, enemyRec.y-3);
		}
		else
		{
			enemySprite.setPosition(enemyRec.x-5, enemyRec.y-8);
		}
		enemySprite.draw(batch);
	}
	
	public void drawDebug(ShapeRenderer shape)
	{
		if (isHorizontal)
			shape.setColor(Color.RED);
		else
			shape.setColor(Color.CYAN);
		shape.rect(enemyRec.x, enemyRec.y, enemyRec.width, enemyRec.height);
		if (!isTarget)
		{
			if (game.player.isFlashLight())
			{
				if(!isLeft)
					shape.line(enemyRec.x-normalRange/2, enemyRec.getY()+enemyRec.getHeight()/2, enemyRec.x+enemyRec.width+normalRange*1.5f, enemyRec.getY()+enemyRec.getHeight()/2);
				else
					shape.line(enemyRec.x+enemyRec.width+normalRange/2, enemyRec.getY()+enemyRec.getHeight()/2, enemyRec.x-normalRange*1.5f, enemyRec.getY()+enemyRec.getHeight()/2);
			}
			else
			{
				if(!isLeft)
					shape.line(enemyRec.x+enemyRec.width, enemyRec.getY()+enemyRec.getHeight()/2, enemyRec.x+enemyRec.width+normalRange, enemyRec.getY()+enemyRec.getHeight()/2);
				else
					shape.line(enemyRec.x, enemyRec.getY()+enemyRec.getHeight()/2, enemyRec.x-normalRange, enemyRec.getY()+enemyRec.getHeight()/2);
			}
		}
		else
		{
			if (game.player.isFlashLight())
			{
				if(!isPlayerLeft)
					shape.line(enemyRec.x+enemyRec.width, enemyRec.getY()+enemyRec.getHeight()/2, enemyRec.x+enemyRec.width+targetRange*1.2f, enemyRec.getY()+enemyRec.getHeight()/2);
				else
					shape.line(enemyRec.x, enemyRec.getY()+enemyRec.getHeight()/2, enemyRec.x-targetRange*1.2f, enemyRec.getY()+enemyRec.getHeight()/2);
			}
			else
			{
				if(!isPlayerLeft)
					shape.line(enemyRec.x+enemyRec.width, enemyRec.getY()+enemyRec.getHeight()/2, enemyRec.x+enemyRec.width+targetRange, enemyRec.getY()+enemyRec.getHeight()/2);
				else
					shape.line(enemyRec.x, enemyRec.getY()+enemyRec.getHeight()/2, enemyRec.x-targetRange, enemyRec.getY()+enemyRec.getHeight()/2);
			}
		}
	}
	
	public Rectangle getRec()
	{
		return enemyRec;
	}
	
	public boolean isFreeze()
	{
		return isFreeze;
	}

	public void setFreeze(boolean isUpgraded)
	{
		isFreeze = true;
		game.game.audioManager.playSound(sfxICEON);
		if (isUpgraded)
		{
			if (type == enemyNORMAL)
			{
				freezeTime = 4;
			}
			else
			{
				freezeTime = 3.5f;
			}
		}
		enemySprite.setColor(iceCOLOR);
	}

	public void setTarget(boolean bool)
	{
		isTarget = bool;
		if (isPlayerLeft)
			isLeft = true;
		else
			isLeft = false;
	}

	public float getX()
	{
		return enemyRec.x;
	}
	
	public float getWidth()
	{
		return enemyRec.width;
	}
	
	public float getY()
	{
		return enemyRec.y;
	}
	
	public float getHeight()
	{
		return enemyRec.height;
	}

	public boolean isBig()
	{
		if (type == enemyBIG)
			return true;
		else
			return false;
	}
}
