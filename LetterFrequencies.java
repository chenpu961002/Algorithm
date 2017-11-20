import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

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
	
	private double [] Diff;

	private String LFmessage;
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
		
		alphaCounts = new int [SIZE];

		// your code
	}
		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{
		int i = 0;
		while(ch != alphabet[i])
			i++;
		alphaCounts[i]++;
	    // your code
	}
	
	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	private double getMaxPC()
	{
		int maxCount = alphaCounts[0];
		for(int i = 1; i<SIZE; i++){
			if(alphaCounts[i]>maxCount)
				maxCount = alphaCounts[i];
		}
	    return Double.parseDouble(String.format("%.1f", 100.0*maxCount/totChars));  // replace with your code
	}
	
	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport()
	{
		for (int i : alphaCounts)
			totChars+=i;
		Diff = new double [SIZE];
		for (int i = 0; i<SIZE; i++)
			Diff[i] = 100.0*alphaCounts[i]/totChars-avgCounts[i];
		FileWriter frequencyFW = null;
		try{
			 frequencyFW = new FileWriter(LFmessage.substring(0,LFmessage.length()-1)+"F.txt");
			
			try{
				frequencyFW.write("Letter  Freq  Freq%  AvgFreq%  Diff"+"\r\n");
				for (int i = 0; i<SIZE; i++){
					System.err.println(String.format("%4s"+"  "+"%4d"+"    "+
							"%4.1f"+"    "+"%4.1f"+"     "+"%4.1f",alphabet[i],alphaCounts[i],
							100.0*alphaCounts[i]/totChars, avgCounts[i], Diff[i]));
					frequencyFW.write(String.format("%4s"+"  "+"%4d"+"    "+
							"%4.1f"+"    "+"%4.1f"+"    "+"%5.1f",alphabet[i],alphaCounts[i],
							100.0*alphaCounts[i]/totChars, avgCounts[i], Diff[i])+"\r\n");
				}
				
				int j = 0;
				for(int i = 1; i<SIZE; i++){
					if(alphaCounts[i]>alphaCounts[j])
						j = i;
				}
				frequencyFW.write("The most frequent letter is "+alphabet[j]+" at "+getMaxPC()+"%"+"\r\n");
			}
			finally{
				if(frequencyFW != null)frequencyFW.close();
			}
		}
		catch(IOException e){
			System.err.println("end of file");
		}

	    return "";  // replace with your code
	}
	
	public void setMessage(String message){
		this.LFmessage = message;
	}
}
