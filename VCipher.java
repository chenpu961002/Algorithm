/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher
{
	private char [] alphabet;   //the letters of the alphabet
	private final int SIZE = 26;
        // more instance variables
	private char [][] cipher;
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	private int count;
	private String keyword;
	public VCipher(String keyword)
	{
		this.keyword = keyword;
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
		
		// create first part of cipher from keyword
		int k = keyword.length();
		cipher =new char [k][SIZE];
		for (int j = 0; j < k; j++){
			cipher[j][0] = keyword.charAt(j);
			System.err.println(cipher[j][1]+"    "+keyword.charAt(j));
		}
		
		for (int j = 0; j < k; j++){
			int l =1;
			for (int i = 0; i < SIZE; i++){
				char remainder = (char)('Z' - i);
				if(keyword.charAt(j) != remainder){
					cipher[j][l] = remainder;
					System.err.println(l);
					l++;
				}
			}
		}
		for (int j = 0; j<k; j++){
			for (int i = 0; i<SIZE; i++)
				System.err.print(cipher[j][i]);
			System.err.println();
		}
		System.err.println(cipher[0][0]+"  "+cipher[1][0]);
	    // your code
	}
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public void setcount(int count){
		this.count = count;
	}
	
	public char encode(char ch)
	{
		int j = count%keyword.length();
		int i = 0;
		while(ch != alphabet[i])
			i++;
	    return cipher[j][i];
	}
	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch)
	{
		int j = count%keyword.length();
		int i = 0;
		while(ch != cipher[j][i])
			i++;
	    return alphabet[i]; 
	    // replace with your code
	}
}
