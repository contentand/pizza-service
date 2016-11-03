package ua.rd.pizza.infrastructure.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MyController {
    default void handleMyRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
