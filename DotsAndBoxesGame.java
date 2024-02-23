/*
 * DotsAndBoxesGame.java
 * Contains all the core gameplay mechanics of the Dots and Boxes game.
 */

public class DotsAndBoxesGame extends BoardGame{
  private Player player1;
  private Player player2;
  public static final int ANOTHER_TURN = 2;

  public DotsAndBoxesGame(DotsAndBoxesBoard board) {
    super(board);
    setPlayers(board.getPlayer1(), board.getPlayer2());
  }
  
  public DotsAndBoxesBoard getCurrentBoard() {
    return (DotsAndBoxesBoard) super.getCurrentBoard();
  }

  @Override
  public void printBoard() {
    System.out.println(getCurrentBoard());
  }

  public Player getPlayer1() {
    return this.player1;
  }
  public Player getPlayer2() {
    return this.player2;
  }
  private void setPlayers(Player player1, Player player2) {
    if (player1 == null || player2 == null) {
      throw new IllegalArgumentException();
    }
    this.player1 = player1;
    this.player2 = player2;
  }

  private void dummyAgentMove(Player player) {
    DummyAgent dummyAgent = (DummyAgent) player;
    DotsAndBoxesBoard board = getCurrentBoard();

    boolean isTileBoxed = true;
    int tileSelection = Constants.INVALID_SELECTION;

    // Continue randomly until we get a valid tile selection
    while (isTileBoxed) {
      tileSelection = dummyAgent.generateRandomMoveInRange(board.MIN_VALUE, 
        board.MAX_VALUE-1);
      // System.out.println("tile selection by Ai: " + tileSelection);
      isTileBoxed = board.findTileByValue(tileSelection).isTileBoxed();
    }

    DotsAndBoxesTile currentTile = board.findTileByValue(tileSelection);
    // Tile is not boxed, meaning there is at least one edge available
    boolean isEdgeDrawn = true;
    int edgeSelection = Constants.INVALID_SELECTION;
    while (isEdgeDrawn) {
      edgeSelection = dummyAgent.generateRandomMoveInRange
        (DotsAndBoxesTile.LEFT, DotsAndBoxesTile.BOTTOM);
      // System.out.println("edge selection by Ai: " + edgeSelection);
      isEdgeDrawn = currentTile.isEdgeDrawn(edgeSelection);
    }
    
    board.drawEdge(currentTile, edgeSelection, player);
    System.out.println("[+] " + player.getInitials() + " has made at move at tile " + tileSelection + ", edge " + edgeSelection);
  }

  @Override
  public int makeMove(InputHandler inputHandler, Player player) {
    boolean hasMadeValidMove = false;
    int moveStatus = 0;
    DotsAndBoxesBoard board = getCurrentBoard();

    while (!hasMadeValidMove) {
      int tileInput = inputHandler.getIntInput("[+] Enter the value of the tile " + player + " wants to select, or -1 to quit: ");

      if (tileInput == Constants.QUIT) {
        hasMadeValidMove = true;
        moveStatus = Constants.QUIT;
      } else {
        DotsAndBoxesTile selectedTile = board.findTileByValue(tileInput);

        // Selected a valid tile, then go on to select a valid edge
        if (selectedTile != null) {
          boolean hasSelectedEdge = false;
          
          while (!hasSelectedEdge) {
            int edgeInput = inputHandler.getIntInput("[+] Enter the edge number "
             + player +" wants to select (1 = left edge, 2 = top edge, " 
             + "3 = right edge, 4 = bottom edge), 0 to go back to selecting a tile, "
             + "or -1 to quit playing: ");

            // To quit playing
            if (edgeInput == Constants.QUIT) {
              hasSelectedEdge = true;
              hasMadeValidMove = true;
              moveStatus = Constants.QUIT;
            // To go back to select a tile
            } else if (edgeInput == Constants.PREV_LEVEL) {
              hasSelectedEdge = true;
            } else if (edgeInput >= 0 && edgeInput <= 4) {
              if (selectedTile.isEdgeDrawn(edgeInput)) {
                System.out.println("[+] Invalid edge number. Please select a valid edge.");
              } else { // Has selected a valid edge
                board.drawEdge(selectedTile, edgeInput, player);
                hasSelectedEdge = true;
                hasMadeValidMove = true;
                moveStatus = 1;
              }
            } else {
              System.out.println("[+] Invalid edge number. Please select a valid edge.");
            }
          }

        } else {
          System.out.println("[+] Invalid tile value. Please select a valid tile.");
        }
      }
    }
    return moveStatus;
  }

  @Override
  public int gameStart(InputHandler inputHandler) {
    int gameStatus = 0;
    boolean hasWon = false;
    Player currentPlayer = null;

    int firstToTakeTurn = inputHandler.getIntInput("[+] Enter 0 to let " + player1 + " go first, 1 to let " + player2 + " go first, or -1 to quit:");

    switch (firstToTakeTurn) {
      case 0:
        currentPlayer = player1;
        break;
      case 1:
        currentPlayer = player2;
        break;
      default:
        break;
    }

    while (!hasWon) {
      int moveStatus = 1;
      printBoard();

      if (currentPlayer.isAgent()) {
        dummyAgentMove(currentPlayer);
      } else {
        moveStatus = makeMove(inputHandler, currentPlayer);
      }
      

      if (moveStatus == Constants.QUIT) {
        int[] boxes = getCurrentBoard().getBoxes();
        player1.incGames(Constants.DB_GAMENAME, false, boxes[0]);
        player2.incGames(Constants.DB_GAMENAME, false, boxes[1]);
        gameStatus = Constants.QUIT;
        break;
      }

      boolean anotherTurn = getCurrentBoard().assignBoxedTiles(currentPlayer);

      if(getCurrentBoard().checkForWin()) {
        gameStatus = 1;
        hasWon = true;
        System.out.println(getCurrentBoard());
        int[] boxes = getCurrentBoard().getBoxes();

        // Player1 won
        if (boxes[0] > boxes[1]) {
          System.out.println("[+] " + boxes[0] + ":" + boxes[1] + " Congratulations! " + player1 + " has won!");
          player1.incGames(Constants.DB_GAMENAME, true, boxes[0]);
          player2.incGames(Constants.DB_GAMENAME, false, boxes[1]);
        // Player2 won
        } else if (boxes[0] < boxes[1]) {
          System.out.println("[+] " + boxes[0] + ":" + boxes[1] + " Congratulations! " + player2 + " has won!");
          player1.incGames(Constants.DB_GAMENAME, false, boxes[0]);
          player2.incGames(Constants.DB_GAMENAME, true, boxes[1]);
        } else { // Tie
          System.out.println("[+] " + boxes[0] + ":" + boxes[1] + " It is a tie!");
          player1.incGames(Constants.DB_GAMENAME, true, boxes[0]);
          player2.incGames(Constants.DB_GAMENAME, false, boxes[1]);
        }
        break;
      }
      
      if (!anotherTurn) { // Player gets another turn after successfully boxing a tile
        currentPlayer = (currentPlayer.equals(getPlayer1()) ? getPlayer2() : getPlayer1());
      }
    }
    return gameStatus;
  }
}
