package com.libgdxjam.darkerthanspace.backgroundscrolling;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.libgdxjam.darkerthanspace.backgroundscrolling.Utils.WH;

public class TextureRegionParallaxLayer extends ParallaxLayer{

	private TextureRegion texRegion;
	private float padLeft=0,padRight=0,padBottom=0,padTop=0;
	private float regionWidth,regionHeight;

	public TextureRegionParallaxLayer(TextureRegion texRegion, Vector2 parallaxScrollRatio){
		this.texRegion = texRegion;
		setRegionWidth(texRegion.getRegionWidth());
		setRegionHeight(texRegion.getRegionHeight());
		setParallaxRatio(parallaxScrollRatio);
	}
	
	public TextureRegionParallaxLayer(TextureRegion texRegion, float regionWidth, float regionHeight, Vector2 parallaxScrollRatio){
		this.texRegion = texRegion;
		setRegionWidth(regionWidth);
		setRegionHeight(regionHeight);
		setParallaxRatio(parallaxScrollRatio);
	}
	
	public TextureRegionParallaxLayer(TextureRegion texRegion, float oneDimen, Vector2 parallaxScrollRatio, WH wh){
		this.texRegion = texRegion;
		switch(wh){
		    case width:
		    	setRegionWidth(oneDimen);
		    	setRegionHeight(Utils.calculateOtherDimension(WH.width, oneDimen, this.texRegion));
		    	break;
		    case height:
		    	setRegionHeight(oneDimen);
		    	setRegionWidth(Utils.calculateOtherDimension(WH.height, oneDimen, this.texRegion));
		    	break;
		}
		setParallaxRatio(parallaxScrollRatio);
	}
	
	@Override
	public void draw(Batch batch, float x, float y) {
		batch.draw(texRegion, x+padLeft, y+padBottom, getRegionWidth(), getRegionHeight());
	}
	
	@Override
	public float getWidth() {
		return getPadLeft()+getRegionWidth()+getPadRight();
	}

	@Override
	public float getHeight() {
		return getPadTop()+getRegionHeight()+getPadBottom();
	}
	
	public void setAllPad(float pad){
		setPadLeft(pad);
		setPadRight(pad);
		setPadTop(pad);
		setPadBottom(pad);
	}

	public TextureRegion getTexRegion() {
		return texRegion;
	}

	public float getPadLeft() {
		return padLeft;
	}

	public void setPadLeft(float padLeft) {
		this.padLeft = padLeft;
	}

	public float getPadRight() {
		return padRight;
	}

	public void setPadRight(float padRight) {
		this.padRight = padRight;
	}

	public float getPadBottom() {
		return padBottom;
	}

	public void setPadBottom(float padBottom) {
		this.padBottom = padBottom;
	}

	public float getPadTop() {
		return padTop;
	}

	public void setPadTop(float padTop) {
		this.padTop = padTop;
	}


	public float getRegionWidth() {
		return regionWidth;
	}
	
	public float getRegionHeight() {
		return regionHeight;
	}

	
	private void setRegionWidth(float width){
		this.regionWidth = width;
	}
	private void setRegionHeight(float height){
		this.regionHeight = height;
	}
}
