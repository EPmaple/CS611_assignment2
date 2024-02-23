/*
 * SlidingPuzzleGame.java
 * Contains all the main logics related to playing a 
  Sliding Puzzle Game.
 */
public class SlidingPuzzleGame extends BoardGame{
  private Player player1;
  public SlidingPuzzleGame(SlidingPuzzleBoard spBoard, Player player) {
    super(spBoard);
    setPlayer(player);
  }

  public SlidingPuzzleBoard getCurrentBoard() {
    return (SlidingPuzzleBoard) super.getCurrentBoard();
  }

  public void setPlayer(Player player) {
    if (player == null){
      throw new IllegalArgumentException();
    }
    this.player1 = player;
  }
  public Player getPlayer() {
    return this.player1;
  }

  @Override
  public void printBoard() {
    System.out.print(getCurrentBoard());
  }

  @Override
  public int makeMove(InputHandler inputHandler, Player player) {
    boolean hasMadeValidMove = false;
    int moveStatus = 0;
    SlidingPuzzleBoard board = getCurrentBoard();

    while (!hasMadeValidMove) {
      int userInput = inputHandler.getIntInput("[+] Enter the value of the tile you want to slide, or -1 to quit: ");

      if (userInput == Constants.QUIT) {
        hasMadeValidMove = true;
        moveStatus = Constants.QUIT;

      } else if ( board.canTileMove(userInput) ) {
        SlidingPuzzleTile selectedTile = board.findTileByValue(userInput);
        SlidingPuzzleTile emptyTile = board.findEmptyTile();

        board.switchTileValues(selectedTile, emptyTile);
        hasMadeValidMove = true;
        moveStatus = 1;
      } else {
        System.out.println("[+] Invalid move. Please select a tile next to the empty space.");
      }
    }

    return moveStatus;
  }

  /*
   * The main game loop, which continues until either the user quits, or until
   * the user has won
   */
  @Override
  public int gameStart(InputHandler inputHandler) {
    int gameStatus = 0;
    int numOfMoves = 0;
    boolean hasWon = false;

    while (!hasWon) {
      printBoard();

      // Move status of -1 is to quit the game, not a move
      int moveStatus = makeMove(inputHandler, getPlayer());
      if (moveStatus == Constants.QUIT) {
        player1.incGames(Constants.SP_GAMENAME, hasWon, numOfMoves);
        gameStatus = Constants.QUIT;
        break;
        
      } else if (moveStatus == 0) { // The else if condition should not be possible
        // given the makeMove method is implemented correctly
        gameStatus = Constants.QUIT;
        break;
      }

      // we should only count valid moves made by the player
      numOfMoves++;
      if (getCurrentBoard().checkForWin()) {
        gameStatus = 1;
        hasWon = true;
        System.out.println(getCurrentBoard());
        System.out.println("[+] Congratulations! You've won!");
        player1.incGames(Constants.SP_GAMENAME, hasWon, numOfMoves);
      }
    }
    return gameStatus;
  }
}
