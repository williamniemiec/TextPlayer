package models;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;


public class MusicParserTest 
{
	@Test
	public void test() 
	{
		MusicParser mp = new MusicParser();
		File f = new File("text_small.txt");
		
		String result = mp.parseFile(f);
		//String expectedResult = "C y b b r p u n k @1 2 0 7 7 @1 t e m @1 m a a s @1 d d @1 7 0 @1 m i s s o e s @1 s e c c n d d r i a s ";
		String expectedResult = "a b c d d f g @1 E s s e :CON(7, 100) @1 e @1 u m :CON(7, 50) @1 t e x t o6 @1 d d @1 t e s t e , @1 u o @1 o4 @1 T[Presto]T[Presto]T[Allegro]T[Presto]o6T[Allegro]:CON(7, 25):CON(7, 50)";
		System.out.println(result);
		
		// texto+ não irá aumentar oitava, pois letra anterior é t
		
		
		assertEquals(expectedResult, result);
	}
}
