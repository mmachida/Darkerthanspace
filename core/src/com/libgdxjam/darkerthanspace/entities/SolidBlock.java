package com.libgdxjam.darkerthanspace.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class SolidBlock
{
	private Rectangle blockRec;
	private int id;
	
	public SolidBlock(Rectangle rec, int id)
	{
		blockRec = rec;
		this.id = id;
	}
	
	public void drawDebug(ShapeRenderer shape)
	{
		shape.setColor(Color.LIGHT_GRAY);
		shape.rect(blockRec.x, blockRec.y, blockRec.width, blockRec.height);
	}
	
	public Rectangle getRec()
	{
		return blockRec;
	}
	
	public float getX()
	{
		return blockRec.x;
	}
	
	public float getXWidth()
	{
		return blockRec.x+blockRec.width;
	}
	
	public float getY()
	{
		return blockRec.y;
	}
	
	public float getYHeight()
	{
		return blockRec.y+blockRec.height;
	}
	
	public int getId()
	{
		return id;
	}
}
