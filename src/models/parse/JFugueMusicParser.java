package models.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.music.JFugueMusicalNote;


/**
 * Responsible for processing text files in a format that JFugue accepts. 
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public class JFugueMusicParser implements ParseType
{
	//-------------------------------------------------------------------------
	//		Attributes
	//-------------------------------------------------------------------------
	private int bpm = 120;
	private int volume = 50;
	private List<String> instruments = new ArrayList<>();
	//private final char[] notes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
	//private final Map<Character, Integer> octaves = new HashMap<>();
	private final List<JFugueMusicalNote> musicalNotes = new ArrayList<>();

	
	//-------------------------------------------------------------------------
	//		Initialization blocks
	//-------------------------------------------------------------------------
	{
		musicalNotes.addAll(List.of(
			new JFugueMusicalNote('A', 9),
			new JFugueMusicalNote('B', 11),
			new JFugueMusicalNote('C', 0),
			new JFugueMusicalNote('D', 2),
			new JFugueMusicalNote('E', 4),
			new JFugueMusicalNote('F', 5),
			new JFugueMusicalNote('G', 7)
		));
	}
	
	
	//-------------------------------------------------------------------------
	//		Methods
	//-------------------------------------------------------------------------
	/**
	 * {@inheritDoc}
	 * Process txt file according to the following criteria:
	 * <table width='100%' border='1'>
	 * 	<tr>	
	 * 		<th>Text</th>
	 * 		<th>Action</th>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>Letter A or a</td>
	 * 		<td>Musical note 'La'</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>Letter B or b</td>
	 * 		<td>Musical note 'Si'</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>Letter C or c</td>
	 * 		<td>Musical note 'Do'</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>Letter D or d</td>
	 * 		<td>Musical note 'Re'</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>Letter E or e</td>
	 * 		<td>Musical note 'Mi'</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>Letter F or f</td>
	 * 		<td>Musical note 'Fa'</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>Letter G or g</td>
	 * 		<td>Musical note 'Sol'</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>Space</td>
	 * 		<td>Increases volume to double the current volume or sets default 
	 * 		volume if it exceed the limit</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>a, b, c, d, e, f, g</td>
	 * 		<td>If the previous character was a musical note (from A to G) 
	 * 		repeats the note; otherwise, silence or pause</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>? or .</td>
	 * 		<td>Increases one octave or sets default octave if it exceed the limit</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>!</td>
	 * 		<td>Changes current instrument to Agogo (General MIDI #144)</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>O, o, I, i, U, u</td>
	 * 		<td>Changes current instrument to Harpsichord) (General MIDI #7)</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>All consonants except those that are notes</td>
	 * 		<td>If the previous character was a musical note (from A to G) 
	 * 		repeats the note; otherwise, silence or pause</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>Number</td>
	 * 		<td>Change instrument to General MIDI instrument whose number is 
	 * 		equal to the value of the CURRENT instrument + digit value</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>NL (new line)</td>
	 * 		<td>Changes current instrument to Tubular Bells (General MIDI #15)</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>;</td>
	 * 		<td>Changes current instrument to Pan Flutes (General MIDI #76)</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>,</td>
	 * 		<td>Changes current instrument to Church Organ (General MIDI #20)</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>Others</td>
	 * 		<td>If the previous character was a musical note (from A to G) 
	 * 		repeats the note; otherwise, silence or pause</td>
	 * 	</tr>
	 * </table>
	 * 
	 * @param		content Content to be processed
	 * 
	 * @return		Processed content
	 * 
	 * @throws		IllegalArgumentException If content is null or empty
	 * 
	 * @see			ParseType#parseFile(File)
	 */
	@Override
	public List<String> parse(List<String> content)
	{
		if (content == null || content.size() == 0)
			throw new IllegalArgumentException("Content cannot be empty");
		
		List<String> processedContent = new ArrayList<>();

		
		initInstruments();
		// Problema: pode continuar na proxima linha
		// pega letra anterior ao o+ / o- tb para aumentar / diminuir ela
		// _ INDICARÁ QUE FOI ADD ALGO NA LINHA (METODOS ANTERIORES. SE ACHAR _, SÓ CONTINUAR PROCURANDO APÓS ACHAR OUTRO)
		// USAR TEMPLATE PATTERN NAS FUNÇÕES
		
		
		for (String line : content) {
			line = removeAccentuation(line);
			line = parseAtoG(line);
			line = parseSpace(line);	
			line = parseExclamationMark(line);
			line = parseIOU(line);
			line = parseConsonant(line);
			line = parseNumber(line);
			line = parseInterrogationMark(line);	
			line = parseDot(line);
			line = parseSemicolon(line);
			line = parseComma(line);
			line = parseElse(line);
			line = spaceTerms(line);
			line = parseNewLine(line);
			
			// Saves processed line
			processedContent.add(line);
		}
		
		return processedContent;
	}
	
	/**
	 * If the previous character was a musical note (from A to G) repeats the
	 * note; otherwise, silence or pause.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseAtoG(String line)
	{
		if (line == null)
			throw new IllegalArgumentException("Line cannot be null");
		
		String regex_aTog = "[abcdefg]";
		Pattern p = Pattern.compile(regex_aTog);
		Matcher m = p.matcher(line);
		
		StringBuilder sb = new StringBuilder();
		String character;
		boolean inJFugueCommand = false;
		

		for (int i=1; i<line.length(); i++) {
			character = line.charAt(i) + "";
			
			if ((line.charAt(i) == '_')) {
				inJFugueCommand = !inJFugueCommand;
			}
			else if (!inJFugueCommand) {
				
				if (character.matches(regex_aTog)) {
					if (isNote(line.charAt(i-1))) {
						character = increaseVolume();
					}
				}	
			}
			
			sb.append(character);
		}
		
		return sb.toString();
	}
	
	/**
	 * Increases volume to double the current volume or sets default volume if
	 * it exceed the limit.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseSpace(String line)
	{
		StringBuilder sb = new StringBuilder();
		String character;
		boolean inJFugueCommand = false;
		

		for (int i=0; i<line.length(); i++) {
			character = line.charAt(i) + "";
			
			if ((line.charAt(i) == '_')) {
				inJFugueCommand = !inJFugueCommand;
			}
			else if (!inJFugueCommand) {
				
				if (character.equals(" ")) {
					character = increaseVolume();
				}
				
			}
			
			sb.append(character);				
		}
		
		return sb.toString();
	}
	
	/**
	 * Changes current instrument to Agogo) (General MIDI #114).
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseExclamationMark(String line)
	{
		StringBuilder sb = new StringBuilder();
		String character;
		boolean inJFugueCommand = false;
		

		for (int i=0; i<line.length(); i++) {
			character = line.charAt(i) + "";
			
			if ((line.charAt(i) == '_')) {
				inJFugueCommand = !inJFugueCommand;
			}
			else if (!inJFugueCommand) {
				
				if (character.equals("!")) {
					character = changeInstrument(113);
				}	
			}
			
			sb.append(character);				
		}
		
		return sb.toString();
	}
	
	/**
	 * Changes current instrument to Harpsichord) (General MIDI #7).
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseIOU(String line)
	{
		String regex_iou = "[iouIOU]";
		
		StringBuilder sb = new StringBuilder();
		String character;
		boolean inJFugueCommand = false;
		
		
		for (int i=0; i<line.length(); i++) {
			character = line.charAt(i) + "";
			
			if ((line.charAt(i) == '_')) {
				inJFugueCommand = !inJFugueCommand;
			}
			else if (!inJFugueCommand) {
				if (character.matches(regex_iou)) {
					character = changeInstrument(6);	// muda instrumento para Harpsichord
				}	
			}
			
			sb.append(character);
		}
		
		return sb.toString();
	}
	
	/**
	 * If the previous character was a musical note (from A to G) repeats the 
	 * note; otherwise, silence or pause.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseConsonant(String line)
	{
		String regex_consonant = "[bcdfghjklmnpqrstvwxyz]";
		Pattern p = Pattern.compile(regex_consonant);
		Matcher m = p.matcher(line);
		StringBuilder newLine = new StringBuilder();
		boolean inJFugueCommand = false;
		
		for (int i=1; i<line.length(); i++) {
			String currentChar = line.charAt(i) + "";
			
			
			if ((line.charAt(i) == '_')) {
				inJFugueCommand = !inJFugueCommand;
			}
			else if (!inJFugueCommand) {
				char previousChar = line.charAt(i-1);
				boolean isConsonant = currentChar.matches(regex_consonant) || 
						currentChar.matches(regex_consonant.toUpperCase());
				
				
				if (isConsonant && isNote(previousChar)) {
					currentChar = previousChar + "";
				}
				else {
					currentChar = putDelay();
				}
				
			}
			
			newLine.append(currentChar);				
		}
		
		return newLine.toString();
	}
	
	/**
	 * Change instrument to General MIDI instrument whose number is equal to 
	 * the value of the CURRENT instrument + digit value.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseNumber(String line)
	{
		String regex_numbers = "[0-9]+";
		boolean inJFugueCommand = false;
		List<Integer> idxNumbers = new ArrayList<>();
		StringBuilder number = new StringBuilder();
		int idxStart = -1;
		
		
		for (int i=0; i<line.length(); i++) {
			String character = line.charAt(i) + "";
			
			if ((line.charAt(i) == '_')) {
				inJFugueCommand = !inJFugueCommand;
			}
			else if (!inJFugueCommand) {
				if (character.matches(regex_numbers)) {
					number.append(character);
					idxStart = (idxStart < 0) ? i : idxStart;
				}
				else if (number.length() > 0) { // final do numero
					String tmpLine = line.substring(0, idxStart);
					
					tmpLine += changeInstrument(Integer.parseInt(number.toString()) + currentInstrument);
					tmpLine += line.substring(idxStart + number.length());
					
					line = tmpLine;
					
					idxStart = -1;
					number.delete(0, number.length());
				}	
			}
		}
	}
	
	/**
	 * Changes current instrument to Pan Flutes (General MIDI #76).
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseSemicolon(String line)
	{		
		line = line.replaceAll(";", changeInstrument(76));
		
		return line;
	}
	
	/**
	 * Changes current instrument to Church Organ (General MIDI #20).
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseComma(String line)
	{
		line = line.replaceAll(",", changeInstrument(20));
		
		return line;
	}
	
	/**
	 * If the previous character was a musical note (from A to G) repeats the 
	 * note; otherwise, silence or pause.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseElse(String line)
	{
		StringBuilder newLine = new StringBuilder();
		boolean inJFugueCommand = false;
		
		
		for (int i=1; i<line.length(); i++) {
			String currentChar = line.charAt(i) + "";
			
			
			if ((line.charAt(i) == '_')) {
				inJFugueCommand = !inJFugueCommand;
			}
			else if (!inJFugueCommand) {
				char previousChar = line.charAt(i-1);
				
				
				if (isNote(previousChar)) {
					currentChar = previousChar + "";
				}
				else {
					currentChar = putDelay();
				}				
			}
			
			newLine.append(currentChar);				
		}
		
		return newLine.toString();
	}
	
	/**
	 * Changes current instrument to Tubular Bells (General MIDI #15).
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseNewLine(String line)
	{
		return line + changeInstrument(15);
	}
	
	/**
	 * Increases one octave or sets default octave if it exceed the limit.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseDot(String line)
	{
		return parseInterrogationMark(line);
	}
	
	/**
	 * Increases one octave or sets default octave if it exceed the limit.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseInterrogationMark(String line)
	{
		if (line == null)
			throw new IllegalArgumentException("Line cannot be null");
		
		
		StringBuilder newLine = new StringBuilder();
		boolean inJFugueCommand = false;
		
		
		for (int i=1; i<line.length(); i++) {
			String currentChar = line.charAt(i) + "";
			
			
			if ((line.charAt(i) == '_')) {
				inJFugueCommand = !inJFugueCommand;
			}
			else if (!inJFugueCommand) {
				increaseOctave();				
			}
			
			newLine.append(currentChar);				
		}
		
		return newLine.toString();
	}
	
	/**
	 * Removes all accents from a String.
	 * 
	 * @param		str String that will have its accents removed
	 * 
	 * @return		String without accentuation
	 * 
	 * @throws		IllegalArgumentException If str is null
	 */
	private String removeAccentuation(String str)
	{
		if (str == null)
			throw new IllegalArgumentException("String cannot be null");
		
		str = str.replaceAll("[ÃãÀàÁáÂâ]", "a");
		str = str.replaceAll("[ÊêÈèÉé]", "e");
		str = str.replaceAll("[ÍíÌìÎî]", "i");
		str = str.replaceAll("[ÕôÓóÒòÕõ]", "o");
		str = str.replaceAll("[ÛûÚúÙù]", "u");
		
		return str;
	}
	
	/**
	 * Performs line processing for the case of the following symbols:
	 * <ul>
	 * 	<li>+</li>
	 * 	<li>-</li>
	 * </ul>
	 * Volume will be increased by 50 units when the symbol + is found. On the 
	 * other hand it will be decreased by 50 units if the symbol - is found.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
//	private String parsePlusMinus(String line)
//	{
//		if (line == null)
//			throw new IllegalArgumentException("Line cannot be null");
//		
//		// Tem que ser em sequencia
//		Pattern p = Pattern.compile("(\\+)|(\\-)");
//		Matcher m = p.matcher(line);
//		
//		
//		while (m.find()) {
//			if (m.group().contains("+")) 
//				line = line.replaceFirst("\\+", increaseVolume());
//			else
//				line = line.replaceFirst("\\-", decreaseVolume());
//		}
//		
//		return line;
//	}
//	
	/**
	 * Checks if a character is a vowel and not a musical note.
	 * 
	 * @param		letter Character to be analyzed
	 * 
	 * @return		If character is not 'I', 'O' or 'U'
	 * 
	 * @implNote	It does not consider accents
	 */
	private boolean isVowelAndNotNote(char letter) 
	{
		letter = Character.toUpperCase(letter);
		
		return (letter == 'I' || letter == 'O' || letter == 'U');
	}
	
	/**
	 * Performs line processing for the case of the following symbols:
	 * <ul>
	 * 	<li>I</li>
	 * 	<li>O</li>
	 * 	<li>U</li>
	 * </ul>
	 * If some of these symbols is found, if the previous character is a musical
	 * note, duplicate it; otherwise, perform a brief pause / interruption.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
//	private String parseVogals(String line)
//	{
//		if (line == null)
//			throw new IllegalArgumentException("Line cannot be null");
//		
//		StringBuilder sb = new StringBuilder();
//		char[] lineChar = line.toCharArray();
//		
//		
//		for (int i=0; i<lineChar.length; i++) {
//			// Ignora conchetes e tudo que tem dentro deles
//			if (lineChar[i] == '_') {
//				sb.append(lineChar[i]);
//				i++;
//				
//				while (i<lineChar.length && lineChar[i] != '_') {
//					sb.append(lineChar[i]);
//					i++;
//				}
//				
//				if (i<lineChar.length) {
//					sb.append(lineChar[i]);
//				}
//			} 
//			else {
//				
//				if (i > 0 && isVowelAndNotNote(lineChar[i])) {
//					if (isNote(lineChar[i-1])) {
//						sb.append(lineChar[i-1]);
//						sb.append(lineChar[i-1]);
//					}
//					
//					else
//						sb.append(putDelay());	// Coloca pausa
//				} 
//				else if (i<lineChar.length)
//					sb.append(lineChar[i]);
//				}
//			}
//
//		return sb.toString();
//	}
	
	/**
	 * Performs line processing for the case of spaces. When a space is found,
	 * there will be a brief pause / interruption.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
//	private String parseSpaces(String line)
//	{
//		if (line == null)
//			throw new IllegalArgumentException("Line cannot be null");
//		
//		return line.replaceAll(" ", putDelay());
//	}
	
	/**
	 * Given a processed line, performs the spacing of its terms, where the 
	 * terms represent JFugue commands.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String spaceTerms(String line)
	{
		if (line == null)
			throw new IllegalArgumentException("Line cannot be null");
		
		char[] lineChar = line.toCharArray();
		StringBuilder sb = new StringBuilder();
		
		
		if (line.length() <= 1) { return line; }
		
		// SE ACHAR _, SÓ CONTINUAR PROCURANDO APÓS ACHAR OUTRO (IGNORA TUDO ENTRE _)
		for (int i=0; i<lineChar.length-1; i++) {
			if (lineChar[i] == '_') {
				i++;
				
				while (i<lineChar.length && lineChar[i] != '_') {
					sb.append(lineChar[i]);
					i++;
				}
				
				sb.append(" ");
				//i--;	// Pq vai ser incrementado no for
			} 
			else {
				sb.append(lineChar[i]+" ");
			}
		}

		return sb.toString();
	}
	
	/**
	 * Removes all numbers from a string.
	 * 
	 * @param		str String that will be its numbers removed
	 * 
	 * @return		String without numbers
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String removeNumbers(String str) 
	{
		if (str == null)
			throw new IllegalArgumentException("String cannot be null");
		
		return str.replaceAll("[0-9]", " ");
	}
	
	/**
	 * Generates a random musical note.
	 * 
	 * @return		Musical note
	 */
	private String randomNote()
	{
		int index = (int) (Math.random()*notes.length);
		
		return "I["+notes[index]+"]";
	}
	
	/**
	 * Generates JFugue command to increase octave of all musical notes.
	 * 
	 * @return		JFugue command
	 */
	private String increaseOctave()
	{
		for (JFugueMusicalNote note : musicalNotes) {
			note.increaseOctave();
		}
		
		
		
		
		
		
		
		int newOctave = currentOctave + 1;
		int maxOctave = 10;
		int defaultOctave = 5;
		
		
		if (newOctave > maxOctave) {
			newOctave = defaultOctave;
		}
		
		return generateCommand(String.valueOf(newOctave));
	}
	
	/**
	 * Loads all available instruments.
	 */
	private void initInstruments()
	{
		File file_inst = new File("src/assets/files/instruments.tp");
		String line;
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(file_inst))) {
			while ((line = br.readLine()) != null) {
				instruments.add(line);
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks whether a letter is a musical note.
	 * 
	 * @param		letter Letter to be analyzed
	 * 
	 * @return		If the letter is a musical note
	 * 
	 * @implNote	It does not consider accents
	 */
	private boolean isNote(char letter)
	{
		return letter >= 'A' && letter <= 'G';
	}
	
	/**
	 * Increases BPM every 50 units.
	 * 
	 * @return		JFugue command
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
		
		return generateCommand("T["+bpm_constant+"]");
	}
	
	/**
	 * Decreases BPM every 50 units.
	 * 
	 * @return		JFugue command 
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
		
		return generateCommand("T["+bpm_constant+"]");
	}
	
	/**
	 * Increases volume by doubling the current volume.
	 * 
	 * @return		JFugue command
	 */
	private String increaseVolume()
	{
		if (2*volume >= 100)
			volume = 50;		// Volume default
		else
			volume *= 2;
		
		return generateCommand(":CON(7, "+volume+")");
	}
	
	/**
	 * Decreases volume by dividing the current volume in half.
	 * 
	 * @return		JFugue command
	 */
	private String decreaseVolume()
	{
		if (volume == 0 || volume/2 <= 1)
			volume = 1;
		else
			volume /= 2;
		
		return generateCommand(":CON(7, "+volume+")");
	}
	
	/**
	 * Generates a brief break / interruption.
	 * 
	 * @return		JFugue command
	 */
	private String putDelay()
	{
		return generateCommand("@1");
	}
	
	/**
	 * Selects a random instrument.
	 * 
	 * @return		JFugue command
	 */
	private String changeInstrument()
	{
		int num = (int) (Math.random()*instruments.size());
		
		
		return generateCommand("I["+instruments.get(num)+"]");
	}
	
	/**
	 * Generates JFugue commands with tokens around.
	 * 
	 * @param		command JFugue command
	 * 
	 * @return		Command between two underscores
	 */
	private String generateCommand(String command)
	{
		return "_" + command + "_";
	}
}
