package ua.rd.pizza.infrastructure;

public class InitialContext {

    private static Config config = new MapBasedConfig();

    public static <T> T getInstance(String beanName) {
        try {
            Class<?> type = config.getImplementation(beanName);
            return (T) type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
