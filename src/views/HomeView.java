package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
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
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import controllers.HomeController;
import core.Controller;
import core.Model;
import core.View;
import models.io.input.dialog.FileInputDialog;
import models.io.input.dialog.FileInputType;
import models.io.input.dialog.TextInputDialog;


/**
 * View associated with HomeController. It will be responsible for program's 
 * main screen view.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public class HomeView extends JPanel implements View
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private static final long serialVersionUID = 100L;
	private static final ResourceBundle lang = 
			ResourceBundle.getBundle("resources.lang.home.home");
	private static final int MAIN_FRAME_WIDTH = 800;
	private static final int MAIN_FRAME_HEIGHT = 500;
	private static final int MAIN_FRAME_X = 100;
	private static final int MAIN_FRAME_Y = 100;
	private static final String VERSION = "1.0.0";	
	private transient HomeController homeController;
	private JFrame window;
	private JMenuBar mb;
	private JLabel lblHomeBackground;
	private transient BufferedImage imgHomeBackground;
	
	
	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	/**
	 * Creates a representation of home view.
	 * 
	 * @param		homeController Controller responsible for the view
	 * @param		frame Main frame of the application
	 * 
	 * @throws		IOException If an error occurs during reading home image 
	 */
	public HomeView(HomeController homeController, JFrame frame) throws IOException
	{
		if (homeController == null)
			throw new IllegalArgumentException("Controller cannot be null");
		
		if (frame == null)
			throw new IllegalArgumentException("Frame cannot be null");
		
		this.homeController = homeController;
		this.window = frame;
		
		makeMainFrame();
		makeHome();
		autoresize();
			
		frame.setVisible(true);
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	/**
	 * {@inheritDoc}
	 * @see			View#update(Model, Object)
	 */
	@Override
	public void update(Model model, Object data) 
	{
		homeController.updateMenuBar();
	}
	
	/**
	 * Creates main frame of the application.
	 */
	private void makeMainFrame()
	{
		window.setOpacity(1.0f);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setBounds(MAIN_FRAME_X, MAIN_FRAME_Y, MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
		window.setMinimumSize(new Dimension(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT));
		window.setTitle("Text Player");

		makeMenuBar();
	}
	
	/**
	 * Creates main screen.
	 * 
	 * @throws		IOException If an error occurs during reading home image 
	 */
	private void makeHome() throws IOException
	{
		setLayout(new BorderLayout(0, 0));

		makeBackground();
		makeInputOptions();
	}
	
	/**
	 * Creates top bar.
	 */
	private void makeMenuBar()
	{
		mb = new JMenuBar();
		window.setJMenuBar(mb);
		
		makeMenuBarOptionFile();
		makeMenuBarOptionController();
		makeMenuBarOptionAbout();		
	}
	
	/**
	 * Creates the 'File' menu in the top bar.
	 */
	private void makeMenuBarOptionFile()
	{
		JMenu mbFile = new JMenu(lang.getString("FILE"));
		
		
		makeMenuOptionTextEntry(mbFile);
		makeMenuOptionOpenFile(mbFile);
		makeMenuOptionClose(mbFile);
		makeMenuOptionExit(mbFile);
		
		mb.add(mbFile);
	}
	
	/**
	 * Creates the 'Control' menu in the top bar.
	 */
	private void makeMenuBarOptionController()
	{
		JMenu mbCtrl = new JMenu(lang.getString("CONTROL"));
		
		
		makeMenuOptionPlayPause(mbCtrl);
		makeMenuOptionStop(mbCtrl);
		
		mb.add(mbCtrl);
	}
	
	/**
	 * Creates the 'About' menu in the top bar.
	 */
	private void makeMenuBarOptionAbout()
	{
		JButton mbAbout = new JButton(lang.getString("ABOUT"));
		
		
		mbAbout.setOpaque(true);
		mbAbout.setContentAreaFilled(false);
		mbAbout.setBorderPainted(false);
		mbAbout.setFocusable(false);
		mbAbout.addActionListener(event -> showAbout());
		mb.add(mbAbout);
	}
	
	/**
	 * Creates text entry menu button and adds it to a JMenu.
	 * 
	 * @param		menu Menu that the button will be added
	 */
	private void makeMenuOptionTextEntry(JMenu menu)
	{
		JMenuItem mbItemTextEntry = new JMenuItem(lang.getString("TEXT_ENTRY"));
		
		
		mbItemTextEntry.setFocusable(false);
		mbItemTextEntry.addActionListener(event -> 
				homeController.openPlayer(new TextInputDialog(window))
		);
		
		menu.add(mbItemTextEntry);
		
		homeController.addMainFrameComponent("mb_file_textEntry", mbItemTextEntry);
	}
	
	/**
	 * Creates text entry menu button and adds it to a JMenu.
	 * 
	 * @param		menu Menu that the button will be added
	 */
	private void makeMenuOptionOpenFile(JMenu menu)
	{
		JMenuItem mbItemOpenFile = new JMenuItem(lang.getString("OPEN_FILE"));
		
		
		mbItemOpenFile.setFocusable(false);
		mbItemOpenFile.addActionListener(event -> 
				homeController.openPlayer(new FileInputDialog(window, "txt", FileInputType.LOAD))
		);
		
		menu.add(mbItemOpenFile);
		
		homeController.addMainFrameComponent("mb_file_openFile", mbItemOpenFile);
	}
	
	/**
	 * Creates close menu button and adds it to a JMenu.
	 * 
	 * @param		menu Menu that the button will be added
	 */
	private void makeMenuOptionClose(JMenu menu)
	{
		JMenuItem mbItemCloseFileFile = new JMenuItem(lang.getString("CLOSE"));
		
		
		mbItemCloseFileFile.setFocusable(false);
		mbItemCloseFileFile.setEnabled(false);
		mbItemCloseFileFile.addActionListener(event -> Controller.loadView("HomeView"));
		
		menu.add(mbItemCloseFileFile);
		
		homeController.addMainFrameComponent("mb_file_close", mbItemCloseFileFile);
	}
	
	/**
	 * Creates exit menu button and adds it to a JMenu.
	 * 
	 * @param		menu Menu that the button will be added
	 */
	private void makeMenuOptionExit(JMenu menu)
	{
		JMenuItem mbItemExit = new JMenuItem(lang.getString("EXIT"));
		
		
		mbItemExit.setFocusable(false);
		mbItemExit.addActionListener(event -> window.dispose());
		
		menu.add(mbItemExit);
	}
	
	/**
	 * Creates play / pause menu button.
	 * 
	 * @param		menu Menu that the button will be added
	 */
	private void makeMenuOptionPlayPause(JMenu menu)
	{
		JMenuItem mbItemPlayPause = new JMenuItem(lang.getString("PLAY") + "/" + lang.getString("PAUSE"));
		
		
		mbItemPlayPause.setFocusable(false);
		mbItemPlayPause.setEnabled(false);
		
		menu.add(mbItemPlayPause);
		
		homeController.addMainFrameComponent("mb_ctrl_playPause", mbItemPlayPause);
	}
	
	/**
	 * Creates stop menu button.
	 * 
	 * @param		menu Menu that the button will be added
	 */
	private void makeMenuOptionStop(JMenu menu)
	{
		JMenuItem mbItemStop = new JMenuItem(lang.getString("STOP"));
		
		
		mbItemStop.setFocusable(false);
		mbItemStop.setEnabled(false);
		
		menu.add(mbItemStop);
		
		homeController.addMainFrameComponent("mb_ctrl_stop", mbItemStop);
	}
	
	/**
	 * Sets main screen background.
	 * 
	 * @throws		IOException If an error occurs during reading home image   
	 */
	private void makeBackground() throws IOException
	{
		File homeImageFile = new File(System.getProperty("user.dir")+"/src/assets/img/home/logo.jpg");
		ImageIcon homeImage;
		
		
		try {
			imgHomeBackground = ImageIO.read(homeImageFile);
		}
		catch (IOException e) {
			throw new IOException("Error while reading home image");
		}
		
		homeImage = new ImageIcon(
				imgHomeBackground.getScaledInstance(
						MAIN_FRAME_WIDTH, 
						MAIN_FRAME_HEIGHT, 
						Image.SCALE_FAST
		));
		
		lblHomeBackground = new JLabel(homeImage);
		
		add(lblHomeBackground);
	}
	
	/**
	 * Creates input options.
	 */
	private void makeInputOptions()
	{
		JPanel pnlInput = new JPanel();
		
		
		pnlInput.setLayout(new GridLayout(0, 2, 0, 0));
		add(pnlInput, BorderLayout.SOUTH);
		
		makeBtnTextEntry(pnlInput);
		makeBtnOpenFile(pnlInput);
	}
	
	/**
	 * Creates text entry button.
	 * 
	 * @param		panel Panel that the button will be added
	 */
	private void makeBtnTextEntry(JPanel panel)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		JButton btnTextEntry = new JButton(lang.getString("TEXT_ENTRY"));
		
		
		btnTextEntry.setFocusable(false);
		btnTextEntry.setFocusable(false);
		btnTextEntry.setFocusPainted(false);
		btnTextEntry.addActionListener(event -> 
				homeController.openPlayer(new TextInputDialog(window))
		);
		
		panel.add(btnTextEntry);
	}
	
	/**
	 * Creates open file button.
	 * 
	 * @param		panel Panel that the button will be added
	 */
	private void makeBtnOpenFile(JPanel panel)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		JButton btnOpenFile = new JButton(lang.getString("FILE_OPEN"));
		
		btnOpenFile.setFocusable(false);
		btnOpenFile.setFocusPainted(false);
		btnOpenFile.addActionListener(event -> 
				homeController.openPlayer(new FileInputDialog(window, "txt", FileInputType.LOAD))
		);
		
		panel.add(btnOpenFile);
	}
	
	/**
	 * Sets dimensions of main screen background.
	 * 
	 * @param		w Background width
	 * @param		h Background height
	 */
	private void resizeBackground(int w, int h)
	{
		if (w < 0)
			throw new IllegalArgumentException("Width must be greater than zero");
		
		if (h < 0)
			throw new IllegalArgumentException("Height must be greater than zero");
		
		Image homeBackgroundImage = imgHomeBackground.getScaledInstance(w, h, Image.SCALE_FAST);
		
		
		lblHomeBackground.setIcon(new ImageIcon(homeBackgroundImage));
	}
	
	/**
	 * Open about screen.
	 */
	private void showAbout()
	{
		JOptionPane.showMessageDialog(
				window,
				lang.getString("VERSION") +" " + VERSION + "\n\n" +lang.getString("MADE_BY") + ":\n"
				+ "-> Matheus Hiroyuki Suwa Moura \n"
				+ "-> William Niemiec", lang.getString("ABOUT"),
				JOptionPane.INFORMATION_MESSAGE
		);
	}
	
	/**
	 * Updates menu bar items when the view is displayed. Also, updates the
	 * dimensions of the background image dimensions when the window is resized 
	 */
	private void autoresize()
	{
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) 
			{
				homeController.updateMenuBar();
			}
			
			@Override
			public void componentResized(ComponentEvent e) 
			{
				resizeBackground(window.getWidth(), window.getHeight());
			}

			@Override
			public void componentMoved(ComponentEvent e) 
			{
				// Do nothing because when the frame is moved, there is no need
				// to resize the background image or update the menu bar
			}

			@Override
			public void componentHidden(ComponentEvent e)
			{
				// Do nothing because when the frame is hidden, there is no need
				// to resize the background image or update the menu bar
			}
		});
	}
}
