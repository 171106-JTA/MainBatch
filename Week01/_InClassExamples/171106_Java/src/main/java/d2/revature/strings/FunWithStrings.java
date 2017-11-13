package d2.revature.strings;

public class FunWithStrings{

	public static void main(String[] args) {
		/*
		 * String is a java object, also described as an array of characters.
		 * String is an IMMUTABLE object.
		 * -What is immutable?
		 * -Cannot be changed.
		 */
		System.out.println("===Strings are Immutable===\n");
		
		//Difference between system.out.println and system.out.print
		System.out.println("word1");
		System.out.println("word2\n");
		System.out.print("word1");
		System.out.print("word2\n");
		
		String s1 = "dog";
		
		System.out.println(s1);
		
		s1 += "s"; // s1 = s1 + 's';
		System.out.println(s1);
		
		System.out.println("\n====String HashCodes====\n");
		
		System.out.println("S1: " + s1 + "|" + System.identityHashCode(s1));
		
		String s2 = "dog";
		System.out.println("S2: " + s2 + "|" + System.identityHashCode(s2));
		
		s2 += 's';
		System.out.println();
		
		System.out.println("S1: " + s1 + "|" + System.identityHashCode(s1));
		System.out.println("S2: " + s2 + "|" + System.identityHashCode(s2));
		/*
		 * though s1 and s2 are the same string, they do not share the same string in the
		 * string pool. S1 is in the pool, as it was created as a literal. S2 is NOT in the 
		 * pool, because by concatenating an s, we created a 'new' String object.
		 */
		s1 = "dogs";
		String s3 = "dogs";
		//By creating a "dogs" string via literal, this string should match s1.
		
		System.out.println("\nS1: " + s1 + "|" + System.identityHashCode(s1));
		System.out.println("S2: " + s2 + "|" + System.identityHashCode(s2));
		System.out.println("S3: " + s3 + "|" + System.identityHashCode(s3));
		
		/*
		 * There is a string method that will check the string pool and either place itself
		 * in it, if the word doesnt exist, or point to the word in the word in the string
		 * pool if it DOES exist.
		 */
		s2 = s2.intern();
		
		System.out.println("\nS1: " + s1 + "|" + System.identityHashCode(s1));
		System.out.println("S2: " + s2 + "|" + System.identityHashCode(s2));
		System.out.println("S3: " + s3 + "|" + System.identityHashCode(s3));
		
		String s4 = new String("dogs");
		
		System.out.println("\nS1: " + s1 + "|" + System.identityHashCode(s1));
		System.out.println("S2: " + s2 + "|" + System.identityHashCode(s2));
		System.out.println("S3: " + s3 + "|" + System.identityHashCode(s3));
		System.out.println("S4: " + s4 + "|" + System.identityHashCode(s4));
		
		System.out.println("====StringBuffer and StringBuidler====");
		
		/*
		 * There exist a way in java, to handle MUTABLE Strings. This is done by wrapping
		 * a string in an class called StringBuffer or StringBuilder.
		 * These two classes allow us to change strings without having to create a new string
		 * object.
		 * As a result, these are faster with string manipulation.
		 */
		
		StringBuilder sBuild = new StringBuilder("bobbert");
		StringBuffer sBuff = new StringBuffer("bobbert");
		
		System.out.println("sBuild: " + sBuild + "|" + System.identityHashCode(sBuild));
		
		sBuild.append("s");
		System.out.println("sBuild: " + sBuild + "|" + System.identityHashCode(sBuild));
		
		/*
		 * The key difference between StringBuffer and StringBuilder is that 
		 * StringBuffer is threadsafe (Synchronized).
		 * StringBuilder is NOT threadsafe.
		 */
		
		String string = "bobberts";
		
		int size = 100000000;
		System.out.println("Looping " + size + " times!");
		long curtime;
		
		/*curtime = System.currentTimeMillis();
		for(int i = 0; i < size; i++){
			string += 'a';
		}
		System.out.println("String: " + (System.currentTimeMillis() - curtime));
		*/
		curtime = System.currentTimeMillis();
		for(int i = 0; i < size; i++){
			sBuild.append('a');
		}
		System.out.println("StringBuilder: " + (System.currentTimeMillis() - curtime));
		
		curtime = System.currentTimeMillis();
		for(int i = 0; i < size; i++){
			sBuff.append('a');
		}
		System.out.println("StringBuffer: " + (System.currentTimeMillis() - curtime));
		
		
		
	}

}
