package ru.myspring.di.configurator;

import lombok.RequiredArgsConstructor;
import ru.myspring.di.ObjectConfigurator;
import ru.myspring.di.ObjectFactory;
import ru.myspring.di.annotation.InjectByType;

import java.lang.reflect.Field;

/**
 * Конфигуратор для автоматического внедрения зависимостей в поля, аннотированные @InjectByType.
 */
@RequiredArgsConstructor
public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
    private final ObjectFactory factory;

    @Override
    public void configure(Object t) throws ReflectiveOperationException {
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);
                field.set(t, factory.createObject(field.getType()));
            }
        }
    }
}
