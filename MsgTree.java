package edu.iastate.cs228.hw4;

/**
 * @author PranavaSaiMaganti
 * 
 * This class is the parent class of the MsgTree wherein the encrypted message is processed recursively and converted into a binary tree as specified in the assignment guidelines and then prints the extracted message.
 */

public class MsgTree {
	/**
	 * The character in the encoded string that contains the character coded message like ^a^^!^dc^rb in the file cadbard.arch. This is used to build the Binary Tree.
	 */
	public char payloadChar;
	/**
	 * For the left branch of the tree
	 */
	public MsgTree left;
	/**
	 * For the right branch of the tree
	 */
	public MsgTree right;

	/**
	 * Can use a static char idx to the tree string for recursive solution, but it is not strictly necessary
	 */
	private static int staticCharIdx = 0;
	/**
	 * To keep track of the total bits in the encrypted message
	 */
	private static int totalBits = 0;
	/**
	 * To keep track of the number of characters in the message
	 */
	public static int chars = 0;

	/**
	 * Constructor for the class
	 * @param encodingString - This is the encoded tree message that contains the character coded message like ^a^^!^dc^rb in the file cadbard.arch. This is used to build the Binary Tree.
	 */
	public MsgTree(String encodingString) {
        if (encodingString == null || encodingString.length() < 2) {
            return;
        }

        // Start the recursive construction of the tree
        this.payloadChar = encodingString.charAt(staticCharIdx++);
        buildTree(this, encodingString);
    }

	/**
	 * This method builds a tree based on the encodedString that contains the message like ^a^^!^dc^rb in the file cadbard.arch
	 * @param root - The parent root of the tree
	 * @param encodingString - This is the encoded tree message that contains the character coded message like ^a^^!^dc^rb in the file cadbard.arch. This is used to build the Binary Tree.
	 */
    public void buildTree(MsgTree root, String encodingString) {
        // Base case: if all characters are processed, return
        if (staticCharIdx == encodingString.length()) {
            return;
        }

        // Left Child of the Root
        if (root.payloadChar == '^') {
            MsgTree leftNode = new MsgTree(encodingString.charAt(staticCharIdx++));
            root.left = leftNode;
            buildTree(leftNode, encodingString);
        }

        // Right Child of the Root
        if (staticCharIdx < encodingString.length() && (root.payloadChar == '^' || root.left != null)) {
            MsgTree rightNode = new MsgTree(encodingString.charAt(staticCharIdx++));
            root.right = rightNode;
            buildTree(rightNode, encodingString);
        }
    }

    /**
     * Additional Constructor for the character processing
     * @param payloadChar - The character in the encoded string that contains the character coded message like ^a^^!^dc^rb in the file cadbard.arch. This is used to build the Binary Tree.
     */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
	}
	
	/**
	 * This method is used to print the codes for each of the character, for how the binary tree has been made. Whenever there is a 0, the tree goes to the left and whenever there is a 1, the tree goes to the right. 
	 * Finally, whenever there is a '^', the tree further splits into left and right
	 * @param root - This is the encoded tree message that contains the character coded message like ^a^^!^dc^rb in the file cadbard.arch. This is used to build the Binary Tree.
	 * @param code - This is the secret hidden message that contains the message like 10110101011101101010100 in the file cadbard.arch. The secret message is in the form of 0's and 1's
	 */
	public static void printCodes(MsgTree root, String code) {
		String left = "0";
		String right = "1";
		
		MsgTree leftBranch = root.left;
		MsgTree rightBranch = root.right;
		
		char charAtPoint = root.payloadChar;
		
		String preOrderLeft = code + left;
		String preOrderRight = code + right;
		
		if (root.payloadChar != '^') {
			System.out.println("\t" + charAtPoint + "\t" + code);
			totalBits += code.length();
			chars++;
		} else {
			printCodes(leftBranch, preOrderLeft);
			printCodes(rightBranch, preOrderRight);
		}
	}

	/**
	 * Gets the total bits in the encrypted message
	 * @return totalBits
	 */
	public static int getTotalBits() {
		return totalBits;
	}

}
