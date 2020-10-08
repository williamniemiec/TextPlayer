package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import core.Controller;
import models.io.IOType;
import models.io.InputContent;
import models.io.dialog.IOManager;
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
		try {
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
	
	public InputContent getContent(IOType inputDialogType)
	{
		InputContent content = null;
		
		
		try {
			content = IOManager.getContent(mainFrame, inputDialogType);
		} 
		catch (IOException e) {
			onException(e);
		}
		
		return content;
	}
	
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
	 * Updates music player control buttons.
	 */
	private void updateControlsMenu()
	{
		((JMenuItem)getComponent("mb_file_close")).setEnabled(true);
		((JMenuItem)getComponent("mb_ctrl_playPause")).setEnabled(true);
	}
	
	private void onException(Exception e)
	{
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
