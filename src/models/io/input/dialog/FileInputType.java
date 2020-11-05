package models.io.input.dialog;

import java.awt.FileDialog;


/**
 * Contains file input types available.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public enum FileInputType
{
	//-------------------------------------------------------------------------
	//		Enumerations
	//-------------------------------------------------------------------------
	LOAD("FILE_LOAD_DIALOG_TITLE", FileDialog.LOAD),
	STORE("FILE_STORE_DIALOG_TITLE", FileDialog.SAVE);

	
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private int fileDialogType;
	private String langKey;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	/**
	 * @param		langKey Resource bundle key
	 * @param		fileDialogType {@link FileDialog} type
	 */
	private FileInputType(String langKey, int fileDialogType)
	{
		this.fileDialogType = fileDialogType;
		this.langKey = langKey;
	}
	
	
	//-------------------------------------------------------------------------
	//		Getters
	//-------------------------------------------------------------------------
	/**
	 * Gets {@link FileDialog} type.
	 * 
	 * @return		FileDialog type
	 */
	public int getFileDialogType()
	{
		return fileDialogType;
	}

	/**
	 * Gets resource bundle key.
	 * 
	 * @return		Resource bundle key
	 */
	public String getLangKey()
	{
		return langKey;
	}
}
