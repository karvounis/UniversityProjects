package AE2;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;

	//application instance variables
	//including the 'core' part of the textfile filename
	//some way of indicating whether encoding or decoding is to be done
	private MonoCipher mcipher;
	private VCipher vcipher;
	private String filename;
	private String keyword;
	private char fileInput;
	private LetterFrequencies lettFreq;
	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}

	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);

		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);

		//bottom panel is green and contains 2 buttons

		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}

	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(getKeyword()){
			if(processFileName()){
				if(e.getSource()==monoButton){
					//Code here suggests that everything is OK. Time to create the MonoCipher object.
					mcipher = new MonoCipher(keyword);
					//If processFile returns false, then something is wrong. So, we do not want to find frequencies but reset the field and wait for other input.
					if(processFile(false)){	
						findFrequencies();
					}else{
						messageField.setText(null);
						return;
					}
				}else if(e.getSource()==vigenereButton){
					//Code here suggests that everything is OK. Time to create the VCipher object.
					vcipher = new VCipher(keyword);
					if(processFile(true)){
						findFrequencies();
					}else{
						messageField.setText(null);
						return;
					}
				}
			}else{					
				messageField.setText(null);
				return;
			}
		}else{
			JOptionPane.showMessageDialog(null, "You entered invalid keyword! Be careful next time please!", "Invalid keyword", JOptionPane.ERROR_MESSAGE);
			keyField.setText(null);
			return;
		}
		System.exit(0);	//After one encryption or decryption option, the program terminates.
	}

	/**
	 * Used to create an output file that contains the frequencies of the characters.
	 */
	private void findFrequencies(){

		String lettFreqName = filename.substring(0, filename.length() - 5) + "F.txt";	//Creates the proper name of the output file that contains the frequencies.

		try {
			FileWriter foutFreq = new FileWriter(lettFreqName);

			foutFreq.append(lettFreq.getReport());
			foutFreq.close();

		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "IOException in writing frequency file", "Frequency File IOException", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword()
	{
		int[] checkArray = new int[26];		//Creates an array of integers to check the number of appearances of chars.
		String tempKeyword = keyField.getText();

		if(tempKeyword.length() > 26)	return false;	//Exits the method if the keyword has more than 26 chars.

		if(tempKeyword == null || tempKeyword.isEmpty()){	//Returns false if keyword is empty or null
			return false;
		}else{
			for (int i = 0; i < tempKeyword.length(); i++){

				char c = tempKeyword.charAt(i);

				//Checks if the char is not an upper-case letter.
				if( c < 'A' || c > 'Z'){
					return false;
				}
				int index = c - 'A';

				//Checks the cell in the array with index the distance from A.
				//If there is a value other than 0, that means that the same char has already appeared and returns false.
				//Else, it increments the value.
				if(checkArray[index]!=0){
					return false;
				}else{
					checkArray[index]++;
				}
			}
		}

		keyword = tempKeyword;	//If everything is OK, we store the keyword to the keyword variable.
		return true;  
	}

	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName()
	{
		String tempFileName = messageField.getText();
		
		if(tempFileName.isEmpty() || tempFileName==null){
			JOptionPane.showMessageDialog(null, "You entered an empty filename!", "Empty filename", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		char lastChar = tempFileName.charAt(tempFileName.length()-1);	//Gets the last character of the filename.

		if(lastChar!='P' && lastChar!='C'){
			JOptionPane.showMessageDialog(null, "You entered invalid filename! Filename must finish with 'P' or 'C'", "Invalid filename", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		filename = tempFileName + ".txt";

		File f = new File(filename);
		if(!f.exists()){	//Checks if a file exists. If not, it prints an error message and returns false.
			JOptionPane.showMessageDialog(null, "You entered invalid filename! There is no file with that name.", "File Not Found", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(lastChar=='P'){
			fileInput = 'P';
		}else{
			fileInput = 'C';
		}

		return true;  // replace with your code
	}

	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere)
	{
		FileWriter fout;
		FileReader fin = null;
		String filewriterName = filenameMaker();
		int next;		
		try {
			fin = new FileReader(filename);			
			lettFreq = new LetterFrequencies();
			fout = new FileWriter(filewriterName);
			
			while((next = fin.read()) != -1){	//Loops the characters until it gets to the end of the file. That will happen when next == -1.
				
				char characterNext = (char) next;	//Casts the integer to char
						
				if(characterNext >= 'A' && characterNext <= 'Z'){
					
					char character = charToOutput(characterNext, vigenere);
					lettFreq.addChar(character);	//Calls the addChar method of the lettFreq object.
					fout.append(character);	//Appends the character to the output file.
					
				}else if(characterNext >= 'a' && characterNext <= 'z'){
					//Program terminates if a letter is lower case.
					JOptionPane.showMessageDialog(null, "File contains lower case letter. Program terminates. Change the letter and restart the program.", "Lower case letter", JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}else{
					//Appends to the file the not to be encoded character
					fout.append(characterNext);
				}
			}			
			fout.append("\n");
			fin.close();
			fout.close();
			
		} catch (FileNotFoundException e) {
			//If FileNotFound displays an error message, clears the message field and returns false.
			JOptionPane.showMessageDialog(null, "File not found! Re-enter the filename", "File Not Found", JOptionPane.ERROR_MESSAGE);
			messageField.setText(null);
			return false;
			
		} catch (IOException e) {
			//If problem with IO displays an error message, clears the message field and returns false.
			JOptionPane.showMessageDialog(null, "IOException in writing file", "File IOException", JOptionPane.ERROR_MESSAGE);
			messageField.setText(null);
			return false;
		}
		return true;  
	}
	
	/**
	 * Gets the last char of the filename. Returns the according name of the output file.
	 * @return	Name of the output file
	 */
	private String filenameMaker(){
		
		//Checks the last character of the keyword. If 'P'->encoding output file should be created. Else, decoding output file. 
		if(fileInput=='P'){
			
			return filename.substring(0, filename.length() - 5) + "C.txt";	//Cuts the 5 last characters of the complete filename and adds C.txt to the new one.
			
		}else{
			
			return filename.substring(0, filename.length() - 5) + "D.txt";	//Cuts the 5 last characters of the complete filename and adds D.txt to the new one.
			
		}
	}
	
	/**
	 * Used to find the encoded/decoded char based on monocipher/vigenere cipher.
	 * @param next String object that contains the character.
	 * @param vigenere Whether the encoding is Vigenere (true) or Mono (false).
	 * @return
	 */
	private char charToOutput(char character, boolean vigenere){

		char temp = 0;
		//Checks if last character of message is P. If it is, then the program knows that encoding is expected to happen.
		if(fileInput == 'P'){	
			//Checks if we want monocipher or vigenere cipher encoding and calls the corresponding methods.
			if(!vigenere){
				
				temp = mcipher.encode(character);
				
			}else{
				
				temp=vcipher.encode(character);
				
			}

		}else{
			//If the program reaches here, then decoding is expected to happen.
			//Checks if we want monocipher or vigenere cipher decoding and calls the corresponding methods.
			if(!vigenere){
				
				temp = mcipher.decode(character);
				
			}else{
				
				temp = vcipher.decode(character);
				
			}
		}
		return temp;
	}
}
