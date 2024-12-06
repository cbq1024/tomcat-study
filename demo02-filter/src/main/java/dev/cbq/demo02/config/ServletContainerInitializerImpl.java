package dev.cbq.demo02.config;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@HandlesTypes(WebInitializer.class)
public class ServletContainerInitializerImpl implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("ServletContainerInitializerImpl.onStartup");

        if (c == null || c.isEmpty()) {
            System.out.println("No classes found for initialization.");
            return;
        }

        List<WebInitializer> initializers = c.stream()
                .filter(clazz -> !clazz.isInterface())
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .filter(WebInitializer.class::isAssignableFrom)
                .flatMap(clazz -> Arrays.stream(clazz.getDeclaredConstructors()))
                .filter(constructor -> constructor.getParameterCount() == 0)
                .map(constructor -> {
                    try {
                        return (WebInitializer) constructor.newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException("Failed to initialize WebInitializer: " + constructor.getDeclaringClass(), e);
                    }
                })
                .toList();

        System.out.println("initializers size = " + initializers.size());
        for (WebInitializer initializer : initializers) {
            initializer.onStartup(ctx);
        }
    }
}

