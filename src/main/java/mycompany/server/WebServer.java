package mycompany.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.jboss.resteasy.plugins.server.sun.http.HttpContextBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class WebServer {
    private final HttpServer httpServer;
    private final HttpContextBuilder contextBuilder;

    public WebServer(int port) throws IOException {
        InetSocketAddress addr = new InetSocketAddress("localhost", port);
        httpServer = HttpServer.create(addr, 10);
        contextBuilder = new HttpContextBuilder();
    }

    public void start() {
        contextBuilder
                .getDeployment()
                .setApplicationClass(RestApplicationImpl.class.getName());
        HttpContext context = contextBuilder
                .bind(httpServer);

        System.out.println("Application bound to " + context.getPath());

        httpServer.start();
    }

    public String getURL() throws MalformedURLException {
        return new URL("http", httpServer.getAddress().getHostString(), httpServer.getAddress().getPort(), "/").toString();
    }

    public void stop() {
        contextBuilder.cleanup();
        httpServer.stop(0);
    }

    public static void main(String[] args) {
        WebServer server = null;
        try {
            server = new WebServer(8181);
            server.start();
            Thread.currentThread().join(); // keep server running...
        } catch (Exception e) {
            e.printStackTrace(System.err);
            if (server != null) {
                server.stop();
            }
            System.exit(1);
        }
    }
}
