package com.libgdxjam.darkerthanspace.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Ellipse;

public class LightMap
{
	private Ellipse ellipse;
	
	public LightMap(Ellipse ellipse)
	{
		this.ellipse = ellipse;
		ellipse.setSize(ellipse.width/2, ellipse.height/2);
	}

	public void drawDebug(ShapeRenderer shape)
	{
		shape.ellipse(ellipse.x, ellipse.y, ellipse.width*2, ellipse.height*2);
	}
	
	public float getX()
	{
		return ellipse.x+ellipse.width;
	}
	
	public float getY()
	{
		return ellipse.y+ellipse.height;
	}
	
	public float getWidth()
	{
		return ellipse.width;
	}
	
	public float getHeight()
	{
		return ellipse.height;
	}
}
