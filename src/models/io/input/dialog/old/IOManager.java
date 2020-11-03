package models.io.input.dialog.old;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;


public class IOManager 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private static final ResourceBundle RB_IO = 
			ResourceBundle.getBundle("resources.lang.io.io");
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	public static File getFile(JFrame window)
	{
		FileIO fi = new FileIO("txt");
		
		
		return fi.getInput(window, RB_IO.getString("FILE_LOAD_DIALOG_TITLE"));
	}
	
	public static List<String> getText(JFrame window)
	{
		TextInput ti = new TextInput();
		
		
		return ti.getInput(
				window, 
				RB_IO.getString("TEXT_ENTRY"), 
				RB_IO.getString("CLEAR"), 
				RB_IO.getString("PROCESS")
		);
	}
	
//	public static File getOutput(JFrame window, String extension)
	{
		if (window == null)
			throw new IllegalArgumentException("Window cannot be null");
		
		if ((extension == null) || extension.isBlank())
			throw new IllegalArgumentException("Extension cannot be empty");
		
		FileIO fi = new FileIO(extension);
		
		
		return fi.getOutput(window, RB_IO.getString("FILE_STORE_DIALOG_TITLE"));
	}
}
