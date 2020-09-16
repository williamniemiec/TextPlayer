package controllers;

import java.awt.Component;
import java.io.File;
import javax.swing.JMenuItem;
import core.Controller;
import models.parse.JFugueMusicParser;
import models.parse.Parser;
import util.FileUtil;
import views.HomeView;


/**
 * Main controller. It will be responsible for {@link HomeView} behavior.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public class HomeController extends Controller 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private HomeView homeView;
	private TextPlayerController textPlayerController;
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	@Override
	public void run()
	{
		// Initializes HomeView
		homeView = new HomeView(this, mainFrame);
		addView("HomeView", homeView);
		
		// Sets program windows as visible
		mainFrame.setVisible(true);
	}

	/**
	 * Adds a {@link Component component} on the main frame.
	 * 
	 * @param		name Component name (it is recommended to use the same name
	 * as the variable)
	 * @param		component Component to be added
	 */
	public void addMainFrameComponent(String name, Component component)
	{
		addComponent(name, component);
	}
	
	/**
	 * Processes a file and send it to {@link TextPlayerController}. After, it
	 * will be opened {@link TextPlayerView}.
	 * 
	 * @param		file File to be processed
	 */
	public void parseFile(File file)
	{
		String parsedFile;
		Parser parser = new Parser(new JFugueMusicParser());
		
		
		// SE DER ALGUM ERRO, TRATAR AQUI
		parsedFile = parser.open(file).parse().get();
		
		String text = FileUtil.extractText(file);
		
		textPlayerController = new TextPlayerController(parsedFile, text, file.getName());
		textPlayerController.run();
	}
	
	/**
	 * Updates top bar buttons.
	 */
	public void updateMenuBar()
	{	
		((JMenuItem)getComponent("mb_ctrl_play")).setEnabled(false);
		((JMenuItem)getComponent("mb_ctrl_pause")).setEnabled(false);
		((JMenuItem)getComponent("mb_ctrl_stop")).setEnabled(false);
		((JMenuItem)getComponent("mb_file_close")).setEnabled(false);
	}
}
