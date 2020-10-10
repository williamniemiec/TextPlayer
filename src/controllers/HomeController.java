package controllers;

import java.awt.Component;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import core.Controller;
import models.io.IOType;
import models.io.InputContent;
import models.io.dialog.IOManager;
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
		try {
			// Initializes HomeView
			homeView = new HomeView(this, mainFrame, RB);
			addView("HomeView", homeView);
			
			KeyboardController keyboardController = new KeyboardController();
			keyboardController.run();
			
			// Sets program windows as visible
			mainFrame.setVisible(true);		
		} 
		catch (IOException e) {
			mainFrame.setVisible(true);
			onException(e);
			System.exit(-1);
		}
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
		if (inputContent == null)
			throw new IllegalArgumentException("Content cannot be null");
		
		Parser parser = new Parser(new JFugueMusicParser());
		List<String> parsedContent = parser.parse(inputContent.getContent());
		
		
		textPlayerController = new TextPlayerController(
				parsedContent, 
				inputContent.getContent(), 
				inputContent.getName()
		);
		textPlayerController.run();
	}
	
	public InputContent getContent(IOType inputDialogType)
	{
		if (inputDialogType == null)
			throw new IllegalArgumentException("Input dialog type cannot be null");
		
		InputContent content = null;
		
		
		KeyboardController.disable();
		
		try {
			content = IOManager.getContent(mainFrame, inputDialogType);
		}
		catch (IOException e) {
			onException(e);
		}
		
		KeyboardController.enable();
		
		return content;
	}
	
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
}
