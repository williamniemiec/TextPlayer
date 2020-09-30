package controllers;

import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

import core.Controller;

public class KeyboardController extends Controller
{
	private static KeyboardFocusManager manager;
	private static KeyboardHandler keyboardHandler;
	
	@Override
	public void run() 
	{
		enable();
	}
	
	public static void enable()
	{
		if (keyboardHandler == null)
			keyboardHandler = new KeyboardHandler();
		manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(keyboardHandler);
	}
	
	public static void disable()
	{
		manager.removeKeyEventDispatcher(keyboardHandler);
	}
	
	
	private static class KeyboardHandler implements KeyEventDispatcher
	{
		/**
		 * {@inheritDoc}
		 * @see		KeyEventDispatcher#dispatchKeyEvent(KeyEvent)
		 */
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) 
		{
			if (e.getID() == KeyEvent.KEY_RELEASED) {
				if (e.isControlDown()) {
					switch (e.getKeyCode()) {
						case KeyEvent.VK_N:
							System.out.println("n");
							break;
						case KeyEvent.VK_O:
							System.out.println("o");
							JMenuItem c = (JMenuItem)getComponent("mb_file_open");System.out.println(c);
							c.doClick();
							break;
						case KeyEvent.VK_W:
							System.out.println("w");
							break;
					}
				}
				else {
					switch (e.getKeyCode()) {
						case KeyEvent.VK_SPACE:
							System.out.println("play/pause");
							JMenuItem c = (JMenuItem)getComponent("mb_ctrl_play");
							c.doClick();
							break;
						case KeyEvent.VK_H:
							System.out.println("stop");
							break;
					}
				}
			}
			
			return false;
		}
	}
}
