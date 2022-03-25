package mycompany.server;

public class RestApiImpl implements RestApi {
    private final HelloResourceImpl helloResource = new HelloResourceImpl();

    public HelloResourceImpl hello() {
        return helloResource;
    }
}
