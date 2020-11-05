package core;

import static org.junit.Assert.*;

import java.awt.Component;

import javax.swing.JButton;

import org.junit.Test;

public class ControllerTest 
{
	@Test
	public void testComponent() 
	{
		String componentName = "foo";
		Component componentValue = new JButton();
		
		
		Controller.addComponent(componentName, componentValue);
		assertEquals(componentValue, Controller.getComponent(componentName));
		Controller.removeComponent(componentName);
	}
	
	public void testView() 
	{
		String viewName = "foo";
		View view = new TestView();
		
		
		Controller.addView(viewName, view);
		Controller.loadView(viewName);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetComponentNull() 
	{
		assertEquals(null, Controller.getComponent(null));
		fail();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddComponentNull() 
	{
		Controller.addComponent(null, null);
		fail();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveComponentNull() 
	{
		Controller.removeComponent(null);
		fail();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetComponentBlank() 
	{
		assertEquals(null, Controller.getComponent(""));
		fail();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddComponentBlank() 
	{
		Controller.addComponent("", null);
		fail();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveComponentBlank() 
	{
		Controller.removeComponent("");
		fail();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testViewNoExtendsComponent() 
	{
		String viewName = "foo";
		View view = new View() {
			@Override
			public void update(Model model, Object data)
			{
				
			}
		};
		
		
		Controller.addView(viewName, view);
		Controller.loadView(viewName);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddNullView()
	{
		Controller.addView("foo", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddNullViewName()
	{
		Controller.addView(null, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetNullViewName()
	{
		Controller.loadView(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddBlankViewName()
	{
		Controller.addView("", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetBlankViewName()
	{
		Controller.loadView("");
	}
}
