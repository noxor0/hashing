package hash;

import java.util.Arrays;
import java.util.LinkedList;

public final class Anagram {
	public String myKey;
	public LinkedList<String> myWords;

	public Anagram(final String theWord) {
		myWords = new LinkedList<String>();
		myWords.add(theWord.toLowerCase());

		char[] word = theWord.toLowerCase().toCharArray();
		Arrays.sort(word);
		myKey = new String(word);
	}

	public int wordCount() {
		return myWords.size();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(myKey);
		sb.append(" ");
		sb.append(myWords.size());
		sb.append(" ");
		for(String s: myWords) {
			sb.append(s);
			sb.append(" ");
		}
		return sb.toString();
	}
}
