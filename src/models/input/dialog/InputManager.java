package models.input.dialog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;


public class InputManager 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private static final ResourceBundle RB_INPUT = 
			ResourceBundle.getBundle("resources.lang.input.input");
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	public static InputContent getContent(JFrame window, InputDialogType inputDialogType) throws IOException
	{
		List<String> content = null;
		String filename = "N/A";
		
		
		if (inputDialogType == InputDialogType.FILE) {
			FileInput fi = new FileInput("txt");
			File file;
			
			
			file = fi.getInput(window, RB_INPUT.getString("FILE_CHOOSE_DIALOG_TITLE"));
			
			if (file != null) {
				content = Files.readAllLines(file.toPath());
				filename = file.getName();
			}
		}
		else if (inputDialogType == InputDialogType.TEXT) {
			TextInput ti = new TextInput();
			
			
			content = ti.getInput(window, RB_INPUT.getString("TEXT_ENTRY"), RB_INPUT.getString("CLEAR"), RB_INPUT.getString("PROCESS"));
		}
		else {
			throw new IllegalArgumentException("Unsupported type");
		}
		
		return new InputContent(filename, content);
	}
}
