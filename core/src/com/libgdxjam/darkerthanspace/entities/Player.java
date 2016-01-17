package com.libgdxjam.darkerthanspace.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.libgdxjam.darkerthanspace.Interf.AudioId;
import com.libgdxjam.darkerthanspace.Interf.FinalStates;
import com.libgdxjam.darkerthanspace.screens.GameScreen;
import com.libgdxjam.darkerthanspace.utils.Assets;
import com.libgdxjam.darkerthanspace.utils.CheckCollision;

public class Player implements FinalStates, AudioId
{
	//Generals
	private GameScreen game;
	private Assets assets;
	
	//Player
	private Rectangle playerRec;
	private Sprite sprite;
	private float stateTime;
	private boolean isLeft;
	private boolean canChangeScene;
	private boolean isOverlapping;
	private boolean isFlashLight;
	public boolean hasFlashLight;
	public boolean isGunUpgraded;
	
	//Gravity
	private Vector2 speed;
	/*private final float gravity = 9.8f;
	private boolean isOnGround;
	private boolean canJump;*/
	
	//Utils
	private int n;
	private int indexPlatf;
	private float oldY;
	private Array<SolidBlock> listSolid;
	public Array<ActBlock> listAct;
	private CheckCollision checkCollision;
	private int state;
	public Array<Item> listItem;
	int i=1;
	private boolean needFixLeftGun;
	
	//Hit/enemy
	private float hitController;
	private boolean isHit;
	private int lives;
	private float restoreController;
	private boolean isDead;
	private int enemySide;
	private int enemyCounterSide;
	
	//Shoot
	public int cooldown;
	public Bullet bullet;
	private boolean bulletLeft;
	
	//Act
	public int indexAct;
	private ActBlock currentAct;
	private boolean canRemoveIten;
	
	//Map
	public boolean hasMap;
	public boolean hasGun;
	
	public Player(GameScreen game, Array<SolidBlock> listSolid, Array<ActBlock> listAct, float x, float y)
	{
		//General
		this.game = game;
		assets = game.game.assets;
		this.listSolid = listSolid;
		this.listAct = listAct;
		
		initialize(x, y);
	}
	
	public Player(GameScreen game, Array<SolidBlock> listSolid, Array<ActBlock> listAct, Vector2 vec2)
	{
		//General
		this.game = game;
		assets = game.game.assets;
		this.listSolid = listSolid;
		this.listAct = listAct;
		
		initialize(vec2.x, vec2.y);
	}
	
	private void initialize(float x, float y)
	{
		//Gravity / Speed
		speed = new Vector2(70.0f, 0f);
		/*isOnGround = false;
		canJump = true;*/
		
		//Utils
		checkCollision = new CheckCollision();
		indexPlatf = -1;
		listItem = new Array<Item>();
		isOverlapping = false;
		canRemoveIten = false;
		isFlashLight = false;
		isLeft = false;
		canChangeScene = true;
		
		//Shoot
		bullet = new Bullet(assets);
		cooldown = 2;
		isGunUpgraded = false;
		
		loadStuff();
		
		//Enemy
		hitController = 0;
		restoreController = 0;
		isHit = false;
		lives = 3;
		isDead = false;
		enemySide = -1;
		enemyCounterSide = 0;
		
		//Act
		indexAct = -1;
		
		//Sprite
		sprite = new Sprite(assets.skin.getRegion("charStopRight"));
		//sprite.setSize(19, 42);
		sprite.setSize((int)(73/3f), (int)(163/3f));
		stateTime = 0;

		playerRec = new Rectangle(x-sprite.getWidth()/4, y-2, sprite.getWidth(), sprite.getHeight());
	}
	
	private void loadStuff()
	{
		hasMap = game.game.prefs.load("hasmap");
		hasFlashLight = game.game.prefs.load("hasflashlight");
		hasGun = game.game.prefs.load("hasgun");
		isGunUpgraded = game.game.prefs.load("s12t3id1");
		if (isGunUpgraded)
		{
			cooldown = 3;
		}
		
		n = game.game.prefs.loadInventory("itemsize");
		if (n > 0)
		{
			for (int i=0; i<n; i++)
			{
				listItem.add(new Item(game.game.prefs.loadInventory("itemid"+i)));
			}
		}
	}

