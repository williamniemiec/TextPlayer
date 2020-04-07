package models;

import java.util.ArrayList;
import java.util.List;

import views.View;

public class MusicPlayer implements Model 
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	private List<View> views = new ArrayList<>();
	private String musicalNotes;
	
	
	//-----------------------------------------------------------------------
	//		Constructor
	//-----------------------------------------------------------------------
	public MusicPlayer(String musicalNotes)
	{
		this.musicalNotes = musicalNotes;
	}
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void attach(View view) 
	{
		views.add(view);
	}
	
	@Override
	public void detach(View view) 
	{
		views.remove(view);
	}
	
	@Override
	public void notifyViews() 
	{
		for (View view : views) {
			view.update(this, null);
		}
	}
	
	
	public MusicPlayer play()
	{
		notifyViews();
		return this;
	}
	
	public MusicPlayer pause()
	{
		notifyViews();
		return this;
	}
	
	public MusicPlayer stop()
	{
		notifyViews();
		return this;
	}
	
	public long getMusicLength()
	{
		
	}
	
	public long getMusicPosition()
	{
		
	}
	
	public boolean isPlaying()
	{
		
	}
	
	public boolean isPaused()
	{
		
	}
	
	public boolean isStopped()
	{
		
	}
	
	public MusicPlayer change(String musicalNotes)
	{
		
	}
	
}
