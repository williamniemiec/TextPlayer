package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	 * @param		mainFrame Main application frame
	 */
	public TextPlayerView(TextPlayerController textPlayerController, JFrame frame)
	{
		this.textPlayerController = textPlayerController;
		this.frame = frame;
		
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
	 * @see		View#update(Model, Object)
	 */
	@Override
	public void update(Model model, Object data) {
		MusicPlayer mp = (MusicPlayer) model;
		long musicLength = mp.getMusicLength();
		long musicPosition = mp.getMusicPosition();
		
		// Updates progress bar
		pb_music.setValue((int)(musicPosition/musicLength));
				
		// Updates control player buttons
		btn_ctrl_play.setEnabled(!mp.isPlaying());
		btn_ctrl_pause.setEnabled(!mp.isPaused());
		btn_ctrl_stop.setEnabled(!mp.isStopped());
		
		// Updates top bar buttons
		textPlayerController.updateMenuBar(mp);
	}
	
	/**
	 * Updates the section that displays the text data.
	 */
	public void updateFileContent() 
	{
		textArea.setText(textPlayerController.getText());
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
	 */
	private void make_header()
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
			myPicture = ImageIO.read(new File(System.getProperty("user.dir")+"/src/assets/images/musicPlayer/header_logo.jpg"));
			lbl_musicPlayer = new JLabel();
			pnl_top.add(lbl_musicPlayer, BorderLayout.NORTH);
			
			img = new ImageIcon(myPicture.getScaledInstance(frame.getWidth(), frame.getHeight()/3, Image.SCALE_SMOOTH));
			lbl_musicPlayer.setIcon(img);
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Adds banner to the view
		add(pnl_top, BorderLayout.NORTH);
	}
	
	/**
	 * Creates player control buttons.
	 */
	private void make_controls()
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
	 */
	private JButton make_btn_play(JPanel panel)
	{
		return make_btn_ctrl(
			panel, 
			System.getProperty("user.dir")+"/src/assets/images/musicPlayer/play.png"
		);
	}
	
	/**
	 * Creates pause button.
	 * 
	 * @param		panel Panel to which the button will be placed
	 * 
	 * @return		Created button
	 */
	private JButton make_btn_pause(JPanel panel)
	{
		return make_btn_ctrl(
			panel, 
			System.getProperty("user.dir")+"/src/assets/images/musicPlayer/pause.png"
		);
	}
	
	/**
	 * Creates stop button.
	 * 
	 * @param		panel Panel to which the button will be placed
	 * 
	 * @return		Created button
	 */
	private JButton make_btn_stop(JPanel panel)
	{
		return make_btn_ctrl(
			panel, 
			System.getProperty("user.dir")+"/src/assets/images/musicPlayer/stop.png"
		);
	}
	
	/**
	 * Creates player control button.
	 * 
	 * @param		panel Panel to which the button will be placed
	 * @param		filepath Path of the file containing the button image
	 * 
	 * @return		Created button
	 */
	private JButton make_btn_ctrl(JPanel panel, String filepath)
	{
		JButton btn_ctrl = new JButton();
		BufferedImage myPicture;
		ImageIcon img;
		
		
		panel.add(btn_ctrl);
		
		try {
			myPicture = ImageIO.read(new File(filepath));
			img = new ImageIcon(myPicture.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			btn_ctrl.setEnabled(false);
			btn_ctrl.setIcon(img);
			btn_ctrl.setContentAreaFilled(false);
			btn_ctrl.setFocusPainted(true);
			btn_ctrl.setBorderPainted(false);
			btn_ctrl.setOpaque(false);
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return btn_ctrl;
	}
	
	/**
	 * Creates central area.
	 */
	private void make_centralPanel()
	{
		JPanel pnl_center, pnl_center_center;
		
		
		// Panel responsible for the central section of the view
		pnl_center = new JPanel();
		pnl_center.setBorder(null);
		pnl_center.setLayout(new BorderLayout(0, 0));
		add(pnl_center);
		
		// Panel that will be in the center of the center section panel
		pnl_center_center = new JPanel(new BorderLayout(0,0));
		pnl_center_center.setLayout(new BorderLayout(0, 0));
		pnl_center.add(pnl_center_center, BorderLayout.CENTER);
		
		make_fileInfo(pnl_center, BorderLayout.NORTH);
		make_btn_change(pnl_center_center, BorderLayout.NORTH);
		make_textArea(pnl_center_center, BorderLayout.CENTER);
		make_progressBar(pnl_center, BorderLayout.SOUTH);
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
		JPanel pnl_filename = new JPanel();
		JLabel lbl_filename_title;
		
		
		pnl_filename.setLayout(new BorderLayout(0, 0));
		
		lbl_filename_title = new JLabel("Filename: ");
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
		JScrollPane scrollPane = new JScrollPane();
		
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);		
		textArea.setLineWrap(false);
		textArea.setText(textPlayerController.getText());
		textArea.setEditable(false);
		
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
		pb_music = new JProgressBar();
		pb_music.setStringPainted(true);
		
		pb_music.setForeground(new Color(238,90,9));
		pb_music.setValue(0);
		panel.add(pb_music, constraints);
	}
	
	/**
	 * Creates the section that will contain change file button.
	 * 
	 * @param		panel Panel to which the section will be placed
	 * @param		constraints Position where this section will be added to 
	 * the panel
	 */
	private void make_btn_change(JPanel panel, Object constraints)
	{
		JButton btn_changeFile = new JButton("Escolher outro arquivo");
		
		
		btn_changeFile.setEnabled(true);
		btn_changeFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				open_file();
			}
		});
		
		panel.add(btn_changeFile, constraints);
	}

	/**
	 * Opens a window for choosing a file. It will be used to exchange the 
	 * current file for another one.
	 */
	private void open_file()
	{
		FileDialog fd = new FileDialog(frame, "Escolha um arquivo", FileDialog.LOAD);
		String filepath;
		
		
		fd.setDirectory(".");
		fd.setFile("*.txt");
		fd.setVisible(true);
		filepath = fd.getDirectory()+fd.getFile();
		
		if (filepath != null)
			textPlayerController.changeFile(filepath);
	}
}
