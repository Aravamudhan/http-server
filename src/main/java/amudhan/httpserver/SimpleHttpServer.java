package amudhan.httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import amudhan.httpserver.handlers.ServerHandler;

public class SimpleHttpServer {
  public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/", new ServerHandler());
    server.setExecutor(null);
    server.start();
  }
}
