import java.util.Random;

import FormatIO.*;

/**
 * Implements the Time Memory Tradeoff Table. Gets the block of plain text, constructs the table and writes it to a file.
 * The entries are written as two integers per line (XL X0).
 * @author Evangelos Karvounis - 2163659
 */
public final class TMT1 {

	/**16bit Key space (2^16).*/
	private static final int MAX16VALUE = 65536;
	/**Filename of the first block of plain text.*/
	private static final String TMT1_PLAINTEXT = "CyberSecurity/thirdPlain.txt";
	/**Filename of the Time Memory Tradeoff Table.*/
	private static final String TMT1_TABLE = "CyberSecurity/TimeMemoryTradeoffTable.txt";
	/**Length (L) of each chain.*/
	public static final int LENGTH_OF_CHAINS = 20;
	/**Number of chains (N) to be constructed*/
	public static final int NUMBER_OF_CHAINS = 1000;

	/**
	 * Creates a Time Memory Tradeoff Table and stores it to "thirdTable.txt".
	 */
	private static void createTable(){
		FileIn fin = new FileIn(TMT1_PLAINTEXT);
		FileOut fout = new FileOut(TMT1_TABLE);
		
		String plainText = null;
		int plainInt=0;
		Random rand = new Random();
		
		try{
			plainText = fin.readWord();
			plainInt = convert(plainText);
		}catch(EofX e){
			
		}finally{
			fin.close();
		}
		
		for(int N = 0; N < NUMBER_OF_CHAINS; N++){
			int X0 = rand.nextInt(MAX16VALUE);
			int XL = findLastElement(X0, plainInt);

			fout.print(XL + " " + X0 + "\n");
		}
		fout.close();		
	}

	/**
	 * Calculates the last element of the chain. Encrypts the plaintext L times.
	 * The encryption function is Xi = E(X(i-1),P)
	 * @param currentLength	The current length of the chain. 
	 * @param X0	The first element of the chain.
	 * @param plainInt	The plaintext P.
	 * @return The last element of the chain - XL.
	 */
	private static int findLastElement(int X0, int plainInt){
		int XL = X0;
		for(int i = 0; i < LENGTH_OF_CHAINS; i++){
			XL = Coder.encrypt(XL, plainInt);
		}
		return XL;
	}
	
	private static int convert(String s)
	{
		int	i0 = hex2int(s.charAt(2));
		int	i1 = hex2int(s.charAt(3));
		int	i2 = hex2int(s.charAt(4));
		int	i3 = hex2int(s.charAt(5));
		return i3 + 16 * (i2 + 16 * (i1 + 16 * i0));
	}
	
	private	static	int	hex2int(char c)
	{
		if (c >= '0' && c <= '9')
			return (int)(c - '0');
		else if (c >= 'a' && c <= 'f')
			return (int) (c - 'a') + 10;
		else if (c >= 'A' && c <= 'F')
			return (int) (c - 'A') + 10;
		else
			return 0;
	}
	
	public static void main(String[] args) {
		createTable();
	}
}
