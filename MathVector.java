package assign01;

/**
 * This class represents a simple row or column vector of numbers. In a row
 * vector, the numbers are written horizontally (i.e., along the columns). In a
 * column vector, the numbers are written vertically (i.e., along the rows).
 * 
 * @author Erin Parker & Nils Streedain
 * @version January 20, 2021
 */
public class MathVector {

	// 2D array to hold the numbers of the vector, either along the columns or rows
	private double[][] data;
	// set to true for a row vector and false for a column vector
	private boolean isRowVector;
	// count of elements in the vector
	private int vectorSize;

	/**
	 * Creates a new row or column vector. For a row vector, the input array is
	 * expected to have 1 row and a positive number of columns, and this number of
	 * columns represents the vector's length. For a column vector, the input array
	 * is expected to have 1 column and a positive number of rows, and this number
	 * of rows represents the vector's length.
	 * 
	 * @param data - a 2D array to hold the numbers of the vector
	 * @throws IllegalArgumentException if the numbers of rows and columns in the
	 *                                  input 2D array is not compatible with a row
	 *                                  or column vector
	 */
	public MathVector(double[][] data) {
		if (data.length == 0)
			throw new IllegalArgumentException("Number of rows must be positive.");
		if (data[0].length == 0)
			throw new IllegalArgumentException("Number of columns must be positive.");

		if (data.length == 1) {
			// This is a row vector with length = number of columns.
			this.isRowVector = true;
			this.vectorSize = data[0].length;
		} else if (data[0].length == 1) {
			// This is a column vector with length = number of rows.
			this.isRowVector = false;
			this.vectorSize = data.length;
		} else
			throw new IllegalArgumentException("Either the number of rows or the number of columns must be 1.");

		// Create the array and copy data over.
		if (this.isRowVector)
			this.data = new double[1][vectorSize];
		else
			this.data = new double[vectorSize][1];
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				this.data[i][j] = data[i][j];
			}
		}
	}

	/**
	 * Determines whether this vector is "equal to" another vector, where equality
	 * is defined as both vectors being row (or both being column), having the same
	 * vector length, and containing the same numbers in the same positions.
	 * 
	 * @param other - another vector to compare
	 */
	public boolean equals(Object other) {
		if (!(other instanceof MathVector))
			return false;

		MathVector otherVec = (MathVector) other;

		// Checks that each value of otherVec.data is equal to each value of this.data
		for (int i = 0; i < otherVec.data.length; i++) {
			for (int j = 0; j < otherVec.data[0].length; j++) {
				if (!(this.data[i][j] == otherVec.data[i][j])) {
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * Generates a returns a new vector that is the transposed version of this
	 * vector.
	 */
	public MathVector transpose() {
		// Creates a new vector with swapped dimensions of the original vector
		MathVector newVector = new MathVector(new double[this.data[0].length][this.data.length]);

		// Iterates over the original vector length in both dimensions and adds each
		// value to newVector with the dimensions swapped
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				newVector.data[j][i] = this.data[i][j];
			}
		}

		return newVector;
	}

	/**
	 * Generates and returns a new vector representing the sum of this vector and
	 * another vector.
	 * 
	 * @param other - another vector to be added to this vector
	 * @throws IllegalArgumentException if the other vector and this vector are not
	 *                                  both row vectors of the same length or
	 *                                  column vectors of the same length
	 */
	public MathVector add(MathVector other) {
		// Checks to make sure vectors are the same dimensions which by definition also
		// is checking whether they are both row or column vectors
		if (this.data.length != other.data.length || this.data[0].length != other.data[0].length)
			throw new IllegalArgumentException("Only vectors of the same dimmensions may be added together.");

		// Creates a new vector with swapped dimensions of the original vector
		MathVector newVector = new MathVector(new double[this.data.length][this.data[0].length]);

		// Iterates over the original vector length in both dimensions and adds each
		// value of the original vector and the other vector to newVector
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				newVector.data[i][j] = this.data[i][j] + other.data[i][j];
			}
		}

		return newVector;
	}

	/**
	 * Computes and returns the dot product of this vector and another vector.
	 * 
	 * @param other - another vector to be combined with this vector to produce the
	 *              dot product
	 * @throws IllegalArgumentException if the other vector and this vector are not
	 *                                  both row vectors of the same length or
	 *                                  column vectors of the same length
	 */
	public double dotProduct(MathVector other) {
		// Checks to make sure vectors are the same dimensions which by definition also
		// is checking whether they are both row or column vectors
		if (this.data.length != other.data.length || this.data[0].length != other.data[0].length)
			throw new IllegalArgumentException("Only vectors of the same dimmensions may be added together.");

		// Creates a dotProduct variable to be added to
		double dotProduct = 0;

		// Iterates over the original vector length in both dimensions and adds each
		// value of the original vector multiplied by the other vector to dotProduct
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				dotProduct += this.data[i][j] * other.data[i][j];
			}
		}
		
		return dotProduct;
	}

	/**
	 * Computes and returns this vector's magnitude (also known as a vector's
	 * length) .
	 */
	public double magnitude() {
		// Creates a temporary value to be added to
		double tempValue = 0;

		// Iterates over the original vector length in both dimensions and adds each
		// value of the original vector squared to tempValue
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				tempValue += Math.pow(this.data[i][j], 2);
			}
		}

		// Returns the square root of tempValue, completing the magnitude equation
		return Math.sqrt(tempValue);
	}

	/**
	 * Generates and returns a normalized version of this vector.
	 */
	public MathVector normalize() {
		// Creates a new vector
		MathVector newVector = new MathVector(new double[this.data.length][this.data[0].length]);

		// Calculates and stores the magnitude of the original vector
		double magnitude = this.magnitude();

		// Iterates over the original vector length in both dimensions and adds each
		// value of the original vector divided by the magnitude to newVector
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				newVector.data[i][j] = this.data[i][j] / magnitude;
			}
		}
		
		return newVector;
	}

	/**
	 * Generates and returns a textual representation of this vector. For example,
	 * "1.0 2.0 3.0 4.0 5.0" for a sample row vector of length 5 and "1.0 2.0 3.0
	 * 4.0 5.0" for a sample column vector of length 5. In both cases, notice the
	 * lack of a newline or space after the last number.
	 */
	public String toString() {
		// Creates a string to add values to so they can be printed
		String returnString = "";

		// Iterates over all values in a vector of any dimensions and adds each value to
		// a string spread out by spaces for values on the same row and lines for values
		// on different rows. The last value on a row or column is not followed by a
		// respective space or line.
		for (int i = 0; i < this.data.length; i++) {
			if (i == this.data.length - 1) {
				for (int j = 0; j < this.data[0].length; j++) {
					if (j < this.data[0].length - 1)
						returnString += this.data[i][j] + " ";
					else
						returnString += this.data[i][j];
				}
			} else {
				returnString += this.data[i][this.data[0].length - 1] + "\n";
			}
		}

		return returnString;
	}
}
