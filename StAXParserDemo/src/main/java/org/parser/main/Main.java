package org.parser.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.parser.controller.StAXParser;

/**
 * Main class of the project. The execution of the application starts in this class.
 *
  *	@author Adela B.
  *
 */

public class Main 
{
	public static void main( String[] args ) throws XMLStreamException, IOException
	{

		if(args.length != 1){
			throw new RuntimeException("Please provide the name of the XML file to be parsed!");
		} else {
			try	(InputStream is = new FileInputStream(args[0])) {
				if(is == null) 
					throw new IOException("Failed to open resource");

				//creating a StAXParser object
				StAXParser parser = new StAXParser();
				//write the data extracted by the parser to the standard output
				System.out.println(parser.readFromXML(is));
			}
		}

	}
}

