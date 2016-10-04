package ua.rd.pizza.infrastructure;

import java.lang.reflect.Constructor;
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
        try {
            if (beans.containsKey(beanName)) {
                return type.cast(beans.get(beanName));
            } else {
                T initializedBean = getInitializedBean(beanName);
                beans.put(beanName, initializedBean);
                return initializedBean;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T getInitializedBean(String beanName) throws Exception {
        Class<?> type = config.getImplementation(beanName);
        if (type == null) throw new IllegalArgumentException("No Such Bean Found: " + beanName);
        return instantiateType(type);
    }

    private <T> T instantiateType(Class<?> type) throws Exception {
        if (hasNoConstructor(type)) {
            return (T) type.newInstance();
        } else {
            Constructor<?> constructor = getConstructor(type);
            Object[] initializedConstructorArgs = getInitializedConstructorArguments(constructor);
            return (T) constructor.newInstance(initializedConstructorArgs);
        }
    }

    private boolean hasNoConstructor(Class<?> type) {
        return type.getConstructors().length == 0;
    }

    private Constructor<?> getConstructor(Class<?> type) {
        Constructor<?>[] constructors = type.getConstructors();
        if (hasMultiple(constructors)) throw new IllegalStateException("Multiple constructors");
        return constructors[0];
    }

    private Object[] getInitializedConstructorArguments(Constructor<?> constructor) {
        Class<?>[] types = constructor.getParameterTypes();
        Object[] args = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            String name = getAdjustedName(types[i]);
            args[i] = this.getBean(name);
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
}
