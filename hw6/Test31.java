

public class Test31 {

	public static <E extends Comparable<E>> void merge(E[] input1, E[] input2,
			E[] expected) {
		Singly<E> chain1 = input1 != null?new Singly<E>(input1):null;
		Singly<E> chain2 = input2 != null?new Singly<E>(input2):null;
		Singly<E> expandable_chain1 = input1 != null?new Singly<E>(input1):null;
		Singly<E> expandable_chain2 = input2 != null?new Singly<E>(input2):null;
		Singly<E> answer = expected != null?new Singly<E>(expected):null;
		Singly<E> output = MergeSort.merge(expandable_chain1, expandable_chain2);
		assert (Singly.areEqual(output, answer)) : "\n"
				+ "chain1 =\n" + chain1 + "\n" + "chain2 =\n"
				+ chain2 + "\n" + "the merge of the two lists is\n"
				+ answer + "\nwhile your merge implementation returns\n"
				+ output
				;
	}

	public static <E extends Comparable<E>> void sort(E[] input,
			E[] expected) {
		Singly<E> chain = input!=null?new Singly<E>(input):null;
		Singly<E> expandable_chain = input!=null?new Singly<E>(input):null;
		Singly<E> answer = expected != null?new Singly<E>(expected):null;
		Singly<E> output = MergeSort.sort(expandable_chain);
		assert (Singly.areEqual(answer, output)) : "\n" + "The input list is\n"
				+ chain + "\n" + "the list produced is\n"
				+ output + "\n"
				+ "while it should be\n" + answer
				+ "\n";
	}
	
	public static void main(String[] args) {

		//To ensure that the assert's are active
		if (!Test31.class.desiredAssertionStatus()) {
	        System.err.println("You must pass the -ea option to the Java Virtual Machine.");
	        System.exit(1);
	      }
		
		System.out.println("Question 3.1");
		

		// Test of the merge method (generic version)
		
		System.out.print("Test of the merge method (generic version): ");
		merge(null, null, null);
		merge(new String[] { "Zoé" }, new String[] { "Albert" },
				new String[] { "Albert", "Zoé" });
		merge(null, new String[] { "Albert", "Zoé" },
				new String[] { "Albert", "Zoé" });
		merge(new String[] { "Albert", "Zoé" }, null,
				new String[] { "Albert", "Zoé" });
		merge(new String[] { "banane", "cerise", "citron", "datte",
				"figue", "grenade", "pamplemousse", "prune" }, new String[] {
				"abricot", "cerise", "grenade", "pomme" },
				new String[] { "abricot", "banane", "cerise", "cerise",
						"citron", "datte", "figue", "grenade", "grenade",
						"pamplemousse", "pomme", "prune" });
		System.out.println("[OK]");

		// Test of the sort method (generic version)
		
		System.out.print("Test of the sort method (generic version): ");
		Test31.sort(null, null);
		String[] input = new String[] { "OK", "KO" };
		String[] output = new String[] { "KO", "OK" };
		Test31.sort(input, output);
		input = new String[] { "ga", "bu", "zo", "ga", "zo", "ga", "bu", "ga",
				"meu", "bu" };
		output = new String[] { "bu", "bu", "bu", "ga", "ga", "ga", "ga",
				"meu", "zo", "zo" };
		Test31.sort(input, output);
		System.out.println("[OK]");

	}
	
}
