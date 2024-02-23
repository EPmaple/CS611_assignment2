/*
 * BoardGame.java 
 * Template for designing various board games.
 */
public abstract class BoardGame {
  private Board currentBoard;

  public BoardGame(Board board) {
    this.setCurrentBoard(board);
  }

  public void setCurrentBoard(Board board) {
    this.currentBoard = board;
  }

  public Board getCurrentBoard() {
    return this.currentBoard;
  }

  public abstract void printBoard();

  // public abstract boolean checkForWin();

  public abstract int makeMove(InputHandler inputHandler, Player player);

  public abstract int gameStart(InputHandler inputHandler);
}
