package views;

import java.awt.BorderLayout;
import java.awt.Color;
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
import models.Model;
import models.MusicPlayer;

public class TextPlayerView extends JPanel implements View 
{
	@SuppressWarnings("unused")
	private TextPlayerController textPlayerController;
	private JFrame frame;
	private JButton btn_ctrl_play;
	private JButton btn_ctrl_pause;
	private JButton btn_ctrl_stop;
	
	public TextPlayerView(TextPlayerController textPlayerController, JFrame frame)
	{
		this.textPlayerController = textPlayerController;
		this.frame = frame;
		
		make_panel();
		make_header();
		make_controls();
		make_centralPanel();
	}
	
	
	@Override
	public void close() 
	{
		
	}
	
	private void make_panel()
	{
		setBorder(null);
		setBounds(frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight());
		setLayout(new BorderLayout(0, 0));
	}
	
	private void make_header()
	{
		JPanel pnl_top = new JPanel();
		pnl_top.setBackground(new Color(64, 64, 64));
		FlowLayout flowLayout = (FlowLayout) pnl_top.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		
		// Logo
		try {
			BufferedImage myPicture;
			myPicture = ImageIO.read(new File(System.getProperty("user.dir")+"/src/content/images/musicPlayer/header_logo.jpg"));
			
			JLabel lbl_musicPlayer = new JLabel();
			pnl_top.add(lbl_musicPlayer, BorderLayout.NORTH);
			
			ImageIcon img = new ImageIcon(myPicture.getScaledInstance(frame.getWidth(), frame.getHeight()/3, Image.SCALE_SMOOTH));
			lbl_musicPlayer.setIcon(img);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		add(pnl_top, BorderLayout.NORTH);
	}
	
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
	
	private JButton make_btn_play(JPanel panel)
	{
		return make_btn_ctrl(panel, System.getProperty("user.dir")+"/src/content/images/musicPlayer/play.png");
	}
	
	private JButton make_btn_pause(JPanel panel)
	{
		return make_btn_ctrl(panel, System.getProperty("user.dir")+"/src/content/images/musicPlayer/pause.png");
	}
	
	private JButton make_btn_stop(JPanel panel)
	{
		return make_btn_ctrl(panel, System.getProperty("user.dir")+"/src/content/images/musicPlayer/stop.png");
	}
	
	private JButton make_btn_ctrl(JPanel panel, String filepath)
	{
		JButton btn_ctrl = new JButton();
		panel.add(btn_ctrl);
		try {
			BufferedImage myPicture = ImageIO.read(new File(filepath));
			ImageIcon img = new ImageIcon(myPicture.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			btn_ctrl.setEnabled(false);
			btn_ctrl.setIcon(img);
			btn_ctrl.setContentAreaFilled(false);
			btn_ctrl.setFocusPainted(true);
			btn_ctrl.setBorderPainted(false);
			btn_ctrl.setOpaque(false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return btn_ctrl;
	}
	
	private void make_centralPanel()
	{
		JPanel pnl_center = new JPanel();
		pnl_center.setBorder(null);
		pnl_center.setLayout(new BorderLayout(0, 0));
		add(pnl_center);
		
		JPanel pnl_center_center = new JPanel(new BorderLayout(0,0));
		pnl_center_center.setLayout(new BorderLayout(0, 0));
		pnl_center.add(pnl_center_center, BorderLayout.CENTER);
		
		make_fileInfo(pnl_center, BorderLayout.NORTH);
		make_btn_change(pnl_center_center, BorderLayout.NORTH);
		make_textArea(pnl_center_center, BorderLayout.CENTER);
		make_progressBar(pnl_center, BorderLayout.SOUTH);
	}
	
	private void make_fileInfo(JPanel panel, Object constraints)
	{
		JPanel pnl_filename = new JPanel();
		pnl_filename.setLayout(new BorderLayout(0, 0));
		
		JLabel lbl_filename_title = new JLabel("Filename: ");
		lbl_filename_title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_filename_title.setFont(new Font("Tahoma", Font.BOLD, 15));
		pnl_filename.add(lbl_filename_title, BorderLayout.WEST);
		
		JLabel lbl_filename_name = new JLabel(textPlayerController.getFilename());
		lbl_filename_name.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_filename_name.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnl_filename.add(lbl_filename_name, BorderLayout.CENTER);
		
		panel.add(pnl_filename, constraints);
	}
	
	private void make_textArea(JPanel panel, Object constraints)
	{
		JScrollPane scrollPane = new JScrollPane();
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);		
		textArea.setLineWrap(false);
		textArea.setText(textPlayerController.getText());
		textArea.setEditable(false);
		
		panel.add(scrollPane, constraints);
	}
	
	private void make_progressBar(JPanel panel, Object constraints)
	{
		JProgressBar pb_music = new JProgressBar();
		pb_music.setStringPainted(true);
		
		pb_music.setForeground(new Color(238,90,9));
		pb_music.setValue(60);
		panel.add(pb_music, constraints);
	}
	
	private void make_btn_change(JPanel panel, Object constraints)
	{
		JButton btn_changeFile = new JButton("Change file");
		btn_changeFile.setEnabled(true);
		
		//pnl_center_center.add(btn_changeFile, BorderLayout.NORTH);
		panel.add(btn_changeFile, constraints);
	}


	@Override
	public void update(Model model, Object data) {
		MusicPlayer mp = (MusicPlayer) model;
		
		// Atualiza update bar
		//mp.getMusicLength();
		//mp.getMusicPosition();
		
		// Atualiza botoes de controle
		//btn_ctrl_play.setEnabled(!mp.isPlaying());
		//btn_ctrl_pause.setEnabled(!mp.isPaused());
		//btn_ctrl_stop.setEnabled(!mp.isStopped());
		
		// Atualiza barra menu main frame
		textPlayerController.updateMenuBar(mp);
	}
}
