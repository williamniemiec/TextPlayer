package models;

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

public class MusicParser implements ParseType
{
	private Map<String, String> dictionary = new HashMap<>();
	private Map<Integer, String> bpm_jfugue = new HashMap<>(); // sera inicializado via arquivo
	private int bpm = 120;
	private int volume = 50;
	private Instrument instrument;
	private List<String> instruments = new ArrayList<>();
	private char[] notes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
	
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	private String parseOPlusMinus(String line)
	{
		Pattern p = Pattern.compile("[A-z]((O|o)\\+|(O|o)\\-)");
		Matcher m = p.matcher(line);
		Pattern p2;
		Matcher m2;
		int positiveOctaves, negativeOctaves;
		char letter;
		
		// fica enquanto houver o+ / o- na linha
		while (m.find()) {
			// Conta o+
			p2 = Pattern.compile("(O|o)\\+");
			m2 = p2.matcher(m.group());
			System.out.println(m.start());
			System.out.println(m.end());
			positiveOctaves = (int) m2.results().count();
			
			// Conta o-
			p2 = Pattern.compile("(O|o)\\-");
			m2 = p2.matcher(m.group());
			negativeOctaves = (int) m2.results().count();
			
			// Pega letra da esq
			p2 = Pattern.compile("[A-z]");
			m2 = p2.matcher(m.group());
			
			if (m2.find()) {
				letter = m2.group().charAt(0); // PROBLEMA: PD TER o+ / o- sem letra nenhuma
				//line = line.replaceFirst("[A-z](O|o)\\+", setOctave(letter, positiveOctaves-negativeOctaves));
				String[] aux = line.split("[A-z]((O|o)\\+|(O|o)\\-)");
				StringBuilder sb = new StringBuilder();
				
				//System.out.println(letter+String.valueOf(setOctave(positiveOctaves-negativeOctaves)));
				sb.append(aux[0]);
				sb.append(letter+String.valueOf(setOctave(positiveOctaves-negativeOctaves)));
				
				
				for (int i=1; i<aux.length; i++) {
					sb.append(aux[i]);
				}
				/*
				for (var item : aux) {
					sb.append(item);
				}*/
				
				line = sb.toString();
				//line = String.copyValueOf(lineChar);// replaceFirst("[A-z](O|o)\\+", setOctave(letter, positiveOctaves-negativeOctaves));
			//} else {
			//	return line;
			}
		}
		
		return line;
	}
	
	
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
		/*
		Pattern p2 = Pattern.compile("((B|b)\\+)");
		Matcher m2 = p.matcher(line);
		
		while (m.find()) {
			line = line.replaceFirst("((B|b)\\+)", increaseBPM());
		}
		
		p = Pattern.compile("((B|b)\\-)");
		m = p.matcher(line);
		
		while (m.find()) {
			line = line.replaceFirst("((B|b)\\-)", decreaseBPM());
		}
		*/
		return line;
	}
	
	private String parseDotInterrogationMark(String line)
	{
		Pattern p = Pattern.compile("\\.|\\?");
		Matcher m = p.matcher(line);
		
		while (m.find()) {
			line = line.replaceFirst("\\.|\\?", randomNote());
		}
		
		return line;
	}
	
	private String removeAccentuation(String line)
	{
		line = line.replaceAll("[ÃãÀàÁáÂâ]", "a");
		line = line.replaceAll("[ÊêÈèÉé]", "e");
		line = line.replaceAll("[ÍíÌìÎî]", "i");
		line = line.replaceAll("[ÕôÓóÒòÕõ]", "o");
		line = line.replaceAll("[ÛûÚúÙù]", "u");
		
		return line;
	}
	
	
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
	
	private String parsePlus(String line)
	{
		Pattern p = Pattern.compile("\\+");
		Matcher m = p.matcher(line);
		
		while (m.find()) {
			line = line.replaceFirst("\\+", increaseVolume());
		}
		
		return line;
	}
	
	private String parseMinus(String line)
	{
		Pattern p = Pattern.compile("\\-");
		Matcher m = p.matcher(line);
		
		while (m.find()) {
			line = line.replaceFirst("\\-", decreaseVolume());
		}
		
		return line;
	}
	
	/**
	 * Retorna se um caracter é uma vogal.
	 * 
	 * @implNote Não considera acentos
	 * @param letter Letra a ser verificada
	 * @return Se a letra é vogal
	 */
	private boolean isVowel(char letter) 
	{
		letter = Character.toUpperCase(letter);
		
		return (letter == 'A' || letter == 'E' || letter == 'I' || letter == 'O' || letter == 'U');
	}
	
	private String parseVogals(String line)
	{
		char[] lineChar = line.toCharArray();
		
		for (int i=0; i<line.length(); i++) {
			// Ignora conchetes e tudo que tem dentro deles
			if (lineChar[i] == '[') {
				while (lineChar[i] != ']' && i<line.length()) {
					i++;
				}
			}
			
			if (i > 0 && isVowel(lineChar[i]) && isNote(lineChar[i-1])) {
				lineChar[i] = lineChar[i-1];
			}
		}
		
		return String.copyValueOf(lineChar);
	}
	
	private String spaceLetters(String line)
	{
		StringBuilder sb = new StringBuilder();
		char[] dividedLine = line.toCharArray();
		int length = dividedLine.length;
		char character;
		
		for (int i=0; i<length; i++) {
			character = dividedLine[i];
			
			if (character >= 'A' && character <= 'G' || character >= 'a' && character <= 'g') {
				sb.append(character+" ");
			} else if (character == ' ') {
				sb.append(putDelay()+" ");
			} else if(character == '_') {
				do {
					i++;
				} while (dividedLine[i] != '_');
				
			} else {
				sb.append(character+" ");
			}
		}
		
		return sb.toString();
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
				// Tem q fazer contagem para ver qts o+ e qts o- tem para saber tam total da oitava
				
				// Remove numeros
				
				// Remove acentos
				line = removeAccentuation(line);
				
				// O+ | O-
				line = parseOPlusMinus(line);
				
				// B+ | B-
				line = parseBPlusMinus(line);
				
				// ? | .
				line = parseDotInterrogationMark(line);
				
				//O | o | I | i | U | u
				line = parseVogals(line);

				// + | -
				//line = parsePlus(line);
				//line = parseMinus(line);
				line = parsePlusMinus(line);

				
				// Separa as letras
				// _ INDICARÁ QUE FOI ADD ALGO NA LINHA (METODOS ANTERIORES. SE ACHAR _, SÓ CONTINUAR PROCURANDO APÓS ACHAR OUTRO)
				//line = spaceLetters(line);
				
				// NL
				//line = line + changeInstrument();
				
				// Salva linha resultante
				parsedFile.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return parsedFile.toString();
	}
	
	private String randomNote()
	{
		int index = (int) (Math.random()*notes.length);
		
		return "I["+notes[index]+"]";
	}
	
	private int setOctave(int amountOctaves)
	{
		int defaultOctave = 5;
		amountOctaves = defaultOctave + amountOctaves;
		
		if (defaultOctave >= 10) {
			return 10;
		}
		
		if (defaultOctave <= 0) {
			return 0;
		}
		
		return amountOctaves;
	}
	
	private void initInstruments()
	{
		File file_inst = new File("config/instruments.tp");
		String line;
		
		try (BufferedReader br = new BufferedReader(new FileReader(file_inst))) {
			while ((line = br.readLine()) != null) {
				instruments.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private boolean isNote(char letter)
	{
		//System.out.println("l: "+letter);
		return letter >= 'A' && letter <= 'G' || letter >= 'a' && letter <= 'g';
	}
	
	/**
	 * Aumenta BPM de 50 em 50 unidades
	 * @return
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
		
		return "T["+bpm_constant+"]";
	}
	
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
		
		return "T["+bpm_constant+"]";
	}
	
	private String increaseVolume()
	{
		//System.out.println("volume aum antes: "+volume);
		if (2*volume >= 100)
			volume = 100;
		else
			volume *= 2;
		//System.out.println("volume aum dps: "+volume);
		return ":CON(7, "+volume+")";
	}
	
	private String decreaseVolume()
	{
		//System.out.println("volume dim antes: "+volume);
		if (volume == 0 || volume/2 <= 1)
			volume = 1;
		else
			volume /= 2;
		//System.out.println("volume dim dps: "+volume);
		return ":CON(7, "+volume+")";
	}
	
	private String putDelay()
	{
		return "@1";
	}
	
	/**
	 * Gera um instrumento aleatório.
	 * 
	 * @return Instrumento musical
	 */
	private String changeInstrument()
	{
		int num = (int) (Math.random()*instruments.size());
		
		return "I["+instruments.get(num)+"]";
	}
}
