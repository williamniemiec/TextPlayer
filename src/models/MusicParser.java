package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while ((line = br.readLine()) != null) {
				// Problema: pode continuar na proxima linha
				// pega letra anterior ao o+ / o- tb para aumentar / diminuir ela
				// Tem q fazer contagem para ver qts o+ e qts o- tem para saber tam total da oitava
				p = Pattern.compile("[A-z]((O|o)\\+|(O|o)\\-)");
				m = p.matcher(line);
				
				// fica enquanto houver o+ / o- na linha
				while (m.find()) {
					// Conta o+
					p2 = Pattern.compile("(O|o)\\+");
					m2 = p.matcher(m.group());
					positiveOctaves = m2.groupCount();
					
					// Conta o-
					p2 = Pattern.compile("(O|o)\\-");
					m2 = p.matcher(m.group());
					negativeOctaves = m2.groupCount();
					
					// Pega letra da esq
					p2 = Pattern.compile("[A-z]");
					m2 = p.matcher(m.group());
					letter = m2.group(); // PROBLEMA: PD TER o+ / o- sem letra nenhuma
					
					line = line.replaceFirst("[A-z](O|o)\\+", setOctave(letter, positiveOctaves-negativeOctaves));
				}
				
				// B+ | B-
				p = Pattern.compile("((B|b)\\+)");
				m = p.matcher(line);
				
				while (m.find()) {
					line = line.replaceFirst("((B|b)\\+)", increaseBPM());
				}
				
				p = Pattern.compile("((B|b)\\-)");
				m = p.matcher(line);
				
				while (m.find()) {
					line = line.replaceFirst("((B|b)\\-)", decreaseBPM());
				}
				
				
				// ? | .
				p = Pattern.compile("\\.|\\?");
				m = p.matcher(line);
				
				while (m.find()) {
					line = line.replaceFirst("\\.|\\?", randomNote());
				}
				
				
				//O | o | I | i | U | u
				p = Pattern.compile("[A-z](O|o|I|i|U|u))");
				m = p.matcher(line);
				
				while (m.find()) {
					// Se for uma nota musical, repete ela
					if (isNote(m.group().charAt(0))) {
						line = line.replaceFirst("[A-z](O|o|I|i|U|u))", Character.toString(m.group().charAt(0)) + m.group().charAt(0));
					} else {
						line = line.replaceFirst("[A-z](O|o|I|i|U|u))", "  ");
					}
				
				}
				
				
				// + | -
				p = Pattern.compile("\\+");
				m = p.matcher(line);
				
				while (m.find()) {
					line = line.replaceFirst("+", increaseVolume());
				}
				
				
				p = Pattern.compile("\\-");
				m = p.matcher(line);
				
				while (m.find()) {
					line = line.replaceFirst("-", decreaseVolume());
				}
				
				
				// Separa as letras
				// _ INDICARÁ QUE FOI ADD ALGO NA LINHA (METODOS ANTERIORES. SE ACHAR _, SÓ CONTINUAR PROCURANDO APÓS ACHAR OUTRO)
				StringBuilder sb = new StringBuilder();
				char[] dividedLine = line.toCharArray();
				int length = dividedLine.length;
				char character;
				
				for (int i=0; i<length; i++) {
					character = dividedLine[i];
					
					if (character >= 'A' && character <= 'G' || character >= 'a' && character <= 'g') {
						sb.append(character+" ");
					} else if (character == ' ') {
						sb.append(" "+putDelay()+" ");
					} else if(character == '_') {
						do {
							i++;
						} while (dividedLine[i] != '_');
						
					}
				}
				
				// NL
				line = line + changeInstrument();
				
				
				// Salva linha resultante
				parsedFile.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(parsedFile);
		return parsedFile.toString();
	}
	
	private String randomNote()
	{
		int index = (int) (Math.random()*notes.length);
		
		return "I["+notes[index]+"]";
	}
	
	private String setOctave(String letter, int amountOctaves)
	{
		int defaultOctave = 5;
		
		defaultOctave += amountOctaves;
		
		if (defaultOctave >= 10) {
			return letter+"10";
		}
		
		if (defaultOctave <= 0) {
			return letter+"0";
		}
		
		return letter+"amountOctaves";
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
		return letter >= 'A' && letter <= 'G' || letter >= 'a' && letter <= 'g';
	}
	
	/**
	 * Aumenta BPM de 50 em 50 unidades
	 * @return
	 */
	private String increaseBPM()
	{
		String bpm_constant = "";
		
		if (bpm == 120) {
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
		
		if (bpm == 120) {
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
		if (2*volume >= 100)
			volume = 100;
		else
			volume *= 2;
		
		return ":CON(7, "+volume+")";
	}
	
	private String decreaseVolume()
	{
		if (volume == 0 || volume/2 <= 1)
			volume = 1;
		else
			volume /= 2;
		
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
