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
import models.musicPlayer.MusicPlayer;


/**
 * View associated with HomeController. It will be responsible for program's 
 * main screen view.
 */
public class HomeView extends JPanel implements View
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	@SuppressWarnings("unused")
	private HomeController homeController;
	private JFrame mainFrame;
	private JMenuBar mb;
	private JLabel home_background;
	private BufferedImage home_background_file;
	private final static int MAIN_FRAME_WIDTH = 800;
	private final static int MAIN_FRAME_HEIGHT = 500;
	private final static int MAIN_FRAME_X = 100;
	private final static int MAIN_FRAME_Y = 100;
	
	
	//-----------------------------------------------------------------------
	//		Constructor
	//-----------------------------------------------------------------------
	public HomeView(HomeController homeController, JFrame mainFrame)
	{
		this.homeController = homeController;
		this.mainFrame = mainFrame;
		
		make_mainFrame();
		make_home();
		
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
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void update(Model model, Object data) 
	{
		//homeController.updateMenuBar();
		System.out.println("update");
	}
	
	private void make_mainFrame()
	{
		mainFrame.setOpacity(1.0f);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(MAIN_FRAME_X, MAIN_FRAME_Y, MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
		mainFrame.setMinimumSize(new Dimension(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT));
		mainFrame.setTitle("Text Player");

		make_menuBar();
	}
	
	private void make_menuBar()
	{
		mb = new JMenuBar();
		mainFrame.setJMenuBar(mb);
		
		make_mn_file();
		make_mn_controller();
		make_mn_about();		
	}
	
	private void make_mn_file()
	{
		JMenu mb_file = new JMenu("Arquivo");
		mb.add(mb_file);
		
		JMenuItem mb_file_open = new JMenuItem("Abrir");
		mb_file.add(mb_file_open);
		mb_file_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ask_file_open();
			}
		});
		
		JMenuItem mb_file_close = new JMenuItem("Fechar");
		mb_file_close.setEnabled(false);
		mb_file.add(mb_file_close);
		mb_file_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Controller.loadView("HomeView");
			}
		});
		homeController.addMainFrameComponent("mb_file_close", mb_file_close);
		
		JMenuItem btn_file_exit = new JMenuItem("Sair");
		mb_file.add(btn_file_exit);
		btn_file_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.dispose();
			}
		});
	}
	
	private void make_mn_controller()
	{
		JMenu mn_ctrl = new JMenu("Controles");
		mb.add(mn_ctrl);
		
		JMenuItem mb_ctrl_play = new JMenuItem("Play");
		mb_ctrl_play.setEnabled(false);
		mn_ctrl.add(mb_ctrl_play);
		homeController.addMainFrameComponent("mb_ctrl_play", mb_ctrl_play);
		
		JMenuItem mb_ctrl_pause = new JMenuItem("Pause");
		mb_ctrl_pause.setEnabled(false);
		mn_ctrl.add(mb_ctrl_pause);
		homeController.addMainFrameComponent("mb_ctrl_pause", mb_ctrl_pause);
		
		JMenuItem mb_ctrl_stop = new JMenuItem("Stop");
		mb_ctrl_stop.setEnabled(false);
		mn_ctrl.add(mb_ctrl_stop);
		homeController.addMainFrameComponent("mb_ctrl_stop", mb_ctrl_stop);
	}
	
	private void make_mn_about()
	{
		JButton mn_about = new JButton("Sobre");
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
	
	private void show_about()
	{
		JOptionPane.showMessageDialog(
				mainFrame,
				"Versão 1.0\n\nFeito por:\n"
				+ "-> Matheus Hiroyuki Suwa Moura \n"
				+ "-> William Niemiec", "Sobre",
				JOptionPane.INFORMATION_MESSAGE
		);
	}
	
	private void ask_file_open()
	{
		Object[] options = {"GUI", "Texto"};
		int op = JOptionPane.showOptionDialog(
			mainFrame, "Como você deseja abrir o arquivo?","Abrir arquivo", 
			-1, JOptionPane.QUESTION_MESSAGE, null, options, options[0]
		);
		
		if (op == 0)
			open_file_gui();
		else if (op == 1)
			open_file_text();
	}
	
	private void open_file_text()
	{
		String filepath = JOptionPane.showInputDialog("Digite o caminho do arquivo");
		
		while (filepath != null && !fileExists(filepath)) {
			JOptionPane.showMessageDialog(mainFrame, "Erro! Arquivo não encontrado :/","Error",JOptionPane.ERROR_MESSAGE);
			filepath = JOptionPane.showInputDialog("Digite o caminho do arquivo");
		}
		
		if (filepath != null)
			homeController.parseFile(new File(filepath));
	}
	
	private void open_file_gui()
	{
		FileDialog fd = new FileDialog(mainFrame, "Escolha um arquivo", FileDialog.LOAD);
		fd.setDirectory("./");
		fd.setFile("*.txt");
		fd.setVisible(true);
		
		if (fd.getFile() == null) { return; }
		
		String filepath = fd.getDirectory()+fd.getFile();
		
		homeController.parseFile(new File(filepath));
	}
	
	private boolean fileExists(String filepath)
	{
		return new File(filepath).exists();
	}
	
	
	private void make_home()
	{
		setLayout(new BorderLayout(0, 0));

		make_background();
		make_btn_openFile();
	}
	
	private void make_background()
	{
		try {
			home_background_file = ImageIO.read(new File(System.getProperty("user.dir")+"/src/content/images/home/logo.jpg"));
			home_background = new JLabel(new ImageIcon(home_background_file.getScaledInstance(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT, Image.SCALE_FAST)));
			add(home_background);
		} catch (IOException e1) { e1.printStackTrace();}
	}
	
	private void resize_background(int w, int h)
	{
		home_background.setIcon(new ImageIcon(home_background_file.getScaledInstance(w, h, Image.SCALE_FAST)));
	}
	
	
	private void make_btn_openFile()
	{
		JButton btn_openFile = new JButton("Abrir arquivo");
		add(btn_openFile, BorderLayout.SOUTH);
		btn_openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ask_file_open();
			}
		});
	}
}
