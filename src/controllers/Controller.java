package controllers;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import views.View;

/**
 * Responsible for the communication between views and models in addiction to
 * being responsible for the behavior of the program.
 */
public abstract class Controller 
{	
	protected static final JFrame mainFrame = new JFrame();
	private static final JPanel contentPane = new JPanel(new CardLayout());
	
	{
		mainFrame.add(contentPane);
	}
	
	/**
	 * Stores main frame's components whose behavior can change during execution.
	 */
	private static final Map<String,Component> mainFrameComponents = new HashMap<>();
	
	
	/**
	 * Executes controller and associated view with it.
	 */
	public abstract void run();
	
	
	public static final void addView(String viewName, View view)
	{
		contentPane.add((Component)view, viewName);
	}
	
	public static final void changeView(String viewName)
	{
		CardLayout cl = (CardLayout)contentPane.getLayout();
		cl.show(contentPane, viewName);
	}
	
	
	public static final void addComponent(String name, Component component)
	{
		mainFrameComponents.put(name, component);
	}
	
	public static final void removeComponent(String name)
	{
		mainFrameComponents.remove(name);
	}
	
	public static final Component getComponent(String name)
	{
		return mainFrameComponents.get(name);
	}
}
