package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Button;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class TextPlayer extends JFrame {

	private JPanel contentPane;
	final static String MUSICPANEL = "MUSICPANEL";
	final static String HOMEPANEL = "HOME";
	final static int WIDTH = 600;
	final static int HEIGHT = 400;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextPlayer frame = new TextPlayer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TextPlayer() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		setBounds(100, 100, WIDTH, HEIGHT);
		
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		
		JMenu mb_file = new JMenu("Arquivo");
		mb.add(mb_file);
		
		JMenuItem mb_file_open = new JMenuItem("Abrir");
		mb_file.add(mb_file_open);
		
		JMenuItem mb_file_close = new JMenuItem("Fechar");
		mb_file_close.setEnabled(false);
		mb_file.add(mb_file_close);
		
		JMenuItem btn_exit = new JMenuItem("Sair");
		mb_file.add(btn_exit);
		
		JMenu mnNewMenu = new JMenu("Controles");
		mb.add(mnNewMenu);
		
		JMenuItem mb_crl_play = new JMenuItem("Play");
		mb_crl_play.setEnabled(false);
		mnNewMenu.add(mb_crl_play);
		
		JMenuItem mb_crl_pause = new JMenuItem("Pause");
		mb_crl_pause.setEnabled(false);
		mnNewMenu.add(mb_crl_pause);
		
		JMenuItem mb_crl_stop = new JMenuItem("Stop");
		mb_crl_stop.setEnabled(false);
		mnNewMenu.add(mb_crl_stop);
		
		JMenu mn_about = new JMenu("Sobre");
		mb.add(mn_about);
		
		
		// contentPane <=> cards
		//contentPane = new JPanel();
		contentPane = new JPanel(new CardLayout());
		
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setTitle("Text Player");
		
		homePanel homePanel_ = new homePanel();
		contentPane.add(homePanel_, HOMEPANEL);
		
		// Add card
		MusicPlayer mp = new MusicPlayer();
		contentPane.add(mp, MUSICPANEL);
		
		// Troca card
		CardLayout cl = (CardLayout)contentPane.getLayout();
		//cl.show(contentPane, HOMEPANEL);
		cl.show(contentPane, MUSICPANEL);
		
		// Sera util para redimencionar logos
		System.out.println(this.getWidth());
		System.out.println(this.getHeight());
	}
}
