package com.libgdxjam.darkerthanspace.utils;

import com.badlogic.gdx.math.Rectangle;
import com.libgdxjam.darkerthanspace.Interf.FinalStates;

public class CheckCollision implements FinalStates
{
	/*private Vector2 centerA;
	private Vector2 centerB;
	private boolean isClose;*/
	private int pad = 5; //pad only for safety
	private int returnValue;
	
	public CheckCollision()
	{
		/*centerA = new Vector2();
		centerB = new Vector2();*/
	}

	
	public int check(Rectangle a, Rectangle b)
	{
		//a = player
		//b = block
		
		//If is close, can calc
		/*a.getCenter(centerA);
		b.getCenter(centerB);

		if (centerA.dst(centerB) > 500)
		{
			return NOTHING;
		}*/
		
		returnValue = NOTHING;
		
		//CheckOverlap
		if (a.overlaps(b))
		{
			//Block Below player
			if (	a.x+a.width > b.x &&
							a.x < b.x+b.width &&
							a.y >= b.y+b.height-pad)
			{
				returnValue = DOWN;
			}
				//Block Above player
				else if (	a.x+a.width > b.x &&
								a.x < b.x+b.width &&
								a.y+a.height <= b.y+pad)
				{
					returnValue = UP;
				}
					//Block in the left of the player
					else if (	a.x >= b.x+b.width-pad &&
									a.y <= b.y+b.height &&
									a.y+a.height >= b.y)
					{
						returnValue = LEFT;
					}
						//Block in the right of the player
						else if (	a.x+a.width <= b.x+pad &&
										a.y <= b.y+b.height &&
										a.y+a.height >= b.y)
						{
							returnValue = RIGHT;
						}
		}
		
		return returnValue;
	}
}