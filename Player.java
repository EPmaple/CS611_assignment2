/*
 * Player.java
 * Class to represent a player, manage player data and game stats.
 */
import java.util.Map;
import java.util.HashMap;

public class Player {
  private String username;
  private int id;
  private Map<String, int[]> gameStats;

  public Player(String username, int id) {
    setName(username);
    setID(id);
    gameStats = new HashMap<>();
  }

  // setter methods
  public void setName(String input) {
    String username = input.trim();

    if (username.isEmpty()) {
      System.out.println("Invalid input. Please enter a non-empty string.");
    }

    this.username = username;
  }

  public void setID(int id) {
    this.id = id;
  }

  // getter methods
  public String getInitials() {
    return getUsername().substring(0,1) + getID();
  }

  public String getUsername() {
    return this.username;
  }

  public int getID() {
    return this.id;
  }

  // either return the int array associated with the game, or initialize a new int array given there has not been one initialize for this game
  public int[] getGameStats(String gameName) {
    return gameStats.getOrDefault(gameName, new int[3]);
  }

  // Method to increment the stats of the player for the correponding game
  // s/he played
  public void incGames(String gameName, boolean hasWon, int movesOrBoxes) {
    int[] stats = getGameStats(gameName);
    stats[0]++; // increment games played

    if (hasWon) {
      stats[1]++; // increment games won
    }

    // increment moves for Sliding Puzzle or increment boxes for Dots and Boxes
    if (gameName.equals(Constants.SP_GAMENAME) || 
      gameName.equals(Constants.DB_GAMENAME)) {

      stats[2] += movesOrBoxes;
  }

    gameStats.put(gameName, stats);
  }

  // methods to print player statistics for Sliding Puzzle Game
  public void printSPGameStats() {
    int[] spGameStats = getGameStats(Constants.SP_GAMENAME);

    System.out.println("\n[+] Summary for " + username + ":");
    System.out.println("[+] Number of Sliding Puzzle games played: " + spGameStats[0]);
    System.out.println("[+] Number of Sliding Puzzle games won: " + spGameStats[1]);
    System.out.println("[+] Total number of moves made: " + spGameStats[2]);
  }

  // methods to print player statistics for Dots And Boxes Game
  public void printDBGameStats() {
    int[] dbGameStats = getGameStats(Constants.DB_GAMENAME);

    System.out.println("\n[+] Summary for " + username + ":");
    System.out.println("[+] Number of Dots and Boxes games played: " + dbGameStats[0]);
    System.out.println("[+] Number of Dots and Boxes games won: " + dbGameStats[1]);
    System.out.println("[+] Total number of boxes got: " + dbGameStats[2]);
  }

  public boolean isAgent() {
    return this instanceof DummyAgent;
  }

  @Override
  public String toString() {
    return username + id;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true; // same memory address
    // either obj is null or obj and this are not of the same class
    if (obj == null || getClass() != obj.getClass()) return false;
    Player player = (Player) obj;
    return (this.username == player.username && this.id == player.id);
  }
}
