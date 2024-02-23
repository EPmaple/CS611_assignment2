/*
 * InputHandler.java
 * rovides methods for getting user inputs from the console.
 */
import java.util.Scanner;

public class InputHandler {
  private Scanner scanner = new Scanner(System.in);

  // Example usage:
  // int userInput = InputHandler.getIntInput("Please enter any integer: ");
  public int getIntInput(String prompt) { 
    int input;

    do {
      System.out.println(prompt);

      while (!scanner.hasNextInt()) {
        System.out.println("Invalid input. Please enter a valid integer.");
        scanner.next(); // If user input is not an int, this line consumes it to clear the buffer
      }
      input = scanner.nextInt();
      /*
       * after reading an integer input with the above line, there is still
       * a newline character in the input buffer, which the following will consume
       * to ensure other getInputs will work as intended
       */
      scanner.nextLine();

    } while (input < Constants.QUIT);

    return input;
  }

  // Example usage
  // String userInput = InputHandler.getStringInput("Please enter any non-empty string: ");
  public String getStringInput(String prompt) {
    String input;

    do {
      System.out.println(prompt);
      // Read the entire line and remove leading/trailing whitespaces
      input = scanner.nextLine().trim();

      if (input.isEmpty()) {
        System.out.println("Invalid input. Please enter a non-empty string.");
      }

    } while (input.isEmpty());

    return input;
  }

  // Example usage
  // boolean userInput = InputHandler.getBooleanInput("Please enter y for yes, n for no: ");
  public Boolean getBooleanInput(String prompt) {
    Boolean input = null;

    do {
      System.out.println(prompt);
      String userInput = scanner.next().toLowerCase();

      if (userInput.equals("y")) {
        input = true;
      } else if (userInput.equals("n")) {
        input = false;
      } else {
        System.out.println("Invalid input.");
      }

    } while (input == null);

    return input;
  }
}
