/*
 * RandomGenerator.java
 * Generates a random board and checks if the board is 
  solvable.
 */
import java.util.Random;

public class RandomGenerator {

  // Generates a random board
  public static void generateRandomBoard(SlidingPuzzleBoard spBoard) {
    int[] values = new int[spBoard.getRows() * spBoard.getCols() - 1];
    for (int i = 0; i < values.length; i++) {
      values[i] = i;
    }

    RandomGenerator.shuffleArray(values);

    int index = 0;
    for (int i = 0; i < spBoard.getRows(); i++) {
      for (int j = 0; j < spBoard.getCols(); j++) {
        // Assign values to all tiles except the last one
        // System.out.println(i + ", " + j);
        if (index < values.length) {
          // System.out.println(index);
          spBoard.getTile(i, j).getPiece().setValue(values[index++] + "");
          // spBoard.getTile(i, j).setValue(values[index++]);
        }
      }
    }
  }

  // Shuffles the given array using the Fisher-Yates shuffle algorithm
  public static void shuffleArray(int[] array) {
    Random rand = new Random();

    // Fisher-Yates shuffle algo
    for (int i = array.length - 1; i > 0; i--) {
      int j = rand.nextInt(i + 1);

      // Swap array[i] with array[j]
      int temp = array[i];
      array[i] = array[j];
      array[j] = temp;
    }
  }

  // Checks if a generator board is solvable
  public static boolean isSolvable(SlidingPuzzleBoard spBoard) {
    int inversions = countInversions(spBoard);
    int emptyRow = spBoard.findEmptyTile().getX();

    if ( (spBoard.getRows() * spBoard.getCols()) % 2 == 1 ) {
      // Odd-sized board
      return inversions % 2 == 0;
    } else {
      // Even-sized board
      return (inversions + emptyRow) % 2 == 1;
    }
  }

  // Counts the number of inversion in a board
  private static int countInversions(SlidingPuzzleBoard spBoard) {
    int rows = spBoard.getRows();
    int cols = spBoard.getCols();
    int[] flattenBoard = new int[rows * cols - 1]; // excluding the empty tile
    int index = 0;

    // Flatten the board, excluding the empty tile
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j ++) {
        if (index < flattenBoard.length) {
          // post increment, assignt the value value to the current index, then increment the index
          // flattenBoard[index] = spBoard.getTile(i, j).getValue();
          flattenBoard[index] = Integer.parseInt(spBoard.getValueofTilePiece(i, j));
          index++;
        }
      }
    }

    // Count inversions
    int inversions = 0;
    for (int i = 0; i < flattenBoard.length - 1; i++) {
      for (int j = i + 1; j < flattenBoard.length; j++) {
        if (flattenBoard[i] > flattenBoard[j]) {
          inversions++;
        }
      }
    }

    return inversions;
  }
}
