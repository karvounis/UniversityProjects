import java.util.*;

import FormatIO.*;

/**
 * Reads in the table entries from the file, constructs the table using a HashMap, 
 * obtains the first cipher text block, 
 * discovers the key and decodes the rest of the cipher text.
 * @author Evangelos Karvounis - 2163659
 */
public final class TMT2 {

	/**Filename of the first block of cipher text.*/
	private static final String TMT2_CIPHERTEXT = "CyberSecurity/thirdCipher";
	/**Filename of the Time Memory Tradeoff Table.*/
	private static final String TMT1_TABLE = "CyberSecurity/TimeMemoryTradeoffTable";
	/**Filename of the first block of plain text.*/
	private static final String TMT2_PLAINTEXT = "CyberSecurity/thirdPlain";
	/**The integer representation of the block of plain text.*/
	private static int PLAIN_TEXT_INT;
	/**The Time Memory Tradeoff Table constructed using a HashMap.*/
	private static HashMap<Integer, Integer> TMT_TABLE;

	/**
	 * Extracts the block of plain text from a file.
	 */
	private static void extractPlainBlock(){
		FileIn fin = null;
		try{
			fin = new FileIn(TMT2_PLAINTEXT + ".txt");
			//Gets the block of plain text.
			String plain = fin.readWord();
			PLAIN_TEXT_INT = convert(plain);
		}catch (EofX e){}
		finally{fin.close();}
	}

	/**
	 * Reads the Time Memory Tradeoff Table and populates a HashMap with the inputs from the file "thirdCipher.txt".
	 * XL is the key and X0 is the value of this HashMap.
	 */
	private static void readTable(){
		FileIn fin = new FileIn(TMT1_TABLE + ".txt");
		String X0, XL;
		TMT_TABLE = new HashMap<Integer, Integer>();

		try{
			for(;;){
				XL = fin.readWord();
				X0 = fin.readWord();

				TMT_TABLE.put(Integer.parseInt(XL), Integer.parseInt(X0));
			}
		}catch(EofX e){		
		}finally{
			fin.close();
		}
	}

	/**
	 * Reads the first block of the cipher text.
	 * @return The key that decrypts the cipher text.
	 */
	private static int readFirstBlock(){
		FileIn fin = new FileIn(TMT2_CIPHERTEXT + ".txt");
		String firstCipherBlock = null;
		int firstCipherBlockInt;

		try{
			firstCipherBlock = fin.readWord();
			fin.close();
		}catch(EofX e){}

		firstCipherBlockInt = convert(firstCipherBlock);

		int possibleKey = cipherEndValue(firstCipherBlockInt);
		if(possibleKey == -1){
			return cipherMiddleValue(firstCipherBlockInt);
		}else{
			return possibleKey;
		}
	}

	/**
	 * Checks the last element XL in every chain.
	 * If it is equal to cipher text, that means that cipher is an end value and 
	 * that the key is the element before the last.
	 * We have to reconstruct a new chain until the (L-1)th element. 
	 * @param firstCipherBlockInt CipherText
	 * @return If the cipher is an End value, returns the key. Otherwise, returns -1.
	 */
	private static int cipherEndValue(int firstCipherBlockInt){
		for (Map.Entry<Integer, Integer> entry : TMT_TABLE.entrySet()) {
			Integer XL = entry.getKey();
			Integer X0 = entry.getValue();

			//Check if it is an end value.
			if(XL == firstCipherBlockInt){
				return createChain(X0);
			}
		}
		return -1;
	}

	/**
	 * Creates a chain of encrypted elements.
	 * @param X0	The starting element.
	 * @return	The key that decrypts the cipher text.
	 */
	private static int createChain(int X0){
		int chainElement = X0;

		for(int i = 0; i < TMT1.LENGTH_OF_CHAINS - 1; i++){
			chainElement = Coder.encrypt(chainElement, PLAIN_TEXT_INT);
		}
		return chainElement;
	}

	/**
	 * Calculates the key knowing that the cipher is a middle value.
	 * Encypts the cipher block a number of times (M times) and every time checks the table to see if there is a XL value that is equal to it.
	 * The program now knows which chain contains the key.
	 * Then, encrypts the X0 of the chain a numberOfTimes = (Length of the chain - M - 1) times and this is the key.
	 * @param firstCipherBlockInt A block of cipher text.
	 * @return	The actual key.
	 */
	private static int cipherMiddleValue(int firstCipherBlockInt){
		int counter = 0, C1 = firstCipherBlockInt, X0 = 0, actualX0 = 0, actualKey;
		boolean flag = false;
	
		while(!flag){
			counter++;
			if(counter > TMT1.LENGTH_OF_CHAINS){
				return -1;
			}
			//Encrypts the cipher text at least once.
			C1 = Coder.encrypt(C1, PLAIN_TEXT_INT);

			//Checks every XL if equals to C1.
			for (Map.Entry<Integer, Integer> entry : TMT_TABLE.entrySet()) {
				X0 = entry.getValue();
				Integer XL = entry.getKey();			
				if(XL == C1){
					actualX0 = X0;
					flag = true;
					break;
				}
			}
		}
		actualKey = actualX0;

		if(TMT1.LENGTH_OF_CHAINS == counter + 1){
			return X0;
		}
		//Encrypts the X0 of the chain that the key belongs to a number of times in order to extract the key 
		for(int times = 0; times < (TMT1.LENGTH_OF_CHAINS - counter - 1); times++){
			actualKey = Coder.encrypt(actualKey, PLAIN_TEXT_INT);
		}

		return actualKey;		
	}

	/**
	 * Decrypts the cipher text using a given key and outputs it to a file.
	 * @param key
	 */
	private static void decryptCipher(int key){
		FileIn fin = null;
		FileOut fout = null;
		try{
			fin = new FileIn(TMT2_CIPHERTEXT + ".txt"); 
			fout = new FileOut(TMT2_CIPHERTEXT + "_p.txt");

			for(;;){
				String block = fin.readWord();
				int	blockInt = convert(block);
				int	decrypted = Coder.decrypt(key, blockInt);

				int	c0 = decrypted / 256;
				int	c1 = decrypted % 256;

				fout.print((char)c0);
				if (c1 != 0)
					fout.print((char)c1);
			}
		}catch (EofX x){}
		finally{
			fout.close();
			fin.close();
		}
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
		extractPlainBlock();
		readTable();
		int key = readFirstBlock();

		if( key != -1){
			System.out.println("The key is: " + key);
			decryptCipher(key);
		}else{
			System.err.println("Key was not found in this table. Please construct another.");
		}
	}
}
