package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import core.Controller;
import models.io.IOType;
import models.io.input.dialog.IOManager;
import models.io.input.dialog.InputDialog;
import models.musicPlayer.JFugueMusicPlayer;
import models.musicPlayer.MusicPlayer;
import models.parser.JFugueMusicParser;
import models.parser.Parser;
import views.TextPlayerView;


/**
 * Responsible for {@link TextPlayerView} behavior.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public class TextPlayerController extends Controller 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private static final ResourceBundle RB = 
			ResourceBundle.getBundle("resources.lang.textplayer.textplayer");
	private List<String> musicalText;
	private TextPlayerView textPlayerView;
	private MusicPlayer musicPlayer;
	private List<String> originalText;
	private String filename;
	

	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	/**
	 * Controller for {@link TextPlayerView}.
	 * 
	 * @param		musicalText Text containing musical commands obtained 
	 * after processing the file text
	 * @param		originalText File text before processing
	 * @param		filename Text filename
	 */
	public TextPlayerController(List<String> musicalText, List<String> originalText, String filename)
	{
		if (musicalText == null)
			throw new IllegalArgumentException("Musical text cannot be null");
		
		if (originalText == null)
			throw new IllegalArgumentException("Original text cannot be null");
		
		this.musicalText = musicalText;
		this.originalText = originalText;
		this.filename = (filename == null) ? "N/A" : filename;
	}
	
	public TextPlayerController(List<String> musicalText, List<String> originalText)
	{
		this(musicalText, originalText, null);
	}
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void run() 
	{
		try {
			// Initializes TextPlayerView
			textPlayerView = new TextPlayerView(this, mainFrame, RB);
			
			// Updates top bar buttons
			setMenuBarItemStatus("mb_file_close", true);
			setMenuBarItemStatus("mb_ctrl_playPause", true);
			setMenuBarItemStatus("mb_ctrl_stop", false);
			
			// Creates music player
			musicPlayer = new JFugueMusicPlayer(musicalText);
			musicPlayer.attach(textPlayerView);
			
			// Displays TextPlayerView
			addView("TextPlayerView", textPlayerView);
			loadView("TextPlayerView");
		} 
		catch (IOException e) {
			onException(e);
			Controller.loadView("HomeView");
		}
	}
	
	/**
	 * Plays the music that was generated from a text.
	 */
	public void play()
	{
		musicPlayer.play();
	}
	
	/**
	 * Pauses the playing music.
	 */
	public void pause()
	{
		musicPlayer.pause();
	}
	
	/**
	 * Stops the music playing and positions the player to the beginning of it.
	 */
	public void stop()
	{
		musicPlayer.stop();
	}
	
	/**
	 * Select another text to generate music.
	 * 
	 * @param		newText text Text to be converted to music
	 * @param		filename Filename containing the text, or null if the text
	 * did not come from a file
	 * 
	 * @throws		IllegalArgumentException If newText is null
	 */
//	private void changeText(List<String> newText, String filename)
//	{
//		if (newText == null)
//			throw new IllegalArgumentException("New text cannot be null");
//		
//		List<String> parsedFile;
//		Parser parser = new Parser(new JFugueMusicParser());
//		
//		
//		// Process the file
//		parsedFile = parser.parse(newText);
//		
//		// Loads processed file into the player
//		musicPlayer.change(parsedFile);
//		
//		// Updates view with informations about the loaded file
//		originalText = newText;
//		this.filename = (filename == null) ? "N/A" : filename;
//		textPlayerView.update_content();
//	}
	
	public void changeText(InputDialog id)
	{
		if (id == null)
			throw new IllegalArgumentException("Input dialog cannot be null");
		
		if (!id.ask())
			return;
		
		List<String> parsedFile;
		Parser parser = new Parser(new JFugueMusicParser());
		
		
		// Process the file
		parsedFile = parser.parse(id.getContent());
		
		// Loads processed file into the player
		musicPlayer.change(parsedFile);
		
		// Updates view with informations about the loaded file
		originalText = id.getContent();
		filename = id.getTitle();
		
		textPlayerView.update_content();
	}
	
	/**
	 * Select another text to generate music.
	 * 
	 * @param		newText text Text to be converted to music
	 * @param		filename Filename containing the text, or null if the text
	 * did not come from a file
	 * 
	 * @throws		IllegalArgumentException If newText is null
	 */
//	public void changeText(IOType inputDialogType)
//	{
//		String filename = null;
//		List<String> text = null;
//		
//		
//		switch (inputDialogType) {
//			case FILE_LOAD:
//				File file = IOManager.getFile(mainFrame);
//				
//				try {
//					text = Files.readAllLines(file.toPath());
//				} 
//				catch (IOException e) {}
//				
//				break;
//			case TEXT:
//				text = IOManager.getText(mainFrame);
//				
//				break;
//			default:
//				break;
//		}
//		
//		changeText(text, filename);
//	}
	
	/**
	 * Exports generated music to a MIDI file.
	 * 
	 * @return		True if the file has been successfully saved, or false 
	 * otherwise
	 * 
	 * @throws		IllegalArgumentException If file extension is not MIDI
	 */
	public void exportMusicFile()
	{
		String extension = "midi";
		File outputFile = IOManager.getOutput(mainFrame, extension);
		
		
		if (outputFile == null)
			return;
		
		try {
			if (!outputFile.getName().endsWith(extension))
				throw new IllegalArgumentException("File extension must be '.midi'");
		
			musicPlayer.saveMidi(outputFile);
		} 
		catch (IOException | IllegalArgumentException e) {
			onException(e);
		}
	}
	
	/**
	 * Defines whether a menu item should be enabled or not.
	 * 
	 * @param		menuBarItemLabel Menu item label
	 * @param		enable True if the menu item should be enabled; false 
	 * otherwise
	 */
	public void setMenuBarItemStatus(String menuBarItemLabel, boolean enable) 
	{
		JMenuItem menuBarItem = ((JMenuItem)getComponent(menuBarItemLabel));
		
		
		if (menuBarItem != null)
			menuBarItem.setEnabled(enable);
	}
	
	/**
	 * Defines exception behavior.
	 * 
	 * @param		e Exception
	 * 
	 * @throws		IllegalArgumentException If exception is null
	 */
	private void onException(Exception e)
	{
		if (e == null)
			throw new IllegalArgumentException("Exception cannot be null");
		
		JOptionPane.showMessageDialog(
				mainFrame, 
				e.getMessage(), 
				"Error", 
				JOptionPane.ERROR_MESSAGE
		);
	}
	
	
	//-----------------------------------------------------------------------
	//		Getters & Setters
	//-----------------------------------------------------------------------
	public List<String> getText()
	{
		return this.originalText;
	}
	
	public TextPlayerView getView()
	{
		return textPlayerView;
	}
	
	public String getFilename()
	{
		return filename;
	}
}
