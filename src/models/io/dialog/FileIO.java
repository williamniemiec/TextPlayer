package models.io.dialog;

import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFrame;

import models.io.IOType;


public class FileIO 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private String directory;
	private String fileExtension;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	public FileIO(String directory, String fileExtension)
	{
		this.directory = directory;
		this.fileExtension = fileExtension;
	}
	
	public FileIO(String fileExtension)
	{
		this("./", fileExtension);
	}
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	/**
	 * Choose a file to open.
	 */
	public File getInput(JFrame window, String windowTitle)
	{
		return chooseFile(window, windowTitle, IOType.FILE_LOAD);
	}
	
	/**
	 * Choose a file to store.
	 */
	public File getOutput(JFrame window, String windowTitle)
	{
		return chooseFile(window, windowTitle, IOType.FILE_STORE);
	}
	
	private File chooseFile(JFrame window, String windowTitle, IOType ioType)
	{
		FileDialog fd;
		File chosenFile = null;
		
		
		fd = new FileDialog(window, windowTitle, getFileDialogType(ioType));
		fd.setDirectory(directory);
		fd.setFile("*." + fileExtension);
		fd.setVisible(true);
		
		if (fd.getFile() != null) {
			chosenFile = new File(fd.getDirectory() + fd.getFile());
		}
		
		return chosenFile;
	}
	
	@SuppressWarnings("incomplete-switch")
	private int getFileDialogType(IOType ioType)
	{
		int fileDialogType = -1;
		
		
		switch (ioType) {
			case FILE_LOAD:
				fileDialogType = FileDialog.LOAD;
				break;
			case FILE_STORE:
				fileDialogType = FileDialog.SAVE;
				break;
		}
		
		return fileDialogType;
	}
}
