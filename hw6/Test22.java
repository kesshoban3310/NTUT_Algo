
public class Test22 {
	
	public static void merge(String[] input1, String[] input2, String[] expected) {
		Singly<String> chain1 = input1!=null?new Singly<String>(input1):null;
		Singly<String> chain2 = input2!=null?new Singly<String>(input2):null;
		Singly<String> expandable_chain1 = input1!=null?new Singly<String>(input1):null;
		Singly<String> expandable_chain2 = input2!=null?new Singly<String>(input2):null;
		Singly<String> answer = expected!=null?new Singly<String>(expected):null;
		Singly<String> output = MergeSortString.merge(expandable_chain1, expandable_chain2);
		assert (Singly.areEqual(output, answer)) : "\n"
				+ "chain1 =\n"
				+ chain1
				+ "\n"
				+ "chain2 =\n"
				+ chain2
				+ "\n"
				+ "the merge of the two lists is\n"
				+ answer
				+ "\nwhile your merge implementation returns\n"
				+ output
				;
	}

	public static void sort(String[] input, String[] expected) {
		Singly<String> chain = input!=null? new Singly<String>(input):null;
		Singly<String> expandable_chain = input!=null? new Singly<String>(input):null;
		Singly<String> answer = expected!=null? new Singly<String>(expected):null;
		Singly<String> output = MergeSortString.sort(expandable_chain);
		assert (Singly.areEqual(output, answer)) : "\n" + "The input string is\n"
				+ chain + "\n" 
				+ "the sorted string is\n"
				+ answer + "\n"
				+ "while your sort implementation returns\n" + output + "\n";
	}
	
	public static void main(String[] args) {

		//To ensure that the assert's are active
		if (!Test22.class.desiredAssertionStatus()) {
	        System.err.println("You must pass the -ea option to the Java Virtual Machine.");	        
	        System.exit(1);
	      }
		
		System.out.println("Question 2.2");
		
		// Test of the merge method
		
		System.out.print("Test of the method merge: ");
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
		
		// Test of the method sort
		
		System.out.print("Test of the method sort: ");
		sort(null, null);
		String[] input = new String[] { "OK", "KO" };
		String[] output = new String[] { "KO", "OK" };
		sort(input, output);
		input = new String[] { "ga", "bu", "zo", "ga", "zo", "ga", "bu", "ga",
				"meu", "bu" };
		output = new String[] { "bu", "bu", "bu", "ga", "ga", "ga", "ga",
				"meu", "zo", "zo" };
		sort(input, output);
		System.out.println("[OK]");

		
	}


}
