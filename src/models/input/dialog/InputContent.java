package models.input.dialog;

import java.util.List;

public class InputContent 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private String name;
	private List<String> content;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	public InputContent(String name, List<String> content)
	{
		this.name = name;
		this.content = content;
	}
	
	
	//-------------------------------------------------------------------------
	//		Getters
	//-------------------------------------------------------------------------
	public String getName()
	{
		return name;
	}
	
	public List<String> getContent()
	{
		return content;
	}
}
