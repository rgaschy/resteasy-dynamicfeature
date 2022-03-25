package mycompany.server;

import org.jboss.resteasy.annotations.cache.Cache;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;

@Path("")
public interface HelloResource {
    @GET
    @Produces("text/plain")
    @Path("sayHello")
    String sayHello();

    @GET
    @Path("showHello")
    @Cache(maxAge = 3600)
    @Produces("image/png")
    byte[] showHello() throws IOException;
}
