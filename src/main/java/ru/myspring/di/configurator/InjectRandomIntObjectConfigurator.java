package ru.myspring.di.configurator;

import org.reflections.ReflectionUtils;
import ru.myspring.di.ObjectConfigurator;
import ru.myspring.di.annotation.InjectRandomInt;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Конфигуратор для инъекции случайных целых чисел в поля, аннотированные @InjectRandomInt.
 */
public class InjectRandomIntObjectConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object t) throws IllegalAccessException {
        Class<?> type = t.getClass();
        Set<Field> allFields = ReflectionUtils.getAllFields(type);
        for (Field field : allFields) {
            InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
            if (annotation != null) {
                int min = annotation.min();
                int max = annotation.max();
                int value = ThreadLocalRandom.current().nextInt(min, max + 1);
                field.setAccessible(true);
                field.set(t, value);
            }
        }
    }
}
