package models.parser.jfugue;

import java.util.HashMap;
import java.util.Map;

import models.util.Pair;



public class JFugueMusicalNote 
{
	private static final int DEFAULT_OCTAVE = 5;
	private Map<Integer, Integer> octave = new HashMap<>();
	private int currentOctave = DEFAULT_OCTAVE;
	private char note;
	
	public JFugueMusicalNote(char note, int initialValue)
	{
		this.note = note;
		int range = 12;
		
		for (int i=0; i<=10; i++) {
			int newValue = initialValue + range * i;
			
			if (newValue <= 127)
				octave.put(i, initialValue + range * i);
		}
	}
	
	public int getCurrentOctave()
	{
		return currentOctave;
	}
	
	/**
	 * Gets the musical note according to its octave.
	 * 
	 * @return		Musical note character along with its numerical 
	 * representation in JFugue
	 */
	public Pair<Character, Integer> getNote()
	{
		return Pair.of(note, octave.get(currentOctave));
	}
	
	public void increaseOctave()
	{
		if (currentOctave + 1 < octave.size())
			currentOctave++;
		else
			currentOctave = DEFAULT_OCTAVE;
	}
}
