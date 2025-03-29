/**
 * HW4. Backtracking, solution and maze generation <br>
 * This file contains 2 classes: <br> designed by Jean-Christophe Filliâtre
 * 	- ExtendCell provides a cell of the maze with operations to calculate a path to the exit and generate a maze recursively <br>
 * 	- Maze models a maze.
 */

import java.util.*;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class extends and enriches the representation of a cell of the maze. <br>
 * It provides to a cell the operations to: <br>
 * -) find a path to the exit <br>
 * -) Generate a maze recursively
 */
class ExtendedCell extends Cell {
	
	public ExtendedCell(Maze maze) {
		super(maze);
	}
	
	// Question 1

	/**
	 * Test if there is a path from the current cell to an exit
	 * 
	 * @return true if there is a path from the current cell to an exit
	 */
	boolean searchPath() {
		maze.slow(); // slow down the search animation (to help debugging)
		List<Cell> near = getNeighbors(false);
		if(isExit()){
			setMarked(true);
			return true;
		}
		int n = near.size();
		for(int i=0;i<n;i++) {
			if(near.get(i).isMarked()){
				continue;
			}
			setMarked(true);
			if(near.get(i).searchPath())
				return true;
			setMarked(false);
		}
		return false;
	}

	// Question 2

	/**
	 * generate a perfect maze using recursive backtracking
	 */
	void generateRec() {
		maze.slow();
		List<Cell> near = getNeighbors(true);
		Collections.shuffle(near);
		int n = near.size();
		for(int i=0;i<n;i++) {
			if(near.get(i).isIsolated()){
				near.get(i).breakWall(this);
				near.get(i).addNeighbor(this);
				near.get(i).generateRec();
			}
		}

	}

}

/**
 * this class models a maze
 */
class Maze {

	private int height, width;
	/** the grid (array of cells) representing the maze */ 
	private Cell[][] grid;


	// Question 3

	/**
	 * generate a perfect maze using iterative backtracking
	 */
	void generateIter(int selectionMethod) {
		Bag cells = new Bag(selectionMethod);
		cells.add(getFirstCell());

		while(!cells.isEmpty()) {
			slow();

			Cell cell = cells.peek();
			List<Cell> near = cell.getNeighbors(true);
			Collections.shuffle(near);
			int n = near.size();
			boolean hasNeighbor = false;
			for(Cell c:near) {
				if(c.isIsolated()){
					cell.breakWall(c);
					cells.add(c);
					hasNeighbor = true;
				}
			}

			if(!hasNeighbor) {
				cells.pop();
			}
		}

	}


	// Question 4
	
