package models.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
	private final char[] notes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};

	
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
	 * 		<td>Character</td>
	 * 		<td>Pause / Silence</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>+</td>
	 * 		<td>Increases volume to double the current volume</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>-</td>
	 * 		<td>Volume returns to initial value</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>O, o, I, i, U or u</td>
	 * 		<td>If the previous character was a musical note (from A to G) 
	 * 		repeats the note; otherwise, pauses</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>O+</td>
	 * 		<td>Increases one octave</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>O-</td>
	 * 		<td>Decreases one octave</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>?</td>
	 * 		<td>Random note</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>NL (new line)</td>
	 * 		<td>Changes current instrument</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>B+</td>
	 * 		<td>Increases BPM by 50 units</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>B-</td>
	 * 		<td>Decreases BPM by 50 units</td>
	 * 	</tr>
	 * 	<tr align="center">
	 * 		<td>Others</td>
	 * 		<td>Ignores</td>
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
		
		//String line;
//		StringBuilder processedContent = new StringBuilder();
		List<String> processedContent = new ArrayList<>();

		
		initInstruments();
		// Problema: pode continuar na proxima linha
		// pega letra anterior ao o+ / o- tb para aumentar / diminuir ela
		// _ INDICAR¡ QUE FOI ADD ALGO NA LINHA (METODOS ANTERIORES. SE ACHAR _, S” CONTINUAR PROCURANDO AP”S ACHAR OUTRO)
		
//		try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
		for (String line : content) {
//			line = br.readLine();
			
			while (line != null) {
				line = removeNumbers(line);
				line = removeAccentuation(line);
				line = parseSpaces(line);				
				line = parseOPlusMinus(line);			// O+ | O-
				line = parseBPlusMinus(line);			// B+ | B-
				line = parseDotInterrogationMark(line);	// ? | .
				line = parseVogals(line);				
				line = parsePlusMinus(line);			// + | -
				line = spaceTerms(line);				
				line += changeInstrument();		
				
				// Saves processed line
				processedContent.add(line);
			}
		}
		
		return processedContent;
	}
	
	/**
	 * Performs line processing for the case of the following symbols:
	 * <ul>
	 * 	<li>O+</li>
	 * 	<li>O-</li>
	 * 	<li>o+</li>
	 * 	<li>o-</li>
	 * </ul>
	 * An octave will be increased if the symbol is O+ or o+ and will be 
	 * decreased by one octave if the symbol is O- or o-.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
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
	 * Performs line processing for the case of the following symbols:
	 * <ul>
	 * 	<li>B+</li>
	 * 	<li>B-</li>
	 * 	<li>b+</li>
	 * 	<li>b-</li>
	 * </ul>
	 * BPM will be increased by 50 units if the symbol is B+ or b+ and will 
	 * also be decreased by 50 units if the symbol is B- or b-.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
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
	 * Performs line processing for the case of the following symbols:
	 * <ul>
	 * 	<li>.</li>
	 * 	<li>?</li>
	 * </ul>
	 * When one of these symbols is found, it will be replaced by a random note.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
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
	 * Removes all accents from a String.
	 * 
	 * @param		str String that will have its accents removed
	 * 
	 * @return		String without accentuation
	 */
	private String removeAccentuation(String str)
	{
		str = str.replaceAll("[√„¿‡¡·¬‚]", "a");
		str = str.replaceAll("[ Í»Ë…È]", "e");
		str = str.replaceAll("[ÕÌÃÏŒÓ]", "i");
		str = str.replaceAll("[’Ù”Û“Ú’ı]", "o");
		str = str.replaceAll("[€˚⁄˙Ÿ˘]", "u");
		
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
	 * @return		Processed lineLinha processada
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
			} 
			else {
				
				if (i > 0 && isVowelAndNotNote(lineChar[i])) {
					if (isNote(lineChar[i-1])) {
						sb.append(lineChar[i-1]);
						sb.append(lineChar[i-1]);
					}
					
					else
						sb.append(putDelay());	// Coloca pausa
				} 
				else if (i<lineChar.length)
					sb.append(lineChar[i]);
				}
			}

		return sb.toString();
	}
	
	/**
	 * Performs line processing for the case of spaces. When a space is found,
	 * there will be a brief pause / interruption.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 */
	private String parseSpaces(String line)
	{
		return line.replaceAll(" ", putDelay());
	}
	
	/**
	 * Given a processed line, performs the spacing of its terms, where the 
	 * terms represent JFugue commands.
	 * 
	 * @param		line Line to be processed
	 * 
	 * @return		Processed line
	 */
	private String spaceTerms(String line)
	{
		char[] lineChar = line.toCharArray();
		StringBuilder sb = new StringBuilder();
		
		
		if (line.length() <= 1) { return line; }
		
		// SE ACHAR _, S” CONTINUAR PROCURANDO AP”S ACHAR OUTRO (IGNORA TUDO ENTRE _)
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
	 */
	private String removeNumbers(String str) 
	{
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
	 * Generates JFugue command to change octave of a musical note depending on
	 * the amount sent.
	 * 
	 * @param		note Musical note that will have its octave increased
	 * @param		amountOctaves Number of octaves
	 * 
	 * @return		JFugue command
	 */
	private String setOctave(char note, int amountOctaves)
	{
		String response;
		int defaultOctave = 5;
		
		
		amountOctaves = defaultOctave + amountOctaves;
		response = "_"+note+amountOctaves+"_";
		
		if (defaultOctave >= 10) {
			response = "_"+note+"10"+"_";
		}
		
		if (defaultOctave <= 0) {
			response = "_"+note+"0"+"_";
		}
		
		return response;
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
		return letter >= 'A' && letter <= 'G' || letter >= 'a' && letter <= 'g';
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
		
		return "_T["+bpm_constant+"]_";
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
		
		return "_T["+bpm_constant+"]_";
	}
	
	/**
	 * Increases volume by doubling the current volume.
	 * 
	 * @return		JFugue command
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
		
		return "_:CON(7, "+volume+")_";
	}
	
	/**
	 * Generates a brief break / interruption.
	 * 
	 * @return		JFugue command
	 */
	private String putDelay()
	{
		return "_@1_";
	}
	
	/**
	 * Selects a random instrument.
	 * 
	 * @return		JFugue command
	 */
	private String changeInstrument()
	{
		int num = (int) (Math.random()*instruments.size());
		
		
		return "I["+instruments.get(num)+"]";
	}
}
