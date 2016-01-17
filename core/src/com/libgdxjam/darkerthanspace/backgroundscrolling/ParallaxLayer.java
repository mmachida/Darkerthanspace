package com.libgdxjam.darkerthanspace.backgroundscrolling;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public abstract class ParallaxLayer {
	
	public enum TileMode{
		repeat,single
	}
	
	protected Vector2 parallaxRatio;
	protected TileMode tileModeX = TileMode.repeat;
	protected TileMode tileModeY = TileMode.single;
	
	public abstract float getWidth();

	public abstract float getHeight();


	public Vector2 getParallaxRatio() {
		return parallaxRatio;
	}


	public void setParallaxRatio(Vector2 parallaxRatio) {
		if(this.parallaxRatio == null)
			this.parallaxRatio = new Vector2();
		this.parallaxRatio.set(parallaxRatio);
	}

	public void setParallaxRatio(float ratioX, float ratioY) {
		if(this.parallaxRatio == null)
			this.parallaxRatio = new Vector2();
		this.parallaxRatio.set(ratioX,ratioY);
	}

	public void draw(Batch batch,Vector2 pos){
		this.draw(batch, pos.x, pos.y);
	}
	
	public abstract void draw(Batch batch,float x, float y);

	public TileMode getTileModeX() {
		return tileModeX;
	}

	public void setTileModeX(TileMode tileModeX) {
		this.tileModeX = tileModeX;
	}

	public TileMode getTileModeY() {
		return tileModeY;
	}

	public void setTileModeY(TileMode tileModeY) {
		this.tileModeY = tileModeY;
	}

}
