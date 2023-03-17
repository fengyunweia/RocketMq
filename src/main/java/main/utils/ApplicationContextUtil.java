package main.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * @author fengyunwei
 */
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
        environment = context.getEnvironment();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Environment getEnvironment() {
        return environment;
    }

    public static Resource getResource(String location) {
        return applicationContext.getResource(location);
    }

    public static boolean containsBean(String beanId) {
        assertApplicationContext();
        return applicationContext.containsBean(beanId);
    }

    public static Object getBean(String beanId) {
        assertApplicationContext();
        return applicationContext.getBean(beanId);
    }

    public static <T> T getBean(String beanId, Class<T> clazz) {
        assertApplicationContext();
        return applicationContext.getBean(beanId, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        assertApplicationContext();
        return applicationContext.getBean(clazz);
    }

    public static boolean containsProperty(String key) {
        assertEnvironment();
        return environment.containsProperty(key);
    }

    public static String getProperty(String key) {
        assertEnvironment();
        return environment.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        assertEnvironment();
        return environment.getProperty(key, defaultValue);
    }

    public static <T> T getProperty(String key, Class<T> clazz) {
        assertEnvironment();
        return environment.getProperty(key, clazz);
    }

    public static <T> T getProperty(String key, Class<T> clazz, T defaultValue) {
        assertEnvironment();
        return environment.getProperty(key, clazz, defaultValue);
    }

    public static String[] getActiveProfiles() {
        assertEnvironment();
        return environment.getActiveProfiles();
    }

    public static String[] getDefaultProfiles() {
        assertEnvironment();
        return environment.getDefaultProfiles();
    }

    private static void assertApplicationContext() {
        Assert.notNull(applicationContext, "ApplicationContext对象未注入！");
    }

    private static void assertEnvironment() {
        Assert.notNull(environment, "Environment对象未注入！");
    }
}

