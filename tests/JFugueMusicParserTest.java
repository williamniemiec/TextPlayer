

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import models.parse.JFugueMusicParser;


public class JFugueMusicParserTest 
{
	@Test
	public void test() 
	{
		JFugueMusicParser mp = new JFugueMusicParser();
		File f = new File("tests/files/text_small.txt");
		
		String result = mp.parseFile(f);
		//String expectedResult = "C y b b r p u n k @1 2 0 7 7 @1 t e m @1 m a a s @1 d d @1 7 0 @1 m i s s o e s @1 s e c c n d d r i a s ";
		String expectedResult = "a b c d e f g @1 E s s e :CON(7, 100) @1 e @1 @1 m :CON(7, 50) @1 t e x t @1 :CON(7, 100) @1 d e @1 t e6 s t e , @1 @1 @1 @1 @1 :CON(7, 50) @1 T[Presto] T[Presto] T[Allegro] T[Presto] @1 :CON(7, 100) T[Allegro] :CON(7, 50) :CON(7, 100) ";
		System.out.println(result);
		
		// texto+ não irá aumentar oitava, pois letra anterior é t
		
		
		assertEquals(expectedResult, result);
	}
}
