package com.explorex.lxpool;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Consumer;

public class ConsumerReflectionDemo {
    public static void main(String[] args) {
        Consumer<Object[]> consumer = (args1) -> {
            // 处理逻辑
        };

        Type genericType = consumer.getClass().getGenericInterfaces()[0];
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                Type argumentType = actualTypeArguments[0];
                if (argumentType instanceof Class) {
                    Class<?> argumentClass = (Class<?>) argumentType;
                    System.out.println("Parameter type: " + argumentClass);
                    System.out.println("Number of parameters: " + argumentClass.getDeclaredFields().length);
                }
            }
        }
    }
}
