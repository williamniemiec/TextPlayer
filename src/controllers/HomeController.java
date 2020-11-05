package controllers;

import java.awt.Component;
import java.io.IOException;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import core.Controller;
import models.io.input.dialog.InputDialog;
import models.parser.JFugueMusicParser;
import models.parser.Parser;
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
	//		Methods
	//-------------------------------------------------------------------------
	@Override
	public void run()
	{
		try {
			HomeView homeView = new HomeView(this, mainFrame);
			KeyboardController keyboardController;
			
			
			addView("HomeView", homeView);
			
			keyboardController = new KeyboardController();
			keyboardController.run();
			
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
	
	/**
	 * Opens player view.
	 * 
	 * @param		dialog Specifies how the text to be converted to 
	 * music will be obtained
	 */
	public void openPlayer(InputDialog dialog)
	{
		if (!dialog.ask())
			return;
		
		Parser parser = new Parser(new JFugueMusicParser());
		List<String> processedText;
		TextPlayerController textPlayerController;
		
		
		processedText = parser.parse(dialog.getContent());
		
		textPlayerController = new TextPlayerController(
				processedText, 
				dialog.getContent(), 
				dialog.getTitle()
		);
		textPlayerController.run();
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
}
