package gui;

import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class homePanel extends JPanel {
	final static int WIDTH = 600;
	final static int HEIGHT = 400;
	
	/**
	 * Create the panel.
	 */
	public homePanel() {
		setLayout(new BorderLayout(0, 0));
		
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
		
		/*
		JLabel lbl_title = new JLabel("TextPlayer");
		lbl_title.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		add(lbl_title);
		*/
		
		JButton btn_openFile = new JButton("Abrir arquivo");
		btn_openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "clicou");
			}
		});
		add(btn_openFile, BorderLayout.SOUTH);

	}

}
