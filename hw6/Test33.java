public class Test33 {

	private static void test(Double[] data, double min, double max) {
		Singly<Double> chain = null;
		if (data.length != 0)
			chain = new Singly<Double>(data);
		Singly<Double> chainbis = Singly.copy(chain);
		Pair<Double> m = Median.median(chainbis);
		assert (m.first.equals(min) && m.second.equals(max)) : "\n"
				+ "The sample median\n"
				+ chain
				+"\nis in the interval ["
				+ min
				+ ","
				+ max
				+ "] while your program places the median in the interval ["
				+ m.first + "," + m.second + "]";
	}

	public static void main(String[] args) {
		//TO ensure that the assert's are active
		if (!Test33.class.desiredAssertionStatus()) {
	        System.err.println("You must pass the -ea option to the Java Virtual Machine.");
	        System.exit(1);
	      }
		System.out.print("Testing median: ");
		test(new Double[0], Double.NaN, Double.NaN);
		test(new Double[] { 1. }, 1., 1.);
		test(new Double[] { 2., 1. }, 1., 2.);
		test(new Double[] { 3., 3. }, 3., 3.);
		test(new Double[] { 1., 4., 3., 2. }, 2., 3.);
		test(new Double[] { 5., 2., 4., 1., 3. }, 3., 3.);
		test(new Double[] { 1.3, 0.7, 0.3, 2.1, 0.7 }, 0.7, 0.7);
		System.out.println("[OK]");

	}

}
