package com.libgdxjam.darkerthanspace.Interf;

import com.badlogic.gdx.Screen;
import com.libgdxjam.darkerthanspace.MainClass;

public class ScreenInterface implements Screen
{
	public MainClass game;
	
	public ScreenInterface(MainClass game)
	{
		this.game = game;
	}
	
	@Override
	public void show()
	{
		initialize();
	}

	public void initialize() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta)
	{
		logic(delta);
		draw();
	}

	public void draw() {
		// TODO Auto-generated method stub
	}
	
	public void logic(float delta) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		System.out.println("pause");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
