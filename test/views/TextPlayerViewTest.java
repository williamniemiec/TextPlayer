package views;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;

import controllers.TextPlayerController;
import models.music.player.JFugueMusicPlayer;
import models.parser.JFugueMusicParser;
import models.parser.Parser;


@SuppressWarnings("unused")
public class TextPlayerViewTest
{
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullControllerAndFrame() throws IOException
	{
		TextPlayerView view = new TextPlayerView(null, null);
		
		fail();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullController() throws IOException
	{
		JFrame window = new JFrame();
		TextPlayerView view = new TextPlayerView(null, window);
		
		window.dispose();
		
		fail();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullFrame() throws IOException
	{
		TextPlayerController controller = new TextPlayerController(new ArrayList<>(), new ArrayList<>());
		TextPlayerView view = new TextPlayerView(controller, null);
		
		fail();
	}
	
	@Test
	public void testUpdate() throws IOException
	{
		JFrame window = new JFrame();
		List<String> originalText = new ArrayList<>();
		List<String> processedText;
		JFugueMusicPlayer player;
		TextPlayerController controller;
		TextPlayerView view;
		Parser parser = new Parser(new JFugueMusicParser());
		
		
		window.setSize(100, 100);
		
		originalText.add("Suspendisse volutpat arcu eros, ac commodo est ");
		originalText.add("aliquet vel. Cras scelerisque vitae lacus eu sagittis.");
		
		processedText = parser.parse(originalText);
		
		player = new JFugueMusicPlayer(processedText);
		player.play().pause();
		
		controller = new TextPlayerController(new ArrayList<>(), originalText);
		view = new TextPlayerView(controller, window);
		
		view.update(player, null);
		
		player.stop();
		
		assertEquals(91, player.getMusicLength());
		
		window.dispose();
	}
	
	@Test
	public void testUpdateContent() throws IOException
	{
		JFrame window = new JFrame();
		List<String> originalText = new ArrayList<>();
		TextPlayerController controller;
		TextPlayerView view;
		String phrase = "Suspendisse volutpat arcu eros, ac commodo est";
		
		
		window.setSize(100, 100);
		
		originalText.add(phrase);
		
		controller = new TextPlayerController(new ArrayList<>(), originalText);
		view = new TextPlayerView(controller, window);
		
		view.updateContent();
		
		assertEquals(phrase, view.getTextAreaContent().replaceAll("\\n", ""));
		
		window.dispose();
	}
}
