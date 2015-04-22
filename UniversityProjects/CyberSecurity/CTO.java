import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import FormatIO.*;

/**
 * This class implements the Cipher Text Only Attack. 
 * @author Evangelos Karvounis - 2163659
 */
public final class CTO {

	/**16bit Key space (2^16).*/
	private static final int MAX16VALUE = 65536;
	/**Filename of the cipher text.*/
	private static final String CTO_CIPHERTEXT = "CyberSecurity/secondCipher";
	/**The key that decodes the message.*/
	private static int THE_KEY;

	/**
	 * Uses a REGular EXpression to check if the input String matches the regex. This regex holds some rules that exist in the English language.
	 * @param input A cipher text block
	 * @return True if the String matches the regex.
	 */
	private static boolean checkRegex(String input){
		Pattern p = Pattern.compile("[A-Z][a-z ]|[,.?!][ ]|[ ][a-zA-Z ,.?!]|[a-z][ ,.!?]|[a-z]{2}");
		Matcher m = p.matcher(input);
		return m.matches();
	}

	/**
	 * Creates an ArrayList that contains all of the 2^16 possible keys.
	 * For every block of the cipher text, we decrypt it with every possible key.
	 * If either of the two chars that are produced are not valid, then this key is removed from the ArrayList of possible keys.
	 * This algorithm continues until there is only one key left in the ArrayList. This key is the key that decrypts the cipher text to the original plaintext.
	 * @return The number of blocks that the program needs to decrypt in order to find the key that decrypts the message unambiguously.
	 */
	public static int findNumOfBlocks(){
		FileIn fin = null;
		ArrayList<Integer> possibleKeys = new ArrayList<Integer>();
		int blockNumber = 0;
		//Populates the ArrayList.
		for(int i = 0;i < MAX16VALUE;i++){
			possibleKeys.add(i);
		}
		try{
			fin = new FileIn(CTO_CIPHERTEXT + ".txt");
			//Loops all the blocks until an EofX exception is catched.
			for(;;){				
				String block = fin.readWord();
				blockNumber++;
				System.out.println("Block number: " + blockNumber + " .Possible keys: " + possibleKeys.size());				
				//Loops the ArrayList in a reverse order.
				for(int key = possibleKeys.size() - 1; key >= 0; key--){

					int	blockInt = convert(block);
					int temp = (int) possibleKeys.get(key);
					int	decrypted = Coder.decrypt(temp, blockInt);

					int charLeftInt = decrypted / 256;
					int charRighInt = decrypted % 256;

					char charLeft = (char) charLeftInt;
					char charRight = (char) charRighInt;
					String word = charLeft + "" + charRight;
					//If a decoded block does not match the regex, then that key is removed from the possible ones.
					if(!checkRegex(word)){
						possibleKeys.remove(possibleKeys.get(key));
					}

					if(possibleKeys.size() == 1){
						//Stores the key that decodes the message unambiguously.
						THE_KEY = possibleKeys.get(0);
						System.out.println("There is only 1 key left at block number: " + blockNumber + "\nThe key is: " + THE_KEY);
						return blockNumber;
					}
				}
			}			
		}catch(EofX e){
			System.err.println("Done");
		}finally{
			fin.close();
		}
		return blockNumber;
	}

	/**
	 * Decrypts the cipher text using a given key.
	 * @param key
	 */
	private static void decryptSecondCipher(int key){
		FileIn fin = null;
		FileOut fout = null;
		try{
			fin = new FileIn(CTO_CIPHERTEXT + ".txt"); 
			fout = new FileOut(CTO_CIPHERTEXT + "_p.txt");

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
	
	public static void main(String[] args) throws InterruptedException {
		findNumOfBlocks();
		decryptSecondCipher(THE_KEY);		
	}
}
