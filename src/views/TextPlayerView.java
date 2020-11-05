package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
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
import models.io.input.dialog.FileInputDialog;
import models.io.input.dialog.FileInputType;
import models.io.input.dialog.TextInputDialog;
import models.musicPlayer.MusicPlayer;


/**
 * Responsible for music player view.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public class TextPlayerView extends JPanel implements View 
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private static final long serialVersionUID = 100L;
	private static final ResourceBundle lang = 
			ResourceBundle.getBundle("resources.lang.textplayer.textplayer");
	private transient TextPlayerController textPlayerController;
	private JFrame frame;
	private JLabel lblFilename;
	private JTextArea textArea;
	private JProgressBar pbMusic;
	private JButton btnCtrlPlay;
	private JButton btnCtrlPause;
	private JButton btnCtrlStop;
	

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
	public TextPlayerView(TextPlayerController textPlayerController, JFrame frame) throws IOException
	{
		if (textPlayerController == null)
			throw new IllegalArgumentException("Controller cannot be null");
		
		if (frame == null)
			throw new IllegalArgumentException("Frame cannot be null");

		this.textPlayerController = textPlayerController;
		this.frame = frame;
		
		makePanel();
		makeHeader();
		makeControls();
		makeCenterPanel();
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
		pbMusic.setValue((int)(musicPosition/musicLength));
				
		// Updates control player buttons
		btnCtrlPlay.setEnabled(!mp.isPlaying());
		btnCtrlPause.setEnabled(!mp.isPaused());
		btnCtrlStop.setEnabled(!mp.isStopped());
		
		// Updates menu bar items
		textPlayerController.setMenuBarItemStatus("mb_ctrl_playPause", mp.isPlaying() || mp.isPaused());
		textPlayerController.setMenuBarItemStatus("mb_ctrl_stop", !mp.isStopped());
		textPlayerController.setMenuBarItemStatus("mb_file_close", true);
	}
	
	/**
	 * Updates the section that displays the text data.
	 */
	public void updateContent() 
	{
		String content = textPlayerController.getText()
				.stream()
				.map((line) -> line + "\n")
				.collect(Collectors.joining(""));
		
		
		textArea.setText(content);
		textArea.setCaretPosition(0);
		lblFilename.setText(textPlayerController.getFilename());
	}
	
	/**
	 * Creates {@link JPanel} of the view.
	 */
	private void makePanel()
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
	private void makeHeader() throws IOException
	{
		JPanel pnlTop = new JPanel();
		FlowLayout flowLayout;
		BufferedImage imgHeader;
		JLabel lblMusicPlayer;
		ImageIcon img;
		
		
		pnlTop.setBackground(new Color(64, 64, 64));
		flowLayout = (FlowLayout) pnlTop.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		
		// Places the banner in the header
		try {
			imgHeader = ImageIO.read(new File(System.getProperty("user.dir")+"/src/assets/img/player/header_logo.jpg"));
		}
		catch (IOException e) {
			throw new IOException("Error while reading header image");
		}
		
		lblMusicPlayer = new JLabel();
		pnlTop.add(lblMusicPlayer, BorderLayout.NORTH);
		
		img = new ImageIcon(imgHeader.getScaledInstance(frame.getWidth(), frame.getHeight()/3, Image.SCALE_SMOOTH));
		lblMusicPlayer.setIcon(img);
		
		// Adds banner to the view
		add(pnlTop, BorderLayout.NORTH);
	}
	
	/**
	 * Creates player control buttons.
	 * 
	 * @throws		IOException If an error occurs during reading control icon   
	 */
	private void makeControls() throws IOException
	{
		JPanel pnlDown = new JPanel();
		
		
		add(pnlDown, BorderLayout.SOUTH);
		pnlDown.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		makeCtrlBtnPlay(pnlDown);
		makeCtrlBtnPause(pnlDown);
		makeCtrlBtnStop(pnlDown);
	}
	
	/**
	 * Creates play control button.
	 * 
	 * @param		panel Panel to which the button will be placed
	 * 
	 * @throws		IOException If an error occurs during reading play control
	 * icon
	 */
	private void makeCtrlBtnPlay(JPanel panel) throws IOException
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		btnCtrlPlay = makeCtrlBtn(
				panel, 
				System.getProperty("user.dir")+"/src/assets/img/player/play.png"
		);
		
		btnCtrlPlay.addActionListener((event) ->
				textPlayerController.play()
		);
		
		btnCtrlPlay.setEnabled(true);
	}
	
	/**
	 * Creates pause control button.
	 * 
	 * @param		panel Panel to which the button will be placed
	 * 
	 * @throws		IOException If an error occurs during reading pause control
	 * icon
	 */
	private void makeCtrlBtnPause(JPanel panel) throws IOException
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		btnCtrlPause = makeCtrlBtn(
				panel, 
				System.getProperty("user.dir")+"/src/assets/img/player/pause.png"
		);
		
		btnCtrlPause.addActionListener(event ->	textPlayerController.pause());
	}
	
	/**
	 * Creates stop control button.
	 * 
	 * @param		panel Panel to which the button will be placed

	 * @throws		IOException If an error occurs during reading stop control
	 * icon
	 */
	private void makeCtrlBtnStop(JPanel panel) throws IOException
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		btnCtrlStop = makeCtrlBtn(
				panel, 
				System.getProperty("user.dir")+"/src/assets/img/player/stop.png"
		);
		
		btnCtrlStop.addActionListener(event -> textPlayerController.stop());
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
	private JButton makeCtrlBtn(JPanel panel, String filepath) throws IOException
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		if ((filepath == null) || filepath.isBlank())
			throw new IllegalArgumentException("Filepath cannot be empty");
		
		JButton btnCtrl = new JButton();
		BufferedImage myPicture;
		ImageIcon img;
		
		
		panel.add(btnCtrl);
		
		try {
			myPicture = ImageIO.read(new File(filepath));
		}
		catch (IOException e) {
			throw new IOException("Error while reading control icon");
		}
		
		img = new ImageIcon(myPicture.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		btnCtrl.setEnabled(false);
		btnCtrl.setIcon(img);
		btnCtrl.setContentAreaFilled(false);
		btnCtrl.setFocusPainted(true);
		btnCtrl.setBorderPainted(false);
		btnCtrl.setOpaque(false);
		
		return btnCtrl;
	}
	
	/**
	 * Creates center area.
	 */
	private void makeCenterPanel()
	{
		JPanel pnlCenter;
		JPanel pnlCenterCenter;
		JPanel pnlOptions;
		
		
		// Panel responsible for the central section of the view
		pnlCenter = new JPanel();
		pnlCenter.setBorder(null);
		pnlCenter.setLayout(new BorderLayout(0, 0));
		add(pnlCenter);
		
		// Panel that will be in the center of the center section panel
		pnlCenterCenter = new JPanel(new BorderLayout(0,0));
		pnlCenterCenter.setLayout(new BorderLayout(0, 0));
		pnlCenter.add(pnlCenterCenter, BorderLayout.CENTER);
		
		pnlOptions = makePnlOptions();
		pnlCenterCenter.add(pnlOptions, BorderLayout.NORTH);
		
		makeFileInfo(pnlCenter, BorderLayout.NORTH);
		makeTextArea(pnlCenterCenter, BorderLayout.CENTER);
		makeProgressBar(pnlCenter, BorderLayout.SOUTH);
	}
	
	/**
	 * Creates options panel.
	 * 
	 * @return		Options panel
	 */
	private JPanel makePnlOptions()
	{
		JPanel pnlOptions;
		JPanel pnlOptionsTop;
		JPanel pnlOptionsBottom;
		
		
		pnlOptionsTop = new JPanel();
		pnlOptionsTop.setLayout(new GridLayout(0, 2, 0, 0));
		
		pnlOptionsBottom = new JPanel();
		pnlOptionsBottom.setLayout(new GridLayout(0, 1, 0, 0));
		
		pnlOptions = new JPanel();
		pnlOptions.setLayout(new BorderLayout(0, 0));
		pnlOptions.add(pnlOptionsTop, BorderLayout.NORTH);
		pnlOptions.add(pnlOptionsBottom, BorderLayout.SOUTH);
		
		makeBtnTextEntry(pnlOptionsTop);
		makeBtnChangeFile(pnlOptionsTop);
		makeBtnExportFileMusic(pnlOptionsBottom);
		
		return pnlOptions;
	}
	
	/**
	 * Creates the section that will display filename.
	 * 
	 * @param		panel Panel to which the section will be placed
	 * @param		constraints Position where this section will be added to 
	 * the panel
	 */
	private void makeFileInfo(JPanel panel, Object constraints)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		if (constraints == null)
			throw new IllegalArgumentException("Constraints cannot be null");
		
		JPanel pnlFilename = new JPanel();
		JLabel lblFilenameTitle;
		
		
		pnlFilename.setLayout(new BorderLayout(0, 0));
		
		lblFilenameTitle = new JLabel(lang.getString("FILENAME") + ": ");
		lblFilenameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilenameTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
		pnlFilename.add(lblFilenameTitle, BorderLayout.WEST);
		
		lblFilename = new JLabel(textPlayerController.getFilename());
		lblFilename.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilename.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnlFilename.add(lblFilename, BorderLayout.CENTER);
		
		panel.add(pnlFilename, constraints);
	}
	
	/**
	 * Creates the section that will display file content.
	 * 
	 * @param		panel Panel to which the section will be placed
	 * @param		constraints Position where this section will be added to 
	 * the panel
	 */
	private void makeTextArea(JPanel panel, Object constraints)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		if (constraints == null)
			throw new IllegalArgumentException("Constraints cannot be null");
		
		JScrollPane scrollPane = new JScrollPane();
		String text = textPlayerController.getText()
				.stream()
				.map(line -> line + "\n")
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
	private void makeProgressBar(JPanel panel, Object constraints)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		if (constraints == null)
			throw new IllegalArgumentException("Constraints cannot be null");
		
		pbMusic = new JProgressBar();
		pbMusic.setStringPainted(true);
		
		pbMusic.setForeground(new Color(238,90,9));
		pbMusic.setValue(0);
		panel.add(pbMusic, constraints);
	}
	
	/**
	 * Creates the section that will contain change file button.
	 * 
	 * @param		panel Panel that the button will be added
	 */
	private void makeBtnChangeFile(JPanel panel)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		JButton btnOpenFile = new JButton(lang.getString("FILE_OPEN"));
		

		panel.add(btnOpenFile);
		
		btnOpenFile.addActionListener(event ->
				textPlayerController.changeText(new FileInputDialog(frame, "txt", FileInputType.LOAD))
		);
	}
	
	/**
	 * Creates the section that will contain export file button.
	 * 
	 * @param		panel Panel that the button will be added
	 */
	private void makeBtnExportFileMusic(JPanel panel)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		JButton btnOpenFile = new JButton(lang.getString("EXPORT_TO_MIDI"));
		

		panel.add(btnOpenFile);
		
		btnOpenFile.addActionListener(event ->
				textPlayerController.exportMusicFile()
		);
	}
	
	/**
	 * Creates text entry button.
	 * 
	 * @param		panel Panel that the button will be added
	 */
	private void makeBtnTextEntry(JPanel panel)
	{
		if (panel == null)
			throw new IllegalArgumentException("Panel cannot be null");
		
		JButton btnTextEntry = new JButton(lang.getString("TEXT_ENTRY"));
		
		
		panel.add(btnTextEntry);
		btnTextEntry.setFocusPainted(false);
		btnTextEntry.addActionListener(event -> 
				textPlayerController.changeText(new TextInputDialog(frame))
		);
	}
}
