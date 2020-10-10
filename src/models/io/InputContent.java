package models.io;

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
		if (content == null)
			throw new IllegalArgumentException("Content cannot be null");
		
		this.name = (name == null) ? "" : name;
		this.content = content;
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	@Override
	public String toString() 
	{
		return "InputContent [name=" + name + ", content=" + content + "]";
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
