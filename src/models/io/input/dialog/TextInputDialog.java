package models.io.input.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;


/**
 * Responsible for obtaining a text input through a dialog window.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public class TextInputDialog extends InputDialog 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private static final ResourceBundle lang = 
			ResourceBundle.getBundle("resources.lang.io.input.dialog.text");
	private boolean wasProcessButtonPressed;
	private JTextArea txtContent;
	private JDialog textInputWindow;
	private JFrame window;
	private int windowWidth;
	private int windowHeight;
	private int windowX;
	private int windowY;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	/**
	 * @param		Window that will be the parent of the window dialog
	 * @param		width Window width
	 * @param		height Window height
	 * @param		x Initial horizontal position
	 * @param		y Initial vertical position
	 */
	public TextInputDialog(JFrame window, int width, int height, int x, int y)
	{
		if (window == null)
			throw new IllegalArgumentException("Window cannot be null");
		
		if (width < 0)
			throw new IllegalArgumentException("Width must be greater than zero");
		
		if (height < 0)
			throw new IllegalArgumentException("Height must be greater than zero");
		
		if (x < 0)
			throw new IllegalArgumentException("X-position must be greater than zero");
		
		if (y < 0)
			throw new IllegalArgumentException("Y-position must be greater than zero");
		
		this.window = window;
		windowWidth = width;
		windowHeight = height;
		windowX = x;
		windowY = y;
	}
	
	/**
	 * @param		window Window that will be the parent of the window dialog
	 * 
	 * @implNote	Using this constructor, window parameters will be:
	 * <table border=1>
	 * 	<tr>
	 * 		<th>Name</th>
	 * 		<th>Value</th>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>Width</td>
	 * 		<td>600</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>Height</td>
	 * 		<td>250</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>Initial horizontal position</td>
	 * 		<td>150</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>Initial vertical position</td>
	 * 		<td>150</td>
	 * 	</tr>
	 * </table>
	 */
	public TextInputDialog(JFrame window)
	{
		this(window, 600, 250, 150, 150);
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	/**
	 * {@inheritDoc}}
	 */
	@Override
	public boolean openDialog() 
	{
		JPanel pnlControl = createControlPanel();
		
		
		createDialog(pnlControl);
		
		return true;
	}
	
	/**
	 * Creates control panel that will contain the available actions.
	 * 
	 * @return		Control panel
	 */
	private JPanel createControlPanel()
	{
		JPanel pnlControl = new JPanel();
		JButton btnClear = createCtrlButton(lang.getString("CLEAR"), event -> clearText());
		JButton btnAction = createCtrlButton(lang.getString("DONE"), event -> {
			wasProcessButtonPressed = true;
			textInputWindow.dispose();
		});		
		
		
		pnlControl.setLayout(new GridLayout(0, 2, 0, 0));
		pnlControl.add(btnClear);
		pnlControl.add(btnAction);
		
		return pnlControl;
	}
	
	/**
	 * Creates a control button.
	 * 
	 * @param		btnLabel Button label
	 * @param		onClick Action that will be performed when clicking the 
	 * button
	 * 
	 * @return		Control button
	 */
	private JButton createCtrlButton(String btnLabel, ActionListener onClick)
	{
		JButton btnCtrl = new JButton(btnLabel);
		
		
		btnCtrl.setFocusPainted(false);
		btnCtrl.setFocusable(false);
		btnCtrl.addActionListener(onClick);
		
		return btnCtrl;
	}
	
	/**
	 * Creates text input dialog.
	 * 
	 * @param		pnlControl Control panel containing buttons with the 
	 * available actions.
	 */
	private void createDialog(JPanel pnlControl)
	{
		if (pnlControl == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		JScrollPane txtArea = createTextArea();


		textInputWindow = new JDialog(window, lang.getString("DIALOG_TITLE"));
		textInputWindow.setLayout(new BorderLayout(0, 0));
		textInputWindow.add(txtArea, BorderLayout.CENTER);
		textInputWindow.add(pnlControl, BorderLayout.SOUTH);
		textInputWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		textInputWindow.setBounds(windowX, windowY, windowWidth, windowHeight);
		textInputWindow.setModalityType(ModalityType.APPLICATION_MODAL);
		textInputWindow.setVisible(true);
	}
	
	private JScrollPane createTextArea()
	{
		JScrollPane txtAreaWithScroll = new JScrollPane();
		txtContent = new JTextArea();
		
		
		txtContent.setLineWrap(true);
		txtContent.setWrapStyleWord(true);
		txtContent.setMargin(new Insets(10, 10, 10, 10));
		
		// Sets scroll bar on text area
		txtAreaWithScroll.setViewportView(txtContent);
		
		return txtAreaWithScroll;
	}
	
	/**
	 * Erases all text from the text area.
	 */
	private void clearText()
	{
		if (txtContent != null) {
			txtContent.setText("");
			txtContent.setCaretPosition(0);
			txtContent.requestFocus();
		}
	}
	
	
	//-------------------------------------------------------------------------
	//		Getters
	//-------------------------------------------------------------------------
	/**
	 * @return		"N/A" if the 'process' button was pressed; null otherwise
	 */
	@Override
	public String getTitle() 
	{
		return wasProcessButtonPressed ? "N/A" : null;
	}

	/**
	 * Gets text input.
	 * 
	 * @return		Text input or null if 'process' button was not pressed
	 */
	@Override
	public List<String> getContent()
	{
		return wasProcessButtonPressed ? 
				Arrays.asList(txtContent.getText().split("\\n")) 
				: new ArrayList<>();
	}
}
