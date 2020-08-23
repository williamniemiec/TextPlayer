package models.parse;

import java.io.File;
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
	private File file;
	private ParseType parseType;
	private String parsedText;
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
	
	/**
	 * Opens the file to be parsed.
	 * 
	 * @param		filename Filename
	 * 
	 * @return		Itself to allow chained calls
	 * 
	 * @throws		IllegalArgumentException If file is null
	 */
	public Parser open(File file)
	{
		if (file == null)
			throw new IllegalArgumentException("File cannot be empty");
		
		this.file = file;
		
		return this;
	}
	
	/**
	 * Opens the file to be parsed.
	 * 
	 * @param		filename Filename
	 * 
	 * @return		Itself to allow chained calls
	 */
	public Parser open(String filename)
	{
		this.file = new File(filename);
		
		return this;
	}
	
	/**
	 * Parses the opened file.
	 * 
	 * @return		Itself to allow chained calls
	 * 
	 * @throws		IllegalStateException If file has not been opened
	 */
	public Parser parse()
	{
		if (file == null)
			throw new IllegalStateException("A file must be opened before parsing");
		parsedText = parseType.parseFile(file);
		notifyViews();
		
		return this;
	}
	
	/**
	 * Gets parsed content.
	 * 
	 * @return		Parsed content
	 */
	public String get()
	{
		return parsedText;
	}
}
