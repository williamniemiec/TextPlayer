package controllers;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JMenuItem;

import core.Controller;
import models.IOManager;
import models.parse.JFugueMusicParser;
import models.parse.Parser;
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
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void run()
	{
		homeView = new HomeView(this, mainFrame);
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
		Parser parser = new Parser(new JFugueMusicParser());
		
		// SE DER ALGUM ERRO, TRATAR AQUI
		parsedFile = parser.open(file).parse().get();
		
		String text = IOManager.extractText(file);
		
		textPlayerController = new TextPlayerController(parsedFile, text, file.getName());
		textPlayerController.run();
	}
	
	public void updateMenuBar()
	{	
		((JMenuItem)getComponent("mb_ctrl_play")).setEnabled(false);
		((JMenuItem)getComponent("mb_ctrl_pause")).setEnabled(false);
		((JMenuItem)getComponent("mb_ctrl_stop")).setEnabled(false);
		((JMenuItem)getComponent("mb_file_close")).setEnabled(false);
	}
}
