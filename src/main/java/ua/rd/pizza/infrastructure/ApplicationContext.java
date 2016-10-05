package ua.rd.pizza.infrastructure;

import ua.rd.pizza.infrastructure.annotation.PostCreate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.spec.ECField;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext implements Context {

    private final Config config;
    private final Map<String, Object> beans;

    public ApplicationContext(Config config) {
        this.config = config;
        this.beans = new ConcurrentHashMap<>();
    }

    @Override
    public <T> T getBean(String beanName) {
        //noinspection unchecked
        return (T) getBean(beanName, Object.class);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> type) {
        if (beans.containsKey(beanName)) {
            return type.cast(beans.get(beanName));
        } else {
            T initializedBean = getInitializedBean(beanName);
            beans.put(beanName, initializedBean);
            return initializedBean;
        }
    }

    private <T> T getInitializedBean(String beanName) {
        Class<?> type = config.getImplementation(beanName);
        if (type == null) throw new IllegalArgumentException("No Such Bean Found: " + beanName);
        BeanBuilder<?> beanBuilder = new BeanBuilder<>(type);
        beanBuilder.createBean();
        beanBuilder.callPostCreateMethod();
        beanBuilder.callInitMethod();
        return (T) beanBuilder.build();
    }

    private class BeanBuilder<T> {

        private Class<T> type;
        private T instance;

        BeanBuilder(Class<T> type) {
            this.type = type;
        }

        public void createBean() {
            try {
                this.instance = instantiateType();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public T build() {
            return this.instance;
        }

        public void callInitMethod() {
            try {
                Method initMethod = type.getMethod("init");
                if (!initMethod.isAnnotationPresent(PostCreate.class)) {
                    initMethod.invoke(instance);
                }
            } catch (NoSuchMethodException e)
            {
            } catch (Exception e) {
                  throw new RuntimeException(e);
            }
        }

        private T instantiateType() throws Exception {
            Constructor<?> constructor = getConstructor();
            if (hasNoParameters(constructor)) {
                return type.newInstance();
            }
            Object[] initializedConstructorArgs = getInitializedConstructorArguments(constructor);
            return (T) constructor.newInstance(initializedConstructorArgs);
        }

        private boolean hasNoParameters(Constructor<?> constructor) {
            return constructor.getParameterTypes().length == 0;
        }

        private Constructor<?> getConstructor() {
            Constructor<?>[] constructors = type.getConstructors();
            if (hasMultiple(constructors)) throw new IllegalStateException("Multiple constructors");
            return constructors[0];
        }

        private Object[] getInitializedConstructorArguments(Constructor<?> constructor) {
            Class<?>[] types = constructor.getParameterTypes();
            Object[] args = new Object[types.length];
            for (int i = 0; i < types.length; i++) {
                String name = getAdjustedName(types[i]);
                args[i] = getBean(name);
            }
            return args;
        }

        private boolean hasMultiple(Constructor<?>[] constructors) {
            return constructors.length > 1;
        }

        private String getAdjustedName(Class<?> klazz) {
            String name = klazz.getSimpleName();
            char[] chars = name.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return new String(chars);
        }


        public void callPostCreateMethod() {
            Method[] methods = type.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(PostCreate.class)) {
                    try {
                        method.invoke(instance);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
