/*
 * Team.java
 * Contains functionality to manage a team of players
 */
public class Team {
  private final int MIN_TEAM_SIZE = 1;

  private Player[] team;
  private int numOfPlayers; 
  private String name;

  public Team(int size, String name) {
    initTeam(size, name);
  }

  // teamName is acquired through the InputHandler, which should be a valid string
  private void initTeam(int size, String name) {
    if (size < MIN_TEAM_SIZE) {
      throw new IllegalArgumentException();
    }
    this.team = new Player[size];
    setNumofPlayers(0);
    setName(name);
  }

  // setter methods
  private void setNumofPlayers(int num) {
    this.numOfPlayers = num;
  }

  private void setName(String name) {
    this.name = name;
  }

  // getter methods
  public int getNumofPlayers() {
    return this.numOfPlayers;
  }

  public Player[] getPlayers() {
    return this.team;
  }

  public String getName() {
    return this.name;
  }

  /*
   * *****************************************
   * *****************************************
   */

  // method to add a player to team, method does handle the case when the number
  // of players would exceed the capacity of the Player[] array
  public void addPlayer(Player player) {
    int teamLength = team.length;
    if (numOfPlayers == teamLength) {
      Player[] newArray = new Player[teamLength * 2];
      System.arraycopy(team, 0, newArray, 0, teamLength);
      team = newArray;
    }
    team[numOfPlayers++] = player;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Team ").append(name).append(": {");

    for (int i = 0; i < numOfPlayers; i++) {
      if (i > 0) {
        sb.append(", "); // Add comma separator
      }
      sb.append(team[i].getUsername());
    }

    sb.append("}");
    return sb.toString();
  }
}
