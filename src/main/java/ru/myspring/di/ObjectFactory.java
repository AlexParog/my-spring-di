package ru.myspring.di;

import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Фабрика для создания и конфигурирования объектов.
 */
public class ObjectFactory {
    private final Reflections scanner = new Reflections("ru.myspring");
    private final List<ObjectConfigurator> configurators = new ArrayList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    /**
     * Конструктор фабрики объектов.
     *
     * @throws ReflectiveOperationException в случае ошибки инициализации конфигураторов
     */
    public ObjectFactory() throws ReflectiveOperationException {
        Set<Class<? extends ObjectConfigurator>> classes = scanner.getSubTypesOf(ObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> aClass : classes) {
            try {
                Constructor<? extends ObjectConfigurator> constructor = aClass.getConstructor(ObjectFactory.class);
                configurators.add(constructor.newInstance(this));
            } catch (NoSuchMethodException e) {
                configurators.add(aClass.newInstance());
            }
        }
        Set<Class<? extends ProxyConfigurator>> set = scanner.getSubTypesOf(ProxyConfigurator.class);
        for (Class<? extends ProxyConfigurator> aClass : set) {
            proxyConfigurators.add(aClass.newInstance());
        }
    }

    /**
     * Создает и конфигурирует объект заданного типа.
     *
     * @param type тип объекта
     * @param <T> тип объекта
     * @return сконфигурированный объект
     * @throws ReflectiveOperationException в случае ошибки создания или конфигурации объекта
     */
    public <T> T createObject(Class<? extends T> type) throws ReflectiveOperationException {
        // Находим реализацию запрошенного типа
        Class<? extends T> implType = resolveImpl(type);
        // Создаем объект (с помощью конструктора по умолчанию)
        T obj = implType.getDeclaredConstructor().newInstance();

        // Конфигурируем
        configure(obj);
        // Запускаем методы PostConstructor
        invokeInitMethods(type, obj);

        obj = wrapWithProxyIfNeeded(obj);

        return obj;
    }

    /**
     * Находим и возвращаем имплементацию Интерфейса или Объект.
     * Если переданный type является интерфейсом, то находим его реализацию. В случае если их несколько или 0, то
     * выбрасываем RuntimeException, так как реализации написана "на коленке".
     * Иначе возвращаем класс.
     *
     * @param type тип объекта
     * @param <T> тип объекта
     * @return имплементация класса
     */
    private <T> Class<? extends T> resolveImpl(Class<? extends T> type) {
        if (type.isInterface()) {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf((Class<T>) type);
            if (classes.size() != 1) {
                throw new RuntimeException("0 or more than one impl found for type "
                        + type + " please update your config");
            }
            type = classes.iterator().next();
        }
        return type;
    }

    private <T> void configure(T t) throws ReflectiveOperationException {
        for (ObjectConfigurator configurator : configurators) {
            configurator.configure(t);
        }
    }

    private <T> T wrapWithProxyIfNeeded(T t) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            t = proxyConfigurator.wrapWithPoxy(t);
        }
        return t;
    }

    private <T> void invokeInitMethods(Class<? extends T> type, T t) throws ReflectiveOperationException {
        Method[] methods = type.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }
}
