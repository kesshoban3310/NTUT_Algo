import java.util.*;



/**
 * the representation of the problem is as follows:
 * the grid has 6 columns, numbered 0 to 5 from left to right
 * and 6 rows, numbered 0 to 5 from top to bottom
 *
 * there are nbCars cars, numbered from 0 to nbCars-1
 * for each car i:
 * - color[i] gives its color
 * - horiz[i] indicates if it is a horizontal car
 * - len[i] gives its length (2 or 3)
 * - moveOn[i] indicates on which line it moves for a horizontal car
 *  and on which column for a vertical car
 *
 * the car 0 is the one that must exit, so we have
 * horiz[0]==true, len[0]==2, moveOn[0]==2
 */
class RushHour {
	int nbCars;
	String[] color;
	boolean[] horiz;
	int[] len;
	int[] moveOn;

    public RushHour(int nbCars,String[] color,boolean[] horiz,int[] len,int[] moveOn){
        this.nbCars = nbCars;
        this.color = color;
        this.horiz = horiz;
        this.len = len;
        this.moveOn = moveOn;
    }
    
	
	/** return the list of possible moves from s */
	LinkedList<State> moves(State s) {
		LinkedList<State> ans = new LinkedList<>();
		boolean[][] nowState = s.free();

		for (int i = 0; i < nbCars; i++) {
			int head = s.pos[i];
			int len = s.plateau.len[i];

			if (s.plateau.horiz[i]) {
				int row = s.plateau.moveOn[i];

				if (head - 1 >= 0 && nowState[row][head - 1]) {
					ans.add(new State(s, i, -1));
				}

				if (head + len < 6 && nowState[row][head + len]) {
					ans.add(new State(s, i, +1));
				}

			} else {
				int col = s.plateau.moveOn[i];

				if (head - 1 >= 0 && nowState[head - 1][col]) {
					ans.add(new State(s, i, -1));
				}

				if (head + len < 6 && nowState[head + len][col]) {
					ans.add(new State(s, i, +1));
				}
			}
		}

		return ans;
	}


	State solveDFS(State s){
		HashSet<State> visited = new HashSet<>();
		LinkedList<State> ans = this.moves(s);
		while(!ans.isEmpty()){
			State cur = ans.pop();

			if (cur.success()) {
				return cur;
			}

			for (State next : this.moves(cur)) {
				if (!visited.contains(next)) {
					visited.add(next);
					ans.push(next);
				}
			}
		}
		return null;
	}

	/** search for a solution from state s */
	State solveBFS(State s) {
		HashSet<State> visited = new HashSet<>();
		LinkedList<State> ans = this.moves(s);
		while(!ans.isEmpty()){
			State cur = ans.poll();

			if (cur.success()) {
				return cur;
			}

			for (State next : this.moves(cur)) {
				if (!visited.contains(next)) {
					visited.add(next);
					ans.add(next);
				}
			}
		}
		return null;
	}

	static int nbMoves = 0;

	void printSolution(State s) {
		LinkedList<State> path = new LinkedList<>();

		// 先回溯整條路徑（從終點到起點）
		while (s != null) {
			path.addFirst(s); // 確保順序正確（從起點到終點）
			s = s.prev;
		}

		int nbMoves = path.size() - 1;
		System.out.println(nbMoves + " trips");

		for (int i = 1; i < path.size(); i++) {
			State to = path.get(i);

			int carIndex = to.c;
			int moveDir = to.d;
			String color = to.plateau.color[carIndex];
			boolean horiz = to.plateau.horiz[carIndex];

			String direction;
			if (horiz) {
				direction = moveDir > 0 ? "to the right" : "to the left";
			} else {
				direction = moveDir > 0 ? "down" : "up";
			}

			System.out.println("we move the " + color + " vehicle " + direction);
		}
	}


	
	
}

/** given the position of each car, with the following convention:
 * for a horizontal car it is the column of its leftmost square
 * for a vertical car it is the column of its topmost square
 * (recall: the leftmost column is 0, the topmost row is 0)
 */
class State {
	RushHour plateau;
	int[] pos;

	/** we remember which move led to this state, for the display of the solution */
	State prev;
	int c;
	int d;

	/** construct an initial state (c, d and prev are not significant) */
	public State(RushHour plateau, int[] pos) {
		this.plateau = plateau;
		this.pos = pos;
	}

	/** construct a state obtained from s by moving car c by d (-1 or +1) */
	public State(State s, int c, int d) {
		this.prev = s;
		this.c = c;
		this.d = d;

		this.plateau = s.plateau;
		this.pos = s.pos.clone();
		this.pos[c] += d;
	}

	/** winning ? */
	public boolean success() {
		int redCar = 0;
		int tail = pos[redCar] + plateau.len[redCar] - 1;

		return plateau.horiz[redCar]
				&& plateau.moveOn[redCar] == 2
				&& tail == 5;
	}
	
	/** what are the free places */
	public boolean[][] free() {
		boolean[][] vis = new boolean[6][6];
		for (int i = 0; i < 6; i++) {
			Arrays.fill(vis[i], true);
		}

		for(int i=0;i<plateau.nbCars;i++){
			int leng = plateau.len[i];
			int start = pos[i];
			if(plateau.horiz[i]){
				int row = plateau.moveOn[i];
				for(int j=0;j<leng;j++){
					vis[row][start+j] = false;
				}
			}
			else {
				int col = plateau.moveOn[i];
				for(int j=0;j<leng;j++){
					vis[start+j][col] = false;
				}
			}
		}

		return vis;
	}

	/** test of equality of two states */
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		State other = (State) o;

		return Arrays.equals(this.pos, other.pos);
	}

	/** hash code of the state */
	public int hashCode() {
		int h = 0;
		for (int i = 0; i < pos.length; i++)
			h = 37 * h + pos[i];
		return h;
	}


}

