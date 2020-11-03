package models.io.input.dialog;

import java.awt.FileDialog;

public enum FileInputType
{
	LOAD("FILE_LOAD_DIALOG_TITLE", FileDialog.LOAD),
	STORE("FILE_STORE_DIALOG_TITLE", FileDialog.SAVE);

	private int fileDialogType;
	private String rbKey;
	
	private FileInputType(String rbKey, int fileDialogType)
	{
		this.fileDialogType = fileDialogType;
		this.rbKey = rbKey;
	}
	
	public int getFileDialogType()
	{
		return fileDialogType;
	}

	public String getResourceBundleKey()
	{
		return rbKey;
	}
}
