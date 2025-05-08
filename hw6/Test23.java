import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Set;
import java.io.FileNotFoundException;

public class Test23 {

	public static Integer find(Singly<Occurrence> occ, String word) {
		while (occ != null) {
			if (occ.element.word.equals(word))
				return occ.element.count;
			occ = occ.next;
		}
		return null;
	}

	private static void test(String textfile) throws FileNotFoundException {
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
		Singly<Occurrence> occ_list = Occurrence.count(chaincopy);
		assert (Singly.length(occ_list) == nb_mots_diff) : "\nIl y a "
				+ nb_mots_diff + " different words while "
				+ "your counting algorithm in counts "
				+ Singly.length(occ_list);
		Singly<Occurrence> cursor = occ_list;
		while (cursor != null) {
			n = dico.get(cursor.element.word);
			assert (n != null) : "\nthe word \"" + cursor.element.word
					+ "\" does not appear in the text";
			assert (n.equals(cursor.element.count)) : "\nLe texte contient "
					+ n + " occurences of word \"" + cursor.element.word
					+ "\" but your program counts "
					+ cursor.element.count;
			cursor = cursor.next;
		}
		for (String word : words) {
			assert (find(occ_list, word) != null) : "\n" + "the word \"" + word
					+ "\" appears in the text, but not in your count";
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		// To ensure that the assert's are active
		if (!Test23.class.desiredAssertionStatus()) {
	        System.err.println("You must pass the -ea option to the Java Virtual Machine.");
	        System.exit(1);
	      }
		
		System.out.println("Question 2.3");
		
		System.out.println("Test de la méthode «count», ce test va prendre quelques secondes :");
		// La liste vide
		assert(Occurrence.count(null)==null) : "Le cas de la liste vide est mal traité.";
		// À partir de texte
		System.out.println("Comptage des mots du texte contenu dans le fichier «dummy.txt»");
		test("dummy.txt");
		System.out.println("Comptage des mots du roman «Le tour du monde en 80 jours» (J. Verne)");
		test("ltdme80j.txt");
		System.out.println("Comptage des mots du roman «Dracula» (B. Stoker)");
		test("dracula.txt");
		System.out.println("Comptage des mots du roman «Ulysses» (J. Joyce)");
		test("ulysses.txt");
		System.out.println("[OK]");
	}

}
