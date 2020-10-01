package models.input.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class TextInput 
{
	private JTextArea txt_content;
	private JDialog textInputWindow;
	private final static int MAIN_FRAME_WIDTH = 600;
	private final static int MAIN_FRAME_HEIGHT = 250;
	private final static int MAIN_FRAME_X = 150;
	private final static int MAIN_FRAME_Y = 150;
	
	
	public List<String> getInput(JFrame window, String windowTitle, String clearButtonTitle, String actionButtonTitle) 
	{
		JPanel pnl_control = createControlPanel(clearButtonTitle, actionButtonTitle);
		
		
		createDialog(window, windowTitle, pnl_control);
		
		return Arrays.asList(txt_content.getText().split("\\n"));
	}
	
	private void clearText()
	{
		if (txt_content != null)
			txt_content.setText("");
	}
	
	private void createDialog(JFrame parent, String title, JPanel controlPanel)
	{
		txt_content = new JTextArea();
		
		textInputWindow = new JDialog(parent, title);
		
		textInputWindow.setLayout(new BorderLayout(0, 0));
		textInputWindow.add(txt_content, BorderLayout.CENTER);
		textInputWindow.add(controlPanel, BorderLayout.SOUTH);
		textInputWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		textInputWindow.setBounds(MAIN_FRAME_X, MAIN_FRAME_Y, MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
		textInputWindow.setModalityType(ModalityType.APPLICATION_MODAL);
		textInputWindow.setVisible(true);
	}
	
	private JPanel createControlPanel(String clearButtonTitle, String actionButtonTitle)
	{
		JButton btn_action = new JButton(actionButtonTitle);
		JButton btn_clear = new JButton(clearButtonTitle);
		JPanel pnl_control = new JPanel();
		
		
		btn_action.setFocusPainted(false);
		btn_action.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
