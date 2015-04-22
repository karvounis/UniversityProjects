package AE2;

import java.util.Arrays;

/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher
{
	private char [] alphabet;   //the letters of the alphabet.
	private final int SIZE = 26;
	private char[][] vigenere;	//2D array that contains the characters in the Vigenere cipher.
	private int counterEncode = 0, counterDecode = 0;	//Counters
	private final int sizeKey;

	/** 
	 * The constructor generates the cipher.
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword)
	{
		alphabet = new char [SIZE];	//Creation of alphabet array.
		sizeKey = keyword.length();

		vigenere = new char[sizeKey][SIZE];	//Creation of vigenere array.
		char[] tempVigenere = new char[SIZE];

		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);

		for(int i = 0; i < sizeKey; i++){
			int tempIndex= keyword.charAt(i) - 'A';

			//Copies the part from the index of the character to the end of the alphabet array to tempVigenere.
			System.arraycopy(alphabet, tempIndex, tempVigenere, 0, SIZE-tempIndex);
			//Copies the alphabet array from the beginning to the previous character and pastes it to tempVigenere.
			System.arraycopy(alphabet, 0, tempVigenere, SIZE-tempIndex, tempIndex);
			
			//Copies the tempVigenere array to the i-th row of 2D vigenere array
			vigenere[i] = Arrays.copyOf(tempVigenere,SIZE);
		}

		drawVCipherArray();	//Displays the vigenere cipher array

	}
	
	/**
	 * Displays the vigenere array for verification.
	 * @param sizeKey The length of the keyword.
	 */
	private void drawVCipherArray(){
		
		System.out.print("Vigenere cipher array:\n[ ");
		
		for(int row=0; row < sizeKey ; row++){
			
			System.out.print("[");
			for(int col=0; col < SIZE; col++){
				
				System.out.print(vigenere[row][col]);
				if(col == SIZE-1){					
					System.out.print("");
				}else{					
					System.out.print(", ");
				}
			}
			System.out.print("]");
			
			if(row == sizeKey-1){				
				System.out.print("");
			}else{				
				System.out.print("\n");
			}
		}
		System.out.print(" ]");
	}
	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch)
	{	
		//CounterEncode is a counter integer that counts the number  of characters that have already been encoded.
		//Reset it if its value == size of keyword.
		if(counterEncode % sizeKey == 0){
			
			counterEncode = 0;
			
		}
		int index = ch - 'A';
		
		//The encoded character is in the row = counterEncode and in the column = index = distance_of_char from 'A' of the vigenere array.
		char encodedChar = vigenere[counterEncode][index];
		counterEncode++;
		
		return encodedChar;  
	}

	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch)
	{
		int index = 0;
		//CounterDecode is a counter integer that counts the number  of characters that have already been decoded.
		//Reset it if its value == size of keyword.
		if(counterDecode % sizeKey == 0){
			
			counterDecode = 0;	
			
		}
	
		//Loops the columns in the row = counterDecode of the vigenere array.
		for(int i = 0; i < SIZE; i++){
			//If we find the character, we store the number of the column.
			if(vigenere[counterDecode][i] == ch){
				
				index = i;
				
			}
		}
		
		counterDecode++;
		return alphabet[index];  //Return the character  in the alphabet array in the position = index;
	}
}
