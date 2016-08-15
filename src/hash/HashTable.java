package hash;

import java.util.Arrays;

public final class HashTable {
	
	private Anagram[] myArr;
	public int myCollisions = 0;
	public int myKeyRehash = 0;
	public int myTotalWords = 0;
	private int myTableSize = 199967;
	
	public HashTable() {
		myArr = new Anagram[myTableSize];
	}
	
	public void put(final Anagram theAna) {
		if(!theAna.myKey.contains(".") || !theAna.myKey.contains("'")) {
			int hash = hash(theAna.myKey);
			if (myArr[hash] == null) {
				myArr[hash] = theAna;
				myTotalWords++;
			} else {
				if (isCollision(theAna, hash)) {			
					dumbCollsions(theAna, hash);
				} else {
					myArr[hash].myWords.add(theAna.myWords.getFirst());
				}
			}
		}
	}
	
	public Anagram get(final String theWord) {
		Anagram ana = null;
		boolean isFound = false;
		char[] word = theWord.toCharArray();
		Arrays.sort(word);
		String sortedWord = new String(word);
		int hash = hash(sortedWord);
		if(myArr[hash] != null) {
			if (myArr[hash].myWords.contains(theWord)) {
				ana = myArr[hash];
				isFound = true;
			} else {
				while(!isFound) {
					if(myArr[hashAgain(hash)] == null) {
						break;
					}
					hash = hashAgain(hash);
					if (myArr[hash].myWords.contains(theWord)) {
						ana = myArr[hash];
						isFound = true;									
					} 
				}
				if(!isFound) ana = new Anagram("Not in List");
			}
		} else {
			ana = new Anagram("Not in List");
		}
		return ana;
	}
	
	public int hash(final String theKey) {
		double hash = 37;
		for(int i = 0; i < theKey.length(); i++) {
			hash = 41 * hash  + theKey.charAt(i);
		}
		hash %= myTableSize;
		return (int) hash;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Anagram a: myArr) {
			if(a != null) {
				sb.append(a.toString());
				sb.append("\n");				
			}
		}
		return sb.toString();
	}
	
	private boolean isCollision(final Anagram theAna, final int theHash) {
		boolean isCollision;
		if (myArr[theHash].myKey.equalsIgnoreCase(theAna.myKey)) {
			isCollision = false;
		} else {
			myCollisions++;
			isCollision = true;
		}
		return isCollision;
	}
	private int hashAgain(final int theHash) {
		double hashNotInt = Math.pow(theHash, 2);
		hashNotInt %= myTableSize;
		int newHash = (int) hashNotInt;
		return newHash;
	}
	
	private void dumbCollsions(final Anagram theAna, final int theHash) {		
 		put(theAna, hashAgain(theHash));
	}
	
	private void put(final Anagram theAna, final int newHash) {
		if (myArr[newHash] == null) {
			myArr[newHash] = theAna;
			myTotalWords++;
		} else {
			if (isCollision(theAna, newHash)) {
				myKeyRehash++;
				dumbCollsions(theAna, newHash);
			} else {
				myArr[newHash].myWords.add(theAna.myWords.getFirst());
			}
		}
	}
}
