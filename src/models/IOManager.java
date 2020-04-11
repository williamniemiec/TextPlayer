package models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe auxiliar de IO
 */
public class IOManager 
{
	public static String extractText(File file)
	{
		String text = null;
		
		try {
			List<String> lines;
			lines = Files.readAllLines(file.toPath());
			text = lines.stream().map(l->l+"\n").collect(Collectors.joining(""));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return text;
	}
}
