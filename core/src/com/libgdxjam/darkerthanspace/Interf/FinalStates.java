package com.libgdxjam.darkerthanspace.Interf;

public interface FinalStates
{
	//Act
	final int DOOR = 0, ACT = 1, LIGHT = 2, MAPITEM = 3;
	
	//GameState
	final int INGAME=0, TEXT=1, CHANGESCENE=2, MENU=3, PAUSE=4, FINISHMENU = 5, RESETGAME = 6;
	
	//Pos
	final int NOTHING = -1, LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
}
