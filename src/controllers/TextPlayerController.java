package controllers;

import java.io.File;

import javax.swing.JMenuItem;

import core.Controller;
import models.IOManager;
import models.musicPlayer.JFugueMusicPlayer;
import models.musicPlayer.MusicPlayer;
import models.parse.JFugueMusicParser;
import models.parse.Parser;
import views.TextPlayerView;


public class TextPlayerController extends Controller 
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	private String musicalText;
	private TextPlayerView textPlayerView;
	private MusicPlayer musicPlayer;
	private String originalText;
	private String filename;
	

	//-----------------------------------------------------------------------
	//		Constructor
	//-----------------------------------------------------------------------
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
		textPlayerView = new TextPlayerView(this, mainFrame);
		
		updateControlsMenu();
		
		musicPlayer = new JFugueMusicPlayer(musicalText);
		musicPlayer.attach(textPlayerView);
		
		addView("TextPlayerView", textPlayerView);
		loadView("TextPlayerView");
	}
	
	
	public void play()
	{
		musicPlayer.play();
	}
	
	public void pause()
	{
		musicPlayer.pause();
	}
	
	public void stop()
	{
		musicPlayer.stop();
	}
	
	public void changeFile(String filepath)
	{
		String parsedFile;
		Parser parser = new Parser(new JFugueMusicParser());
		File file = new File(filepath);
		
		// SE DER ALGUM ERRO, TRATAR AQUI
		parsedFile = parser.open(file).parse().get();
		musicPlayer.change(parsedFile);
		
		originalText = IOManager.extractText(file);
		this.filename = file.getName();
		textPlayerView.updateFileContent();
	}
	
	public void updateMenuBar(MusicPlayer mp)
	{	
		//((JMenuItem)getComponent("mb_ctrl_play")).setEnabled(!mp.isPlaying());
		//((JMenuItem)getComponent("mb_ctrl_pause")).setEnabled(!mp.isPaused());
		//((JMenuItem)getComponent("mb_ctrl_stop")).setEnabled(!mp.isStopped());
	}
	
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
