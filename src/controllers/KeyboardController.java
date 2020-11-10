package controllers;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

import core.Controller;


/**
 * Responsible for keyboard management.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public class KeyboardController extends Controller
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private static KeyboardFocusManager manager;
	private static KeyboardHandler keyboardHandler;
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	@Override
	public void run() 
	{
		enable();
	}
	
	/**
	 * Enables keyboard listener.
	 */
	public static void enable()
	{
		if (keyboardHandler == null)
			keyboardHandler = new KeyboardHandler();
		
		manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(keyboardHandler);
	}
	
	/**
	 * Disables keyboard listener.
	 */
	public static void disable()
	{
		manager.removeKeyEventDispatcher(keyboardHandler);
	}
	
	
	//-------------------------------------------------------------------------
	//		Inner classes
	//-------------------------------------------------------------------------
	private static class KeyboardHandler implements KeyEventDispatcher
	{
		/**
		 * {@inheritDoc}
		 * @see			KeyEventDispatcher#dispatchKeyEvent(KeyEvent)
		 */
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) 
		{
			if (e.getID() == KeyEvent.KEY_RELEASED) {
				JMenuItem component;
				
				
				if (e.isControlDown()) {
					switch (e.getKeyCode()) {
						case KeyEvent.VK_N:
							component = (JMenuItem) getComponent("mb_file_textEntry");
							component.doClick();
							break;
						case KeyEvent.VK_O:
							component = (JMenuItem) getComponent("mb_file_openFile");
							component.doClick();
							break;
						case KeyEvent.VK_W:
							component = (JMenuItem) getComponent("mb_file_close");
							component.doClick();
							break;
					}
				}
				else {
					switch (e.getKeyCode()) {
						case KeyEvent.VK_SPACE:
							component = (JMenuItem) getComponent("mb_ctrl_playPause");
							component.doClick();
							break;
						case KeyEvent.VK_H:
							component = (JMenuItem) getComponent("mb_ctrl_stop");
							component.doClick();
							break;
					}
				}
			}
			
			return false;
		}
	}
}
