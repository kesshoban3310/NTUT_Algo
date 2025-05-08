public class Test1 {

	// Test length: iterative, not recursive
	public static boolean TestLength() {
		Singly<Integer> chain;
		// test lenght(null) == 0
		chain = null;
		assert (Singly.length(chain) == 0) : "\nerror : length(null) is euqal to "+Singly.length(chain)+" or the expected answer is 0\n";		
		// test lenght(ptr -> null) == 1
		chain = new Singly<Integer>(1, null);
		assert (Singly.length(chain) == 1) : "\nerror : length(l) is euqal to "+Singly.length(chain)+" or the expected answer is 1\n";
		Integer[] data = new Integer[] {1,3};
		chain = new Singly<Integer>(data);
		assert (Singly.length(chain) == 2) : "\nerror : length(l) is euqal to "+Singly.length(chain)+" or the expected answer is 2\n";
		
		// test with 200000 values
		int size_r = 200000;
		Integer[] rndm = new Integer[size_r];
		for (int i=0; i < size_r; i++)
		    rndm[i] = (int) Math.random()*size_r*2;
		
		chain = new Singly<Integer>(rndm);
		// timing

		
		//long startTime = System.nanoTime();
		int size_s = Singly.length(chain);
		//long endTime = System.nanoTime();

		//System.out.print("(length "+size_s +" calculated in " + (endTime-startTime)/1000000.0 + " ms) : ");
		assert (size_s == size_r) : "\nerror : length(l) is euqal to "+size_s+" or the expected answer is "+ size_r +"\n";

		return true;
	}
	
	
	// Test split
	public static <E> void TestSplit(E[] data, E[] first, E[] second) {
		Singly<E> origin, chain1, chain2, answer1, answer2;
		chain1 = data!=null?new Singly<E>(data):null;
		origin = data!=null?new Singly<E>(data):null;
		answer1 = first!=null?new Singly<E>(first):null;
		answer2 = second!=null?new Singly<E>(second):null;
		chain2 = Singly.split(chain1);
		assert (Singly.areEqual(chain1, answer1)) : "\nThe original chain is\n"
				+ origin
				+ "\nThe initial segment should be\n"
				+ answer1 + "\nwhile it is\n"
				+ chain1;
		assert (Singly.areEqual(chain2, answer2)) : "\nThe original chain is\n"
				+ origin + "\nThe final segment should be\n"
				+ answer2 + "\nwhile it is\n"
				+ chain2;
	}

	
	public static void main(String[] args) {
    
		//To ensure that the assert's are active
		if (!Test1.class.desiredAssertionStatus()) {
	        System.err.println("You must activate the -ea option globally of the JVM");	        
	        System.exit(1);
	      }
    
		System.out.println("Question 1");
		System.out.println("If you programmed the \"length\" method recursively, the following test will trigger java.lang.StackOverflowError.");
		System.out.print("Test of the method «length» ");
		TestLength();
		System.out.println("[OK]");

		
		System.out.print("Test of the method «split» : ");
		//TestSplit(null, null, null);
		TestSplit(null, null, null);
		TestSplit(new String[] { "one" }, new String[] { "one" },
				null);
		TestSplit(new String[] { "one", "two" }, new String[] { "one" },
				new String[] { "two" });
		TestSplit(new String[] { "one", "two", "three" }, new String[] {
				"one", "two" }, new String[] { "three" });
		TestSplit(new String[] { "one", "two", "three", "four" },
				new String[] { "one", "two" }, new String[] { "three", "four" });
		TestSplit(new String[] { "one", "two", "three", "four","five" },
				new String[] { "one", "two","three" }, new String[] {"four","five"});
		TestSplit(new String[] { "one", "two", "three", "four","five","six" },
				new String[] { "one", "two","three" }, new String[] {"four","five","six" });
		System.out.println("[OK]");
	}

}

