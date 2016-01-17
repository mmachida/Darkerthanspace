package com.libgdxjam.darkerthanspace.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.libgdxjam.darkerthanspace.Interf.FinalStates;
import com.libgdxjam.darkerthanspace.screens.GameScreen;
import com.libgdxjam.darkerthanspace.utils.Assets;

public class ActBlock implements FinalStates
{
	//General
	private GameScreen game;
	private Assets assets;
	
	//Attibutes
	private int type, typeId;
	private Rectangle actRec;
	
	//Act with Text
	private int parts;
	
	//Door only
	private Sprite spriteDoor;
	private Sprite backDoor;
	private float acc, speed;
	private int nextScene, nextDoor, sideDoor;
	
	//ItemId
	private Sprite spriteItem;
	private int itemId;
	
	//Act
	public ActBlock (int type, int typeId, int parts, Rectangle rec, GameScreen game)
	{
		this.game = game;
		this.type = type;
		this.typeId = typeId;
		this.parts = parts;
		actRec = rec;
	}
	
	//DOOR
	public ActBlock (int type, int typeId, int nextScene, int nextDoor, int sideDoor, Rectangle rec, GameScreen game)
	{
		this.game = game;
		this.type = type;
		this.typeId = typeId;
		this.nextScene = nextScene;
		this.nextDoor = nextDoor;
		this.sideDoor = sideDoor;
		actRec = rec;
		
		assets = game.game.assets;
		
		acc = 2f;
		speed = 20f;
		
		if (sideDoor ==0) //Normal
		{
			spriteDoor = new Sprite(assets.skin.getRegion("door"));
			spriteDoor.setPosition(actRec.x-12, actRec.y);
		}
		if (sideDoor == 1) //Back
		{
			spriteDoor = new Sprite(assets.skin.getRegion("door"));
			spriteDoor.setPosition(actRec.x-12, actRec.y);
			actRec.y -= 2;
			spriteDoor.setY(actRec.y);
			spriteDoor.setFlip(true, false);
			spriteDoor.setColor(0.4f, 0.4f, 0.4f, 0.7f);
			backDoor = new Sprite(assets.skin.getRegion("backDoor"));
			backDoor.setColor(0.2f, 0.2f, 0.2f, 0.3f);
			backDoor.setPosition(spriteDoor.getX(), spriteDoor.getY());
		}
		else if (sideDoor == 2) //Side
		{
			spriteDoor = new Sprite(assets.skin.getRegion("cornerDoor"));
			spriteDoor.setPosition(actRec.x, actRec.y);
		}
	}
	
	//Items
	public ActBlock (int type, int typeId, int parts,Rectangle rec, int itemId, GameScreen game)
	{
		this.game = game;
		this.type = type;
		this.parts = parts;
		this.typeId = typeId;
		this.itemId = itemId;
		actRec = rec;
		
		assets = game.game.assets;
		
		spriteItem = new Sprite(assets.skin.getRegion("item"+itemId));
		spriteItem.setPosition(actRec.x+actRec.getWidth()/2-spriteItem.getWidth()/2, actRec.y);
	}
	
	public void update(float delta, float posX)
	{
		if (type == DOOR && game.canChangeScene)
		{
			if (game.eventInGame.getCurrentAct() != null)
			{
				if (game.eventInGame.getCurrentAct().getTypeId() == typeId)
				{
					if (sideDoor == 3)
					{
						
					}
					else if (spriteDoor.getY() >= actRec.y+63)
					{
						//game.canSetScene = true;
					}
					else
					{
						spriteDoor.setY(spriteDoor.getY()+speed*delta*acc);
						speed -= acc*delta;
						if (speed < 15)
							speed = 15;
					}
				}
			}
		}
	}
	
	public void drawDoor(Batch batch)
	{
		if (sideDoor < 3)
		{
			spriteDoor.draw(batch);
			if (sideDoor == 1)
			{
				backDoor.draw(batch);
			}
		}
	}
	
	public void drawItem(Batch batch)
	{
		spriteItem.draw(batch);
	}
	
	public void drawDebug(ShapeRenderer shape)
	{
		shape.setColor(Color.PINK);
		shape.rect(actRec.x, actRec.y, actRec.width, actRec.height);
	}
	
	public Rectangle getRec()
	{
		return actRec;
	}
	
	public float getX()
	{
		return actRec.x;
	}
	
	public float getY()
	{
		return actRec.y;
	}
	
	public float getCenterX()
	{
		return actRec.x+actRec.width/2;
	}
	
	public float getCenterY()
	{
		return actRec.y+actRec.height/2;
	}
	
	public float getXWidth()
	{
		return actRec.x+actRec.width;
	}
	
	public float getYHeight()
	{
		return actRec.y+actRec.height;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	
	public int getTypeId()
	{
		return typeId;
	}
	
	public void setTypeId(int typeId)
	{
		this.typeId = typeId;
	}

	public int getParts()
	{
		return parts;
	}
	
	public int getNextScene()
	{
		return nextScene;
	}
	
	public int getNextDoor()
	{
		return nextDoor;
	}
	
	public void setParts(int parts)
	{
		this.parts = parts;
	}
	
	public int getSideDoor()
	{
		return sideDoor;
	}
	
	public int getItemId()
	{
		return itemId;
	}

	public void setY(int i)
	{
		actRec.y = i;
	}

	public void setX(int i)
	{
		actRec.x = i;
	}
}
