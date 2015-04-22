package AE2;

/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	/** Count for each letter */
	private int [] alphaCounts;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars;
	
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{
	    // your code
		alphaCounts = new int[SIZE];
		
		alphabet = new char [SIZE];
		
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
	}
		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{
	    // Calculates the distance from A and passes it as index to alphaCounts. Increments the value in that index and increments totChars.
		int index = ch - 'A';
		alphaCounts[index]++;
		totChars++;
	}
	
	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	private double getMaxPC()
    {
		maxCh = alphabet[0];
		int temp = alphaCounts[0];
		
		for(int  i= 1; i < alphaCounts.length; i++){
			if(alphaCounts[i] >= temp){
				maxCh = alphabet[i];
				temp = alphaCounts[i];
			}
		}
		
	    return 100.0 * temp/totChars;  // Returns the Frequency%. Automatic conversion to double because we multiply with 100.0.
	}
	
	/**
	 * Returns a String consisting of the full frequency report.
	 * @return the report
	 */
	public String getReport()
	{
		StringBuilder temp = new StringBuilder("LETTER ANALYSIS");
		temp.append(String.format("%n%nLetter\tFreq\tFreq%%\tAvgFreq%%  Diff%n"));
		
		for(int i = 0; i < alphabet.length; i++){

			double freq = 100.0*alphaCounts[i]/totChars;

			//Appends a row of desired results to StringBuilder.
			temp.append(String.format("    %c\t%d\t%.1f\t%.1f\t  %.1f%n", alphabet[i], alphaCounts[i], freq, avgCounts[i], freq-avgCounts[i]));
		}
		double maxFreq = getMaxPC();
		
		//Appends the last line that informs of the most frequent letter and the maximum frequency
		temp.append(String.format("%nThe most frequent letter is %c at %.1f%%.%n", maxCh, maxFreq));
		
	    return temp.toString(); 
	}
}
