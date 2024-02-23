/*
 * GameConsole.java
 * Serves as the main user interface and handles general user 
  interactions.
 */

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class GameConsole {
  private final int MIN_TEAMS = 1;
  private final int TO_ADD_PLAYERS = 98;
  private final int TO_ADD_TEAMS = 99;

  // private GameConfiguration gameConfig = new GameConfiguration();
  private InputHandler inputHandler = new InputHandler();

  private Team[] teams;
  private int numOfTeams;
  private int index = 0;

  /*
     * When we ask for which team does the user want to pick from, we show the following
     * (0) Team CN: Tony, Xiao
     * When we ask for which player does the user want to pick from, we show the following
     * Team CN: (0) Tony, (1) Xiao
     */
  private Player[] selectTeamAndPlayer(int iteration) {
    // pass in an integer to represent the number of players the user want to pick
    // initialize a variable playersToBeReturned to hold the players to be returned
    // then we do a for loop with the following code, but for every player in 
    // playersToBeReturned, we will take them out when printing and will not display them as 
    // valid choices
    Player[] playersToBeReturned = new Player[iteration];
    Set<Player> addedPlayers = new HashSet<>();

    for (int i = 0; i < iteration; i++) {
      // To display to the user available teams
      StringBuilder sb = new StringBuilder();
      sb.append("\n[+] Teams:");

      for (int j = 0; j < numOfTeams; j++) {
        Team currentTeam = teams[j];
        Player[] currentPlayers = currentTeam.getPlayers();
        ArrayList<Player> playerList = new ArrayList<>();

        for (int k = 0; k < currentTeam.getNumofPlayers(); k++) {
          Player currentPlayer = currentPlayers[k];
          if (!addedPlayers.contains(currentPlayer)) {
            playerList.add(currentPlayer);
          }
        }

        if (playerList.size() != 0) {
          sb.append("\n[+] (").append(j).append(") Team ");
          sb.append(currentTeam.getName()).append(": ");
          sb.append(playerList.toString());
        }
      }
      System.out.println(sb.toString());

      // To get a valid team selection from the user
      int selectedTeamIndex = Constants.INVALID_SELECTION;
      do {
        selectedTeamIndex = inputHandler.getIntInput("[+] Select a team (Enter the team number), enter 999 if you would like to select a dummy AI player, or enter -1 to quit:");
        if (selectedTeamIndex == Constants.QUIT) {
          return null;
        }
      } while ((selectedTeamIndex < Constants.MIN_INDEX || selectedTeamIndex >= numOfTeams) && selectedTeamIndex != Constants.AI_SELECTION);

      if (selectedTeamIndex == Constants.AI_SELECTION) {
        playersToBeReturned[i] = (Player) new DummyAgent("AI", index++);
        continue;
      }
    
      Team selectedTeam = teams[selectedTeamIndex];

      // Display to the user the players available to selection from the specified team
      System.out.println("[+] Players in Team " + selectedTeam.getName() + ":");
      Player[] playersInSelectedTeam = selectedTeam.getPlayers();
      for (int j = 0; j < playersInSelectedTeam.length; j++) {
        Player currentPlayer = playersInSelectedTeam[j];
        if (!addedPlayers.contains(currentPlayer)) {
          System.out.println("[+] (" + j + ") " + playersInSelectedTeam[j].getUsername());
        }
      }

      // To get a valid player selection from the user
      int selectedPlayerIndex = Constants.INVALID_SELECTION;
      do {
        selectedPlayerIndex = inputHandler.getIntInput("[+] Select a player (Enter the player number), or enter -1 to quit:");
        if (selectedPlayerIndex == Constants.QUIT) {
          return null;
        }
      } while (selectedPlayerIndex < 0 || selectedPlayerIndex > playersInSelectedTeam.length);

      playersToBeReturned[i] = playersInSelectedTeam[selectedPlayerIndex];
      addedPlayers.add(playersInSelectedTeam[selectedPlayerIndex]);
    }

    return playersToBeReturned;
  }

  /*
   * Displays a list of available board games, prompts the user to choose a
   * game, and returns the user's choice.
   */
  private int getGameChoice() {
    // currentPlayer.getUsername()
    System.out.println("\n[+] Hello! Find below a list of board games to play from: ");

    for (int i = 1; i <= GameConfiguration.getMaxGameIndex(); i++) {
      System.out.println("[+] " + i + ": " + GameConfiguration.getGameName(i));
    }

    int choice;
    do {
      choice = inputHandler.getIntInput("[+] Enter the integer for your choice of game, 98 to add new players, 99 to add new teams, or -1 to quit: ");

      if (choice == Constants.QUIT || (choice > Constants.MIN_INDEX && choice <= GameConfiguration.getMaxGameIndex()) || choice == TO_ADD_PLAYERS || choice == TO_ADD_TEAMS) {
        break;
      }
    } while (true);

    return choice;
  }

  private void setNumOfTeams(int num) {
    this.numOfTeams = num;
  }

  // To add team to the array Teams, which the following algo is designed
  // to allow for the expansion of teams
  private void addTeam(Team team) {
    int teamsLength = teams.length;
    if (numOfTeams == teamsLength) {
      Team[] newArray = new Team[teamsLength * 2];
      System.arraycopy(teams, Constants.MIN_INDEX, newArray, Constants.MIN_INDEX, teamsLength);
      teams = newArray;
    }
    teams[numOfTeams++] = team;
  }

  // Method specifically written to allow the user to only add new teams
  private void addTeams() {
    int teamsToAdd;
    do {
      teamsToAdd = inputHandler.getIntInput("\n[+] Enter the number of teams you want to make (min is 1), or -1 to quit:");
      if (teamsToAdd == Constants.QUIT) {
        return;
      }
    } while (teamsToAdd < MIN_TEAMS);

    for (int i = 0; i < teamsToAdd; i ++) {
      String teamName = inputHandler.getStringInput("\n[+] Enter the name for your team, or q to quit:");
      if (teamName.equals("q")) {
        return;
      }

      int teamSize;
      do {
        teamSize = inputHandler.getIntInput("\n[+] Enter the team size for Team " + teamName + " (min is 1), or -1 to quit:");
        if (teamSize == Constants.QUIT) {
          return;
        }
      } while (teamSize < MIN_TEAMS);

      Team team = new Team(teamSize, teamName);
      addTeam(team);
      System.out.println("[+] Team " + teamName + " successfully added.");
    }
  }

  // Method specifically written to allow the user to only add new players
  private void addPlayers() {
    while (true) {
      // print all teams along with their members
      StringBuilder sb = new StringBuilder();
      sb.append("\n[+] Teams:");
      for (int i = 0; i < numOfTeams; i++) {
        Team currentTeam = teams[i];
        sb.append("\n[+] (").append(i).append(") ").append(currentTeam);
      }
      System.out.println(sb.toString());

      // to get valid team
      int teamInput = Constants.INVALID_SELECTION;
      while (!(teamInput == Constants.QUIT) && !(0 <= teamInput && teamInput < numOfTeams)) {
        teamInput = inputHandler.getIntInput("[+] Enter the number corresponding to the team you would like to add a member to, or -1 to quit: ");
      }
      if (teamInput == Constants.QUIT) {
        return;
      }

      Team selectedTeam = teams[teamInput];
      String playerName = inputHandler.getStringInput("[+] Enter the name for the player you would like to add to Team " + selectedTeam.getName() +":");
      
      selectedTeam.addPlayer(new Player(playerName, index++));
      System.out.println("[+] Player named " + playerName +" successfully added.");
    }
  }

  // Method to initialize the teams and players 
  private int initTeams() {
    int numOfTeams;
    do {
      numOfTeams = inputHandler.getIntInput("\n[+] Enter the number of teams you want to make (min is 1), or -1 to quit:");
      if (numOfTeams == Constants.QUIT) {
        return Constants.QUIT;
      }
    } while (numOfTeams < MIN_TEAMS);

    this.teams = new Team[numOfTeams];
    setNumOfTeams(0);

    for (int i = 0; i < numOfTeams; i++) {
      String teamName = inputHandler.getStringInput("\n[+] Enter the name for your team, or q to quit:");
      if (teamName.equals("q")) {
        return Constants.QUIT;
      }

      int teamSize;
      do {
        teamSize = inputHandler.getIntInput("\n[+] Enter the team size for Team " + teamName + " (min is 1), or -1 to quit:");
        if (teamSize == Constants.QUIT) {
          return Constants.QUIT;
        }
      } while (teamSize < MIN_TEAMS);

      Team team = new Team(teamSize, teamName);
      addTeam(team);

      for (int j = 0; j < teamSize; j++) {
        String playerName = inputHandler.getStringInput("\n[+] Enter the name for a player on Team " + teamName + ", or q to quit:");
          if (playerName.equals("q")) {
            return Constants.QUIT;
          }

        Player player = new Player(playerName, index++);
        team.addPlayer(player);
      }
    }
    return 1;
  }


  /*
   * ************************************************
   * ************************************************
   */

  private void printWelcomeMessage() {
    System.out.println("[+] Welcome to the Board Games!");
  }

  /*
   * ************************************************
   * ************************************************
   */

  // Initiates the game loop, allowing the user to choose and play different 
  // board games until they choose to quit by entering -1
  public void startGame() {
    printWelcomeMessage();
    int gameStatus = 1;

    gameStatus = initTeams();
    if (gameStatus == Constants.QUIT) {
      System.out.println("\n[+] Goodbye! Thanks for playing.");
      return;
    }

    while (true) {
      int choice = getGameChoice();
      if (choice == Constants.QUIT) {
        break;
      }
      playSelectedGame(choice);
    }

    System.out.println("\n[+] Goodbye! Thanks for playing.");
  }

  /*
   * A switch to execute appropriate methods to play the selected board game
   * based on the user's choice
   */
  private void playSelectedGame(int choice) {
    switch (choice) {
      case 1:
        playSlidingPuzzleGame();
        break;
      case 2:
        playDotsAndBoxesGame();
        break;
      case 98:
        addPlayers();
        break;
      case 99:
        addTeams();
        break;
      default:
        break;
    }
  }

  private void playDotsAndBoxesGame() {
    System.out.println("[+] Welcome to Dots and Boxes!");
    boolean playAgain = true;
    Player player1 = null;
    Player player2 = null;

    while (playAgain) {

      Player[] players = selectTeamAndPlayer(2);
      if (players == null) {
        return;
      }
      player1 = players[0];
      player2 = players[1];

      int rows;
      do {
        rows = inputHandler.getIntInput("[+] Please enter the number of rows you want your board to be (min is 2), or -1 to quit: ");
      } while (rows != Constants.QUIT && rows < Constants.MIN_ROWS);
      if (rows == Constants.QUIT) {
        break;
      }

      int cols;
      do {
        cols = inputHandler.getIntInput("[+] Please enter the number of cols you want your board to be (min is 2), or -1 to quit: ");
      } while (cols != Constants.QUIT && cols < Constants.MIN_COLS);
      if (cols == Constants.QUIT) {
        break;
      }

      DotsAndBoxesBoard dbBoard = new DotsAndBoxesBoard(rows, cols, player1, player2);
      DotsAndBoxesGame dbGame = new DotsAndBoxesGame(dbBoard);

      int result = dbGame.gameStart(inputHandler);
      boolean gameStatus = (result == 1) ? true : false;

      if (!gameStatus) {
        break;
      }

      playAgain = inputHandler.getBooleanInput("[+] Do you want to play another round of Dots and Boxes? (y for yes, n for no): ");
    }
    player1.printDBGameStats();
    player2.printDBGameStats();
  }

  /*
   * Allow the user to enter the dimensions of the board s/he wants to have.
   * Executes the main game loop for Sliding Puzzle Game
   * Checks if the user wants to play another round of this game
   */
  private void playSlidingPuzzleGame() {
    System.out.println("[+] Welcome to Sliding Puzzle Game!");

    int iteration = 0;
    boolean playAgain = true;
    Player player1 = null;

    while (playAgain) {
      // after the first iteration of the game, we ask if the user would
      // like to play the game again as the same player 
      if (iteration > 0) {
        boolean playAsSameCharacterAgain = inputHandler.getBooleanInput("[+] Would you like to play as " + player1.getUsername() +" again? y for yes, n for no");
        if (!playAsSameCharacterAgain) {
          // Since the user chose to play as a different player, we print
          // out the stats for the previous player and ask the user for
          // a potentially different player input
          player1.printSPGameStats();
          player1 = selectTeamAndPlayer(1)[0];
        }
      } else {
        // this is a first iteration of the game
        player1 = selectTeamAndPlayer(1)[0];
      }
      // if the user decides to quit at player selection phase, then there's
      // either the stats have been printed or there is no stat for null
      if (player1 == null) {
        return;
      }
      iteration++;

      // by this point, the user have selected a player to play as for 
      // Sliding Puzzle Game
      int rows;
      do {
        rows = inputHandler.getIntInput("[+] Please enter the number of rows you want your board to be (min is 2), or -1 to quit: ");
      } while (rows != Constants.QUIT && rows < Constants.MIN_ROWS);
      if (rows == Constants.QUIT) {
        break;
      }

      int cols;
      do {
        cols = inputHandler.getIntInput("[+] Please enter the number of cols you want your board to be (min is 2), or -1 to quit: ");
      } while (cols != Constants.QUIT && cols < Constants.MIN_COLS);
      if (cols == Constants.QUIT) {
        break;
      }

      SlidingPuzzleBoard spBoard = new SlidingPuzzleBoard(rows, cols);
      // if we pass in the player to the spGame, then incrementing the stats of the player can be taken care of in sliding puzzle game, and then gamestart would only have to return the single key of gamestatus
      SlidingPuzzleGame spGame = new SlidingPuzzleGame(spBoard, player1);

      int result = spGame.gameStart(inputHandler);
      boolean gameStatus = (result == 1) ? true : false;

      if (!gameStatus) {
        break;
      }

      playAgain = inputHandler.getBooleanInput("[+] Do you want to play another round of SlidingPuzzleGame? (y for yes, n for no): ");
    }
    player1.printSPGameStats();
  }
}
