package models.parser;

import java.util.ArrayList;
import java.util.List;

import core.Model;
import core.View;
import models.musicPlayer.MusicPlayer;


/**
 * Responsible for processing a file.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public class Parser implements Model
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private ParseType parseType;
	private List<View> views;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	/**
	 * Creates file processing.
	 * 
	 * @param		parseType File parser
	 * 
	 * @throws		IllegalArgumentException If parseType is null
	 */
	public Parser(ParseType parseType)
	{
		if (parseType == null)
			throw new IllegalArgumentException("Parser cannot be empty");
		
		this.parseType = parseType;
		
		views = new ArrayList<>();
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
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
	 * Parses the opened file.
	 * 
	 * @param		content Content to be processed
	 * 
	 * @return		Parsed content
	 * 
	 * @throws		IllegalArgumentException If content is null or empty
	 */
	public List<String> parse(List<String> content)
	{
		if (content == null || content.isEmpty())
			throw new IllegalArgumentException("Content cannot be empty");
		
		List<String> parsedText = parseType.parse(content);
		
		
		notifyViews();
		
		return parsedText;
	}
}
