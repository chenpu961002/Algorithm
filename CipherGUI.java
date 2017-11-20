import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

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
	private String keyword;
	private String message;
	private String fileName;
	private boolean vigenere;
	
	public void actionPerformed(ActionEvent e)
	{
		if(getKeyword()){
			if(processFileName()){				
				if (e.getSource() == monoButton)
					vigenere = false;	
				else
					vigenere = true;
				processFile(vigenere);
				System.exit(0);
			}
		}		
	}
	
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword()
	{
		try{
			this.keyword = keyField.getText();
			if(keyword==""){
				return false;
			}
			else{
				for(int i = 0; i<keyword.length(); i++){
					if(!(keyword.charAt(i)>=65&&keyword.charAt(i)<=90))
						throw new Exception();
					for(int j = i + 1; j<keyword.length(); j++){
						if(keyword.charAt(i)==keyword.charAt(j))
							throw new Exception();
					}
				}
			}
			return true;  // replace with your code
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Upper-case Letters with no duplicates only!", "Enter Error", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
			messageField.setText("");
			return false;
		}
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
		this.message = messageField.getText();
		this.fileName = messageField.getText()+".txt";
		try{
			FileReader FR = new FileReader(fileName);
		}
		catch(FileNotFoundException f){
			JOptionPane.showMessageDialog(null, "File not found!", "Enter Error", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
			messageField.setText("");
			return false;
		}
		if(this.fileName.charAt(this.fileName.length()-5)=='P')
			return true;
		else if(this.fileName.charAt(this.fileName.length()-5)=='C')
			return true;
		else{
			JOptionPane.showMessageDialog(null, "The last character of the file name is illegal!", "Enter Error", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
			messageField.setText("");
			return false;
		}
		
	    // replace with your code
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
		FileReader FR = null;
		FileWriter FW = null;
		try{
			FR = new FileReader(fileName);
		}
		catch(FileNotFoundException f){
			System.err.println("file not found");
		}
		
		process(FR, FW, vigenere);
	    return true;  // replace with your code
	}
	
	
	private void process(FileReader FR,FileWriter FW, boolean vigenere){
		int ch;
		int count = 0;
		char encoder;
		char decoder;
		LetterFrequencies LF = new LetterFrequencies();
		LF.setMessage(message);
		
		try{
			try{
				if(this.fileName.charAt(this.fileName.length()-5)=='P'){
					FW = new FileWriter(message.substring(0,message.length()-1)+"C.txt");
					if(!vigenere){
						MonoCipher MC = new MonoCipher(keyword);
						while((ch = FR.read()) !=-1){
							if(ch>=65&&ch<=90){
								encoder = MC.encode((char)ch);
								FW.write(encoder);
								LF.addChar(encoder);
							}
							else
								FW.write((char)ch);
						}
					}
					else{
						VCipher VC = new VCipher(keyword);
						while((ch = FR.read()) !=-1){
							if(ch>=65&&ch<=90){
								encoder = VC.encode((char)ch);
								count++;
								FW.write(encoder);
								LF.addChar(encoder);
							}
							else
								FW.write((char)ch);
						}
					}
					
				}
				else{
					FW = new FileWriter(message.substring(0,message.length()-1)+"D.txt");
					if(!vigenere){
						MonoCipher MC = new MonoCipher(keyword);
							while((ch = FR.read()) !=-1){
						
								if(ch>=65&&ch<=90){
									decoder = MC.decode((char)ch);
									FW.write(decoder);
									LF.addChar(decoder);
								}
								else
									FW.write((char)ch);
							}
					}
					else{
						VCipher VC = new VCipher(keyword);
						
						while((ch = FR.read()) !=-1){
							
							if(ch>=65&&ch<=90){
								decoder = VC.decode((char)ch);
								count++;
								FW.write(decoder);
								LF.addChar(decoder);
							}
							else
								FW.write((char)ch);
						}
						
					}

				}		
				LF.getReport();
			}
			finally{
				if(FW != null)FW.close();
			}
		}
		catch(IOException g){
			System.err.println("End of file");
		}
	}
	
	
	
	
}
