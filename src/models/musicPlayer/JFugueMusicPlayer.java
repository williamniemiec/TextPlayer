package models.musicPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Responsible for representing JFugue music player.
 *
 * @version		1.0.0
 * @since		1.0.0
 */
public class JFugueMusicPlayer extends MusicPlayer
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	/**
	 * Text that the player will read and play.
	 */
	private List<String> processedText;
	
	
	//-----------------------------------------------------------------------
	//		Constructor
	//-----------------------------------------------------------------------
	/**
	 * Music player that will play with JFugue.
	 * 
	 * @param		processedText Text with JFugue commands.
	 */
	public JFugueMusicPlayer(List<String> processedText)
	{
		this.processedText = processedText;
		views = new ArrayList<>();
	}
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#play()
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
	 * @see			MusicPlayer#pause()
	 */
	@Override
	public MusicPlayer pause()
	{
		//...
		
		notifyViews();
		
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#stop()
	 */
	@Override
	public MusicPlayer stop()
	{
		//...
		
		notifyViews();
		
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#saveMidi(java.io.File)
	 */
	@Override
	public MusicPlayer saveMidi(File output) throws IOException
	{
		//...
		
		notifyViews();
		
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#change(String)
	 */
	@Override
	public MusicPlayer change(List<String> text)
	{
		//...
		
		notifyViews();
		
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#getMusicLength()
	 */
	@Override
	public long getMusicLength()
	{
		//...
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#getMusicPosition()
	 */
	@Override
	public long getMusicPosition()
	{
		//...
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#isPlaying()
	 */
	@Override
	public boolean isPlaying()
	{
		//...
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#isPaused()
	 */
	@Override
	public boolean isPaused()
	{
		//...
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#isStopped()
	 */
	@Override
	public boolean isStopped()
	{
		//...
	}
}
