package amudhan.httpserver.handlers;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import amudhan.httpserver.request.GetRequest;
import amudhan.httpserver.request.PostRequest;
import amudhan.httpserver.request.Request;

public class ServerHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String httpMethod = exchange.getRequestMethod();
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
