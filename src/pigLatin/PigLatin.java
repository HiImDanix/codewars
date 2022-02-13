/**
 * 
 */
package pigLatin;

/**
 * @author Daniels Kanepe
 *
 */
public class PigLatin {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(pigIt("Hello world!. ,Yays !"));

	}
	
	public static String pigIt(String str) {
		StringBuilder newStr = new StringBuilder("");
		for (String word: str.split(" ")) {
			// get first char
			char firstChar = word.charAt(0);
			
			
			// Find ending character index for middle
			int endChar = word.length();
			for (int i = word.length(); i >= 1; i--) {
				endChar = i;
				if (!isPunctuation(word.charAt(i - 1))) {
					break;
				}
			}
			// Get middle
			String middle = word.substring(1, endChar);
			
			// Get end (punctuation)
			String end = "";
			if (endChar < word.length()) {
				end = word.substring(endChar, word.length());
			}
			
			// Append the characters & add 'ay'
			newStr.append(middle + firstChar + (isPunctuation(firstChar) ? "" : "ay") + end + " ");
			
			}
		return newStr.toString().trim();
	}
	
    public static boolean isPunctuation(char c) {
        return c == ',' || c == '.' || c == '!'
            || c == '?' || c == ':' || c == ';'
            ;
    }
    
    
    /*
     * ============= OTHER SOLUTIONS ===================
     */
    
    // group 1 match: first a-Z character
    // group 2 match: rest of a-Z characters
    // Switches the groups and adds ay. Punctuation stays in place
    public static String pigIt2(String str) {
        return str.replaceAll("(\\w)(\\w*)", "$2$1ay");
    }

}
