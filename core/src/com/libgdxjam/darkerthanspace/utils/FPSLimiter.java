package com.libgdxjam.darkerthanspace.utils;

public class FPSLimiter
{
	
	//Not using this, using v-sync.
	
	
	
	
	
	private long diff, start = System.currentTimeMillis();
	
	public void setFPS(int fps)
	{
	    if(fps>0)
	    {
		      diff = System.currentTimeMillis() - start;
		      long targetDelay = 1000/fps;
		      
		      if (diff < targetDelay)
		      {
			        try
			        {
			        	Thread.sleep(targetDelay - diff);
			        }
			        catch (InterruptedException e)
			        {}
		      }   
		      start = System.currentTimeMillis();
	    }
	}
}
