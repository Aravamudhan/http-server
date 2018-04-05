package amudhan.httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import amudhan.httpserver.handlers.ServerHandler;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class SimpleHttpServer {
  private static final Logger logger = LogManager.getLogger(SimpleHttpServer.class.getName());

  public static void main(String[] args) throws IOException {
    InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8000);
    HttpServer server = HttpServer.create(address, 0);
    server.createContext("/", new ServerHandler());
    server.setExecutor(null);
    logger.info("Server started at " + server.getAddress().getHostString() + ":"
        + server.getAddress().getPort());
    server.start();
  }
}
