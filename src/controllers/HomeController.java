package controllers;

import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import core.Controller;
import models.input.TextInput;
import models.parse.JFugueMusicParser;
import models.parse.Parser;
import util.FileUtil;
import views.HomeView;
import views.TextPlayerView;


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
		
		//
//		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//		manager.addKeyEventDispatcher(new KeyEventDispatcher () {
//			@Override
//	        public boolean dispatchKeyEvent(KeyEvent e) {
//				System.out.println("Key event");
//				return false;
//	        }
//		});
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
	 * Processes a file and send it to {@link TextPlayerController}. After, it
	 * will be opened {@link TextPlayerView}.
	 * 
	 * @param		file File to be processed
	 */
	public void parseFile(File file)
	{
		String parsedFile, text;
		Parser parser = new Parser(new JFugueMusicParser());
		

		parsedFile = parser.open(file).parse().get();
		
		text = FileUtil.extractText(file);
		
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
	
	public String getTextEntry(JFrame window, String windowTitle, String clearButtonTitle, String actionButtonTitle)
	{
		TextInput ti = new TextInput();
		return ti.getInput(window, windowTitle, clearButtonTitle, actionButtonTitle);
	}
}
