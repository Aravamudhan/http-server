package amudhan.httpserver.request;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;

public interface Request {

  String GET_REQUEST = "GET";

  void handleRequest(HttpExchange exchange) throws IOException;
}
