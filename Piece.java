/*
 * Piece.java
 * Basic implementation of a piece in board games, template for designing 
  various board game pieces.
 */
public abstract class Piece {
  private String value;

  public Piece(String value) {
    setValue(value);
  }

  public Piece() {
    this(null);
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean isEmpty() {
    return value == null;
  }

  public String toString() {
    return isEmpty() ? " " : getValue();
  }
}
