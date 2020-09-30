package models.musicPlayer;

import java.util.ArrayList;
import java.util.List;

import core.View;


/**
 * Responsible for representing JFugue music player.
 *
 * @version		1.0.0
 * @since		1.0.0
 */
public class JFugueMusicPlayer implements MusicPlayer
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
	 * @param		musicalNotes Text with JFugue commands.
	 */
	public JFugueMusicPlayer(String musicalNotes)
	{
		this.text = musicalNotes;
		views = new ArrayList<>();
		
	}
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	/**
	 * {@inheritDoc}
	 * @see		MusicPlayer#attach(View)
	 */
	@Override
	public void attach(View view) 
	{
		views.add(view);
	}
	
	/**
	 * {@inheritDoc}
	 * @see		MusicPlayer#detach(View)
	 */
	@Override
	public void detach(View view) 
	{
		views.remove(view);
	}
	
	/**
	 * {@inheritDoc}
	 * @see		MusicPlayer#notifyViews()
	 */
	@Override
	public void notifyViews() 
	{
		for (View view : views) {
			view.update(this, null);
		}
	}
	
	//---------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 * @see		MusicPlayer#play()
	 */
	@Override
	public MusicPlayer play()
	{
		notifyViews();
		
		//...
		
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see		MusicPlayer#pause()
	 */
	@Override
	public MusicPlayer pause()
	{
		notifyViews();
		
		//...
		
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see		MusicPlayer#stop()
	 */
	@Override
	public MusicPlayer stop()
	{
		notifyViews();
		
		//...
		
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see		MusicPlayer#change(String)
	 */
	@Override
	public MusicPlayer change(String musicalNotes)
	{
		//...
		
		notifyViews();
		
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see		MusicPlayer#getMusicLength()
	 */
	@Override
	public long getMusicLength()
	{
		//...
	}
	
	/**
	 * {@inheritDoc}
	 * @see		MusicPlayer#getMusicPosition()
	 */
	@Override
	public long getMusicPosition()
	{
		//...
	}
	
	/**
	 * {@inheritDoc}
	 * @see		MusicPlayer#isPlaying()
	 */
	@Override
	public boolean isPlaying()
	{
		//...
	}
	
	/**
	 * {@inheritDoc}
	 * @see		MusicPlayer#isPaused()
	 */
	@Override
	public boolean isPaused()
	{
		//...
	}
	
	/**
	 * {@inheritDoc}
	 * @see		MusicPlayer#isStopped()
	 */
	@Override
	public boolean isStopped()
	{
		//...
	}
}
