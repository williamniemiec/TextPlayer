package models.io.input.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class TextInput 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private boolean actionPressed;
	private JTextArea txt_content;
	private JDialog textInputWindow;
	private int frameWidth;
	private int frameHeight;
	private int frameX;
	private int frameY;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	public TextInput(int width, int height, int x, int y)
	{
		if (width < 0)
			throw new IllegalArgumentException("Width must be greater than zero");
		
		if (height < 0)
			throw new IllegalArgumentException("Height must be greater than zero");
		
		if (x < 0)
			throw new IllegalArgumentException("X-position must be greater than zero");
		
		if (y < 0)
			throw new IllegalArgumentException("Y-position must be greater than zero");
		
		frameWidth = width;
		frameHeight = height;
		frameX = x;
		frameY = y;
	}
	
	public TextInput()
	{
		frameWidth = 600;
		frameHeight = 250;
		frameX = 150;
		frameY = 150;
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	public List<String> getInput(JFrame window, String windowTitle, String clearButtonTitle, String actionButtonTitle) 
	{
		if (window == null)
			throw new IllegalArgumentException("Window cannot be null");
		
		if ((windowTitle == null) || windowTitle.isBlank())
			throw new IllegalArgumentException("Window title cannot be empty");
		
		if ((clearButtonTitle == null) || clearButtonTitle.isBlank())
			throw new IllegalArgumentException("Clear button title cannot be empty");
		
		if ((actionButtonTitle == null) || actionButtonTitle.isBlank())
			throw new IllegalArgumentException("Action button title cannot be empty");
		
		JPanel pnl_control = createControlPanel(clearButtonTitle, actionButtonTitle);
		
		
		createDialog(window, windowTitle, pnl_control);
		
		return actionPressed ? Arrays.asList(txt_content.getText().split("\\n")) : null;
	}
	
	private void clearText()
	{
		if (txt_content != null) {
			txt_content.setText("");
			txt_content.setCaretPosition(0);
			txt_content.requestFocus();
		}
	}
	
	private void createDialog(JFrame parent, String title, JPanel controlPanel)
	{
		if (parent == null)
			throw new IllegalArgumentException("Parent cannot be null");
		
		if ((title == null) || title.isBlank())
			throw new IllegalArgumentException("Title cannot be empty");
		
		if (controlPanel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		JScrollPane scrl_txtContent = new JScrollPane();
		

		// Creates text area		
		txt_content = new JTextArea();
		txt_content.setLineWrap(true);
		txt_content.setWrapStyleWord(true);
		txt_content.setMargin(new Insets(10, 10, 10, 10));
		
		// Sets scroll bar on text area
		scrl_txtContent.setViewportView(txt_content);
		
		// Creates dialog
		textInputWindow = new JDialog(parent, title);
		textInputWindow.setLayout(new BorderLayout(0, 0));
		textInputWindow.add(scrl_txtContent, BorderLayout.CENTER);
		textInputWindow.add(controlPanel, BorderLayout.SOUTH);
		textInputWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		textInputWindow.setBounds(frameX, frameY, frameWidth, frameHeight);
		textInputWindow.setModalityType(ModalityType.APPLICATION_MODAL);
		textInputWindow.setVisible(true);
	}
	
	private JPanel createControlPanel(String clearButtonTitle, String actionButtonTitle)
	{
		if ((clearButtonTitle == null) || clearButtonTitle.isBlank())
			throw new IllegalArgumentException("Clear button title cannot be empty");
		
		if ((actionButtonTitle == null) || actionButtonTitle.isBlank())
			throw new IllegalArgumentException("Action button title cannot be empty");
		
		JButton btn_action = new JButton(actionButtonTitle);
		JButton btn_clear = new JButton(clearButtonTitle);
		JPanel pnl_control = new JPanel();
		
		
		btn_action.setFocusPainted(false);
		btn_action.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPressed = true;
				textInputWindow.dispose();
			}
		});
		
		btn_clear.setFocusPainted(false);
		btn_clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearText();
			}
		});
		
		pnl_control.setLayout(new GridLayout(0, 2, 0, 0));
		pnl_control.add(btn_clear);
		pnl_control.add(btn_action);
		
		return pnl_control;
	}
}
