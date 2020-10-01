package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class containing file manipulation methods.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public class FileUtil 
{
	/**
	 * Extracts text from a file.
	 * 
	 * @param		file Text file
	 * 
	 * @return		File content
	 * 
	 * @throws		IOException If an I/O error occurs reading from the file or
	 * a malformed or unmappable byte sequence is read
	 */
	public static List<String> extractText(File file) throws IOException
	{
		List<String> lines;
		
		
		lines = Files.readAllLines(file.toPath());
		lines = lines.stream().map(l->l+"\n").collect(Collectors.toList());
		
		return lines;
	}
}
