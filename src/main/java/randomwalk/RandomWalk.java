package randomwalk;
// cd bin
// java -cp . randomwalk/RandomWalk

import java.awt.Color;

/*************************************************************************
 *  Compilation:  javac RandomWalk.java
 *  Execution:    java RandomWalk N
 *  Dependencies: StdDraw.java
 *
 *  % java RandomWalk 20
 *  total steps = 300
 *
 *  % java RandomWalk 50
 *  total steps = 2630
 *
 *  Simulates a 2D random walk and plots the trajectory.
 *
 *  Remarks: works best if N is a divisor of 600.
 *
 *  GKIKER thoughts on how to proceed
 *  1.  vary starting points all around the periphery... whatever border the elephant starts at, keep it moving in the opposite direction...
 *  2.  when an elephant comes upon a previously created path, it will use it with a higher priority...
 *  3.  the path would then widen... and become wider (more impact? number of visits?
 *  4.  Email Marietjie about her rhino path paper draft...
 *  5.  
 *
 *************************************************************************/

public class RandomWalk {
	private static final int GRID_SIZE = 100;
	private static final double CELL_SIZE = 0.45;
	private static final int TOTAL_ELEPHANTS = 20;  
	private static final int DRAW_DELAY_MILLISECS = 40;  // 40 milliseconds
	private static final boolean DISPLAY_CELL_VISITS = false; // Works well when cell is large (grid size 10). otherwise too small to read.
	private static final double PATH_BROADENING_FACTOR = 0.20;  // .20 = 20% larger per cell visit over 1
	private static final int DRAWING_CANVAS_SIZE = 512;

	public static void main(String[] args) {

		// int N = Integer.parseInt(args[0]);
		initializeDrawingCanvas();

		for (int i = 1; i <= TOTAL_ELEPHANTS; i++) {

			ElephantWalk elephantWalk = new ElephantWalk(GRID_SIZE);
			System.out.print("Elephant #" + i + " originating from "+elephantWalk.getStartingBorder()+" border.");

			int steps = 0;
			while (elephantWalk.isCoordinateWithinBounds()) {
				drawCell(elephantWalk, StdDraw.WHITE, DISPLAY_CELL_VISITS);
				
				elephantWalk.meander();				
				drawCell(elephantWalk, StdDraw.BLUE, DISPLAY_CELL_VISITS);
				
				StdDraw.show(DRAW_DELAY_MILLISECS);
				
				steps++;
			}
			//elephantWalk.printCellVisits(); // print out the cell visits array for debugging purposes
			System.out.println(" Total steps = " + steps);
					

		} // end of elephant loop
	}

	private static void initializeDrawingCanvas() {
		StdDraw.setCanvasSize(DRAWING_CANVAS_SIZE, DRAWING_CANVAS_SIZE);
		StdDraw.setXscale(-GRID_SIZE / 2, GRID_SIZE / 2);
		StdDraw.setYscale(-GRID_SIZE / 2, GRID_SIZE / 2);
		StdDraw.clear(StdDraw.GRAY);
	}
	
	public static void drawCell(ElephantWalk elephantWalk,  Color color, boolean displayCellVisits) {
		int cellVisits = elephantWalk.getCellVisits();
		double cellSize = broadenPathByIncreasingCellSizeAccordingToNumberOfCellVisits(cellVisits);
		//System.out.println("visits="+cellVisits+" size="+cellSize);
		StdDraw.setPenColor(color);
		StdDraw.filledSquare(elephantWalk.getX(), elephantWalk.getY(),	cellSize);
		if (displayCellVisits) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.text(elephantWalk.getX(), elephantWalk.getY(), String.valueOf(cellVisits));
		}
	}
	
	public static double broadenPathByIncreasingCellSizeAccordingToNumberOfCellVisits(int cellVisits) {
		double cellSize = CELL_SIZE;
		if (cellVisits > 1) {
			cellSize = CELL_SIZE * (1.0 + (cellVisits-1) * PATH_BROADENING_FACTOR);
		}
		return cellSize;
	}
}
