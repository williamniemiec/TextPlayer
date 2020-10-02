package models.input.dialog;

import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFrame;


public class FileInput 
{
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	/**
	 * Opens file dialog.
	 */
	public File getInput(JFrame window, String windowTitle)
	{
		String filepath = null;
		FileDialog fd;
		
		
		fd = new FileDialog(window, windowTitle, FileDialog.LOAD);
		fd.setDirectory("./");
		fd.setFile("*.txt");
		fd.setVisible(true);
		
		filepath = fd.getDirectory()+fd.getFile();
		
		return (filepath == null) ? null : new File(filepath);
	}
}
