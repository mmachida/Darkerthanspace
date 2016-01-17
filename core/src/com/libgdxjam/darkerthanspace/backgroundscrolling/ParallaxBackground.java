package com.libgdxjam.darkerthanspace.backgroundscrolling;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.libgdxjam.darkerthanspace.backgroundscrolling.ParallaxLayer.TileMode;

public class ParallaxBackground {
	
	public Array<ParallaxLayer> layers;
	private Matrix4 cachedProjectionView;
	private Vector3 cachedPos;
	private float cachedZoom;

	public ParallaxBackground(){
		initialize();
	}
	
	public ParallaxBackground(ParallaxLayer... layers){
		initialize();
		this.layers.addAll(layers);
	}
	
    private void initialize() {
    	layers = new Array<ParallaxLayer>();
		cachedPos = new Vector3();
		cachedProjectionView = new Matrix4();
	}
	
	public void addLayers(ParallaxLayer... layers){
		this.layers.addAll(layers);
	}
	
	public void draw(OrthographicCamera worldCamera, Batch batch){
		cachedProjectionView.set(worldCamera.combined);
		cachedPos.set(worldCamera.position);
		cachedZoom = worldCamera.zoom;
		
		
		for(int i=0; i<layers.size; i++){
			ParallaxLayer layer = layers.get(i);
			Vector2 origCameraPos = new Vector2(cachedPos.x,cachedPos.y);
			worldCamera.position.set(origCameraPos.scl(layer.getParallaxRatio()),cachedPos.z);
		    worldCamera.update();
		    batch.setProjectionMatrix(worldCamera.combined);
		    float currentX = (layer.getTileModeX().equals(TileMode.single)?0:((int)((worldCamera.position.x-worldCamera.viewportWidth*.5f*worldCamera.zoom) / layer.getWidth())) * layer.getWidth())-(Math.abs((1-layer.getParallaxRatio().x)%1)*worldCamera.viewportWidth*.5f);
			do{
		            float currentY =  (layer.getTileModeY().equals(TileMode.single)?0:((int)((worldCamera.position.y-worldCamera.viewportHeight*.5f*worldCamera.zoom) / layer.getHeight())) * layer.getHeight())-(((1-layer.getParallaxRatio().y)%1)*worldCamera.viewportHeight*.5f);
		            do{
		               if(!((worldCamera.position.x-worldCamera.viewportWidth*worldCamera.zoom*.5f>currentX+layer.getWidth())||(worldCamera.position.x+worldCamera.viewportWidth*worldCamera.zoom*.5f<currentX)||(worldCamera.position.y-worldCamera.viewportHeight*worldCamera.zoom*.5f>currentY+layer.getHeight())||(worldCamera.position.y+worldCamera.viewportHeight*worldCamera.zoom*.5f<currentY)))
		                   layer.draw(batch, currentX, currentY);
		               currentY += layer.getHeight();
		               if(layer.getTileModeY().equals(TileMode.single))
			        	     break;
		            }while( currentY < worldCamera.position.y+worldCamera.viewportHeight*worldCamera.zoom*.5f);
		            currentX += layer.getWidth();
		            if(layer.getTileModeX().equals(TileMode.single))
		        	     break;
		         }while( currentX < worldCamera.position.x+worldCamera.viewportWidth*worldCamera.zoom*.5f);
		     
		}
		
		worldCamera.combined.set(cachedProjectionView);
		worldCamera.position.set(cachedPos);
		worldCamera.zoom = cachedZoom;
		worldCamera.update();
		batch.setProjectionMatrix(worldCamera.combined);
	    
	}
	

}
