package models;

import java.io.File;

public class Parser 
{
	private File file;
	private ParseType parseType;
	private String parsedText;
	
	
	public Parser(ParseType parseType)
	{
		this.parseType = parseType;
	}
	
	
	public Parser open(File file)
	{
		this.file = file;
		return this;
	}
	
	public Parser open(String filename)
	{
		this.file = new File(filename);
		return this;
	}
	
	public Parser parse()
	{
		return this;
	}
	
	public String get()
	{
		return parsedText;
	}
}
