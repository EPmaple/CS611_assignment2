/*
 * Tile.java
 * Tiles that contribute to the structure of a Board.
 */
public abstract class Tile {
  private final int MIN_X = 0;
  private final int MIN_Y = 0;

  private Piece piece;
  private int x;
  private int y;

  public Tile(Piece piece, int x, int y) {
    this.setPiece(piece);
    this.setX(x);
    this.setY(y);
  }

  public Tile(int x, int y) {
    this(null, x, y);
  }

  public Tile() {
    this(0, 0);
  }

  // getter methods
  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public Piece getPiece() {
    return this.piece;
  }

  // setter methods

  public void setX(int x) {
    if (x < MIN_X) {
      throw new IllegalArgumentException();
    }
    this.x = x;
  }

  public void setY(int y) {
    if (y < MIN_Y) {
      throw new IllegalArgumentException();
    }
    this.y = y;
  }

  public void setPiece(Piece piece) {
    this.piece = piece;
  }

  @Override
  public abstract String toString();
}
