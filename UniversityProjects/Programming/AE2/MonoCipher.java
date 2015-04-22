package AE2;

/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;	//Made it final so that it can not be changed.

	/** The alphabet. */
	private char [] alphabet;

	/** The cipher array. */
	private char [] cipher;

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{
		alphabet = new char [SIZE];
		char[] tempAlphabet = new char[SIZE];	//Created to check the appearance of characters in the keyword.
		int sizeKey = keyword.length();
		
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);

		//Copy alphabet to tempAlphabet. If a character appears in the keyword, the value of the position that contains the char changes to -1.
		//By doing that, we can see if there are duplicate characters in the keyword.
		System.arraycopy(alphabet, 0, tempAlphabet, 0, SIZE);	
		
		// create first part of cipher from keyword
		cipher = new char[SIZE];

		for(int i = 0; i < keyword.length(); i++){
			char temp = keyword.charAt(i);	//Gets the character at position i.
			cipher[i] = temp;	//Adds him to cipher array.
			tempAlphabet[temp-'A'] = (char) -1;	//Change the value of the character in the alphabet array to -1. 
		}
		
		// create remainder of cipher from the remaining characters of the alphabet array
		for(int i=tempAlphabet.length-1; i>=0 ; i--){
			//Checks if the character in position i in the alphabet array is an uppercase letter. 
			//Remember that the characters that already appear in the keyword have taken the value -1 in the alphabet array.
			if(tempAlphabet[i]>='A' && tempAlphabet[i]<='Z'){	
				cipher[sizeKey] = tempAlphabet[i];	//Adds the character to cipher array
				sizeKey++;
			}
		}
		
		drawMonoCipherArray();
	}

	/**
	 * Prints the monocipher array for testing and tutors.
	 */
	private void drawMonoCipherArray(){
		
		System.out.print("Monocipher array:\n[");
		for(int j = 0; j < cipher.length; j++){			
			if(j!=cipher.length-1){				
				System.out.print(cipher[j] + ", ");				
			}else{				
				System.out.print(cipher[j]);
			}
		}System.out.print("]\n");
	}
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{
		//Calculates the distance of the char from character 'A'.
		int index = ch - 'A';
		
		return cipher[index];  // Returns the character in the position index from the cipher array.
	}

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{
		int index=0;
		
		//By looping through all the positions in the cipher array, we find the character's position.
		for(int i=0; i<cipher.length; i++){
			if(ch==cipher[i]){
				index = i;
			}
		}
		
		return alphabet[index];  // Returns the character in the position index from the alphabet array.
	}
}
