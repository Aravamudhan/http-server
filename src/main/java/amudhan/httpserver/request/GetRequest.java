package amudhan.httpserver.request;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import amudhan.httpserver.utils.FileUtils;
import amudhan.httpserver.utils.StringUtils;

public class GetRequest implements Request {

  private final Logger logger = LogManager.getLogger(this.getClass().getName());

  @Override
  public void handleRequest(HttpExchange exchange) throws IOException {
    Headers requestheaders = exchange.getRequestHeaders();
    Set<Entry<String, List<String>>> headerSet = requestheaders.entrySet();
    logger.debug("==========Headers==========");
    for (Entry<String, List<String>> value : headerSet) {
      logger.trace("{} : {} ", new Object[] {value.getKey(), value.getValue()});
    }
    logger.debug("===========================");
    logger.debug("==========Query parameters==========");
    Map<String, String> queryParams =
        StringUtils.getQueryParameters(exchange.getRequestURI().getQuery());
    for (String key : queryParams.keySet()) {
      logger.debug("{} = {} ", new Object[] {key, queryParams.get(key)});
    }
    logger.debug("====================================");
    // Returns the current working directory
    String currentDirectory = System.getProperty("user.dir");
    // Get the resource path
    String path = exchange.getRequestURI().toString();
    // Construct an absolute path for a resource
    String finalPath = currentDirectory + path;
    boolean isValidPath = Files.exists(Paths.get(finalPath));
    byte[] response = null;
    // If this is a valid path and a file
    if (!isValidPath && !new File(finalPath).isFile()) {
      logger.info("The path {} is not valid", new Object[] {finalPath});
      Path notFound = FileUtils.getTemplate("notfound.html");
      response = Files.readAllBytes(notFound);
      exchange.sendResponseHeaders(404, response.length);
    } else {
      Headers responseheaders = exchange.getResponseHeaders();
      Path filepath = Paths.get(finalPath);
      Tika tika = new Tika();
      String type = tika.detect(new File(finalPath));
      logger.info("The resource content-type : {}", new Object[] {type});
      if (StringUtils.hasLength(type)) {
        responseheaders.set("Content-type", type);
        byte[] bytes = Files.readAllBytes(filepath);
        exchange.sendResponseHeaders(200, Files.size(filepath));
        response = bytes;
      } else {
        logger.info("The filetype is not valid");
        Path invalidType = FileUtils.getTemplate("invalidtype.html");
        response = Files.readAllBytes(invalidType);
        type = Files.probeContentType(invalidType);
        responseheaders.set("Content-type", type);
        exchange.sendResponseHeaders(400, response.length);
      }
    }
    logger.info("{} {} {} {}", new Object[] {exchange.getLocalAddress().getHostString(),
        exchange.getRequestURI(), exchange.getProtocol(), exchange.getResponseCode()});
    OutputStream os = exchange.getResponseBody();
    os.write(response);
    os.close();
  }


}
