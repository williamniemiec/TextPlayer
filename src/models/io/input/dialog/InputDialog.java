package models.io.input.dialog;

import java.util.List;


/**
 * Responsible for obtaining an input through a dialog window.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public abstract class InputDialog
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	protected String title;
	protected List<String> content;
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	/**
	 * Displays dialog input.
	 * 
	 * @return		False if the user cancels the operation; True otherwise
	 */
	public abstract boolean openDialog();
	
	
	//-------------------------------------------------------------------------
	//		Getters
	//-------------------------------------------------------------------------
	/**
	 * Gets title of the input provided in {@link #openDialog()}.
	 * 
	 * @return		Input content
	 */
	public abstract String getTitle();
	
	/**
	 * Gets content of the input provided in {@link #openDialog()}.
	 * 
	 * @return		Input content
	 */
	public abstract List<String> getContent();
}
