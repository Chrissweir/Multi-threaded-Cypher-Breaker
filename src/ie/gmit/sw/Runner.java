//CHRISTOPHER WEIR - G00309429
package ie.gmit.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFileChooser;

public class Runner 
{
	public static void main(String[] args) throws Exception 
	{
		String plainText = null;
		String gram = "4grams.txt";
		Scanner console = new Scanner(System.in);

		//Menu to select input
		System.out.println("Please choose one of the following options!");
		System.out.println("======MENU======");
		System.out.println("1. Enter text\n2. Choose a File");
		System.out.println("================");
		String option = console.nextLine();
		
		//Switch statement for user option
		switch(option)
		{
		case "1"://User input
			System.out.println("Enter Text: ");
			plainText = console.nextLine();
			break;

		case "2"://File input
			try 
			{	
				JFileChooser chooser = new JFileChooser() ;
				int result = chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				FileReader reader = new FileReader(file);

				int i = reader.read();
				StringBuilder sb = new StringBuilder(); 

				while (i != -1)
				{
					char ch = (char)i;
					sb.append(ch); // Add each char to string builder
					i = reader.read(); // Read the next char
				}
				plainText = sb.toString();    //Create string from string builder
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		RailFence rf = new RailFence();//New Railfence
		String cypherText = rf.encrypt(plainText, 5);//Railfence encrypt the inputed plaintext and give key of 5
		System.out.println("\nEncrypted text = " + cypherText);//Output encrypted Text
		System.out.println();
		FileParser fp = new FileParser();//Parse 4grams.txt 
		Map<String, Double> map = new ConcurrentHashMap<String, Double>();//New map
		map = fp.parse(gram);//Parse 4grams into map
		TextScorer test = new TextScorer(map);//New TextScorer takes in the map
		CypherBreaker cypher = new CypherBreaker(cypherText, test);//New CypherBreaker takes in the encrypted text and TextScorer key
	}
}
