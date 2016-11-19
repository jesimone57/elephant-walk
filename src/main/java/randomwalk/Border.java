package randomwalk;

import java.util.Random;

public enum Border {
	
	//    left  right down  up (probabilities)
	NORTH(0.25, 0.50, 0.9,  1.0), // top
    EAST (0.4,  0.50, 0.75, 1.0), // right
	SOUTH(0.25, 0.50, 0.6,  1.0), // bottom
    WEST (0.1,  0.50, 0.75, 1.0)  // left
    ;

	private double leftMoveProbability;
	private double rightMoveProbability;
	private double downwardMoveProbability;
	private double upwardMoveProbability;
	
	// Constructor
	private Border(double leftMoveProbability, double rightMoveProbability, double downwardMoveProbability, double upwardMoveProbability) {
		this.leftMoveProbability = leftMoveProbability;
		this.rightMoveProbability = rightMoveProbability;
		this.downwardMoveProbability = downwardMoveProbability;
		this.upwardMoveProbability = upwardMoveProbability;
	}
	
	public double getLeftMoveProbability() {
		return leftMoveProbability;
	}
	public double getRightMoveProbability() {
		return rightMoveProbability;
	}
	public double getDownwardMoveProbability() {
		return downwardMoveProbability;
	}
	public double getUpwardMoveProbability() {
		return upwardMoveProbability;
	}
	
	
	// Helper method to pick a random border from the enumeration of the 4 compass directions
	private static final Random RND = new Random();
	public static Border random() {
		return values()[RND.nextInt(values().length)];
	}
}
