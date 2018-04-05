package amudhan.httpserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.sun.net.httpserver.HttpExchange;

public class PostRequest implements Request {

  private final Logger logger = LogManager.getLogger(this.getClass().getName());

  @Override
  public void handleRequest(HttpExchange exchange) throws IOException {
    InputStream inputStream = exchange.getRequestBody();
    String requestBody = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel()
        .collect(Collectors.joining("\n"));
    logger.trace("Request body : {}", new Object[] {requestBody});
    String response = "Successfully received the request";
    exchange.sendResponseHeaders(200, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();

  }

}
