/*
 * DotsAndBoxesPiece.java
 * Contains all the functionality related to a D&B piece.
 */
public class DotsAndBoxesPiece extends Piece{
  public DotsAndBoxesPiece(String value) {
    this.setValue(value);
  }

  public DotsAndBoxesPiece() {
    super();
  }

  public void setOccupancy(Player player) {
    if (player == null) {
      throw new IllegalArgumentException();
    }
    setValue(player.getInitials());
  }

  public boolean isOccupied() {
    return getValue() != null;
  }

  // setter methods
  public void setValue(String value) {
    // value is retrived using the getStringInput method
    // for which the value returned should be a valid string
    super.setValue(value);
  }

  public String toString() {
    return getValue();
  }
}
