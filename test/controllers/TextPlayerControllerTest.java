package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import org.junit.Test;

import core.Controller;
import models.music.player.JFugueMusicPlayer;
import models.parser.JFugueMusicParser;
import models.parser.Parser;
import util.FileUtil;
import views.HomeView;
import views.TextPlayerView;


@SuppressWarnings("unused")
public class TextPlayerControllerTest
{
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullMusicalTextAndOriginalText()
	{
		TextPlayerController controller = new TextPlayerController(null, null);
		
		fail();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullMusicalTextA()
	{
		TextPlayerController controller = new TextPlayerController(null, new ArrayList<>());
		
		fail();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullOriginalText()
	{
		TextPlayerController controller = new TextPlayerController(new ArrayList<>(), null);
		
		fail();
	}
	
	@Test
	public void testConstructorNullFilename()
	{
		TextPlayerController controller = new TextPlayerController(new ArrayList<>(), new ArrayList<>(), null);
		
		assertEquals("N/A", controller.getFilename());
	}
	
	@Test
	public void testFilename()
	{
		String filename = "foo";
		TextPlayerController controller = new TextPlayerController(new ArrayList<>(), new ArrayList<>(), filename);
		
		assertEquals(filename, controller.getFilename());
	}
	
	@Test
	public void testGetOriginalText()
	{
		List<String> text = new ArrayList<>();
		TextPlayerController controller = new TextPlayerController(new ArrayList<>(), text, null);
		
		text.add("Test");
		
		assertEquals(text, controller.getText());
	}
	
	@Test
	public void testPlaySong() throws IOException
	{
		JFrame window = new JFrame();
		List<String> originalText;
		List<String> processedText;
		HomeView homeView = new HomeView(new HomeController(), window);
		TextPlayerController controller;
		Parser parser = new Parser(new JFugueMusicParser());
		JMenuItem menuButtomPlayPause = (JMenuItem) Controller.getComponent("mb_ctrl_playPause");
		JMenuItem menuButtomStop = (JMenuItem) Controller.getComponent("mb_ctrl_stop");
		JMenuItem menuButtomClose = (JMenuItem) Controller.getComponent("mb_file_close");
		
		
		Controller.setSize(100, 100);
		
		originalText = FileUtil.getLines(
				Path.of("test/parser/jfugue/files/source/text_big.txt"), 
				StandardCharsets.UTF_8
		);
		
		processedText = parser.parse(originalText);
				
		controller = new TextPlayerController(processedText, originalText);
		
		controller.run();
		controller.play();
		
		assertTrue(menuButtomPlayPause.isEnabled());
		assertTrue(menuButtomStop.isEnabled());
		assertTrue(menuButtomClose.isEnabled());
		
		controller.pause();
		
		assertTrue(menuButtomPlayPause.isEnabled());
		assertTrue(menuButtomStop.isEnabled());
		assertTrue(menuButtomClose.isEnabled());
		
		controller.stop();
		
		assertTrue(menuButtomPlayPause.isEnabled());
		assertFalse(menuButtomStop.isEnabled());
		assertTrue(menuButtomClose.isEnabled());
		
		window.dispose();
	}
}
