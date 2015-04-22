import FormatIO.EofX;
import FormatIO.FileIn;
import FormatIO.FileOut;

/**
 * This class implements the Known Plain Text Attack. The first block of the plaintext is extracted from a file.
 * It takes the first block of the ciphertext and loops all possible keys. 
 * The key that decrypts the cipher block to the plain block is the key we want.
 * After that, uses that key to decrypt the rest of the ciphertext and produce the original plaintext.
 * @author Evangelos Karvounis - 2163659
 */
public final class KPT {
	/**Our keyspace.16bit*/
	private static final int MAX16VALUE = 65536;
	/**The first block of the plain text.*/
	private static  String FIRST_BLOCK_PLAIN;
	/**Filename of the cipher text.*/
	private static final String FIRST_CIPHER = "CyberSecurity/firstCipher";
	/**Filename of the plain text.*/
	private static final String FIRST_PLAIN = "CyberSecurity/firstPlain";

	/**
	 * Extracts the block of plaintext from a file.
	 */
	private static void extractPlainBlock(){
		FileIn fin = null;
		try{
			fin = new FileIn(FIRST_PLAIN + ".txt");
			//Gets the block of plain text.
			FIRST_BLOCK_PLAIN = fin.readWord();

		}catch (EofX e){}
		finally{fin.close();}
	}

	/**
	 * Finds a key that decrypting the first block of cipher text, produces a result equal to the first block of plaintext.
	 * @return
	 */
	private static int bruteForceKeySearch(){
		FileIn fin = null;
		int firstCipherBlock = 0, key = 0;
		//Integer representation of the known plain text block.
		int knownInt = Hex16.convert(FIRST_BLOCK_PLAIN);

		try{
			fin = new FileIn(FIRST_CIPHER + ".txt");

			//Gets the first block of cipher text.
			String temp = fin.readWord();
			//Converts it to integer.
			firstCipherBlock = convert(temp);

			fin.close();
		}catch (EofX e){}

		for(int i = 0; i < MAX16VALUE; i++){
			int	p = Coder.decrypt(i, firstCipherBlock);
			if(p == knownInt){
				key = i;
				System.out.println("The key is: " + key);
				break;
			}
		}			
		return key;
	}

	/**
	 * Decrypts the cipher text using a given key and outputs it to a file.
	 * @param key
	 */
	private static void decryptFirstCipher(int key){
		FileIn fin = null;
		FileOut fout = null;
		try{
			fin = new FileIn(FIRST_CIPHER + ".txt"); 
			fout = new FileOut(FIRST_CIPHER + "_p.txt");

			for(;;){
				String block = fin.readWord();
				int	blockInt = Hex16.convert(block);
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
		decryptFirstCipher(bruteForceKeySearch());
	}
}
