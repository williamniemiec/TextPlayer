package models.io.dialog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import models.io.IOType;
import models.io.InputContent;


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
	public static InputContent getContent(JFrame window, IOType ioType) throws IOException
	{
		if (window == null)
			throw new IllegalArgumentException("Window cannot be null");
		
		if (ioType == null)
			throw new IllegalArgumentException("ioType cannot be null");
		
		List<String> content = null;
		String filename = "N/A";
		
		
		switch (ioType) {
			case FILE_LOAD:
				FileIO fi = new FileIO("txt");
				File file;
				
				
				file = fi.getInput(window, RB_IO.getString("FILE_LOAD_DIALOG_TITLE"));
				
				if (file != null) {
					try {
						content = Files.readAllLines(file.toPath());
						filename = file.getName();
					}
					catch (IOException e) {
						throw new IOException("File not found");
					}
				}
				
				break;
			case TEXT:
				TextInput ti = new TextInput();
				
				
				content = ti.getInput(
						window, 
						RB_IO.getString("TEXT_ENTRY"), 
						RB_IO.getString("CLEAR"), 
						RB_IO.getString("PROCESS")
				);
				
				break;
			default:
				throw new IllegalArgumentException("Unsupported type");
		}
		
		return new InputContent(filename, content);
	}
	
	public static File getOutput(JFrame window, String extension)
	{
		if (window == null)
			throw new IllegalArgumentException("Window cannot be null");
		
		if ((extension == null) || extension.isBlank())
			throw new IllegalArgumentException("Extension cannot be empty");
		
		FileIO fi = new FileIO(extension);
		
		
		return fi.getOutput(window, RB_IO.getString("FILE_STORE_DIALOG_TITLE"));
	}
}
