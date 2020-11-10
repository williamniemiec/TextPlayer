package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import core.Controller;
import models.io.input.dialog.FileInputDialog;
import models.io.input.dialog.FileInputType;
import models.io.input.dialog.InputDialog;
import models.music.player.JFugueMusicPlayer;
import models.music.player.MusicPlayer;
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
			textPlayerView = new TextPlayerView(this, mainFrame);
			
			// Creates music player
			musicPlayer = new JFugueMusicPlayer(musicalText);
			musicPlayer.attach(textPlayerView);
			
			updateMenuBarItems();
			
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
	 * @param		dialog Specifies how the text to be converted to 
	 * music will be obtained
	 * 
	 * @throws		IllegalArgumentException If newText is null
	 */
	public void changeText(InputDialog dialog)
	{
		if (dialog == null)
			throw new IllegalArgumentException("Input dialog cannot be null");
		
		boolean wasFileChosen = dialog.openDialog();
		List<String> parsedFile;
		Parser parser;
		
		
		if (!wasFileChosen || dialog.getContent().isEmpty())
			return;
		
		// Process the file
		parser = new Parser(new JFugueMusicParser());
		parsedFile = parser.parse(dialog.getContent());
		
		// Loads processed file into the player
		musicPlayer.change(parsedFile);
		
		// Updates view with informations about the loaded file
		originalText = dialog.getContent();
		filename = dialog.getTitle();
		
		textPlayerView.updateContent();
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
		File outputFile;
		FileInputDialog inputDialog = new FileInputDialog(mainFrame, extension, FileInputType.STORE);
		boolean wasFileChoosen = inputDialog.openDialog();
		
		
		if (!wasFileChoosen)
			return;
				
		outputFile = inputDialog.getFile();
		
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
		JMenuItem menuBarItem = (JMenuItem) getComponent(menuBarItemLabel);
		
		
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
	
	private void updateMenuBarItems()
	{
		JMenuItem mbCtrlPlayPause = ((JMenuItem)getComponent("mb_ctrl_playPause"));
		JMenuItem mbCtrlStop = ((JMenuItem)getComponent("mb_ctrl_stop"));
		
		
		setMenuBarItemStatus("mb_file_close", true);
		setMenuBarItemStatus("mb_ctrl_playPause", true);
		setMenuBarItemStatus("mb_ctrl_stop", false);
		
		mbCtrlPlayPause.addActionListener(event -> {
			if (musicPlayer.isPlaying())
				pause();
			else 
				play();
		});
		
		mbCtrlStop.addActionListener(event -> stop());
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
