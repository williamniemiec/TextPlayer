package models.io.input.dialog;

import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import models.io.IOType;

public class FileInputDialog extends InputDialog 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private static final ResourceBundle RB;
	private JFrame frame;
	private Path directory;
	private String fileExtension;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	public FileInputDialog(JFrame window, Path directory, String fileExtension)
	{
		frame = window;
		this.directory = directory;
		this.fileExtension = fileExtension;
	}
	
	public FileInputDialog(JFrame window, String fileExtension)
	{
		this(window, null, fileExtension);
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	@Override
	public boolean ask() 
	{
		FileDialog fd;
		boolean error = false;
		
		
		fd = new FileDialog(frame, RB.getString("FILE_LOAD_DIALOG_TITLE"), FileDialog.LOAD);
		
		if (directory != null)
			fd.setDirectory(directory.toString());
		
		fd.setFile("*." + fileExtension);
		fd.setVisible(true);
		
		if (fd.getFile() != null) {
			File chosenFile = new File(fd.getDirectory() + fd.getFile());
					
			
			try {
				content = Files.readAllLines(chosenFile.toPath());
			} 
			catch (IOException e) {
				error = true;
			}
			
			title = chosenFile.getName();
		}
		
		return !error;
	}
}
