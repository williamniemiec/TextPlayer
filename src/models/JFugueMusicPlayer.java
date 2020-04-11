package models;

import java.util.ArrayList;
import java.util.List;

import views.View;


public class JFugueMusicPlayer implements MusicPlayer, Model
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	private List<View> views;

	/**
	 * Text that the player will read and play.
	 */
	private String text;
	
	
	//-----------------------------------------------------------------------
	//		Constructor
	//-----------------------------------------------------------------------
	/**
	 * Music player that will play with JFugue.
	 * 
	 * @param musicalNotes Text with JFugue commands.
	 */
	public JFugueMusicPlayer(String musicalNotes)
	{
		this.text = musicalNotes;
		views = new ArrayList<>();
		
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
	//---------------------------------------------------------------------
	
	public MusicPlayer play()
	{
		notifyViews();
		
		//...
		
		return this;
	}
	
	public MusicPlayer pause()
	{
		notifyViews();
		
		//...
		
		return this;
	}
	
	public MusicPlayer stop()
	{
		notifyViews();
		
		//...
		
		return this;
	}
	
	public MusicPlayer change(String musicalNotes)
	{
		//...
		
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
}
