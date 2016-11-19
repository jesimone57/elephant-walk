package randomwalk;

import java.util.Random;

public class ElephantWalk {
	
	private static int MAX_GRID_SIZE = 10001;
	private static final Random RANDOM = new Random();
	
	// elephantWalk properties
	int x=0;
	int y=0;
	Border startingBorder;
	int gridSize;
	static int [][] cellVisits = new int[MAX_GRID_SIZE] [MAX_GRID_SIZE]; // NOTE:  static array for all instances of elephantWalk.  Keeps history of visits.
	
	// Accessors for properties
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getGridSize() {
		return gridSize;
	}
	public int getCellVisits() {
		return cellVisits[y + gridSize/2][x + gridSize/2];
	}
	public Border getStartingBorder() {
		return startingBorder;
	}
	
	// Constructor
	public ElephantWalk(int gridSize) {
		if (gridSize > MAX_GRID_SIZE) {
			throw new IllegalArgumentException("Max grid size of "+MAX_GRID_SIZE+" exceeded");
		}
		this.gridSize = gridSize;
		initializeOnARandomBorder();
	}


	public void initializeOnARandomBorder() {
		int halfGridSize = gridSize/2;
		startingBorder = Border.random();
		switch (startingBorder) {
		case NORTH:
			x = randomIntIntInRange(-halfGridSize, halfGridSize);
			y = halfGridSize - 2;
			break;

		case EAST:
			y = randomIntIntInRange(-halfGridSize, halfGridSize);
			x = halfGridSize - 2;
			break;

		case SOUTH:
			x = randomIntIntInRange(-halfGridSize, halfGridSize);
			y = -(halfGridSize - 2);
			break;

		case WEST:
			y = randomIntIntInRange(-halfGridSize, halfGridSize);
			x = -(halfGridSize - 2);
			break;
		}
		
		incrementCellVisits();
	}
	
	public void meander() {
        double probability = Math.random();
        if      (probability < startingBorder.getLeftMoveProbability())    x--;
        else if (probability < startingBorder.getRightMoveProbability())   x++;
        else if (probability < startingBorder.getDownwardMoveProbability()) y--;
        else if (probability < startingBorder.getUpwardMoveProbability()) y++;
        
        incrementCellVisits();
	}
	
	private void incrementCellVisits() {
		if (isCoordinateWithinBounds()) {
			// convert display coordinates of x,y to cellVisits matrix indices
        	int col = x + gridSize/2;
        	int row = y + gridSize/2;
        	cellVisits[row][col] += 1;
        }
	}
	
	public boolean isCoordinateWithinBounds() {
		int halfGridSize = gridSize/2;
		return Math.abs(x) < halfGridSize && Math.abs(y) < halfGridSize;
	}
	
	
	private int randomIntIntInRange(int rangeStart, int rangeEnd) {
		if (rangeStart >= rangeEnd) {
			throw new IllegalArgumentException("Range start "+rangeStart+" must be less than range end "+rangeEnd);
		}
		int range = rangeEnd - rangeStart;
		return rangeStart + RANDOM.nextInt(range);
	}


	public void printCellVisits() {
		for (int row=gridSize; row>=0; row--) {
			for (int col=0; col<gridSize; col++) {
				System.out.print(cellVisits[row][col]+" ");
			}
			System.out.println("\n");
		}
	}
}
