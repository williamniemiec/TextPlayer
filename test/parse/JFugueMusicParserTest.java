package parse;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;

import models.parse.JFugueMusicParser;
import util.FileUtil;


public class JFugueMusicParserTest 
{
	@Test
	public void testPhraseFile() throws IOException 
	{
		JFugueMusicParser mp = new JFugueMusicParser();
		Path sourceFile, expectedFile;
		List<String> sourceFileContent, expectedFileContent, parsedSourceFileContent;
		
		
		sourceFile = Path.of("test/parse/files/source/phrase.txt");
		sourceFileContent = FileUtil.getLines(sourceFile, StandardCharsets.UTF_8);
		
		expectedFile = Path.of("test/parse/files/expected/phrase.txt");
		expectedFileContent = FileUtil.getLines(expectedFile, StandardCharsets.UTF_8);
		
		parsedSourceFileContent = mp.parse(sourceFileContent);
		
		for (int i=0; i<parsedSourceFileContent.size(); i++) {
			assertEquals(expectedFileContent.get(i), parsedSourceFileContent.get(i));
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullFile()
	{
		JFugueMusicParser mp = new JFugueMusicParser();
		
		mp.parse(null);
		
		fail();
	}
}
