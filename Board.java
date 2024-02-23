/*
 * Board.java
 * Template for designing various boards.
 */
public abstract class Board {
  private final int MIN_ROWS = 2;
  private final int MIN_COLS = 2;
  protected final int MIN_VALUE = 0;
  protected final int MAX_VALUE;

  private Tile[][] board;
  private int rows;
  private int cols;

  public Board(int rows, int cols) {
    this.setRows(rows);
    this.setCols(cols);
    this.setTiles(rows, cols);
    MAX_VALUE = rows * cols;
  }

  // setter methods
  public void setTiles(int rows, int cols) {
    if (rows < MIN_ROWS || cols < MIN_COLS) { // minimum of 2 rows
      throw new IllegalArgumentException(); // throwin an exception ends the method call
    }
    this.board = new Tile[rows][cols];
  }

  public void setRows(int r) {
    if (r < MIN_ROWS) { // minimum of 2 rows
      throw new IllegalArgumentException(); // throwin an exception ends the method call
    }
    this.rows = r;
  }

  public void setCols(int c) {
    if (c < MIN_COLS) { // minimum of 2 cols
      throw new IllegalArgumentException(); 
    }
    this.cols = c;
  } 

  // getter methods
  public Tile[][] getTiles() {
    return this.board;
  }

  public Tile getTile(int row, int col) {
    return board[row][col];
  }

  public String getValueofTilePiece(int row, int col) {
    return getTile(row, col).getPiece().getValue();
  }

  public int getRows() {
    return this.rows;
  }

  public int getCols() {
    return this.cols;
  }


  // abstract methods
  public abstract String toString();

  public abstract boolean checkForWin();

  /*
   * ******************************************
   * ******************************************
   */
  protected boolean isValueInbound(int value) {
    return MIN_VALUE <= value && value < MAX_VALUE;
  }

  public Tile findTileByValue(int value) {
    if (isValueInbound(value)) {
      int row = value / cols;
      int col = value % cols;

      return getTile(row, col);
    }
    return null;
  }
}
