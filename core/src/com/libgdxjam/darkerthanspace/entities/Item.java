package com.libgdxjam.darkerthanspace.entities;

import com.libgdxjam.darkerthanspace.Interf.GameItems;

public class Item implements GameItems
{
	private int id;
	private String desc;
	
	public Item(int itemId)
	{
		id = itemId;
		setDesc();
	}
	
	private void setDesc()
	{
		switch (id)
		{
			case ITEM1: desc = "black key"; break;
			case ITEM2: desc = "flashlight"; break;
			case ITEM3: desc = "frozen gun"; break;
			case ITEM4: desc = "yellow key"; break;
			case ITEM5: desc = "map"; break;
			case ITEM6: desc = "red key"; break;
			case ITEM7: desc = "magnetic bomb"; break;
			case ITEM8: desc = "crowbar"; break;
			case ITEM9: desc = "attachment"; break;
	
			default: desc = ""; break;
		}
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getDesc()
	{
		return desc;
	}
}