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
	 * @return		File content or null if an error occurs
	 */
	public static String extractText(File file)
	{
		String text = null;
		List<String> lines;
		
		
		try {
			lines = Files.readAllLines(file.toPath());
			text = lines.stream().map(l->l+"\n").collect(Collectors.joining(""));
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return text;
	}
}
