/* 
 * HW6. In place merge sort and generics
 * this file contains 5 classes:
 * 	- Singly<E> : generic linked lists,
 * 	- MergeSortString : merge-sort algorithm for (linked) lists of strings,
 * 	- Occurrence : word counting of a text,
 *  - MergeSort : generic merge-sort algorithm (we replace the type «String» by the generic type «E»),
 *  - Median : calculation of the median of a set of numerical values
 */

/* 
 * Remark: only the constructors and methods whose visibility cannot be reduced are declared "public",
 * here toString and compareTo.
 */

// SINGLY 
 
class Singly<E> {
	E element;
	Singly<E> next;

	// we choose to represent the empty list by null, the two constructors that follow cannot
	// therefore build an empty list.

	// create a list with one element.
	
	public Singly(E element, Singly<E> next) {
		this.element = element;
		this.next = next;
	}

	// create a list from a non-empty array.
	
	public Singly(E[] data) {
		assert (data.length > 0) : "\nThe constructor Singly(E[] data) cannot be used with an empty array"
				+ "\nbecause we cannot build a non-empty list without data.";
		this.element = data[0];
		this.next = null;
		Singly<E> cursor = this;
		for (int i = 1; i < data.length; i++) {
			cursor.next = new Singly<E>(data[i], null);
			cursor = cursor.next;
		}
		;
	}

	// physical copy of a list (for testing only)
	
	static <E> Singly<E> copy(Singly<E> l) {
		if (l == null)
			return null;
		Singly<E> res = new Singly<E>(l.element, l.next);
		Singly<E> cursor = res;
		while (l.next != null) {
			l = l.next;
			cursor.next = new Singly<E>(l.element, l.next);
			cursor = cursor.next;
		}
		return res;
	}

	// test of equality of two lists
	
	static <E> boolean areEqual(Singly<E> chain1, Singly<E> chain2) {
		while (chain1 != null && chain2 != null) {
			if (!chain1.element.equals(chain2.element))
				return false;
			chain1 = chain1.next;
			chain2 = chain2.next;
		}
		return chain1 == chain2;
	}
	
	// create a string from a linked list (necessary for display).
	
	public String toString() {
		Singly<E> cursor = this;
		String answer = "[ ";
		while (cursor != null) {
			answer = answer + (cursor.element).toString() + " ";
			cursor = cursor.next;
		}
		answer = answer + "]";
		return answer;
	}

	// Question 1
	// Length of a list. Iterative implementation to avoid stack overflow.
	
	static<E> int length(Singly<E> l) {
		if(l == null) return 0;
		Singly<E> head = l;
		int ans = 1;
		while (head.next != null) {
			head = head.next;
			ans++;
		}
		return ans;
	}
	
	// Question 1
	// Cutte the second half of the list passed as an argument,
	// the removed part is returned.
	// The split method therefore modifies the list passed as an argument.
	
	static<E> Singly<E> split(Singly<E> l) {
		int leng = length(l);
		if(leng <= 1) return null;
		int r = ((leng & 1) != 0) ? (leng / 2 + 1) : (leng / 2);
		Singly<E> ans,h = l;
		for(int i=0;i<r-1;i++){
			h = h.next;
		}
		ans = h.next;
		h.next = null;
		return ans;
	}
}

/* MERGE_SORT_STRING */

class MergeSortString {

	// Question 2.2
	// Realizes the merge of the two lists passed as arguments, returns the merged list.
	// The two lists passed as arguments are destroyed since the operation
	// is done "in place".
	
	static Singly<String> merge(Singly<String> l1, Singly<String> l2) {
		throw new Error("Method merge(Singly<String> l1, Singly<String> l2) to be completed (Question 2.2)");
	}

	// Question 2.2
	// Sort (recursively) the list passed as an argument by sorting each of its two halves separately before merging the two sorted halves.
	// The list passed as an argument is destroyed during the operation.
	
	static Singly<String> sort(Singly<String> l) {
		throw new Error("Method sort(Singly<String> l) to be completed (Question 2.2)");
	}

}

/* OCCURRENCE */

class Occurrence implements Comparable<Occurrence> {
	String word;
	int count;

	Occurrence(String word, int count) {
		this.word = word;
		this.count = count;
	}
	
	public String toString() {
		return word;
	}
	
	// Question 2.3 :
	// Return a list whose each link contains a word present
	// in the list of words passed as an argument, with its multiplicity.
	// The list passed as an argument can be destroyed.
	
	static Singly<Occurrence> count(Singly<String> l) {
		throw new Error("Method count(Singly<String> l) to be completed (Question 2.3)");
	}
	
	// Question 3.2
	// Method of comparison necessary for the use of the sorting algorithm
	
	public int compareTo(Occurrence that) {
		throw new Error("Method compareTo(Occurrence that) to be completed (Question 3.2)");
	}

	// Question 3.2
	// Identical to the count(Singly<String> l) method except that the returned list
	// is sorted in descending order of multiplicity.
	
	static Singly<Occurrence> sortedCount(Singly<String> l) {
		throw new Error("Method sortedCount(Singly<String> l) to be completed (Question 3.2)");
	}
}

/* MERGE_SORT */

// Generic version of MergeSortString
// We replace the type "String" with the generic type "E" in the implementation of MergeSort

class MergeSort {
	
	// Question 3.1
	// Identical to merge(Singly<String> l1, Singly<String> l2) with "E" instead of "String"
	
	static<E extends Comparable<E>> Singly<E> merge(Singly<E> l1, Singly<E> l2) {
		throw new Error("Method merge(Singly<E> l1, Singly<E> l2) to be completed (Question 3.1)");
	}

	// Question 3.1
	// Identical to sort(Singly<String> l) with "E" instead of "String"
	
	static<E extends Comparable<E>> Singly<E> sort(Singly<E> l) {
		throw new Error("Method sort(Singly<E> l) to be completed (Question 3.1)");
	}

}

/* MEDIAN */

class Median {

	// Question 3.3
	// Returns a median of the set of numerical values passed as an argument
	// in the form of a linked list.
	
	static Pair<Double> median (Singly<Double> data) {
		throw new Error("Method median (Singly<Double> data) to be completed (Question 3.3)");
	}
}
