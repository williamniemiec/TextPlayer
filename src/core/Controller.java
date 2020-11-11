package core;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Responsible for the communication between views and models in addiction to
 * being responsible for the behavior of the program.
 */
public abstract class Controller 
{	
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	/**
	 * Main window of the program.
	 */
	protected static final JFrame mainFrame = new JFrame();
	
	/**
	 * Stores all views that can be loaded in the main window.
	 */
	private static final JPanel viewsViewer = new JPanel(new CardLayout());
		
	/**
	 * Stores main frame's components whose behavior can change during execution.
	 * Will usually be {@link JMenuBar}.
	 */
	private static final Map<String,Component> mainFrameComponents = new HashMap<>();
	
	
	//-------------------------------------------------------------------------
	//		Initialization blocks
	//-------------------------------------------------------------------------
	/**
	 * Puts views viewer in the main window.
	 */
	static {
		mainFrame.add(viewsViewer);
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	/**
	 * Executes controller and associated view with it.
	 */
	public abstract void run();
	
	/**
	 * Adds a view in main frame.
	 * 
	 * @param		viewName Name of the view
	 * @param		view View to be added
	 * 
	 * @throws		IllegalArgumentException If viewName is null or empty, if
	 * view is null or if view does not extends a {@link Component}
	 */
	public static final void addView(String viewName, View view)
	{
		if (viewName == null || viewName.isBlank())
			throw new IllegalArgumentException("View name cannot be null or empty");
		
		if (view == null)
			throw new IllegalArgumentException("View cannot be null");
		
		if (!(view instanceof Component)) {
			throw new IllegalArgumentException("View must extend a component");
		}
		
		viewsViewer.add((Component)view, viewName);
	}
	
	/**
	 * Loads a view in main frame.
	 * 
	 * @param		viewName Name of the view
	 * 
	 * @throws		IllegalArgumentException If viewName is null or empty
	 */
	public static final void loadView(String viewName)
	{
		if (viewName == null || viewName.isBlank())
			throw new IllegalArgumentException("View name cannot be null or empty");
		
		CardLayout cl = (CardLayout)viewsViewer.getLayout();
		cl.show(viewsViewer, viewName);
	}

	/**
	 * Adds a new component in the list of main frame's components.
	 * 
	 * @param		name Name of the component
	 * @param		component Component to be added
	 * 
	 * @throws		IllegalArgumentException If name is null or empty or if 
	 * component is null
	 */
	public static final void addComponent(String name, Component component)
	{
		if (name == null || name.isBlank())
			throw new IllegalArgumentException("Name cannot be null or empty");
		
		if (component == null)
			throw new IllegalArgumentException("Component cannot be null");
		
		mainFrameComponents.put(name, component);
	}
	
	/**
	 * Removes a component from the list of main frame's components.
	 * 
	 * @param		name Name of the component
	 * 
	 * @throws		IllegalArgumentException If name is null or empty
	 */
	public static final void removeComponent(String name)
	{
		if (name == null || name.isBlank())
			throw new IllegalArgumentException("Name cannot be null or empty");
		
		mainFrameComponents.remove(name);
	}
	
	/**
	 * Returns an added component in the list of main frame's components
	 * 
	 * @param		name Name of the component
	 * 
	 * @return		Component with the provided name or null if it is not in 
	 * the list
	 * 
	 * @throws		IllegalArgumentException If name is null or empty
	 */
	public static final Component getComponent(String name)
	{
		if (name == null || name.isBlank())
			throw new IllegalArgumentException("Name cannot be null or empty");
		
		return mainFrameComponents.get(name);
	}
	
	/**
	 * Sets main frame width and height.
	 * 
	 * @param		w Width
	 * @param		h Height
	 * 
	 * @throws		IllegalArgumentException If width or height is less than or
	 * equal to zero
	 */
	public static final void setSize(int w, int h)
	{
		if (w <= 0)
			throw new IllegalArgumentException("Width cannot be less than or equal to zero");
		
		if (h <= 0)
			throw new IllegalArgumentException("Height cannot be less than or equal to zero");
		
		mainFrame.setSize(w, h);
	}
}
