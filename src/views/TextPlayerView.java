package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import controllers.TextPlayerController;
import core.Model;
import core.View;
import models.io.IOType;
import models.musicPlayer.MusicPlayer;


/**
 * Responsible for music player view.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
@SuppressWarnings("serial")
public class TextPlayerView extends JPanel implements View 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private final ResourceBundle RB;
	private TextPlayerController textPlayerController;
	private JFrame frame;
	private JLabel lbl_filename_name;
	private JTextArea textArea;
	private JProgressBar pb_music;
	private JButton btn_ctrl_play;
	private JButton btn_ctrl_pause;
	private JButton btn_ctrl_stop;
	

	//-------------------------------------------------------------------------
	//		Constructor
	//-------------------------------------------------------------------------
	/**
	 * Creates a representation of the music player view.
	 * 
	 * @param		homeController Controller responsible for the view
	 * @param		frame Main application frame
	 * 
	 * @throws		IOException If an error while creating header or controls
	 */
	public TextPlayerView(TextPlayerController textPlayerController, JFrame frame, ResourceBundle RB) throws IOException
	{
		if (textPlayerController == null)
			throw new IllegalArgumentException("Controller cannot be null");
		
		if (frame == null)
			throw new IllegalArgumentException("Frame cannot be null");
		
		if (RB == null)
			throw new IllegalArgumentException("Resource bundle cannot be null");
		
		this.textPlayerController = textPlayerController;
		this.frame = frame;
		this.RB = RB;
		
		make_panel();
		make_header();
		make_controls();
		make_centralPanel();
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	/**
	 * {@inheritDoc}
	 * @see			View#update(Model, Object)
	 */
	@Override
	public void update(Model model, Object data) {
		if (!(model instanceof MusicPlayer))
			return;
		
		MusicPlayer mp = (MusicPlayer) model;
		long musicLength = mp.getMusicLength();
		long musicPosition = mp.getMusicPosition();
		
		
		// Updates progress bar
		pb_music.setValue((int)(musicPosition/musicLength));
				
		// Updates control player buttons
		btn_ctrl_play.setEnabled(!mp.isPlaying());
		btn_ctrl_pause.setEnabled(!mp.isPaused());
		btn_ctrl_stop.setEnabled(!mp.isStopped());
	}
	
	/**
	 * Updates the section that displays the text data.
	 */
	public void update_content() 
	{
		String content = textPlayerController.getText()
				.stream()
				.map((line) -> line + "\n")
				.collect(Collectors.joining(""));
		
		
		textArea.setText(content);
		textArea.setCaretPosition(0);
		lbl_filename_name.setText(textPlayerController.getFilename());
	}
	
	/**
	 * Creates {@link JPanel} of the view.
	 */
	private void make_panel()
	{
		setBorder(null);
		setBounds(frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight());
		setLayout(new BorderLayout(0, 0));
	}
	
	/**
	 * Creates header.
	 * 
	 * @throws		IOException If an error occurs during reading header image  
	 */
	private void make_header() throws IOException
	{
		JPanel pnl_top = new JPanel();
		FlowLayout flowLayout;
		BufferedImage myPicture;
		JLabel lbl_musicPlayer;
		ImageIcon img;
		
		
		pnl_top.setBackground(new Color(64, 64, 64));
		flowLayout = (FlowLayout) pnl_top.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		
		// Places the banner in the header
		try {
			myPicture = ImageIO.read(new File(System.getProperty("user.dir")+"/src/assets/img/player/header_logo.jpg"));
		}
		catch (IOException e) {
			throw new IOException("Error while reading header image");
		}
		
		lbl_musicPlayer = new JLabel();
		pnl_top.add(lbl_musicPlayer, BorderLayout.NORTH);
		
		img = new ImageIcon(myPicture.getScaledInstance(frame.getWidth(), frame.getHeight()/3, Image.SCALE_SMOOTH));
		lbl_musicPlayer.setIcon(img);
		
		// Adds banner to the view
		add(pnl_top, BorderLayout.NORTH);
	}
	
	/**
	 * Creates player control buttons.
	 * 
	 * @throws		IOException If an error occurs during reading control icon   
	 */
	private void make_controls() throws IOException
	{
		JPanel pnl_down = new JPanel();
		
		
		add(pnl_down, BorderLayout.SOUTH);
		pnl_down.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// Play button
		btn_ctrl_play = make_btn_play(pnl_down);
		btn_ctrl_play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textPlayerController.play();
			}
		});
		btn_ctrl_play.setEnabled(true);
		
		// Pause button
		btn_ctrl_pause = make_btn_pause(pnl_down);
		btn_ctrl_pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textPlayerController.pause();
			}
		});
		
		// Stop button
		btn_ctrl_stop = make_btn_stop(pnl_down);
		btn_ctrl_stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textPlayerController.stop();
			}
		});
	}
	
	/**
	 * Creates play button.
	 * 
	 * @param		panel Panel to which the button will be placed
	 * 
	 * @return		Created button
	 * 
	 * @throws		IOException If an error occurs during reading play control
	 * icon
	 */
	private JButton make_btn_play(JPanel panel) throws IOException
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		return make_btn_ctrl(
			panel, 
			System.getProperty("user.dir")+"/src/assets/img/player/play.png"
		);
	}
	
	/**
	 * Creates pause button.
	 * 
	 * @param		panel Panel to which the button will be placed
	 * 
	 * @return		Created button
	 * 
	 * @throws		IOException If an error occurs during reading pause control
	 * icon
	 */
	private JButton make_btn_pause(JPanel panel) throws IOException
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		return make_btn_ctrl(
			panel, 
			System.getProperty("user.dir")+"/src/assets/img/player/pause.png"
		);
	}
	
	/**
	 * Creates stop button.
	 * 
	 * @param		panel Panel to which the button will be placed
	 * 
	 * @return		Created button
	 * 
	 * @throws		IOException If an error occurs during reading stop control
	 * icon
	 */
	private JButton make_btn_stop(JPanel panel) throws IOException
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		return make_btn_ctrl(
			panel, 
			System.getProperty("user.dir")+"/src/assets/img/player/stop.png"
		);
	}
	
	/**
	 * Creates player control button.
	 * 
	 * @param		panel Panel to which the button will be placed
	 * @param		filepath Path of the file containing the button image
	 * 
	 * @return		Created button
	 * 
	 * @throws		IOException If an error occurs during reading control icon   
	 */
	private JButton make_btn_ctrl(JPanel panel, String filepath) throws IOException
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		if ((filepath == null) || filepath.isBlank())
			throw new IllegalArgumentException("Filepath cannot be empty");
		
		JButton btn_ctrl = new JButton();
		BufferedImage myPicture;
		ImageIcon img;
		
		
		panel.add(btn_ctrl);
		
		try {
			myPicture = ImageIO.read(new File(filepath));
		}
		catch (IOException e) {
			throw new IOException("Error while reading control icon");
		}
		
		img = new ImageIcon(myPicture.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		btn_ctrl.setEnabled(false);
		btn_ctrl.setIcon(img);
		btn_ctrl.setContentAreaFilled(false);
		btn_ctrl.setFocusPainted(true);
		btn_ctrl.setBorderPainted(false);
		btn_ctrl.setOpaque(false);
		
		return btn_ctrl;
	}
	
	/**
	 * Creates central area.
	 */
	private void make_centralPanel()
	{
		JPanel pnl_center, pnl_center_center, pnl_options;
		
		
		// Panel responsible for the central section of the view
		pnl_center = new JPanel();
		pnl_center.setBorder(null);
		pnl_center.setLayout(new BorderLayout(0, 0));
		add(pnl_center);
		
		// Panel that will be in the center of the center section panel
		pnl_center_center = new JPanel(new BorderLayout(0,0));
		pnl_center_center.setLayout(new BorderLayout(0, 0));
		pnl_center.add(pnl_center_center, BorderLayout.CENTER);
		
		pnl_options = make_pnl_options();
		pnl_center_center.add(pnl_options, BorderLayout.NORTH);
		
		make_fileInfo(pnl_center, BorderLayout.NORTH);
		make_textArea(pnl_center_center, BorderLayout.CENTER);
		make_progressBar(pnl_center, BorderLayout.SOUTH);
	}
	
	/**
	 * Creates options panel.
	 * 
	 * @return		Options panel
	 */
	private JPanel make_pnl_options()
	{
		JPanel pnl_options, pnl_options_top, pnl_options_bottom;
		
		
		pnl_options_top = new JPanel();
		pnl_options_top.setLayout(new GridLayout(0, 2, 0, 0));
		
		pnl_options_bottom = new JPanel();
		pnl_options_bottom.setLayout(new GridLayout(0, 1, 0, 0));
		
		pnl_options = new JPanel();
		pnl_options.setLayout(new BorderLayout(0, 0));
		pnl_options.add(pnl_options_top, BorderLayout.NORTH);
		pnl_options.add(pnl_options_bottom, BorderLayout.SOUTH);
		
		make_btn_textEntry(pnl_options_top);
		make_btn_changeFile(pnl_options_top);
		make_btn_exportFileMusic(pnl_options_bottom);
		
		return pnl_options;
	}
	
	/**
	 * Creates the section that will display filename.
	 * 
	 * @param		panel Panel to which the section will be placed
	 * @param		constraints Position where this section will be added to 
	 * the panel
	 */
	private void make_fileInfo(JPanel panel, Object constraints)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		if (constraints == null)
			throw new IllegalArgumentException("Constraints cannot be null");
		
		JPanel pnl_filename = new JPanel();
		JLabel lbl_filename_title;
		
		
		pnl_filename.setLayout(new BorderLayout(0, 0));
		
		lbl_filename_title = new JLabel(RB.getString("FILENAME") + ": ");
		lbl_filename_title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_filename_title.setFont(new Font("Tahoma", Font.BOLD, 15));
		pnl_filename.add(lbl_filename_title, BorderLayout.WEST);
		
		lbl_filename_name = new JLabel(textPlayerController.getFilename());
		lbl_filename_name.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_filename_name.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnl_filename.add(lbl_filename_name, BorderLayout.CENTER);
		
		panel.add(pnl_filename, constraints);
	}
	
	/**
	 * Creates the section that will display file content.
	 * 
	 * @param		panel Panel to which the section will be placed
	 * @param		constraints Position where this section will be added to 
	 * the panel
	 */
	private void make_textArea(JPanel panel, Object constraints)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		if (constraints == null)
			throw new IllegalArgumentException("Constraints cannot be null");
		
		JScrollPane scrollPane = new JScrollPane();
		String text = textPlayerController.getText()
				.stream()
				.map((line) -> line + "\n")
				.collect(Collectors.joining(""));
		
		
		// Creates text area
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setText(text);
		textArea.setEditable(false);
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setCaretPosition(0);
		
		// Sets scroll bar on text area
		scrollPane.setViewportView(textArea);
		
		panel.add(scrollPane, constraints);
	}
	
	/**
	 * Creates the section that will display music progress bar.
	 * 
	 * @param		panel Panel to which the section will be placed
	 * @param		constraints Position where this section will be added to 
	 * the panel
	 */
	private void make_progressBar(JPanel panel, Object constraints)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		if (constraints == null)
			throw new IllegalArgumentException("Constraints cannot be null");
		
		pb_music = new JProgressBar();
		pb_music.setStringPainted(true);
		
		pb_music.setForeground(new Color(238,90,9));
		pb_music.setValue(0);
		panel.add(pb_music, constraints);
	}
	
	/**
	 * Creates the section that will contain change file button.
	 * 
	 * @param		panel Panel that the button will be added
	 */
	private void make_btn_changeFile(JPanel panel)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		JButton btn_openFile = new JButton(RB.getString("FILE_OPEN"));
		

		panel.add(btn_openFile);
		
		btn_openFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textPlayerController.changeText(IOType.FILE_LOAD);
			}
		});
	}
	
	/**
	 * Creates the section that will contain export file button.
	 * 
	 * @param		panel Panel that the button will be added
	 */
	private void make_btn_exportFileMusic(JPanel panel)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		JButton btn_openFile = new JButton(RB.getString("EXPORT_TO_MIDI"));
		

		panel.add(btn_openFile);
		
		btn_openFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textPlayerController.exportMusicFile();
			}
		});
	}
	
	/**
	 * Creates text entry button.
	 * 
	 * @param		panel Panel that the button will be added
	 */
	private void make_btn_textEntry(JPanel panel)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		JButton btn_textEntry = new JButton(RB.getString("TEXT_ENTRY"));
		
		
		panel.add(btn_textEntry);
		btn_textEntry.setFocusPainted(false);
		btn_textEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPlayerController.changeText(IOType.TEXT);
			}
		});
	}
}
