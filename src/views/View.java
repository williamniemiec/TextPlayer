package views;

import models.Model;

/**
 * Responsible for the program interface.
 */
public interface View 
{	
	public void update(Model model, Object data);
	//-----------------------------------------------------------------------
	//		Constants
	//-----------------------------------------------------------------------
	/*
	final static int FRAME_WIDTH = 600;
	final static int FRAME_HEIGHT = 400;
	final static int FRAME_X = 100;
	final static int FRAME_Y = 100;
	*/
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	/**
	 * Closes the interface. It is called when the window is closed.
	 */
	public void close();
}
