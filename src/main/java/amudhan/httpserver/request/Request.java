package amudhan.httpserver.request;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;

public interface Request {

  String GET_REQUEST = "GET";
  String POST_REQUEST = "POST";

  void handleRequest(HttpExchange exchange) throws IOException;
}
