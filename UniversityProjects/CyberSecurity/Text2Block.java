
/**
 *  Text2Block converts a text file to a block file.
 *  If the text has an odd number of characters then
 *  an extra ascii 0 character is added.
 *  (c) Ron Poet
 */

import FormatIO.*;

public class Text2Block 
{
	private	static	FileIn	fin;
	private	static	FileOut	fout;
	
public	static	void	main(String[] arg)
{
		// create a Console for IO
	Console	con = new Console("Text2Block");
	
		// get file names
	con.print("Enter base file name: ");
	String	name = con.readWord();
	con.println("Input  file is " + name + ".txt");
	con.println("Output file is " + name + "_b.txt");
	
		// open files
	fin = new FileIn(name + ".txt");
	fout = new FileOut(name + "_b.txt");
	
		// read chars and output blocks
	int	count = 0;
	char	c0 = 0;
	try
	{
		for (;;)
		{
			char	c1 = fin.getChar();
			count++;
			if (count % 2 == 0)	// two chars for full block
				out2(c0, c1);
			c0 = c1;	// remember this one
		}
	}
	catch (EofX x)
	{
		if (count % 2 == 1)	// odd number, pad with 0
			out2(c0, (char)0);
	}
	fout.close();
	con.println("-- Finished --");
}
	
	public	static	void	out2(char c0, char c1)
	{
		String	out = String.format("0x%02x%02x", (int) c0, (int) c1);
		fout.println(out);
	}
}
