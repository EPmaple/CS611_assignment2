/*
 * SlidingPuzzlePiece.java
 * Contains all the functionality realted to a Sliding Puzzle piece
 */
public class SlidingPuzzlePiece extends Piece{
  private final int MIN_VALUE = 0;

  public SlidingPuzzlePiece(int value) {
    this.setValue(value);
  }

  public SlidingPuzzlePiece() {
    super();
  }

  // setter methods

  public void setValue(int value) {
    if (value < MIN_VALUE) { // min of 1
      throw new IllegalArgumentException(); // throwin an exception ends the method call
    }
    super.setValue(value + "");
  }

  public void setValue(String value) {
    if (value == null) {
      super.setValue(value);
    } else {
      int intValue = Integer.parseInt(value);
      this.setValue(intValue);
    }
  }

  public void setValue() {
    super.setValue(null);
  }
}
