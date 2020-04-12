package models.musicPlayer;

import core.Model;

public interface MusicPlayer extends Model
{
	/**
	 * Plays current music.
	 * 
	 * @return Itself to allow chain calls
	 */
	public abstract MusicPlayer play();
	
	/**
	 * Pauses current music.
	 * 
	 * @return Itself to allow chain calls
	 */
	public abstract MusicPlayer pause();
	
	/**
	 * Stops current music.
	 * 
	 * @return Itself to allow chain calls
	 */
	public abstract MusicPlayer stop();
	
	/**
	 * Changes current music, loading a new file.
	 * 
	 * @param text New text that will be loaded in the player
	 * @return Itself to allow chain calls
	 */
	public abstract MusicPlayer change(String text);
	
	/**
	 * Returns total length of the current music.
	 * 
	 * @return Total length of the current music
	 */
	public abstract long getMusicLength();
	
	/**
	 * Returns how many of the length of the music was played.
	 * 
	 * @return Music length of the music that was played
	 */
	public abstract long getMusicPosition();
	
	/**
	 * Checks if the player is playing a music.
	 * 
	 * @return If the player is playing a music.
	 */
	public abstract boolean isPlaying();
	
	/**
	 * Checks if the player is paused.
	 * 
	 * @return If the player is paused.
	 */
	public abstract boolean isPaused();
	
	/**
	 * Checks if the player is stopped.
	 * 
	 * @return If the player is stopped.
	 */
	public abstract boolean isStopped();
	
}
