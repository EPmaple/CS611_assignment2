/*
 * GameConfiguration.java
 * Dynamically loads and retrieves game configuration 
  properties from a file “config.properties”.
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameConfiguration {
  private static Properties properties;
  private static int maxGameIndex;

  /*
   * Attempt to load the configuration file 'config.properties' and determine
   * the number of board games there are by the variable maxGameIndex
   */
  static {
    properties = new Properties();

    try (InputStream input = GameConfiguration.class.getClassLoader().getResourceAsStream("config.properties")) {
      properties.load(input);
      maxGameIndex = calculateMaxGameIndex();
    } catch (IOException err) {
      err.printStackTrace();
    }
  }

  private static int calculateMaxGameIndex() {
    int index = 1;
    while (properties.containsKey("game" + index + ".name")) {
      if (properties.containsKey("game" + (index+1) + ".name")) {
        index++;
      } else {
        break;
      }
    }
    return index;
  }

  // Example usage
  // gameConfig.getGameName(1)
  public static String getGameName(int choice) {
    String key = "game" + choice + ".name";
    return properties.getProperty(key);
  }

  // Example usage
  // gameConfig.getMaxGameIndex()
  public static int getMaxGameIndex() {
    return maxGameIndex;
  }
}
