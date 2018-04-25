package amudhan.httpserver.request;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import amudhan.httpserver.utils.FileUtils;

public class GetRequest implements Request {

  private final Logger logger = LogManager.getLogger(this.getClass().getName());
  private Tika contentDetector = new Tika();
  private String currentDirectory = System.getProperty("user.dir");

  @Override
  public void handleRequest(HttpExchange exchange) throws IOException {
    // Returns the current working directory
    // Get the resource path
    String path = exchange.getRequestURI().toString();
    // Construct an absolute path for a resource
    String finalPath = currentDirectory + path;
    boolean isValidPath = FileUtils.isValidPath(finalPath);
    byte[] response = null;
    // If this is a valid path and a file
    if (!isValidPath) {
      logger.info("The path {} is not valid", new Object[] {finalPath});
      Path notFound = FileUtils.getTemplate("notfound.html");
      response = Files.readAllBytes(notFound);
      exchange.sendResponseHeaders(404, response.length);
    } else {
      File resource = new File(finalPath);
      Headers responseheaders = exchange.getResponseHeaders();
      // If the path is a file
      if (resource.isFile()) {
        Path filepath = Paths.get(finalPath);
        String type = contentDetector.detect(new File(finalPath));
        logger.debug("The resource content-type : {}", new Object[] {type});
        if (type != null && !type.isEmpty()) {
          responseheaders.set("Content-type", type);
          byte[] bytes = Files.readAllBytes(filepath);
          exchange.sendResponseHeaders(200, bytes.length);
          response = bytes;
        } else {
          logger.info("The filetype is not valid");
          Path invalidType = FileUtils.getTemplate("invalidtype.html");
          response = Files.readAllBytes(invalidType);
          type = contentDetector.detect(invalidType.toFile());
          responseheaders.set("Content-type", type);
          exchange.sendResponseHeaders(400, response.length);
        }
      } else if (resource.isDirectory()) {
        Path directoryList = FileUtils.getTemplate("directoryList.html");
        responseheaders.set("Content-type", contentDetector.detect(directoryList.toFile()));
        byte[] templateContent =
            formatDirectoryTemplate(Files.readAllBytes(directoryList), resource);
        exchange.sendResponseHeaders(200, templateContent.length);
        response = templateContent;
      }
    }
    logger.info("{} {} {} {}", new Object[] {exchange.getLocalAddress().getHostString(),
        exchange.getRequestURI(), exchange.getProtocol(), exchange.getResponseCode()});
    OutputStream os = exchange.getResponseBody();
    os.write(response);
    os.close();
  }

  private byte[] formatDirectoryTemplate(byte[] fileContent, File resource) {
    String directoryName = resource.getName();
    String content = new String(fileContent);
    byte[] result = null;
    File files[] = resource.listFiles();
    Path homeDir = new File(currentDirectory).toPath();
    StringBuilder fileLinks = new StringBuilder();
    for (int i = 0; i < files.length; i++) {
      Path filePath = files[i].toPath();
      Path relativePath = homeDir.relativize(filePath);
      String linkTag =
          "<p><a href='/" + relativePath.toString() + "'>" + files[i].getName() + "</a></p>";
      fileLinks.append(linkTag);
    }
    result = MessageFormat.format(content, directoryName, fileLinks.toString()).getBytes();
    return result;
  }

}
