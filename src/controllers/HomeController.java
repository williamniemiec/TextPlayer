package controllers;

import java.awt.CardLayout;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import models.MusicParser;
import models.MusicPlayer;
import models.Parser;
import views.HomeView;


/**
 * Main controller. It will be responsible for program's main screen behavior.
 */
public class HomeController extends Controller 
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	@SuppressWarnings("unused")
	private HomeView homeView;
	private TextPlayerController textPlayerController;
	
	//private JPanel contentPane;
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void run()
	{
		// Init content pane
		//contentPane = new JPanel(new CardLayout());
		homeView = new HomeView(this, mainFrame);
		//contentPane.add(homeView, "HomeView");
		
		//mainFrame.setContentPane(contentPane);
		addView("HomeView", homeView);
		mainFrame.setVisible(true);
		
	}

	public void addMainFrameComponent(String name, Component component)
	{
		addComponent(name, component);
	}
	
	public void parseFile(File file)
	{
		String parsedFile;
		Parser parser = new Parser(new MusicParser());
		
		parsedFile = parser.open(file).parse().get();
		
		String text = extractText(file);
		
		textPlayerController = new TextPlayerController(this, parsedFile, text, file.getName());
		textPlayerController.run();
		
		//contentPane.add(textPlayerController.getView(), "TextPlayerView");
		//addView("TextPlayerView", textPlayerController.getView());
		changeView("TextPlayerView");
	}
	
	private String extractText(File file)
	{
		String text = null;
		
		try {
			List<String> lines;
			lines = Files.readAllLines(file.toPath());
			text = lines.stream().map(l->l+"\n").collect(Collectors.joining(""));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return text;
	}
	
	public void updateMenuBar()
	{	
		((JMenuItem)getComponent("mb_ctrl_play")).setEnabled(false);
		((JMenuItem)getComponent("mb_ctrl_pause")).setEnabled(false);
		((JMenuItem)getComponent("mb_ctrl_stop")).setEnabled(false);
		((JMenuItem)getComponent("mb_file_close")).setEnabled(false);
		//((JMenuItem)getComponent("mb_ctrl_play")).setEnabled(!mp.isPlaying());
		//((JMenuItem)getComponent("mb_ctrl_pause")).setEnabled(!mp.isPaused());
		//((JMenuItem)getComponent("mb_ctrl_stop")).setEnabled(!mp.isStopped());
	}
}
