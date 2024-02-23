## CS611-Assignment 2
---------------------------------------------------------------------------
Tony Cen Cen
tcen17@bu.edu
U30361395

## Files
---------------------------------------------------------------------------
Board.java: Template for designing various boards.
BoardGame.java: Template for designing various board games.
Constants.java: Class to organize the static contants that may need used throughout
  the program.
DotsAndBoxesBoard.java: Contains all the related logic to a DB board.
DotsAndBoxesGame.java: Contains all the core gameplay mechanics of the Dots and Boxes game.
DotsAndBoxesPiece.java: Contains all the functionality related to a D&B piece.
DotsAndBoxesTile.java: Contains all the funcitonality related to a D&B tile.
DummyAgent.java: A dummy that extends from player which makes random moves
GameConfiguration.java: Dynamically loads and retrieves game configuration 
  properties from a file “config.properties”.
GameConsole.java: Serves as the main user interface and handles general user 
  interactions.
InputHandler.java: Provides methods for getting user inputs from the console.
Main.java: Entry point of the program.
Piece.java: Basic implementation of a piece in board games, template for designing 
  various board game pieces.
Player.java: Class to represent a player, manage player data and game stats.
RandomGenerator.java: Generates a random board and checks if the board is 
  solvable.
SlidingPuzzleBoard.java: Contains the implementations for a Sliding Puzzle 
  Board.
SlidingPuzzleGame.java: Contains all the main logics related to playing a 
  Sliding Puzzle Game.
SlidingPuzzlePiece.java: Contains all the functionality realted to a Sliding Puzzle piece
SlidingPuzzleTile.java: Contains all the functionality realted to a Sliding Puzzle tile
Team.java: Contains functionality to manage a team of players
Tile.java: Tiles that contribute to the structure of a Board.
config.properties: Includes the configuration for game choices.

## Notes
---------------------------------------------------------------------------
1. GameConsole.selectTeamAndPlayer is designed to allow for selections of more
  than two players => scalability
2. Constants.java allows me to organize a collection of public static constants
  to be used throughout the program, to greatly avoid the use of magic numbers
3. DummyAgent.java and its related methods in D&BGame allows for an AI that makes
  random moves to be possible and allow for single player gameplay in multiplayer games

## Citaions
---------------------------------------------------------------------------
https://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html 
  (for color terminal line outputs)
Course material (IllegalArgumentException)
https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/ 
  (to shuffle an array so I can produce a random board)
https://analogbit.com/software/puzzletools/ (for testing boards)
https://www.cs.princeton.edu/courses/archive/spring21/cos226/assignments/8puzzle/specification.php 
  (for finding solvable boards)
https://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java/ 
  (on how to create a config.properties file and read from it)
https://www.geeksforgeeks.org/stringbuilder-class-in-java-with-examples/ 
  (regarding the use of the String Builder class in printing out my board)
https://www.geeksforgeeks.org/system-arraycopy-in-java/
  (System.arraycopy)
https://www.geeksforgeeks.org/operators-in-java/
  (To understand / and % in java)
https://pythontutor.com/render.html#mode=display
  (To simulate simple case runs)
https://www.baeldung.com/java-string-of-repeated-characters
  (To pad dynamically with spaces)
https://www.baeldung.com/java-generating-random-numbers-in-range
  (to select randomly from a range)

## How to compile and run
---------------------------------------------------------------------------
1. Navigate to the directory "TCCBoardGames" after unzipping the files
2. Run the following commands:
javac *.java
java Main 

