package models.input.dialog;

import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFrame;


public class FileInput 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private String directory;
	private String fileExtension;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	public FileInput(String directory, String fileExtension)
	{
		this.directory = directory;
		this.fileExtension = fileExtension;
	}
	
	public FileInput(String fileExtension)
	{
		this("./", fileExtension);
	}
	
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
		fd.setDirectory(directory);
		fd.setFile("*." + fileExtension);
		fd.setVisible(true);
		
		filepath = fd.getDirectory() + fd.getFile();
		
		return (filepath == null) ? null : new File(filepath);
	}
}
