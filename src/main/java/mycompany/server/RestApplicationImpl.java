package mycompany.server;

import org.jboss.resteasy.plugins.interceptors.CacheControlFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/rest")
public class RestApplicationImpl extends Application {

    @Override
    public Set<Object> getSingletons() {
        // CacheControlFeature is implicitly registered
        return Set.of(new RestApiImpl());
    }
}
