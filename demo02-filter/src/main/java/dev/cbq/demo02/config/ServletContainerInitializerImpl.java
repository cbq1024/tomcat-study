package dev.cbq.demo02.config;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@HandlesTypes(WebInitializer.class)
public class ServletContainerInitializerImpl implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("ServletContainerInitializerImpl.onStartup");
        List<WebInitializer> initializers = new ArrayList<>();

        assert c != null;
        c.stream()
                .filter(clazz -> !clazz.isInterface())
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .filter(WebInitializer.class::isAssignableFrom)
                .map(Class::getDeclaredConstructors)
                .forEach(constructors -> {
                    for (Constructor<?> constructor : constructors) {
                        if (constructor.getParameterCount() == 0) {
                            try {
                                WebInitializer initializer = (WebInitializer) constructor.newInstance();
                                initializers.add(initializer);
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

        System.out.println("initializers size = " + initializers.size());
        for (WebInitializer initializer : initializers) {
            initializer.onStartup(ctx);
        }

    }
}
