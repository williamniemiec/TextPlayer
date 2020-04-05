package models;

import java.util.ArrayList;
import java.util.List;

import views.View;

public class MusicPlayer implements Model {
	private List<View> views = new ArrayList<>();
	
	// attach  view
	public void attach(View view) {
		views.add(view);
	}
	public void detach(View view) {
		views.remove(view);
	}
	public void notifyViews() {
		for (View view : views) {
			view.update(this, null);
		}
	}
	
	private String musicalNotes;
	
	public MusicPlayer(String musicalNotes)
	{
		this.musicalNotes = musicalNotes;
	}
	
	public MusicPlayer play()
	{
		return this;
	}
	
	public MusicPlayer pause()
	{
		return this;
	}
	
	public MusicPlayer stop()
	{
		return this;
	}
	
	/*
	public MusicPlayer change(String musicalNotes)
	{
		
	}
	*/
}
