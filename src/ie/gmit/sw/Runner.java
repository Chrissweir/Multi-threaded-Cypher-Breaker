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

		System.out.println("1. Enter text\n2. Choose File");
		String option = console.nextLine();

		switch(option)
		{
		case "1":
			System.out.println("Enter Text: ");
			plainText = console.nextLine();
			break;

		case "2":
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

		RailFence rf = new RailFence();
		String cypherText = rf.encrypt(plainText, 5);
		System.out.println("\nEncrypted text = " + cypherText);
		System.out.println();
		FileParser fp = new FileParser();
		Map<String, Double> map = new ConcurrentHashMap<String, Double>();
		map = fp.parse(gram);
		TextScorer test = new TextScorer(map);
		CypherBreaker cypher = new CypherBreaker(cypherText, test);
	}
}
