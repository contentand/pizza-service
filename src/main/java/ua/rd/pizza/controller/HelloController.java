package ua.rd.pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.infrastructure.web.MyController;
import ua.rd.pizza.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class HelloController implements MyController, org.springframework.web.servlet.mvc.Controller {

    @Autowired
    ProductService productService;

    @RequestMapping("/here")
    @ResponseBody
    public String hello(){
        return "hello";
    }

    @RequestMapping(value = "/here", method = RequestMethod.POST)
    @ResponseBody
    public String postHello(){
        return "REST POST HELLO";
    }

    @RequestMapping("/product/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable Long id) {
        return productService.getById(id);
    }

    @RequestMapping("/json")
    @ResponseBody
    Object json() {
        return new Object() {
            private String name;
            private String last;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLast() {
                return last;
            }

            public void setLast(String last) {
                this.last = last;
            }
        };
    }


    @Override
    public void handleMyRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handle(request, response);
    }


    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        handle(request, response);
        return null;
    }

    private void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(PrintWriter out=response.getWriter()){
            out.println(productService.getAllProducts());
        }
    }
}
