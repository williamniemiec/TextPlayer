package models.musicPlayer;

import org.jfugue.pattern.PatternProducer;
import org.jfugue.player.Player;

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
	private Player player = new Player();
	
	
	//-----------------------------------------------------------------------
	//		Constructor
	//-----------------------------------------------------------------------
	/**
	 * Music player that will play with JFugue.
	 * 
	 * @param		processedText Text with JFugue commands.
	 * 
	 * @throws		IllegalArgumentException If processed text is null 
	 */
	public JFugueMusicPlayer(List<String> processedText)
	{
		if (processedText == null)
			throw new IllegalArgumentException("Processed text cannot be null");
			
		this.processedText = processedText;
		views = new ArrayList<>();
	}
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	
	//---------------------------------------------------------------------

	public void playJfuguePlayer()
	{
		player.play(processedText.toString());
	}

	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#play()
	 */
	@Override
	public MusicPlayer play()
	{
		notifyViews();

		//...
	}

	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#pause()
	 */
	@Override
	public MusicPlayer pause()
	{
		player.pause();
		
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
		player.stop();
		
		notifyViews();
		
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#saveMidi(java.io.File)
	 * 
	 * @throws		IllegalArgumentException If output file is null or if 
	 * output file does not have MIDI extension
	 */
	@Override
	public MusicPlayer saveMidi(File outputFile) throws IOException
	{
		if (outputFile == null)
			throw new IllegalArgumentException("Output file cannot be null");
		
		if (!outputFile.getName().endsWith("midi"))
			throw new IllegalArgumentException("Output file extension must be '.midi'");
		
		player.saveMidi(outputFile);
		
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
		return player.isPlaying();
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#isPaused()
	 */
	@Override
	public boolean isPaused()
	{
		return player.isPaused();
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#isStopped()
	 */
	@Override
	public boolean isStopped()
	{
		return player.isStopped();
	}
}
