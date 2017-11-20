/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;
	
	/** The cipher array. */
	private char [] cipher;

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	private String keyword;
	private String message;
	public void setKeyword(String keyword){
		this.keyword = keyword;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public MonoCipher(String keyword)
	{
	//	String keyword = new CipherGUI().getKey();
	//	String message = new CipherGUI().getMessage();
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
		
		// create first part of cipher from keyword
		cipher =new char [SIZE];
		int k = keyword.length();
		for (int i = 0; i < keyword.length(); i++)
			cipher[i] = keyword.charAt(i);
		for (int i = 0; i < SIZE; i++){
			char remainder = (char)('Z' - i);
			if(keyword.indexOf(remainder)==-1){
					cipher[k] = remainder;
					k++;
			}
		}
		System.err.println(cipher);
		// create remainder of cipher from the remaining characters of the alphabet
		// print cipher array for testing and tutors
	}
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{
		int i = 0;
		while(ch != alphabet[i])
			i++;
	    return cipher[i];  // replace with your code
	}

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{
		int i = 0;
		while(ch != cipher[i])
			i++;
	    return alphabet[i]; 
	}
}
