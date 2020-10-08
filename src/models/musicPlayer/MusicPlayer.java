package models.musicPlayer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import core.Model;
import core.View;


/**
 * Responsible for representing music players.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public abstract class MusicPlayer implements Model
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	protected List<View> views;
		
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#attach(View)
	 */
	@Override
	public void attach(View view) 
	{
		views.add(view);
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#detach(View)
	 */
	@Override
	public void detach(View view) 
	{
		views.remove(view);
	}
	
	/**
	 * {@inheritDoc}
	 * @see			MusicPlayer#notifyViews()
	 */
	@Override
	public void notifyViews() 
	{
		for (View view : views) {
			view.update(this, null);
		}
	}
	
	/**
	 * Plays current music.
	 * 
	 * @return		Itself to allow chained calls
	 */
	public abstract MusicPlayer play();
	
	/**
	 * Pauses current music.
	 * 
	 * @return		Itself to allow chained calls
	 */
	public abstract MusicPlayer pause();
	
	/**
	 * Stops current music.
	 * 
	 * @return		Itself to allow chained calls
	 */
	public abstract MusicPlayer stop();
	
	/**
	 * Saves the generated music in a MIDI file.
	 * 
	 * @param		outputFile Output file with MIDI extension
	 * 
	 * @return		Itself to allow chained calls
	 */
	public abstract MusicPlayer saveMidi(File outputFile) throws IOException;
	
	/**
	 * Changes current music, loading a new file.
	 * 
	 * @param		text New text that will be loaded in the player
	 * 
	 * @return		Itself to allow chained calls
	 */
	public abstract MusicPlayer change(List<String> text);
	
	/**
	 * Returns total length of the current music.
	 * 
	 * @return		Total length of the current music
	 */
	public abstract long getMusicLength();
	
	/**
	 * Returns how many of the length of the music was played.
	 * 
	 * @return		Music length of the music that was played
	 */
	public abstract long getMusicPosition();
	
	/**
	 * Checks if the player is playing a music.
	 * 
	 * @return		If the player is playing a music.
	 */
	public abstract boolean isPlaying();
	
	/**
	 * Checks if the player is paused.
	 * 
	 * @return		If the player is paused.
	 */
	public abstract boolean isPaused();
	
	/**
	 * Checks if the player is stopped.
	 * 
	 * @return		If the player is stopped.
	 */
	public abstract boolean isStopped();
}
