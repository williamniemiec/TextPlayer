package controllers;

import java.io.File;

import javax.swing.JMenuItem;

import core.Controller;
import models.musicPlayer.JFugueMusicPlayer;
import models.musicPlayer.MusicPlayer;
import models.parse.JFugueMusicParser;
import models.parse.Parser;
import util.FileUtil;
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
	private String musicalText;
	private TextPlayerView textPlayerView;
	private MusicPlayer musicPlayer;
	private String originalText;
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
	public TextPlayerController(String musicalText, String originalText, String filename)
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
		textPlayerView = new TextPlayerView(this, mainFrame);
		
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
	 * Select another file to generate music.
	 * 
	 * @param		filepath Filename
	 */
	public void changeFile(String filepath)
	{
		String parsedFile;
		Parser parser = new Parser(new JFugueMusicParser());
		File file = new File(filepath);
		
		
		// Process the file
		parsedFile = parser.open(file).parse().get();
		
		// Loads processed file into the player
		musicPlayer.change(parsedFile);
		
		// Updates view with informations about the loaded file
		originalText = FileUtil.extractText(file);
		this.filename = file.getName();
		textPlayerView.updateFileContent();
	}
	
//	// será feito na view
//	public void updateMenuBar(MusicPlayer mp)
//	{	
//		//((JMenuItem)getComponent("mb_ctrl_play")).setEnabled(!mp.isPlaying());
//		//((JMenuItem)getComponent("mb_ctrl_pause")).setEnabled(!mp.isPaused());
//		//((JMenuItem)getComponent("mb_ctrl_stop")).setEnabled(!mp.isStopped());
//	}
	
	/**
	 * Updates music player control buttons.
	 */
	private void updateControlsMenu()
	{
		((JMenuItem)getComponent("mb_file_close")).setEnabled(true);
		((JMenuItem)getComponent("mb_ctrl_play")).setEnabled(true);
	}
	
	
	//-----------------------------------------------------------------------
	//		Getters & Setters
	//-----------------------------------------------------------------------
	public String getText()
	{
		return this.originalText;
	}
	
	public void setText(String text)
	{
		this.originalText = text;
	}
	
	public TextPlayerView getView()
	{
		return textPlayerView;
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public void setFilename(String filename)
	{
		this.filename = filename;
	}
}
