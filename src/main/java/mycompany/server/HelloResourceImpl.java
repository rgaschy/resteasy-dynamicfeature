package mycompany.server;

import java.io.IOException;

public class HelloResourceImpl implements HelloResource {

    public HelloResourceImpl() {
    }

    @Override
    public String sayHello() {
        return "Hello world";
    }

    @Override
    public byte[] showHello() throws IOException {
        return getClass()
                .getResourceAsStream("hello.png")
                .readAllBytes();
    }
}
