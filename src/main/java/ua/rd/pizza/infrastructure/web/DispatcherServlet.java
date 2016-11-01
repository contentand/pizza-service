package ua.rd.pizza.infrastructure.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.rd.pizza.domain.other.Order;
import ua.rd.pizza.domain.product.Pizza;
import ua.rd.pizza.domain.product.Product;
import ua.rd.pizza.service.Cart;
import ua.rd.pizza.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DispatcherServlet extends HttpServlet {

    ConfigurableApplicationContext context;

    public void init() throws ServletException
    {
        String servletConfigLocation = getInitParameter("servletConfigLocation");
        List<Class<?>> classes = new ArrayList<>();
        try {
            if (servletConfigLocation != null)
                classes.add(Class.forName(servletConfigLocation));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to initialize " + servletConfigLocation);
        }

        String[] contextConfigLocation = getServletContext().getInitParameter("contextConfigLocation")
                .split(" ");
        classes.addAll(Arrays.stream(contextConfigLocation).map(s -> {
            try {
                return Class.forName(s);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Unable to initialize " + s);
            }
        }).collect(Collectors.toList()));

        context = new AnnotationConfigApplicationContext(classes.toArray(new Class<?>[classes.size()]));
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        // Set response content type
        response.setContentType("text/html");

        ProductService productService = context.getBean(ProductService.class, "simpleProductService");

        Pizza p = new Pizza();
        p.setName("Margarita");
        p.setType(Pizza.Type.MEAT);
        p.setUnitPrice(new BigDecimal("232.12"));

        Product pr = productService.save(p);

        Cart c = context.getBean(Cart.class);
        c.addItem(pr.getId(), 5);
        Order o = c.buy();

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println("<h1>" + o + "</h1>");
        process(request, response);
    }


    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url=request.getRequestURI();
        String controllerName=getControllerName(url);


        MyController controller= (MyController) context.getBean(controllerName);
        if(controller!=null){
            controller.handleRequest(request,response);
        }
    }

    private String getControllerName(String url) {
        return url.substring(url.lastIndexOf('/'));
    }

    @Override
    public void destroy(){
        context.close();
    }
}
