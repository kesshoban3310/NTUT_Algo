import java.util.Arrays;
import java.util.Random;

public class TestSqDist {

	static double eps = 1e-5;

	public static double euclideanDistance(double[] a, double[] b) {
		double sum = 0.0;
		for (int i = 0; i < a.length; i++) {
			sum += Math.pow(a[i] - b[i], 2);
		}
		return Math.sqrt(sum);
	}

	static boolean comp(double a, double b) {
		return Math.abs(a - b) < eps;
	}

	public static void main(String[] args) {
		if (!TestCompare.class.desiredAssertionStatus()) {
			System.err.println("You must pass the -ea option to the Java Virtual Machine.");
			System.exit(1);
		}
		System.out.println("--Test of the method SqDist ...");
		double[] root = {0.0,0.0};
		KDTree t = new KDTree(root,0);
		Random rand = new Random();
		for (int k = 0; k <= 100; k++) {

			System.out.printf("--Test of the dimension %d ...",k);

			double[] a = new double[k];
			double[] b = new double[k];

			// 產生長度為 k 的隨機陣列
			for (int i = 0; i < k; i++) {
				a[i] = rand.nextDouble() * 1000; // 0~10 之間的隨機數
				b[i] = rand.nextDouble() * 1000;
			}

			// 計算 Euclidean distance
			double distance = euclideanDistance(a, b);
			double test = t.sqDist(a, b);

			assert comp(distance,test):
					String.format("t.sqDist(a,b) for a Euclidean distance with dimension %d counting must be correct.", k);
			System.out.println("[OK]");
		}

	}

}
