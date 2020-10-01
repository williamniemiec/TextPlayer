package models.parse;

import java.io.File;
import java.util.List;


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
	 * @param		content Content to be processed
	 * 
	 * @return		Processed content
	 */
	public List<String> parse(List<String> content);
}
