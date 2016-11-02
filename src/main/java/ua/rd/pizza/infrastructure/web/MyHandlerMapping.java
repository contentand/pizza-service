package ua.rd.pizza.infrastructure.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MyHandlerMapping {
    void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
