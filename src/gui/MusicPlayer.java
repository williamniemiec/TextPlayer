package gui;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class MusicPlayer extends JPanel {
	final static int WIDTH = 600;
	final static int HEIGHT = 400;
	final static int X = 100;
	final static int Y = 100;
	
	
	/**
	 * Create the panel.
	 */
	public MusicPlayer() {
		setBorder(null);
		this.setBounds(X,Y, WIDTH, HEIGHT);
		setLayout(new BorderLayout(0, 0));
		
		// Header
		JPanel pnl_top = new JPanel();
		pnl_top.setBackground(Color.DARK_GRAY);
		FlowLayout flowLayout = (FlowLayout) pnl_top.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		add(pnl_top, BorderLayout.NORTH);
		
		
		// Logo
		try {
			BufferedImage myPicture;
			myPicture = ImageIO.read(new File("C:\\Users\\William Niemiec\\Documents\\GitHub\\TextPlayer\\headerlogo.jpg"));
			
			JLabel lbl_musicPlayer = new JLabel();
			pnl_top.add(lbl_musicPlayer, BorderLayout.NORTH);
			
			ImageIcon img = new ImageIcon(myPicture.getScaledInstance(WIDTH, HEIGHT/3, Image.SCALE_SMOOTH));
			lbl_musicPlayer.setIcon(img);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		JPanel pnl_down = new JPanel();
		add(pnl_down, BorderLayout.SOUTH);
		pnl_down.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		JButton btn_play = new JButton();
		pnl_down.add(btn_play);
		try {
			BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\William Niemiec\\Documents\\GitHub\\TextPlayer\\src\\content\\images\\musicPlayer\\play.png"));
			ImageIcon img = new ImageIcon(myPicture.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			btn_play.setEnabled(false);
			btn_play.setIcon(img);
			btn_play.setContentAreaFilled(false);
			btn_play.setFocusPainted(true);
			btn_play.setBorderPainted(false);
			btn_play.setOpaque(false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		JButton btn_pause = new JButton();
		pnl_down.add(btn_pause);
		try {
			BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\William Niemiec\\Documents\\GitHub\\TextPlayer\\src\\content\\images\\musicPlayer\\pause.png"));
			ImageIcon img = new ImageIcon(myPicture.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			btn_pause.setIcon(img);
			btn_pause.setContentAreaFilled(false);
			btn_pause.setFocusPainted(true);
			btn_pause.setBorderPainted(false);
			btn_pause.setOpaque(false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		JButton btn_stop = new JButton();
		pnl_down.add(btn_stop);
		try {
			BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\William Niemiec\\Documents\\GitHub\\TextPlayer\\src\\content\\images\\musicPlayer\\stop.png"));
			ImageIcon img = new ImageIcon(myPicture.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			btn_stop.setIcon(img);
			btn_stop.setContentAreaFilled(false);
			btn_stop.setFocusPainted(true);
			btn_stop.setBorderPainted(false);
			btn_stop.setOpaque(false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		JPanel pnl_center = new JPanel();
		pnl_center.setBorder(null);
		add(pnl_center);
		pnl_center.setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_filename = new JPanel();
		pnl_center.add(pnl_filename, BorderLayout.NORTH);
		pnl_filename.setLayout(new BorderLayout(0, 0));
		
		JLabel lbl_filename_title = new JLabel("Filename: ");
		lbl_filename_title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_filename_title.setFont(new Font("Tahoma", Font.BOLD, 15));
		pnl_filename.add(lbl_filename_title, BorderLayout.WEST);
		
		JLabel lbl_filename_name = new JLabel("name_of_the_file");
		lbl_filename_name.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_filename_name.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnl_filename.add(lbl_filename_name, BorderLayout.CENTER);
		
		JPanel pnl_center_center = new JPanel(new BorderLayout(0,0));
		pnl_center.add(pnl_center_center, BorderLayout.CENTER);
		pnl_center_center.setLayout(new BorderLayout(0, 0));
		
		
		JButton btn_changeFile = new JButton("Change file");
		btn_changeFile.setEnabled(false);
		pnl_center_center.add(btn_changeFile, BorderLayout.NORTH);
		
		
		JScrollPane scrollPane = new JScrollPane();
		pnl_center_center.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		textArea.setLineWrap(true);
		
		JProgressBar pb_music = new JProgressBar();
		pb_music.setStringPainted(true);
		
		pb_music.setForeground(new Color(238,90,9));
		pb_music.setValue(60);
		pnl_center.add(pb_music, BorderLayout.SOUTH);

	}

}