## Input/Output Example
---------------------------------------------------------------------------
Output:
[+] Welcome to the Board Games!
[+] Enter the number of teams you want to make (min is 1), or -1 to quit:
Input: 
2
Output:
[+] Enter the name for your team, or q to quit:
Input:
Springfield
Output:
[+] Enter the team size for Team Springfield (min is 1), or -1 to quit:
Input:
2
Output:
[+] Enter the name for a player on Team Springfield, or q to quit:
Input:
Tony
Output:
[+] Enter the name for a player on Team Springfield, or q to quit:
Input:
Xiao
Output:
[+] Enter the name for your team, or q to quit:
Input:
Boston
Output:
[+] Enter the team size for Team Boston (min is 1), or -1 to quit:
Input:
1
Output:
[+] Enter the name for a player on Team Boston, or q to quit:
Input:
Kevin
Output:
[+] Hello! Find below a list of board games to play from: 
[+] 1: Sliding Puzzle
[+] 2: Dots And Boxes
[+] Enter the integer for your choice of game, 98 to add new players, 99 to add new teams, or -1 to quit: 
Input:
1
Output:
[+] Welcome to Sliding Puzzle Game!
[+] Teams:
[+] (0) Team Springfield: [Tony0, Xiao1]
[+] (1) Team Boston: [Kevin2]
[+] Select a team (Enter the team number), or enter -1 to quit:
Input:
1
Output:
[+] Players in Team Boston:
[+] (0) Kevin
[+] Select a player (Enter the player number), or enter -1 to quit:
Input:
0
Output:
[+] Please enter the number of rows you want your board to be (min is 2), or -1 to quit:
Input:
2
Output:
[+] Please enter the number of cols you want your board to be (min is 2), or -1 to quit: 
Input:
2
Output:
+---+---+
| 1 | 2 |
+---+---+
| 0 |   |
+---+---+
[+] Enter the value of the tile you want to slide, or -1 to quit: 
Input:
2
Output:
+---+---+
| 1 |   |
+---+---+
| 0 | 2 |
+---+---+
[+] Enter the value of the tile you want to slide, or -1 to quit: 
.......
.......
Output:
[+] Enter the value of the tile you want to slide, or -1 to quit: 
Input:
2
Output:
+---+---+
| 0 | 1 |
+---+---+
| 2 |   |
+---+---+
[+] Congratulations! You've won!
[+] Do you want to play another round of SlidingPuzzleGame? (y for yes, n for no): 
Input:
n
Output:
[+] Summary for Kevin:
[+] Number of Sliding Puzzle games played: 1
[+] Number of Sliding Puzzle games won: 1
[+] Total number of moves made: 4

[+] Hello! Find below a list of board games to play from: 
[+] 1: Sliding Puzzle
[+] 2: Dots And Boxes
[+] Enter the integer for your choice of game, 98 to add new players, 99 to add new teams, or -1 to quit: 
Input:
2
Output:
[+] Welcome to Dots and Boxes!
[+] Teams:
[+] (0) Team Springfield: [Tony0, Xiao1]
[+] (1) Team Boston: [Kevin2]
[+] Select a team (Enter the team number), or enter -1 to quit:
Input:
0
Output:
[+] Players in Team Springfield:
[+] (0) Tony
[+] (1) Xiao
[+] Select a player (Enter the player number), or enter -1 to quit:
Input:
1
Output:
[+] Teams:
[+] (0) Team Springfield: [Tony0]
[+] (1) Team Boston: [Kevin2]
[+] Select a team (Enter the team number), or enter -1 to quit:
Input:
1
Output:
[+] Players in Team Boston:
[+] (0) Kevin
[+] Select a player (Enter the player number), or enter -1 to quit:
Input:
0
Output:
[+] Please enter the number of rows you want your board to be (min is 2), or -1 to quit: 
Input:
2
Output:
[+] Please enter the number of cols you want your board to be (min is 2), or -1 to quit: 
Input:
2
Output:
[+] Enter 0 to let Xiao1 go first, 1 to let Kevin2 go first, or -1 to quit:
Input:
0
Output:
.            .            .
 null (0)     null (1)     
.            .            .
 null (2)     null (3)     
.            .            .

[+] Enter the value of the tile Xiao1 wants to select, or -1 to quit: 
Input:
0
Output:
[+] Enter the edge number Xiao1 wants to select (1 = left edge, 2 = top edge, 3 = right edge, 4 = bottom edge), 0 to go back to selecting a tile, or -1 to quit playing: 
Input:
1
Output:
.            .            .
|null (0)     null (1)     
.            .            .
 null (2)     null (3)     
.            .            .
......
......
Output:
.------------.------------.
|X1 (0)      |X1 (1)      |
.------------.------------.
|X1 (2)      |null (3)    |
.------------.            .

[+] Enter the value of the tile Xiao1 wants to select, or -1 to quit: 
Input:
3
Output:
[+] Enter the edge number Xiao1 wants to select (1 = left edge, 2 = top edge, 3 = right edge, 4 = bottom edge), 0 to go back to selecting a tile, or -1 to quit playing: 
Input:
4
Output:
.------------.------------.
|X1 (0)      |X1 (1)      |
.------------.------------.
|X1 (2)      |X1 (3)      |
.------------.------------.

[+] 4:0 Congratulations! Xiao1 has won!
[+] Do you want to play another round of Dots and Boxes? (y for yes, n for no): 
Input:
n
Output:
[+] Summary for Xiao:
[+] Number of Dots and Boxes games played: 1
[+] Number of Dots and Boxes games won: 1
[+] Total number of boxes got: 4

[+] Summary for Kevin:
[+] Number of Dots and Boxes games played: 1
[+] Number of Dots and Boxes games won: 0
[+] Total number of boxes got: 0

[+] Hello! Find below a list of board games to play from: 
[+] 1: Sliding Puzzle
[+] 2: Dots And Boxes
[+] Enter the integer for your choice of game, 98 to add new players, 99 to add new teams, or -1 to quit: 
Input:
-1
Output:
[+] Goodbye! Thanks for playing.