package controllers;

import models.MusicPlayer;

public class MusicController implements Controller {
	private String musicalNotes;
	
	
	public MusicController(String musicalNotes)
	{
		
	}


	@Override
	public void run() {
		MusicPlayer player = new MusicPlayer(musicalNotes);
		
	}
}
