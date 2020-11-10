package models.musicPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jfugue.Pattern;
import org.jfugue.Player;

import models.util.Clock;


/**
 * Responsible for representing JFugue music player.
 *
 * @version		1.0.0
 * @since		1.0.0
 */
public class JFugueMusicPlayer extends MusicPlayer implements Runnable
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	private Player player;
	private Pattern musicPattern;
	private boolean pause;
	private boolean finished;
	private boolean stop;
	
	
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
			
		views = new ArrayList<>();
		
		updateText(processedText);
	}
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void run()
	{
		int tickUpdateId = 1;
		int tickUpdateInterval = 100;
		
		
		Clock.setInterval(() -> {
			if (!pause)
				notifyViews();
		}, tickUpdateId, tickUpdateInterval);
		
		player.play(musicPattern);
		
		finished = true;
		player.close();
		Clock.clearInterval(tickUpdateId);
		
		notifyViews();
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#play()
	 */
	@Override
	public MusicPlayer play()
	{
		finished = false;
		stop = false;
		
		if (pause) {
			pause = false;
			player.resume();
		}
		else {
			Thread threadPlay = new Thread(this);

			
			player = new Player();
			threadPlay.start();
			
			try {
				Thread.sleep(400);
			} 
			catch (InterruptedException e) {}
		}
		
		notifyViews();
		
		return this;
	}

	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#pause()
	 */
	@Override
	public MusicPlayer pause()
	{
		if (!player.isFinished()) {
			pause = true;
			player.pause();
			
			notifyViews();
		}
		
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#stop()
	 */
	@Override
	public MusicPlayer stop()
	{
		if (!player.isFinished()) {
			pause = false;
			stop = true;
			player.stop();
			notifyViews();
		}
		
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

		player.saveMidi(musicPattern, outputFile);
		
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
		stop();
		updateText(text);
		this.play();
		
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
		return player.getSequencer().getTickLength();
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#getMusicPosition()
	 */
	@Override
	public long getMusicPosition()
	{
		return player.getSequencer().getTickPosition();
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
		return player.isFinished();
	}

	@Override
	public boolean isFinished()
	{
		return finished && !stop;
	}
	
	private void updateText(List<String> processedText)
	{
		String text = String.join("", processedText);
		
		
		musicPattern = new Pattern(text);
		finished = false;
	}
}
