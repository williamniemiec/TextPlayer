package models.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.parser.jfugue.JFugueMusicalNote;


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
	private final Map<Character, JFugueMusicalNote> musicalNotes = new HashMap<>();
	private int currentVolume = 50;
	private int currentInstrument = 0;
	
	
	//-------------------------------------------------------------------------
	//		Initialization blocks
	//-------------------------------------------------------------------------
	{
		musicalNotes.put('A', new JFugueMusicalNote('A', 9));
		musicalNotes.put('B', new JFugueMusicalNote('B', 11));
		musicalNotes.put('C', new JFugueMusicalNote('C', 0));
		musicalNotes.put('D', new JFugueMusicalNote('D', 2));
		musicalNotes.put('E', new JFugueMusicalNote('E', 4));
		musicalNotes.put('F', new JFugueMusicalNote('F', 5));
		musicalNotes.put('G', new JFugueMusicalNote('G', 7));
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
		StringBuilder newLine, workingLine;


		for (String line : content) {
			String currentChar, previousChar;
			
			
			line = removeAccentuation(line);
			
			workingLine = new StringBuilder(line);			
			newLine = new StringBuilder();
			
			for (int i=0; i<workingLine.length(); i++) {
				currentChar = String.valueOf(line.charAt(i));
				
				if (i > 0) {
					previousChar = String.valueOf(workingLine.charAt(i-1));
					
					currentChar = parseAtoG(currentChar, previousChar);
					currentChar = parseConsonant(currentChar, previousChar);
					currentChar = parseElse(currentChar, previousChar);
					
					if (isNote(currentChar.charAt(0))) {
						workingLine.replace(i, i+1, currentChar);
					}
				}
				
				currentChar = parseMusicalNotes(currentChar);
				currentChar = parseInterrogationMark(currentChar);
				currentChar = parseExclamationMark(currentChar);
				currentChar = parseIOU(currentChar);
				
				currentChar = parseDot(currentChar);
				currentChar = parseSemicolon(currentChar);
				currentChar = parseComma(currentChar);
				currentChar = parseDigit(currentChar);
				currentChar = parseSpace(currentChar);
				
				newLine.append(currentChar);
			}

			line = newLine.toString();
			line = parseNewLine(line);
			line = spaceTerms(line);
			
			// Saves processed line
			processedContent.add(line);
		}
		
		return processedContent;
	}
	
	/**
	 * Converts musical notes (from A to G) to musical notes in JFugue taking 
	 * into account the current octave of each note. 
	 * 
	 * @param		currentChar Character being parsed
	 * 
	 * @return		Processed character
	 */
	private String parseMusicalNotes(String currentChar)
	{
		if (isNote(currentChar.charAt(0))) {
			int musicalNote = musicalNotes.get(currentChar.charAt(0)).getNote().second;
			
			currentChar = generateCommand("[" + musicalNote + "]");
		}	
		
		return currentChar;
	}
	
	/**
	 * If the previous character was a musical note (from A to G) repeats the
	 * note; otherwise, silence or pause.
	 * 
	 * @param		currentChar Character being parsed
	 * @param		previousChar Character previously parsed
	 * 
	 * @return		Processed character
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseAtoG(String currentChar, String previousChar)
	{
		String regex_aTog = "[a-g]";

		
		if (currentChar.matches(regex_aTog)) {
			if (isNote(previousChar.charAt(0))) {
				currentChar = previousChar;
			}
			else {
				currentChar = putDelay();
			}
		}	
	
		return currentChar;
	}
	
	/**
	 * Increases volume to double the current volume or sets default volume if
	 * it exceed the limit.
	 * 
	 * @param		currentChar Character being parsed
	 * 
	 * @return		Processed character
	 */
	private String parseSpace(String currentChar)
	{
		if (currentChar.equals(" ")) {
			currentChar = increaseVolume();
		}
		
		return currentChar;
	}
	
	/**
	 * Changes current instrument to Agogo) (General MIDI #114).
	 * 
	 * @param		currentChar Character being parsed
	 * 
	 * @return		Processed character
	 */
	private String parseExclamationMark(String currentChar)
	{
		if (currentChar.equals("!")) {
			currentChar = changeInstrument(113);
		}	
			
		
		return currentChar;
	}
	
	/**
	 * Changes current instrument to Harpsichord) (General MIDI #7).
	 * 
	 * @param		currentChar Character being parsed
	 * 
	 * @return		Processed character
	 */
	private String parseIOU(String currentChar)
	{
		String regex_iou = "[iouIOU]";
		
		if (currentChar.matches(regex_iou)) {
			currentChar = changeInstrument(6);	// muda instrumento para Harpsichord
		}
		
		return currentChar;
	}
	
	/**
	 * If the previous character was a musical note (from A to G) repeats the 
	 * note; otherwise, silence or pause.
	 * 
	 * @param		currentChar Character being parsed
	 * @param		previousChar Character previously parsed
	 * 
	 * @return		Processed character
	 * 
	 * @throws		IllegalArgumentException If line is null
	 */
	private String parseConsonant(String currentChar, String previousChar)
	{
		String regex_consonant = "[bcdfghjklmnpqrstvwxyz]";
		boolean isConsonant = currentChar.matches(regex_consonant) || 
				currentChar.matches(regex_consonant.toUpperCase());
				
				
		if (isConsonant) {
			if (isNote(previousChar.charAt(0))) {
				currentChar = previousChar + "";						
			}
			else {
				currentChar = putDelay();
			}
			
		}			

		return currentChar;
	}
	
	/**
	 * Change instrument to General MIDI instrument whose number is equal to 
	 * the value of the CURRENT instrument + digit value.
	 * 
	 * @param		currentChar Character being parsed
	 * 
	 * @return		Processed character
	 */
	private String parseDigit(String currentChar)
	{
		String regex_numbers = "[0-9]+";
		
		
		if (currentChar.matches(regex_numbers)) {
			currentChar = changeInstrument(Integer.parseInt(currentChar) + currentInstrument);
		}
		
		return currentChar;
	}
	
	/**
	 * Changes current instrument to Pan Flutes (General MIDI #76).
	 * 
	 * @param		currentChar Character being parsed
	 * 
	 * @return		Processed character
	 */
	private String parseSemicolon(String currentChar)
	{
		if (currentChar.equals(";")) {
			currentChar = changeInstrument(76);
		}
		
		return currentChar;
	}
	
	/**
	 * Changes current instrument to Church Organ (General MIDI #20).
	 * 
	 * @param		currentChar Character being parsed
	 * 
	 * @return		Processed character
	 */
	private String parseComma(String currentChar)
	{
		if (currentChar.equals(";")) {
			currentChar = changeInstrument(20);
		}

		return currentChar;
	}
	
	/**
	 * If the previous character was a musical note (from A to G) repeats the 
	 * note; otherwise, silence or pause.
	 * 
	 * @param		currentChar Character being parsed
	 * @param		previousChar Character previously parsed
	 * 
	 * @return		Processed character
	 */
	private String parseElse(String currentChar, String previousChar)
	{
		String regex_else = "[^a-zA-Z0-9\\s!\\?;.,]";
		
		if (currentChar.matches(regex_else)) {
			if (isNote(previousChar.charAt(0))) {
				currentChar = previousChar + "";
			}
			else {
				currentChar = putDelay();
			}
		}

		
		return currentChar;
	}
	
	/**
	 * Changes current instrument to Tubular Bells (General MIDI #15).
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 */
	private String parseNewLine(String line)
	{
		return line + changeInstrument(15);
	}
	
	/**
	 * Increases one octave or sets default octave if it exceed the limit.
	 * 
	 * @param		currentChar Character being parsed
	 * 
	 * @return		Processed character
	 */
	private String parseDot(String currentChar)
	{
		if (currentChar.equals(".")) {
			increaseOctave();
			currentChar = "";
		}
		
		return currentChar;
	}
	
	/**
	 * Increases one octave or sets default octave if it exceed the limit.
	 * 
	 * @param		currentChar Character being parsed
	 * 
	 * @return		Processed character
	 */
	private String parseInterrogationMark(String currentChar)
	{
		if (currentChar.equals("?")) {
			increaseOctave();
			currentChar = "";
		}
		
		return currentChar;
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
		
		str = str.replaceAll("[√„¿‡¡·¬‚]", "a");
		str = str.replaceAll("[ Í»Ë…È]", "e");
		str = str.replaceAll("[ÕÌÃÏŒÓ]", "i");
		str = str.replaceAll("[’Ù”Û“Ú’ı]", "o");
		str = str.replaceAll("[€˚⁄˙Ÿ˘]", "u");
		
		return str;
	}
	
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
		
		if (line.length() <= 1) 
			return line;
		
		char[] lineChar = line.toCharArray();
		StringBuilder sb = new StringBuilder();
		boolean inJFugueCommand = false;
		
		
		// Replaces underscores between JFugue commands with spaces
		for (int i=0; i<lineChar.length; i++) {
			if (lineChar[i] == '_') {
				inJFugueCommand = !inJFugueCommand;
				sb.append(" ");
			}
			else if (inJFugueCommand) {
				sb.append(lineChar[i]);
			}
			
		}

		return sb.toString();
	}
	
	/**
	 * Generates JFugue command to increase octave of all musical notes.
	 * 
	 * @return		JFugue command
	 */
	private void increaseOctave()
	{
		for (JFugueMusicalNote note : musicalNotes.values()) {
			note.increaseOctave();
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
	 * Increases volume by doubling the current volume.
	 * 
	 * @return		JFugue command
	 */
	private String increaseVolume()
	{
		int maxVolume = 100;
		int defaultVolume = 50;
		
		
		if (2*currentVolume > maxVolume)
			currentVolume = defaultVolume;		
		else
			currentVolume *= 2;
		
		return generateCommand(":CON(7,"+currentVolume+")");
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
	 * Changes current instrument.
	 * 
	 * @param		instrNumber Instrument number
	 * 
	 * @return		JFugue command
	 */
	private String changeInstrument(int instrNumber)
	{
		return generateCommand("I[" + instrNumber + "]");
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
