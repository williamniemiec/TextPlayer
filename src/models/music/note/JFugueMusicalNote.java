package models.music.note;

import java.util.HashMap;
import java.util.Map;

import models.util.Pair;


/**
 * Responsible for representing JFugue musical notes.
 */
public class JFugueMusicalNote 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private static final int DEFAULT_OCTAVE = 5;
	private Map<Integer, Integer> octaves = new HashMap<>();
	private int currentOctave = DEFAULT_OCTAVE;
	private char note;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	public JFugueMusicalNote(char note, int initialValue)
	{
		this.note = note;
		int range = 12;
		
		for (int i=0; i<=10; i++) {
			int newValue = initialValue + range * i;
			
			if (newValue <= 127)
				octaves.put(i, initialValue + range * i);
		}
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	/**
	 * Increases octave by one unit. In case of overflow, sets the default 
	 * value.
	 */
	public void increaseOctave()
	{
		if (currentOctave + 1 < octaves.size())
			currentOctave++;
		else
			currentOctave = DEFAULT_OCTAVE;
	}
	
	
	//-------------------------------------------------------------------------
	//		Getters
	//-------------------------------------------------------------------------
	/**
	 * Gets the musical note according to its octave.
	 * 
	 * @return		Musical note character along with its numerical 
	 * representation in JFugue
	 */
	public Pair<Character, Integer> getNote()
	{
		return Pair.of(note, octaves.get(currentOctave));
	}
	
	/**
	 * Gets current octave.
	 * 
	 * @return		Octave
	 */
	public int getOctave()
	{
		return octaves.get(currentOctave);
	}
}
