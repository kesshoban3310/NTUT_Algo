import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.PriorityQueue;

public class KDTree {
	int depth;
	double[] point;
	KDTree left;
	KDTree right;

	KDTree(double[] point, int depth) {
		this.point = point;
		this.depth = depth;
	}

	boolean compare(double[] a) {
		boolean ans;

		int d = depth % point.length;
		ans = (a[d] - point[d]) >= 0.0;

		return ans;
	}

	static KDTree insert(KDTree tree, double[] p) {
		if(tree == null) {
			tree = new KDTree(p, 0);
			return tree;
		}
		if(tree.compare(p)){
			if(tree.right == null){
				tree.right = new KDTree(p, tree.depth + 1);
			}
			else{
				insert(tree.right, p);
			}
		}
		else{
			if(tree.left == null){
				tree.left = new KDTree(p, tree.depth + 1);
			}
			else {
				insert(tree.left, p);
			}
		}
		return tree;
	}

	static double sqDist(double[] a, double[] b) {
		int d = a.length;
		double sum = 0.0;
		for(int i = 0; i < d; i++) {
			sum += Math.pow(a[i] - b[i], 2);
		}
		return Math.sqrt(sum);
	}

	static double[] closestNaive(KDTree tree, double[] a, double[] champion) {
		return closestNaive(tree,a);
	}


	static double[] closestNaive(KDTree tree, double[] a) {
		LinkedList<KDTree> queue = new LinkedList<>();
		queue.add(tree);
		double[] ans = {};
		double sum = Double.MAX_VALUE;
		while(!queue.isEmpty()) {
			KDTree current = queue.remove();
			double dis = current.sqDist(a, current.point);
			if(dis < sum) {
				ans = current.point;
				sum = dis;
			}

			if(current.left != null) {
				queue.add(current.left);
			}
			if(current.right != null) {
				queue.add(current.right);
			}
		}
		return ans;
	}

	static double[] closest(KDTree tree, double[] a, double[] champion) {
		if (tree == null)
			return champion;

		// sert pour InteractiveClosest.
		InteractiveClosest.trace(tree.point, champion);

		double d = sqDist(a, champion);
		double d2 = sqDist(a, tree.point);
		if (d2 <= d) {
			champion = tree.point;
			d = d2;
		}

		int idx = tree.depth % a.length;
		KDTree near, far;
		if (a[idx] < tree.point[idx]) {
			near = tree.left;
			far = tree.right;
		} else {
			near = tree.right;
			far = tree.left;
		}

		double[] nearChampion = closest(near, a, champion);
		d2 = sqDist(a, nearChampion);
		if (d2 <= d) {
			champion = nearChampion;
			d = d2;
		}

		double sqPlaneDist = Math.sqrt((tree.point[idx] - a[idx]) * (tree.point[idx] - a[idx]));
		if (sqPlaneDist < d) {
			double[] farChampion = closest(far, a, champion);
			d2 = sqDist(a, farChampion);
			if (d2 <= d) {
				champion = farChampion;
			}
		}

		return champion;
	}

	static double[] closest(KDTree tree, double[] a) {
		double[] tmp = tree.point;
		return closest(tree,a,tmp);
	}

	static int size(KDTree tree) {
		if(tree == null){
			return 0;
		}
		int l = size(tree.left);
		int r = size(tree.right);
		return l + r + 1;
	}

	static void sum(KDTree tree, double[] acc) {
		Queue<KDTree> queue = new LinkedList<>();
		if(tree == null){
			return;
		}
		queue.add(tree);
		int n = acc.length;
		while (!queue.isEmpty()){
			KDTree current = queue.remove();
			for(int i=0;i<n;i++){
				acc[i] += current.point[i];
			}
			if(current.left != null){
				queue.add(current.left);
			}
			if(current.right != null){
				queue.add(current.right);
			}
		}
	}

	static double[] average(KDTree tree) {
		double[] ans = new double[tree.point.length];
		sum(tree, ans);
		int si = size(tree);
		for(int i=0;i<ans.length;i++){
			ans[i] /= si;
		}
		return ans;
	}


	static Vector<double[]> palette(KDTree tree, int maxpoints) {



		Vector<double[]> palette = new Vector<>();
		if (tree == null)
			return palette;

		PriorityQueue<KDTree> queue = new PriorityQueue<>(
				(a, b) -> Integer.compare(size(b), size(a)));
		queue.add(tree);
		while (queue.size() < maxpoints) {
			KDTree current = queue.poll();
			if (current == null || (current.left == null && current.right == null))
				continue;
			if (current.left != null) {
				queue.add(current.left);
			}
			if (current.right != null) {
				queue.add(current.right);
			}
		}
		for (KDTree t : queue) {
			double[] avg = average(t);
			palette.add(avg);
		}
		return palette;
		
	}

	public String pointToString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (this.point.length > 0)
			sb.append(this.point[0]);
		for (int i = 1; i < this.point.length; i++)
			sb.append("," + this.point[i]);
		sb.append("]");
		return sb.toString();
	}

}
