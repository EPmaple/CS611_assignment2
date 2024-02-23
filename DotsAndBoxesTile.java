/*
 * DotsAndBoxesTile.java
 * Contains all the funcitonality related to a D&B tile.
 */
public class DotsAndBoxesTile extends Tile{
  public static final int LEFT = 1;
  public static final int TOP = 2;
  public static final int RIGHT = 3;
  public static final int BOTTOM = 4;
  // in the array, LEFT is 0, TOP is 1, and so on
  private Player[] edges = new Player[]{null, null, null, null};

  public DotsAndBoxesTile(DotsAndBoxesPiece piece, int x, int y) {
    super(piece, x, y);
  }

  public DotsAndBoxesTile(DotsAndBoxesPiece piece) {
    super(piece, 0, 0);
  }

  public DotsAndBoxesPiece getPiece() {
    return (DotsAndBoxesPiece) super.getPiece();
  }

  public String toString() {
    return getPiece() + "";
  }

  // edge related methods
  public void setEdge(int value, Player player) {
    if (value < LEFT || value > BOTTOM) {
      throw new IllegalArgumentException();
    }
    this.edges[value-1] = player;
  }

  public Player getEdge(int direction) {
    return edges[direction-1];
  }

  public boolean isEdgeDrawn(int value) {
    if (edges[value-1] != null) {
      return true;
    }
    return false;
  }

  public boolean isTileBoxed() {
    for (Player edge : edges) {
      if (edge == null) {
        return false;
      }
    }
    return true;
  }
}
