package models;

public class MusicPlayer {
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
