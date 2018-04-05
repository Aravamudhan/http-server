package amudhan.httpserver.handlers;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import amudhan.httpserver.request.GetRequest;
import amudhan.httpserver.request.PostRequest;
import amudhan.httpserver.request.Request;

public class ServerHandler implements HttpHandler {
  private final Logger logger = LogManager.getLogger(this.getClass().getName());

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String httpMethod = exchange.getRequestMethod();
    String host = exchange.getRemoteAddress().getHostString();
    String path = exchange.getRequestURI().toString();
    logger.info("{} {} {}", new Object[] {host, httpMethod, path});
    Request request;
    if (httpMethod.equals(Request.GET_REQUEST)) {
      request = new GetRequest();
      request.handleRequest(exchange);
    } else if (httpMethod.equals(Request.POST_REQUEST)) {
      request = new PostRequest();
      request.handleRequest(exchange);
    }
  }

}
