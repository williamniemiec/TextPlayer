package controllers;

import java.io.File;

import javax.swing.JMenuItem;

import core.Controller;
import models.IOManager;
import models.musicPlayer.JFugueMusicPlayer;
import models.musicPlayer.MusicPlayer;
import models.parse.JFugueMusicParser;
import models.parse.Parser;
import views.TextPlayerView;


/**
 * Responsável pelo comportamento da view {@link TextPlayerView}.
 */
public class TextPlayerController extends Controller 
{
	//-----------------------------------------------------------------------
	//		Atributos
	//-----------------------------------------------------------------------
	private String musicalText;
	private TextPlayerView textPlayerView;
	private MusicPlayer musicPlayer;
	private String originalText;
	private String filename;
	

	//-----------------------------------------------------------------------
	//		Construtor
	//-----------------------------------------------------------------------
	/**
	 * Controlador da view {@link TextPlayerView}.
	 * 
	 * @param musicalText Texto contendo comandos musicais obtidos através do {@link #originalText}
	 * @param originalText Texto antes de sofrer o processamento
	 * @param filename Nome do arquivo de texto
	 */
	public TextPlayerController(String musicalText, String originalText, String filename)
	{
		this.musicalText = musicalText;
		this.originalText = originalText;
		this.filename = filename;
	}
	
	
	//-----------------------------------------------------------------------
	//		Métodos
	//-----------------------------------------------------------------------
	@Override
	public void run() 
	{
		// Inicializa TextPlayerView
		textPlayerView = new TextPlayerView(this, mainFrame);
		
		//Atualiza os botões do menu que fica no topo da janela
		updateControlsMenu();
		
		// Cria o music player
		musicPlayer = new JFugueMusicPlayer(musicalText);
		musicPlayer.attach(textPlayerView);
		
		// Exibe a view TextPlayerView
		addView("TextPlayerView", textPlayerView);
		loadView("TextPlayerView");
	}
	
	/**
	 * Toca a musica que foi gerada a partir de um texto.
	 */
	public void play()
	{
		musicPlayer.play();
	}
	
	/**
	 * Pausa a música em reprodução.
	 */
	public void pause()
	{
		musicPlayer.pause();
	}
	
	/**
	 * Para a música em reprodução e posiciona o player para o início dela.
	 */
	public void stop()
	{
		musicPlayer.stop();
	}
	
	/**
	 * Seleciona outro arquivo para ser gerada música.
	 * 
	 * @param filepath Nome do arquivo
	 */
	public void changeFile(String filepath)
	{
		String parsedFile;
		Parser parser = new Parser(new JFugueMusicParser());
		File file = new File(filepath);
		
		// Realiza o processamento do arquivo
		parsedFile = parser.open(file).parse().get();
		
		// Carrega o arquivo processado no player
		musicPlayer.change(parsedFile);
		
		// Atualiza a view com as informações desse arquivo
		originalText = IOManager.extractText(file);
		this.filename = file.getName();
		textPlayerView.updateFileContent();
	}
	// será feito na view
	public void updateMenuBar(MusicPlayer mp)
	{	
		//((JMenuItem)getComponent("mb_ctrl_play")).setEnabled(!mp.isPlaying());
		//((JMenuItem)getComponent("mb_ctrl_pause")).setEnabled(!mp.isPaused());
		//((JMenuItem)getComponent("mb_ctrl_stop")).setEnabled(!mp.isStopped());
	}
	
	/**
	 * Atualiza os botões de controle do music player.
	 */
	private void updateControlsMenu()
	{
		((JMenuItem)getComponent("mb_file_close")).setEnabled(true);
		((JMenuItem)getComponent("mb_ctrl_play")).setEnabled(true);
	}
	
	
	//-----------------------------------------------------------------------
	//		Getters & Setters
	//-----------------------------------------------------------------------
	public String getText()
	{
		return this.originalText;
	}
	
	public void setText(String text)
	{
		this.originalText = text;
	}
	
	public TextPlayerView getView()
	{
		return textPlayerView;
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public void setFilename(String filename)
	{
		this.filename = filename;
	}
}
