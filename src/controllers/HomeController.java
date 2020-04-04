package controllers;

import java.awt.CardLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;

import models.MusicParser;
import models.Parser;
import views.HomeView;


/**
 * Main controller. It will be responsible for program's main screen behavior.
 */
public class HomeController implements Controller 
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	@SuppressWarnings("unused")
	private HomeView homeView;
	private TextPlayerController textPlayerController;
	private JPanel contentPane;
	private JFrame frame;
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void run()
	{
		frame = new JFrame();
		
		// Init content pane
		contentPane = new JPanel(new CardLayout());
		homeView = new HomeView(this, contentPane, frame);
		contentPane.add(homeView, "HomeView");
		
		frame.setContentPane(contentPane);
		frame.setVisible(true);
		
	}

	public void changeView(String viewName)
	{
		CardLayout cl = (CardLayout)contentPane.getLayout();
		cl.show(contentPane, viewName);
	}
	
	public void parseFile(File file)
	{
		String parsedFile;
		Parser parser = new Parser(new MusicParser());
		
		parsedFile = parser.open(file).parse().get();
		
		String text = extractText(file);
		
		textPlayerController = new TextPlayerController(this, parsedFile, text, file.getName());
		textPlayerController.run();
		// Dará problema - e se ja tiver adicionado?!
		contentPane.add(textPlayerController.getView(), "TextPlayerView");
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
}
