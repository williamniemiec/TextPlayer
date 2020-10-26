package musicPlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;

import models.musicPlayer.JFugueMusicPlayer;
import models.parser.JFugueMusicParser;
import util.FileUtil;

public class JFugueMusicPlayerTest 
{
	private static final Path sourceFile = Path.of("test/parser/jfugue/files/source/phrase.txt");
	private static List<String> processedFile;
	
	
	static {
		try {
			processedFile = processFile(sourceFile);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testPlay() throws InterruptedException
	{
		JFugueMusicPlayer player = new JFugueMusicPlayer(processedFile);
		
		
		player.play();
		Thread.sleep(1500);
		
		assertEquals(true, player.isPlaying());
	}
	
	@Test
	public void testPause() throws InterruptedException
	{
		JFugueMusicPlayer player = new JFugueMusicPlayer(processedFile);
		
		
		player.play();
		Thread.sleep(1500);
		
		player.pause();
		assertEquals(true, player.isPaused());
	}
	
	@Test
	public void testStop() throws InterruptedException
	{
		JFugueMusicPlayer player = new JFugueMusicPlayer(processedFile);
		
		
		player.play();
		Thread.sleep(1500);
		
		player.stop();
		assertEquals(true, player.isStopped());
	}
	
	@Test
	public void testChange() throws IOException, InterruptedException
	{
		JFugueMusicPlayer player = new JFugueMusicPlayer(processedFile);
		Path sourceFile2 = Path.of("test/parser/jfugue/files/source/Cyberpunk2077.txt");
		List<String> processedFile2 = processFile(sourceFile2);
	
				
		player.play();
		Thread.sleep(1500);
		
		player.change(processedFile2);
		
		player.play();
		Thread.sleep(1500);
		
		assertEquals(true, player.isStopped());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullChange() throws InterruptedException
	{
		JFugueMusicPlayer player = new JFugueMusicPlayer(processedFile);
		
		
		player.play();
		Thread.sleep(1500);
		
		player.change(null);
		fail();
	}
	
	private static List<String> processFile(Path file) throws IOException
	{
		JFugueMusicParser mp = new JFugueMusicParser();
		List<String> sourceFileContent;
		List<String> processedFile = null;
		
		
		sourceFileContent = FileUtil.getLines(sourceFile, StandardCharsets.UTF_8);
		processedFile = mp.parse(sourceFileContent);
		
		return processedFile;
	}
}
