package ua.rd.pizza.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ua.rd.pizza.infrastructure.annotation.Benchmark;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BenchMarkBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return createBeanProxy(o);
    }

    private Object createBeanProxy(Object o) {
        if (isAnyMethodAnnotated(o, Benchmark.class)) {
            ClassLoader classLoader = this.getClass().getClassLoader();
            Class<?>[] interfaces = getAllDeclaredInterfaces(o);

            InvocationHandler ih = (proxy, method, args) -> {
                Benchmark annotation = o.getClass().getMethod(method.getName(),
                        method.getParameterTypes()).getAnnotation(Benchmark.class);
                if (annotation.value()) {
                    long start = System.nanoTime();
                    Object result = method.invoke(o, args);
                    long end = System.nanoTime();
                    System.out.println(method.getName() + " took " + (end - start) + " nano to execute.");
                    return result;
                }
                return method.invoke(o, args);
            };
            return Proxy.newProxyInstance(classLoader, interfaces, ih);
        }
        return o;
    }

    private boolean isAnyMethodAnnotated(Object o, Class<? extends Annotation> annotation) {
        for (Method method : o.getClass().getMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                return true;
            }
        }
        return false;
    }

    private Class<?>[] getAllDeclaredInterfaces(Object o) {
        List<Class<?>> interfaces = new ArrayList<>();
        Class<?> klazz = o.getClass();
        while (klazz != Object.class) {
            Collections.addAll(interfaces, klazz.getInterfaces());
            klazz = klazz.getSuperclass();
        }
        return interfaces.stream().toArray(Class<?>[]::new);
    }

}
