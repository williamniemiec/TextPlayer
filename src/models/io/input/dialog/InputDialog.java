package models.io.input.dialog;

import java.util.List;

public abstract class InputDialog
{
	protected boolean wasAsked;
	protected String title;
	protected List<String> content;
	
	public abstract boolean ask();
	public abstract String getTitle();
	public abstract List<String> getContent();
}