	/**
	 * generate a maze using Wilson's algorithm
	 */
	void generateWilson() {
		ArrayList<Cell> wilson = new ArrayList<>();
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				wilson.add(getCell(i,j));
			}
		}
		ArrayList<Cell> vis = new ArrayList<>();
		vis.add(getFirstCell());
		wilson.remove(getFirstCell());
		while(!wilson.isEmpty()) {
			slow();
			Cell cell = wilson.get( new Random().nextInt(wilson.size()) );
			ArrayList<Cell> path = new ArrayList<>();
			path.add(cell);
			while(!vis.contains(cell)) {
				cell = cell.getNeighbors(true).get(new Random().nextInt(cell.getNeighbors(true).size()));
				if(path.contains(cell)) {
					int idx = path.indexOf(cell);
					path = new ArrayList<>(path.subList(0,idx+1));
				}
				else {
					path.add(cell);
				}
			}
			int n = path.size();
			for(int i=0;i<n-1;i++) {
				Cell c = path.get(i);
				c.breakWall(path.get(i+1));
				vis.add(c);
				wilson.remove(c);
			}
		}
	}

	/**
	 * return the cell with coordinates (i, j)
	 * 
	 * @return the cell with coordinates (i, j)
	 */
	Cell getCell(int i, int j) {
		if(i < 0 || i >= height || j < 0 || j >= width)
			throw new IllegalArgumentException("invalid indices");

		return grid[i][j];
	}

	/**
	 * return the cell with coordinates (0, 0)
	 * 
	 * @return the cell with coordinates (0, 0)
	 */
	Cell getFirstCell() {
		return getCell(0, 0);
	}

	// translate coordinates to cell number
	int coordToInt(int i, int j) {
		if(i < 0 || i >= height || j < 0 || j >= width)
			throw new IndexOutOfBoundsException();

		return i*width + j;
	}

	// translate cell number to coordinates
	Coordinate intToCoord(int x) {
		if(x < 0 || x >= height*width)
			throw new IndexOutOfBoundsException();

		return new Coordinate(x/width, x%width);
	}


	// slow down the display of the maze if a graphical window is open
	void slow(){
		if (frame == null) return;

		try {
			Thread.sleep(10);
			frame.repaint();
		} catch (InterruptedException e) {}
	}

	private MazeFrame frame;
	private static final int step = 20;

	Maze(int height, int width) {
		this(height, width, true);
	}

	Maze(int height, int width, boolean window) {
		if((height <= 0) || (width <= 0))
			throw new IllegalArgumentException("height and width of a Maze must be positive");

		this.height = height;
		this.width = width;

		grid = new Cell[height][width];

		for(int i = 0; i < height; ++i)
			for(int j = 0; j < width; ++j)
				grid[i][j] = new ExtendedCell(this);

		for(int i = 0; i < height; ++i) {
			for(int j = 0; j < width; ++j) {
				if(i < height - 1) {
					grid[i][j].addNeighbor(grid[i+1][j]);
					grid[i+1][j].addNeighbor(grid[i][j]);
				}

				if(j < width - 1) {
					grid[i][j].addNeighbor(grid[i][j+1]);
					grid[i][j+1].addNeighbor(grid[i][j]);
				}
			}
		}

		grid[height-1][width-1].setExit(true);

		if(window)
			frame = new MazeFrame(grid, height, width, step);
	}

	Maze(String path) throws IOException {
		this(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8));
	}

	Maze(String path, boolean window) throws IOException {
		this(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8), window);
	}

	Maze(List<String> lines) {
		this(lines, true);
	}

	Maze(List<String> lines, boolean window) {
		if(lines.size() < 2)
			throw new IllegalArgumentException("too few lines");

		this.height = Integer.parseInt(lines.get(0));
		this.width = Integer.parseInt(lines.get(1));

		this.grid = new Cell[height][width];
		for(int i = 0; i < height; ++i)
			for(int j = 0; j < width; ++j)
				grid[i][j] = new ExtendedCell(this);

		for(int i = 0; i < height; ++i) {
			for(int j = 0; j < width; ++j) {
				if(i < height - 1) {
					grid[i][j].addNeighbor(grid[i+1][j]);
					grid[i+1][j].addNeighbor(grid[i][j]);
				}

				if(j < width - 1) {
					grid[i][j].addNeighbor(grid[i][j+1]);
					grid[i][j+1].addNeighbor(grid[i][j]);
				}
			}
		}

		grid[height-1][width-1].setExit(true);

		int i = 0;
		int j = 0;

		for(String line : lines.subList(2, lines.size())) {

			for(int k = 0; k < line.length(); ++k) {
				switch(line.charAt(k)) {
					case 'N':
						grid[i][j].breakWall(grid[i-1][j]);
						break;
					case 'E':
						grid[i][j].breakWall(grid[i][j+1]);
						break;
					case 'S':
						grid[i][j].breakWall(grid[i+1][j]);
						break;
					case 'W':
						grid[i][j].breakWall(grid[i][j-1]);
						break;
					case '*':
						grid[i][j].setMarked(true);
						break;
					default:
						throw new IllegalArgumentException("illegal character");
				}
			}
			++j;
			if(j >= width) {
				j = 0;
				++i;
			}
		}

		if(window)
			frame = new MazeFrame(grid, height, width, step);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(height);
		sb.append('\n');
		sb.append(width);
		sb.append('\n');

		for(int i = 0; i < height; ++i) {
			for(int j = 0; j < width; ++j) {
				if(i > 0 && grid[i][j].hasPassageTo(grid[i-1][j]))
					sb.append('N');
				if(j < width-1 && grid[i][j].hasPassageTo(grid[i][j+1]))
					sb.append('E');
				if(i < height-1 && grid[i][j].hasPassageTo(grid[i+1][j]))
					sb.append('S');
				if(j > 0 && grid[i][j].hasPassageTo(grid[i][j-1]))
					sb.append('W');
				if(grid[i][j].isMarked())
					sb.append('*');
				sb.append('\n');
			}
		}

		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Maze))
			return false;
		Maze that = (Maze)o;

		return this.toString().equals(that.toString());
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	boolean isPerfect() {
		UnionFind uf = new UnionFind(height*width);

		// union find cycle detection
		for(int i = 0; i < height; ++i) {
			// horizontal edges
			for(int j = 0; j < width-1; ++j) {
				if(grid[i][j].hasPassageTo(grid[i][j+1])) {
					if(uf.sameClass(coordToInt(i,j), coordToInt(i,j+1)))
						return false;
					uf.union(coordToInt(i,j), coordToInt(i,j+1));
				}
			}

			// there are no vertical edges in last row, so we're done
			if(i == height-1)
				continue;

			// vertical edges
			for(int j = 0; j < width; ++j) {
				if(grid[i][j].hasPassageTo(grid[i+1][j])) {
					if(uf.sameClass(coordToInt(i,j), coordToInt(i+1,j)))
						return false;
					uf.union(coordToInt(i,j), coordToInt(i+1,j));
				}
			}
		}

		// check if connected
		return (uf.getSize(0) == height*width);
	}

	void clearMarks() {
		for (Cell[] row : grid)
			for (Cell c : row)
				c.setMarked(false);
	}
}

