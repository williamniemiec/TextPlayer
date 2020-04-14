package controllers;

import java.awt.Component;
import java.io.File;
import javax.swing.JMenuItem;
import core.Controller;
import models.IOManager;
import models.parse.JFugueMusicParser;
import models.parse.Parser;
import views.HomeView;


/**
 * Controlador principal. Ser� o respons�vel pelo comportamento da tela inicial do programa.
 */
public class HomeController extends Controller 
{
	//-----------------------------------------------------------------------
	//		Atributos
	//-----------------------------------------------------------------------
	private HomeView homeView;
	private TextPlayerController textPlayerController;
	
	
	//-----------------------------------------------------------------------
	//		M�todos
	//-----------------------------------------------------------------------
	@Override
	public void run()
	{
		// Inicializa HomeView
		homeView = new HomeView(this, mainFrame);
		addView("HomeView", homeView);
		
		// Deixa janela do programa vis�vel
		mainFrame.setVisible(true);
	}

	/**
	 * Adiciona um {@link Component componente} na janela principal. 
	 * 
	 * @param name Nome do componente (� recomendado usar o mesmo nome da vari�vel)
	 * @param component Componente a ser adicionado
	 */
	public void addMainFrameComponent(String name, Component component)
	{
		addComponent(name, component);
	}
	
	/**
	 * Realiza o processamento de um arquivo e manda-o para o {@link TextPlayerController}.
	 * Ap�s a execu��o desse m�todo, ser� aberta a view {@link TextPlayerView}.
	 * 
	 * @param file Arquivo a ser processado
	 */
	public void parseFile(File file)
	{
		String parsedFile;
		Parser parser = new Parser(new JFugueMusicParser());
		
		// SE DER ALGUM ERRO, TRATAR AQUI
		parsedFile = parser.open(file).parse().get();
		
		String text = IOManager.extractText(file);
		
		textPlayerController = new TextPlayerController(parsedFile, text, file.getName());
		textPlayerController.run();
	}
	
	/**
	 * Atualiza os bot�es do menu que fica no topo da janela.
	 */
	public void updateMenuBar()
	{	
		((JMenuItem)getComponent("mb_ctrl_play")).setEnabled(false);
		((JMenuItem)getComponent("mb_ctrl_pause")).setEnabled(false);
		((JMenuItem)getComponent("mb_ctrl_stop")).setEnabled(false);
		((JMenuItem)getComponent("mb_file_close")).setEnabled(false);
	}
}
