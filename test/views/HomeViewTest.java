package views;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import org.junit.Test;

import controllers.HomeController;
import controllers.TextPlayerController;
import core.Controller;


@SuppressWarnings("unused")
public class HomeViewTest
{
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullControllerAndFrame() throws IOException
	{
		HomeView view = new HomeView(null, null);
		
		fail();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullController() throws IOException
	{
		JFrame window = new JFrame();
		HomeView view = new HomeView(null, window);
		
		window.dispose();
		
		fail();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullFrame() throws IOException
	{
		HomeController controller = new HomeController();
		HomeView view = new HomeView(controller, null);
		
		fail();
	}
	
	@Test
	public void testUpdate() throws IOException
	{
		JFrame window = new JFrame();
		HomeController controller = new HomeController();
		HomeView view = new HomeView(controller, window);
		JMenuItem menuButtomPlayPause;
		JMenuItem menuButtomStop;
		JMenuItem menuButtomClose;
		
		
		window.setSize(100, 100);
		
		view.update(null, null);
		
		menuButtomPlayPause = (JMenuItem) Controller.getComponent("mb_ctrl_playPause");
		menuButtomStop = (JMenuItem) Controller.getComponent("mb_ctrl_stop");
		menuButtomClose = (JMenuItem) Controller.getComponent("mb_file_close");
		
		assertFalse(menuButtomPlayPause.isEnabled());
		assertFalse(menuButtomStop.isEnabled());
		assertFalse(menuButtomClose.isEnabled());
		
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
