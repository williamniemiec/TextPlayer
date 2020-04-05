package models;

import views.View;

public class MusicPlayer implements Model {
	// attach  view
	public void attach(View view) {}
	public void detach(View view) {}
	public void notifyViews() {}
	
	private String musicalNotes;
	
	public MusicPlayer(String musicalNotes)
	{
		this.musicalNotes = musicalNotes;
	}
	
	public MusicPlayer play()
	{
		return this;
	}
	
	public MusicPlayer pause()
	{
		return this;
	}
	
	public MusicPlayer stop()
	{
		return this;
	}
	
	/*
	public MusicPlayer change(String musicalNotes)
	{
		
	}
	*/
}
