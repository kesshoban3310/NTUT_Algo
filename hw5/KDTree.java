import java.util.LinkedList;
import java.util.Vector;

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
		if (tree == null) {
			return champion;  // 如果樹為空，返回當前的最佳選擇
		}

		// 對 InteractiveClosest 進行追蹤
		InteractiveClosest.trace(tree.point, champion);

		double[] tmp = tree.point;
		int dim = tree.depth % tmp.length;  // 根據深度選擇維度

		// 比較目標點與當前節點的對應維度的大小，選擇進入左子樹或右子樹
		KDTree same = (a[dim] < tmp[dim]) ? tree.left : tree.right;
		KDTree ops = (a[dim] < tmp[dim]) ? tree.right : tree.left;

		// 先從選擇的子樹中查找最近點
		double[] champ = closest(same, a, champion);

		// 如果目前的最小距離比過去的最小距離大，則可能需要檢查反向子樹
		if (tree.sqDist(a, champ) > Math.pow(a[dim] - tmp[dim], 2)) {
			champ = closest(ops, a, champ);  // 在另一個子樹中繼續查找
		}

		return champ;  // 返回當前找到的最近點
	}

	static double[] closest(KDTree tree, double[] a) {
		return closest(tree,a,tree.point);
	}

	static int size(KDTree tree) {
		throw(new Error("TODO"));
	}

	static void sum(KDTree tree, double[] acc) {
		throw(new Error("TODO"));
	}

	static double[] average(KDTree tree) {
		throw(new Error("TODO"));
	}


	static Vector<double[]> palette(KDTree tree, int maxpoints) {
		throw(new Error("TODO"));
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
