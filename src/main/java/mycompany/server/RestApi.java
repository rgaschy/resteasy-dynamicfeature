package mycompany.server;

import javax.ws.rs.Path;

@Path("")
public interface RestApi {
    @Path("hello")
    HelloResource hello();
}
