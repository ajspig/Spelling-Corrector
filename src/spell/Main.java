package spell;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];

		ISpellCorrector corrector = new SpellCorrector(); // should it be SpellCorrector or ISpellCorrector?
		corrector.useDictionary(dictionaryFileName);

		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}

		System.out.println("Suggestion is: " + suggestion);
	}

}
// difference between static and non-static classes:
// static means it's a class thing, whereas
//		 means it doesn't have access to instance variables and methods in the outer class

