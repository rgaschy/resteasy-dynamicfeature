package mycompany.server;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebServerTest {
    private static WebServer webServer;
    private ResteasyClient client;

    @BeforeAll
    static void init() throws IOException  {
        webServer = new WebServer(0);
        webServer.start();
        System.out.print("Server started at " + webServer.getURL());
    }

    @AfterAll
    static void finish() {
        webServer.stop();
    }

    @BeforeEach
    void openClient() {
        client = new ResteasyClientBuilder()
                .build();
    }

    @AfterEach
    void closeClient() {
        client.close();
    }


    @Test
    void testSayHello() throws MalformedURLException {
        RestApi rest = client
                .target(webServer.getURL())
                .proxy(RestApi.class);
        HelloResource hello = rest.hello();
        assertEquals("Hello world", hello.sayHello());
    }

    @Test
    void testShowHello() throws MalformedURLException, ExecutionException, InterruptedException {
        Response response = client
                .target(webServer.getURL())
                .path("/hello/showHello")
                .request()
                .buildGet()
                .submit()
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("max-age=3600", response.getHeaderString("cache-control"));
        // The cache-control header is not set, because the @Cache annotation is not detected by the CacheControlFeature
        // Indeed, since version 3.12.0.Final, resourceInfo.getMethod() gives the implementation method (i.e. HelloResourceImpl#showHello),
        // and no more the annotated method which in our case is hold by the interface (i.e. HelloResource#showHello)
        // The DynamicFeature implementation is now responsible for retrieving the annotated method.
        // This applies to all implementations of DynamicFeature relying on annotations:
        //  CacheControlFeature, RoleBasedSecurityFeature, ClientContentEncodingAnnotationFeature, ServerContentEncodingAnnotationFeature
        // and more generally, any class calling ResourceInfo#getResourceMethod() should be checked
    }
}