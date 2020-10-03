package models.input.dialog;

import java.util.List;

public class InputContent 
{
	private String name;
	private List<String> content;
	
	public InputContent(String name, List<String> content)
	{
		this.name = name;
		this.content = content;
	}
	
	public String getName()
	{
		return name;
	}
	
	public List<String> getContent()
	{
		return content;
	}
}