	public void update(float delta)
	{
		switch (game.gameState)
		{
		case INGAME:
			if (!game.fadeStart && !game.canChangeScene && !isDead)
			{
				if (stateTime > 1000)
					stateTime = 0;
				
				//Inputs
				/*if (Gdx.input.isKeyJustPressed(Keys.UP) && canJump)
				{
					speed.y = 12.5f;
					isOnGround = false;
					canJump = false;
				}*/
				
				
				if (!game.isShooting && !isHit)
				{
					//Reset sprite
					if (sprite.getWidth() != playerRec.getWidth())
					{
						sprite.setSize((int)(73/3f), (int)(163/3f));
						needFixLeftGun = false;
					}
					
					if (Gdx.input.isKeyJustPressed(Keys.P))
					{
						game.gameState = PAUSE;
					}
					
					if (Gdx.input.isKeyJustPressed(Keys.F) && hasFlashLight)
					{
						isFlashLight = !isFlashLight;
						if (hasFlashLight)
						{
							game.game.audioManager.playSound(sfxFLASHLIGHT);
						}
					}
					
					if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !game.canBullet && hasGun)
					{
						game.isShooting = true;
						bullet.setStartPos(playerRec, isLeft);
						bullet.setDead(false);
						bulletLeft = isLeft;
						if (isLeft)
						{
							sprite.setRegion(assets.skin.getRegion("playerGunLeft"));
							needFixLeftGun = true;
						}
						else
						{
							sprite.setRegion(assets.skin.getRegion("playerGunRight"));
						}
						sprite.setSize((int)(95/3f), (int)(163/3f));
						game.setBarPos(playerRec.x, playerRec.y);
					}
					
					
					if (!game.isShooting)
					{
						if (Gdx.input.isKeyPressed(Keys.RIGHT))
						{
							stateTime += delta*6.5f;
							sprite.setRegion(assets.animationRight.getKeyFrame(stateTime, true));
							
							n = 0;
							for (int i=0; i< game.listEnemy.size; i++)
							{
								if (checkCollision.check(playerRec, game.listEnemy.get(i).getRec()) == RIGHT && !game.listEnemy.get(i).isFreeze())
								{
									n++;
								}
							}
							
							if (n == 0)
							{
								playerRec.x += speed.x*delta;
								if (playerRec.x > game.mapW-19)
									playerRec.x = game.mapW-19;
								isLeft = false;
							}
							
						/*	if ( enemySide != RIGHT)
							{

							}*/
							
						}
							else if (Gdx.input.isKeyPressed(Keys.LEFT))
							{
								stateTime += delta*6.5f;
								sprite.setRegion(assets.animationLeft.getKeyFrame(stateTime, true));
								
								n = 0;
								for (int i=0; i< game.listEnemy.size; i++)
								{
									if (checkCollision.check(playerRec, game.listEnemy.get(i).getRec()) == LEFT && !game.listEnemy.get(i).isFreeze() )
									{
										n++;
									}
								}
								
								if (n == 0)
								{
									playerRec.x -= speed.x*delta;
									if (playerRec.x < 0)
										playerRec.x = 0;
									isLeft = true;
								}
								
								
								
							/*	if (enemySide != LEFT)
								{

								}*/
							}
								else
								{
									if (isLeft)
										sprite.setRegion(assets.skin.getRegion("charStopLeft"));
									else
										sprite.setRegion(assets.skin.getRegion("charStopRight"));
								}
					
						//Check later if Y is changed up or down
						oldY = playerRec.y;
						
						//High Limit
						/*if (!isOnGround)
						{
							playerRec.y += (speed.y*delta*gravity);
							speed.y -= gravity*delta;
							if (speed.y < -10)
								speed.y = -10;
						}*/
						
						/*if (playerRec.y+playerRec.height > 192)
						{
							playerRec.y= 192-playerRec.height;
							speed.y = -1;
						}*/
						if (playerRec.y < 30)
						{
							playerRec.y = 30;
						}
					}
				}
				
				checkCollision(delta);
			}
		break;
		case TEXT:
			if (sprite.getWidth() != playerRec.getWidth())
			{
				needFixLeftGun = false;
				sprite.setSize((int)(73/3f), (int)(163/3f));
			}
			if (isLeft)
				sprite.setRegion(assets.skin.getRegion("charStopLeft"));
			else
				sprite.setRegion(assets.skin.getRegion("charStopRight"));
		break;
		case CHANGESCENE:
			if (sprite.getWidth() != playerRec.getWidth())
			{
				needFixLeftGun = false;
				sprite.setSize((int)(73/3f), (int)(163/3f));
			}
			if (canChangeScene)
			{
				if (isLeft)
					sprite.setRegion(assets.skin.getRegion("charStopLeft"));
				else
					sprite.setRegion(assets.skin.getRegion("charStopRight"));
				canChangeScene = false;
			}
		break;
		case FINISHMENU:
			if (sprite.getWidth() != playerRec.getWidth())
			{
				needFixLeftGun = false;
				sprite.setSize((int)(73/3f), (int)(163/3f));
			}
			if (canChangeScene)
			{
				if (isLeft)
					sprite.setRegion(assets.skin.getRegion("charStopLeft"));
				else
					sprite.setRegion(assets.skin.getRegion("charStopRight"));
				canChangeScene = false;
			}
		break;
		case RESETGAME:
			if (sprite.getWidth() != playerRec.getWidth())
			{
				needFixLeftGun = false;
				sprite.setSize((int)(73/3f), (int)(163/3f));
			}
			if (canChangeScene)
			{
				if (isLeft)
					sprite.setRegion(assets.skin.getRegion("charStopLeft"));
				else
					sprite.setRegion(assets.skin.getRegion("charStopRight"));
				canChangeScene = false;
			}
		break;
		case MENU:
			if (sprite.getWidth() != playerRec.getWidth())
			{
				needFixLeftGun = false;
				sprite.setSize((int)(73/3f), (int)(163/3f));
			}
			if (isLeft)
				sprite.setRegion(assets.skin.getRegion("charStopLeft"));
			else
				sprite.setRegion(assets.skin.getRegion("charStopRight"));
		break;
		default:
		break;
		}
		
