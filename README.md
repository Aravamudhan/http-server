## A simple http-server in Java
* Java provides a http server through ```com.sun.net.httpserver.HttpServer```
* The requests can be processed using an implementation of ```com.sun.net.httpserver.HttpHandler```
* The following code creates server in the current host and in the port 8000 and the ServerHandler is an implementation of HttpHandler 
```
HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
server.createContext("/", new ServerHandler());
```
