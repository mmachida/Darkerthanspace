package com.libgdxjam.darkerthanspace.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.libgdxjam.darkerthanspace.entities.Item;

public class MainPreferences
{
	public Preferences prefs = Gdx.app.getPreferences("dtb");
	
	public void save(
			boolean hasSave,
			int scene,
			boolean hasFlashLight,
			boolean hasMap,
			boolean hasGun,
			
			//S1
			boolean s1t1id0,
			boolean s1t1id1,
			boolean s1t3id0,
			boolean s1t3id1,
			boolean s1t3id2,
			
			//S2
			boolean s2t1id0,
			
			//S3
			boolean s3t3id0,
			
			//S5
			boolean s5t3id0,
			
			//S6
			boolean s6t3id0,
			
			//S7
			boolean s7t0id2,
			
			//S8
			boolean s8t3id0,
			
			//S9
			boolean s9t1id1,
			boolean s9t1id2,
			boolean s9t1id3,
			
			//S10
			boolean s10t0id2,
			
			//S12
			boolean s12t3id0,
			boolean s12t3id1
			)
	{
		prefs.putBoolean("hassave", hasSave);
		prefs.putInteger("scene", scene);
		prefs.putBoolean("hasflashlight", hasFlashLight);
		prefs.putBoolean("hasmap", hasMap);
		prefs.putBoolean("hasgun", hasGun);
		
		//S1
		prefs.putBoolean("s1t1id0", s1t1id0);
		prefs.putBoolean("s1t1id1", s1t1id1);
		prefs.putBoolean("s1t3id0", s1t3id0);
		prefs.putBoolean("s1t3id1", s1t3id0);
		prefs.putBoolean("s1t3id2", s1t3id2);
		
		//S2
		prefs.putBoolean("s2t1id0", s2t1id0);
		
		//S3
		prefs.putBoolean("s3t3id0", s3t3id0);
		
		//S5
		prefs.putBoolean("s5t3id0", s5t3id0);
		
		//S6
		prefs.putBoolean("s6t3id0", s6t3id0);
		
		//S7
		prefs.putBoolean("s7t0id2", s7t0id2);
		
		//S8
		prefs.putBoolean("s8t3id0", s8t3id0);
		
		//S9
		prefs.putBoolean("s9t1id1", s9t1id1);
		prefs.putBoolean("s9t1id2", s9t1id2);
		prefs.putBoolean("s9t1id3", s9t1id2);
		
		//S10
		prefs.putBoolean("s10t0id2", s10t0id2);
		
		//S12
		prefs.putBoolean("s12t3id0", s12t3id0);
		prefs.putBoolean("s12t3id1", s12t3id1);
		
		prefs.flush();
		//System.out.println("Saved");
	}
	
	public void saveInventory(int itemSize, Array<Item> listId)
	{
		//List Size
		prefs.putInteger("itemsize", itemSize);
		//Items
		for (int i=0; i<itemSize; i++)
			prefs.putInteger("itemid"+i, listId.get(i).getId());
		
		prefs.flush();
		//System.out.println("Inventory Saved");
	}
	
	public boolean load(String string)
	{
		boolean bool = false;
		
		if (string.equals("hassave"))
			bool = prefs.getBoolean(string);
		if (string.equals("hasflashlight"))
			bool = prefs.getBoolean(string);
		else if (string.equals("hasmap"))
			bool = prefs.getBoolean(string);
		else if (string.equals("hasgun"))
			bool = prefs.getBoolean(string);
		
		//S1
		else if (string.equals("s1t1id0"))
			bool = prefs.getBoolean(string);
		else if (string.equals("s1t1id1"))
			bool = prefs.getBoolean(string);
		else if (string.equals("s1t3id0"))
			bool = prefs.getBoolean(string);
		else if (string.equals("s1t3id1"))
			bool = prefs.getBoolean(string);
		else if (string.equals("s1t3id2"))
			bool = prefs.getBoolean(string);
		
		//S2
		else if (string.equals("s2t1id0"))
			bool = prefs.getBoolean(string);
		
		//S3
		else if (string.equals("s3t3id0"))
			bool = prefs.getBoolean(string);
		
		//S5
		else if (string.equals("s5t3id0"))
			bool = prefs.getBoolean(string);
		
		//S6
		else if (string.equals("s6t3id0"))
			bool = prefs.getBoolean(string);
		
		//S7
		else if (string.equals("s7t0id2"))
			bool = prefs.getBoolean(string);
		
		//S8
		else if (string.equals("s8t3id0"))
			bool = prefs.getBoolean(string);
		
		//S9
		else if (string.equals("s9t1id1"))
			bool = prefs.getBoolean(string);
		else if (string.equals("s9t1id2"))
			bool = prefs.getBoolean(string);
		else if (string.equals("s9t1id3"))
			bool = prefs.getBoolean(string);
		
		//S10
		else if (string.equals("s10t0id2"))
			bool = prefs.getBoolean(string);
		
		//S12
		else if (string.equals("s12t3id0"))
			bool = prefs.getBoolean(string);
		else if (string.equals("s12t3id1"))
			bool = prefs.getBoolean(string);
		
		//System.out.println("Load: "+string +" - "+bool);
		return bool;
	}
	
	public int loadScene()
	{
		return prefs.getInteger("scene");
	}
	
	public int loadInventory(String string)
	{
		int value = 0;
		
		if (string.equals("itemsize"))
			value = prefs.getInteger(string);
		else
			value = prefs.getInteger(string);
		
		//System.out.println("Invetory loaded: "+ string + " - "+value);
		return value;
	}
	
	public void reset()
	{
		prefs.clear();
		prefs.putBoolean("hassave", false);
		prefs.putInteger("scene", 0);
		prefs.putBoolean("notfirsttime", true);
		
		prefs.flush();
		//System.out.println("Reseted");
	}
		
	public void loadFirstTime()
	{
		if (!prefs.getBoolean("notfirsttime"))
		{
			reset();
			
			prefs.flush();
			//System.out.println("Load First Time");
		}
		else
		{
			//System.out.println("Not First Time");
		}
	}
}
