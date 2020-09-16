package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controllers.HomeController;
import core.Controller;
import core.Model;
import core.View;


/**
 * View associated with HomeController. It will be responsible for program's 
 * main screen view.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
@SuppressWarnings("serial")
public class HomeView extends JPanel implements View
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private static final ResourceBundle RB = ResourceBundle.getBundle("resources.lang.home.home");
	private final static int MAIN_FRAME_WIDTH = 800;
	private final static int MAIN_FRAME_HEIGHT = 500;
	private final static int MAIN_FRAME_X = 100;
	private final static int MAIN_FRAME_Y = 100;
	private final static String VERSION = "1.0.0";
	private HomeController homeController;
	private JFrame mainFrame;
	private JMenuBar mb;
	private JLabel home_background;
	private BufferedImage home_background_file;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	/**
	 * Creates a representation of home view.
	 * 
	 * @param		homeController Controller responsible for the view
	 * @param		mainFrame Main frame of the application
	 */
	public HomeView(HomeController homeController, JFrame mainFrame)
	{
		this.homeController = homeController;
		this.mainFrame = mainFrame;

		make_mainFrame();
		make_home();
		
		
		// Updates menu bar items when the view is displayed. Also, updates the
		// dimensions of the background image dimensions when the window is resized 
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
				homeController.updateMenuBar();
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				resize_background(mainFrame.getWidth(), mainFrame.getHeight());
			}

			@Override
			public void componentMoved(ComponentEvent e) { }

			@Override
			public void componentHidden(ComponentEvent e) { }
		});
				
		mainFrame.setVisible(true);
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	/**
	 * {@inheritDoc}
	 * @see		View#update(Model, Object)
	 */
	@Override
	public void update(Model model, Object data) 
	{
		//homeController.updateMenuBar();
		System.out.println("update");
	}
	
	/**
	 * Creates main frame of the application.
	 */
	private void make_mainFrame()
	{
		mainFrame.setOpacity(1.0f);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(MAIN_FRAME_X, MAIN_FRAME_Y, MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
		mainFrame.setMinimumSize(new Dimension(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT));
		mainFrame.setTitle("Text Player");

		make_menuBar();
	}
	
	/**
	 * Creates top bar.
	 */
	private void make_menuBar()
	{
		mb = new JMenuBar();
		mainFrame.setJMenuBar(mb);
		
		make_mn_file();
		make_mn_controller();
		make_mn_about();		
	}
	
	/**
	 * Creates the 'File' menu in the top bar.
	 */
	private void make_mn_file()
	{
		JMenuItem mb_file_open, mb_file_close, btn_file_exit;
		JMenu mb_file = new JMenu(RB.getString("FILE"));
		mb.add(mb_file);
		
		// Creates 'Open' button
		mb_file_open = new JMenuItem(RB.getString("OPEN"));
		mb_file.add(mb_file_open);
		mb_file_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ask_file_open();
			}
		});
		
		// Creates 'Close' button
		mb_file_close = new JMenuItem(RB.getString("CLOSE"));
		mb_file_close.setEnabled(false);
		mb_file.add(mb_file_close);
		mb_file_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Controller.loadView("HomeView");
			}
		});
		homeController.addMainFrameComponent("mb_file_close", mb_file_close);
		
		// Creates 'Exit' button
		btn_file_exit = new JMenuItem(RB.getString("EXIT"));
		mb_file.add(btn_file_exit);
		btn_file_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.dispose();
			}
		});
	}
	
	/**
	 * Creates the 'Control' menu in the top bar.
	 */
	private void make_mn_controller()
	{
		JMenuItem mb_ctrl_play, mb_ctrl_pause, mb_ctrl_stop;
		JMenu mn_ctrl = new JMenu(RB.getString("CONTROL"));
		mb.add(mn_ctrl);
		
		// Creates 'Play' button
		mb_ctrl_play = new JMenuItem(RB.getString("PLAY"));
		mb_ctrl_play.setEnabled(false);
		mn_ctrl.add(mb_ctrl_play);
		homeController.addMainFrameComponent("mb_ctrl_play", mb_ctrl_play);
		
		// Creates 'Pause' button
		mb_ctrl_pause = new JMenuItem(RB.getString("PAUSE"));
		mb_ctrl_pause.setEnabled(false);
		mn_ctrl.add(mb_ctrl_pause);
		homeController.addMainFrameComponent("mb_ctrl_pause", mb_ctrl_pause);
		
		// Creates 'Stop' button
		mb_ctrl_stop = new JMenuItem(RB.getString("STOP"));
		mb_ctrl_stop.setEnabled(false);
		mn_ctrl.add(mb_ctrl_stop);
		homeController.addMainFrameComponent("mb_ctrl_stop", mb_ctrl_stop);
	}
	
	/**
	 * Creates the 'About' menu in the top bar.
	 */
	private void make_mn_about()
	{
		JButton mn_about = new JButton(RB.getString("ABOUT"));
		
		
		mn_about.setOpaque(true);
		mn_about.setContentAreaFilled(false);
		mn_about.setBorderPainted(false);
		mn_about.setFocusable(false);
		mb.add(mn_about);
		mn_about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				show_about();
			}
		});
	}
	
	/**
	 * Open about screen.
	 */
	private void show_about()
	{
		JOptionPane.showMessageDialog(
				mainFrame,
				RB.getString("VERSION") +" " + VERSION + "\n\n" +RB.getString("MADE_BY") + ":\n"
				+ "-> Matheus Hiroyuki Suwa Moura \n"
				+ "-> William Niemiec", RB.getString("ABOUT"),
				JOptionPane.INFORMATION_MESSAGE
		);
	}
	
	/**
	 * Opens open file dialog.
	 */
	private void ask_file_open()
	{
		Object[] options = {RB.getString("FILE_CHOOSE_OP1"), RB.getString("FILE_CHOOSE_OP2")};
		int op = JOptionPane.showOptionDialog(
			mainFrame, RB.getString("FILE_OPEN_CHOOSE"), RB.getString("FILE_OPEN"), 
			-1, JOptionPane.QUESTION_MESSAGE, null, options, options[0]
		);
		
		
		if (op == 0)
			open_file_gui();
		else if (op == 1)
			open_file_text();
	}
	
	/**
	 * Opens open file dialog by text.
	 */
	private void open_file_text()
	{
		String filepath = JOptionPane.showInputDialog("Enter the file path");
		
		
		while (filepath != null && !fileExists(filepath)) {
			JOptionPane.showMessageDialog(mainFrame, RB.getString("ERROR_FILE_NOT_FOUND"),RB.getString("ERROR"),JOptionPane.ERROR_MESSAGE);
			filepath = JOptionPane.showInputDialog(RB.getString("FILE_GET_FILEPATH"));
		}
		
		if (filepath != null)
			homeController.parseFile(new File(filepath));
	}
	
	/**
	 * Opens open file dialog by GUI.
	 */
	private void open_file_gui()
	{
		String filepath;
		FileDialog fd;
		
		
		fd = new FileDialog(mainFrame, RB.getString("FILE_CHOOSE_DIALOG_TITLE"), FileDialog.LOAD);
		fd.setDirectory("./");
		fd.setFile("*.txt");
		fd.setVisible(true);
		
		if (fd.getFile() != null) {
			filepath = fd.getDirectory()+fd.getFile();
			
			homeController.parseFile(new File(filepath));
		}
	}
	
	/**
	 * Checks if a file exists or not.
	 * 
	 * @param		filepath File
	 * 
	 * @return		If file exists
	 */
	private boolean fileExists(String filepath)
	{
		return new File(filepath).exists();
	}
	
	/**
	 * Creates main screen.
	 */
	private void make_home()
	{
		setLayout(new BorderLayout(0, 0));

		make_background();
		make_btn_openFile();
	}
	
	/**
	 * Sets main screen background.
	 */
	private void make_background()
	{
		try {
			home_background_file = ImageIO.read(new File(System.getProperty("user.dir")+"/src/assets/img/home/logo.jpg"));
			home_background = new JLabel(new ImageIcon(home_background_file.getScaledInstance(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT, Image.SCALE_FAST)));
			add(home_background);
		} 
		catch (IOException e1) { e1.printStackTrace();}
	}
	
	/**
	 * Sets dimensions of main screen background.
	 * 
	 * @param		w Background width
	 * @param		h Background height
	 */
	private void resize_background(int w, int h)
	{
		home_background.setIcon(new ImageIcon(home_background_file.getScaledInstance(w, h, Image.SCALE_FAST)));
	}
	
	/**
	 * Creates open file button.
	 */
	private void make_btn_openFile()
	{
		JButton btn_openFile = new JButton(RB.getString("FILE_OPEN"));
		
		
		add(btn_openFile, BorderLayout.SOUTH);
		btn_openFile.setFocusPainted(false);
		btn_openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ask_file_open();
			}
		});
	}
}
