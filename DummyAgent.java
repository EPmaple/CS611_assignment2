/*
 * DummyAgent.java
 * A dummy that extends from player which makes random moves
 */
import java.util.Random;

public class DummyAgent extends Player{
  public DummyAgent(String username, int id) {
    super(username, id);
  }

  public int generateRandomMoveInRange(int min, int max) {
    Random random = new Random();
    // To get a number that falls within the range [min, max], inclusive on both ends
    // System.out.println("range: " + max + ", " + min);
    return random.nextInt(max - min + 1) + min;
  }
}
