package models.io.input.dialog;

import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;

public class FileInputDialog extends InputDialog 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private static final ResourceBundle RB = 
			ResourceBundle.getBundle("resources.lang.io.input.dialog.file");
	private JFrame frame;
	private Path directory;
	private String fileExtension;
	private FileInputType fileInputType;
	private File chosenFile;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	public FileInputDialog(JFrame window, Path directory, String fileExtension, FileInputType type)
	{
		frame = window;
		this.directory = directory;
		this.fileExtension = fileExtension;
		fileInputType = type;
	}
	
	public FileInputDialog(JFrame window, String fileExtension, FileInputType type)
	{
		this(window, null, fileExtension, type);
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	@Override
	public boolean ask() 
	{
		FileDialog fd;
		boolean success = false;
		
		
		fd = new FileDialog(
				frame, 
				RB.getString(fileInputType.getResourceBundleKey()), 
				fileInputType.getFileDialogType()
		);
		
		if (directory != null)
			fd.setDirectory(directory.toString());
		
		fd.setFile("*." + fileExtension);
		fd.setVisible(true);
		
		if (fd.getFile() != null) {
			chosenFile = new File(fd.getDirectory() + fd.getFile());
			success = true;
		}
		
		return success;
	}

	@Override
	public String getTitle()
	{
		if (chosenFile == null)
			throw new IllegalStateException("No file has been choosen");
		
		return chosenFile.getName();
	}

	/**
	 * {@inheritDoc}
	 * @implNote	Lazy initialization
	 */
	@Override
	public List<String> getContent()
	{
		if (chosenFile == null)
			throw new IllegalStateException("No file has been choosen");
			
		if (content == null) {
			try {
				content = Files.readAllLines(chosenFile.toPath());
			} 
			catch (IOException e) {
				content = new ArrayList<>();
			}
		}
		
		return content;
	}
	
	public File getFile()
	{
		return chosenFile;
	}
}
