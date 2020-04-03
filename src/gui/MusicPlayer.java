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

public class MusicPlayer extends JPanel {

	/**
	 * Create the panel.
	 */
	public MusicPlayer() {
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		
		// Header
		/*
		JLabel lbl_musicPlayer = new JLabel("Music Player");
		lbl_musicPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_musicPlayer.setFont(new Font("Tahoma", Font.PLAIN, 50));
		add(lbl_musicPlayer, BorderLayout.NORTH);
		*/
		
		// Logo
		try {
			BufferedImage myPicture;
			myPicture = ImageIO.read(new File("C:\\Users\\William Niemiec\\Documents\\GitHub\\TextPlayer\\headerlogo.jpg"));
			JLabel lbl_musicPlayer = new JLabel(new ImageIcon(myPicture));
			//JLabel lbl_musicPlayer = new JLabel(new ImageIcon(myPicture));
			add(lbl_musicPlayer, BorderLayout.NORTH);
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
		JPanel pnl_down = new JPanel();
		add(pnl_down, BorderLayout.SOUTH);
		pnl_down.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btn_play = new JButton("Play");
		pnl_down.add(btn_play);
		
		JButton btn_pause = new JButton("Pause");
		pnl_down.add(btn_pause);
		
		JButton btn_stop = new JButton("Stop");
		pnl_down.add(btn_stop);
		
		JPanel pnl_center = new JPanel();
		pnl_center.setBorder(null);
		add(pnl_center, BorderLayout.CENTER);
		pnl_center.setLayout(null);
		
		JPanel pnl_filename = new JPanel();
		pnl_filename.setBounds(0, 0, 450, 19);
		pnl_center.add(pnl_filename);
		pnl_filename.setLayout(new BorderLayout(0, 0));
		
		JLabel lbl_filename_title = new JLabel("Filename: ");
		lbl_filename_title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_filename_title.setFont(new Font("Tahoma", Font.BOLD, 15));
		pnl_filename.add(lbl_filename_title, BorderLayout.WEST);
		
		JLabel lbl_filename_name = new JLabel("name_of_the_file");
		lbl_filename_name.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_filename_name.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnl_filename.add(lbl_filename_name, BorderLayout.CENTER);
		
		JButton btn_changeFile = new JButton("Change file");
		btn_changeFile.setBounds(0, 183, 450, 23);
		btn_changeFile.setEnabled(false);
		pnl_center.add(btn_changeFile);
		
		JProgressBar pb_music = new JProgressBar();
		pb_music.setBounds(0, 60, 450, 94);
		pb_music.setStringPainted(true);
		
		pb_music.setForeground(new Color(238,90,9));
		pb_music.setValue(60);
		pnl_center.add(pb_music);

	}

}
