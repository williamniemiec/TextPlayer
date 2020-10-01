package controllers;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import core.Controller;
import models.input.dialog.FileInput;
import models.input.dialog.InputDialogType;
import models.input.dialog.TextInput;
import models.parse.JFugueMusicParser;
import models.parse.Parser;
import util.FileUtil;
import util.Pair;
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
	private static final ResourceBundle RB = ResourceBundle.getBundle("resources.lang.home.home");
	
	
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
	 * Processes a file and send it to {@link TextPlayerController}. After, it
	 * will be opened {@link TextPlayerView}.
	 * 
	 * @param		file File to be processed
	 */
//	public void parseFile(File file)
//	{
//		String parsedFile, text;
//		Parser parser = new Parser(new JFugueMusicParser());
//		
//
//		parsedFile = parser.open(file).parse().get();
//		
//		text = FileUtil.extractText(file);
//		
//		textPlayerController = new TextPlayerController(parsedFile, text, file.getName());
//		textPlayerController.run();
//	}
	
	/**
	 * Updates top bar buttons.
	 */
	public void updateMenuBar()
	{	
		((JMenuItem)getComponent("mb_ctrl_playPause")).setEnabled(false);
		((JMenuItem)getComponent("mb_ctrl_stop")).setEnabled(false);
		((JMenuItem)getComponent("mb_file_close")).setEnabled(false);
	}
	
	public void openPlayer(Pair<String, List<String>> inputContent)
	{
		//Pair<String, String> inputContent = getContent(InputDialogType);
		List<String> parsedContent = parseContent(inputContent.second);
		
		
		textPlayerController = new TextPlayerController(parsedContent, inputContent.second, inputContent.first);
		textPlayerController.run();
	}
	
	public Pair<String, List<String>> getContent(InputDialogType inputDialogType) throws IOException
	{
		List<String> content;
		String filename = "N/A";
		
		
		if (inputDialogType == InputDialogType.FILE) {
			FileInput fi = new FileInput();
			File file;
			
			
			file = fi.getInput(mainFrame, RB.getString("FILE_CHOOSE_DIALOG_TITLE"));
			content = FileUtil.extractText(file);
			filename = file.getName();
		}
		else if (inputDialogType == InputDialogType.TEXT) {
			TextInput ti = new TextInput();
			
			
			content = ti.getInput(mainFrame, RB.getString("TEXT_ENTRY"), RB.getString("CLEAR"), RB.getString("PROCESS"));
		}
		else {
			throw new IllegalArgumentException("Unsupported type");
		}
		
		return Pair.of(filename, content);
	}
	
	private List<String> parseContent(List<String> content)
	{
		Parser parser = new Parser(new JFugueMusicParser());
		

		return parser.parse(content).get();
	}
	
//	public void getTextEntry(JFrame window, String windowTitle, String clearButtonTitle, String actionButtonTitle)
//	{
//		TextInput ti = new TextInput();
//		ti.getInput(window, windowTitle, clearButtonTitle, actionButtonTitle);
//	}
//	
//	public void getFileInput(JFrame window, String windowTitle)
//	{
//		FileInput fi = new FileInput();
//		fi.getInput(window, windowTitle);
//	}
}
