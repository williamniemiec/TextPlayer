package models.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JFugueMusicParser implements ParseType
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	//private Map<String, String> dictionary = new HashMap<>();
	//private Map<Integer, String> bpm_jfugue = new HashMap<>(); // sera inicializado via arquivo
	private int bpm = 120;
	private int volume = 50;
	private List<String> instruments = new ArrayList<>();
	private final char[] notes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
	
	
	{
		instruments = new ArrayList<>();
	}
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	/**
	 * Realiza o processamento da linha para o caso dos s�mbolos O+ | O- | o+ | o-.
	 * Ser� aumentada uma oitava se o s�mbolo for O+ | o+ e ser� diminu�da uma oitava
	 * se o s�mbolo for O- | o-.
	 * 
	 * @param line Linha do texto a ser processada
	 * @return Linha processada
	 */
	private String parseOPlusMinus(String line)
	{
		Pattern p = Pattern.compile("[A-Ga-g]((O|o)\\+|(O|o)\\-)+");
		Matcher m = p.matcher(line);
		Pattern p2;
		Matcher m2;
		int positiveOctaves, negativeOctaves;
		char letter;
		
		// fica enquanto houver o+ / o- na linha COM UMA LETRA ANTES
		// Tem q fazer contagem para ver qts o+ e qts o- tem para saber tam total da oitava
		while (m.find()) {
			System.out.println(m.group());
			
			// Conta o+
			p2 = Pattern.compile("(O|o)\\+");
			m2 = p2.matcher(m.group());
			positiveOctaves = (int) m2.results().count();
			
			// Conta o-
			p2 = Pattern.compile("(O|o)\\-");
			m2 = p2.matcher(m.group());
			negativeOctaves = (int) m2.results().count();		
			
			letter = m.group().charAt(0);
			
			line = line.replaceFirst("[A-Ga-g]((O|o)\\+|(O|o)\\-)+", setOctave(letter, positiveOctaves-negativeOctaves));	
		}	
		
		
		return line;
	}
	
	
	/**
	 * Realiza o processamento da linha para o caso dos s�mbolos B+ | B- | b+ | b-.
	 * Ser� aumentado o BPM em 50 unidades se o s�mbolo for B+ | b+ e ser� diminu�do
	 * tamb�m em 50 unidades se o s�mbolo for B- | b-.
	 * 
	 * @param line Linha do texto a ser processada
	 * @return Linha processada
	 */
	private String parseBPlusMinus(String line)
	{
		// Tem que ser em sequencia
		Pattern p = Pattern.compile("((B|b)\\+|(B|b)\\-)");
		Matcher m = p.matcher(line);
		
		Pattern bPlus = Pattern.compile("((B|b)\\+)");
		Pattern bMinus = Pattern.compile("((B|b)\\-)");
		
		while (m.find()) {
			line = line.replaceFirst("((B|b)\\+)", increaseBPM());
			line = line.replaceFirst("((B|b)\\-)", decreaseBPM());
		}
		
		return line;
	}
	
	/**
	 * Realiza o processamento da linha para o caso dos s�mbolos . e ?. Ao 
	 * encontrar esses s�mbolos, ser� tocada uma nota aleatoriamente.
	 * 
	 * @param line Linha do texto a ser processada
	 * @return Linha processada
	 */
	private String parseDotInterrogationMark(String line)
	{
		Pattern p = Pattern.compile("\\.|\\?");
		Matcher m = p.matcher(line);
		
		while (m.find()) {
			line = line.replaceFirst("\\.|\\?", "_"+randomNote()+"_");
		}
		
		return line;
	}
	
	/**
	 * Remove todos os acentos de uma string.
	 * 
	 * @param str String que ter� os acentos removidos
	 * @return String sem acentua��o
	 */
	private String removeAccentuation(String str)
	{
		str = str.replaceAll("[��������]", "a");
		str = str.replaceAll("[������]", "e");
		str = str.replaceAll("[������]", "i");
		str = str.replaceAll("[��������]", "o");
		str = str.replaceAll("[������]", "u");
		
		return str;
	}
	
	/**
	 * Realiza o processamento da linha para o caso dos s�mbolos + e -. Ao 
	 * encontrar + ser� aumentado o volume em 50 unidades. Por outro lado,
	 * se achar - o volume ser� decrescido de 50 unidades.
	 * 
	 * @param line Linha do texto a ser processada
	 * @return Linha processada
	 */
	private String parsePlusMinus(String line)
	{
		// Tem que ser em sequencia
		Pattern p = Pattern.compile("(\\+)|(\\-)");
		Matcher m = p.matcher(line);
		
		
		while (m.find()) {
			if (m.group().contains("+")) 
				line = line.replaceFirst("\\+", increaseVolume());
			else
				line = line.replaceFirst("\\-", decreaseVolume());
		}
		
		return line;
	}
	
	/**
	 * Retorna se um caracter � uma vogal e n�o � uma nota musical, isto �,
	 * se o caracter � 'I' ou 'O' ou 'U'.
	 * 
	 * @implNote N�o considera acentos
	 * @param letter Letra a ser verificada
	 * @return Se a letra � vogal e n�o � uma nota musical
	 */
	private boolean isVowelAndNotNote(char letter) 
	{
		letter = Character.toUpperCase(letter);
		
		return (letter == 'I' || letter == 'O' || letter == 'U');
	}
	
	/**
	 * Realiza o processamento da linha para o caso dos s�mbolos 'I', 'O' e 'U'.
	 * Ao encontrar esses s�mbolos, se o caracter anterior for uma nota musical,
	 * duplica esta; sen�o, realiza uma breve pausa / interrup��o.
	 * 
	 * @param line Linha do texto a ser processada
	 * @return Linha processada
	 */
	private String parseVogals(String line)
	{
		StringBuilder sb = new StringBuilder();
		char[] lineChar = line.toCharArray();
		
		
		for (int i=0; i<lineChar.length; i++) {
			// Ignora conchetes e tudo que tem dentro deles
			if (lineChar[i] == '_') {
				sb.append(lineChar[i]);
				i++;
				
				while (i<lineChar.length && lineChar[i] != '_') {
					sb.append(lineChar[i]);
					i++;
				}
				
				if (i<lineChar.length) {
					sb.append(lineChar[i]);
				}
			} else {
				
				if (i > 0 && isVowelAndNotNote(lineChar[i])) {
					if (isNote(lineChar[i-1])) {
						sb.append(lineChar[i-1]);
						sb.append(lineChar[i-1]);
					}
					
					else
						sb.append(putDelay());	// Coloca pausa
				} else if (i<lineChar.length)
					sb.append(lineChar[i]);
				}
			}

		return sb.toString();
	}
	
	/**
	 * Realiza o processamento da linha para o caso de espa�os no texto. Ao 
	 * encontrar um espa�o ser� feita uma breve pausa / interrup��o.
	 * 
	 * @param line Linha do texto a ser processada
	 * @return Linha processada
	 */
	private String parseSpaces(String line)
	{
		return line.replaceAll(" ", putDelay());
	}
	
	/**
	 * Dada uma linha j� processada, realiza o espa�amento dos termos dela, onde
	 * os termos representam comandos do JFugue.
	 * 
	 * @param line Linha do texto a ser processada
	 * @return Linha processada
	 */
	private String spaceTerms(String line)
	{
		char[] lineChar = line.toCharArray();
		StringBuilder sb = new StringBuilder();
		
		if (line.length() <= 1) { return line; }
		
		// SE ACHAR _, S� CONTINUAR PROCURANDO AP�S ACHAR OUTRO (IGNORA TUDO ENTRE _)
		for (int i=0; i<lineChar.length-1; i++) {
			if (lineChar[i] == '_') {
				i++;
				
				while (i<lineChar.length && lineChar[i] != '_') {
					sb.append(lineChar[i]);
					i++;
				}
				
				sb.append(" ");
				//i--;	// Pq vai ser incrementado no for
			} else {
				sb.append(lineChar[i]+" ");
			}
		}

		return sb.toString();
	}
	
	/**
	 * Remove todos os numeros de uma string.
	 * 
	 * @param str String que ter� todos os seus n�meros removidos
	 * @return String sem acentua��o
	 */
	private String removeNumbers(String str) 
	{
		return str.replaceAll("[0-9]", " ");
	}
	
	
	@Override
	public String parseFile(File file) 
	{
		String regex1Char = "[a-g\\s\\+\\-iou\\?\\n]+";
		String regex2Char = "((o\\+|o\\-|b\\+|b\\-)+)?";
		String line;
		Pattern p;
		Pattern p2;
		Matcher m;
		Matcher m2;
		List<String> oneChar = new ArrayList<>();
		List<String> twoChar = new ArrayList<>();
		int positiveOctaves;
		int negativeOctaves;
		String letter;
		StringBuilder parsedFile = new StringBuilder();
		
		initInstruments();
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
			while ((line = br.readLine()) != null) {
				// Problema: pode continuar na proxima linha
				// pega letra anterior ao o+ / o- tb para aumentar / diminuir ela
				
				
				// Remove numeros
				line = removeNumbers(line);
				
				// Remove acentos
				line = removeAccentuation(line);
				
				// Separa as letras
				// _ INDICAR� QUE FOI ADD ALGO NA LINHA (METODOS ANTERIORES. SE ACHAR _, S� CONTINUAR PROCURANDO AP�S ACHAR OUTRO)
				line = parseSpaces(line);
				
				// O+ | O-
				line = parseOPlusMinus(line);
				
				// B+ | B-
				line = parseBPlusMinus(line);
				
				// ? | .
				line = parseDotInterrogationMark(line);
				
				//O | o | I | i | U | u
				line = parseVogals(line);

				// + | -
				line = parsePlusMinus(line);

				// Space terms
				line = spaceTerms(line);
				
				// New line
				line = line + changeInstrument();
				
				// Saves parsed line
				parsedFile.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return parsedFile.toString();
	}
	
	/**
	 * Gera uma nota musical aleat�ria qualquer.
	 * 
	 * @return Nota musical
	 */
	private String randomNote()
	{
		int index = (int) (Math.random()*notes.length);
		
		return "I["+notes[index]+"]";
	}
	
	/**
	 * Gera comando do JFugue para mudar oitava de uma nota musical dependendo 
	 * da quantidade enviada.
	 * 
	 * @param note Letra musical que ter� aumento de oitava
	 * @param amountOctaves Numero de oitavas
	 * @return Termo do JFugue com as oitavas enviadas
	 */
	private String setOctave(char note, int amountOctaves)
	{
		int defaultOctave = 5;
		amountOctaves = defaultOctave + amountOctaves;
		String response = "_"+note+amountOctaves+"_";
		
		if (defaultOctave >= 10) {
			response = "_"+note+"10"+"_";
		}
		
		if (defaultOctave <= 0) {
			response = "_"+note+"0"+"_";
		}
		
		return response;
	}
	
	/**
	 * Carrega os instrumentos dispon�veis na aplica��o.
	 */
	private void initInstruments()
	{
		File file_inst = new File("config/instruments.tp");
		String line;
		
		try (BufferedReader br = new BufferedReader(new FileReader(file_inst))) {
			while ((line = br.readLine()) != null) {
				instruments.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Verifica se uma letra � uma nota musical.
	 * 
	 * @implNote N�o considera acentos
	 * @param letter Letra a ser verificada
	 * @return Se a letra � uma nota musical
	 */
	private boolean isNote(char letter)
	{
		return letter >= 'A' && letter <= 'G' || letter >= 'a' && letter <= 'g';
	}
	
	/**
	 * Aumenta BPM de 50 em 50 unidades.
	 * 
	 * @return Comando do JFugue para aumentar BPM 
	 */
	private String increaseBPM()
	{
		String bpm_constant = "";
		
		if (bpm >= 120) {
			bpm_constant = "Presto";
			bpm = 180;
		}
		else if(bpm == 70) {
			bpm_constant = "Allegro";
			bpm = 120;
		}
		
		return "_T["+bpm_constant+"]_";
	}
	
	/**
	 * Diminui BPM de 50 em 50 unidades.
	 * 
	 * @return Comando do JFugue para diminuir BPM 
	 */
	private String decreaseBPM()
	{
		String bpm_constant = "";
		
		if (bpm <= 120) {
			bpm_constant = "Andante";
			bpm = 70;
		}
		else if(bpm == 180) {
			bpm_constant = "Allegro";
			bpm = 120;
		}
		
		return "_T["+bpm_constant+"]_";
	}
	
	/**
	 * Aumenta volume dobrando o volume atual.
	 * 
	 * @return Comando do JFugue para aumentar o volume
	 */
	private String increaseVolume()
	{
		if (2*volume >= 100)
			volume = 100;
		else
			volume *= 2;
		
		return "_:CON(7, "+volume+")_";
	}
	
	/**
	 * Diminui volume dividindo o volume atual pela metade.
	 * 
	 * @return Comando do JFugue para diminuir o volume
	 */
	private String decreaseVolume()
	{
		if (volume == 0 || volume/2 <= 1)
			volume = 1;
		else
			volume /= 2;
		
		return "_:CON(7, "+volume+")_";
	}
	
	/**
	 * Gera uma pausa / interrup��o tempor�ria no JFugue.
	 * 
	 * @return Comando do JFugue para gerar uma pausa / interrup��o
	 */
	private String putDelay()
	{
		return "_@1_";
	}
	
	/**
	 * Gera um instrumento aleat�rio.
	 * 
	 * @return Comando do JFugue para tocar o instrumento musical gerado
	 */
	private String changeInstrument()
	{
		int num = (int) (Math.random()*instruments.size());
		
		return "I["+instruments.get(num)+"]";
	}
}
