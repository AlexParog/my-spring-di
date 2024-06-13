package ru.myspring.di;

/**
 * Интерфейс для создания прокси объектов.
 */
public interface ProxyConfigurator {
    /**
     * Оборачивает объект в прокси.
     *
     * @param t объект для оборачивания
     * @param <T> тип объекта
     * @return прокси-объект
     */
    <T> T wrapWithPoxy(T t);
}
