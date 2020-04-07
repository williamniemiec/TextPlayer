package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import views.View;

public class Parser implements Model
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	private File file;
	private ParseType parseType;
	private String parsedText;
	private List<View> views = new ArrayList<>();
	
	
	//-----------------------------------------------------------------------
	//		Constructor
	//-----------------------------------------------------------------------
	public Parser(ParseType parseType)
	{
		this.parseType = parseType;
	}
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void attach(View view) 
	{
		views.add(view);
	}
	
	@Override
	public void detach(View view) 
	{
		views.remove(view);
	}
	
	@Override
	public void notifyViews() 
	{
		for (View view : views) {
			view.update(this, null);
		}
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
		
		
		
		notifyViews();
		return this;
	}
	
	public String get()
	{
		return parsedText;
	}
}
