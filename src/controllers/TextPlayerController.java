package controllers;

import javax.swing.JMenuItem;

import models.MusicPlayer;
import views.TextPlayerView;


public class TextPlayerController extends Controller {
	private String musicalText;
	private HomeController homeController;
	private TextPlayerView textPlayerView;
	private MusicPlayer musicPlayer;
	private String originalText;
	private String filename;
	
	public TextPlayerController(HomeController homeController, String musicalText, String originalText, String filename)
	{
		this.homeController = homeController;
		this.musicalText = musicalText;
		this.originalText = originalText;
		this.filename = filename;
		
	}
	
	@Override
	public void run() {
		textPlayerView = new TextPlayerView(this, mainFrame);
		
		((JMenuItem)getComponent("mb_file_close")).setEnabled(true);
		((JMenuItem)getComponent("mb_ctrl_play")).setEnabled(true);
		
		addView("TextPlayerView", textPlayerView);
	}
	
	public String getText()
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
	
	
	public void play()
	{
		
	}
	
	public void pause()
	{
		
	}
	
	public void stop()
	{
		
	}
	
	public void changeFile()
	{
		
	}
	
	public void updateMenuBar(MusicPlayer mp)
	{	
		//((JMenuItem)getComponent("mb_ctrl_play")).setEnabled(!mp.isPlaying());
		//((JMenuItem)getComponent("mb_ctrl_pause")).setEnabled(!mp.isPaused());
		//((JMenuItem)getComponent("mb_ctrl_stop")).setEnabled(!mp.isStopped());
	}
	
}
