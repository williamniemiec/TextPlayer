package controllers;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;

import core.Controller;
import models.input.dialog.InputContent;
import models.input.dialog.InputDialogType;
import models.input.dialog.InputManager;
import models.musicPlayer.JFugueMusicPlayer;
import models.musicPlayer.MusicPlayer;
import models.parse.JFugueMusicParser;
import models.parse.Parser;
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
		this.musicalText = musicalText;
		this.originalText = originalText;
		this.filename = filename;
	}
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void run() 
	{
		// Initializes TextPlayerView
		textPlayerView = new TextPlayerView(this, mainFrame, RB);
		
		// Updates top bar buttons
		updateControlsMenu();
		
		// Creates music player
		musicPlayer = new JFugueMusicPlayer(musicalText);
		musicPlayer.attach(textPlayerView);
		
		// Displays TextPlayerView
		addView("TextPlayerView", textPlayerView);
		loadView("TextPlayerView");
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
	 * @param		newContent New text along with its filename
	 */
	public void changeText(InputContent newContent)
	{
		List<String> parsedFile;
		Parser parser = new Parser(new JFugueMusicParser());
		
		
		// Process the file
		parsedFile = parser.parse(newContent.getContent());
		
		// Loads processed file into the player
		musicPlayer.change(parsedFile);
		
		// Updates view with informations about the loaded file
		originalText = newContent.getContent();
		filename = newContent.getName();
		textPlayerView.update_content();
	}
	
	public InputContent getContent(InputDialogType inputDialogType) throws IOException
	{
		return InputManager.getContent(mainFrame, inputDialogType);
	}
	
	/**
	 * Updates music player control buttons.
	 */
	private void updateControlsMenu()
	{
		((JMenuItem)getComponent("mb_file_close")).setEnabled(true);
		((JMenuItem)getComponent("mb_ctrl_playPause")).setEnabled(true);
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
