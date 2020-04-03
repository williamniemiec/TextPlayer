package views;

import controllers.MusicController;

public class MusicView implements View 
{
	@SuppressWarnings("unused")
	private MusicController musicController;
	
	
	public MusicView(MusicController musicController)
	{
		this.musicController = musicController;
	}
	
	
	@Override
	public void close() 
	{
		
	}
}
