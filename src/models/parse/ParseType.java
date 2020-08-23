package models.parse;

import java.io.File;


/**
 * Represents parsers that will be accepted by {@link Parser}.
 * 
 * @version		1.0.0
 * @since		1.0.0
 */
public interface ParseType 
{
	/**
	 * Parses a file.
	 * 
	 * @return		Processed content
	 * 
	 * @throws		IllegalArgumentException If file is empty
	 */
	public String parseFile(File file) throws IllegalArgumentException;
}
