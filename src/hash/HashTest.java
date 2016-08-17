package hash;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/*
 * After my first failed attempt to write my own hashing algorithm, I decided to do 
 * use someone else's. Java has a very elegant and simple hashing function. It
 * takes the sum of a prime * the ascii value of the character at a position in the string
 * 
 * This is identical to the hashing function to what Java uses, so I know its log(n) 
 * and therefore as fast as java's. I changed the values (primes) associated with the 
 * algorithm. I start at 37 and multiply each iteration by 41. I wrote a small program
 * to test a large amount of primes, and these were the fastest.
 * 
 * In addition to this hashing function, I have a very large table size. Approaching the limit
 * of 200,000, I used 199967. Almost 8 times the size of the dictionary. This greatly reduced
 * the amount of collisions in my hash table. To handle collisions, I simply raised the previous
 * hash to 2. Being such a large data set, this was a sufficient second hash. 
 *
 * My worst case needed to be rehashed 4 times. Best case for retrieval is O(1), and worse case
 * is O(4). It is O(4) because the program has to rehash the value's key. 
 * 
 * 
 * My hashing function: 
 * 		double hash = 37;
		for(int i = 0; i < theKey.length(); i++) {
			hash = 41 * hash  + theKey.charAt(i);
		}
		hash %= myTableSize;
		return (int) hash;
 * 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Work cited:
 * 
 * Joshua Bloch. Effective Java Second Edition. Chapter 3, page 48. 
 * Methods common to all objects.
 * 
 */

public final class Testing {

	public static void main(String[] args) throws IOException {
		//TODO: add to lower case when searching for a word.
		//mine
		HashTable ht = new HashTable();
		//the competition
		HashMap<String, Anagram> mp = new HashMap<String, Anagram>();
		
		BufferedReader br = new BufferedReader
				(new FileReader(new File("./words.txt")));
		String line = null;
		while((line = br.readLine()) != null) {
			ht.put(new Anagram(line));
			//competition
			Anagram a = new Anagram(line);
			mp.put(a.myKey, a);
		}
		br.close();
		
//		Anagram a = new Anagram("banana");
		double start = System.nanoTime();
//		ht.get("yo");
//		mp.get(a.myKey);
		double stop = System.nanoTime();
		double total = (stop - start) / 1000000;

		
		System.out.println("--------------");
		System.out.println("Total words placed: " + ht.myTotalWords);
		System.out.println("Total number of collisions: " + ht.myCollisions);
		System.out.println("Secondary hashes used: " + ht.myKeyRehash);
		System.out.println("time: " + total + "ms");
	}

}
