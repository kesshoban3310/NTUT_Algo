import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.io.FileNotFoundException;

public class Test32 {

	private static void test(String textfile)
			throws FileNotFoundException {
		Scanner sc = new Scanner(new File(textfile));
		sc.useDelimiter("[\\p{javaWhitespace}\\p{Punct}]+");
		Singly<String> chain = null;
		HashMap<String, Integer> dico = new HashMap<String, Integer>();
		Integer n = null;
		String current = null;
		while (sc.hasNext()) {
			current = sc.next();
			chain = new Singly<String>(current, chain);
			n = dico.get(current);
			if (n != null)
				dico.put(current, n + 1);
			else
				dico.put(current, 1);
		}
		sc.close();
		Set<String> words = dico.keySet();
		int nb_mots_diff = words.size();
		Singly<String> chaincopy = Singly.copy(chain);
		Singly<Occurrence> occ_list = Occurrence.sortedCount(chaincopy);
		assert (Singly.length(occ_list) == nb_mots_diff) : "\nIl y a "
				+ nb_mots_diff + " different words while "
				+ "your counting algorithm in counts "
				+ Singly.length(occ_list);
		Singly<Occurrence> cursor = occ_list;
		while (cursor != null) {
			n = dico.get(cursor.element.word);
			assert (n != null):"\nThe word \""+cursor.element.word+"\" does not appear in the text";
			assert (n.equals(cursor.element.count)) : "\nThe text contains "
					+ n
					+ " occurences of word \""
					+ cursor.element.word
					+ "\" but your program counts " + cursor.element.count;
			if(cursor.next!=null){
				assert(cursor.element.count>=cursor.next.element.count):"\n"
					+"The word \""+cursor.next.element.word+"\" is more common, so it should appear before the word \""+cursor.element.word+"\"";				
				if(cursor.element.count==cursor.next.element.count)
				assert(cursor.element.word.compareTo(cursor.next.element.word)<0):"\n"+
					"Les mots \""+cursor.element.word+"\" and \""+cursor.next.element.word+"\" have the same number of occurrences in the text, ("
							+cursor.element.count+" and "+cursor.next.element.count+") it should therefore appear in lexicographical order";
			}
			cursor = cursor.next;
		}
		for(String word:words){
			assert(Test23.find(occ_list,word) != null):"\n"+
		"The word \""+word+"\" appears in the text, but not in your count";
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		// To ensure that the assert's are active
		if (!Test32.class.desiredAssertionStatus()) {
	        System.err.println("You must pass the -ea option to the Java Virtual Machine.");
	        System.exit(1);
	      }
		System.out.println("Test of the method «sortedCount», this test will take a few seconds :");
		// Empty list
		assert(Occurrence.sortedCount(null)==null) : "The empty list case is poorly handled.";
		// From text
		System.out.println("Word count of the text contained in the file «dummy.txt»");
		test("dummy.txt");
		System.out.println("Word count of the novel «Le tour du monde en 80 jours» (J. Verne)");
		test("ltdme80j.txt");
		System.out.println("Word count of the novel «Dracula» (B. Stoker)");
		test("dracula.txt");
		System.out.println("Word count of the novel «Ulysses» (J. Joyce)");
		test("ulysses.txt");
		System.out.println("[OK]");
	}

}
