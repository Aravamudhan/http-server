package amudhan.httpserver.request;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import amudhan.httpserver.utils.StringUtils;

public class GetRequest implements Request {

  @Override
  public void handleRequest(HttpExchange exchange) throws IOException {
    Headers headers = exchange.getRequestHeaders();
    Set<Entry<String, List<String>>> headerSet = headers.entrySet();
    System.out.println(".....Headers.....");
    for (Entry<String, List<String>> value : headerSet) {
      System.out.println(value.getKey() + ":" + value.getValue());
    }
    Map<String, String> queryParams =
        StringUtils.getQueryParameters(exchange.getRequestURI().getQuery());
    System.out.println(".....Query parameters.....");
    for (String key : queryParams.keySet()) {
      System.out.println(key + " = " + queryParams.get(key));
    }
    String response = "Successfully received the request";
    exchange.sendResponseHeaders(200, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }


}
