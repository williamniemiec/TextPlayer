package gui;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class homePanel extends JPanel {
	final static int WIDTH = 600;
	final static int HEIGHT = 400;
	
	/**
	 * Create the panel.
	 */
	public homePanel() {
		setLayout(new BorderLayout(0, 0));
		
		JOptionPane.showMessageDialog(this, "Erro! Arquivo não encontrado :/","Error",JOptionPane.ERROR_MESSAGE);
		
		// Logo
		try {
			BufferedImage myPicture;
			//myPicture = ImageIO.read(new File("C:\\Users\\William Niemiec\\Documents\\GitHub\\TextPlayer\\media\\logo\\TextPlayer_logo.jpg"));
			myPicture = ImageIO.read(new File("C:\\Users\\William Niemiec\\Documents\\GitHub\\TextPlayer\\logo.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_FAST)));
			add(picLabel);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		JButton btn_openFile = new JButton("Abrir arquivo");
		btn_openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FileDialog fd = new FileDialog((Frame)null, "Choose a file", FileDialog.LOAD);
				fd.setDirectory(".");
				fd.setFile("*.txt");
				fd.setVisible(true);
				String filename = fd.getFile();
				System.out.println(new File(filename).getAbsolutePath());
				
				Object[] options = {"GUI", "Texto" };
				int x = JOptionPane.showOptionDialog(null, "Como você deseja abrir o arquivo?","Abrir arquivo", -1, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				
				//int x = JOptionPane.showConfirmDialog(null, "Como você deseja abrir o arquivo?", "Abrir arquivo", JOptionPane.YES_NO_OPTION);
				System.out.println("x: "+x);
				
				
				String filepath = JOptionPane.showInputDialog("Digite o caminho do arquivo");
				System.out.println(filepath);
			}
		});
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setHorizontalAlignment(SwingConstants.TRAILING);
		btnNewButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		panel.add(btnNewButton);
		panel.add(btnNewButton_1);
		panel.
		add(btn_openFile);
		
		JLabel label = new JLabel("");
		panel.add(label);

	}

}
