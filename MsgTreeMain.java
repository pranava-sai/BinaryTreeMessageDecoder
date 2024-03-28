package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author PranavaSaiMaganti
 *
 * This MsgTreeMain class contains the main method and the method for printing the statistics and extracting the message from the encoded message.
 */

public class MsgTreeMain {

	/**
	 * This is the main method for the class MsgTree. The file name is taken as input and read and prints the contents of the file followed by the table of the characters and code followed by MESSAGE and the statistics for that particular file.
	 * @param args
	 * @throws FileNotFoundException - if such a file does not exist
	 */
	public static void main(String[] args) throws FileNotFoundException {
	    String binaryCode = null;
	    String binaryCoding = null;
	    String hiddenMessage = null;

	    try (// Ask for the file name once
		Scanner scnr = new Scanner(System.in)) {
			System.out.print("Enter file name to see the contents and the hidden message in the file: ");
			String fileName = scnr.next();
			System.out.println();

			File file = new File(fileName);
			try (Scanner scnr3 = new Scanner(file)) {
				String[] fileArray = new String[5];
				int index = 0;
				while (scnr3.hasNextLine()) {
				    String fileData = scnr3.nextLine();
				    fileArray[index] = fileData;
				    index++;
				}
				String secretMessage = fileArray[index - 1];
				String treeData = "";
				for (int i = 0; i <= index - 2; i++) {
				    treeData = treeData + fileArray[i] + "\n";
				}
				System.out.println("The encoded tree message is: ");
				System.out.println(treeData);
				System.out.println("The secret message in the form of 0's and 1's is: " + secretMessage);
				System.out.println();
			}
			// Process the same file for hidden message
			try (Scanner scnr4 = new Scanner(file)) {
			    binaryCode = scnr4.nextLine();
			    binaryCoding = scnr4.nextLine();
			    if (scnr4.hasNextLine()) {
			        binaryCode = binaryCode + "\n" + binaryCoding;
			        hiddenMessage = scnr4.nextLine();
			    } else {
			        hiddenMessage = binaryCoding;
			    }
			} catch (Exception error) {
			    System.out.println(error.getMessage());
			}
		}
	    MsgTree msg = new MsgTree(binaryCode);
	    System.out.println("character \tcode");
	    System.out.println("-------------------------------");
	    MsgTree.printCodes(msg, "");
	    System.out.println();
	    System.out.println("Here comes the SECRET MESSAGE:");
	    extractMsg(msg, hiddenMessage);
	}
 
	
	/**
	 * This method extracts the hidden encoded message that is in the form of 0's and 1's and prints the output as a String to the Console
	 * @param msg - This is the encoded tree message that contains the character coded message like ^a^^!^dc^rb in the file cadbard.arch. This is used to build the Binary Tree.
	 * @param secretMessage - This is the secret hidden message that contains the message like 10110101011101101010100 in the file cadbard.arch. The secret message is in the form of 0's and 1's
	 */

	public static void extractMsg(MsgTree msg, String secretMessage) {
		int secretLength = 0;
		MsgTree leaf = msg;
		for (int i = 0; i < secretMessage.length(); i++) {
			char character = secretMessage.charAt(i);

			if (character == '0') {
				leaf = leaf.left;
			} else if (character == '1') {
				leaf = leaf.right;
			}

			if (leaf.payloadChar != '^') {
				System.out.print(leaf.payloadChar);
				secretLength++;
				leaf = msg;
			}
		}
		System.out.println();
		statistics(msg, secretLength);
	}
	
	/**
	 * 5% Extra Credit
	 * This method is made to print the statistics related to each file. It calculates the average bits per character, the total number of characters and the space savings.
	 * The average bits per character is calculated as (length of the encoded tree message / total number of characters)
	 * The space savings is calculated as 1 - (Average bits per character / 16). The 16 here refers to the 16-bits of the system.
	 * @param msg
	 * @param secretMessageLength
	 */

	public static void statistics(MsgTree msg, int secretMessageLength) {
		double msgLength = (double) MsgTree.getTotalBits();
		double totalChars = (double) MsgTree.chars;
		double avg_bits_char = msgLength/totalChars;
		double spaceSavings = (1 - (avg_bits_char/16)) * 100;
		System.out.println();
		System.out.println("STATISTICS:");
		System.out.printf("Avg bits/char:    \t%.1f\n", avg_bits_char);
		System.out.println("Total Characters:\t" + secretMessageLength);
		System.out.printf("Space Savings:    \t%.1f%%\n", spaceSavings);
		
	}

}
