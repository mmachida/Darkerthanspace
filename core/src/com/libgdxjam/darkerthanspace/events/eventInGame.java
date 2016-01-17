package com.libgdxjam.darkerthanspace.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.libgdxjam.darkerthanspace.Interf.AudioId;
import com.libgdxjam.darkerthanspace.Interf.FinalStates;
import com.libgdxjam.darkerthanspace.entities.ActBlock;
import com.libgdxjam.darkerthanspace.entities.Item;
import com.libgdxjam.darkerthanspace.entities.SolidBlock;
import com.libgdxjam.darkerthanspace.screens.GameScreen;

public class eventInGame implements FinalStates, AudioId
{
	//General
	private GameScreen game;
	private Vector2 vec2;
	
	//Event
	private int scene;
	private ActBlock currentAct;

	//Utils
	private boolean boolItemFail;
	private boolean bool2;
	private int n;
	private Array<ActBlock> listActTemp;
	private Array<SolidBlock> listSolidTemp;
	
	//SaveStates
	//S1
	public boolean s1t1id0;
	public boolean s1t1id1;
	public boolean s1t3id0;
	public boolean s1t3id1;
	public boolean s1t3id2;
	//S2
	public boolean s2t1id0;
	//S3
	public boolean s3t3id0;
	//S5
	public boolean s5t3id0;
	//S6
	public boolean s6t3id0;
	//S7
	public boolean s7t0id2;
	//S8
	public boolean s8t3id0;
	//S9
	public boolean s9t1id1;
	public boolean s9t1id2;
	public boolean s9t1id3;
	public boolean s9t1id4;
	//S10
	public boolean s10t0id2;
	//S12
	public boolean s12t3id0;
	public boolean s12t3id1;
	//S13
	public boolean s13t1id0;
	
	public eventInGame (GameScreen game, int scene)
	{
		this.game = game;
		this.scene = scene;
		boolItemFail = true;
		bool2 = true;
		vec2 = new Vector2();
		
		listActTemp = new Array<ActBlock>();
		listSolidTemp = new Array<SolidBlock>();
	}
	
