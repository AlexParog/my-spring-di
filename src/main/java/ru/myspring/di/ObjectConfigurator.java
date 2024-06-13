package ru.myspring.di;

/**
 * Интерфейс для конфигурирования объектов.
 */
public interface ObjectConfigurator {
    /**
     * Конфигурирует заданный объект.
     *
     * @param t объект для конфигурирования
     * @throws ReflectiveOperationException в случае ошибки конфигурации
     */
    void configure(Object t) throws ReflectiveOperationException;
}
