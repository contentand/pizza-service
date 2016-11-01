package ua.rd.pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.rd.pizza.infrastructure.web.MyController;
import ua.rd.pizza.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller("/hello")
public class HelloController implements MyController {

    @Autowired
    ProductService productService;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(PrintWriter out=response.getWriter()){
            out.println(productService.getAllProducts());
        }
    }
}