	public void updateSpecificScene()
	{
		switch (scene)
		{
		case 0:
			if (game.player.isOverlapping())
			{
				if (currentAct != null)
				{
					if (currentAct.getType() == ACT && currentAct.getTypeId() == 1) //Show Light
					{
						if (Gdx.input.isKeyJustPressed(Keys.X))
						{
							game.canDrawLight = true;
							game.game.audioManager.playSound(sfxFLASHLIGHT);
							game.player.removeAct();
							game.player.removeSolid(1);
						}
					}
					else if (currentAct.getType() == ACT && currentAct.getTypeId() == 2) //Help
					{
						if (Gdx.input.isKeyJustPressed(Keys.F1))
						{
							game.player.removeAct();
							game.player.removeSolid(1);
						}
					}
				}
			}
		break;

		case 1:
			if (game.player.isOverlapping())
			{
				if (currentAct != null)
				{
					//item works
					if (game.usingItem > -1)
					{
						//key to open door
						if (!s1t1id0)
						{
							if (currentAct.getType() == ACT && currentAct.getTypeId() == 0 && game.usingItem == 1)
							{
								if (boolItemFail)
								{
									s1t1id0 = true;
									game.isJailOpen = true;
									game.player.removeAct();
									game.player.removeSolid(1);
									game.itemToRemove = game.usingItem;
									game.usingItem = -1;
									game.canDefaultText = true;
									game.defaultText = "[YELLOW]system:[]\ndoor unlocked";
									boolItemFail = false;
								}
							}
						}
						else if (currentAct.getType() == ACT && currentAct.getTypeId() == 2 && game.usingItem == 8)
						{
							if (boolItemFail)
							{
								s1t3id2 = true;
								
								game.player.removeAct();
								game.itemToRemove = game.usingItem;
								game.usingItem = -1;
								game.canDefaultText = true;
								game.defaultText = "[GREEN]you:[]\nwhat???? a secret tunnel?";
								game.canMoveBed = true;
								boolItemFail = false;
							}
						}
					}
					//Key on the ground
					else if (Gdx.input.isKeyJustPressed(Keys.X) && currentAct.getType() == MAPITEM && currentAct.getTypeId() == 0)
					{
						s1t3id0 = true;
					}
					//Flashlight
					else if (Gdx.input.isKeyJustPressed(Keys.X) && currentAct.getType() == MAPITEM && currentAct.getTypeId() == 1)
					{
						s1t3id1 = true;
						game.player.hasFlashLight = true;
					}
					//Startgame text
					else if (currentAct.getType() == ACT && currentAct.getTypeId() == 1)
					{
						if (bool2)
						{
							game.gameState = TEXT;
							bool2 = false;
						}
						else if (game.gameState == INGAME)
						{
							game.player.removeAct();
							s1t1id1 = true;
						}
					}
				}
			}
		break;
		
		case 2:
			if (game.player.isOverlapping())
			{
				if (currentAct != null)
				{
					//Item works
					if (game.usingItem > -1)
					{
						//Item works
					}
					//Event 'there is something'
					else if (currentAct.getType() == ACT && currentAct.getTypeId() == 0)
					{
						if (bool2)
						{
							game.gameState = TEXT;
							bool2 = false;
						}
						else if (game.gameState == INGAME)
						{
							s2t1id0 = true;
							game.player.removeAct();
						}
					}
				}
			}
		break;
		
		case 3:
			if (game.player.isOverlapping())
			{
				if (currentAct != null)
				{
					//Item works
					if (game.usingItem > -1)
					{
						//Use Item
					}
					//get item Gun
					else if (Gdx.input.isKeyJustPressed(Keys.X) && currentAct.getType() == MAPITEM && currentAct.getTypeId() == 0)
					{
						s3t3id0 = true;
						game.player.hasGun = true;
					}
				}
			}
		break;
		
		case 5:
			if (game.player.isOverlapping())
			{
				if (currentAct != null)
				{
					//Item works
					if (game.usingItem > -1)
					{
						//Use Item
					}
					//get item
					else if (Gdx.input.isKeyJustPressed(Keys.X) && currentAct.getType() == MAPITEM && currentAct.getTypeId() == 0)
					{
						s5t3id0 = true;
					}
				}
			}
			break;
			
		case 6:
			if (game.player.isOverlapping())
			{
				if (currentAct != null)
				{
					//Item works
					if (game.usingItem > -1)
					{
						//Use Item
					}
					//get item
					else if (Gdx.input.isKeyJustPressed(Keys.X) && currentAct.getType() == MAPITEM && currentAct.getTypeId() == 0)
					{
						s6t3id0 = true;
					}
				}
			}
			break;
			
		case 7:
			if (game.player.isOverlapping())
			{
				if (currentAct != null)
				{
					//Item works
					if (game.usingItem > -1)
					{
						//Key Door
						if (currentAct.getType() == DOOR && currentAct.getTypeId() == 2 && game.usingItem == 4)
						{
							if (boolItemFail)
							{
								s7t0id2 = true;
								game.itemToRemove = game.usingItem;
								game.usingItem = -1;
								game.canDefaultText = true;
								game.defaultText = "[YELLOW]system:[]\ndoor unlocked";
								boolItemFail = false;
							}
						}
					}
					//get item
					else if (Gdx.input.isKeyJustPressed(Keys.X) && currentAct.getType() == MAPITEM && currentAct.getTypeId() == 0)
					{
						s6t3id0 = true;
					}
				}
			}
		break;
		
		case 8:
			if (game.player.isOverlapping())
			{
				if (currentAct != null)
				{
					//Item works
					if (game.usingItem > -1)
					{
						//Use Item
					}
					//map
					else if (Gdx.input.isKeyJustPressed(Keys.X) && currentAct.getType() == MAPITEM && currentAct.getTypeId() == 0)
					{
						s8t3id0 = true;
						game.player.hasMap = true;
					}
				}
			}
		break;
		
		case 9:
			if (game.player.isOverlapping())
			{
				if (currentAct != null)
				{
					//Item works
					if (game.usingItem > -1)
					{
						//Use Item
					}
					//get item map
					else if (currentAct.getType() == ACT && currentAct.getTypeId() == 1)
					{
						if (Gdx.input.isKeyJustPressed(Keys.X) && game.gameState == INGAME)
						{
							bool2 = false;
						}
						else if (game.gameState == INGAME && !bool2)
						{
							game.player.removeAct();
							s9t1id1 = true;
							s9t1id2 = false;
							game.player.listItem.add(new Item(8));
							game.canPickupItem = true;
						}
					}
					else if (currentAct.getType() == ACT && currentAct.getTypeId() == 4)
					{
						if (Gdx.input.isKeyJustPressed(Keys.X) && game.gameState == INGAME)
						{
							bool2 = false;
						}
						else if (game.gameState == INGAME && !bool2)
						{
							game.player.removeAct();
							s9t1id4 = true;
							game.isNeila = true;
						}
					}
				}
			}
		break;
		
		case 10:
			if (game.player.isOverlapping())
			{
				if (currentAct != null)
				{
					//Item works
					if (game.usingItem > -1)
					{
						//Key Door
						if (currentAct.getType() == DOOR && currentAct.getTypeId() == 2 && game.usingItem == 6)
						{
							if (boolItemFail)
							{
								s10t0id2 = true;
								game.itemToRemove = game.usingItem;
								game.usingItem = -1;
								game.canDefaultText = true;
								game.defaultText = "[YELLOW]system:[]\ndoor unlocked";
								boolItemFail = false;
							}
						}
					}
					//get item
					else if (Gdx.input.isKeyJustPressed(Keys.X) && currentAct.getType() == MAPITEM && currentAct.getTypeId() == 0)
					{
						s10t0id2 = true;
					}
				}
			}
		break;
		
		case 12:
			if (game.player.isOverlapping())
			{
				if (currentAct != null)
				{
					//Item works
					if (game.usingItem > -1)
					{
						//Use Item
					}
					//get item
					else if (Gdx.input.isKeyJustPressed(Keys.X) && currentAct.getType() == MAPITEM && currentAct.getTypeId() == 0)
					{
						s12t3id0 = true;
					}
					else if (Gdx.input.isKeyJustPressed(Keys.X) && currentAct.getType() == MAPITEM && currentAct.getTypeId() == 1)
					{
						s12t3id1 = true;
						game.player.setGunUpgrade();
					}
				}
			}
		break;
		
		case 13:
			if (game.player.isOverlapping())
			{
				if (currentAct != null)
				{
					//Item works
					if (game.usingItem > -1)
					{
						//Bomb
						if (currentAct.getType() == ACT && currentAct.getTypeId() == 0 && game.usingItem == 7)
						{
							if (boolItemFail)
							{
								game.isBombPlanted = true;
								game.player.removeAct();
								s13t1id0 = true;
								game.itemToRemove = game.usingItem;
								game.usingItem = -1;
								game.canDefaultText = true;
								game.defaultText = "[YELLOW]system:[]\nthe bomb has been planted.";
								boolItemFail = false;
							}
						}
					}
					else if (currentAct.getType() == ACT && currentAct.getTypeId() == 2 && game.isNeila)
					{
						if (Gdx.input.isKeyJustPressed(Keys.X))
						{
							game.neilas13 = true;
							game.cantDieBomb = true;
							game.gameState = TEXT;
							game.player.setSide(true);
						}
						if (game.neilas13)
						{
							if (game.gameState == INGAME)
							{
								game.player.removeAct();
								game.neilas13 =false;
								game.changeSpaceShip();
								game.s13canMoveSpaceShip = true;
							}
						}
					}
				}
			}
		break;
		
		default:
		break;
		}
		
		//item fail
		if (game.usingItem > -1)
		{
			if (boolItemFail)
			{
				game.canDefaultText = true;
				game.defaultText = "[GREEN]You:[]\nnothing happens";
				boolItemFail = false;
			}
		}
		else
		{
			if (!boolItemFail)
				boolItemFail = true;
		}
	}
	
