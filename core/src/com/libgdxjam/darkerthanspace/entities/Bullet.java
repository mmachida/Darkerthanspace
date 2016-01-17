package com.libgdxjam.darkerthanspace.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.libgdxjam.darkerthanspace.utils.Assets;

public class Bullet
{
	//General
	private Sprite bulletSprite;
	private Rectangle bulletRec;
	private Assets assets;
	private float startPos;

	//Utils
	private boolean isDead;
	private int n;
	private int enemyHit;
	
	public Bullet(Assets assets)
	{
		this.assets = assets;
		
		initiliaze();
	}
	
	private void initiliaze()
	{
		bulletSprite = new Sprite(assets.skin.getRegion("bullet"));
		bulletRec = new Rectangle(0, 0, bulletSprite.getWidth(), bulletSprite.getHeight());
		isDead = false;
		enemyHit = -1;
	}
	
	public void setStartPos(Rectangle playerRec, boolean bulletLeft)
	{
		if (bulletLeft)
			bulletRec.setPosition(playerRec.x-bulletRec.getWidth(), playerRec.y+93/3f-bulletRec.getHeight()/2);
		else
			bulletRec.setPosition(playerRec.x+playerRec.getWidth()+bulletRec.getWidth(), playerRec.y+93/3f-bulletRec.getHeight()/2);
		startPos = bulletRec.x;
		isDead = false;
	}
	
	public void update(float delta, int range, boolean bulletLeft, Array<Enemy> listEnemy, Array<SolidBlock> listBlock)
	{
		if (!isDead)
		{
			if (bulletLeft)
			{
				bulletRec.x -= 120*delta;
				if (bulletRec.x < startPos-range)
					isDead = true;
			}
			else
			{
				bulletRec.x += 120*delta;
				if (bulletRec.x > startPos+range)
					isDead = true;
			}
			
			n = listEnemy.size;
			for (int i=0; i<n; i++)
			{
				if (!listEnemy.get(i).isFreeze())
				{
					if (bulletRec.overlaps(listEnemy.get(i).getRec()))
					{
						isDead = true;
						enemyHit = i;
						break;
					}
				}
			}
			n = listBlock.size;
			for (int i=0; i<n; i++)
			{
				if (bulletRec.overlaps(listBlock.get(i).getRec()))
				{
					isDead = true;
					break;
				}
			}
		}
	}
	
	public void draw(Batch batch)
	{
		bulletSprite.setPosition(bulletRec.x, bulletRec.y);
		bulletSprite.draw(batch);
	}
	
	public boolean isDead()
	{
		return isDead;
	}
	
	public void setDead(boolean bool)
	{
		if (bool != isDead)
			isDead = bool;
	}
	
	public int getEnemyHit()
	{
		return enemyHit;
	}
	
	public void resetEnemyHit()
	{
		enemyHit = -1;
	}

	public float getX()
	{
		return bulletRec.x;
	}
	
	public float getWidth()
	{
		return bulletRec.width;
	}
	
	public float getY()
	{
		return bulletRec.y;
	}
	
	public float getHeight()
	{
		return bulletRec.height;
	}
}
