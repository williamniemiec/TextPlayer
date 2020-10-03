package controllers;

import java.awt.Component;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;

import core.Controller;
import models.input.dialog.InputContent;
import models.input.dialog.InputDialogType;
import models.input.dialog.InputManager;
import models.parse.JFugueMusicParser;
import models.parse.Parser;
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
	private static final ResourceBundle RB = 
			ResourceBundle.getBundle("resources.lang.home.home");
	private HomeView homeView;
	private TextPlayerController textPlayerController;
	
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	@Override
	public void run()
	{
		// Initializes HomeView
		homeView = new HomeView(this, mainFrame, RB);
		addView("HomeView", homeView);
		
		KeyboardController keyboardController = new KeyboardController();
		keyboardController.run();
		
		// Sets program windows as visible
		mainFrame.setVisible(true);		
	}

	/**
	 * Adds a {@link Component component} on the main frame.
	 * 
	 * @param		name Component name (it is recommended to use the same name
	 * as the variable)
	 * @param		component Component to be added
	 * 
	 * @throws		IllegalArgumentException If name is null or empty or if 
	 * component is null
	 */
	public void addMainFrameComponent(String name, Component component)
	{
		if (name == null || name.isBlank())
			throw new IllegalArgumentException("Name cannot be null or empty");
		
		if (component == null)
			throw new IllegalArgumentException("Component cannot be null");
			
		addComponent(name, component);
	}
	
	/**
	 * Updates top bar buttons.
	 */
	public void updateMenuBar()
	{	
		((JMenuItem)getComponent("mb_ctrl_playPause")).setEnabled(false);
		((JMenuItem)getComponent("mb_ctrl_stop")).setEnabled(false);
		((JMenuItem)getComponent("mb_file_close")).setEnabled(false);
	}
	
	public void openPlayer(InputContent inputContent)
	{
		Parser parser = new Parser(new JFugueMusicParser());
		List<String> parsedContent = parser.parse(inputContent.getContent());
		
		
		textPlayerController = new TextPlayerController(
				parsedContent, 
				inputContent.getContent(), 
				inputContent.getName()
		);
		textPlayerController.run();
	}
	
	public InputContent getContent(InputDialogType inputDialogType) throws IOException
	{
		InputContent content;
		
		
		KeyboardController.disable();
		content = InputManager.getContent(mainFrame, inputDialogType);
		KeyboardController.enable();
		
		return content;
	}
}
