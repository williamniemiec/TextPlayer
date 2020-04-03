package controllers;

import java.io.File;
import models.MusicParser;
import models.Parser;
import views.HomeView;


/**
 * Main controller. It will be responsible for program's main screen behavior.
 */
public class HomeController implements Controller 
{
	@SuppressWarnings("unused")
	private HomeView homeView;
	
	
	@Override
	public void run()
	{
		homeView = new HomeView(this);
	}
	
	
	public void parseFile(File file)
	{
		String parsedFile;
		Parser parser = new Parser(new MusicParser());
		
		parsedFile = parser.open(file).parse().get();
		
		MusicController musicController = new MusicController(parsedFile);
		musicController.run();
	}
}
