package amudhan.httpserver.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
  /**
   * Returns a template
   * 
   * @param fileName
   * @return
   */
  public static Path getTemplate(String fileName) {
    String currentDirectory = System.getProperty("user.dir");
    Path path = Paths.get(currentDirectory + "/src/main/resources/templates/" + fileName);
    return path;
  }
}