		if (!game.isShooting && game.gameState != CHANGESCENE)
		{
			if (game.canBullet)
			{
				bullet.update(delta, 200, bulletLeft, game.listEnemy, listSolid);
				if (bullet.getEnemyHit() > -1)
				{
					game.listEnemy.get(bullet.getEnemyHit()).setFreeze(isGunUpgraded);
					bullet.resetEnemyHit();
				}
				game.setBarPos(playerRec.x, playerRec.y);
			}
			else if (!game.isShooting)
			{
				bullet.setDead(true);
			}
		}
	}
	
	public void checkCollision(float delta)
	{
		//Collision with solid
		n = listSolid.size;
		for (int i=0; i<n; i++)
		{
			state = checkCollision.check(playerRec, listSolid.get(i).getRec());
			
			if (i == indexPlatf)
			{
				//System.out.println("INDEX");
				indexPlatf = -1;
				//isOnGround = false;
				if (speed.y < 10)
					speed.y = -2;
			}
			else if (state == LEFT)
			{
				//System.out.println("LEFT");
				playerRec.x = listSolid.get(i).getXWidth();
			}
			else if (state == RIGHT)
			{
				//System.out.println("RIGHT");
				playerRec.x = listSolid.get(i).getX()-playerRec.width;
			}
			else if (state == DOWN)
			{
				if (oldY > playerRec.y)
				{
					//canJump = true;
					//System.out.println("DOWN");
					playerRec.y = listSolid.get(i).getYHeight();
					//isOnGround = true;
					indexPlatf = i;
				}
			}
			else if (state == UP)
			{
				if (oldY < playerRec.y)
				{
					//System.out.println("UP");
					playerRec.y = listSolid.get(i).getY()-playerRec.height;
					speed.y = 0;
				}
			}
		}
		
		
		//Stop HB Sound
		if (lives == 3)
		{
			game.game.audioManager.stopLoopingSound(sfxHEARTBEAT);
		}
		
		//Collision with enemy
		if (!isHit)
		{
			//Invunerable
			if (hitController != 0)
			{
				hitController += delta;
				if (2-hitController < 0)
				{
					hitController = 0;
				}
				n = game.listEnemy.size;
				for (int i=0; i<n; i++)
				{
					if (playerRec.overlaps(game.listEnemy.get(i).getRec()) && !game.listEnemy.get(i).isFreeze())
					{
						enemySide = checkCollision.check(playerRec, game.listEnemy.get(i).getRec());
					}
					else
					{
						enemyCounterSide++;
					}
				}
				if (enemyCounterSide == n)
				{
					enemySide = -1;
					enemyCounterSide = 0;
				}
			}
			else
			{
				if (lives < 3 && lives > 0)
				{
					restoreController += delta;
					if (5-restoreController <= 0)
					{
						lives++;
						restoreController = 0;
					}
				}
				n = game.listEnemy.size;
				for (int i=0; i<n; i++)
				{
					if (playerRec.overlaps(game.listEnemy.get(i).getRec()) && !game.listEnemy.get(i).isFreeze())
					{
						enemySide = checkCollision.check(playerRec, game.listEnemy.get(i).getRec());
						isHit = true;
						game.game.audioManager.playSound(sfxHIT);
						game.listEnemy.get(i).setTarget(true);
						isFlashLight = false;
						lives--;
						if (lives <= 0)
						{
							isDead = true;
							
							if (game.listEnemy.get(i).isBig())
							{
								game.game.audioManager.playSound(sfxDEADBIG);
							}
							else
							{
								game.game.audioManager.playSound(sfxDEAD);
							}
							
							game.canResetGame = true;
							game.gameState = RESETGAME;
						}
						else
						{
							game.game.audioManager.playSound(sfxHEARTBEAT);
						}
					}
				}
			}
		}
		else
		{
			//Hit
			hitController += delta;
			
			if (!game.isShooting)
			{
				if (isLeft)
					sprite.setRegion(assets.skin.getRegion("charStopLeft"));
				else
					sprite.setRegion(assets.skin.getRegion("charStopRight"));
				
				if (sprite.getWidth() != playerRec.getWidth())
				{
					needFixLeftGun = false;
					sprite.setSize((int)(73/3f), (int)(163/3f));
				}
			}
			
			if (1-hitController < 0)
			{
				isHit = false;
				enemySide = -1;
			}
		}
		
		//Collision with Act
		if (indexAct == -1)
		{
			n = listAct.size;
			for (int i=0; i<n; i++)
			{
				if (playerRec.overlaps(listAct.get(i).getRec()))
				{
					currentAct = listAct.get(i);
					indexAct = i;
					game.canAct = true;
					isOverlapping = true;
					game.setAction(currentAct);
					break;
				}
			}
		}
		else
		{
			if (!playerRec.overlaps(listAct.get(indexAct).getRec()))
			{
				indexAct = -1;
				isOverlapping = false;
				game.canAct = false;
				game.eventInGame.removeEvent();
			}
			else
			{
				game.setActionPos(currentAct.getCenterX(), currentAct.getYHeight());
				if (!game.isShooting)
				{
					if (currentAct.getType() == ACT)
					{
						if (Gdx.input.isKeyJustPressed(Keys.X) && game.gameState == INGAME && currentAct.getParts() > 0 && !game.canDefaultText)
						{
							if (game.eventInGame.getScene() == 9 && currentAct.getTypeId() == 0)
							{
								game.canSaveProgress = true;
							}
							else
							{
								game.gameState = TEXT;
							}
						}
					}
						else if (currentAct.getType() == DOOR)
						{
							if (!game.fadeStart)
							{
								if (Gdx.input.isKeyJustPressed(Keys.X))
								{
									if (game.eventInGame.isDoorUnlocked())
									{
										if (game.isBombDestroyed && game.eventInGame.getScene() ==13 && currentAct.getType() == DOOR && currentAct.getType() == 0)
										{
											game.finishGame = true;
										}
										else if (game.eventInGame.getScene() == 0 && currentAct.getTypeId() == 0)
										{
											game.game.audioManager.playSound(sfxSCAREWIND);
										}
										else
										{
											game.game.audioManager.playSound(sfxDOOR);
										}
										game.canChangeScene = true;
										game.gameState = CHANGESCENE;
										game.fade = 0;
										//game.inGameEvent.changeScene();
									}
								}
							}
						}
							else if (currentAct.getType() == MAPITEM)
							{
								if (Gdx.input.isKeyJustPressed(Keys.X))
								{
									if (currentAct.getParts() > 0)
										game.gameState = TEXT;
									isOverlapping = false;
									canRemoveIten = true;
									if (game.eventInGame.getScene() == 12 && currentAct.getTypeId() == 1)
									{
										;
									}
									else
									{
										listItem.add(new Item(currentAct.getItemId()));
										game.canPickupItem = true;
									}
								}
								if (canRemoveIten && game.gameState != TEXT)
								{
									currentAct = null;
									listAct.removeIndex(indexAct);
									indexAct = -1;
									game.canAct = false;
									canRemoveIten = false;
								}
							}
				}
			}
		}
	}
	
	public void draw(Batch batch)
	{
		if (needFixLeftGun)
			sprite.setPosition(playerRec.x-9, playerRec.y);
		else
			sprite.setPosition(playerRec.x, playerRec.y);
		sprite.draw(batch);
		
		if (!bullet.isDead() && game.canBullet)
			bullet.draw(batch);
	}
	
	public void drawDebug(ShapeRenderer shape)
	{
		shape.setColor(Color.GREEN);
		shape.rect(playerRec.x, playerRec.y, playerRec.width, playerRec.height);
	}
	

	public void setLists(Array<SolidBlock> listSolidBlock, Array<ActBlock> listActBlock, int typeId)
	{
		listSolid = listSolidBlock;
		listAct = listActBlock;
		canChangeScene = true;
		indexAct = -1;
		isOverlapping = false;
		
		n = listAct.size;
		
		if (typeId == -1)
		{
			playerRec.setPosition(game.eventInGame.getStartPosition());
			playerRec.y -= 2;
		}
		else
		{
			for (int i=0; i<n; i++)
			{
				if (listAct.get(i).getType() == DOOR && listAct.get(i).getTypeId() == typeId)
				{
					playerRec.x = listAct.get(i).getX()-playerRec.width/4;
					playerRec.y = listAct.get(i).getY();
					if (listAct.get(i).getSideDoor() == 0 || listAct.get(i).getSideDoor() == 3)
					{
						playerRec.y -= 2;
					}
					else if (listAct.get(i).getSideDoor() == 2)
					{
						playerRec.y -= 2;
						if (listActBlock.get(i).getX() == 0)
						{
							playerRec.x = listAct.get(i).getXWidth();
						}
						else
						{
							playerRec.x = listAct.get(i).getX()-playerRec.width;
						}
					}
					break;
				}
			}
		}
	}
	
	public float getX()
	{
		return playerRec.x;
	}
	
	public void setX(float value)
	{
		playerRec.x = value;
	}
	
	public float getY()
	{
		return playerRec.y;
	}
	
	public float setY(float value)
	{
		return playerRec.y = value;
	}
	
	public float getWidth()
	{
		return playerRec.width;
	}
	public float getXWidth()
	{
		return playerRec.x + playerRec.width;
	}
	
	public float getYHeight()
	{
		return playerRec.y+playerRec.height;
	}
	
	public Rectangle getRec()
	{
		return playerRec;
	}
	
	public float getCenterX()
	{
		return playerRec.x + playerRec.width/2;
	}
	
	public void setOnGroud(boolean value)
	{
		//isOnGround = value;
	}
	
	public Array<Item> getListItem()
	{
		return listItem;
	}
	
	public boolean hasMap()
	{
		return hasMap;
	}
	
	public boolean isOverlapping()
	{
		return isOverlapping;
	}
	
	public void removeAct()
	{
		listAct.removeIndex(indexAct);
		indexAct = -1;
		isOverlapping = false;
		game.canAct = false;
		game.eventInGame.removeEvent();
	}
	
	public void removeSolid(int index)
	{
		listSolid.removeIndex(index);
	}
	
	public void setSide(boolean isLeft)
	{
		this.isLeft = isLeft;
		if (sprite.getWidth() != playerRec.getWidth())
		{
			needFixLeftGun = false;
			sprite.setSize((int)(73/3f), (int)(163/3f));
		}
	}
	
	public boolean isFlashLight()
	{
		return isFlashLight;
	}
	
	public void setGunUpgrade()
	{
		cooldown = 3;
		isGunUpgraded = true;
	}
	
	public boolean isDead()
	{
		return isDead;
	}
	
	public void playerShoot()
	{
		if (isLeft)
		{
			sprite.setRegion(assets.skin.getRegion("playerGunFireLeft"));
			needFixLeftGun = true;
		}
		else
		{
			sprite.setRegion(assets.skin.getRegion("playerGunFireRight"));
		}
	}
	
	public int getLives()
	{
		return lives;
	}
	
	public int getEnemySide()
	{
		return enemySide;
	}
}