	public void load()
	{
		switch (scene)
		{
		case 1:
			if (!s1t1id0)
				s1t1id0 = game.game.prefs.load("s1t1id0");
			if (!s1t1id1)
				s1t1id1 = game.game.prefs.load("s1t1id1");
			if (!s1t3id0)
				s1t3id0 = game.game.prefs.load("s1t3id0");
			if (!s1t3id1)
				s1t3id1 = game.game.prefs.load("s1t3id1");
			if (!s1t3id2)
				s1t3id2 = game.game.prefs.load("s1t3id2");
			
			listActTemp.clear();
			n = game.listActBlock.size;
			for (int i=0; i<n; i++)
			{
				if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 0 && s1t1id0)
				{
					//Do nothing
				}
				else if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 1 && s1t1id1)
				{
					//Do nothing
				}
				else if (game.listActBlock.get(i).getType() == MAPITEM && game.listActBlock.get(i).getTypeId() == 0 && s1t3id0)
				{
					//Do nothing
				}
				else if (game.listActBlock.get(i).getType() == MAPITEM && game.listActBlock.get(i).getTypeId() == 1 && s1t3id1)
				{
					//Do nothing
				}
				else if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 2 && s1t3id2)
				{
					//Do nothing
				}
				else if (game.listActBlock.get(i).getType() == DOOR && game.listActBlock.get(i).getTypeId() == 1 && s1t3id2)
				{
					game.listActBlock.get(i).setY(32);
					listActTemp.add(game.listActBlock.get(i));
				}
				else
				{
					listActTemp.add(game.listActBlock.get(i));
				}
			}
			
			game.listActBlock.clear();
			n = listActTemp.size;
			for (int i=0; i<n; i++)
				game.listActBlock.add(listActTemp.get(i));
			
			if (s1t1id0)
			{
				listSolidTemp.clear();
				n = game.listSolidBlock.size;
				for (int i=0; i<n; i++)
				{
					if (game.listSolidBlock.get(i).getId() == 1)
					{
						//Do nothing
					}
					else
					{
						listSolidTemp.add(game.listSolidBlock.get(i));
					}
				}
				
				game.listSolidBlock.clear();
				n = listSolidTemp.size;
				for (int i=0; i<n; i++)
					game.listSolidBlock.add(listSolidTemp.get(i));
			}
		break;
		case 2:
			if (!s2t1id0)
				s2t1id0 = game.game.prefs.load("s2t1id0");
			
			listActTemp.clear();
			n = game.listActBlock.size;
			for (int i=0; i<n; i++)
			{
				if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 0 && s2t1id0)
				{
					//Do nothing
				}
				else
				{
					listActTemp.add(game.listActBlock.get(i));
				}
			}
			
			game.listActBlock.clear();
			n = listActTemp.size;
			for (int i=0; i<n; i++)
				game.listActBlock.add(listActTemp.get(i));
		break;
		
		case 3:
			if (!s3t3id0)
				s3t3id0 = game.game.prefs.load("s3t3id0");
			
			listActTemp.clear();
			n = game.listActBlock.size;
			for (int i=0; i<n; i++)
			{
				if (game.listActBlock.get(i).getType() == MAPITEM && game.listActBlock.get(i).getTypeId() == 0 && s3t3id0)
				{
					//Do nothing
				}
				else
				{
					listActTemp.add(game.listActBlock.get(i));
				}
			}
			
			game.listActBlock.clear();
			n = listActTemp.size;
			for (int i=0; i<n; i++)
				game.listActBlock.add(listActTemp.get(i));
		break;
		
		case 5:
			if (!s5t3id0)
				s5t3id0 = game.game.prefs.load("s5t3id0");
			
			listActTemp.clear();
			n = game.listActBlock.size;
			for (int i=0; i<n; i++)
			{
				if (game.listActBlock.get(i).getType() == MAPITEM && game.listActBlock.get(i).getTypeId() == 0 && s5t3id0)
				{
					//Do nothing
				}
				else
				{
					listActTemp.add(game.listActBlock.get(i));
				}
			}
			
			game.listActBlock.clear();
			n = listActTemp.size;
			for (int i=0; i<n; i++)
				game.listActBlock.add(listActTemp.get(i));
			break;
			
		case 6:
			if (!s6t3id0)
				s6t3id0 = game.game.prefs.load("s6t3id0");
			
			listActTemp.clear();
			n = game.listActBlock.size;
			for (int i=0; i<n; i++)
			{
				if (game.listActBlock.get(i).getType() == MAPITEM && game.listActBlock.get(i).getTypeId() == 0 && s6t3id0)
				{
					//Do nothing
				}
				else
				{
					listActTemp.add(game.listActBlock.get(i));
				}
			}
			
			game.listActBlock.clear();
			n = listActTemp.size;
			for (int i=0; i<n; i++)
				game.listActBlock.add(listActTemp.get(i));
		break;
		
		case 7:
			if (!s7t0id2)
				s7t0id2 = game.game.prefs.load("s7t0id2");
			break;
			
		case 8:
			if (!s8t3id0)
				s8t3id0 = game.game.prefs.load("s8t3id0");
			
			listActTemp.clear();
			n = game.listActBlock.size;
			for (int i=0; i<n; i++)
			{
				if (game.listActBlock.get(i).getType() == MAPITEM && game.listActBlock.get(i).getTypeId() == 0 && s8t3id0)
				{
					//Do nothing
				}
				else
				{
					listActTemp.add(game.listActBlock.get(i));
				}
			}
			
			game.listActBlock.clear();
			n = listActTemp.size;
			for (int i=0; i<n; i++)
				game.listActBlock.add(listActTemp.get(i));
		break;
		
		case 9:
			if (game.isBombPlanted)
			{
				s9t1id1 = true;
				s9t1id2 = true;
				s9t1id3 = true;
				s9t1id4 = false;
				
				n = game.listActBlock.size;
				for (int i=0; i<n; i++)
				{
					if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 4)
					{
						game.listActBlock.get(i).setY(32);
					}
				}
			}
			else
			{
				if (!s9t1id1)
				{
					s9t1id1 = game.game.prefs.load("s9t1id1");
				}
				
				if (s6t3id0 || game.game.prefs.load("s6t3id0"))
				{
					s9t1id2 = true;
					s9t1id3 = false;
				}
				
				if (!s9t1id2)
				{
					s9t1id2 = game.game.prefs.load("s9t1id2");
				}
				
				if (game.isBombPlanted || !s9t1id1 || !s9t1id2)
				{
					s9t1id3 = true;
				}
				
				if (!s9t1id1)
				{
					n = game.listActBlock.size;
					for (int i=0; i<n; i++)
					{
						if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 1)
						{
							game.listActBlock.get(i).setY(32);
						}
					}
				}
				else if (!s9t1id2)
				{
					n = game.listActBlock.size;
					for (int i=0; i<n; i++)
					{
						if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 2)
						{
							game.listActBlock.get(i).setY(32);
						}
					}
				}
				else if (!s9t1id3)
				{
					n = game.listActBlock.size;
					for (int i=0; i<n; i++)
					{
						if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 3)
						{
							game.listActBlock.get(i).setY(32);
						}
					}
				}
			}
			
			listActTemp.clear();
			n = game.listActBlock.size;
			for (int i=0; i<n; i++)
			{
				if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 1 && s9t1id1)
				{
					//Do nothing
				}
				if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 2 && s9t1id2)
				{
					//Do nothing
				}
				if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 3 && s9t1id3)
				{
					//Do nothing
				}
				if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 4 && s9t1id4)
				{
					//Do nothing
				}
				else
				{
					listActTemp.add(game.listActBlock.get(i));
				}
			}
			
			game.listActBlock.clear();
			n = listActTemp.size;
			for (int i=0; i<n; i++)
				game.listActBlock.add(listActTemp.get(i));
		break;
		
		case 10:
			if (!s10t0id2)
				s10t0id2 = game.game.prefs.load("s10t0id2");
		break;
		
		case 12:
			if (!s12t3id0)
				s12t3id0 = game.game.prefs.load("s12t3id0");
			if (!s12t3id1)
				s12t3id1 = game.game.prefs.load("s12t3id1");
			
			listActTemp.clear();
			n = game.listActBlock.size;
			for (int i=0; i<n; i++)
			{
				if (game.listActBlock.get(i).getType() == MAPITEM && game.listActBlock.get(i).getTypeId() == 0 && s12t3id0)
				{
					//Do nothing
				}
				else if (game.listActBlock.get(i).getType() == MAPITEM && game.listActBlock.get(i).getTypeId() == 1 && s12t3id1)
				{
					//Do nothing
				}
				else
				{
					listActTemp.add(game.listActBlock.get(i));
				}
			}
			
			game.listActBlock.clear();
			n = listActTemp.size;
			for (int i=0; i<n; i++)
				game.listActBlock.add(listActTemp.get(i));
		break;
		
		case 13:
			listActTemp.clear();
			n = game.listActBlock.size;
			for (int i=0; i<n; i++)
			{
				if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 1 && game.isNeila)
				{
					//Do nothing
				}
				else if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 2 && !game.isNeila)
				{
					//Do nothing
				}
				else if (game.listActBlock.get(i).getType() == ACT && game.listActBlock.get(i).getTypeId() == 0 && s13t1id0)
				{
					//Do nothing
				}
				else
				{
					listActTemp.add(game.listActBlock.get(i));
				}
			}
			
			game.listActBlock.clear();
			n = listActTemp.size;
			for (int i=0; i<n; i++)
				game.listActBlock.add(listActTemp.get(i));
		break;
			
		default:
		break;
		}
	}
	
	public void loadFirstTime()
	{
		//S1
		if (!s1t1id0)
			s1t1id0 = game.game.prefs.load("s1t1id0");
		if (!s1t1id1)
			s1t1id1 = game.game.prefs.load("s1t1id1");
		if (!s1t3id0)
			s1t3id0 = game.game.prefs.load("s1t3id0");
		if (!s1t3id1)
			s1t3id1 = game.game.prefs.load("s1t3id1");
		if (!s1t3id2)
			s1t3id2 = game.game.prefs.load("s1t3id2");
		//S2
		if (!s2t1id0)
			s2t1id0 = game.game.prefs.load("s2t1id0");
		//S3
		if (!s3t3id0)
			s3t3id0 = game.game.prefs.load("s3t3id0");
		//S5
		if (!s5t3id0)
			s5t3id0 = game.game.prefs.load("s5t3id0");
		//S6
		if (!s6t3id0)
			s6t3id0 = game.game.prefs.load("s6t3id0");
		//S7
		if (!s7t0id2)
			s7t0id2 = game.game.prefs.load("s7t0id2");
		//S8
		if (!s8t3id0)
			s8t3id0 = game.game.prefs.load("s8t3id0");
		//S9
		if (!s9t1id1)
			s9t1id1 = game.game.prefs.load("s9t1id1");
		if (!s9t1id2)
			s9t1id2 = game.game.prefs.load("s9t1id2");
		if (!s9t1id3)
			s9t1id3 = game.game.prefs.load("s9t1id3");
		s9t1id4 = false;
		//S10
		if (!s10t0id2)
			s10t0id2 = game.game.prefs.load("s10t0id2");
		//S12
		if (!s12t3id0)
			s12t3id0 = game.game.prefs.load("s12t3id0");
		if (!s12t3id1)
			s12t3id1 = game.game.prefs.load("s12t3id1");
		
		s13t1id0 = false;
		load();
	}
	
	public boolean isDoorUnlocked()
	{
		boolean bool = false;
		
		if (currentAct != null)
		{
			switch (scene)
			{
			case 7:
				if (currentAct.getType() == DOOR && currentAct.getTypeId() == 2 && s7t0id2)
				{
					bool = true;
				}
				else if (currentAct.getType() == DOOR && currentAct.getTypeId() == 2 && !s7t0id2)
				{
					bool = false;
					if (boolItemFail)
					{
						game.canDefaultText = true;
						game.defaultText = "[GREEN]You:[]\nthe door is locked, i need to find a\nway to open it.";
						boolItemFail = false;
					}
				}
				else
				{
					bool = true;
				}
			break;
			
			case 10:
				if (currentAct.getType() == DOOR && currentAct.getTypeId() == 2 && s10t0id2)
				{
					bool = true;
				}
				else if(currentAct.getType() == DOOR && currentAct.getTypeId() == 2 && !s10t0id2)
				{
					bool = false;
					if (boolItemFail)
					{
						game.canDefaultText = true;
						game.defaultText = "[GREEN]You:[]\nthe door is locked, i need to find a\nway to open it.";
						boolItemFail = false;
					}
				}
				else
				{
					bool = true;
				}
				break;

			default:
				bool = true;
			break;
			}
		}
		
		return bool;
	}
	
	public void changeScene()
	{
		if (currentAct.getType() == DOOR)
		{
			//Stop any song in some rooms
			if (scene == 8 && currentAct.getNextScene() == 7 || scene == 13 && currentAct.getNextScene() == 10)
			{
				game.game.audioManager.stopMusic();
			}
			
			//Play specific song
			if (scene == 7 && currentAct.getNextScene() == 8)
			{
				game.game.audioManager.playMusic(musicSAVE);
			}
			else if (scene == 10 && currentAct.getNextScene() == 13)
			{
				game.game.audioManager.playMusic(musicCOMMANDROOM);
			}
			else if (currentAct.getNextScene() == 5)
			{
				game.game.audioManager.playSound(sfxROOMBIGENEMY);
			}

			scene = currentAct.getNextScene();
			game.setScene(scene, currentAct.getNextDoor());
			
			currentAct = null;
			boolItemFail = true;
			bool2 = true;
		}
	}
	
	public Vector2 getStartPosition()
	{
		switch (scene)
		{
				case 0: vec2.set(32, 32); break;
				case 1: vec2.set(160, 32); break;
				case 9: vec2.set(168, 32); break;
				default: vec2.set(32, 32); break;
		}
		
		return vec2;
	}
	
	public void setEvent(ActBlock currentAct)
	{
		this.currentAct = currentAct;
	}
	
	public void removeEvent()
	{
		currentAct = null;
	}
	
	public int getType()
	{
		return currentAct.getType();
	}

	public ActBlock getCurrentAct()
	{
		return currentAct;
	}
	
	public int getScene()
	{
		return scene;
	}
}
