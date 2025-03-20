package mister.pippo;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws IOException {
        final String portEnv = System.getenv("PORT");
        int port = 8080;
        if (portEnv != null)
            port = Integer.parseInt(System.getenv("PORT"));
        final HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        server.createContext("/", httpExchange -> {
            final String res = "<html>" +
                    "<body>" +
                    "<h1>My Server</h1>" +
                    "<p>Ciao Pippo</p>" +
                    "<ul>" +
                    "<li><a href='/hello'>Hello service</a></li>" +
                    "<li><a href='/date'>Date service</a></li>" +
                    "</ul>" +
                    "</body></html>";
            httpExchange.sendResponseHeaders(200, res.length());
            final OutputStream os = httpExchange.getResponseBody();
            os.write(res.getBytes(StandardCharsets.UTF_8));
            os.close();
        });
        server.createContext("/hello", httpExchange -> {
            final String res = "chips";
            httpExchange.sendResponseHeaders(200, res.length());
            final OutputStream os = httpExchange.getResponseBody();
            os.write(res.getBytes(StandardCharsets.UTF_8));
            os.close();
        });

        server.createContext("/date", httpExchange -> {
            final String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
            httpExchange.sendResponseHeaders(200, now.length());
            final OutputStream os = httpExchange.getResponseBody();
            os.write(now.getBytes(StandardCharsets.UTF_8));
            os.close();
        });
        server.setExecutor(null);
        server.start();
    }
}