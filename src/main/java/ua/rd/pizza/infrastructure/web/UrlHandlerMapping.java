package ua.rd.pizza.infrastructure.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UrlHandlerMapping implements MyHandlerMapping, ApplicationContextAware {

    private ApplicationContext webContext;

    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getRequestURI();
        String controllerName = getControllerName(url);
        MyController controller= (MyController) webContext.getBean(controllerName);
        if(controller!=null){
            controller.handleMyRequest(req,resp);
        }
    }

    private String getControllerName(String url) {
        return url.substring(url.lastIndexOf('/'));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.webContext = applicationContext;
    }
}
