package controllers;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import core.Controller;
import models.io.input.dialog.IOManager;
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
	
	/**
	 * Opens player view.
	 * 
	 * @param		inputDialogType Specifies how the text to be converted to 
	 * music will be obtained
	 * 
	 * @throws		IllegalArgumentException If text is null
	 */
//	public void openPlayer(IOType inputDialogType)
//	{
//		String filename = null;
//		List<String> text = null;
//		
//		
//		switch (inputDialogType) {
//			case FILE_LOAD:
//				File file = IOManager.getFile(mainFrame);
//				
//				try {
//					text = Files.readAllLines(file.toPath());
//					filename = file.getName();
//				} 
//				catch (IOException e) {}
//				
//				break;
//			case TEXT:
//				text = IOManager.getText(mainFrame);
//				
//				break;
//			default:
//				break;
//		}
//		
//		openPlayer(text, filename);
//	}
	
	public void openPlayer(InputDialog id)
	{
		if (!id.ask())
			return;
		
		Parser parser = new Parser(new JFugueMusicParser());
		List<String> processedText;
		
		
		processedText = parser.parse(id.getContent());
		
		textPlayerController = new TextPlayerController(
				processedText, 
				id.getContent(), 
				id.getTitle()
		);
		textPlayerController.run();
	}
	
	/**
	 * Opens player view.
	 * 
	 * @param		text Text to be converted to music
	 * @param		filename Filename containing the text, or null if the text
	 * did not come from a file
	 */
//	public void openPlayer(List<String> text, String filename)
//	{
//		if ((text == null) || text.isEmpty())
//			return;
//		
//		Parser parser = new Parser(new JFugueMusicParser());
//		List<String> processedText = parser.parse(text);
//		
//		
//		textPlayerController = new TextPlayerController(
//				processedText, 
//				text, 
//				filename
//		);
//		textPlayerController.run();
//	}
	
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
