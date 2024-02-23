/*
 * SlidingPuzzleTile.java
 * Contains all the functionality realted to a Sliding Puzzle tile
 */
public class SlidingPuzzleTile extends Tile{
  public SlidingPuzzleTile(SlidingPuzzlePiece piece, int x, int y) {
    super(piece, x, y);
  }

  public SlidingPuzzleTile(SlidingPuzzlePiece piece) {
    super(piece, 0, 0);
  }

  public String toString() {
    return getPiece() + "";
  }
}
