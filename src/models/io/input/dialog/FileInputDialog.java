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


/**
 * Responsible for obtaining a file input through a dialog window.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public class FileInputDialog extends InputDialog 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private static final ResourceBundle lang = 
			ResourceBundle.getBundle("resources.lang.io.input.dialog.file");
	private JFrame window;
	private Path workingDirectory;
	private String fileExtension;
	private FileInputType fileInputType;
	private File chosenFile;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	/**
	 * @param		window Window that will be the parent of the window dialog
	 * @param		workingDirectory Initial directory
	 * @param		fileExtension File extension
	 * @param		type Operation type (indicates whether the file will be
	 * saved or loaded)
	 */
	public FileInputDialog(JFrame window, Path workingDirectory, String fileExtension, FileInputType type)
	{
		this.window = window;
		this.workingDirectory = workingDirectory;
		this.fileExtension = fileExtension;
		fileInputType = type;
	}
	
	/**
	 * @param		window Window that will be the parent of the window dialog
	 * @param		fileExtension File extension
	 * @param		type Operation type (indicates whether the file will be
	 * saved or loaded)
	 */
	public FileInputDialog(JFrame window, String fileExtension, FileInputType type)
	{
		this(window, null, fileExtension, type);
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	@Override
	public boolean openDialog() 
	{
		FileDialog fd;
		boolean success = false;
		
		
		fd = new FileDialog(
				window, 
				lang.getString(fileInputType.getLangKey()), 
				fileInputType.getFileDialogType()
		);
		
		if (workingDirectory != null)
			fd.setDirectory(workingDirectory.toString());
		
		fd.setFile("*." + fileExtension);
		fd.setVisible(true);
		
		if (fd.getFile() != null) {
			chosenFile = new File(fd.getDirectory() + fd.getFile());
			success = true;
		}
		
		return success;
	}

	
	//-------------------------------------------------------------------------
	//		Getters
	//-------------------------------------------------------------------------
	/**
	 * Gets filename.
	 * 
	 * @return		Filename or null if 
	 */
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
	
	/**
	 * Gets chosen file.
	 * 
	 * @return		Chosen file or null if no file has been chosen
	 */
	public File getFile()
	{
		return chosenFile;
	}
}
