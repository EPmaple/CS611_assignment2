/*
 * DotsAndBoxesBoard.java
 * Contains all the related logic to a DB board.
 */
public class DotsAndBoxesBoard extends Board{
  private Player player1;
  private Player player2;

  public DotsAndBoxesBoard(int rows, int cols, Player player1, Player player2) {
    super(rows,cols);
    setPlayers(player1, player2);
  }

  // getter methods
  public Player getPlayer1() {
    return this.player1;
  }
  public Player getPlayer2() {
    return this.player2;
  }

  // setter methods
  private void setPlayers(Player player1, Player player2) {
    if (player1 == null || player2 == null) {
      throw new IllegalArgumentException();
    }
    this.player1 = player1;
    this.player2 = player2;
  }

  public void setTiles(int rows, int cols) {
    super.setTiles(rows, cols);
    // maybe additional logics for this board
    initDefaultBoard(rows, cols);
  }

  // common methods
  private void initDefaultBoard(int rows, int cols) {
    Tile[][] board = getTiles();
    
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        board[i][j] = new DotsAndBoxesTile(new DotsAndBoxesPiece(), i, j);
      }
    }
  }

  // Winning condition is simply is all tiles have been occupied
  public boolean checkForWin() {
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getCols(); j++) {
        DotsAndBoxesTile currentTile = getTile(i, j);
        if (!currentTile.isTileBoxed() || !currentTile.getPiece().isOccupied()) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public DotsAndBoxesTile findTileByValue(int value) {
    return (DotsAndBoxesTile) super.findTileByValue(value);
  }

  public DotsAndBoxesTile getTile(int row, int col) {
    return (DotsAndBoxesTile) super.getTile(row, col);
  }

  // Method to be run after the user has made a valid move to draw an edge
  // And all boxes resulted from this move will be assigned to this player
  public boolean assignBoxedTiles(Player player) {
    boolean anotherTurn = false;
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getCols(); j++) {
        DotsAndBoxesTile currentTile = getTile(i, j);

        // the tile is boxed
        if (currentTile.isTileBoxed()) {
          // but a player name has not been assigned to the piece
          // then it means the tile is one that we just boxed with 
          // our move
          if (!currentTile.getPiece().isOccupied()) {
            currentTile.getPiece().setOccupancy(player);
            anotherTurn = true;
          }
        }
      }
    }
    return anotherTurn;
  }

  // Tally up the boxes that are occupied and assign them accordingly to each player
  public int[] getBoxes() {
    int[] boxes = new int[]{0, 0};
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getCols(); j++) {
        String tileOccupancy = getTile(i, j).getPiece().toString();
        if (tileOccupancy != null) {
          if (player1.getInitials().equals(tileOccupancy)) {
            boxes[0] += 1;
          } else {
            boxes[1] += 1;
          }
        }
      }
    }
    return boxes;
  }

  public DotsAndBoxesTile findTileByCoordinates(int x, int y) {
    if (x < Constants.MIN_X || y < Constants.MIN_Y || x >= getRows() || y >= getCols()) {
      return null;
    }

    // x and y are inbound
    int tileValue = x * getCols() + y;
    return findTileByValue(tileValue);
  }

  // first find target tile and draw the corresponding edge
  // then find neighbor tile, and if neighbor tile is not returned
  // as null, we will set the corresponding edge of the neighbor tile
  public void drawEdge(DotsAndBoxesTile currentTile, int edgeValue, Player player) {
    // Set the corresponding edge of the target tile
    int currentTileX = currentTile.getX();
    int currentTileY = currentTile.getY();
    currentTile.setEdge(edgeValue, player);
    
    DotsAndBoxesTile neighborTile = null;
    int neighborEdgeValue = Constants.INVALID_SELECTION;
    switch (edgeValue) {
      // TOP of target means BOTTOM of neighbor
      case DotsAndBoxesTile.LEFT:
        neighborTile = findTileByCoordinates(currentTileX, currentTileY-1);
        neighborEdgeValue = DotsAndBoxesTile.RIGHT;
        break;
      case DotsAndBoxesTile.TOP:
        neighborTile = findTileByCoordinates(currentTileX-1, currentTileY);
        neighborEdgeValue = DotsAndBoxesTile.BOTTOM;
        break;
      case DotsAndBoxesTile.RIGHT:
        neighborTile = findTileByCoordinates(currentTileX, currentTileY+1);
        neighborEdgeValue = DotsAndBoxesTile.LEFT;
        break;
      case DotsAndBoxesTile.BOTTOM:
        neighborTile = findTileByCoordinates(currentTileX+1, currentTileY);
        neighborEdgeValue = DotsAndBoxesTile.TOP;
        break;
      default:
        break;
    }

    // Set the edge of the neighboring tile as well
    if (neighborTile != null) {
      neighborTile.setEdge(neighborEdgeValue, player);
    }
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    int maxTileContentLength = 12;
    int rows = getRows();
    int cols = getCols();

    // Iterate through each row of the board
    for (int i = 0; i < rows; i++) {
      // Print top lines and dots
      for (int j = 0; j < cols; j++) {
        DotsAndBoxesTile currentTile = getTile(i, j);
        result.append(".");
        Player edge = currentTile.getEdge(DotsAndBoxesTile.TOP);
        if (edge != null) {
          result.append(edge.equals(player1) ? "\u001B[34m" : "\u001B[31m").append("------------").append("\u001B[0m");
        } else {
          result.append("            ");
        }
      }
      result.append(".\n");

      // Print vertical lines and tile contents
      for (int j = 0; j < cols; j++) {
        DotsAndBoxesTile currentTile = getTile(i, j);
        Player edge = currentTile.getEdge(DotsAndBoxesTile.LEFT);
        if (edge != null) {
          result.append(edge.equals(player1) ? "\u001B[34m|" : "\u001B[31m|").append("\u001B[0m");
        } else {
          result.append(" ");
        }

        String tileContent = "";
        if ((currentTile.getPiece()).getValue() != null) {
          String name = (currentTile.getPiece().toString().equals(player1.getInitials()) ? "\u001B[34m" + currentTile.toString() : "\u001B[31m" + currentTile.toString()) + ("\u001B[0m");

          tileContent = name + " (" + (currentTile.getX() * cols + currentTile.getY()) + ")";

          result.append(tileContent);

          for (int k = 0; k < 6; k ++) {
            result.append(" ");
          }

        } else {
          tileContent = currentTile.toString() + " (" + (currentTile.getX() * cols + currentTile.getY()) + ")";
          result.append(tileContent);
        }
        // Pad the tile content to ensure fixed width
        int paddingSpaces = maxTileContentLength - tileContent.length();

        for (int k = 0; k < paddingSpaces; k++) {
          result.append(" ");
        }
      }

      // Print last vertical line for the last tile in the row
      DotsAndBoxesTile lastTile = getTile(i, cols-1);
      Player edge = lastTile.getEdge(DotsAndBoxesTile.RIGHT);
      if (edge != null) {
        result.append(edge.equals(player1) ? "\u001B[34m|" : "\u001B[31m|").append("\u001B[0m").append("\n");
      } else {
        result.append(" \n");
      }
    }

    // Print bottome lines and dots for the last row
    for (int j = 0; j < cols; j++) {
      result.append(".");
      DotsAndBoxesTile currentTile = getTile(rows - 1, j);
      Player edge = currentTile.getEdge(DotsAndBoxesTile.BOTTOM);
      if (edge != null) {
        result.append(edge.equals(player1) ? "\u001B[34m" : "\u001B[31m").append("------------").append("\u001B[0m");
      } else {
        result.append("            ");
      }
    }
    result.append(".\n");

    return result.toString();
  }
}
