/*
 * SlidingPuzzleBoard.java
 * Contains the implementations for a Sliding Puzzle 
  Board.
 */
public class SlidingPuzzleBoard extends Board{
  // By the constructor, only a Board of blank Tiles is constructed
  public SlidingPuzzleBoard(int rows, int cols) {
    super(rows, cols);
    setTiles(rows, cols);
  }

  // getter methods
  @Override
  public SlidingPuzzleTile getTile(int row, int col) {
    return (SlidingPuzzleTile) super.getTile(row, col);
  }

  // setter methods
  public void setTiles(int rows, int cols) {
    super.setTiles(rows, cols);
    initDefaultBoard(rows, cols);
    generateSolvableBoard();
  }

  // common methods
  @Override
  public SlidingPuzzleTile findTileByValue(int value) {
    String strValue = value + "";
    if (isValueInbound(value)) {
      for (int i = 0; i < this.getRows(); i++) {
        for (int j = 0; j < this.getCols(); j++) {
          SlidingPuzzleTile currentTile = getTile(i, j);

          String pieceValue = currentTile.getPiece().getValue();
          
          if (pieceValue != null && pieceValue.equals(strValue)) {
            return currentTile;
          }
        }
      }
    }
    return null;
  }

  public boolean checkForWin() {
    int expectedValue = 0;
    int boardRow = getRows();
    int boardCol = getCols();

    for (int i = 0; i < boardRow; i++) {
      for (int j = 0; j < boardCol; j++) {
        if (i == boardRow - 1 && j == boardCol - 1) {
          // Skip the last tile, as it should be empty
          continue;
        }
        String str = getValueofTilePiece(i, j);

        // meaning the empty tile is at other positions other than the last one in the board
        if (str == null) {
          return false;
        }

        // if we got to this step, then the value is not null => tile is not empty
        try {
          int value = Integer.parseInt(str);
          if (value != expectedValue++) {
            return false;
          }
        } catch (NumberFormatException err) {
          System.out.println("Error from method checkForWin(): the string cannot be parsed as an integer: " + str);
          System.out.println("Exception message: " + err.getMessage());
        }
      }
    }
    // At this step, all tiles are in order
    return true;
  }

  private void initDefaultBoard(int rows, int cols) {
    int index = 0;
    Tile[][] board = getTiles();

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++)  {
        // index++;

        if (i == rows-1 && j == cols-1) { // empty tile
          board[i][j] = new SlidingPuzzleTile(new SlidingPuzzlePiece(), i, j);
        } else { // non-empty tile
          board[i][j] = new SlidingPuzzleTile(new SlidingPuzzlePiece(index), i, j); // Initialize each tile
        }
        index++;
      }
    }
  }

  // Keep on generating a random Board until we've come up with one that is 
  // solvable
  private void generateSolvableBoard() {
    while (true) {
      RandomGenerator.generateRandomBoard(this);

      if (RandomGenerator.isSolvable(this) && !checkForWin()) {
        break;
      }
    }
  }

  /*
   * ************************************************
   * ************************************************
   */

  public void switchTileValues(SlidingPuzzleTile tile1, SlidingPuzzleTile tile2) {
    String tile1Value = tile1.getPiece().getValue();

    tile1.getPiece().setValue(tile2.getPiece().getValue());
    tile2.getPiece().setValue(tile1Value);
  }

  private boolean isTileEmpty(SlidingPuzzleTile tile) {
    return tile.getPiece().isEmpty();
  }

  public SlidingPuzzleTile findEmptyTile() {
    // System.out.println("rows: " + getRows() + "cols: " + getCols());

    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getCols(); j++) {
        SlidingPuzzleTile currentTile = this.getTile(i, j);
        if (isTileEmpty(currentTile)) {
          return currentTile;
        }
      }
    }
    // If no empty tile is found, which should not be the case if a valid board is produced
    System.out.println("You may not have remember to initialize a board with an empty tile.");
    return null;
  }

  public boolean isNextToEmptyTile(SlidingPuzzleTile tile1) {
    return isNextToTile(tile1, findEmptyTile());
  }

  private boolean isNextToTile(SlidingPuzzleTile tile1, SlidingPuzzleTile tile2) {
    return ((Math.abs(tile1.getX() - tile2.getX()) == 1 && tile1.getY() == tile2.getY()) ||
    (Math.abs(tile1.getY() - tile2.getY()) == 1 && tile1.getX() == tile2.getX()));
  }

  // Checks if a tile can be moved into the empty slot
  public boolean canTileMove(int tileValue) {
    if (isValueInbound(tileValue)) {
      SlidingPuzzleTile selectedTile = findTileByValue(tileValue);
      SlidingPuzzleTile emptyTile = findEmptyTile();

      // a Tile should not be null if the setTiles method of the Board class
      // is implemented correctly
      if (selectedTile != null && emptyTile != null && isNextToTile(selectedTile, emptyTile)) {
        // Check if the selected tile is next to the empty tile
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < getRows(); i++) {
      // Top border of a row
      for (int j = 0; j < getCols(); j++) {
        result.append("+---");
      }
      result.append("+\n");

      // Cell contents
      for (int j = 0; j < getCols(); j++) {
        SlidingPuzzleTile currentTile = getTile(i, j);
        boolean isValidMove = isNextToEmptyTile(currentTile);

        // Use ANSI escape codes for color
        String colorCode = isValidMove ? "\u001B[32m" : "\u001B[0m";
            
        result.append("| ").append(colorCode).append(currentTile).append("\u001B[0m ");
      }
      result.append("|\n");
    }

    // Bottom border of the last row
    for (int j = 0; j < getCols(); j++) {
      result.append("+---");
    }
    result.append("+\n");

    return result.toString();
  }
}
