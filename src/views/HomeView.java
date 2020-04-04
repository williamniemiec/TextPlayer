package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controllers.HomeController;


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
	private JFrame frame;
	private JPanel contentPane;
	private JMenuBar mb;
	
	//-----------------------------------------------------------------------
	//		Controller
	//-----------------------------------------------------------------------
	public HomeView(HomeController homeController, JPanel contentPane, JFrame frame)
	{
		this.homeController = homeController;
		this.contentPane = contentPane;
		this.frame = frame;
		
		
		make_frame();
		make_home();
		
		setVisible(true);
		//contentPane.add(this, "HomeView");
	}
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void close() 
	{
		
	}
	
	
	private void make_frame()
	{
		frame.setOpacity(1.0f);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(View.FRAME_X, View.FRAME_Y, View.FRAME_WIDTH, View.FRAME_HEIGHT);
		frame.setMinimumSize(new Dimension(View.FRAME_WIDTH, View.FRAME_HEIGHT));
		frame.setTitle("Text Player");

		make_menuBar();
	}
	
	private void make_menuBar()
	{
		mb = new JMenuBar();
		frame.setJMenuBar(mb);
		
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
				homeController.changeView("HomeView");
			}
		});
		
		JMenuItem btn_exit = new JMenuItem("Sair");
		mb_file.add(btn_exit);
	}
	
	private void make_mn_controller()
	{
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
	
	public void show_about()
	{
		JOptionPane.showMessageDialog(
				frame,
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
			frame, "Como você deseja abrir o arquivo?","Abrir arquivo", 
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
			JOptionPane.showMessageDialog(frame, "Erro! Arquivo não encontrado :/","Error",JOptionPane.ERROR_MESSAGE);
			filepath = JOptionPane.showInputDialog("Digite o caminho do arquivo");
		}
		
		if (filepath != null)
			homeController.parseFile(new File(filepath));
	}
	
	private void open_file_gui()
	{
		
		FileDialog fd = new FileDialog(frame, "Escolha um arquivo", FileDialog.LOAD);
		//fd.setDirectory(".");
		fd.setFile("*.txt");
		fd.setVisible(true);
		String filepath = fd.getDirectory()+fd.getFile();
		
		System.out.println(filepath);
		
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
			BufferedImage myPicture = ImageIO.read(new File(System.getProperty("user.dir")+"/src/content/images/home/logo.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture.getScaledInstance(View.FRAME_WIDTH, View.FRAME_HEIGHT, Image.SCALE_FAST)));
			add(picLabel);
		} catch (IOException e1) { }
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
