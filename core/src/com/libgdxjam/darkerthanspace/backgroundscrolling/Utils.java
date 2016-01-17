package com.libgdxjam.darkerthanspace.backgroundscrolling;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Utils {

	public enum WH{
		width, height
	}
	
	public static float calculateOtherDimension(WH wh,float oneDimen,TextureRegion region){
		float result=0;
		switch (wh){
		    // height_specified
		    case width:
    		    result = region.getRegionHeight()*(oneDimen/region.getRegionWidth());
		    	break;
		    // width_specified
	    	case height:
	    		result = region.getRegionWidth()*(oneDimen/region.getRegionHeight());
		    	break;
		
		}
		
		return result;
		
	}
	
	public static float calculateOtherDimension(WH wh,float oneDimen,float originalWidth, float originalHeight){
		float result=0;
		switch (wh){
		    case width:
    		    result = originalHeight*(oneDimen/originalWidth);
		    	break;
	    	case height:
	    		result = originalWidth*(oneDimen/originalHeight);
		    	break;
		
		}
		
		return result;
		
	}
	
	
}
